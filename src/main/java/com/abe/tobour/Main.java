package com.abe.tobour;
import com.abe.tobour.game.Game;
//Entry point will call game.java to execute game
public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}
