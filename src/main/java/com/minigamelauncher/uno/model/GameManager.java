package com.minigamelauncher.uno.model;


import java.util.ArrayList;

public class GameManager {
    private Deck deck;
    private ArrayList<Card> discardPile;
    private HumanPlayer humanPlayer;
    private BotPlayer botPlayer;
    private boolean isHumanTurn;
    private boolean gameOver;
    private String winner;

    public GameManager(String humanName, String botName) {
        deck = new Deck();
        deck.shuffle();
        discardPile = new ArrayList<>();
        humanPlayer = new HumanPlayer(humanName);
        botPlayer = new BotPlayer(botName);
        isHumanTurn = true;
        gameOver = false;
        dealCards();
        startFirstCard();
    }

    private void dealCards() {
        for (int i = 0; i < 7; i++) {
            humanPlayer.addCard(deck.drawCard());
            botPlayer.addCard(deck.drawCard());
        }
    }

    private void startFirstCard() {
        Card firstCard = deck.drawCard();
        while (firstCard.isWildCard()) {
            deck.addCard(firstCard);
            deck.shuffle();
            firstCard = deck.drawCard();
        }
        discardPile.add(firstCard);
    }

    public Card getTopCard() {
        return discardPile.get(discardPile.size() - 1);
    }

    public boolean playCard(Card card) {
        if (!humanPlayer.canPlay(card, getTopCard())) {
            return false;
        }
        humanPlayer.removeCard(card);
        discardPile.add(card);
        if (humanPlayer.hasWon()) {
            gameOver = true;
            winner = humanPlayer.getName();
            return true;
        }
        applyCardEffect(card, botPlayer);
        if (!gameOver) {
            botTurn();
        }
        return true;
    }

    public void humanDrawCard() {
        Card drawn = deck.drawCard();
        if (drawn != null) {
            humanPlayer.addCard(drawn);
        }
        botTurn();
    }

    private void botTurn() {
        Card topCard = getTopCard();
        Card chosen = botPlayer.chooseCard(topCard);
        if (chosen != null) {
            botPlayer.removeCard(chosen);
            discardPile.add(chosen);
            if (botPlayer.hasWon()) {
                gameOver = true;
                winner = botPlayer.getName();
                return;
            }
            applyCardEffect(chosen, humanPlayer);
        } else {
            Card drawn = deck.drawCard();
            if (drawn != null) {
                botPlayer.addCard(drawn);
            }
        }
    }

    private void applyCardEffect(Card card, Player opponent) {
        switch (card.getValue()) {
            case DRAW_TWO:
                for (int i = 0; i < 2; i++) {
                    Card drawn = deck.drawCard();
                    if (drawn != null) opponent.addCard(drawn);
                }
                break;
            case WILD_DRAW_FOUR:
                for (int i = 0; i < 4; i++) {
                    Card drawn = deck.drawCard();
                    if (drawn != null) opponent.addCard(drawn);
                }
                break;
            default:
                break;
        }
    }

    public HumanPlayer getHumanPlayer() { return humanPlayer; }
    public BotPlayer getBotPlayer() { return botPlayer; }
    public boolean isHumanTurn() { return true; }
    public boolean isGameOver() { return gameOver; }
    public String getWinner() { return winner; }
}