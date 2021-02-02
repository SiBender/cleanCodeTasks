package com.foxminded.race;

import java.io.IOException;

import com.foxminded.race.controller.RaceReportBuilder;

public class App {
    public static void main(String[] args) {
        RaceReportBuilder result = new RaceReportBuilder();
        try {
            System.out.println(result.build());
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        } catch (RuntimeException exception) {
            System.out.println("Internal error");
        }
    }
}
