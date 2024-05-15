package com.ryzhyj.inteview.components.matchers;

import com.ryzhyj.inteview.config.CombinationMatch;
import com.ryzhyj.inteview.config.WinCombination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SameSymbolMatcher extends CombMatcher {

    private final Map<Integer, CombinationDescriptor> combinations = new HashMap<>();
    private final Map<String, Integer> symbols = new HashMap<>();

    public SameSymbolMatcher(Map<String, WinCombination> winCombinations) {
        winCombinations.entrySet().stream()
                .filter(e -> e.getValue().when() == CombinationMatch.SAME_SYMBOLS)
                .forEach(e -> combinations.put(e.getValue().count(), new CombinationDescriptor(e.getKey(), e.getValue())));
    }

    public List<MatchingResult> match(String[][] board) {
        for (String[] row : board) {
            for (String symbol : row) {
                symbols.compute(symbol, (k, v) -> v == null ? 1 : v + 1);
            }
        }

        List<MatchingResult> result = new ArrayList<>();
        for (Map.Entry<String, Integer> e : symbols.entrySet()) {
            CombinationDescriptor descriptor = combinations.get(e.getValue());
            if (descriptor != null) {
                MatchingResult matchingResult = new MatchingResult(
                        e.getKey(),
                        descriptor.name(),
                        descriptor.winCombination().group(),
                        descriptor.winCombination().rewardMultiplier()
                );
                result.add(matchingResult);
            }
        }
        return result;
    }
}