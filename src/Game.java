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
    public static final int INSTRUCTION_STATE = 0;
    public static final int MAIN_STATE = 1;
    public static final int GAME_OVER_STATE = 2;


    // Constructs the players and their hands
    public Game() {
        // Get name of the player
        window = new GameViewer(this);
        this.state = 0;
        Scanner scanner = new Scanner(System.in);


        System.out.println("====================");
        System.out.println("|  TYPE NAME HERE  |");
        System.out.println("====================");
        System.out.println("--------> ");

        String name1 = scanner.nextLine();

        // Make players for the real player and computer
        this.player1 = new Player(name1);
        this.computer = new Player("Computer");

        // Make a full deck for the game
        this.deck = new Deck(this.ranks, this.suits, this.values, this.window);

        // Make hands for the player and computer
        ArrayList<Card> playerHand = new ArrayList<Card>();
        ArrayList<Card> computerHand = new ArrayList<Card>();
        // Deal 7 cards into each hand
        for (int i = 0; i < 7; i++) {
           playerHand.add(deck.deal());
           computerHand.add(deck.deal());
        }
        // Set and sort the player and computer's hands
        player1.setHand(playerHand);
        computer.setHand(computerHand);
        player1.sortHand();
        computer.sortHand();
        window.repaint();
    }

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

    public void startRound() {
        while (!isGameDone()) {
            this.state = 1;
            Scanner scanner = new Scanner(System.in);
            // Keep on alternating computer and player turns until someone has won
            System.out.println("Ready for your turn? Press ENTER\n");
            scanner.nextLine();
            Turn(this.player1, this.computer);
            System.out.println("Ready for next turn? Press ENTER\n");
            scanner.nextLine();
            Turn(this.computer, this.player1);
        }
        checkWin();
    }

    // Check if all quads have been collected
    public boolean isGameDone() {
        if (this.computer.getQuads().size() + this.player1.getQuads().size() == 13) {
            return true;
        }
        return false;
    }

    // Check who has the most quads and declare them as the winner
    public void checkWin() {
        if (this.computer.getQuads().size() > this.player1.getQuads().size()) {
            this.winningPlayer = computer;
            this.state = 2;
            System.out.println("COMPUTER WINS!");
        }
        else {
            this.winningPlayer = player1;
            this.state = 2;
            System.out.println(player1.getName() + " WINS!");
        }
        window.repaint();
    }

    // Make sure input when asking for a card is valid by making sure the player has that card in their hand
    public boolean checkValidMove(Player p, String cardRank) {
        for (Card c : p.getHand()) {
            if (c.getRank().equals(cardRank)) {
                return true;
            }
        }
        return false;
    }

    public void Turn(Player currentPlayer, Player otherPlayer) {
        String cardRank = "";
        if (!isGameDone()) {
            if (currentPlayer.equals(player1)) {
                // Gets the input and re-prompts until input is valid
                while (true) {
                    System.out.println(this.player1.getName() + "'s hand: " + this.player1.getHand() + "\n");
                    System.out.print("What card number/royal do you want?\n");
                    Scanner scanner = new Scanner(System.in);
                    cardRank = scanner.nextLine();

                    if (!checkValidMove(player1, cardRank)) {
                        System.out.println("INVALID MOVE, TRY AGAIN");
                        continue;
                    }
                    break;
                }
            }
            else {
                cardRank = this.computer.findMostFrequentCard().getRank();
                System.out.println("Computer: Do you have a " + cardRank + " ?\n");
            }
            ArrayList<Card> result = otherPlayer.checkHand(cardRank);
            if (result.isEmpty()) {
                System.out.print("Go Fish! " + otherPlayer.getName() + " did not have a " + cardRank + ". " + currentPlayer.getName() + " will draw.");
                if (!deck.isEmpty()) {
                    currentPlayer.addCard(this.deck.deal());
                }
                window.repaint();
            }
            else {
                // The other player gives all of their cards of the rank the current player asked for
                for (Card card : result) {
                    currentPlayer.addCard(card);
                    otherPlayer.removeCard(card);
                    // The other player draws the amount of cards it gave to the current player
                    if (!this.deck.isEmpty()) {
                        otherPlayer.addCard(this.deck.deal());

                    }
                }
                System.out.println(currentPlayer.getName() + " took all of the " + otherPlayer.getName() + "'s " + cardRank + "s. " + otherPlayer.getName() + " will draw.");
                window.repaint();

                Card quadCard = otherPlayer.checkQuad();
                if (quadCard != null) {
                    System.out.println(otherPlayer.getName() + " has all the " + quadCard.getRank() + "s! They will be removed from " + otherPlayer.getName() + "'s hand.");
                    otherPlayer.addQuad(quadCard.getRank());
                    otherPlayer.removeQuad(otherPlayer, quadCard.getRank());
                }
            }
            // Checks if the computer has any four-of-a-kinds
            Card quadCard = currentPlayer.checkQuad();
            // If it does, takes those four cards out of the computer's hand and puts them in its quad Array
            if (quadCard != null) {
                System.out.println(currentPlayer.getName() + " has all the " + quadCard.getRank() + "s! They will be removed from " + currentPlayer.getName() + "'s hand.");
                currentPlayer.addQuad(quadCard.getRank());
                currentPlayer.removeQuad(currentPlayer, quadCard.getRank());
                window.repaint();
            }
            // Prints the computer's and player's quads, sorts their hands, and prints the players hand now
            System.out.println("Computer's Quads: " + this.computer.getQuads() + "  |  " + this.player1.getName() + "'s Quads: " + this.player1.getQuads());
            player1.sortHand();
            computer.sortHand();
            System.out.println(this.player1.getName() + "'s hand now: " + this.player1.getHand() + "\n");
            window.repaint();
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



