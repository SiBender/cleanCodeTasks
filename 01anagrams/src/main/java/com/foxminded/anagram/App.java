package com.foxminded.anagram;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write a word or phrase to generate anagrams:");
        String input = scanner.nextLine();

        AnagramGenerator anagram = new AnagramGenerator();

        System.out.println("\nAnagram for your input:");
        System.out.println(anagram.generate(input));
    }
}