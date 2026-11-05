package com.minigamelauncher.flappybird;

import com.minigamelauncher.game.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class FlappyBird extends Game {

    public FlappyBird(){
        super("Flappy-Bird", new Image(FlappyBird.class.getResourceAsStream("/com/minigamelauncher/minigamelauncher/flappybird/flappy-bird-icon.png")));
    }

    @Override
    public void launch(){

        if (Game.getIsRunning()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Game Running!");
            alert.setHeaderText(null);
            alert.setContentText("A game instance is already running!\nClose it and try again!");
            alert.showAndWait();
            return;
        }

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/minigamelauncher/minigamelauncher/flappybird/game.fxml"));
            Parent root = loader.load();
            GameController controller = loader.getController();

            Scene scene = new Scene(root, 360, 640);
            Stage stage = new Stage();

            // Attach key listener directly to the scene
            scene.setOnKeyPressed(controller::handleKeyPress);

            stage.setTitle("Flappy Bird");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            // Force focus onto the scene after window is shown
            root.requestFocus();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}