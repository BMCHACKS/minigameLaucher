package com.minigamelauncher.blackjack;

import com.minigamelauncher.game.Game;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class MainMenuController {
    @FXML
    Button btnNext;
    @FXML
    Button btnQuit;

    @FXML
    TextField txtName;

    public void next() throws IOException {
        if (!(txtName.getText().trim().isEmpty())) {
            String name = txtName.getText();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/minigamelauncher/minigamelauncher/blackjack/botMenu.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            BotMenuController controller = fxmlLoader.getController();
            controller.setName(name);
            Stage stage = (Stage) btnNext.getScene().getWindow();
            stage.setTitle("Bot Menu");
            stage.setScene(scene);
        }
        else {
            txtName.clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("You have not entered your name");
            alert.setContentText("Please enter your name in the provided area");
            alert.showAndWait();
        }
    }

    public void quit() {
        System.out.println("[CUSTOM INFO] User exiting game...");
        Stage stage = (Stage) btnQuit.getScene().getWindow();
        stage.close();

        Game.stopRunning();
    }
}
