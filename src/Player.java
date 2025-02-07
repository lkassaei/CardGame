// Go Fish game by Lily Kassaei
// This file defines the Player Class which creates players for the game

// Import needed libraries
import java.util.ArrayList;

public class Player {
    // Declare instance variables
    private String name;
    private ArrayList<Card> hand;
    private int points;
    private ArrayList<String> quads;

    // Constructor that creates a player with a name, 0 points, and an empty place to store their four-of-a-kinds
    public Player(String name) {
        this.name = name;
        this.points = 0;
        this.quads = new ArrayList<String>();
    }

    // Another constructor that creates a player with a name, a hand, 0 points,
    // and an empty place to store their four-of-a-kinds
    public Player(String name, ArrayList<Card> hand) {
        this.name = name;
        this.hand = hand;
        this.points = 0;
        this.quads = new ArrayList<String>();
    }

    // Gets the name of a player
    public String getName() {
        return name;
    }

    // Sets the name of a player
    public void setName(String name) {
        this.name = name;
    }

    // Accesses the hand of a player
    public ArrayList<Card> getHand() {
        return hand;
    }

    // Alters the hand of a player
    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    // Gets the points a player has
    public int getPoints() {
        return this.points;
    }

    // Alters the points a player has
    public void setPoints(int points) {
        this.points = points;
    }

    // Gives points to a player
    public void addPoints(int points) {
        this.points = this.points + points;
    }

    // Gets the four-of-kinds of a player
    public ArrayList<String> getQuads() {
        return this.quads;
    }

    // Gives a player a four-of-a-kind
    public void addQuad(String cardRank) {
        this.quads.add(cardRank);
    }

    // Gives a card to a player's hand
    public void addCard(Card card) {
        if(card == null)
        {
            System.out.println("Sad");
        }
        this.hand.add(card);
    }

    // Removes a card from a player's hand
    public void removeCard(Card card) {
        this.hand.remove(card);
    }

    // Finds the most frequent card in a player's hand
    public Card findMostFrequentCard() {
        int num = (int)((Math.random() * 2) + 1);

        if (num == 1) {
            int randRank = (int)(Math.random() * hand.size());
            return hand.get(randRank);
        }
        else {
            if (hand == null || hand.isEmpty()) {
                return null; // Return null if the hand is empty
            }

            Card mostFrequent = null;
            int maxFrequency = 0;

            for (int i = 0; i < hand.size(); i++) {
                Card currentCard = hand.get(i);
                int frequency = 0;

                // Count how many times currentCard appears in the hand
                for (int j = 0; j < hand.size(); j++) {
                    if (hand.get(j).equals(currentCard)) {
                        frequency++;
                    }
                }

                // Update the most frequent card if the current one has a higher frequency
                if (frequency > maxFrequency) {
                    maxFrequency = frequency;
                    mostFrequent = currentCard;
                }
            }

            return mostFrequent;
        }
    }

    // Checks if a player has a card/cards with the given rank and returns all cards that match
    public ArrayList<Card> checkHand(String cardRank) {
        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < this.hand.size(); i++) {
            Card c = this.hand.get(i);
            if (c.getRank().equals(cardRank)) {
                cards.add(c);
            }
        }
        return cards;
    }

    // Checks if a player has a four-of-a-kind and returns the rank of their quad or null
    public Card checkQuad() {
        for (Card card : this.hand) {
            int count = 0;
            for (Card otherCard : this.hand) {
                if (card.getRank().equals(otherCard.getRank())) {
                    count++;
                }
            }
            if (count == 4) {
                return card; // Return one card of the quad.
            }
        }
        return null; // No quad found.
    }

    public void removeQuad(Player player, String cardRank) {
        for (int i = 0; i < player.getHand().size(); i++) {
            if (player.getHand().get(i).getRank().equals(cardRank)) {
                player.getHand().remove(i);
                i--;
            }
        }
    }

    // Sorts a player's hand with Ace being the smallest up to King
    public void sortHand() {
        // Remove null values first
        for (int i = 0; i < this.hand.size(); i++) {
            if (this.hand.get(i) == null) {
                this.hand.remove(i);
                i--; // Adjust index after removal.
            }
        }

        // Sorts based on sorting value
        int n = this.hand.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (getRankSortValue(this.hand.get(j).getRank()) < getRankSortValue(this.hand.get(minIndex).getRank())) {
                    minIndex = j;
                }
            }
            // Swap cards at i and minIndex
            swapCards(i, minIndex);
        }
    }

    // Swaps two card's places
    public void swapCards(int i, int j) {
        Card temp = this.hand.get(i);
        this.hand.set(i, this.hand.get(j));
        this.hand.set(j, temp);
    }

    // Gives each card a certain sorting value that indicates the order in which they should be sorted in
    public int getRankSortValue(String rank) {
        switch (rank) {
            case "A":
                return 14; // Ace is last
            case "K":
                return 13;
            case "Q":
                return 12;
            case "J":
                return 11;
            case "10":
                return 10;
            case "9":
                return 9;
            case "8":
                return 8;
            case "7":
                return 7;
            case "6":
                return 6;
            case "5":
                return 5;
            case "4":
                return 4;
            case "3":
                return 3;
            case "2":
                return 2;
            default: return 0;
        }
    }

    // Prints the name of a player, their points, and their hand
    @Override
    public String toString() {
        return this.name + " has " + this.points + " points\n" + this.name + "'s cards: " + this.hand;
    }
}
