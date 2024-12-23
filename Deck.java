import java.util.ArrayList;
import java.util.Iterator;

public class Deck {

    public ArrayList<Card> cards;
    public ArrayList<Card> usedCards;

    public Deck(){
        cards = new ArrayList<>();
        usedCards = new ArrayList<>();

        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10",
                          "Jack", "Queen", "King", "Ace"};

        for (String suit : suits){
            for (String rank : ranks){
                cards.add(new Card(rank, suit));
            }
        }
    }

    public ArrayList<Card> getCards(){
        return cards;
    }

    public ArrayList<Card> getUsedCards(){
        return usedCards;
    }
    
    public Card getCard(int index){
        return cards.get(index);
    }

    public void printDeck(){
        for (Card card: cards){
            System.out.println(card);
        }
    }

    public void shuffle(){
        for (int i = 0; i < cards.size(); i++){
            int randomIndex = (int) (Math.random() * cards.size());
            Card temp = cards.get(i);
            cards.set(i, cards.get(randomIndex));
            cards.set(randomIndex, temp);
        }
    }

    public int getSize(){
        return cards.size();
    }
   

    public void removeCardFromDeck(Card card) {
    
        boolean success = false;
        Iterator<Card> iterator = cards.iterator();
        while (iterator.hasNext()) {
            Card eachCard = iterator.next();
            if (eachCard.getRank().equals(card.getRank()) && eachCard.getSuit().equals(card.getSuit())) {
                iterator.remove();
                usedCards.add(eachCard);
                success = true;
                break;
            }
        }
        if (!success) System.out.println("Card not removed or card has already been removed.");
    
    
    }

    public void resetDeck(){
        cards.addAll(usedCards);
        usedCards.clear();
    }

}
