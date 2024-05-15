package com.ryzhyj.inteview.components;

import com.ryzhyj.inteview.config.Cell;
import com.ryzhyj.inteview.config.Config;

import java.math.BigDecimal;
import java.util.Random;

import static java.math.BigDecimal.ZERO;

public class Scratch {
    private final Config config;

    public Scratch(Config config) {
        this.config = config;
    }

    public Board scratch(BigDecimal bet) {
        return new Analyze(config).analyze(distribute(), bet);
    }

    private Board distribute() {
        boolean isBonus = config.probabilities().bonusSymbols() != null;
        String[][] board = new String[config.rows()][config.columns()];
        String bonusSymbol = null;
        for (Cell cell : config.probabilities().standardSymbols()) {
            if (isBonus && new Random().nextBoolean()) {
                bonusSymbol = new Distribution(config.probabilities().bonusSymbols()).next();
                board[cell.row()][cell.column()] = bonusSymbol;
                isBonus = false;
            } else {
                board[cell.row()][cell.column()] = new Distribution(cell).next();
            }
        }
        return new Board(board, ZERO, null, bonusSymbol);
    }

}
