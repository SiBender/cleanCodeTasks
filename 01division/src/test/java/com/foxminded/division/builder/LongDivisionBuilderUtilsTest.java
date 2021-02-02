package com.foxminded.division.builder;

import static com.foxminded.division.builder.LongDivisionBuilderUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class LongDivisionBuilderUtilsTest {
    @ParameterizedTest
    @CsvSource({ "1,0", 
                 "3,123", 
                 "4,1230001", 
                 "1,1000000", 
                 "6,123456", 
                 "1,1", 
                 "4,10101010", 
                 "4,1010101" })
    void getLengthWithoutZeroesTest(int expected, int input) {
        int actual = getLengthWithoutZeroes(input);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({ "1,0", 
                 "3,123", 
                 "7,1230001", 
                 "7,1000000", 
                 "6,123456", 
                 "1,1", 
                 "8,10101010", 
                 "2,12", 
                 "7,1010101" })
    void getLengthOfIntTest(int expected, int input) {
        int actual = getLengthOfInt(input);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({ "'   ','',3,2", 
                 "'123','123',3,2", 
                 "' 123','123',4,3", 
                 "' 123 ','123',5,3", 
                 "'  123','123',5,4",
                 "'   123   ','123',9,5", 
                 "'  _123','_123',6,5", 
                 "'     _123   ','_123',12,8"})
    void alignOffsetAndLengthTest(String expectedString, String inputString, int length, int offset) {
        StringBuilder expected = new StringBuilder(expectedString);
        
        StringBuilder actual = new StringBuilder(inputString);
        alignOffsetAndLength(actual, length, offset);
        assertEquals(expected.toString(), actual.toString());
    }

    @ParameterizedTest
    @CsvSource({ "'',' ', 0", 
                 "' ',' ', 1", 
                 "'  ',' ', 2", 
                 "'   ',' ', 3", 
                 "'     ',' ', 5", 
                 "'          ',' ', 10",
                 "'','-', 0", 
                 "'-','-', 1", 
                 "'--','-', 2", 
                 "'---','-', 3", 
                 "'----','-', 4", 
                 "'----------','-', 10" })
    void generateRepeatingSymbolsTest(String expectedString, char character, int length) {
        char[] actual = generateRepeatingSymbols(character, length);
        assertEquals(expectedString, String.valueOf(actual));
    }
}

