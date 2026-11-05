package com.minigamelauncher.flappybird;

public class GameState {

    private boolean gameOver = false;
    private int score = 0;
    private int bestScore = 0;

    public boolean isGameOver() {
        return gameOver;
    }

    public int getScore() {
        return score;
    }

    public int getBestScore() {
        return bestScore;
    }

    public void addScore(int amount) {
        score += amount;
        if (score > bestScore) {
            bestScore = score;
        }
    }

    public void setGameOver() {
        gameOver = true;
    }

    public void reset() {
        gameOver = false;
        score = 0;
    }
}
