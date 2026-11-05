package com.minigamelauncher.uno.model;


import java.util.ArrayList;
import java.util.Collections;
import com.minigamelauncher.uno.model.Card.Colour;
import com.minigamelauncher.uno.model.Card.Value;


public class Deck {
    private ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        buildDeck();
    }

    private void buildDeck() {
        Colour[] normalColours = {Colour.RED, Colour.BLUE, Colour.GREEN, Colour.YELLOW};

        for (Colour colour : normalColours) {
            cards.add(new Card(colour, Value.ZERO));

            Value[] values = {
                    Value.ONE, Value.TWO, Value.THREE, Value.FOUR, Value.FIVE,
                    Value.SIX, Value.SEVEN, Value.EIGHT, Value.NINE,
                    Value.DRAW_TWO, Value.REVERSE, Value.SKIP
            };

            for (Value value : values) {
                cards.add(new Card(colour, value));
                cards.add(new Card(colour, value));
            }
        }

        for (int i = 0; i < 4; i++) {
            cards.add(new Card(Colour.WILD, Value.WILD));
            cards.add(new Card(Colour.WILD, Value.WILD_DRAW_FOUR));
        }
    }

    public void shuffle() { Collections.shuffle(cards); }

    public Card drawCard() {
        if (cards.isEmpty()) return null;
        return cards.remove(0);
    }

    public void addCard(Card card) { cards.add(card); }
    public int size() { return cards.size(); }
}