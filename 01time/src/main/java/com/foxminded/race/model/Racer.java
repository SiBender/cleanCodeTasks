package com.foxminded.race.model;

import java.util.ArrayList;
import java.util.List;

public class Racer {
    private final String name;
    private final String team;
    private List<RaceLap> raceLaps;
    
    public Racer(String name, String team) {
        this.name = name;
        this.team = team;
    }
    
    public void addLap(RaceLap lap) {
        if (raceLaps == null) {
            raceLaps = new ArrayList<>();
        }
        raceLaps.add(lap);
    }
    
    public List<RaceLap> getLaps() {
        return raceLaps;
    }
    
    public String getName() {
        return name;
    }
    
    public String getTeam() {
        return team;
    }
}
