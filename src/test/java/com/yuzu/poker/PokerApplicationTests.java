package com.yuzu.poker;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PokerApplicationTests {

	private static final Logger logger = LoggerFactory.getLogger(PokerApplication.class);

	@Autowired private HandValueCalculator handValueCalculator;

	@Test
	public void contextLoads() {
		int players = 2;
		List<Card> deck = new ArrayList<>();
		List<Card> player1 = new ArrayList<>();
		List<Card> player2 = new ArrayList<>();
		List<Card> shared = new ArrayList<>();
		for (Rank rank: Rank.values())
			for (Suit suit: Suit.values())
				deck.add(new Card(suit, rank));
		logger.info("Starting a new game with " + players + " players...");
		int next = ThreadLocalRandom.current().nextInt(0, deck.size());
		player1.add(deck.get(next));
		deck.remove(next);
		next = ThreadLocalRandom.current().nextInt(0, deck.size());
		player2.add(deck.get(next));
		deck.remove(next);
		next = ThreadLocalRandom.current().nextInt(0, deck.size());
		player1.add(deck.get(next));
		deck.remove(next);
		next = ThreadLocalRandom.current().nextInt(0, deck.size());
		player2.add(deck.get(next));
		deck.remove(next);
		logger.info("Player 1 cards: " + player1.get(0).toString() + " " + player1.get(1).toString());
		logger.info("Player 2 cards: " + player2.get(0).toString() + " " + player2.get(1).toString());
		next = ThreadLocalRandom.current().nextInt(0, deck.size());
		deck.remove(next);
		for (int i = 0; i < 3; i++) {
			next = ThreadLocalRandom.current().nextInt(0, deck.size());
			shared.add(deck.get(next));
			deck.remove(next);
		}
		logger.info("Flop cards: " + shared.get(0).toString() + " " + shared.get(1).toString() + " " + shared.get(2).toString());
		next = ThreadLocalRandom.current().nextInt(0, deck.size());
		shared.add(deck.get(next));
		deck.remove(next);
		logger.info("Turn card: " + shared.get(3).toString());
		next = ThreadLocalRandom.current().nextInt(0, deck.size());
		shared.add(deck.get(next));
		deck.remove(next);
		logger.info("River card: " + shared.get(4).toString());
		player1.addAll(shared);
		BestHand player1BestHand = handValueCalculator.getBestHand(player1);
		List<Card> player1BestCards = player1BestHand.getCards();
		logger.info("Player 1 best hand: "
				+ player1BestCards.get(0).toString() + " "
				+ player1BestCards.get(1).toString() + " "
				+ player1BestCards.get(2).toString() + " "
				+ player1BestCards.get(3).toString() + " "
				+ player1BestCards.get(4).toString());
		logger.info("Plyer 1 hand value: " + player1BestHand.getHandValue().toString());
		player2.addAll(shared);
		BestHand player2BestHand = handValueCalculator.getBestHand(player2);
		List<Card> player2BestCards = player2BestHand.getCards();
		logger.info("Player 2 best hand: "
				+ player2BestCards.get(0).toString() + " "
				+ player2BestCards.get(1).toString() + " "
				+ player2BestCards.get(2).toString() + " "
				+ player2BestCards.get(3).toString() + " "
				+ player2BestCards.get(4).toString());
		logger.info("Plyer 2 hand value: " + player2BestHand.getHandValue().toString());
	}

}
