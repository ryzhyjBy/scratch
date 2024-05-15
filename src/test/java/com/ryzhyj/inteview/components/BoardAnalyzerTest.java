package com.ryzhyj.inteview.components;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.ryzhyj.inteview.config.Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.*;

class BoardAnalyzerTest {

    private Config config;

    @BeforeEach
    void setUp() throws IOException {
        ObjectMapper objectMapper = JsonMapper.builder()
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                .build();
        config = objectMapper.readValue(getClass().getClassLoader().getResource("config.json"), Config.class);
    }

    @Test
    void testAnalyzeFail() {
        Analyze analyzer = new Analyze(config);
        Board gameBoard = new Board(
                new String[][]{
                        {"A", "B", "C"},
                        {"E", "B", "5x"},
                        {"F", "D", "C"}
                },
                ZERO,
                null,
                "5x"
        );

        gameBoard = analyzer.analyze(gameBoard, new BigDecimal("100"));

        assertEquals(ZERO, gameBoard.reward());
        assertNull(gameBoard.winningCombinations());
        assertNull(gameBoard.bonusSymbol());
    }

    //In config values "same_symbol_3_times" set to 1, but in sample it is calculated as 2
    @Test
    void testAnalyzeWin_Same_Symbol_3_times() {
        Analyze analyzer = new Analyze(config);
        Board gameBoard = new Board(
                new String[][]{
                        {"A", "B", "C"},
                        {"E", "B", "10x"},
                        {"F", "D", "B"}
                },
                ZERO,
                null,
                "10x"
        );

        gameBoard = analyzer.analyze(gameBoard, new BigDecimal("100"));

        assertEquals(25000, gameBoard.reward().intValue());
        assertEquals(1, gameBoard.winningCombinations().size());
        assertEquals(1, gameBoard.winningCombinations().get("B").size());
        assertEquals("same_symbol_3_times", gameBoard.winningCombinations().get("B").get(0));
        assertEquals("10x", gameBoard.bonusSymbol());
    }

    //In a sample it is also calculated incorrect
    @Test
    void testAnalyzeWin_same_symbol_3_5_times_and_symbols_vertically() {
        Analyze analyzer = new Analyze(config);
        Board gameBoard = new Board(
                new String[][]{
                        {"A", "A", "B"},
                        {"A", "+1000", "B"},
                        {"A", "A", "B"}
                },
                ZERO,
                null,
                "+1000"
        );

        gameBoard = analyzer.analyze(gameBoard, new BigDecimal("100"));

        assertEquals(26000, gameBoard.reward().intValue());
        assertEquals(2, gameBoard.winningCombinations().size());
        assertEquals(2, gameBoard.winningCombinations().get("B").size());
        assertEquals("same_symbol_3_times", gameBoard.winningCombinations().get("B").get(0));
        assertEquals("same_symbols_vertically", gameBoard.winningCombinations().get("B").get(1));
        assertEquals(2, gameBoard.winningCombinations().get("A").size());
        assertEquals("same_symbol_5_times", gameBoard.winningCombinations().get("A").get(0));
        assertEquals("same_symbols_vertically", gameBoard.winningCombinations().get("A").get(1));
        assertEquals("+1000", gameBoard.bonusSymbol());
    }

    @Test
    void testAnalyzeWin3Combinations() {
        Analyze analyzer = new Analyze(config);
        Board gameBoard = new Board(
                new String[][]{
                        {"B", "B", "C"},
                        {"B", "B", "+1000"},
                        {"B", "D", "B"}
                },
                ZERO,
                null,
                "+1000"
        );

        gameBoard = analyzer.analyze(gameBoard, new BigDecimal("100"));

        //B = 25
        //+1000 = 1000
        //same_symbol_6_times = 3
        //same_symbols_vertically = 2
        //same_symbols_diagonally_left_to_right = 5
        assertEquals(76000, gameBoard.reward().intValue());
        assertEquals(1, gameBoard.winningCombinations().size());
        assertEquals(3, gameBoard.winningCombinations().get("B").size());
        assertTrue(gameBoard.winningCombinations().get("B").contains("same_symbol_6_times"));
        assertTrue(gameBoard.winningCombinations().get("B").contains("same_symbols_vertically"));
        assertTrue(gameBoard.winningCombinations().get("B").contains("same_symbols_diagonally_left_to_right"));
        assertEquals("+1000", gameBoard.bonusSymbol());
    }

    @Test
    void testAnalyzeWin2Symbols() {
        Analyze analyzer = new Analyze(config);
        Board gameBoard = new Board(
                new String[][]{
                        {"B", "B", "B"},
                        {"F", "F", "F"},
                        {"C", "F", "C"}
                },
                ZERO,
                null,
                null
        );

        gameBoard = analyzer.analyze(gameBoard, new BigDecimal("100"));

        System.out.println(gameBoard);

        //B = 25
        //F = 1.5
        //same_symbol_3_times = 1
        //same_symbol_4_times = 1.5
        //same_symbols_horizontally = 2
        assertEquals(5450, gameBoard.reward().intValue());
        assertEquals(2, gameBoard.winningCombinations().size());
        assertEquals(2, gameBoard.winningCombinations().get("B").size());
        assertEquals(2, gameBoard.winningCombinations().get("F").size());
        assertTrue(gameBoard.winningCombinations().get("B").contains("same_symbol_3_times"));
        assertTrue(gameBoard.winningCombinations().get("B").contains("same_symbols_horizontally"));
        assertTrue(gameBoard.winningCombinations().get("F").contains("same_symbols_horizontally"));
        assertTrue(gameBoard.winningCombinations().get("F").contains("same_symbol_4_times"));
        assertNull(gameBoard.bonusSymbol());    }


}