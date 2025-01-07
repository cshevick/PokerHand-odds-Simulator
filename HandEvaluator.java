import java.util.*;

public class HandEvaluator {

    ArrayList<Card> cardsInPlay;
    private int[] handValue; // Array: [hand type, high card rank]

    public HandEvaluator(Card userCard1, Card userCard2, ArrayList<Card> dealtCards) {
        cardsInPlay = new ArrayList<>();
        cardsInPlay.add(userCard1);
        cardsInPlay.add(userCard2);
        cardsInPlay.addAll(dealtCards);

        // Rank the hand and assign the hand value
        this.handValue = rankHand(cardsInPlay);
    }

    public int[] getHandValue() {
        return handValue;
    }

    public void printValue(){
        for (int val: handValue){
            System.out.print(val + " ");
        }
    }

    // Method to rank the hand
    private int[] rankHand(ArrayList<Card> cards) {
        // Sort cards by rank
        cards.sort(Comparator.comparingInt(this::getCardRank));
        
        // Check hand rankings in order of precedence
        if (isRoyalFlush(cards)) return new int[]{10, 14}; // Royal Flush, high card Ace
        if (isStraightFlush(cards)) return new int[]{9, getHighCard(cards)};
        if (isFourOfAKind(cards))  return new int[]{8, getRankWithCount(cards, 4)};
        if (isFullHouse(cards))    return new int[]{7, getRankWithCount(cards, 3)};
        if (isFlush(cards))        return new int[]{6, getHighCard(cards)};
        if (isStraight(cards))     return new int[]{5, getHighCard(cards)};
        if (isThreeOfAKind(cards)) return new int[]{4, getRankWithCount(cards, 3)};
        if (isTwoPair(cards))      return new int[]{3, getHighestPair(cards)};
        if (isOnePair(cards))      return new int[]{2, getRankWithCount(cards, 2)};
    
        return new int[]{1, getHighCard(cards)}; // High Card
    }
    
    // Get the highest pair rank
    private int getHighestPair(ArrayList<Card> cards) {
        ArrayList<Integer> pairs = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : countRanks(cards).entrySet()) {
            if (entry.getValue() >= 2) {
                pairs.add(getCardRank(new Card(entry.getKey(), "")));
            }
        }
        pairs.sort(Collections.reverseOrder()); // Sort in descending order
        return pairs.isEmpty() ? 0 : pairs.get(0); // Return the highest pair
    }
    
    // Helper method to get the rank of a card as an integer
    private int getCardRank(Card card) {
        switch (card.getRank()) {
            case "2":    return 2;
            case "3":    return 3;
            case "4":    return 4;
            case "5":    return 5;
            case "6":    return 6;
            case "7":    return 7;
            case "8":    return 8;
            case "9":    return 9;
            case "10":   return 10;
            case "Jack": return 11;
            case "Queen":return 12;
            case "King": return 13;
            case "Ace":  return 14;
            default:     return 0;
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
        // Extract unique ranks and sort them
        TreeSet<Integer> uniqueRanks = new TreeSet<>();
        for (Card card : cards) {
            uniqueRanks.add(getCardRank(card));
        }

        ArrayList<Integer> ranks = new ArrayList<>(uniqueRanks);
        
        // Check for consecutive sequences of at least 5 cards
        for (int i = 0; i <= ranks.size() - 5; i++) {
            boolean isStraight = true;
            for (int j = 0; j < 4; j++) {
                if (ranks.get(i + j) + 1 != ranks.get(i + j + 1)) {
                    isStraight = false;
                    break;
                }
            }
            if (isStraight) return true;
        }

        // Special Case: Low Ace Straight (A, 2, 3, 4, 5)
        if (ranks.contains(14) && ranks.contains(2) && 
            ranks.contains(3) && ranks.contains(4) && ranks.contains(5)) {
            return true;
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

    // Get the highest card in the hand
    private int getHighCard(ArrayList<Card> cards) {
        return cards.stream().mapToInt(this::getCardRank).max().orElse(0);
    }

    // Get the rank that appears a specific number of times
    private int getRankWithCount(ArrayList<Card> cards, int count) {
        return countRanks(cards).entrySet().stream()
                .filter(entry -> entry.getValue() == count)
                .map(entry -> getCardRank(new Card(entry.getKey(), "")))
                .max(Integer::compareTo)
                .orElse(0);
    }

    // Get the second highest pair for two pair logic (not used above, but here if needed)
    private int getSecondHighPair(ArrayList<Card> cards) {
        ArrayList<Integer> pairs = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : countRanks(cards).entrySet()) {
            if (entry.getValue() == 2) {
                pairs.add(getCardRank(new Card(entry.getKey(), "")));
            }
        }
        pairs.sort(Collections.reverseOrder());
        return pairs.size() > 1 ? pairs.get(1) : 0;
    }
}
