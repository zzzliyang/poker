package com.yuzu.poker;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.yuzu.poker.Rank.*;

@Service
public class HandValueCalculator {

    public BestHand getBestHand(List<Card> cards) {
        if (cards.size() < 5)
            throw new RuntimeException("Less than 5 cards, cannot form a hand!");
        if (cards.size() == 5)
            return new BestHand(cards, calculateHandValue(cards));
        return getBestHandRec(cards, new ArrayList<>());
    }

    private BestHand getBestHandRec(List<Card> cards, List<Card> usedCards) {
        List<Card> newCards = new ArrayList<>(cards);
        List<Card> newUsedCards = new ArrayList<>(usedCards);
        if (newUsedCards.size() == 5) return new BestHand(newUsedCards, calculateHandValue(newUsedCards));
        newUsedCards.add(newCards.get(0));
        newCards.remove(0);
        BestHand bestHand = getBestHandRec(newCards, newUsedCards);
        if (newUsedCards.size() + cards.size() >= 5) {
            BestHand candidate = getBestHandRec(newCards, newUsedCards);
            if (candidate.getHandValue().compareTo(bestHand.getHandValue()) > 0)
                bestHand = candidate;
        }
        return bestHand;
    }

    private HandValue calculateHandValue(List<Card> hand) {
        if (hand.size() != 5)
            throw new RuntimeException("Need exactly 5 cards to form a hand! Seeing " + hand.size() + " cards...");
        Collections.sort(hand);
        return getHandValue(hand);
    }

    private HandValue getHandValue(List<Card> sortedHand) {
        int value = getRoyalFlushValue(sortedHand);
        if (value > 0) return new HandValue(HandType.ROYAL_FLUSH, value);
        value = getStraightFlushValue(sortedHand);
        if (value > 0) return new HandValue(HandType.STRAIGHT_FLUSH, value);
        value = getFourAKindValue(sortedHand);
        if (value > 0) return new HandValue(HandType.FOUR_OF_A_KIND, value);
        value = getFullHouseValue(sortedHand);
        if (value > 0) return new HandValue(HandType.FULL_HOUSE, value);
        value = getFlushValue(sortedHand);
        if (value > 0) return new HandValue(HandType.FLUSH, value);
        value = getStraightValue(sortedHand);
        if (value > 0) return new HandValue(HandType.STRAIGHT, value);
        value = getThreeAKindValue(sortedHand);
        if (value > 0) return new HandValue(HandType.THREE_A_KIND, value);
        value = getTwoPairValue(sortedHand);
        if (value > 0) return new HandValue(HandType.TWO_PAIR, value);
        value = getOnePairValue(sortedHand);
        if (value > 0) return new HandValue(HandType.ONE_PAIR, value);
        value = sortedHand.get(4).getRank().getRankValue() * 13 ^ 4
                + sortedHand.get(3).getRank().getRankValue() * 13 ^ 3
                + sortedHand.get(2).getRank().getRankValue() * 13 ^ 2
                + sortedHand.get(1).getRank().getRankValue() * 13 ^ 1
                + sortedHand.get(0).getRank().getRankValue();
        return new HandValue(HandType.HIGH_CARD, value);
    }

    private int getOnePairValue(List<Card> sortedHand) {
        if (sortedHand.get(0).getRank().equals(sortedHand.get(1).getRank()))
            return sortedHand.get(0).getRank().getRankValue() * 13 ^ 3
                    + sortedHand.get(4).getRank().getRankValue() * 13 ^ 2
                    + sortedHand.get(3).getRank().getRankValue() * 13 ^ 1
                    + sortedHand.get(2).getRank().getRankValue();
        if (sortedHand.get(1).getRank().equals(sortedHand.get(2).getRank()))
            return sortedHand.get(1).getRank().getRankValue() * 13 ^ 3
                    + sortedHand.get(4).getRank().getRankValue() * 13 ^ 2
                    + sortedHand.get(3).getRank().getRankValue() * 13 ^ 1
                    + sortedHand.get(0).getRank().getRankValue();
        if (sortedHand.get(2).getRank().equals(sortedHand.get(3).getRank()))
            return sortedHand.get(2).getRank().getRankValue() * 13 ^ 3
                    + sortedHand.get(4).getRank().getRankValue() * 13 ^ 2
                    + sortedHand.get(1).getRank().getRankValue() * 13 ^ 1
                    + sortedHand.get(0).getRank().getRankValue();
        if (sortedHand.get(3).getRank().equals(sortedHand.get(4).getRank()))
            return sortedHand.get(3).getRank().getRankValue() * 13 ^ 3
                    + sortedHand.get(2).getRank().getRankValue() * 13 ^ 2
                    + sortedHand.get(1).getRank().getRankValue() * 13 ^ 1
                    + sortedHand.get(0).getRank().getRankValue();
        return 0;
    }

