package com.minigamelauncher.blackjack;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class BotMenuController {

    @FXML
    Button btnNext;
    @FXML
    Button btnBack;

    @FXML
    Label lblName;

    @FXML
    RadioButton rbtnBaby;
    @FXML
    RadioButton rbtnVEasy;
    @FXML
    RadioButton rbtnEasy;
    @FXML
    RadioButton rbtnMedium;
    @FXML
    RadioButton rbtnHard;
    @FXML
    RadioButton rbtnVHard;
    @FXML
    RadioButton rbtnExtreme;

    public void next() throws IOException {
        if(rbtnBaby.isSelected()||rbtnVEasy.isSelected()||rbtnEasy.isSelected()||rbtnMedium.isSelected()||rbtnHard.isSelected()||rbtnVHard.isSelected()||rbtnExtreme.isSelected()) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/minigamelauncher/minigamelauncher/blackjack/game.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1500, 800);
            BlackjackController controller = fxmlLoader.getController();


            if(rbtnBaby.isSelected())
                controller.setBot(new Baby());
            else if(rbtnVEasy.isSelected())
                controller.setBot(new VEasy());
            else if(rbtnEasy.isSelected())
                controller.setBot(new Easy());
            else if(rbtnMedium.isSelected())
                controller.setBot(new Medium());
            else if(rbtnHard.isSelected())
                controller.setBot(new Hard());
            else if(rbtnVHard.isSelected())
                controller.setBot(new VHard());
            else if(rbtnExtreme.isSelected())
                controller.setBot(new Extreme());

            Stage stage = (Stage) btnNext.getScene().getWindow();
            stage.setTitle("BlackJack Roulette");
            stage.setScene(scene);


        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("You have not selected the difficulty");
            alert.setContentText("Please select one of the difficulty options");
            alert.showAndWait();
        }
    }

    public void back() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BlackjackMain.class.getResource("/com/minigamelauncher/minigamelauncher/blackjack/MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700,390);
        Stage stage = (Stage) btnNext.getScene().getWindow();
        stage.setTitle("Main Menu");
        stage.setScene(scene);
    }

    public void setName(String name) {
        lblName.setText(name);
    }
}
