package com.minigamelauncher.blackjack;

import com.minigamelauncher.game.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.Random;
import java.io.IOException;

public class BlackjackMain extends Game {

    public BlackjackMain(){
        super("Black Jack", new Image(BlackjackMain.class.getResourceAsStream("/com/minigamelauncher/minigamelauncher/blackjack/black-jack-icon.png")));
    }

    @Override
    public void launch(){
        FXMLLoader fxmlLoader = new FXMLLoader(BlackjackMain.class.getResource("/com/minigamelauncher/minigamelauncher/blackjack/MainMenu.fxml"));

        if (Game.getIsRunning()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Game Running!");
            alert.setHeaderText(null);
            alert.setContentText("A game instance is already running!\nClose it and try again!");
            alert.showAndWait();
            return;
        }

        try {
            Scene scene = new Scene(fxmlLoader.load(), 700,390);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setTitle("Main Menu");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

            Game.startRunning();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

//class Main {
//    public static void main(String[] args) {
//        Deck d1 = new Deck();
//
//        for (Card c:d1.getDeck()) {
//            System.out.println(c);
//        }
//        System.out.println();
//
//        d1.shuffle();
//
//        for (Card c:d1.getDeck()) {
//            System.out.println(c);
//        }
//    }
//}

//interface playable {
//    void hit();
//    void stay();
//}

class Card {
    private String face;
    private String suit;
    private int score;

    public Card(String face, String suit, int score) {
        this.face = face;
        this.suit = suit;
        this.score = score;
    }

    public Card(Card other) {
        this.face = other.getFace();
        this.suit = other.getSuit();
        this.score = other.getScore();
    }

    public String getFace() {
        return face;
    }
    public void setFace(String face) {
        this.face = face;
    }

    public String getSuit() {
        return suit;
    }
    public void setSuit(String suit) {
        this.suit = suit;
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return face + "_of_" + suit + ".png";
    }
}

class Deck {
    private Card[] deck = new Card[52];
    private int index = 0;
    private final String[] suits = {"spades", "hearts", "diamonds", "clubs"};
    private final String[] face = {"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};
    private int[] score = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};

    public Deck() {
        int x = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                deck[x] = new Card(face[j], suits[i], score[j]);
                x++;
            }
        }
    }

    public int cheat() {
        return deck[index].getScore();
    }

    public void shuffle() {
        Random rand = new Random();

        for (int i = 0; i < 52; i++) {
            int random = rand.nextInt(52);
            Card temp;
            temp = deck[i];
            deck[i] = deck[random];
            deck[random] = temp;
        }

        index = 0;
    }

    public Card draw() {
        Card c = deck[index];
        index++;
        return c;
    }

    public Card[] getDeck() {
        return deck;
    }

    public void setDeck(Card[] deck) {
        this.deck = deck;
    }

    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
}

class Gun {
    private int bullet;
    private int index;

    public Gun() {
        Random rand = new Random();
        bullet = rand.nextInt(6);
        index = 0;
    }

    public boolean shoot() {
        if(bullet == index)
            return true;
        else {
            index++;
            return false;
        }
    }

    public int getBullet() {
        return bullet;
    }
    public void setBullet(int bullet) {
        this.bullet = bullet;
    }

    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
}

class Player {
    private int score;
    private Deck deck;
    private Gun gun;

    public Player() {
        score = 0;
        deck = new Deck();
        gun = new Gun();
        deck.shuffle();
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }

    public Deck getDeck() {
        return deck;
    }
    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Gun getGun() {
        return gun;
    }
    public void setGun(Gun gun) {
        this.gun = gun;
    }

    public void addScore(int score) {
        this.score += score;
    }
}

abstract class Bot {
    private int score;
    private Deck deck;
    private Gun gun;

    public Bot() {
        score = 0;
        deck = new Deck();
        gun = new Gun();
        deck.shuffle();
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }

    public Deck getDeck() {
        return deck;
    }
    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Gun getGun() {
        return gun;
    }
    public void setGun(Gun gun) {
        this.gun = gun;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public abstract Boolean algorithm();
}

class Baby extends Bot {
    private int cards;

    public Baby() {
        super();
        cards = 0;
    }

    @Override
    public Boolean algorithm() {
        if(cards<3) {
            cards++;
            return true;
        }
        else {
            cards = 0;
            return false;
        }
    }
}

class VEasy extends Bot {
    public VEasy() {
        super();
    }

    @Override
    public Boolean algorithm() {
        if(super.getScore()<15)
            return true;
        else
            return false;
    }
}

class Easy extends Bot {
    private int porbability;
    public Easy() {
        super();
        porbability = 0;
    }

    @Override
    public Boolean algorithm() {
        if(super.getScore()<18)
            return true;
        else
            return false;
    }
}

class Medium extends Bot {
    private Random rand;
    public Medium() {
        super();
        rand = new Random();
    }

    @Override
    public Boolean algorithm() {
        int cheat = rand.nextInt(10);
        int roll = rand.nextInt(2);

        if (super.getScore() < 10) {
            return true;
        }
        else if (cheat == 0) {
            if (super.getDeck().cheat() + super.getScore() < 21)
                return true;
        }
        else if (super.getScore() > 15) {
            return false;
        }
        else {
            if (roll == 1)
                return true;
            else
                return false;
        }
        return false;
    }
}

class Hard extends Bot{
    private Random rand;
    public Hard() {
        super();
        rand = new Random();
    }

    @Override
    public Boolean algorithm() {
        int cheat = rand.nextInt(5);
        int roll = rand.nextInt(2);

        if (super.getScore() < 10) {
            return true;
        }
        else if (cheat == 0) {
            if (super.getDeck().cheat() + super.getScore() < 21)
                return true;
        }
        else if (super.getScore() > 15) {
            return false;
        }
        else {
            if (roll == 1)
                return true;
            else
                return false;
        }
        return false;
    }
}

class VHard extends Bot{
    private Random rand;
    public VHard() {
        super();
        rand = new Random();
    }

    @Override
    public Boolean algorithm() {
        int cheat = rand.nextInt(3);
        int roll = rand.nextInt(2);

        if (super.getScore() < 10) {
            return true;
        }
        else if (cheat == 0) {
            if (super.getDeck().cheat() + super.getScore() < 21)
                return true;
        }
        else if (super.getScore() > 15) {
            return false;
        }
        else {
            if (roll == 1)
                return true;
            else
                return false;
        }
        return false;
    }
}

class Extreme extends Bot {
    public Extreme() {
        super();
    }

    @Override
    public Boolean algorithm() {
        System.out.println("Card cheat: " + super.getDeck().cheat());
        if (super.getDeck().cheat() + super.getScore() < 21)
            return true;
        else
            return false;
    }
}
