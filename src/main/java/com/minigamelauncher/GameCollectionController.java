package com.minigamelauncher;

import com.minigamelauncher.game.Game;
import com.minigamelauncher.launcher.GameCollection;
import com.minigamelauncher.launcher.MinigameLauncher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class GameCollectionController {
    private MinigameLauncher launcher;

    public void setLauncher(MinigameLauncher launcher){ this.launcher = launcher; }

    @FXML
    private FlowPane gameGrid;

    @FXML
    public void handleBack() {
        System.out.println("[CUSTOM INFO] User going back to main menu...");
        try {
            System.out.println("[CUSTOM INFO] FXML being loaded...");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/minigamelauncher/minigamelauncher/MainMenu.fxml"));
            Stage stage       = (Stage) gameGrid.getScene().getWindow();

            stage.setScene(new Scene(loader.load()));

            System.out.println("[CUSTOM INFO] ML passed to MainMenuController...");

            MainMenuController controller = loader.getController();
            controller.setLauncher(launcher);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadGames(GameCollection collection){
        System.out.println("[CUSTOM INFO] Games being loaded to gameGrid");
        for (Game game : collection.getGames()){
            ImageView card = new ImageView(game.getIcon());

            card.setFitHeight(200);
            card.setFitWidth(200);
            card.setPreserveRatio(true);
            card.getStyleClass().add("game-card");
            card.setStyle("-fx-cursor: hand");
            card.setOnMouseClicked(e -> game.launch());

            gameGrid.getChildren().add(card);
        }
    }
}
