package com.yuzu.poker;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PokerApplicationTests {

  private static final Logger logger = LoggerFactory.getLogger(PokerApplication.class);

	@Test
	public void contextLoads() {
    List<Card> deck = new ArrayList<>();
    for (Rank rank: Rank.values())
      for (Suit suit: Suit.values())
        deck.add(new Card(suit, rank));
    List<List<Card>> lists = listAllCombinations(deck, 5);
    logger.info("Total combinations: {}", lists.size());
  }

  private List<List<Card>> listAllCombinations(List<Card> deck, int i) {
    int size = deck.size();
    int begin = 1;
    List<List<List<Card>>> lists = new ArrayList<>();
    for (int j = 0; j < i + 1; j++)
      lists.add(new ArrayList<>());
    lists.get(0).add(new ArrayList<>());
    /* when we list n choose k, need n - 1 choose k and n - 1 choose k - 1
    *  which in turns need n - 2 choose k, n - 2 choose k - 1 and n - 2 choose k - 2,
    *  this continues
    *  hence we need to keep all n choose 0, n choose 1, ... n choose i
    * */
    while (begin < size) {
      List<List<List<Card>>> templists = new ArrayList<>();
      for (int j = 0; j < i + 1; j++)
        templists.add(new ArrayList<>());
      templists.get(0).add(new ArrayList<>());
      /* n choose 0 is always an empty list*/
      for (int j = 1; j < i + 1; j++) {
        Card currentCard = deck.get(begin - 1);
        if (j > begin) break;
        List<List<Card>> list1 = lists.get(j - 1);
        List<List<Card>> list2 = lists.get(j);
        List<List<Card>> newList = new ArrayList<>(list2);
        for (List<Card> list: list1) {
          List<Card> temp = new ArrayList<>(list);
          temp.add(currentCard);
          newList.add(temp);
        }
        templists.set(j, newList);
      }
      lists = templists;
      begin++;
    }
    List<List<Card>> result = new ArrayList<>();
    for (List<Card> list: lists.get(i)) {
      List<Card> temp = new ArrayList<>(list);
      result.add(temp);
    }
    for (List<Card> list: lists.get(i - 1)) {
      List<Card> temp = new ArrayList<>(list);
      temp.add(deck.get(size - 1));
      result.add(temp);
    }
    return result;
  }

}
