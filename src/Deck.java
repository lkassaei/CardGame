// Go Fish game by Lily Kassaei
// This file defines the Deck Class which creates a deck for the players

// Import needed libraries
import java.util.ArrayList;

public class Deck {
    // Declare instance variables
    private ArrayList<Card> cards;
    private int cardsLeft;

    // Constructor that takes all the ranks, suits, and values in a deck and creates 52 cards from them
    public Deck(String[] ranks, String[] suits, int[] values) {
        this.cards = new ArrayList<>();

        for (String suit: suits) {
            for (int i = 0; i < ranks.length; i++) {
                String rank = ranks[i];
                int value = values[i];
                Card c = new Card(rank, suit, value);
                cards.add(c);
            }
        }
        this.cardsLeft = cards.size();
        // Shuffles the deck
        shuffle();
    }

    // Checks if the deck is empty
    public boolean isEmpty() {
        return this.cardsLeft == 0;
    }

    // Returns how many cards are left in the deck
    public int getCardsLeft() {
        return cardsLeft;
    }

    // Deals the top card in the deck to a player
    public Card deal() {
        if (isEmpty()) {
            return null;
        }
        int index = this.cardsLeft - 1;
        this.cardsLeft--;
        return this.cards.get(index);
    }

    // Shuffles the deck
    public void shuffle() {
        this.cardsLeft = cards.size();
        for (int i = 0; i < cards.size(); i++) {
            int random = (int)(Math.random() * cards.size());
            Card tempVal = cards.get(i);
            cards.set(i, cards.get(random));
            cards.set(random, tempVal);
        }
    }
}