    private int getTwoPairValue(List<Card> sortedHand) {
        if ((sortedHand.get(0).getRank().equals(sortedHand.get(1).getRank())
                && sortedHand.get(2).getRank().equals(sortedHand.get(3).getRank())))
            return sortedHand.get(2).getRank().getRankValue() * 13 ^ 2
                    + sortedHand.get(0).getRank().getRankValue() * 13
                    + sortedHand.get(4).getRank().getRankValue();
        if ((sortedHand.get(0).getRank().equals(sortedHand.get(1).getRank())
                && sortedHand.get(3).getRank().equals(sortedHand.get(4).getRank())))
            return sortedHand.get(3).getRank().getRankValue() * 13 ^ 2
                    + sortedHand.get(0).getRank().getRankValue() * 13
                    + sortedHand.get(2).getRank().getRankValue();
        if ((sortedHand.get(1).getRank().equals(sortedHand.get(2).getRank())
                && sortedHand.get(3).getRank().equals(sortedHand.get(4).getRank())))
            return sortedHand.get(3).getRank().getRankValue() * 13 ^ 2
                    + sortedHand.get(1).getRank().getRankValue() * 13
                    + sortedHand.get(0).getRank().getRankValue();
        return 0;
    }

    private int getThreeAKindValue(List<Card> sortedHand) {
        if (sortedHand.get(0).getRank().equals(sortedHand.get(2).getRank()))
            return sortedHand.get(0).getRank().getRankValue() * 13 ^ 2
                    + sortedHand.get(4).getRank().getRankValue() * 13
                    + sortedHand.get(3).getRank().getRankValue();
        if (sortedHand.get(1).getRank().equals(sortedHand.get(3).getRank()))
            return sortedHand.get(1).getRank().getRankValue() * 13 ^ 2
                    + sortedHand.get(4).getRank().getRankValue() * 13
                    + sortedHand.get(0).getRank().getRankValue();
        if (sortedHand.get(2).getRank().equals(sortedHand.get(4).getRank()))
            return sortedHand.get(2).getRank().getRankValue() * 13 ^ 2
                    + sortedHand.get(1).getRank().getRankValue() * 13
                    + sortedHand.get(0).getRank().getRankValue();
        return 0;
    }

    private int getFullHouseValue(List<Card> sortedHand) {
        if ((sortedHand.get(0).getRank().equals(sortedHand.get(2).getRank())
                && sortedHand.get(3).getRank().equals(sortedHand.get(4).getRank())))
            return sortedHand.get(0).getRank().getRankValue() * 13
                    + sortedHand.get(3).getRank().getRankValue();
        if ((sortedHand.get(0).getRank().equals(sortedHand.get(1).getRank())
                && sortedHand.get(2).getRank().equals(sortedHand.get(4).getRank())))
            return sortedHand.get(2).getRank().getRankValue() * 13
                    + sortedHand.get(0).getRank().getRankValue();
        return 0;
    }

    private int getFourAKindValue(List<Card> sortedHand) {
        if (sortedHand.get(0).getRank().equals(sortedHand.get(3).getRank()))
            return sortedHand.get(0).getRank().getRankValue() * 13
                    + sortedHand.get(4).getRank().getRankValue();
        if (sortedHand.get(1).getRank().equals(sortedHand.get(4).getRank()))
            return sortedHand.get(1).getRank().getRankValue() * 13
                    + sortedHand.get(0).getRank().getRankValue();
        return 0;
    }

    private int getRoyalFlushValue(List<Card> sortedHand) {
        // always ties
        return getStraightFlushValue(sortedHand) > 0 && sortedHand.get(4).getRank().equals(Rank.ACE) ? 1 : 0;
    }

    private int getStraightFlushValue(List<Card> sortedHand) {
        // only need to compare straight value
        int straightValue = getStraightValue(sortedHand);
        int flushValue = getFlushValue(sortedHand);
        return straightValue > 0 && flushValue > 0 ? straightValue : 0;
    }

    private int getFlushValue(List<Card> sortedHand) {
        Suit suit = sortedHand.get(0).getSuit();
        boolean isFlush = suit.equals(sortedHand.get(1).getSuit())
                && suit.equals(sortedHand.get(2).getSuit())
                && suit.equals(sortedHand.get(3).getSuit())
                && suit.equals(sortedHand.get(4).getSuit());
        return isFlush ?
                sortedHand.get(0).getRank().getRankValue()
                        + 13 * sortedHand.get(1).getRank().getRankValue()
                        + 13 ^ 2 * sortedHand.get(2).getRank().getRankValue()
                        + 13 ^ 3 * sortedHand.get(3).getRank().getRankValue()
                        + 13 ^ 4 * sortedHand.get(0).getRank().getRankValue()
                : 0;
    }

    private int getStraightValue(List<Card> sortedHand) {
        int card1Rank = sortedHand.get(0).getRank().getRankValue();
        if (card1Rank > 10) return 0;
        int card2Rank = sortedHand.get(0).getRank().getRankValue();
        int card3Rank = sortedHand.get(0).getRank().getRankValue();
        int card4Rank = sortedHand.get(0).getRank().getRankValue();
        int card5Rank = sortedHand.get(0).getRank().getRankValue();
        // Special case for straight, A2345
        if (card1Rank == TWO.getRankValue()
                && card2Rank == THREE.getRankValue()
                && card3Rank == FOUR.getRankValue()
                && card4Rank == FIVE.getRankValue()
                && card5Rank == ACE.getRankValue())
            return FIVE.getRankValue();
        // All other cases
        return card1Rank + 1 == card2Rank
                && card2Rank + 1 == card3Rank
                && card3Rank + 1 == card4Rank
                && card4Rank + 1 == card5Rank ?
                card5Rank
                : 0;
    }
}
