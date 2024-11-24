import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Card> hand;
    private int points;
    private ArrayList<String> quads;

    public Player(String name) {
        this.name = name;
        this.points = 0;
        this.quads = new ArrayList<String>();
    }

    public Player(String name, ArrayList<Card> hand) {
        this.name = name;
        this.hand = hand;
        this.points = 0;
        this.quads = new ArrayList<String>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public int getPoints() {
        return this.points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void addPoints(int points) {
        this.points = this.points + points;
    }

    public ArrayList<String> getQuads() {
        return this.quads;
    }

    public void addQuad(String cardRank) {
        this.quads.add(cardRank);
    }

    public void addCard(Card card) {
        this.hand.add(card);
    }

    public void removeCard(Card card) {
        this.hand.remove(card);
    }

    public Card findMostFrequentCard() {
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

    public Card checkQuad() {
        for (int i = 0; i < hand.size(); i++) {
            String currentRank = hand.get(i).getRank();
            int count = 0;

            // Count occurrences of the current card's rank
            for (int j = 0; j < hand.size(); j++) {
                if (hand.get(j).getRank().equals(currentRank)) {
                    count++;
                }
            }

            // If the card appears 4 times, return that card
            if (count == 4) {
                return hand.get(i);  // Return the card that forms the four-of-a-kind
            }
        }

        // If no Four of a Kind is found, return null
        return null;
    }

    public void sortHand() {
        int n = this.hand.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            // Find the card with the smallest rank value from i to n
            for (int j = i + 1; j < n; j++) {
                if (getRankSortValue(this.hand.get(j).getRank()) < getRankSortValue(this.hand.get(minIndex).getRank())) {
                    minIndex = j;
                }
            }
            // Swap the cards at i and minIndex
            swapCards(i, minIndex);
        }
    }

    public void swapCards(int i, int j) {
        Card temp = this.hand.get(i);
        this.hand.set(i, this.hand.get(j));
        this.hand.set(j, temp);
    }

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

    @Override
    public String toString() {
        return this.name + " has " + this.points + " points\n" + this.name + "'s cards: " + this.hand;
    }
}
