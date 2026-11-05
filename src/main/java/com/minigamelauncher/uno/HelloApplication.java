package com.minigamelauncher.uno;

import com.minigamelauncher.game.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Game {

    public HelloApplication(){
        super("uno", new Image(HelloApplication.class.getResourceAsStream("/com/minigamelauncher/minigamelauncher/uno/uno-game-icon.png")));
    }

    @Override
    public void launch(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/minigamelauncher/minigamelauncher/uno/hello-view.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            Stage stage = new Stage();

            stage.setTitle("UNO");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}