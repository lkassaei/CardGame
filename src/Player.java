import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Card> hand;
    private int points;

    public Player(String name) {
        this.name = name;
        this.points = 0;
    }

    public Player(String name, ArrayList<Card> hand) {
        this.name = name;
        this.hand = hand;
        this.points = 0;
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
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void addPoints(int points) {
        this.points = this.points + points;
    }

    public void addCard(Card card) {
        this.hand.add(card);
    }

    public void removeCard(Card card) {
        this.hand.remove(card);
    }

    public void findMostFrequentCard() {
        int repeats = 0;
        for (int i = 0; i < this.hand.size(); i++) {
            Card card = this.hand.get(i);
            for (int j = 0; j < this.hand.size() - 1; i++) {

            }
        }
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



    @Override
    public String toString() {
        return this.name + " has " + this.points + " points\n" + this.name + "'s cards: " + this.hand;
    }
}
