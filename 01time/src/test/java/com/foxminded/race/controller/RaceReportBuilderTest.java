package com.foxminded.race.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import com.foxminded.race.model.RaceLap;
import com.foxminded.race.model.Racer;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RaceReportBuilderTest {
    String lineBreak = System.lineSeparator();
    
    List<Racer> races = new ArrayList<>();
    
    @BeforeAll
    void init() {
        Racer firstRacer = new Racer("First Name", "First Team");
        firstRacer.addLap(new RaceLap(LocalDateTime.parse("2020-01-01T14:00:10.500")));
        firstRacer.getLaps().get(0).setFinishTime(LocalDateTime.parse("2020-01-01T14:01:20.000"));
        
        Racer secoundRacer = new Racer("Second Name", "Second Team");
        secoundRacer.addLap(new RaceLap(LocalDateTime.parse("2020-01-01T13:00:05.200")));
        secoundRacer.getLaps().get(0).setFinishTime(LocalDateTime.parse("2020-01-01T13:01:10.000"));
        
        Racer thirdRacer = new Racer("Third Name", "Third Team");
        thirdRacer.addLap(new RaceLap(LocalDateTime.parse("2020-01-01T12:00:00.001")));
        thirdRacer.getLaps().get(0).setFinishTime(LocalDateTime.parse("2020-01-01T12:01:00.000"));
        
        races.add(firstRacer);
        races.add(secoundRacer);
        races.add(thirdRacer);
    }
    
    @Test
    void buildCorrectDataAndFormattingTest() throws IOException {
        String expected = "1. Third Name   | Third Team  | 0:59.999" + lineBreak
                        + "2. Second Name  | Second Team | 1:04.800" + lineBreak
                        + "3. First Name   | First Team  | 1:09.500";
        
        RaceDataLoader dataLoader = Mockito.mock(RaceDataLoader.class);
        Mockito.when(dataLoader.loadData()).thenReturn(races);

        RaceReportBuilder reportBuilder = new RaceReportBuilder(dataLoader);

        assertEquals(expected, reportBuilder.build());
    }
    
    @Test
    void buildShouldThrowExceptionWhenConstructorReceivesNullObject() {
        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> {
            FilesDataLoader emptyLoader = null;
            new RaceReportBuilder(emptyLoader);
        });
        assertEquals("Data loader is null", thrown.getMessage());
    }
    
    @Test
    void buildShouldThrowExceptionWhenOneOfRacersHasEmptyLapsListTest() throws IOException {
        List<Racer> fakeRacers = new ArrayList<>();
        fakeRacers.add(new Racer("Poison Racer", "Poison Team"));

        RaceDataLoader dataLoader = Mockito.mock(RaceDataLoader.class);
        Mockito.when(dataLoader.loadData()).thenReturn(fakeRacers);

        RaceReportBuilder reportBuilder = new RaceReportBuilder(dataLoader);

        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> {
            reportBuilder.build();
        });
        assertEquals("No information about races. Racer: Poison Racer", thrown.getMessage());
    }
    
    @Test
    void buildShouldThrowExceptionWhenOneOfRacersHasEmptyFinishTimeTest() throws IOException {
        List<Racer> fakeRacers = new ArrayList<>();
        fakeRacers.add(new Racer("Poison Racer", "Poison Team"));
        fakeRacers.get(0).addLap(new RaceLap(LocalDateTime.parse("2020-01-01T14:00:10.500")));

        RaceDataLoader dataLoader = Mockito.mock(RaceDataLoader.class);
        Mockito.when(dataLoader.loadData()).thenReturn(fakeRacers);

        RaceReportBuilder reportBuilder = new RaceReportBuilder(dataLoader);

        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> {
            reportBuilder.build();
        });
        assertEquals("Finish time is not set. Racer: Poison Racer", thrown.getMessage());
    }
}
