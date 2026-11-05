package com.minigamelauncher.wordle;

import com.minigamelauncher.game.Game;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Wordle extends Game {

    String  secretWord;
    Guess[] guesses = new Guess[6];

    // param-constructor
    public Wordle(){
        super("Wordle", new Image(Wordle.class.getResourceAsStream("/com/minigamelauncher/minigamelauncher/wordle/wordle-icon.png")));
    }

    @Override
    public void launch() {
        System.out.println("[Wordle] launch method called.");

        if (Game.getIsRunning()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Game Running!");
            alert.setHeaderText(null);
            alert.setContentText("A game instance is already running!\nClose it and try again!");
            alert.showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/minigamelauncher/minigamelauncher/wordle/WordleMenu.fxml"));

            Stage stage = new Stage();
            stage.setTitle("Wordle Main Menu");
            stage.setResizable(false);
            stage.setScene(new Scene(loader.load()));
            stage.show();

            // if everything loads, then game can be assumed to be running well
            Game.startRunning();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


}
