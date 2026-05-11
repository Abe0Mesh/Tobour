package com.abe.tobour.ui;
import java.util.Scanner;
import com.abe.tobour.entities.*;
public class ConsoleUI {
    //how does player interact with the game?
    // Replace this class with GUI/View later down
    private Scanner sc = new Scanner(System.in);

    private void narrate(String text){
        System.out.println("[Narration] " + text + '\n');
    }
    private void npcDialogue(String npc, String text){
        System.out.println("[" + npc + "] " + text + '\n');
    }



public void intro(){

    narrate("You swing open the door, stubbling into the adventurers guild");
    narrate ("You can feel the piercing glares throughout the room\n");

    npcDialogue("Guild Clerk", "Welcome in, I dont think I've ever seen your face before");
    npcDialogue("Guild Clerk", "Are you here to join the guild?\n");

    narrate("You nervously nod your head,");
    narrate("still feeling the heavy presence of the others\n");
}

public String getPlayerName(){
    char confirm = 'n';
    String playerName = "noName";
    while (confirm != 'y' && confirm != 'Y') {
    npcDialogue("Guild Clerk", "Alright then, what your name then");
    playerName = sc.next();
    sc.nextLine(); //clearing the buffer so 'confirm' read does take in leftovers
    npcDialogue("Guild Clerk", "So your name is " + playerName + "? (Y/N)");
    confirm = sc.next().charAt(0);
    }
    npcDialogue("Guild Clerk", "Nice to meet you " + playerName + "!"); //using concatination within the function call, pretty cool lol

    return playerName;
}

void classMenu() {
    System.out.print("""  
        |  Warrior |
        |  Brawler |
        |  Mage    |
        |  Paladin |
        """);
}

void raceMenu() {
    System.out.print("""  
        |  Human    |
        |  Draconic |
        |  Dwarf    |
        |  Elf      |
        |  Giant    |
        """);
}

void statMenu(){
    System.out.print("""
        |  Strength     |
        |  Dexterity    |
        |  Speed        |
        |  Intelligence |
        |  Charisma     |
        """);
}



public ClassType getPlayerClass(String playerName){
    char confirm = 'n';
    ClassType classType = ClassType.WARRIOR;
    String input = "";
    while(confirm != 'y' && confirm != 'Y') {
        npcDialogue("Guild Clerk", "Whats your class " + playerName + "? \n");
        classMenu();
        input = sc.next();

        try{
            classType = ClassType.valueOf(input.toUpperCase()); // take input force uppercase to limit syntax error
        }
        catch(IllegalArgumentException e){
            System.out.println("Invalid class choice, please choose again");
            continue;
        }
        System.out.println("Confirm the class " + input + " (Y/N)");
        confirm = sc.next().charAt(0);
    }
    npcDialogue("Guild Clerk", "A " + classType + "! You've got potential kid");
    return classType;

}

public RaceType getPlayerRace(String playerName){
    char confirm = 'n';
    RaceType raceType = RaceType.HUMAN;
    String input = ""; // def outside of loop to use in npc dialogue
    while(confirm != 'y' && confirm != 'Y') {
        npcDialogue("Guild Clerk", "What race are you " + playerName + "? \n");
        raceMenu();
        input = sc.next();
        try{
            raceType = RaceType.valueOf(input.toUpperCase()); // take input force uppercase to limit syntax error
        }
        catch(IllegalArgumentException e){
            System.out.println("Invalid race choice, please choose again");
            continue;
        }
        System.out.println("Confirm the race " + input + " (Y/N)");
        confirm = sc.next().charAt(0);
    }
    npcDialogue("Guild Clerk", "Ah your an " + input.toLowerCase());
    return raceType;
    

}

public void getStartingStats(Player player){
    npcDialogue("Guild Clerk", "Please put your hand on the crystal ball\n");

    narrate("Infront of you shines a crystal ball, after placing your hand on the ball it begins to glow a deep purple");
    narrate("You can feel the energy coursing through your body, through your arm into the crystal ball");
    narrate("Smoke begins to accumulate around the ball, then out of the smoke a mysterous figure appears\n");
    
    npcDialogue("???", "how do you wish to choose your stats...");

    StatType[] statTypes = StatType.values();
    Integer[] statBuilder = {0,0,0,0,0};
    int initialPointPool = 15; //Player starts with 15 stat points they can allocate as they wish
    // use player obj param and .put into player objs hashmap to store stats

    for (int i = 0; i < statBuilder.length; i++) {
        if (initialPointPool <= 0) {
            // end stat building if of points
            // IDK if I need this as I think I can just let player add 0 points to stats 
            npcDialogue("???", "Welp, your out of points");
            break;
        }
        while(true){
            npcDialogue("???", "You currently have " + initialPointPool + " points left");
            npcDialogue("???", "How many points for " + statTypes[i] + "?");
            int allocatedPoints = sc.nextInt();
            if (allocatedPoints <= initialPointPool && allocatedPoints >= 0) {
                npcDialogue("???", "Confirm " + allocatedPoints + " points to " + statTypes[i] + " (Y/N)");
                char confirm = sc.next().charAt(0);
                if (confirm == 'y' || confirm == 'Y') {
                    initialPointPool = initialPointPool - allocatedPoints;
                    // we add onto points instead of replacing as if player has remaining points we let them add points again to stats
                    statBuilder[i] = statBuilder[i] + allocatedPoints; 
                    break;
                }
            }
            else { // handling below 0 & bigger then pool allocations
                npcDialogue("???", "Invalid choice, choice must be from 0 to " + initialPointPool);

            }
        }
        if (i == statBuilder.length - 1 && initialPointPool >= 0) i = -1; // if left over points, start back from first strength, so loop until avaliable points is empty

        
    }
    player.setStats(statBuilder); // Use setting in player obj and initilize stats hashmap
}




}
