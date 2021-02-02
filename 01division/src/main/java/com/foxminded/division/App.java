package com.foxminded.division;

import java.util.Scanner;

import com.foxminded.division.builder.LongDivisionBuilder;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LongDivisionBuilder divisionBuilder = new LongDivisionBuilder();
        
        while(true) {
            System.out.println("\n[Ctrl+C to exit]");
            System.out.println("Enter the divident:");
            String divident = scanner.nextLine();
            System.out.println("Enter the divider:");
            String divider = scanner.nextLine();
            try {
                System.out.println("\n" + divisionBuilder.build(Integer.valueOf(divident),Integer.valueOf(divider)));
            } catch (NumberFormatException e) {
                System.out.println("divident and divider must be valid integers");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (RuntimeException e) {
                System.out.println("internal error");
            }
        }
    }
}

