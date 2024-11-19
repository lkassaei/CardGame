import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private Player player1;
    private Deck deck;
    private final String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private final String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
    private final int[] values = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};

    public Game(Player player1) {
       this.player1 = player1;
       this.deck = new Deck(this.ranks, this.suits, this.values);
       ArrayList<Card> hand = new ArrayList<Card>();
       for (int i = 0; i < 7; i++) {
           hand.add(deck.deal());
       }
       player1.setHand(hand);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Name 1: ");
        String name1 = scanner.next();
        Player p1 = new Player(name1);

        Game g1 = new Game(p1);
    }
}



