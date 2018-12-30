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
        String color = "\u001b[0m";
        switch (suit) {
            case HEART:
                color = "\u001b[31m";
                break;
            case DIAMOND:
                color = "\u001b[34m";
                break;
            case CLUB:
                color = "\u001b[32m";
                break;
            case SPADE:
                color = "\u001b[33m";
                break;
        }
        return color + suit.getSymbol() + rank.getSymbol() + "\u001b[0m";
    }
}
