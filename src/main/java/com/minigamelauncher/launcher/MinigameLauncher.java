package com.minigamelauncher.launcher;

import com.minigamelauncher.blackjack.BlackjackMain;
import com.minigamelauncher.chess.Chess;
import com.minigamelauncher.flappybird.FlappyBird;
import com.minigamelauncher.uno.HelloApplication;
import com.minigamelauncher.uno.HelloController;
import com.minigamelauncher.wordle.Wordle;

public class MinigameLauncher {

    public GameCollection myGames;

    public MinigameLauncher(){
        myGames = new GameCollection();

        /*
            Each GAME package needs to be added to the GameCollection
            in order to be displayed in the GameCollection GUI.
         */

        myGames.addGame(new Wordle());
        myGames.addGame(new BlackjackMain());
        myGames.addGame(new HelloApplication());
        myGames.addGame(new FlappyBird());
    }

}
