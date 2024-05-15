package com.ryzhyj.inteview.components.matchers;

import com.ryzhyj.inteview.config.Combination;

import java.math.BigDecimal;

public record MatchingResult(
        String symbol,
        String combination,
        Combination group,
        BigDecimal rewardMultiplier
) {
}
