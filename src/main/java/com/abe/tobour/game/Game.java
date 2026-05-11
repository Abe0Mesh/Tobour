package com.abe.tobour.game;
import com.abe.tobour.ui.ConsoleUI;
import com.abe.tobour.entities.*;

public class Game {
    // purpose; whats happening in the game?, acutally running the game
    private ConsoleUI ui;
    private GameState state;
    public Game(){
        ui = new ConsoleUI();
        state = new GameState();
    }

    public void start(){
        // setting up the game and play configs
        ui.intro();
        String playerName = ui.getPlayerName();
        ClassType playerClass = ui.getPlayerClass(playerName);
        RaceType playerRace = ui.getPlayerRace(playerName);
        Player player = new Player(playerName, playerClass, playerRace);
        ui.getStartingStats(player);
        // use game state here with new player obj

        gameLoop();
    }

    private void gameLoop(){
        while (!state.isGameOver()){
            // take in actions like movement etc..
        }
    }

}
