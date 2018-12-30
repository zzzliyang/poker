package com.yuzu.poker;

import java.util.List;

public class Player implements Comparable<Player> {

    private final String id;

    private final List<Card> cards;

    private BestHand bestHand;

    public Player(String id, List<Card> cards) {
        this.id = id;
        this.cards = cards;
    }

    public String getId() {
        return id;
    }

    public List<Card> getCards() {
        return cards;
    }

    public BestHand getBestHand() {
        return bestHand;
    }

    public void setBestHand(BestHand bestHand) {
        this.bestHand = bestHand;
    }

    @Override
    public int compareTo(Player o) {
        return this.bestHand.getHandValue().compareTo(o.getBestHand().getHandValue());
    }
}
