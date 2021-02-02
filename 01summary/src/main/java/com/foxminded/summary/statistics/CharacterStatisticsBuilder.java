package com.foxminded.summary.statistics;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;

public class CharacterStatisticsBuilder {
    private HashMap<String, String> cache;

    public CharacterStatisticsBuilder() {
        cache = new HashMap<>();
    }

    public String build(String input) {
        if (input == null) {
            throw new IllegalArgumentException("input is null");
        } else if (input.length() == 0) {
            throw new IllegalArgumentException("input is empty");
        }

        String result = cache.get(input);
        if (result == null) {
            result = buildView(calculateStats(input));
            cache.put(input, result);
        }

        return result;
    }

    private LinkedHashMap<Character, Integer> calculateStats(String input) {
        LinkedHashMap<Character, Integer> statsMap = new LinkedHashMap<>();
        for (char character : input.toCharArray()) {
            statsMap.merge(character, 1, Integer::sum);
        }
        return statsMap;
    }

    private String buildView(LinkedHashMap<Character, Integer> statistics) {
        StringJoiner output = new StringJoiner(System.lineSeparator());
        for (Map.Entry<Character, Integer> entry : statistics.entrySet()) {
            String nextLine = String.format("\"%c\" - %d", entry.getKey(), entry.getValue());
            output.add(nextLine);
        }
        return output.toString();
    }
}
