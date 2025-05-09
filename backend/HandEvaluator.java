package backend;
import java.util.*;          // collections, Map, etc.

/**
 * Evaluates the best 5-card poker hand that can be made from
 * 2 hole-cards + up to 5 community cards.
 *
 * handValue[0]  – category (10 = Royal Flush, … 1 = High Card)
 * handValue[1]  – tie-breaker (highest rank that defines the hand)
 */
public class HandEvaluator {

    private final int[] handValue;      // immutable after construction

    /* ----------  constructor & public helpers  ---------- */

    public HandEvaluator(Card hole1, Card hole2, ArrayList<Card> communityCards) {
        ArrayList<Card> all = new ArrayList<>(7);
        all.add(hole1);
        all.add(hole2);
        all.addAll(communityCards);
        this.handValue = rankHand(all);
    }

    /** returns {category, kicker} – use for comparisons */
    public int[] getHandValue()            { return handValue; }

    public void printValue() {
        System.out.println(handValue[0] + " " + handValue[1]);
    }


    /* ----------  main ranking routine  ---------- */

    private int[] rankHand(ArrayList<Card> cards) {

        /* Straight-flush & Royal-flush (needs suit-consistent straight) */
        int sfHigh = getStraightFlushHigh(cards);
        if (sfHigh == 14) return new int[] {10, 14};   // Royal Flush
        if (sfHigh > 0)   return new int[] { 9, sfHigh};

        /* 4-kind → Full House → Flush */
        if (isFourOfAKind(cards))
            return new int[] { 8, getRankWithCount(cards, 4) };

        if (isFullHouse(cards))
            return new int[] { 7, getRankWithCount(cards, 3) };

        int flushHigh = getFlushHigh(cards);
        if (flushHigh > 0)
            return new int[] { 6, flushHigh };

        /* Straight */
        int straightHigh = getStraightHigh(cards);
        if (straightHigh > 0)
            return new int[] { 5, straightHigh };

        /* Trips / Two-pair / One-pair / High card */
        if (isThreeOfAKind(cards))
            return new int[] { 4, getRankWithCount(cards, 3) };

        if (isTwoPair(cards))
            return new int[] { 3, getHighestPair(cards) };

        if (isOnePair(cards))
            return new int[] { 2, getRankWithCount(cards, 2) };

        return new int[] { 1, getHighCard(cards) };    // High card
    }


    /* ----------  category tests & helpers  ---------- */

    private boolean isFourOfAKind(ArrayList<Card> cards) {
        return countRanks(cards).containsValue(4);
    }

    private boolean isFullHouse(ArrayList<Card> cards) {
        Map<String,Integer> counts = countRanks(cards);
        return counts.containsValue(3) && counts.containsValue(2);
    }

    private boolean isThreeOfAKind(ArrayList<Card> cards) {
        return countRanks(cards).containsValue(3);
    }

    private boolean isTwoPair(ArrayList<Card> cards) {
        int pairs = 0;
        for (int v : countRanks(cards).values())
            if (v == 2) pairs++;
        return pairs == 2;
    }

    private boolean isOnePair(ArrayList<Card> cards) {
        return countRanks(cards).containsValue(2);
    }


    /* ----------  straight / flush helpers  ---------- */

    /** highest card in any straight-flush (14 = royal, 0 = none) */
    private int getStraightFlushHigh(ArrayList<Card> cards) {

        /* group ranks present in each suit */
        Map<String,TreeSet<Integer>> suitRanks = new HashMap<>();
        for (Card c : cards) {
            suitRanks.computeIfAbsent(c.getSuit(), k -> new TreeSet<>())
                     .add(getCardRank(c));
        }

        for (TreeSet<Integer> ranks : suitRanks.values()) {
            if (ranks.size() < 5) continue;

            /* wheel A-2-3-4-5 */
            if (ranks.containsAll(Arrays.asList(14,2,3,4,5)))
                return 5;

            ArrayList<Integer> list = new ArrayList<>(ranks);
            for (int i = 0; i <= list.size() - 5; i++) {
                boolean straight = true;
                for (int j = 0; j < 4; j++) {
                    if (list.get(i+j)+1 != list.get(i+j+1)) {
                        straight = false; break;
                    }
                }
                if (straight) return list.get(i+4);   // high card of the run
            }
        }
        return 0;
    }

    /** highest card in ANY flush (0 = no flush) */
    private int getFlushHigh(ArrayList<Card> cards) {
        Map<String,Integer> counts = countSuits(cards);
        String flushSuit = null;
        for (Map.Entry<String,Integer> e : counts.entrySet())
            if (e.getValue() >= 5) { flushSuit = e.getKey(); break; }
        if (flushSuit == null) return 0;

        int max = 0;
        for (Card c : cards)
            if (c.getSuit().equals(flushSuit))
                max = Math.max(max, getCardRank(c));
        return max;
    }

    /** highest card in ANY straight (0 = no straight) */
    private int getStraightHigh(ArrayList<Card> cards) {
        TreeSet<Integer> uniq = new TreeSet<>();
        for (Card c : cards) uniq.add(getCardRank(c));

        /* wheel */
        if (uniq.containsAll(Arrays.asList(14,2,3,4,5))) return 5;

        ArrayList<Integer> list = new ArrayList<>(uniq);
        for (int i = 0; i <= list.size() - 5; i++) {
            boolean straight = true;
            for (int j = 0; j < 4; j++)
                if (list.get(i+j)+1 != list.get(i+j+1)) { straight = false; break; }
            if (straight) return list.get(i+4);
        }
        return 0;
    }


    /* ----------  rank / suit counters & misc  ---------- */

    private Map<String,Integer> countRanks(ArrayList<Card> cards) {
        Map<String,Integer> map = new HashMap<>();
        for (Card c : cards)
            map.put(c.getRank(), map.getOrDefault(c.getRank(),0) + 1);
        return map;
    }

    private Map<String,Integer> countSuits(ArrayList<Card> cards) {
        Map<String,Integer> map = new HashMap<>();
        for (Card c : cards)
            map.put(c.getSuit(), map.getOrDefault(c.getSuit(),0) + 1);
        return map;
    }

    /** numeric value of a rank (Ace = 14) */
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

    /** highest pair’s rank (used for two-pair comparison) */
    private int getHighestPair(ArrayList<Card> cards) {
        int best = 0;
        for (Map.Entry<String,Integer> e : countRanks(cards).entrySet())
            if (e.getValue() >= 2)
                best = Math.max(best, getCardRank(new Card(e.getKey(),"")));
        return best;
    }

    /** rank appearing ‘count’ times, highest if several */
    private int getRankWithCount(ArrayList<Card> cards, int count) {
        int best = 0;
        for (Map.Entry<String,Integer> e : countRanks(cards).entrySet())
            if (e.getValue() == count)
                best = Math.max(best, getCardRank(new Card(e.getKey(),"")));
        return best;
    }

    private int getHighCard(ArrayList<Card> cards) {
        int max = 0;
        for (Card c : cards) max = Math.max(max, getCardRank(c));
        return max;
    }
}
