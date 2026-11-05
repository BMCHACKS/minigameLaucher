package com.minigamelauncher;

import com.minigamelauncher.launcher.GameCollection;
import com.minigamelauncher.launcher.MinigameLauncher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuController {
    private MinigameLauncher launcher;

    public void setLauncher(MinigameLauncher launcher){ this.launcher = launcher; }

    @FXML
    private Button startBtn;

    @FXML
    private void handleExit(){
        System.out.println("[CUSTOM INFO] User exiting the program...");
        System.exit(0);
    }

    @FXML
    private void handleStart(){
        System.out.println("[CUSTOM INFO] User starting the program (going to game collection)...");
        try {
            System.out.println("[CUSTOM INFO] FXML being loaded...");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/minigamelauncher/minigamelauncher/GameCollection.fxml"));
            Stage stage       = (Stage) startBtn.getScene().getWindow();
            stage.setTitle("Game Collection");
            stage.setScene(new Scene(loader.load()));

            System.out.println("[CUSTOM INFO] ML passed to GameCollectionController...");

            GameCollectionController controller = loader.getController();
            controller.setLauncher(launcher);
            controller.loadGames(launcher.myGames);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
