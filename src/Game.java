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

        System.out.println("====================");
        System.out.println("|  TYPE NAME HERE  |");
        System.out.println("====================");
        System.out.println("--------> ");

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
        player1.sortHand();
        computer.sortHand();
    }


    public static void printInstructions() {
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

    public void playGame() {
        startRound();
    }
    public void startRound() {
        while (!isGameDone()) {
            playerTurn();
            computerTurn();
        }
        checkWin();
    }

    public boolean isGameDone() {
        if (this.computer.getQuads().size() + this.player1.getQuads().size() == 13) {
            return true;
        }
        return false;
    }

    public void checkWin() {
        if (this.computer.getQuads().size() > this.player1.getQuads().size()) {
            System.out.println("COMPUTER WINS!");
        }
        else {
            System.out.println(player1.getName() + " WINS!");
        }
    }

    public boolean checkValidMove(Player p, String cardRank) {
        for (Card c : p.getHand()) {
            if (c.getRank().equals(cardRank)) {
                return true;
            }
        }
        return false;
    }

    public void playerTurn() {
        String cardRank = "";
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
        ArrayList<Card> result = this.computer.checkHand(cardRank);
        if (result.isEmpty()) {
            System.out.println("Go Fish! " + this.player1.getName() + " will draw a card\n");
            this.player1.addCard(deck.deal());
        } else {
            for (Card card : result) {
                this.player1.addCard(card);
                this.computer.removeCard(card);
                this.computer.addCard(this.deck.deal());
            }
            System.out.println(this.player1.getName() + " took all of the computer's " + cardRank + "'s ." + "The computer will draw.");
        }
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
        System.out.println("Computer's Quads: " + this.computer.getQuads() + "  |  " + this.player1.getName() + "'s Quads: " + this.player1.getQuads());
        player1.sortHand();
        computer.sortHand();
        System.out.println(this.player1.getName() + "'s hand now: " + this.player1.getHand() + "\n");

    }

    public void computerTurn() {
        Card cardRank = this.computer.findMostFrequentCard();
        System.out.println("Computer: Do you have a " + cardRank.getRank() + " ?\n");
        ArrayList<Card> result = this.player1.checkHand(cardRank.getRank());
        if (result.isEmpty()) {
            System.out.println(this.player1.getName() + " did not have a " + cardRank.getRank() + ". The computer will draw.");
        }
        else {
            for (Card card : result) {
                this.computer.addCard(card);
                this.player1.removeCard(card);
                this.player1.addCard(this.deck.deal());
            }
            System.out.println("The computer took all of " + this.player1.getName() + "'s " + cardRank.getRank() + "s. " + this.player1.getName() + "  will draw.");
        }
        Card card = this.computer.checkQuad();
        if (card != null) {
            System.out.println(this.computer.getName() + " has all the " + card.getRank() + "s! They will be removed from " + this.computer.getName() + "'s hand.");
            computer.addQuad(card.getRank());
            for (int i = 0; i < computer.getHand().size(); i++) {
                if (computer.getHand().get(i).getRank().equals(card.getRank())) {
                    computer.getHand().remove(i);
                    i--;
                }
            }
        }
        System.out.println("Computer's Quads: " + this.computer.getQuads() + "  |  " + this.player1.getName() + "'s Quads: " + this.player1.getQuads());
        player1.sortHand();
        computer.sortHand();
        System.out.println(this.player1.getName() + "'s hand now: " + this.player1.getHand() + "\n");
    }

    public static void main(String[] args) {
        printInstructions();
        Game g1 = new Game();
        g1.playGame();
    }
}



