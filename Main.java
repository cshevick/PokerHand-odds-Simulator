import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // This will eventually hold the user-chosen community cards
        ArrayList<Card> dealtCards = new ArrayList<>();

        // Build a deck and shuffle it
        Deck deck = new Deck();
        deck.shuffle();

        System.out.print("Enter the number of other players: ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine();

        // === 1) Get Hole Cards ===
        System.out.print("Enter your first card: \n");
        Card userCard1 = cardUserInput(scanner, deck);  // removes from deck
        System.out.print("Enter your second card: \n");
        Card userCard2 = cardUserInput(scanner, deck);  // removes from deck

        // === 2) Pre-Flop Simulation ===
        OddsSimulator simulator = new OddsSimulator(userCard1, userCard2, deck, numPlayers, dealtCards);
        double preflopOdds = simulator.simulate(50, dealtCards, deck);
        System.out.printf("Odds Pre flop: %.2f%%\n", 100 * preflopOdds);

        // === 3) Reset deck before the post-flop stage ===
        // Because we've used up random cards in the preflop simulation.
        deck.resetDeck();
        
        // Remove the user's hole cards again from this fresh deck
        deck.removeCardFromDeck(userCard1);
        deck.removeCardFromDeck(userCard2);

        // === 4) Ask user for the Flop (3 cards) ===
        System.out.print("\nEnter the first flop card: \n");
        Card boardCard1 = cardUserInput(scanner, deck);
        dealtCards.add(boardCard1);

        System.out.print("Enter the second flop card: \n");
        Card boardCard2 = cardUserInput(scanner, deck);
        dealtCards.add(boardCard2);

        System.out.print("Enter the third flop card: \n");
        Card boardCard3 = cardUserInput(scanner, deck);
        dealtCards.add(boardCard3);

        // === 5) Post-Flop Simulation ===
        // The deck at this point has the user’s hole cards + flop cards removed.
        OddsSimulator simulatorFlop = new OddsSimulator(userCard1, userCard2, deck, numPlayers, dealtCards);
        double postFlopOdds = simulatorFlop.simulate(50, dealtCards, deck);
        System.out.printf("Odds Post Flop: %.2f%%\n", 100 * postFlopOdds);

        scanner.close();
    }

    public static Card cardUserInput(Scanner scanner, Deck deck) {
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

        // Validate suit
        while (!suit.equals("Hearts") && !suit.equals("Clubs") &&
               !suit.equals("Spades") && !suit.equals("Diamonds")) {
            System.out.print("Invalid suit. Please enter a valid suit (Hearts, Diamonds, Clubs, Spades): ");
            suit = scanner.nextLine();
        }

        // Validate rank
        while (!rank.equals("2") && !rank.equals("3") && !rank.equals("4") &&
               !rank.equals("5") && !rank.equals("6") && !rank.equals("7") &&
               !rank.equals("8") && !rank.equals("9") && !rank.equals("10") &&
               !rank.equals("Jack") && !rank.equals("Queen") &&
               !rank.equals("King") && !rank.equals("Ace")) {
            System.out.print("Invalid rank. Please enter a valid rank (2, 3, 4, 5, 6, 7, 8, 9, 10, Jack, Queen, King, Ace): ");
            rank = scanner.nextLine();
        }

        Card inputCard = new Card(rank, suit);
        // Remove from deck so we can't draw it again
        deck.removeCardFromDeck(inputCard);

        return inputCard;
    }
}
