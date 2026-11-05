package com.minigamelauncher.uno.model;


import java.util.ArrayList;

public abstract class Player {
    protected String name;
    protected ArrayList<Card> hand;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public void addCard(Card card) { hand.add(card); }
    public void removeCard(Card card) { hand.remove(card); }
    public ArrayList<Card> getHand() { return hand; }
    public String getName() { return name; }
    public boolean hasWon() { return hand.isEmpty(); }

    public boolean canPlay(Card card, Card topCard) {
        return card.getColour() == topCard.getColour() ||
                card.getValue() == topCard.getValue() ||
                card.isWildCard();
    }

    public abstract Card chooseCard(Card topCard);
}