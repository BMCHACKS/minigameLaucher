package com.minigamelauncher.flappybird;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    private Canvas gameCanvas;
    @FXML
    private StackPane root;

    private GraphicsContext gc;

    private static final double BOARD_WIDTH = 360;
    private static final double BOARD_HEIGHT = 640;

    private Image backgroundImg;
    private Bird bird;
    private PipeManager pipeManager;
    private GameState gameState;

    private SoundPlayer flapSound;
    private SoundPlayer hitSound;
    private SoundPlayer dieSound;
    private SoundPlayer pointSound;

    private boolean gameStarted = false;
    private boolean soundedOnDeath = false;

    private double pipeTimer = 0;
    private static final double PIPE_INTERVAL = 1.0;

    private AnimationTimer gameLoop;
    private long lastNano = -1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gc = gameCanvas.getGraphicsContext2D();

        try {
            backgroundImg = ImageLoader.load("/com/minigamelauncher/minigamelauncher/flappybird/flappybirdbg.png");
            Image birdImg = ImageLoader.load("/com/minigamelauncher/minigamelauncher/flappybird/flappybird.png");
            Image topPipeImg = ImageLoader.load("/com/minigamelauncher/minigamelauncher/flappybird/toppipe.png");
            Image bottomPipeImg = ImageLoader.load("/com/minigamelauncher/minigamelauncher/flappybird/bottompipe.png");

            bird = new Bird(BOARD_WIDTH / 8, BOARD_HEIGHT / 2, birdImg);
            pipeManager = new PipeManager(topPipeImg, bottomPipeImg, BOARD_WIDTH, BOARD_HEIGHT);

        } catch (ResourceLoadException e) {
            System.out.println("Could not load image: " + e.getResourcePath());
            return;
        }

        gameState = new GameState();
        flapSound = new SoundPlayer("/com/minigamelauncher/minigamelauncher/flappybird/flap.wav");
        hitSound = new SoundPlayer("/com/minigamelauncher/minigamelauncher/flappybird/hit.wav");
        dieSound = new SoundPlayer("/com/minigamelauncher/minigamelauncher/flappybird/die.wav");
        pointSound = new SoundPlayer("/com/minigamelauncher/minigamelauncher/flappybird/point.wav");

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastNano < 0) {
                    lastNano = now;
                    return;
                }
                double dt = Math.min((now - lastNano) / 1_000_000_000.0, 0.05);
                lastNano = now;
                if (gameStarted && !gameState.isGameOver()) {
                    update(dt);
                }
                draw();
            }
        };

        gameLoop.start();
    }

    private void update(double dt) {
        bird.update();

        pipeTimer += dt;
        if (pipeTimer >= PIPE_INTERVAL) {
            pipeTimer = 0;
            pipeManager.spawnPair();
        }

        int scored = pipeManager.updateAll(bird);
        if (scored > 0) {
//            pointSound.play();
            gameState.addScore(scored);
        }

        if (pipeManager.anyCollision(bird)) {
//            hitSound.play();
            gameState.setGameOver();
            soundedOnDeath = false;
        } else if (bird.isOutOfBounds(BOARD_HEIGHT)) {
            if (!soundedOnDeath) {
//                dieSound.play();
                soundedOnDeath = true;
            }
            gameState.setGameOver();
        }
    }

    private void draw() {
        gc.drawImage(backgroundImg, 0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        pipeManager.drawAll(gc);
        bird.draw(gc);

        if (!gameStarted) {
            drawStartScreen();
        } else if (gameState.isGameOver()) {
            drawGameOverScreen();
        } else {
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 32));
            gc.setTextAlign(TextAlignment.LEFT);
            gc.fillText(String.valueOf(gameState.getScore()), 10, 35);
        }
    }

    private void drawStartScreen() {
        gc.setFill(Color.YELLOW);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("FlappyBird", BOARD_WIDTH / 2, BOARD_HEIGHT / 2 - 80);

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        gc.fillText("by Wahid & Saad", BOARD_WIDTH / 2, BOARD_HEIGHT / 2 - 55);

        gc.setFill(Color.rgb(0, 0, 0, 0.45));
        gc.fillRoundRect(BOARD_WIDTH / 2 - 80, BOARD_HEIGHT / 2 - 22, 160, 45, 15, 15);

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        gc.fillText("SPACE to Start", BOARD_WIDTH / 2, BOARD_HEIGHT / 2 + 10);
    }

    private void drawGameOverScreen() {
        gc.setFill(Color.rgb(0, 0, 0, 0.45));
        gc.fillRoundRect(BOARD_WIDTH / 2 - 100, BOARD_HEIGHT / 2 - 70, 200, 150, 16, 16);

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Game Over", BOARD_WIDTH / 2, BOARD_HEIGHT / 2 - 30);

        gc.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        gc.fillText("Score: " + gameState.getScore(), BOARD_WIDTH / 2, BOARD_HEIGHT / 2 + 5);

        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        gc.setFill(Color.web("#F5C518"));
        gc.fillText("Best:  " + gameState.getBestScore(), BOARD_WIDTH / 2, BOARD_HEIGHT / 2 + 30);

        gc.setFill(Color.rgb(0, 0, 0, 0.45));
        gc.fillRoundRect(BOARD_WIDTH / 2 - 80, BOARD_HEIGHT / 2 + 45, 160, 40, 15, 15);

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        gc.fillText("SPACE to Retry", BOARD_WIDTH / 2, BOARD_HEIGHT / 2 + 70);
    }

    // No @FXML — called directly from Main.java via method reference
    public void handleKeyPress(KeyEvent e) {
        if (e.getCode() != KeyCode.SPACE)
            return;

        if (gameState.isGameOver()) {
            bird.reset();
            pipeManager.clear();
            gameState.reset();
            pipeTimer = 0;
            lastNano = -1;
            soundedOnDeath = false;
            return;
        }

        if (!gameStarted) {
            gameStarted = true;
        }

        bird.jump();
//        flapSound.play();
    }
}