package com.foxminded.anagram;

import java.util.StringJoiner;
import org.apache.commons.lang3.StringUtils;

public class AnagramGenerator {
    private static final String DELIMETER = " ";
    private static final String TRAILING_SEQ = DELIMETER + "\n";
    
    public String generate(String input) {
        if (StringUtils.isEmpty(input)) {
            return "";
        } else if (StringUtils.isBlank(input)) {
            return input;
        }
        
        input += TRAILING_SEQ;
        String[] wordsArray = input.split(DELIMETER);

        StringJoiner output = new StringJoiner(DELIMETER);
        for (int i = 0; i < wordsArray.length - 1; i++) {
            output.add(doAnagramFromWord(wordsArray[i]));
        }

        return output.toString();
    }

    private String doAnagramFromWord(String inputWord) {
        if (inputWord.length() < 2) {
            return inputWord;
        }

        char[] inputCharacters = inputWord.toCharArray();

        for (int leftPointer = 0, rightPointer = inputCharacters.length - 1; leftPointer < rightPointer;) {
            if (Character.isLetter(inputCharacters[leftPointer]) && Character.isLetter(inputCharacters[rightPointer])) {
                char tmpChar = inputCharacters[leftPointer];
                inputCharacters[leftPointer] = inputCharacters[rightPointer];
                inputCharacters[rightPointer] = tmpChar;
                leftPointer++;
                rightPointer--;
            } else {
                if (!Character.isLetter(inputCharacters[leftPointer])) {
                    leftPointer++;
                }
                if (!Character.isLetter(inputCharacters[rightPointer])) {
                    rightPointer--;
                }
            }
        }

        return String.valueOf(inputCharacters);
    }
}
