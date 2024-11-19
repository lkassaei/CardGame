import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cards;
    private int cardsLeft;

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
        shuffle();
    }

    public boolean isEmpty() {
        return this.cardsLeft == 0;
    }

    public int getCardsLeft() {
        return cardsLeft;
    }

    public Card deal() {
        if (isEmpty()) {
            return null;
        }
        int index = this.cardsLeft - 1;
        this.cardsLeft--;
        return this.cards.get(index);
    }

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
