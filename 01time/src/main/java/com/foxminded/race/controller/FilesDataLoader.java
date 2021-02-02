package com.foxminded.race.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.foxminded.race.model.RaceLap;
import com.foxminded.race.model.Racer;

public class FilesDataLoader implements RaceDataLoader {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'_'HH:mm:ss.SSS");
    private static final String SEPARATOR = "_";
    private static final int ABBREVIATION_LEN = 3;
    private static final int RACER_NAME_INDEX = 1;
    private static final int RACER_TEAM_INDEX = 2;
    
    private String abbreviationsFIleName = "abbreviations.txt";
    private String startFileName = "start.log";
    private String finishFileName = "end.log";
      
    @Override
    public List<Racer> loadData() throws IOException {
        Map<String, Racer> output = readRaceNames(abbreviationsFIleName);
        readStartLog(startFileName, output);
        readFinishLog(finishFileName, output);

        return new ArrayList<Racer>(output.values());
    }

    private Map<String, Racer> readRaceNames(String abbreviationsFileName) throws IOException {
        File inputFile = getFileFromResources(abbreviationsFileName);

        try (BufferedReader names = new BufferedReader(new FileReader(inputFile));
             Stream<String> abbreviations = names.lines()) {
            return abbreviations.collect(Collectors.toMap(this::getKey,
                                    value -> new Racer(retrieveRacerName(value), retrieveRacerTeam(value))));
        } catch (IOException e) {
            throw new IOException("Issues while reading 'abbreviations.txt'");
        }
    }
    
    private void readStartLog(String startFileName, Map<String, Racer> output) throws IOException {
        File inputFile = getFileFromResources(startFileName);

        try (BufferedReader startLog = new BufferedReader(new FileReader(inputFile));
             Stream<String> starts = startLog.lines()) {
            starts.forEach(line -> output.get(getKey(line)).addLap(new RaceLap(retrieveDateTime(line))));
        } catch (IOException e) {
            throw new IOException("Issues while reading 'start.log'");
        }
    }

    private void readFinishLog(String finishFileName, Map<String, Racer> output) throws IOException {
        File inputFile = getFileFromResources(finishFileName);

        try (BufferedReader finishLog = new BufferedReader(new FileReader(inputFile));
             Stream<String> filnishLines = finishLog.lines()) {
            filnishLines.forEach(line -> output.get(getKey(line)).getLaps().get(0).setFinishTime(retrieveDateTime(line)));
        } catch (IOException e) {
            throw new IOException("Issues while reading 'end.log'");
        }
    }

    private File getFileFromResources(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();

        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("File is not found:" + fileName);
        } else {
            return new File(resource.getFile());
        }
    }

    private String getKey(String line) {
        return line.substring(0, ABBREVIATION_LEN);
    }
    
    private String getValue(String line) {
        return line.substring(ABBREVIATION_LEN, line.length());
    }

    private LocalDateTime parseDateTime(String input) {
        return LocalDateTime.parse(input, DATE_TIME_FORMATTER);
    }
    
    private LocalDateTime retrieveDateTime(String line) {
        return parseDateTime(getValue(line));
    }
    
    private String retrieveRacerName(String line) {
        String[] subStrings = line.split(SEPARATOR);
        return subStrings[RACER_NAME_INDEX];
    }
    
    private String retrieveRacerTeam(String line) {
        String[] subStrings = line.split(SEPARATOR);
        return subStrings[RACER_TEAM_INDEX];
    }
}
