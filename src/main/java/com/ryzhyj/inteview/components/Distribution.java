package com.ryzhyj.inteview.components;

import com.ryzhyj.inteview.config.Cell;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class Distribution {
    private final NavigableMap<Double, String> map = new TreeMap<>();
    private final Random random = new Random();
    private double total = 0;

    public Distribution(Cell cell) {
        cell.symbols().forEach((symbol, weight) -> add(weight, symbol));
    }

    private void add(double weight, String symbol) {
        if (weight > 0) {
            total += weight;
            map.put(total, symbol);
        }
    }

    public String next() {
        double value = random.nextDouble() * total;
//        String tmp = map.higherEntry(value).getValue();
//        Double tmp1 = map.higherEntry(value).getKey();
//        String json = null;
//        try {
//            json = new ObjectMapper().writeValueAsString(map);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        System.out.println(json);
//        System.out.println(value + "=> " + tmp1 + " => " + tmp);
        return map.higherEntry(value).getValue();
    }
}
