import java.util.Scanner;
import java.util.ArrayList;

class Main {
    public static void main(String[] args) {    
        Scanner scanner = new Scanner(System.in);
        ArrayList<Card> playedCards = new ArrayList<>();
        Deck deck = new Deck();
       

        
        System.out.print("Enter your first card: \n");
        Card userCard1 = cardUserInput(scanner,deck);
        
        System.out.print("Enter your second card: \n");
        Card userCard2 = cardUserInput(scanner, deck);

        OddsSimulator simulator = new OddsSimulator(userCard1, userCard2, deck, 2);

        /* 

        System.out.println("\nEnter the board: \n");

        playedCards.add(Main.cardUserInput(scanner, deck));
        playedCards.add(Main.cardUserInput(scanner, deck));
        playedCards.add(Main.cardUserInput(scanner, deck));
        playedCards.add(Main.cardUserInput(scanner, deck));
        playedCards.add(Main.cardUserInput(scanner, deck));
    

        HandEvaluator hand1 = new HandEvaluator(userCard1, userCard2, playedCards);
        HandEvaluator hand2 = new HandEvaluator(new Card("4","Spades"), new Card("3","Spades"), playedCards);

        betterHand(hand1, hand2);
        
        */

        scanner.close();
    }

    public static void betterHand(HandEvaluator player1, HandEvaluator player2){
        int[] player1Hand = player1.getHandValue();
        int[] player2Hand = player2.getHandValue();
        
        if (player1Hand[0] > player2Hand[0]) {
            System.out.println("Player 1 wins.");
        } else if (player1Hand[0] == player2Hand[0]) {
            if (player1Hand[1] == player2Hand[1]) {
                System.out.println("Both Players split the pot.");
            } else if (player1Hand[1] > player2Hand[1]) {
                System.out.println("Player 1 wins.");
            } else if (player2Hand[1] > player1Hand[1]) {
                System.out.println("Player 2 wins.");
            }
        } else {
            System.out.println("Player 2 wins.");
        }
    }

    public static Card cardUserInput(Scanner scanner, Deck deck){
        System.out.print("Enter a card (e.g., Hearts 10): ");
        String input = scanner.nextLine();
        String[] split = input.split(" ");
        
        while (split.length != 2) {
            System.out.print("Invalid input. Enter a card (e.g., Hearts 10): ");
            input = scanner.nextLine();
            split = input.split(" ");
        }
    
        String suit = split[0];
        String rank = split[1];
    
        while (!suit.equals("Hearts") && !suit.equals("Clubs") && 
               !suit.equals("Spades") && !suit.equals("Diamonds")) {
            System.out.print("Invalid suit. Please enter a valid suit (Hearts, Diamonds, Clubs, Spades): ");
            suit = scanner.nextLine();
        }
        while (!rank.equals("2") && !rank.equals("3") && !rank.equals("4") && 
               !rank.equals("5") && !rank.equals("6") && !rank.equals("7") && 
               !rank.equals("8") && !rank.equals("9") && !rank.equals("10") && 
               !rank.equals("Jack") && !rank.equals("Queen") && 
               !rank.equals("King") && !rank.equals("Ace")) {
            System.out.print("Invalid rank. Please enter a valid rank (2, 3, 4, 5, 6, 7, 8, 9, 10, Jack, Queen, King, Ace): ");
            rank = scanner.nextLine();
        }

        Card inputCard = new Card(rank,suit);

        deck.removeCardFromDeck(inputCard);
        return inputCard;
    }
}