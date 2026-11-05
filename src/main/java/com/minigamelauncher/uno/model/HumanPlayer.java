package com.minigamelauncher.uno.model;


public class HumanPlayer extends Player {
    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public Card chooseCard(Card topCard) {
        for (Card card : hand) {
            if (canPlay(card, topCard)) {
                return card;
            }
        }
        return null;
    }
}