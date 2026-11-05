package com.minigamelauncher.uno.model;

public class Card {
    public enum Colour {
        RED, BLUE, GREEN, YELLOW, WILD;
    }

    public enum Value {
        ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, DRAW_TWO, SKIP, REVERSE, WILD, WILD_DRAW_FOUR;
    }

    private Colour colour;
    private Value value;

    public Card(Colour colour, Value value) {
        this.colour = colour;
        this.value = value;
    }

    public Colour getColour() { return colour; }
    public Value getValue() { return value; }
    public void setColour(Colour colour) { this.colour = colour; }

    public boolean isWildCard() {
        return value == Value.WILD || value == Value.WILD_DRAW_FOUR;
    }

    public boolean isActionCard() {
        return value == Value.DRAW_TWO || value == Value.REVERSE || value == Value.SKIP;
    }

    @Override
    public String toString() {
        return colour + " " + value;
    }
}