package com.ryzhyj.inteview.components.matchers;

import com.ryzhyj.inteview.config.CombinationMatch;
import com.ryzhyj.inteview.config.WinCombination;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LinearMatcher extends CombMatcher {

    private final List<CombinationDescriptor> combinations;

    public LinearMatcher(Map<String, WinCombination> winCombinations) {
        combinations = winCombinations.entrySet().stream()
                .filter(e -> e.getValue().when() == CombinationMatch.LINEAR_SYMBOLS)
                .map(e -> new CombinationDescriptor(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<MatchingResult> match(String[][] board) {
        return combinations.stream()
                .flatMap(cd -> cd.winCombination().coveredAreas().stream()
                        .filter(coveredArea -> matchCoveredArea(board, coveredArea))
                        .map(coveredArea -> new MatchingResult(
                                board[coveredArea.get(0).row()][coveredArea.get(0).column()],
                                cd.name(),
                                cd.winCombination().group(),
                                cd.winCombination().rewardMultiplier()
                        )))
                .collect(Collectors.toList());
    }

    private boolean matchCoveredArea(String[][] board, List<WinCombination.Coordinate> coveredArea) {
        Iterator<WinCombination.Coordinate> iterator = coveredArea.iterator();
        WinCombination.Coordinate first = iterator.next();
        String symbol = board[first.row()][first.column()];
        while (iterator.hasNext()) {
            WinCombination.Coordinate next = iterator.next();
            if (!symbol.equals(board[next.row()][next.column()])) {
                return false;
            }
        }
        return true;
    }
}
