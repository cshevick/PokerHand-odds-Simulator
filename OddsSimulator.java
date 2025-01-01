import java.util.Random;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
public class OddsSimulator {
    private int numPlayers;
    private Card userCard1;
    private Card userCard2;
    private ArrayList<Card> boardCards;

    public OddsSimulator(Card userCard1, Card userCard2, Deck deck, int numPlayers, ArrayList<Card> boardCards){
        this.userCard1 = userCard1;
        this.userCard2 = userCard2;
        this.numPlayers = numPlayers;
        this.boardCards = boardCards;
    }

    public double simulate(int numPlayers, ArrayList<Card> boardCards, Deck deck) {
        double winPercentage;
        int wins = 0;
        int losses = 0;
        
    
        for (int i = 0; i < 100000; i++) {
            // Clear the board cards before each hand
            boardCards.clear();
            deck.shuffle();
            System.out.print("Start of hand, deck size: " + deck.getSize());

            deck.removeCardFromDeck(userCard1);
            deck.removeCardFromDeck(userCard2);

    
            // Generate opponent cards
            Card tempCard1 = generateRandomCard(deck);
            Card tempCard2 = generateRandomCard(deck);
           
    
            // Populate the board cards
            this.randomBoard(deck, boardCards, 5);
    
            validateHand(userCard1, userCard2, tempCard1, tempCard2, boardCards);

            // Evaluate hands
            HandEvaluator user = new HandEvaluator(userCard1, userCard2, boardCards);
            HandEvaluator opponent1 = new HandEvaluator(tempCard1, tempCard2, boardCards);
    
            // Debugging output for clarity
            System.out.println("\nUser hand: " + userCard1 + ", " + userCard2);
            System.out.println("Opponent hand: " + tempCard1 + ", " + tempCard2);
            System.out.println("Board Cards: ");
            for (Card card : boardCards) System.out.print(card + ", ");
            System.out.println();
    
            // Compare hands
            int[] userHandValue = user.getHandValue();
            int[] opponentHandValue = opponent1.getHandValue();

            System.out.println("User Hand Value: " + userHandValue[0] + " " + userHandValue[1]);
            System.out.println("Opponent Hand Value: " + opponentHandValue[0] + " " + opponentHandValue[1]);
    
            if (userHandValue[0] > opponentHandValue[0]) {
                wins++;
            } else if (userHandValue[0] == opponentHandValue[0]) {
                if (userHandValue[1] > opponentHandValue[1]) {
                    wins++;
                    System.out.println("User wins");
                } else if (userHandValue[1] == opponentHandValue[1]) {
                    //wins++;
                    //System.out.println("Split pot, win counted.");
                } else {
                    losses++;
                    System.out.println("Opponent wins");
                }
            } else {
                losses++;
                System.out.println("Opponent wins");
            }
    
            System.out.println("End of hand, deck size: " + deck.getSize());
            // Reset the deck for the next hand
            deck.resetDeck();
           
    
         
            System.out.println("\nNEW HAND\n");
        }
    
        winPercentage = (double) wins / (losses + wins);
        return winPercentage;
    }
    

   public void randomBoard(Deck deck, ArrayList<Card> boardCards, int totalCardsNeeded) {
    Set<Card> dealtCards = new HashSet<>(boardCards);

    while (boardCards.size() < totalCardsNeeded) {
        Card newCard = generateRandomCard(deck);

        // Ensure the card is unique
        if (newCard == null || dealtCards.contains(newCard)) {
            System.out.println("Error: Duplicate card detected in board generation: " + newCard);
            continue;
        }

        boardCards.add(newCard);
        dealtCards.add(newCard);
    }
}

    public void validateHand(Card userCard1, Card userCard2, Card opponentCard1, Card opponentCard2, ArrayList<Card> boardCards) {
        Set<Card> allCards = new HashSet<>();
        allCards.add(userCard1);
        allCards.add(userCard2);
        allCards.add(opponentCard1);
        allCards.add(opponentCard2);
        allCards.addAll(boardCards);

        if (allCards.size() != (2 + 2 + boardCards.size())) {
            System.out.println("Error: Duplicate cards detected in the hand!");
        }
    }

    
    

    
    public Card generateRandomCard(Deck deck) {
        if (deck.getSize() == 0) {
            System.out.println("Error: Deck is empty, cannot generate a card.");
            return null;
        }
    
        Random random = new Random();
        int index = random.nextInt(deck.getSize());
        Card randomCard = deck.getCard(index);
    
        // Ensure the card is unique
        if (!deck.getCards().contains(randomCard)) {
            System.out.println("Error: Duplicate card detected during random generation: " + randomCard);
        }
    
        // Remove the card from the deck
        deck.removeCardFromDeck(randomCard);
        return randomCard;
    }
    
    
    
    

    
}
