package com.foxminded.race.model;

import java.time.LocalDateTime;

public class RaceLap {
    private LocalDateTime start;
    private LocalDateTime finish;
    
    public RaceLap(LocalDateTime startTime) {
        start = startTime;
    }
    
    public LocalDateTime getStartTime() {
        return start;
    }
    
    public void setFinishTime(LocalDateTime finishTime) {
        finish = finishTime;
    }
    
    public LocalDateTime getFinishTime() {
        return finish;
    }
}
