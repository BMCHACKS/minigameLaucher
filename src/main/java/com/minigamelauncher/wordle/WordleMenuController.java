package com.minigamelauncher.wordle;

import com.minigamelauncher.game.Game;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class WordleMenuController {
    @FXML
    private Button exitBtn;

    @FXML
    private Button playBtn;

    @FXML
    private void handleExit(){
        System.out.println("[CUSTOM INFO] User exiting game...");
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();

        Game.stopRunning();
    }

    @FXML
    private void handlePlay(){
        System.out.println("[CUSTOM INFO] User has launched Wordle...");
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/minigamelauncher/minigamelauncher/wordle/WordleGame.fxml"));

            Parent root = loader.load();
            Scene scene = new Scene(root);

            WordleGameController controller = loader.getController();
            controller.setupKeyboardInput(scene);

            Stage stage = (Stage) playBtn.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
