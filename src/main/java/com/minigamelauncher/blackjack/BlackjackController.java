package com.minigamelauncher.blackjack;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.stage.Stage;

import java.io.IOException;

public class BlackjackController {
    Player player = new Player();
    Bot bot;
    int pCard = 1;
    int pGunI = 1;
    int bCard = 1;
    int bGunI = 1;

    @FXML
    ImageView pCard1;
    @FXML
    ImageView pCard2;
    @FXML
    ImageView pCard3;
    @FXML
    ImageView pCard4;
    @FXML
    ImageView pCard5;
    @FXML
    ImageView pCard6;
    @FXML
    ImageView pCard7;
    @FXML
    ImageView pCard8;
    @FXML
    ImageView pGun;

    @FXML
    ImageView bCard1;
    @FXML
    ImageView bCard2;
    @FXML
    ImageView bCard3;
    @FXML
    ImageView bCard4;
    @FXML
    ImageView bCard5;
    @FXML
    ImageView bCard6;
    @FXML
    ImageView bCard7;
    @FXML
    ImageView bCard8;
    @FXML
    ImageView bGun;

    @FXML
    Button btnHit;
    @FXML
    Button btnStay;
    @FXML
    Button btnPShoot;
    @FXML
    Button btnBShoot;

    @FXML
    Label lblScore;
    @FXML
    Label lblBotScore;

