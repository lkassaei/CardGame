// Go Fish game by Lily Kassaei
// This file defines the game class that handles the main game logic and setup.

// Import needed libraries
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    // Declare all instance variables including players for player1 and computer, a deck,
    // And the ranks, suits, and values that go in that deck
    private Player player1;
    private Player computer;
    private Player winningPlayer;
    private Deck deck;
    private final String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private final String[] suits = {"Spades", "Hearts", "Diamonds", "Clubs"};
    private final int[] values = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
    private GameViewer window;
    private int state;

    private static final int MAX_RANKS_IN_DECK = 13;
    // Make public so GameViewer has access
    public static final int INSTRUCTION_STATE = 0;
    public static final int MAIN_STATE = 1;
    public static final int GAME_OVER_STATE = 2;


    // Constructs the players and their hands
    public Game() {
        // Get name of the player
        window = new GameViewer(this);
        this.state = INSTRUCTION_STATE;
        initializeGame();
    }

    private void initializeGame() {
        String name1 = getPlayerName();
        this.player1 = new Player(name1);
        this.computer = new Player("Computer");
        this.deck = new Deck(this.ranks, this.suits, this.values, this.window);
        dealHands();
        window.repaint();
    }

    // Prompts the player to enter their name and returns the input
    private String getPlayerName() {
        Scanner scanner = new Scanner(System.in);

        // Displays a prompt for the player to enter their name
        System.out.println("====================");
        System.out.println("|  TYPE NAME HERE  |");
        System.out.println("====================");
        System.out.println("--------> ");

        return scanner.nextLine(); // Reads and returns the player's name
    }

    // Deals initial hands to both the player and the computer
    private void dealHands() {
        ArrayList<Card> playerHand = new ArrayList<>();  // Player's hand
        ArrayList<Card> computerHand = new ArrayList<>(); // Computer's hand

        // Each player draws 7 cards from the deck
        for (int i = 0; i < 7; i++) {
            playerHand.add(deck.deal());  // Deal a card to the player
            computerHand.add(deck.deal()); // Deal a card to the computer
        }

        // Assign hands to players
        player1.setHand(playerHand);
        computer.setHand(computerHand);

        // Sort hands for better organization
        player1.sortHand();
        computer.sortHand();
    }

    // Getter methods
    public Player getWinningPlayer() {
        return this.winningPlayer;
    }

    public int getState() {
        return this.state;
    }

    public Deck getDeck() {
        return this.deck;
    }

    public Player getPlayer1() {
        return this.player1;
    }

    public Player getComputer() {
        return this.computer;
    }

    public static void printInstructions() {
        // Make Ascii art of "Welcome to Go Fish!" and print the game rules
        System.out.println(" __          __  _                             _            _____          ______ _     _         _  ");
        System.out.println(" \\ \\        / / | |                           | |          / ____|        |  ____(_)   | |       | | ");
        System.out.println("  \\ \\  /\\  / /__| | ___ ___  _ __ ___   ___   | |_ ___    | |  __  ___    | |__   _ ___| |__     | | ");
        System.out.println("   \\ \\/  \\/ / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\  | __/ _ \\   | | |_ |/ _ \\   |  __| | / __| '_ \\    | | ");
        System.out.println("    \\  /\\  /  __/ | (_| (_) | | | | | |  __/  | || (_) |  | |__| | (_) |  | |    | \\__ \\ | | |   |_| ");
        System.out.println("     \\/  \\/ \\___|_|\\___\\___/|_| |_| |_|\\___|   \\__\\___/    \\_____|\\___/   |_|    |_|___/_| |_|   (_) ");
        System.out.println();
        System.out.println("=================================================================================================");
        System.out.println("|                                           GAME RULES                                          |");
        System.out.println("=================================================================================================");
        System.out.println("| 1. You take a turn, followed by the comupter.                                                 |");
        System.out.println("| 2. Whoever's turn it is asks the other player for a specific card rank.                       |");
        System.out.println("| 3. The other player has to give all their cards of that rank.                                 |");
        System.out.println("| 4. If they have none, they say 'Go Fish!' and the player whose turn it is draws from the deck.|");
        System.out.println("| 5. Collect a four of a kind, or a quad, to score points.                                      |");
        System.out.println("| 6. The game ends when all quads have been collected                                           |");
        System.out.println("| 7. Whoever has the most quads wins!                                                           |");
        System.out.println("=================================================================================================");
        System.out.println();

    }

    // Starts a new round of the game and continues until there is a winner
    public void startRound() {
        while (!isGameDone()) {  // Loop continues until the game is over
            this.state = MAIN_STATE; // Set the game state to the main playing state

            Scanner scanner = new Scanner(System.in);

            // Player's turn prompt
            System.out.println("Ready for your turn? Press ENTER\n");
            scanner.nextLine(); // Waits for the player to press Enter to proceed
            Turn(this.player1, this.computer); // Player takes their turn

            // Computer's turn prompt
            System.out.println("Ready for next turn? Press ENTER\n");
            scanner.nextLine(); // Waits for the player to press Enter before the computer's turn
            Turn(this.computer, this.player1); // Computer takes its turn
        }

        checkWin(); // Once the game is done, determine the winner
    }

    // Check if all quads have been collected
    public boolean isGameDone() {
        return computer.getQuads().size() + player1.getQuads().size() == MAX_RANKS_IN_DECK;
    }


    public void checkWin() {
        // Update the state
        this.state = GAME_OVER_STATE;
        // Check who has the most quads and declare them as the winner
        winningPlayer = (computer.getQuads().size() > player1.getQuads().size()) ? computer : player1;
        System.out.println(winningPlayer.getName() + " WINS!");
        // Update the window
        window.repaint();
    }

    // Make sure input when asking for a card is valid
    public boolean checkValidMove(Player p, String cardRank) {
        for (Card c : p.getHand()) {
            // If they don't have the card rank in their hand, they can't ask for it
            if (c.getRank().equals(cardRank)) {
                return true;
            }
        }
        return false;
    }

    public void Turn(Player currentPlayer, Player otherPlayer) {
        String cardRank = ""; // Variable to store the requested card rank

        // Check if the game is still ongoing
        if (!isGameDone()) {

            if (currentPlayer.equals(player1)) {
                // If it's the player's turn, get input and ensure it's valid
                cardRank = getPlayerMove();
            } else {
                // If it's the computer's turn, select the most frequently occurring rank in its hand
                cardRank = this.computer.findMostFrequentCard().getRank();
                System.out.println("Computer: Do you have a " + cardRank + " ?\n");
            }

            // Check if the other player has cards of the requested rank
            ArrayList<Card> result = otherPlayer.checkHand(cardRank);

            if (result.isEmpty()) {
                // If the other player doesn't have the requested card, the current player must "Go Fish"
                System.out.println("Go Fish! " + otherPlayer.getName() + " did not have a " + cardRank + ". "
                        + currentPlayer.getName() + " will draw. ");
                if (!deck.isEmpty()) {
                    currentPlayer.addCard(this.deck.deal()); // Draw a card from the deck if it's not empty
                }
                window.repaint(); // Update the frontend
            } else {
                // The other player gives all matching cards to the current player
                transferCards(currentPlayer, otherPlayer, result);
                window.repaint(); // Update the frontent
            }

            // Check if the current player or the other player has formed a quad (four of a kind)
            handleQuad(currentPlayer);
            handleQuad(otherPlayer);

            // Print the current status of quads for both players
            System.out.println("Computer's Quads: " + this.computer.getQuads() + "  |  "
                    + this.player1.getName() + "'s Quads: " + this.player1.getQuads());

            // Sort hands and print the updated player's hand
            player1.sortHand();
            computer.sortHand();
            System.out.println(this.player1.getName() + "'s hand now: " + this.player1.getHand() + "\n");

            window.repaint(); // Final frontend update
        }
    }

    private String getPlayerMove() {
        Scanner scanner = new Scanner(System.in);
        String cardRank;

        while (true) {
            // Display the player's current hand
            System.out.println(player1.getName() + "'s hand: " + player1.getHand() + "\n");

            // Prompt the player to choose a card rank to ask for
            System.out.print("What card number/royal do you want?\n");
            cardRank = scanner.nextLine();

            // Validate the player's move
            if (checkValidMove(player1, cardRank)) break;

            // If invalid, prompt the player to try again
            System.out.println("INVALID MOVE, TRY AGAIN");
        }

        return cardRank; // Return the valid card rank chosen by the player
    }

    private void transferCards(Player currentPlayer, Player otherPlayer, ArrayList<Card> cards) {
        // Transfer all matching cards from the other player to the current player
        for (Card card : cards) {
            currentPlayer.addCard(card);
            otherPlayer.removeCard(card);

            // If the deck is not empty, the other player draws a replacement card
            if (!deck.isEmpty()) {
                otherPlayer.addCard(deck.deal());
            }
        }

        // Notify the players about the transfer
        System.out.println(currentPlayer.getName() + " took all of " + otherPlayer.getName() + "'s "
                + cards.get(0).getRank() + "s.");
        System.out.println(otherPlayer.getName() + " will draw if the deck is not empty.");
    }

    private void handleQuad(Player player) {
        // Check if the player has collected four of a kind (a quad)
        Card quadCard = player.checkQuad();

        if (quadCard != null) {
            // Announce that the player has collected all four cards of a rank
            System.out.println(player.getName() + " has all the " + quadCard.getRank() + "s! They will be removed.");

            // Add the completed quad to the player's collection
            player.addQuad(quadCard.getRank());

            // Remove the four cards of that rank from the player's hand
            player.removeQuad(player, quadCard.getRank());
        }
    }

    // Main method
    public static void main(String[] args) {
        // Prints the instructions, creates a new game, and plays that game
        printInstructions();
        Game g1 = new Game();
        g1.startRound();
    }
}



