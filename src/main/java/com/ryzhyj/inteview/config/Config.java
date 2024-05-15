package com.ryzhyj.inteview.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public record Config(
        int columns,
        int rows,
        Map<String, Symbol> symbols,
        Probabilities probabilities,
        @JsonProperty("win_combinations")
        Map<String, WinCombination> winCombinations
) {

    /**
     * Just to check config
     */
    boolean isValid() {
        return columns > 0 && rows > 0
                && !symbols.isEmpty()
                && probabilities != null
                && !probabilities.standardSymbols.isEmpty()
                && probabilities.standardSymbols.size() == columns * rows
                && !winCombinations.isEmpty();
    }

    public record Probabilities(
            @JsonProperty("standard_symbols")
            List<Cell> standardSymbols,
            @JsonProperty("bonus_symbols")
            Cell bonusSymbols
    ) {
    }
}
