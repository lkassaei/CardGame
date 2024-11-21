import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private Player player1;
    private Player computer;
    private Deck deck;
    private final String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private final String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
    private final int[] values = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};

    public Game() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Name 1: ");
        String name1 = scanner.nextLine();
        this.player1 = new Player(name1);
        this.computer = new Player("Computer");

        this.deck = new Deck(this.ranks, this.suits, this.values);
        ArrayList<Card> playerHand = new ArrayList<Card>();
        ArrayList<Card> computerHand = new ArrayList<Card>();
        for (int i = 0; i < 7; i++) {
           playerHand.add(deck.deal());
           computerHand.add(deck.deal());
        }
        player1.setHand(playerHand);
        computer.setHand(computerHand);
    }


    public static void printInstructions() {
        System.out.println("Welcome to Go Fish!");
    }

    public void playGame() {
        startRound();
    }
    public void startRound() {
        playerTurn();
        computerTurn();
    }

    public void playerTurn() {
        System.out.println("Your hand: " + this.player1.getHand() + "\n");
        System.out.println("What card?\n");
        Scanner scanner = new Scanner(System.in);
        String cardRank = scanner.nextLine();
        ArrayList<Card> result = this.computer.checkHand(cardRank);
        for (Card card : result) {
            this.player1.addCard(card);
            this.computer.removeCard(card);
            this.computer.addCard(this.deck.deal());
        }
        System.out.println("Your hand: " + this.player1.getHand() + "\n");
    }

    public void computerTurn() {
        System.out.println("Your hand: " + this.player1.getHand() + "\n");
        System.out.println("What card?\n");
        Scanner scanner = new Scanner(System.in);
        String cardRank = scanner.nextLine();
        ArrayList<Card> result = this.computer.checkHand(cardRank);
        for (Card card : result) {
            this.player1.addCard(card);
            this.computer.removeCard(card);
            this.computer.addCard(this.deck.deal());
        }
        System.out.println("Your hand: " + this.player1.getHand() + "\n");
    }

    public static void main(String[] args) {
        printInstructions();
        Game g1 = new Game();
        g1.playGame();
    }
}



