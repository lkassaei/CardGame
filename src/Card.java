// Go Fish game by Lily Kassaei
// This file defines the Card Class which creates cards for the deck

public class Card {
    // Declare instance variables
    private String rank;
    private String suit;
    private int value;

    // Constructs a card with a rank, suit, and value
    public Card(String rank, String suit, int value) {
        this.rank = rank;
        this.suit = suit;
        this.value = value;
    }

    // Accesses the rank of a card
    public String getRank() {
        return rank;
    }

    // Alters the rank of a card
    public void setRank(String rank) {
        this.rank = rank;
    }

    // Accesses the suit of a card
    public String getSuit() {
        return suit;
    }

    // Alters the suit of a card
    public void setSuit(String suit) {
        this.suit = suit;
    }

    // Accesses the value of a card
    public int getValue() {
        return value;
    }

    // Alters the value of a card
    public void setValue(int value) {
        this.value = value;
    }

    // Prints the rank and suit of a card. Ex. Ace of Spades
    @Override
    public String toString() {
        return this.rank + " of " + this.suit;
    }
}
