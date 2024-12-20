import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Deck cards = new Deck();
        cards.shuffle();
    
        // get user info
        System.out.print("Enter how many players are at the table: ");
        int numPlayers = scanner.nextInt();

        System.out.println("Used Cards deck: " + Deck.getSize(cards.getUsedCards()));
        System.out.println("Deck size: " + Deck.getSize(cards.getCards()));

        System.out.println("\nPre flop - Enter Hand:");
        
        Card userCard1 = Main.cardUserInput();
        Card userCard2 = Main.cardUserInput();
       
        cards.identifyRemoveCard(userCard1);
        cards.identifyRemoveCard(userCard2);

        System.out.println("\nFlop board: ");
        Card flop1 = Main.cardUserInput();
        Card flop2 = Main.cardUserInput();
        Card flop3 = Main.cardUserInput();
        cards.identifyRemoveCard(flop1);
        cards.identifyRemoveCard(flop2);
        cards.identifyRemoveCard(flop3);

        System.out.println("\nTurn card: ");
        Card turn = Main.cardUserInput();
        cards.identifyRemoveCard(turn);

        System.out.println("\nRiver card: ");
        Card river = Main.cardUserInput();
        cards.identifyRemoveCard(river);

        System.out.println("Used Cards deck: " + Deck.getSize(cards.getUsedCards()));
        System.out.println("Deck size: " + Deck.getSize(cards.getCards()));

       
    }

    public static Card cardUserInput(){
        Scanner scanner = new Scanner(System.in);
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
        return new Card(rank, suit);
    }
}