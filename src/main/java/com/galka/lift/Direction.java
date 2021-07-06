package com.galka.lift;

enum Direction {
    UP("▲"), DOWN("▼");
    private final String symbol;

    Direction(String symbol) {
        this.symbol = symbol;
    }

    public String symbol() {
        return symbol;
    }
}