    public void hit() {
        Card nextCard = new Card(player.getDeck().draw());
        Image card1 = new Image(getClass().getResourceAsStream("/com/minigamelauncher/minigamelauncher/blackjack/"+nextCard.toString()));
        if (pCard == 1) {
            pCard1.setImage(card1);
            pCard++;
        } else if (pCard == 2) {
            pCard2.setImage(card1);
            pCard++;
        } else if (pCard == 3) {
            pCard3.setImage(card1);
            pCard++;
        } else if (pCard == 4) {
            pCard4.setImage(card1);
            pCard++;
        } else if (pCard == 5) {
            pCard5.setImage(card1);
            pCard++;
        } else if (pCard == 6) {
            pCard6.setImage(card1);
            pCard++;
        } else if (pCard == 7) {
            pCard7.setImage(card1);
            pCard++;
        } else if (pCard == 8) {
            pCard8.setImage(card1);
            pCard++;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("You Exceeded card limit");
            alert.setContentText("I did not think you would get here");
            alert.showAndWait();
        }
        player.addScore(nextCard.getScore());
        int score = player.getScore();
        lblScore.setText("Score: " + score);
        if(score>21) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("You Busted");
            alert.setContentText("You exceeded 21");
            alert.showAndWait();
            btnHit.setDisable(true);
        }
    }

    public void stay() {
        btnHit.setDisable(true);
        btnStay.setDisable(true);

        while(bot.algorithm()) {
            Card nextCard = new Card(bot.getDeck().draw());
            Image card1 = new Image(getClass().getResourceAsStream("/com/minigamelauncher/minigamelauncher/blackjack/"+nextCard.toString()));
            if (bCard == 1) {
                bCard1.setImage(card1);
                bCard++;
            } else if (bCard == 2) {
                bCard2.setImage(card1);
                bCard++;
            } else if (bCard == 3) {
                bCard3.setImage(card1);
                bCard++;
            } else if (bCard == 4) {
                bCard4.setImage(card1);
                bCard++;
            } else if (bCard == 5) {
                bCard5.setImage(card1);
                bCard++;
            } else if (bCard == 6) {
                bCard6.setImage(card1);
                bCard++;
            } else if (bCard == 7) {
                bCard7.setImage(card1);
                bCard++;
            } else if (bCard == 8) {
                bCard8.setImage(card1);
                bCard++;
            }
            else
                break;
            bot.addScore(nextCard.getScore());
            lblBotScore.setText("Score: " + bot.getScore());
        }

        roundEnd();
    }

    public void roundEnd() {
        if(player.getScore()>21 && bot.getScore()>21) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Draw");
            alert.setContentText("This round ended in a draw");
            alert.showAndWait();
            reset();
        }
        else if(player.getScore()>21) {
            lost();
        }
        else if(bot.getScore()>21) {
            won();
        }
        else if(player.getScore()< bot.getScore()) {
            lost();
        }
        else if(bot.getScore()< player.getScore()) {
            won();
        }
        else if(player.getScore() == bot.getScore()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Draw");
            alert.setContentText("This round ended in a draw");
            alert.showAndWait();
            reset();
        }
    }

    public void lost() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("You Lost");
        alert.setContentText("You lost this round.\nPress your shoot button");
        alert.showAndWait();
        btnPShoot.setDisable(false);
    }

    public void won() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("You Won");
        alert.setContentText("You won this round.\nPress the bots shoot button");
        alert.showAndWait();
        btnBShoot.setDisable(false);
    }

    public void reset() {
        btnPShoot.setDisable(true);
        btnBShoot.setDisable(true);
        btnHit.setDisable(false);
        btnStay.setDisable(false);
        pCard = 1;
        bCard = 1;
        player.setScore(0);
        player.getDeck().shuffle();
        bot.setScore(0);
        bot.getDeck().shuffle();

        Image Default = new Image(getClass().getResourceAsStream("/com/minigamelauncher/minigamelauncher/blackjack/placeholder.png"));
        pCard1.setImage(Default);
        pCard2.setImage(Default);
        pCard3.setImage(Default);
        pCard4.setImage(Default);
        pCard5.setImage(Default);
        pCard6.setImage(Default);
        pCard7.setImage(Default);
        pCard8.setImage(Default);

        bCard1.setImage(Default);
        bCard2.setImage(Default);
        bCard3.setImage(Default);
        bCard4.setImage(Default);
        bCard5.setImage(Default);
        bCard6.setImage(Default);
        bCard7.setImage(Default);
        bCard8.setImage(Default);

        lblBotScore.setText("Score: ");
        lblScore.setText("Score: ");
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    public void bShoot() throws IOException {
        boolean dead = bot.getGun().shoot();
        if(dead) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Bot Died");
            alert.setContentText("Congratulations You survived");
            alert.showAndWait();
            FXMLLoader fxmlLoader = new FXMLLoader(BlackjackMain.class.getResource("/com/minigamelauncher/minigamelauncher/blackjack/MainMenu.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 700,390);
            Stage stage = (Stage) btnHit.getScene().getWindow();
            stage.setResizable(false);
            stage.setTitle("Main Menu");
            stage.setScene(scene);
        }
        else {
            Image update = null;
            if(bGunI == 1) {
                update = new Image(getClass().getResourceAsStream("/com/minigamelauncher/minigamelauncher/blackjack/chamber5.png"));
            }
            else if(bGunI == 2) {
                update = new Image(getClass().getResourceAsStream("/com/minigamelauncher/minigamelauncher/blackjack/chamber4.png"));
            }
            else if(bGunI == 3) {
                update = new Image(getClass().getResourceAsStream("/com/minigamelauncher/minigamelauncher/blackjack/chamber3.png"));
            }
            else if(bGunI == 4) {
                update = new Image(getClass().getResourceAsStream("/com/minigamelauncher/minigamelauncher/blackjack/chamber2.png"));
            }
            else if(bGunI == 5) {
                update = new Image(getClass().getResourceAsStream("/com/minigamelauncher/minigamelauncher/blackjack/chamber1.png"));
            }
            bGun.setImage(update);
            bGunI++;
            reset();
        }
    }

    public void pShoot() throws IOException {
        boolean dead = player.getGun().shoot();
        if(dead) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("You Died");
            alert.setContentText("You Died");
            alert.showAndWait();
            FXMLLoader fxmlLoader = new FXMLLoader(BlackjackMain.class.getResource("/com/minigamelauncher/minigamelauncher/blackjack/MainMenu.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 700,390);
            Stage stage = (Stage) btnHit.getScene().getWindow();
//            stage.setResizable(false);
            stage.setTitle("Main Menu");
            stage.setScene(scene);
        }
        else {
            Image update = null;
            if(pGunI == 1) {
                update = new Image(getClass().getResourceAsStream("/com/minigamelauncher/minigamelauncher/blackjack/chamber5.png"));
            }
            else if(pGunI == 2) {
                update = new Image(getClass().getResourceAsStream("/com/minigamelauncher/minigamelauncher/blackjack/chamber4.png"));
            }
            else if(pGunI == 3) {
                update = new Image(getClass().getResourceAsStream("/com/minigamelauncher/minigamelauncher/blackjack/chamber3.png"));
            }
            else if(pGunI == 4) {
                update = new Image(getClass().getResourceAsStream("/com/minigamelauncher/minigamelauncher/blackjack/chamber2.png"));
            }
            else if(pGunI == 5) {
                update = new Image(getClass().getResourceAsStream("/com/minigamelauncher/minigamelauncher/blackjack/chamber1.png"));
            }
            pGun.setImage(update);
            pGunI++;
            reset();
        }
    }
}