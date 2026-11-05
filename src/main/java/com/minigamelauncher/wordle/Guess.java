package com.minigamelauncher.wordle;

import java.util.ArrayList;

public class Guess {
    public static final int GREEN  = 0;
    public static final int YELLOW = 1;
    public static final int GREY   = 2;

    private String word;
    private int[]  tileColor;

    /*
        Array tileColor will contain
        0 - represent GREEN  (word in correct location)
        1 - represent YELLOW (char valid but in wrong location)
        2 - represent GREY   (char not in secretWord)
     */

    Guess(String word){
        this.word = word.toLowerCase();
        tileColor = new int[5];
    }

    // getters

    public String getWord()     { return word;      }
    public int[]  getTileColor(){ return tileColor; }

    // other-method
    public boolean isWordValid(ArrayList<String> givenWords){
        // checking if for a given ArrayList, our GUESS word exists or not...

        for (String s : givenWords){
            if (s.equals(word))
                return true;
        }

        return false;
    }


    public void evaluate(String secretWord) {

        boolean[] used = new boolean[word.length()];

        // FIRST CHECK GREEN
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == secretWord.charAt(i)) {
                tileColor[i] = GREEN;
                used[i] = true;
            } else {
                tileColor[i] = GREY;
            }
        }

        // THEN CHECK YELLOW WORDS
        for (int i = 0; i < word.length(); i++) {

            if (tileColor[i] == GREEN)
                continue;

            for (int j = 0; j < word.length(); j++) {

                if (!used[j] && word.charAt(i) == secretWord.charAt(j)) {
                    tileColor[i] = YELLOW;
                    used[j] = true;
                    break;
                }
            }
        }
    }

    @Override
    public String toString(){
        return "[Guess] "+word;
    }
}
