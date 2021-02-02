package com.foxminded.division.builder;

import static com.foxminded.division.builder.LongDivisionBuilderUtils.*;

public class LongDivisionBuilder {
    public String build(int divident, int divider) {
        divident = Math.abs(divident);
        divider = Math.abs(divider);

        if (divider == 0) {
            throw new IllegalArgumentException("Division by zero");
        }
        int quotient = divident / divider;
        int lengthOfLine = getLengthOfInt(divident) + ONE_CHAR_LENGTH;

        StringBuilder[] outputLines = initOutputLines(divident, divider);

        fillIntermediateValues(outputLines, divident, divider);

        alignOutputLines(outputLines, quotient, lengthOfLine);
        
        buildFinalView(outputLines, divident, divider);
        
        return compileStringFromArray(outputLines);
    }

    public StringBuilder[] initOutputLines(int divident, int divider) {
        int lengthForArray = getLengthWithoutZeroes(divident / divider) * LINES_PER_NUMBER + 1;
        int lengthOfLine = getLengthOfInt(divident) + ONE_CHAR_LENGTH;
        StringBuilder[] output = new StringBuilder[lengthForArray];
        for (int i = 0; i < output.length; i++) {
            output[i] = new StringBuilder(lengthOfLine);
        }
        return output;
    }
    
    public void fillIntermediateValues(StringBuilder[] outputArray, int divident, int divider) {
        fillTempDividents(outputArray, divident, divider);
        fillSubtrahends(outputArray, divident, divider);
        fillDashes(outputArray);
        fillRemainder(outputArray, divident, divider);
    }

    public void fillTempDividents(StringBuilder[] outputArray, int divident, int divider) {
        char[] dividentChars = String.valueOf(divident).toCharArray();
        for (int pointer = 0, remainder = 0, countOfTempDividents = 0; pointer < dividentChars.length; pointer++) {
            int tempDivident = remainder * 10 + Character.getNumericValue(dividentChars[pointer]);
            int quotient = tempDivident / divider;
            if (quotient > 0) {
                outputArray[countOfTempDividents * LINES_PER_NUMBER].append(MINUS_CHARACTER).append(tempDivident);

                remainder = tempDivident % divider;
                countOfTempDividents++;
            } else {
                remainder = tempDivident;
            }
        }
    }

    public void fillSubtrahends(StringBuilder[] outputArray, int divident, int divider) {
        char[] quotientChars = String.valueOf(divident / divider).toCharArray();
        for (int pointer = 0, coutOfSubtrahends = 0; pointer < quotientChars.length; pointer++) {
            char tempChar = quotientChars[pointer];
            if (tempChar != '0') {
                int tempQuotient = Character.getNumericValue(tempChar);
                outputArray[coutOfSubtrahends * LINES_PER_NUMBER + SUBTRAHEND_OFFSET].append(divider * tempQuotient);
                coutOfSubtrahends++;
            }
        }
    }

    public void fillDashes(StringBuilder[] outputArray) {
        int numOfDashes = outputArray.length / LINES_PER_NUMBER;
        for (int i = 0; i < numOfDashes; i++) {
            int lengthOfDash = outputArray[i * LINES_PER_NUMBER + SUBTRAHEND_OFFSET].length();
            char[] dash = generateRepeatingSymbols(DASH_CHARACTER, lengthOfDash);
            outputArray[i * LINES_PER_NUMBER + DASH_OFFSET].append(dash);
        }
    }

    public void fillRemainder(StringBuilder[] outputArray, int divident, int divider) {
        int remainder = divident % divider;
        outputArray[outputArray.length - 1].append(remainder);
    }

    public void alignOutputLines(StringBuilder[] outputArray, int quotient, int lengthOfLine) {
        char[] quotientChars = String.valueOf(quotient).toCharArray();

        if (quotient == 0) {
            alignOffsetAndLength(outputArray[1], lengthOfLine, START_OF_STRING_POINTER);
            alignOffsetAndLength(outputArray[2], lengthOfLine, START_OF_STRING_POINTER);
        }

        for (int i = 0, countOfNumbers = 0; i < quotientChars.length; i++) {
            if (quotientChars[i] != '0') {
                int positionfOfRightSymbol = lengthOfLine - quotientChars.length + i;
                alignOffsetAndLength(outputArray[countOfNumbers * LINES_PER_NUMBER], lengthOfLine, positionfOfRightSymbol);
                alignOffsetAndLength(outputArray[countOfNumbers * LINES_PER_NUMBER + 1], lengthOfLine, positionfOfRightSymbol);
                alignOffsetAndLength(outputArray[countOfNumbers * LINES_PER_NUMBER + 2], lengthOfLine, positionfOfRightSymbol);
                countOfNumbers++;
            }
        }
        alignOffsetAndLength(outputArray[outputArray.length - 1], lengthOfLine, lengthOfLine - 1);
    }

    public void buildFinalView(StringBuilder[] outputArray, int divident, int divider) {
        int quotient = divident / divider;
        buildDivident(outputArray, divident);
        buildDivider(outputArray, divider);
        buildHorizontalLine(outputArray, divider, quotient);
        buildQuotient(outputArray, quotient);
    }

    public void buildDivident(StringBuilder[] outputArray, int divident) {
        StringBuilder firstLine = new StringBuilder();
        firstLine.append(MINUS_CHARACTER).append(divident);
        outputArray[0] = firstLine;
    }
    
    public void buildDivider(StringBuilder[] outputArray, int divider) {
        outputArray[0].append(VERTICAL_LINE_CHAR).append(divider);
    }
    

    public void buildHorizontalLine(StringBuilder[] outputArray, int divider, int quotient) {
        int length = Math.max(getLengthOfInt(divider), getLengthOfInt(quotient));
        outputArray[1].append(VERTICAL_LINE_CHAR).append(generateRepeatingSymbols(DASH_CHARACTER, length));
    }

    public void buildQuotient(StringBuilder[] outputArray, int quotient) {
        outputArray[2].append(VERTICAL_LINE_CHAR).append(quotient);
    }
}

