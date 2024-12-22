import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class HandEvaluator {

    ArrayList<Card> cardsInPlay;
    public int handValue;

    public HandEvaluator(Card userCard1, Card userCard2, ArrayList<Card> dealtCards) {
        cardsInPlay = new ArrayList<>();
        cardsInPlay.add(userCard1);
        cardsInPlay.add(userCard2);

        cardsInPlay.addAll(dealtCards);

        // Rank the hand and assign the hand value
        this.handValue = rankHand(cardsInPlay);
    }

    public int getHandValue() {
        return handValue;
    }

    // Method to rank the hand
    private int rankHand(ArrayList<Card> cards) {
        // Sort cards by rank
        cards.sort(Comparator.comparingInt(this::getCardRank));

        // Check hand rankings in order of precedence
        if (isRoyalFlush(cards)) return 10;
        if (isStraightFlush(cards)) return 9;
        if (isFourOfAKind(cards)) return 8;
        if (isFullHouse(cards)) return 7;
        if (isFlush(cards)) return 6;
        if (isStraight(cards)) return 5;
        if (isThreeOfAKind(cards)) return 4;
        if (isTwoPair(cards)) return 3;
        if (isOnePair(cards)) return 2;

        return 1; // High Card
    }

    private int getCardRank(Card card) {
        switch (card.getRank()) {
            case "2": return 2;
            case "3": return 3;
            case "4": return 4;
            case "5": return 5;
            case "6": return 6;
            case "7": return 7;
            case "8": return 8;
            case "9": return 9;
            case "10": return 10;
            case "J": return 11;
            case "Q": return 12;
            case "K": return 13;
            case "A": return 14;
            default: return 0;
        }
    }

    private boolean isRoyalFlush(ArrayList<Card> cards) {
        return isStraightFlush(cards) && getCardRank(cards.get(0)) == 10;
    }

    private boolean isStraightFlush(ArrayList<Card> cards) {
        return isFlush(cards) && isStraight(cards);
    }

    private boolean isFourOfAKind(ArrayList<Card> cards) {
        return countRanks(cards).containsValue(4);
    }

    private boolean isFullHouse(ArrayList<Card> cards) {
        HashMap<String, Integer> counts = countRanks(cards);
        return counts.containsValue(3) && counts.containsValue(2);
    }

    private boolean isFlush(ArrayList<Card> cards) {
        return countSuits(cards).containsValue(5);
    }

    private boolean isStraight(ArrayList<Card> cards) {
        ArrayList<Integer> ranks = new ArrayList<>();
        for (Card card : cards) {
            ranks.add(getCardRank(card));
        }
        Collections.sort(ranks);

        for (int i = 0; i <= ranks.size() - 5; i++) {
            boolean straight = true;
            for (int j = 0; j < 4; j++) {
                if (ranks.get(i + j) + 1 != ranks.get(i + j + 1)) {
                    straight = false;
                    break;
                }
            }
            if (straight) return true;
        }
        return false;
    }

    private boolean isThreeOfAKind(ArrayList<Card> cards) {
        return countRanks(cards).containsValue(3);
    }

    private boolean isTwoPair(ArrayList<Card> cards) {
        int pairCount = 0;
        for (int value : countRanks(cards).values()) {
            if (value == 2) pairCount++;
        }
        return pairCount == 2;
    }

    private boolean isOnePair(ArrayList<Card> cards) {
        return countRanks(cards).containsValue(2);
    }

    private HashMap<String, Integer> countRanks(ArrayList<Card> cards) {
        HashMap<String, Integer> counts = new HashMap<>();
        for (Card card : cards) {
            counts.put(card.getRank(), counts.getOrDefault(card.getRank(), 0) + 1);
        }
        return counts;
    }

    private HashMap<String, Integer> countSuits(ArrayList<Card> cards) {
        HashMap<String, Integer> counts = new HashMap<>();
        for (Card card : cards) {
            counts.put(card.getSuit(), counts.getOrDefault(card.getSuit(), 0) + 1);
        }
        return counts;
    }
}
