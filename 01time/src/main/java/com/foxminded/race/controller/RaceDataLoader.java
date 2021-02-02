package com.foxminded.race.controller;

import java.io.IOException;
import java.util.List;

import com.foxminded.race.model.Racer;

public interface RaceDataLoader {
    public List<Racer> loadData() throws IOException;
}
