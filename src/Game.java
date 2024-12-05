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
    private Deck deck;
    private final String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private final String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
    private final int[] values = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};

    // Constructs the players and their hands
    public Game() {
        // Get name of the player
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
        this.deck = new Deck(this.ranks, this.suits, this.values);

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

    // Play the game by starting the rounds
    public void playGame() {
        startRound();
    }
    public void startRound() {
        while (!isGameDone()) {
            Scanner scanner = new Scanner(System.in);
            // Keep on alternating computer and player turns until someone has won
            System.out.println("Ready for your turn? Press ENTER\n");
            scanner.nextLine();
            playerTurn();
            System.out.println("Ready for next turn? Press ENTER\n");
            scanner.nextLine();
            computerTurn();
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
            System.out.println("COMPUTER WINS!");
        }
        else {
            System.out.println(player1.getName() + " WINS!");
        }
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

    // Runs turn for when the player asks the computer for a card rank
    public void playerTurn() {
        if (!isGameDone()) {
            String cardRank = "";
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
            // When input is valid
            ArrayList<Card> result = this.computer.checkHand(cardRank);
            // If the computer does not have the card rank the player asked for, it says "Go fish!" and the player draws
            if (result.isEmpty()) {
                System.out.println("Go Fish! " + this.player1.getName() + " will draw a card\n");
                this.player1.addCard(deck.deal());
            } else {
                // The computer gives all of their cards of the rank the player asked for to the player
                for (Card card : result) {
                    this.player1.addCard(card);
                    this.computer.removeCard(card);
                    // The computer draws the amount of cards it gave to the player
                    if (!this.deck.isEmpty()) {
                        this.computer.addCard(this.deck.deal());
                    }
                }
                System.out.println(this.player1.getName() + " took all of the computer's " + cardRank + "'s ." + "The computer will draw.");
                Card card = computer.checkQuad();
                if (card != null) {
                    System.out.println(this.computer.getName() + " has all the " + card.getRank() + "s! They will be removed from the computer's hand.");
                    computer.addQuad(card.getRank());
                    for (int i = 0; i < computer.getHand().size(); i++) {
                        if (computer.getHand().get(i).getRank().equals(card.getRank())) {
                            computer.getHand().remove(i);
                            i--;
                        }
                    }
                }
            }

            // Checks if the player has four-of-a-kind, and if they do, takes those cards out of their hand and puts them in their Quad Array
            Card card = player1.checkQuad();
            if (card != null) {
                System.out.println(this.player1.getName() + " has all the " + card.getRank() + "s! They will be removed from " + this.player1.getName() + "'s hand.");
                player1.addQuad(card.getRank());
                for (int i = 0; i < player1.getHand().size(); i++) {
                    if (player1.getHand().get(i).getRank().equals(card.getRank())) {
                        player1.getHand().remove(i);
                        i--;
                    }
                }
            }
            // Prints out the quads of the player and computer, sorts their hands, and prints the player's hand
            System.out.println("Computer's Quads: " + this.computer.getQuads() + "  |  " + this.player1.getName() + "'s Quads: " + this.player1.getQuads());
            player1.sortHand();
            computer.sortHand();
            System.out.println(this.player1.getName() + "'s hand now: " + this.player1.getHand() + "\n");
        }
    }

    // Runs turn for when the computer asks the player for a card
    public void computerTurn() {
        if (!isGameDone()) {
            // The computer asks for a card rank based on the most frequent card in its hand
            Card cardRank = this.computer.findMostFrequentCard();
            System.out.println("Computer: Do you have a " + cardRank.getRank() + " ?\n");
            ArrayList<Card> result = this.player1.checkHand(cardRank.getRank());
            // If the player does not have the card the computer asked for, the computer draws
            if (result.isEmpty()) {
                System.out.println(this.player1.getName() + " did not have a " + cardRank.getRank() + ". The computer will draw.");
            }
            // If the player does have the rank the computer asked for, the computer takes the players cards of that rank
            else {
                for (Card card : result) {
                    this.computer.addCard(card);
                    this.player1.removeCard(card);
                    if (!this.deck.isEmpty()) {
                        this.player1.addCard(this.deck.deal());
                    }
                }
                System.out.println("The computer took all of " + this.player1.getName() + "'s " + cardRank.getRank() + "s. " + this.player1.getName() + "  will draw.");
                Card card = player1.checkQuad();
                if (card != null) {
                    System.out.println(this.player1.getName() + " has all the " + card.getRank() + "s! They will be removed from " + this.player1.getName() + "'s hand.");
                    player1.addQuad(card.getRank());
                    for (int i = 0; i < player1.getHand().size(); i++) {
                        if (player1.getHand().get(i).getRank().equals(card.getRank())) {
                            player1.getHand().remove(i);
                            i--;
                        }
                    }
                }
            }
            // Checks if the computer has any four-of-a-kinds
            Card card = this.computer.checkQuad();
            // If it does, takes those four cards out of the computer's hand and puts them in its quad Array
            if (card != null) {
                System.out.println(this.computer.getName() + " has all the " + card.getRank() + "s! They will be removed from " + this.computer.getName() + "'s hand.");
                computer.addQuad(card.getRank());
                for (int i = 0; i < computer.getHand().size(); i++) {
                    Card currentCard = computer.getHand().get(i);
                    if (currentCard != null && currentCard.getRank().equals(card.getRank())) {
                        computer.getHand().remove(i);
                        i--;
                    }
                }
            }
            // Prints the computer's and player's quads, sorts their hands, and prints the players hand now
            System.out.println("Computer's Quads: " + this.computer.getQuads() + "  |  " + this.player1.getName() + "'s Quads: " + this.player1.getQuads());
            player1.sortHand();
            computer.sortHand();
            System.out.println(this.player1.getName() + "'s hand now: " + this.player1.getHand() + "\n");
        }
    }


    // Main method
    public static void main(String[] args) {
        // Prints the instructions, creates a new game, and plays that game
        printInstructions();
        Game g1 = new Game();
        g1.playGame();
    }
}



