package com.yuzu.poker;

import java.util.List;

public class BestHand {

    private final List<Card> cards;

    private final HandValue handValue;

    public BestHand(List<Card> cards, HandValue handValue) {
        this.cards = cards;
        this.handValue = handValue;
    }

    public List<Card> getCards() {
        return cards;
    }

    public HandValue getHandValue() {
        return handValue;
    }
}
