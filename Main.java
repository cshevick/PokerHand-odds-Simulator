import java.util.Scanner;

class Main {
    public static void main(String[] args) {

        Deck cards = new Deck();
        //cards.shuffle();
    

        // get user info
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter how many players are at the table: ");
        int numPlayers = scanner.nextInt();
        System.out.println("Number of players entered: " + numPlayers);
        scanner.nextLine(); // Consume the newline character
        
        System.out.print("\nEnter your first card (suit and rank): ");
        String card1 = scanner.nextLine();
       
        cards.identifyRemoveCard(card1);
    }
}