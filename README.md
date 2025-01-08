# PokerHand-odds-Simulator

This project is a Java-based application designed to calculate real-time winning odds in a Texas Hold’em poker scenario. By simulating possible opponents’ hands and dealing out the unknown community cards many times (Monte Carlo style), the application estimates your probability of winning, tying, or losing at each stage of the hand: Pre-Flop, Post-Flop, Post-Turn, and Post-River.

## Installation
  1. Clone the repository in IDE of choice using: https://github.com/cshevick/PokerHand-odds-Simulator.git
  2. Compile the Java files (if using the command line, navigate to the project folder and run): javac *.java
  3. Run the application: java Main
 
## Usage

This application helps poker players estimate their winning odds by providing:
1. **Monte Carlo Simulations:** It simulates the remaining deck for opponents’ cards and any unrevealed community cards multiple times to approximate the likelihood of your hand winning.
2. **Street-by-Street Odds:** You get separate odds calculations at each stage of a Texas Hold’em hand—Pre-Flop, Post-Flop, Post-Turn, and Post-River.

## Gameplay Instructions / Workflow
This application is designed to be used alongside a real or hypothetical Texas Hold’em game, where you manually input the known cards.

**1. Number of Opponents:**
At the start, enter how many other players are in the hand (e.g., 1, 4, etc.).

**2. Enter Your hand:**
You’ll be prompted for the ranks and suits of your first and second card (e.g., "Hearts 10" or "Spades Ace").
The program removes those cards from the deck, so they cannot be dealt to opponents.

**3. Odds Pre-Flop:**
The simulator runs a series of random deals to generate the opponents’ cards and calculates your chance of winning or tying before the flop is shown.

**3. Enter the Flop:**
You’ll be asked for the three community cards (e.g., "Hearts 2", "Clubs King", etc.).
The app simulates your winning odds again with these known flop cards.

**4. Enter the Turn:**
After the flop, enter the turn card.
The simulator updates the odds with four known community cards.

**5. Enter the River:**
Finally, input the river card.
The simulator calculates the final odds when all five community cards are known.

**Monte Carlo Repetitions:**
The default number of simulations is often set to a few thousand for reasonable speed and accuracy.
Adjust the number of simulations (e.g., 10,000 or 50,000) in the code for higher accuracy at the cost of increased runtime.


## License
This project is licensed under the MIT License. Feel free to modify and use the code as per your needs.
