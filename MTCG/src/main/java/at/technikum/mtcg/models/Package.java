package at.technikum.mtcg.models;

import java.util.ArrayList;
import java.util.List;

public class Package {
    private final List<Card> cards;

    public Package() {
        this.cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public List<Card> getCards() {
        return cards;
    }
}
