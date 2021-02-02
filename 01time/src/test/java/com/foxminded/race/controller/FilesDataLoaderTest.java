package com.foxminded.race.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.foxminded.race.model.RaceLap;
import com.foxminded.race.model.Racer;
import com.foxminded.race.controller.FilesDataLoader;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FilesDataLoaderTest {
    List<Racer> actualRacers;
    Racer actual;
    Racer expected;
    
    @BeforeAll
    void init() throws IOException {
        FilesDataLoader dataLoader =  new FilesDataLoader();

        actualRacers = dataLoader.loadData();
        
        expected = new Racer("Second Name", "Second Team");
        expected.addLap(new RaceLap(LocalDateTime.parse("2020-01-01T13:00:05.200")));
        expected.getLaps().get(0).setFinishTime(LocalDateTime.parse("2020-01-01T13:01:10.000"));
        
        for (Racer racer : actualRacers) {
            if (racer.getName().equals(expected.getName()) && racer.getTeam().equals(expected.getTeam())) {
                actual = racer;
            }
        }
    }
    
    @Test
    void loadDataArrayShoulContainExpectedNumberOfRacersTest() {
        assertEquals(3, actualRacers.size());
    }
    
    @Test
    void loadDataContainsSpecifiedRacerTest() {
        assertNotNull(actual);
    }
    
    @Test
    void loadDataStartAndFinishTimeForParticularRacerIsSetTest() {
        assertEquals(expected.getLaps().get(0).getStartTime(), actual.getLaps().get(0).getStartTime());
        assertEquals(expected.getLaps().get(0).getFinishTime(), actual.getLaps().get(0).getFinishTime()); 
    }
}
