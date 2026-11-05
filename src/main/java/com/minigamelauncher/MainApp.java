package com.minigamelauncher;

import com.minigamelauncher.launcher.MinigameLauncher;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        System.out.println("[CUSTOM INFO] Creating the MinigameLaucher object...");

        MinigameLauncher ML = new MinigameLauncher();

        System.out.println("[CUSTOM INFO] Loading MainMenu.fxml file...");

        FXMLLoader f1 = new FXMLLoader(getClass().getResource("/com/minigamelauncher/minigamelauncher/MainMenu.fxml"));
        Parent root   = f1.load();

        System.out.println("[CUSTOM INFO] Passing ML to MainMenuController...");
        MainMenuController controller = f1.getController();
        controller.setLauncher(ML);

        Scene scene = new Scene(root);
        stage.setTitle("Main Menu");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
