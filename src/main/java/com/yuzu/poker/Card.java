package com.yuzu.poker;

public class Card implements Comparable<Card> {

    private final Suit suit;

    private final Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    @Override
    public int compareTo(Card o) {
        return Integer.compare(this.rank.getRankValue(), o.getRank().getRankValue());
    }

    @Override
    public String toString() {
        return suit.getSymbol() + rank.getSymbol();
    }
}
