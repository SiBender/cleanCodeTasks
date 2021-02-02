package com.foxminded.division.builder;

import java.util.StringJoiner;

public final class LongDivisionBuilderUtils {
    private LongDivisionBuilderUtils() {}
    
    public static final char SPACE_CHARACTER = ' ';
    public static final char DASH_CHARACTER = '-';
    public static final char MINUS_CHARACTER = '_';
    public static final char VERTICAL_LINE_CHAR = '|';
    public static final int LINES_PER_NUMBER = 3;
    public static final int ONE_CHAR_LENGTH = 1;
    public static final int SUBTRAHEND_OFFSET = 1;
    public static final int DASH_OFFSET = 2;
    public static final int START_OF_STRING_POINTER = 0;
 
    public static void alignOffsetAndLength(StringBuilder inputString, int lengthOfLine, int positionOfRightSymbol) {
        int lengthOfText = inputString.length();
        int lengthOfPrefix = positionOfRightSymbol - lengthOfText + ONE_CHAR_LENGTH;
        int lengthOfSuffix = lengthOfLine - lengthOfText - lengthOfPrefix;

        inputString.insert(START_OF_STRING_POINTER, generateRepeatingSymbols(SPACE_CHARACTER, lengthOfPrefix));
        inputString.append(generateRepeatingSymbols(SPACE_CHARACTER, lengthOfSuffix));
    }
    
    public static char[] generateRepeatingSymbols(char character, int length) {
        char[] output = new char[length]; 
        for (int i = 0; i < length; i++) {
            output[i] = character;
        }
        return output;
    }
    
    public static int getLengthWithoutZeroes(int num) {
        int length = 0;
        while (num > 0) {
            if ((num % 10) != 0) {
                length++;
            }
            num /= 10;
        }
        return (length > 0) ? length : 1;
    }

    public static int getLengthOfInt(int num) {
        int length = 0;
        while (num > 0) {
            length++;
            num /= 10;
        }
        return (length > 0) ? length : 1;
    }

    public static String compileStringFromArray(StringBuilder[] inputLines) {
        StringJoiner resultString = new StringJoiner("\n");
        for (StringBuilder currentString : inputLines) {
            resultString.add(currentString);
        }
        return resultString.toString();
    }
}

