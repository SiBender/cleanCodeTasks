package com.foxminded.race.controller;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.foxminded.race.model.RaceLap;
import com.foxminded.race.model.Racer;

public class RaceReportBuilder {
    private static final String SEPARATOR = " | ";
    private static final String HORIZONTAL_LINE_SYMBOL = "-";
    private static final int LIST_START_NUM = 1;
    private static final int NAME_PREFIX_LENGTH = 4;
    private static final int WINNERS_NUMBER = 15;
    private static final DateTimeFormatter LAP_TIME_FORMATTER = DateTimeFormatter.ofPattern("m:ss.SSS");
    
    private RaceDataLoader dataLoader;
    
    public RaceReportBuilder() {
        dataLoader = new FilesDataLoader();
    }
    
    public RaceReportBuilder(RaceDataLoader raceDataLoader) {
        if (raceDataLoader == null) {
            throw new IllegalArgumentException("Data loader is null");
        }
        dataLoader = raceDataLoader;
    } 

    public String build() throws IOException {
        List<Racer> races = dataLoader.loadData();
        races.sort((r1, r2) -> getRaceDuration(r1).compareTo(getRaceDuration(r2)));

        return buildResultTable(races);
    }

    public String buildResultTable(List<Racer> races) {     
        StringJoiner output = new StringJoiner(System.lineSeparator());
        String outputFormat = calculateTableFormat(races);
        for (int i = 0; i < races.size(); i++) {
            Racer racer = races.get(i);
            int counter = LIST_START_NUM + i;
            
            String racerNameField = String.format("%d. %s", counter, racer.getName());
            String currentLine = String.format(outputFormat, racerNameField, racer.getTeam(), getLapTime(racer));
            
            if (i == WINNERS_NUMBER) {
                output.add(buildLine(currentLine.length()));
            }
            output.add(currentLine);
        }
        return output.toString();
    }
    
    private String calculateTableFormat(List<Racer> races) {
        int maxNameLength = races.stream().mapToInt(racer -> racer.getName().length()).max().orElse(0);
        maxNameLength += NAME_PREFIX_LENGTH;
        
        int maxTeamLength = races.stream().mapToInt(racer -> racer.getTeam().length()).max().orElse(0);                           

        StringBuilder formatString = new StringBuilder();
        formatString.append("%-").append(maxNameLength).append("s").append(SEPARATOR);
        formatString.append("%-").append(maxTeamLength).append("s").append(SEPARATOR);
        formatString.append("%s");
        
        return formatString.toString();
    }

    private String getLapTime(Racer racer) {
        Duration duration = getRaceDuration(racer);
        LocalTime lapTime = LocalTime.ofNanoOfDay(duration.toNanos());

        return lapTime.format(LAP_TIME_FORMATTER);
    }
    
    private Duration getRaceDuration(Racer racer) {
        List<RaceLap> laps = racer.getLaps();
        if (laps == null) {
            throw new IllegalArgumentException("No information about races. Racer: " + racer.getName());
        }
        RaceLap lap = laps.get(laps.size() - 1);
        if (lap.getStartTime() == null) {
            throw new IllegalArgumentException("Start time is not set. Racer: " + racer.getName());
        } else if (lap.getFinishTime() == null) {
            throw new IllegalArgumentException("Finish time is not set. Racer: " + racer.getName());
        }
        return Duration.between(lap.getStartTime(), lap.getFinishTime());
    }
    
    private String buildLine(int length) {        
        return Stream.generate(() -> HORIZONTAL_LINE_SYMBOL).limit(length).collect(Collectors.joining());
    }
}
