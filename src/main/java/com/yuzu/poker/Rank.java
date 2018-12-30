package com.yuzu.poker;

public enum Rank {
    TWO(0, "2"),
    THREE(1, "3"),
    FOUR(2, "4"),
    FIVE(3, "5"),
    SIX(4, "6"),
    SEVEN(5, "7"),
    EIGHT(6, "8"),
    NINE(7, "9"),
    TEN(8, "10"),
    JACK(9, "J"),
    QUEEN(10, "Q"),
    KING(11, "K"),
    ACE(12, "A");

    private final int rankValue;

    private final String symbol;

    Rank(int rankValue, String symbol) {
      this.rankValue = rankValue;
      this.symbol = symbol;
    }

    public int getRankValue() {
        return rankValue;
    }

    public String getSymbol() {
        return symbol;
    }
}
