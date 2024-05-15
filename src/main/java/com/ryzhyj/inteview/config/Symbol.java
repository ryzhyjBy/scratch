package com.ryzhyj.inteview.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record Symbol(
        @JsonProperty("reward_multiplier")
        BigDecimal rewardMultiplier,
        BigDecimal extra,
        SymbolType type,
        Impact impact
) {
}
