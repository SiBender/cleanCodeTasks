package com.foxminded.summary.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CharacterStatisticsBuilderTest {
    String lineBreak = System.lineSeparator();
    CharacterStatisticsBuilder statisticsBuilder = new CharacterStatisticsBuilder();
    
    @BeforeEach
    void init() {
        statisticsBuilder = new CharacterStatisticsBuilder();
    }
    
    @Test
    void buildShouldThrowExceptionWhenInputIsNullTest() {
        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> {
            statisticsBuilder.build(null);
        });
        assertEquals("input is null", thrown.getMessage());
    }

    @Test
    void buildShouldThrowExceptionWhenInputIsEmptyTest() {
        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> {
            statisticsBuilder.build("");
        });
        assertEquals("input is empty", thrown.getMessage());
    }
    
    @ParameterizedTest
    @CsvSource({"a,\"a\" - 1",
                "A,\"A\" - 1",
                "' ',\" \" - 1",
                ".,\".\" - 1",
                "\\,\"\\\" - 1",
                "!,\"!\" - 1"})
    void buildOneCharacterInputTest(String input, String expected) {
        String actual = statisticsBuilder.build(input);
        assertEquals(expected, actual);
    }
    
    @ParameterizedTest
    @CsvSource({"aaa,\"a\" - 3",
                "AAAAAA,\"A\" - 6",
                "'          ',\" \" - 10",
                ".....,\".\" - 5",
                "\\\\\\\\,\"\\\" - 4",
                "!!!!!!!!,\"!\" - 8"})
    void buildInputIsRepeatingCharacterTest(String input, String expected) {
        String actual = statisticsBuilder.build(input);
        assertEquals(expected, actual);
    }
    
    @Test
    void buildInputIsSimpleWordTest() {
        String expected = "\"h\" - 1"+lineBreak
                         +"\"e\" - 1"+lineBreak
                         +"\"l\" - 2"+lineBreak
                         +"\"o\" - 1";
        assertEquals(expected, statisticsBuilder.build("hello"));
        
        expected = "\"a\" - 3"+lineBreak
                  +"\"b\" - 3"+lineBreak
                  +"\"c\" - 3";
        assertEquals(expected, statisticsBuilder.build("abcabcabc"));
        
        expected = "\"m\" - 1"+lineBreak
                  +"\"i\" - 1"+lineBreak
                  +"\"r\" - 3"+lineBreak
                  +"\"o\" - 1";
        assertEquals(expected, statisticsBuilder.build("mirror"));
    }
    
    @Test
    void buildSpecialCharactersInInputTest() {
        String expected = "\"!\" - 2"+lineBreak
                         +"\"@\" - 2"+lineBreak
                         +"\"#\" - 2"+lineBreak
                         +"\"$\" - 2"+lineBreak
                         +"\"%\" - 2"+lineBreak
                         +"\"`\" - 2";
        assertEquals(expected, statisticsBuilder.build("!@#$%``%$#@!"));
        
        expected = "\"(\" - 2"+lineBreak
                  +"\")\" - 2"+lineBreak
                  +"\"[\" - 2"+lineBreak
                  +"\"]\" - 2"+lineBreak
                  +"\"{\" - 2"+lineBreak
                  +"\"}\" - 2"+lineBreak
                  +"\" \" - 1";
        assertEquals(expected, statisticsBuilder.build("()[]{} {}[]()"));
        
        expected = "\"+\" - 2"+lineBreak
                  +"\"-\" - 2"+lineBreak
                  +"\"*\" - 2"+lineBreak
                  +"\"/\" - 2";
        assertEquals(expected, statisticsBuilder.build("+--+**//"));
    }
    
    @Test
    void buildPhraseInputTest() {
        String expected = "\"L\" - 1"+lineBreak
                         +"\"i\" - 5"+lineBreak
                         +"\"n\" - 5"+lineBreak
                         +"\"k\" - 1"+lineBreak
                         +"\"e\" - 4"+lineBreak
                         +"\"d\" - 2"+lineBreak
                         +"\"H\" - 1"+lineBreak
                         +"\"a\" - 4"+lineBreak
                         +"\"s\" - 3"+lineBreak
                         +"\"h\" - 2"+lineBreak
                         +"\"M\" - 1"+lineBreak
                         +"\"p\" - 1"+lineBreak
                         +"\" \" - 4"+lineBreak
                         +"\"m\" - 1"+lineBreak
                         +"\"t\" - 3"+lineBreak
                         +"\"r\" - 3"+lineBreak
                         +"\"o\" - 2";
        assertEquals(expected, statisticsBuilder.build("LinkedHashMap maintains the insertion order"));
    }
   
    @ParameterizedTest
    @CsvSource({"aaa",
                "bbb",
                "abcdef",
                "12345678"})
    void buildCheckCacheWorkTest(String input) {
        String result = statisticsBuilder.build(input);
        assertSame(result, statisticsBuilder.build(input));
    }
}
