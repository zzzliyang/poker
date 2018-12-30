package com.yuzu.poker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class PokerApplication {

	private static final Logger logger = LoggerFactory.getLogger(PokerApplication.class);

	@Autowired
	private HandValueCalculator handValueCalculator;

	public static void main(String[] args) {
		SpringApplication.run(PokerApplication.class, args);
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		System.out.println("Continue playing?");
		while (reader.nextInt() != 0) {
			play();
		}
	}

	private static void play() {
		int numberOfPlayers = 6;
		List<Card> deck = new ArrayList<>();
		List<Player> players = new ArrayList<>();
		List<Card> shared = new ArrayList<>();
		for (Rank rank: Rank.values())
			for (Suit suit: Suit.values())
				deck.add(new Card(suit, rank));
		for (int i = 0; i < numberOfPlayers; i++) {
			players.add(new Player("Player" + i, new ArrayList<>()));
		}
		logger.info("Starting a new game with " + numberOfPlayers + " players...");
		int next;
		// dealing first card
		for (Player player: players) {
			next = ThreadLocalRandom.current().nextInt(0, deck.size());
			player.getCards().add(deck.get(next));
			deck.remove(next);
		}
		// dealing second card
		for (Player player: players) {
			next = ThreadLocalRandom.current().nextInt(0, deck.size());
			player.getCards().add(deck.get(next));
			deck.remove(next);
		}
		for (Player player: players) {
			logger.info(player.getId() + " cards: "
					+ player.getCards().get(0).toString() + " "
					+ player.getCards().get(1).toString());
		}
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
		for (Player player: players) {
			player.getCards().addAll(shared);
			BestHand bestHand = HandValueCalculator.getBestHand(player.getCards());
			player.setBestHand(bestHand);
			List<Card> bestCards = bestHand.getCards();
			logger.info(player.getId() + " best hand: "
					+ bestCards.get(0).toString() + " "
					+ bestCards.get(1).toString() + " "
					+ bestCards.get(2).toString() + " "
					+ bestCards.get(3).toString() + " "
					+ bestCards.get(4).toString());
			logger.info(player.getId() + " hand value: " + bestHand.getHandValue().toString());
		}
		Collections.sort(players, Collections.reverseOrder());
		int tieUntil = 0;
		Player winningPlayer = players.get(0);
		HandValue winningHandValue = winningPlayer.getBestHand().getHandValue();
		for (int i = 1; i < numberOfPlayers; i++) {
			if (winningHandValue.compareTo(players.get(i).getBestHand().getHandValue()) != 0)
				break;
			tieUntil++;
		}
		if (tieUntil == 0) {
			logger.info(winningPlayer.getId() + " wins!");
		} else {
			for (int i = 0; i <= tieUntil; i++) {
				logger.info(players.get(i).getId() + " ");
			}
			logger.info(" split the pot!");
		}
	}
}

