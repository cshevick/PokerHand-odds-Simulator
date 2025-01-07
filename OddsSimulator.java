import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;

class OddsSimulator {
    private Card userCard1;
    private Card userCard2;
    private ArrayList<Card> boardCards;
    // We also keep numPlayers if you want to extend it to multiple opponents
    // (currently the code only picks one random opponent hand each loop).
    
    public OddsSimulator(Card userCard1, Card userCard2, Deck deck, int numPlayers, ArrayList<Card> boardCards) {
        this.userCard1 = userCard1;
        this.userCard2 = userCard2;
        this.boardCards = boardCards;
    }

   
    public double simulate(int numRepetitions, ArrayList<Card> boardCards, Deck deck) {
        int wins = 0, losses = 0;
            for (int i = 0; i < numRepetitions; i++) {
                // 1) Copy user-chosen board so far
                ArrayList<Card> currentBoard = new ArrayList<>(boardCards);
        
                // 2) Reset the deck for each iteration
                deck.resetDeck();
        
                // 3) Remove user’s hole cards & user-chosen board from the deck
                deck.removeCardFromDeck(userCard1);
                deck.removeCardFromDeck(userCard2);
                for (Card c : currentBoard) {
                    deck.removeCardFromDeck(c);
                }
        
                // 4) Fill out the rest of the board
                int cardsNeeded = 5 - currentBoard.size();
                randomBoard(deck, currentBoard, cardsNeeded);
        
                // 5) Generate 2 random opponent cards, remove them from deck
                Card opp1 = generateRandomCard(deck);
                Card opp2 = generateRandomCard(deck);

            // Evaluate each hand
            HandEvaluator user = new HandEvaluator(userCard1, userCard2, currentBoard);
            HandEvaluator opponent = new HandEvaluator(opp1, opp2, currentBoard);

            System.out.println("Hand " + (i + 1) + ":");
            System.out.println("User Hand: " + userCard1 + ", " + userCard2);
            System.out.println("Opponent Hand: " + opp1 + ", " + opp2);
            System.out.print("Board Cards: ");
            for (Card card : currentBoard) {
                System.out.print(card + ", ");
            }
            System.out.println(); // newline

            int[] userHandValue = user.getHandValue();
            int[] opponentHandValue = opponent.getHandValue();

            System.out.println("User Hand Value: " + Arrays.toString(userHandValue));
            System.out.println("Opponent Hand Value: " + Arrays.toString(opponentHandValue));

            // Compare hands
            if (userHandValue[0] > opponentHandValue[0] ||
               (userHandValue[0] == opponentHandValue[0] && userHandValue[1] > opponentHandValue[1])) {
                wins++;
            } else {
                losses++;
            }
        }

        return (double) wins / (wins + losses);
    }

    public void randomBoard(Deck deck, ArrayList<Card> boardCards, int totalCardsNeeded) {
        // Generate exactly 'totalCardsNeeded' new cards
        for (int i = 0; i < totalCardsNeeded; i++) {
            Card newCard = generateRandomCard(deck);
            boardCards.add(newCard);
        }
    }

    public Card generateRandomCard(Deck deck) {
        if (deck.getSize() == 0) {
            throw new IllegalStateException("Error: Deck is empty, cannot generate a card.");
        }

        Random random = new Random();
        int index = random.nextInt(deck.getSize());
        Card randomCard = deck.getCard(index);
        // Remove from deck so it cannot be redrawn
        deck.removeCardFromDeck(randomCard);
        return randomCard;
    }
}
