package at.technikum.mtcg.models;

import java.util.ArrayList;
import java.util.List;

public class Stack {
    private final List<Card> cards;

    public Stack() {
        this.cards = new ArrayList<>();
    }

    public List<Card> getCards() {
        return cards;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }
}
