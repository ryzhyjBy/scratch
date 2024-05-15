package com.ryzhyj.inteview.components.matchers;

import com.ryzhyj.inteview.config.WinCombination;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CombMatcher {

    private final Map<String, WinCombination> winCombinations;

    protected CombMatcher() {
        winCombinations = Map.of();
    }

    public CombMatcher(Map<String, WinCombination> winCombinations) {
        this.winCombinations = winCombinations;
    }

    public List<MatchingResult> match(String[][] board) {
        List<MatchingResult> result = new ArrayList<>();

        result.addAll(new SameSymbolMatcher(winCombinations).match(board));
        result.addAll(new LinearMatcher(winCombinations).match(board));

        return result;
    }

    protected record CombinationDescriptor(
            String name,
            WinCombination winCombination
    ){}
}
