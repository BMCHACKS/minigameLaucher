package com.minigamelauncher.wordle;

public class HintHelper {
    private String secretWord;
    private int    nextChar;

    public HintHelper(String secretWord){
        this.secretWord = secretWord;
        nextChar        = 0;
    }

    public String getHint(){
        System.out.println("[CUSTOM INFO] User has requested hint: "+(nextChar+1)+"/6");

        if (nextChar == 5)
            return "Hint ["+(nextChar+1)+"/6]\nYou've asked too many hints, the word is: "+secretWord;

        return "Hint ["+(nextChar+1)+"/6]\nThe word contains this character: "+secretWord.charAt(nextChar++);
    }
}
