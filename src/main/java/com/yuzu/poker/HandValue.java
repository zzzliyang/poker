package com.yuzu.poker;

public class HandValue implements Comparable<HandValue> {

    private final HandType handType;

    private final int cardValue;

    public HandValue(HandType handType, int cardValue) {
        this.handType = handType;
        this.cardValue = cardValue;
    }

    @Override
    public int compareTo(HandValue o) {
        // compare hands ranking first
        int handTypeCompare = Integer.compare(this.handType.getRankValue(), o.handType.getRankValue());
        if (handTypeCompare != 0) return handTypeCompare;
        // if ties, then compare with a same hand type
        return Integer.compare(this.cardValue, o.cardValue);
    }

    @Override
    public String toString() {
        return handType.name() + cardValue;
    }
}
