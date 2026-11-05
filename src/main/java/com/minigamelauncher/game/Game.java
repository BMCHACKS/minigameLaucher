package com.minigamelauncher.game;
import javafx.scene.image.Image;

public abstract class Game {
    private static int     gameNo    = 0;
    private static boolean isRunning = false;

    private String name;
    private Image  icon;
//    private String iconURL;

    // default-constructor
    public Game(){
        this("random-game-"+gameNo, new Image(Game.class.getResourceAsStream("/com/minigamelauncher/minigamelauncher/game/default-icon.png")));
    }

    public Game(String name){
        this(name, new Image(Game.class.getResourceAsStream("/com/minigamelauncher/minigamelauncher/game/default-icon.png")));
    }

    // parametrized-constructor
    public Game(String name, Image icon){
        this.name = name;
        this.icon = icon;

        gameNo++;
    }

    // other-methods

    public static void startRunning()    { isRunning = true;  }
    public static void stopRunning()     { isRunning = false; }
    public static boolean getIsRunning() { return  isRunning; }

    // the launch methods LAUNCHES the game implemeted by respective game
    public abstract void launch();

    public String getName() { return this.name; }
    public Image  getIcon() { return this.icon; }

//    public String geticonURL(){ return this.iconURL; }

    @Override
    public String toString(){
        return "[GAME] "+name+" ";
    }
}
