package com.foxminded.summary;

import java.util.Scanner;
import com.foxminded.summary.statistics.CharacterStatisticsBuilder;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CharacterStatisticsBuilder statisticsBuilder = new CharacterStatisticsBuilder();

        while (true) {
            System.out.println("\n[Ctrl+C to exit]");
            System.out.println("Enter the text:");
            String input = scanner.nextLine();
            try {
                System.out.println(statisticsBuilder.build(input));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (RuntimeException e) {
                System.out.println("internal error");
            }
        }
    }
}
