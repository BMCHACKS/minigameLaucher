package com.minigamelauncher.launcher;

import com.minigamelauncher.game.Game;
import java.util.ArrayList;

public class GameCollection {
    private ArrayList<Game> games = new ArrayList<>();

    // aggregate game to games collection
    public void addGame(Game newGame){
        try{
            games.add(newGame);
            System.out.println("[CUSTOM INFO] "+newGame+" has been added!");
        } catch (Exception e){
            System.out.println("[CUSTOM INFO] "+newGame+" unable to be added!");
            System.out.println(e.getMessage());
        }
    }

    // removing game based on game's name
    public void removeGame(String gameToRemove){
        try {
            for (int i = 0; i < games.size(); i++) {
                if (games.get(i).getName().equals(gameToRemove)) {
                    games.remove(i);
                    break;
                }
            }
            System.out.println("[CUSTOM INFO] " + gameToRemove + " has been removed!");
        } catch (Exception e) {
            System.out.println("[CUSTOM INFO] "+gameToRemove+" unable to be removed!");
            System.out.println(e.getMessage());
        }
    }

    // getters

    public ArrayList<Game> getGames(){ return games; }

    // other methods
    @Override
    public String toString(){
        return "[GameCollection] "+games;
    }


}
