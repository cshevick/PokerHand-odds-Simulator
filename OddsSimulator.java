import java.util.Random;
import java.util.ArrayList;
public class OddsSimulator {
    private int numPlayers;
    private Card userCard1;
    private Card userCard2;
    private ArrayList<Card> boardCards;

    public OddsSimulator(Card userCard1, Card userCard2, Deck deck, int numPlayers){
        this.boardCards = new ArrayList<>();
        this.userCard1 = userCard1;
        this.userCard2 = userCard2;
        this.numPlayers = numPlayers;
       

        for (int i = 0; i < 5; i++){
            Card randomCard = generateRandomCard(deck);
            deck.removeCardFromDeck(randomCard);
            boardCards.add(randomCard);
        }

        HandEvaluator evaluator = new HandEvaluator(userCard1, userCard2, boardCards);
        evaluator.printValue();
    }

    
    
    public static Card generateRandomCard(Deck deck){
    
        Random random = new Random();
        return deck.getCard(random.nextInt(deck.getSize()));
    }

    
}
