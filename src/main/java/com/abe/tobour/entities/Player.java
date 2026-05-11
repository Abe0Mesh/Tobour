package com.abe.tobour.entities;
import java.util.HashMap;

public class Player {
    private String playerName;
    private ClassType playerClass;
    private RaceType playerRace;
    private HashMap<StatType, Integer> stats = new HashMap<>();

    // Add stats in the future
    int playerHealth;
    
    public Player(String playerName, ClassType playerClass, RaceType playerRace){
        this.playerName = playerName;
        this.playerClass = playerClass;
        this.playerRace = playerRace;
    }
    public void setStats(Integer[] arr ){ // Integer str, Integer dex, Integer spd, Integer intel, Integer rizz, 
        // update stat fields, allowing stat channges later during the game
        stats.put(StatType.STRENGTH, arr[0]);
        stats.put(StatType.DEXTERITY, arr[1]);
        stats.put(StatType.SPEED, arr[2]);
        stats.put(StatType.INTELLIGENCE, arr[3]);
        stats.put(StatType.CHARISMA, arr[4]);

    }

    
}
