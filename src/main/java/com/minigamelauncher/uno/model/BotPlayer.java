package com.minigamelauncher.uno.model;


public class BotPlayer extends Player {
    public BotPlayer(String name) {
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