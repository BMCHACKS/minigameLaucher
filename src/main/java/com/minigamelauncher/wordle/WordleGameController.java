package com.minigamelauncher.wordle;

import com.minigamelauncher.game.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class WordleGameController {

    private ArrayList<String> words;

    private HintHelper HH;
    private int currentRow = 0;
    private int currentCol = 0;

    private String secretWord;
    private Label[][] grid;

    // all buttons
    @FXML private Button btn_Q, btn_W, btn_E, btn_R, btn_T, btn_Y, btn_U, btn_I, btn_O, btn_P;
    @FXML private Button btn_A, btn_S, btn_D, btn_F, btn_G, btn_H, btn_J, btn_K, btn_L;
    @FXML private Button btn_Z, btn_X, btn_C, btn_V, btn_B, btn_N, btn_M;

    @FXML
    private Button exitBtn;

    // cell labels
    @FXML private Label cell_0_0, cell_0_1, cell_0_2, cell_0_3, cell_0_4;
    @FXML private Label cell_1_0, cell_1_1, cell_1_2, cell_1_3, cell_1_4;
    @FXML private Label cell_2_0, cell_2_1, cell_2_2, cell_2_3, cell_2_4;
    @FXML private Label cell_3_0, cell_3_1, cell_3_2, cell_3_3, cell_3_4;
    @FXML private Label cell_4_0, cell_4_1, cell_4_2, cell_4_3, cell_4_4;
    @FXML private Label cell_5_0, cell_5_1, cell_5_2, cell_5_3, cell_5_4;

    @FXML
    public void initialize() {
        grid = new Label[][]{
            {cell_0_0, cell_0_1, cell_0_2, cell_0_3, cell_0_4},
            {cell_1_0, cell_1_1, cell_1_2, cell_1_3, cell_1_4},
            {cell_2_0, cell_2_1, cell_2_2, cell_2_3, cell_2_4},
            {cell_3_0, cell_3_1, cell_3_2, cell_3_3, cell_3_4},
            {cell_4_0, cell_4_1, cell_4_2, cell_4_3, cell_4_4},
            {cell_5_0, cell_5_1, cell_5_2, cell_5_3, cell_5_4}
        };

        // loading a random word from words.txt
        try {
            InputStream    is     = getClass().getResourceAsStream("/com/minigamelauncher/minigamelauncher/wordle/words.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            words                 = new ArrayList<>(reader.lines().collect(Collectors.toList()));

            secretWord = words.get((int) (Math.random() * words.size())).toLowerCase().trim();
            // setting hinthelper
            HH = new HintHelper(secretWord);

            System.out.println("[Wordle] Secret word loaded: "+secretWord);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExit(){
        System.out.println("[CUSTOM INFO] User exiting game...");
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();

        Game.stopRunning();
    }

    @FXML
    private void handleHint(){
        Alert alter = new Alert(Alert.AlertType.INFORMATION);
        alter.setTitle("HINT!");
        alter.setHeaderText(null);
        alter.setContentText(HH.getHint());
        alter.showAndWait();
    }

    @FXML
    private void handleReset(){
        System.out.println("[Wordle] Resetting the game...");

        Alert alter = new Alert(Alert.AlertType.CONFIRMATION);
        alter.setTitle("Reset?");
        alter.setHeaderText(null);
        alter.setContentText("Are you sure you want to reset & play again?");

        Optional<ButtonType> result = alter.showAndWait();

        if (!(result.isPresent() && result.get() == ButtonType.OK))
            return;

        // otherwise reset
        for (int row = 0; row <= 5; row++){
            for (int col = 0; col <= 4; col++){
                grid[row][col].setText("");
                grid[row][col].setStyle("-fx-background-color: white; -fx-border-color: #cccccc; -fx-border-width: 2px; -fx-font-weight: bold; -fx-font-size: 29px; -fx-alignment: center;");
            }
        }

        // handling keyboard GUI's reset
        String[] letters = {"Q","W","E","R","T","Y","U","I","O","P",
                "A","S","D","F","G","H","J","K","L",
                "Z","X","C","V","B","N","M"};

        for (String letter : letters) {
            Button keyBtn = getKeyButton(letter);
            if (keyBtn != null)
                keyBtn.setStyle("");
        }

        currentRow = 0;
        currentCol = 0;

        initialize();
    }

    @FXML
    private void handleKeyPress(ActionEvent event){
        if (currentRow > 5)
            return;

        if (currentCol >= 5)
            return;

        // we want to see WHICH button actually was clicked
        Button btn    = (Button) event.getSource();
        String letter = btn.getText();

        System.out.println("[Wordle] Key Pressed: "+letter);

        grid[currentRow][currentCol].setText(letter);
        currentCol++; // go to next box
    }

    @FXML
    private void handleBackspace(){
        if (currentCol <= 0 )
            return;

        currentCol--;
        grid[currentRow][currentCol].setText("");

        System.out.println("[Wordle] Key Pressed: ⌫");
    }

    @FXML
    private void handleEnter(){
        if (currentRow > 5)
            return;

        if (currentCol < 5)
            return; // word's length is not 5 yet

        String guessWord = "";
        Guess  guess;

        for (int i = 0; i < 5; i++){
            guessWord += grid[currentRow][i].getText();
        }

        guess = new Guess(guessWord);
        if (guess.isWordValid(words)){
            guess.evaluate(secretWord);
            colorRow(guess);
            currentRow++;
            currentCol = 0;
        } else {
            return;
        }

        // also check WIN or LOSE after each enter
        int winCount = 0;
        for (int x : guess.getTileColor())
            winCount += x;

        if (winCount == 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("You Win!");
            alert.setHeaderText(null);
            alert.setContentText("Congratulations! You guessed the word! :)");
            alert.showAndWait();

            this.handleReset();
        } else if (currentRow > 5){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("You Lose! :(");
            alert.setHeaderText(null);
            alert.setContentText("The word was: "+secretWord);
            alert.showAndWait();

            this.handleReset();
        }

        System.out.println("[Wordle] Key Pressed: ENTER");
    }

    private void colorRow(Guess guess){
        int[] tileColors = guess.getTileColor();
        String guessWord = guess.getWord().toUpperCase();

        // update ROW's COLOR
        for (int i = 0; i < 5; i++){
            Label cell = grid[currentRow][i];

            switch (tileColors[i]){
                case Guess.GREEN  : cell.setStyle("-fx-background-color: #6aaa64; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 29px; -fx-alignment: center;"); break;
                case Guess.YELLOW : cell.setStyle("-fx-background-color: #c9b458; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 29px; -fx-alignment: center;"); break;
                default           : cell.setStyle("-fx-background-color: #787c7e; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 29px; -fx-alignment: center;"); break;
            }
        }

        // update the keyboard's color as well

        for (int i = 0; i < 5; i++) {
            String letter = String.valueOf(guessWord.charAt(i));
            Button keyBtn = getKeyButton(letter);

            if (keyBtn == null) continue;

            String current = keyBtn.getStyle();
            if (current.contains("#6aaa64")) continue; // already green, don't downgrade

            switch (tileColors[i]) {
                case Guess.GREEN  : keyBtn.setStyle("-fx-background-color: #6aaa64; -fx-text-fill: white; -fx-font-weight: bold;"); break;
                case Guess.YELLOW :
                    if (!current.contains("#c9b458"))
                        keyBtn.setStyle("-fx-background-color: #c9b458; -fx-text-fill: white; -fx-font-weight: bold;");
                    break;
                default           : keyBtn.setStyle("-fx-background-color: #787c7e; -fx-text-fill: white; -fx-font-weight: bold;"); break;
            }
        }


    }

    private Button getKeyButton(String letter) {
        if (letter.equals("Q")) return btn_Q;
        if (letter.equals("W")) return btn_W;
        if (letter.equals("E")) return btn_E;
        if (letter.equals("R")) return btn_R;
        if (letter.equals("T")) return btn_T;
        if (letter.equals("Y")) return btn_Y;
        if (letter.equals("U")) return btn_U;
        if (letter.equals("I")) return btn_I;
        if (letter.equals("O")) return btn_O;
        if (letter.equals("P")) return btn_P;
        if (letter.equals("A")) return btn_A;
        if (letter.equals("S")) return btn_S;
        if (letter.equals("D")) return btn_D;
        if (letter.equals("F")) return btn_F;
        if (letter.equals("G")) return btn_G;
        if (letter.equals("H")) return btn_H;
        if (letter.equals("J")) return btn_J;
        if (letter.equals("K")) return btn_K;
        if (letter.equals("L")) return btn_L;
        if (letter.equals("Z")) return btn_Z;
        if (letter.equals("X")) return btn_X;
        if (letter.equals("C")) return btn_C;
        if (letter.equals("V")) return btn_V;
        if (letter.equals("B")) return btn_B;
        if (letter.equals("N")) return btn_N;
        if (letter.equals("M")) return btn_M;
        return null;
    }

    public void setupKeyboardInput(Scene scene) {
        scene.setOnKeyPressed((KeyEvent event) -> {
            String key = event.getCode().toString();

            switch (key) {
                case "ENTER":
                    handleEnter();
                    break;
                case "BACK_SPACE":
                    handleBackspace();
                    break;
                default:
                    if (key.length() == 1 && key.matches("[A-Z]")) {
                        if (currentRow <= 5 && currentCol < 5) {
                            grid[currentRow][currentCol].setText(key);
                            currentCol++;
                            System.out.println("[Wordle] Key Typed: " + key);
                        }
                    }
                    break;
            }
        });
    }

}
