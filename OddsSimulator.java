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
    private int numPlayers; 

    public OddsSimulator(Card userCard1, Card userCard2,  Deck deck, int numPlayers,  ArrayList<Card> boardCards) {
        this.userCard1 = userCard1;
        this.userCard2 = userCard2;
        this.boardCards = boardCards;
        this.numPlayers = numPlayers;
    }

    public double simulate(int numRepetitions, ArrayList<Card> knownCommunity, Deck masterDeck) {
        int wins = 0, losses = 0, ties = 0;
        
        for (int i = 0; i < numRepetitions; i++) {
            // Build a brand-new deck for THIS iteration
            Deck iterationDeck = new Deck();
            iterationDeck.shuffle();
    
            // Remove user hole cards + known board from iterationDeck
            iterationDeck.removeCardFromDeck(userCard1);
            iterationDeck.removeCardFromDeck(userCard2);
            for (Card c : knownCommunity) {
                iterationDeck.removeCardFromDeck(c);
            }
    
            
            ArrayList<Card> currentBoard = new ArrayList<>(knownCommunity);
    
            // If fewer than 5 known board cards, fill the rest:
            int needed = 5 - currentBoard.size();
            randomBoard(iterationDeck, currentBoard, needed);
    
            // Evaluate user
            HandEvaluator userEval = new HandEvaluator(userCard1, userCard2, currentBoard);
            int[] userVal = userEval.getHandValue();
            
            // Evaluate each opponent
            boolean beaten = false;
            boolean exactTie = false;
            for (int p = 0; p < numPlayers; p++) {
                Card opp1 = generateRandomCard(iterationDeck);
                Card opp2 = generateRandomCard(iterationDeck);
    
                HandEvaluator oppEval = new HandEvaluator(opp1, opp2, currentBoard);
                int[] oppVal = oppEval.getHandValue();
    
                // Compare
                if (oppVal[0] > userVal[0] || 
                   (oppVal[0] == userVal[0] && oppVal[1] > userVal[1])) {
                    beaten = true;
                    break;
                } else if (oppVal[0] == userVal[0] && oppVal[1] == userVal[1]) {
                    exactTie = true;
                }
            }
    
            if (beaten) { 
                losses++;
            } else if (exactTie) {
                ties++;
            } else {
                wins++;
            }
        }
    
        return (wins + ties / 2.0) / numRepetitions;
    }
    
    

    public void randomBoard(Deck deck, ArrayList<Card> boardCards, int totalCardsNeeded) {
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
        deck.removeCardFromDeck(randomCard);
        return randomCard;
    }
}
