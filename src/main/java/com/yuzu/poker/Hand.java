package com.yuzu.poker;

public class Hand {

    private final Card card1;

    private final Card card2;

    private final Card card3;

    private final Card card4;

    private final Card card5;

    public Hand(Card card1, Card card2, Card card3, Card card4, Card card5) {
        this.card1 = card1;
        this.card2 = card2;
        this.card3 = card3;
        this.card4 = card4;
        this.card5 = card5;
    }

    public Card getCard1() {
        return card1;
    }

    public Card getCard2() {
        return card2;
    }

    public Card getCard3() {
        return card3;
    }

    public Card getCard4() {
        return card4;
    }

    public Card getCard5() {
        return card5;
    }
}
