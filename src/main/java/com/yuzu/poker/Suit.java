package com.yuzu.poker;

public enum Suit {
    HEART("♥"),
    DIAMOND("♦"),
    CLUB("♣"),
    SPADE("♠");

    private final String symbol;

    Suit(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

}
