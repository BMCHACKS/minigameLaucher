package com.minigamelauncher.uno;

import com.minigamelauncher.uno.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class HelloController {

    @FXML private HBox botHandBox;
    @FXML private HBox playerHandBox;
    @FXML private HBox centerBox;
    @FXML private Label topCardLabel;
    @FXML private Label statusLabel;
    @FXML private Button drawButton;

    private GameManager game;

    @FXML
    public void initialize() {
        game = new GameManager("You", "Bot");
        updateUI();
    }

    private String getImageName(Card card) {
        if (card.getValue() == Card.Value.WILD) {
            return "WILD.jpg";
        }
        if (card.getValue() == Card.Value.WILD_DRAW_FOUR) {
            return "WILD_DRAW_FOUR.jpg";
        }

        String colour = card.getColour().toString();

        String value = "";
        switch (card.getValue()) {
            case ZERO: value = "0"; break;
            case ONE: value = "1"; break;
            case TWO: value = "2"; break;
            case THREE: value = "3"; break;
            case FOUR: value = "4"; break;
            case FIVE: value = "5"; break;
            case SIX: value = "6"; break;
            case SEVEN: value = "7"; break;
            case EIGHT: value = "8"; break;
            case NINE: value = "9"; break;
            case SKIP: value = "SKIP"; break;
            case REVERSE: value = "REVERSE"; break;
            case DRAW_TWO: value = "DRAW_TWO"; break;
            default: value = ""; break;
        }

        return colour + "_" + value + ".jpg";
    }

    private ImageView loadCardImage(String imageName, double width, double height) {
        String path = "/com/minigamelauncher/minigamelauncher/uno/cards/" + imageName;
        Image image = new Image(getClass().getResourceAsStream(path));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    private void updateUI() {
        Card top = game.getTopCard();
        ImageView topCardImage = loadCardImage(getImageName(top), 80, 120);

        centerBox.getChildren().clear();
        centerBox.getChildren().addAll(topCardImage, drawButton);

        if (game.isGameOver()) {
            statusLabel.setText("🎉 " + game.getWinner() + " wins!");
        } else {
            statusLabel.setText(game.isHumanTurn() ? "Your Turn" : "Bot's Turn");
        }

        botHandBox.getChildren().clear();
        for (int i = 0; i < game.getBotPlayer().getHand().size(); i++) {
            ImageView card = loadCardImage("BACK.png", 50, 75);
            botHandBox.getChildren().add(card);
        }

        playerHandBox.getChildren().clear();
        for (Card card : game.getHumanPlayer().getHand()) {
            ImageView cardImage = loadCardImage(getImageName(card), 70, 105);
            StackPane cardPane = new StackPane(cardImage);
            cardPane.setOnMouseEntered(e -> cardPane.setTranslateY(-10));
            cardPane.setOnMouseExited(e -> cardPane.setTranslateY(0));
            cardPane.setOnMouseClicked(e -> handleCardPlay(card));
            playerHandBox.getChildren().add(cardPane);
        }
    }

    private void handleCardPlay(Card card) {
        if (!game.isGameOver() && game.isHumanTurn()) {
            boolean played = game.playCard(card);
            if (!played) {
                statusLabel.setText("❌ Can't play that card!");
            } else {
                updateUI();
            }
        }
    }

    @FXML
    private void handleDrawCard() {
        if (!game.isGameOver() && game.isHumanTurn()) {
            game.humanDrawCard();
            updateUI();
        }
    }
}