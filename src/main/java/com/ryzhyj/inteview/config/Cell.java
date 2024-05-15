package com.ryzhyj.inteview.config;

import java.util.Map;

public record Cell(int column, int row, Map<String, Integer> symbols) {
}
