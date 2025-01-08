import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;
import java.util.List;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of other players: ");
        int numOpponents = scanner.nextInt();
        scanner.nextLine();
    
        // Build one deck instance so user can remove from it as they declare cards
        Deck deck = new Deck();
        deck.shuffle();
    
        System.out.println("Enter your first card:");
        Card userCard1 = cardUserInput(scanner, deck);
        System.out.println("Enter your second card:");
        Card userCard2 = cardUserInput(scanner, deck);
    
        ArrayList<Card> dealtCards = new ArrayList<>();
    
        // --- Pre-Flop ---
        OddsSimulator simPre = new OddsSimulator(userCard1, userCard2, deck, numOpponents, dealtCards);
        double preFlopOdds = simPre.simulate(1000, dealtCards, deck); 
        System.out.printf("Odds Pre-flop: %.2f%%\n", 100 * preFlopOdds);
    
        // --- Flop ---
        Card flop1 = cardUserInput(scanner, deck); dealtCards.add(flop1);
        Card flop2 = cardUserInput(scanner, deck); dealtCards.add(flop2);
        Card flop3 = cardUserInput(scanner, deck); dealtCards.add(flop3);
    
        OddsSimulator simFlop = new OddsSimulator(userCard1, userCard2, deck, numOpponents, dealtCards);
        double flopOdds = simFlop.simulate(1000, dealtCards, deck);
        System.out.printf("Odds Post-Flop: %.2f%%\n", 100 * flopOdds);
    
        // --- Turn ---
        Card turn = cardUserInput(scanner, deck);
        dealtCards.add(turn);
    
        OddsSimulator simTurn = new OddsSimulator(userCard1, userCard2, deck, numOpponents, dealtCards);
        double turnOdds = simTurn.simulate(1000, dealtCards, deck);
        System.out.printf("Odds Post-Turn: %.2f%%\n", 100 * turnOdds);
    
        // --- River ---
        Card river = cardUserInput(scanner, deck);
        dealtCards.add(river);
    
        OddsSimulator simRiver = new OddsSimulator(userCard1, userCard2, deck, numOpponents, dealtCards);
        double riverOdds = simRiver.simulate(1000, dealtCards, deck);
        System.out.printf("Odds Post-River: %.2f%%\n", 100 * riverOdds);
    
        scanner.close();
    }
    

   
    public static Card cardUserInput(Scanner scanner, Deck deck) {
        while (true) {
            System.out.print("Enter a card (e.g., Hearts 10): ");
            String input = scanner.nextLine().trim();
            String[] split = input.split("\\s+");
            if (split.length != 2) {
                System.out.println("Invalid format. Try again.");
                continue;
            }

            // Suit + rank
            String suitInput = capitalizeFirst(split[0]);   
            String rankInput = capitalizeFirst(split[1]);  

            // Validate suit
            if (!suitInput.equals("Hearts") && !suitInput.equals("Diamonds") &&
                !suitInput.equals("Clubs")  && !suitInput.equals("Spades")) {
                System.out.println("Invalid suit. Try again.");
                continue;
            }

            // Validate rank (simply check a small list or parse int)
            ArrayList<String> validRanks = new ArrayList<>(
                List.of("2","3","4","5","6","7","8","9","10","Jack","Queen","King","Ace")
            );
            if (!validRanks.contains(rankInput)) {
                System.out.println("Invalid rank. Try again.");
                continue;
            }

            // Construct card
            Card inputCard = new Card(rankInput, suitInput);

            // Remove from deck
            deck.removeCardFromDeck(inputCard);
            return inputCard;
        }
    }

    // Quick helper to capitalize first letter, rest lower
    private static String capitalizeFirst(String s) {
        if (s.isEmpty()) return s;
        return s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();
    }
}
