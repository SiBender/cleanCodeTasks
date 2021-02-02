package com.foxminded.anagram;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


class AnagramGeneratorTest {
    AnagramGenerator anagramGenerator = new AnagramGenerator();
    
    @Test
    void generateShouldReturnEmptyStringIfInputIsNull() {
        assertEquals("", anagramGenerator.generate(null), "Incorrect behavior when input is null");
    }

    @Test
    void generateShouldReturnEmptyStringIfInputIsEmptyString() {
        String expected = "";
        String actual = anagramGenerator.generate("");
        assertEquals(expected, actual);
    }

    @Test
    void generateShouldReturnOneSpaceIfInputIsOneSpace() {
        String expected = " ";
        String actual = anagramGenerator.generate(" ");
        assertEquals(expected, actual);
    }

    @Test
    void generateShouldReturnLineofSpacesIfInputIsLineOfSpaces() {
        String expected = "     ";
        String actual = anagramGenerator.generate("     ");
        assertEquals(expected, actual);
    }

    @Test
    void generateShouldReturnOneLetterlIfInputIsOneLetter() {
        String expected = "a";
        String actual = anagramGenerator.generate("a");
        assertEquals(expected, actual);
    }

    @Test
    void generateShouldReturnLineOfSameLetterslIfInnputIsLineOfSameLettersl() {
        String expected = "aaaaa";
        String actual = anagramGenerator.generate("aaaaa");
        assertEquals(expected, actual);
    }

    @Test
    void generateShouldReturnOneUpperCaseLetterIfInputIsOneUpperCaseLetter() {
        String expected = "Z";
        String actual = anagramGenerator.generate("Z");
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({"gfedcba,abcdefg",
            "GFEDCBA,ABCDEFG",
            "gFeDcBa,aBcDeFg",
            "GfEdCbA,AbCdEfG",
            "ZZZZZaaaaa,aaaaaZZZZZ",
            "FEDcba,abcDEF",
            "fedCBA,ABCdef",
            "gfedcbA,Abcdefg",
            "Gfedcba,abcdefG"})
    void generateShouldReturnCorrectAnagramIfInputIsOneWordContainingOnlyLettersInUpperAndLowerCases(String expected,String input) {
        String actual = anagramGenerator.generate(input);
        assertEquals(expected, actual);
    }

    @Test
    void generateShouldLeaveAllLeadingSpacesInInputString() {
        String expected = "     cba fed";
        String actual = anagramGenerator.generate("     abc def");
        assertEquals(expected, actual);
    }

    @Test
    void generateShouldLeaveAllTrailingSpacesInInputString() {
        String expected = "cba fed     ";
        String actual = anagramGenerator.generate("abc def     ");
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({"cba fed ihg,abc def ghi",
            "CBA FED IHG,ABC DEF GHI",
            "tsuj rehtona drow,just another word",
            "edoC a elttiL,Code a Little"})
    void generateShoulReturnThreeAnagramsAndSavedOrderOfWordsIfThreeWordsInInputString(String expected,String input) {
        String actual = anagramGenerator.generate(input);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({"droWyna;,anyWord;",
            "dro;Wyna,any;Word",
            ";droWyna,;anyWord"})
    void generateShouldLeaveNonLetterSymbolTheSamePlaceAngMoveAllOtherLetters(String expected,String input) {
        String actual = anagramGenerator.generate(input);
        assertEquals(expected, actual);
    }
    
    @ParameterizedTest
    @CsvSource({"e1d2c3b4a 5z6y7x,a1b2c3d4e 5x6y7z",
            "b;;;a 1d2222c3 fed456cba,a;;;b 1c2222d3 abc456def",
            "wad320sgs;dcba tset4dorw ray4na,abc320dsg;sdaw wrod4test any4ar"})
    void generate_WordsWithNonLetterSimbolsInput(String expected,String input) {
        String actual = anagramGenerator.generate(input);
        assertEquals(expected, actual);
    }
}
