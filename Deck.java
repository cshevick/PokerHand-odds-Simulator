import java.util.ArrayList;

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
    
    public String getCard(int index){
        return cards.get(index).toString();
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

    public  void drawCard(String suit, String rank){
        for (Card card: cards){
            if (card.getRank().equals(rank) && card.getSuit().equals(suit)){
                Card playedCard = card;
                cards.remove(playedCard);
                usedCards.add(playedCard);
                break;
            }
        }
    
    }

    public void identifyRemoveCard(String input){

        System.out.println("Used Cards deck: " + usedCards.size());
        System.out.println("Deck size: " + cards.size());
        
        String[] split = input.split(" ");
        
        if (split.length != 2) {
            System.out.println("Invalid input. Please enter the card in the format 'Suit Rank'.");
            return;
        }
    
        String suit = split[0];
        String rank = split[1];
    
       drawCard(suit, rank);
    
        System.out.println("Used Cards deck: " + usedCards.size());
        System.out.println("Deck size: " + cards.size());
    }

    public void resetDeck(){
        cards.addAll(usedCards);
        usedCards.clear();
    }

}
