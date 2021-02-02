package com.foxminded.division.builder;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.foxminded.division.builder.LongDivisionBuilder;

class LongDivisionBuilderTest {
    LongDivisionBuilder divisionBuilder = new LongDivisionBuilder();

    @Test
    void buildDividerIsZero() {
        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> {
            divisionBuilder.build(123,0);
        });
        assertEquals("Division by zero", thrown.getMessage());
    }
    
    @Test
    void buildDividentIsZero() {
        String expected = "_0|123\n"
                         +"  |---\n"
                         +"  |0\n"
                         +" 0";
        assertEquals(expected, divisionBuilder.build(0,123));
        
        expected = "_0|654321\n"
                  +"  |------\n"
                  +"  |0\n"
                  +" 0";
        assertEquals(expected, divisionBuilder.build(0,654321));
    }
    
    @Test
    void buildDividentLessThanDivider() {
        String expected = "_1|123456\n"
                         +"  |------\n"
                         +"  |0\n"
                         +" 1";
        assertEquals(expected, divisionBuilder.build(1,123456));
        
        expected = "_1234567|987654321\n"
                  +"        |---------\n"
                  +"        |0\n"
                  +" 1234567";
        assertEquals(expected, divisionBuilder.build(1234567,987654321));
    }

    @Test
    void buildOneDigitDivider() {
        String expected = "_1234|1\n"
                         +" 1   |----\n"
                         +" -   |1234\n"
                         +" _2  \n"
                         +"  2  \n"
                         +"  -  \n"
                         +"  _3 \n"
                         +"   3 \n"
                         +"   - \n"
                         +"   _4\n"
                         +"    4\n"
                         +"    -\n"
                         +"    0";
        assertEquals(expected, divisionBuilder.build(1234,1));
    }

    @Test
    void buildFiveDigitDivider() {
        String expected = "_567123567|54321\n"
                         +" 54321    |-----\n"
                         +" -----    |10440\n"
                         +" _239135  \n"
                         +"  217284  \n"
                         +"  ------  \n"
                         +"  _218516 \n"
                         +"   217284 \n"
                         +"   ------ \n"
                         +"     12327";
        assertEquals(expected, divisionBuilder.build(567123567,54321));
    }
    
    @Test
    void buildDivisionWithoutReminderTest() {
        String expected = "_56088|123\n"
                        + " 492  |---\n"
                        + " ---  |456\n"
                        + " _688 \n"
                        + "  615 \n"
                        + "  --- \n"
                        + "  _738\n"
                        + "   738\n"
                        + "   ---\n"
                        + "     0";
        assertEquals(expected, divisionBuilder.build(56088, 123));
    }
    
    @Test 
    void buildSeveralZeroesInRowInQuotientTest() {
        String expected = "_21474836|2147\n"
                        + " 2147    |-----\n"
                        + " ----    |10002\n"
                        + "    _4836\n"
                        + "     4294\n"
                        + "     ----\n"
                        + "      542";
        assertEquals(expected, divisionBuilder.build(21474836, 2147));
    }
    
    @Test
    void buildDividentIsNegativeTest() {
        String expected = "_7777777|12345\n"
                        + " 74070  |-----\n"
                        + " -----  |630\n"
                        + " _37077 \n"
                        + "  37035 \n"
                        + "  ----- \n"
                        + "     427";
        assertEquals(expected, divisionBuilder.build(-7777777, 12345));
    }
    
    @Test
    void buildDividerIsNegativeTest() {
        String expected = "_1234567|55555\n"
                        + " 111110 |-----\n"
                        + " ------ |22\n"
                        + " _123467\n"
                        + "  111110\n"
                        + "  ------\n"
                        + "   12357";
        assertEquals(expected, divisionBuilder.build(1234567, -55555));
    }
    
    @Test
    void buildDividentAndDividerAreNegativeTest() {
        String expected = "_424242|999\n"
                        + " 3996  |---\n"
                        + " ----  |424\n"
                        + " _2464 \n"
                        + "  1998 \n"
                        + "  ---- \n"
                        + "  _4662\n"
                        + "   3996\n"
                        + "   ----\n"
                        + "    666";
        assertEquals(expected, divisionBuilder.build(-424242, -999));
    }
    
    @Test
    void buildNineDigitDivider() {
        String expected = "_2147483647|222222222\n"
                         +" 1999999998|---------\n"
                         +" ----------|9\n"
                         +"  147483649";
        assertEquals(expected, divisionBuilder.build(2147483647,222222222));
    }
    
    @ParameterizedTest
    @CsvSource({"-1234,1234,111",
            "-54321,54321,222",
            "-2147483647,2147483647,333"})
    void buildDividentIsNegativeNumber(int negativeDivident, int positiveDivident, int divider) {
        assertEquals(divisionBuilder.build(positiveDivident, divider), divisionBuilder.build(negativeDivident, divider));
    }
    
    @ParameterizedTest
    @CsvSource({"12345,-111,111",
            "54321,-222,222",
            "7777777,-2147483647,2147483647"})
    void buildDividerIsNegativeNumber(int divident, int negativeDivider, int positiveDivider) {
        assertEquals(divisionBuilder.build(divident, positiveDivider), divisionBuilder.build(divident, negativeDivider));
    }
    
    @Test
    void fillIntermediateValuesTest() {
        StringBuilder[] expected =  new StringBuilder[7];
        expected[0] = new StringBuilder("_454");
        expected[1] = new StringBuilder("450");
        expected[2] = new StringBuilder("---");
        expected[3] = new StringBuilder("_454");
        expected[4] = new StringBuilder("450");
        expected[5] = new StringBuilder("---");
        expected[6] = new StringBuilder("45");
        
        StringBuilder[] actual = new StringBuilder[7];
        for (int i = 0; i < actual.length; i++) {
            actual[i] = new StringBuilder();
        }
        int divident = 454545;
        int divider = 90;
        
        divisionBuilder.fillIntermediateValues(actual, divident, divider);
        
        for (int i = 0; i < actual.length; i++) {
            assertEquals(expected[i].toString(), actual[i].toString());
        }
    }

    @Test 
    void buildFinalViewTest() {
        StringBuilder[] expected = new StringBuilder[3];
        expected[0] = new StringBuilder("_454545|90");
        expected[1] = new StringBuilder(" 450   |----");
        expected[2] = new StringBuilder(" ---   |5050");
        
        StringBuilder[] actual = new StringBuilder[3];
        actual[0] = new StringBuilder();
        actual[1] = new StringBuilder(" 450   ");
        actual[2] = new StringBuilder(" ---   ");
        int divident = 454545;
        int divider = 90;
        
        divisionBuilder.buildFinalView(actual, divident, divider);
        
        for (int i = 0; i < actual.length; i++) {
            assertEquals(expected[i].toString(), actual[i].toString());
        }
    }   
    
    @ParameterizedTest
    @CsvSource({"4,0,12345",
                "22,87654321,2",
                "7,2600026,13"})
    void initOutputLinesTest(int expectedLength, int divident, int divider) {
        StringBuilder[] expected = new StringBuilder[expectedLength];
        for (int i = 0; i < expectedLength; i++) {
            expected[i] = new StringBuilder();
        }
        
        StringBuilder[] actual = divisionBuilder.initOutputLines(divident, divider);
        for (int i = 0; i < expectedLength; i++) {
            assertEquals(expected[i].toString(), actual[i].toString());
        }
    }
    
    @Test
    void alignOutputLinesTest() {
        StringBuilder[] expected =  new StringBuilder[7];
        expected[0] = new StringBuilder("_4   ");
        expected[1] = new StringBuilder(" 4   ");
        expected[2] = new StringBuilder(" -   ");
        expected[3] = new StringBuilder(" _32 ");
        expected[4] = new StringBuilder("  32 ");
        expected[5] = new StringBuilder("  -- ");
        expected[6] = new StringBuilder("    1");
        
        StringBuilder[] actual =  new StringBuilder[7];
        actual[0] = new StringBuilder("_4");
        actual[1] = new StringBuilder("4");
        actual[2] = new StringBuilder("-");
        actual[3] = new StringBuilder("_32");
        actual[4] = new StringBuilder("32");
        actual[5] = new StringBuilder("--");
        actual[6] = new StringBuilder("1");
        divisionBuilder.alignOutputLines(actual, 1080, 5);
        
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i].toString(), actual[i].toString());
        }
    }
}

