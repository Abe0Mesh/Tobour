package com.abe.tobour;

import com.abe.tobour.entity.*;
import com.abe.tobour.tile.*;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.*;

public class GamePanel extends JPanel implements Runnable{
    
    public boolean debugMode = false;

    // screen settings
    final int originalTileSize = 16; // 16 x 16 tile (pixels), which is the standard assest size for pixel art
    final int scale = 3; // Using a scale due to 16x16 being quite small on high res monitor


    public final int tileSize = originalTileSize * scale; // now 48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    //FPS
    int FPS = 60;

    // SYSTEM
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound sound = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread;

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    public Entity obj[] = new Entity[10]; // we can display as many objs that are in this list, if player picks up obj then a slot opens up
    public Entity npc[] = new Entity[10];
    public Entity monster[] = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList();

    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;

    // Set players default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // Impoves rendering 
        this.addKeyListener(keyH);
        this.setFocusable(true); // Helps receive key inputs
    }

    public void setUpGame(){

        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();

        gameState = titleState;

    }

    public void startGameThread(){

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){
        double drawInterval = 1000000000 /FPS; 
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        // The game loop
        while(gameThread != null){

            currentTime = System.nanoTime();
            
            // calculating how much time has passed and how close it is to needing to show next frame
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            // Delta works like a bucket it fills up until enough time has passed to move to next frame (delta bigger then 1)
            if (delta >= 1) {
                // 1 UPDATE: update info like character positons
                update();
                // 2. DRAW: draw screen with the updated information
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                //System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {

        // DEBUG, GP owns whether debug mode is enabled
        if (keyH.consumeDebugToggleRequest() == true) {
            debugMode = !debugMode; // Flipping state if debug button was toggled
        }

        if(gameState == playState) {
            player.update();
            // NPC
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].update();
                }
            }
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    monster[i].update();
                }
            }
        }
        if(gameState == pauseState) {

        }



    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g); 

        Graphics2D g2 = (Graphics2D)g;

        // DEBUG
        long drawStart = 0; // TODOWhy are we using a long here?
        if (debugMode == true) {
            drawStart = System.nanoTime(); // Tracking time to analyze rendering performance/ how long it tkaes to draw
        }

        if (gameState == titleState) { 
            ui.draw(g2);

        }
        else { 

            // TILE
            tileM.draw(g2); // Tiles need to be drawn before players or they will cover player

            entityList.add(player);
            
            // ADD ENTITES TO THE LIST
            for(int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    entityList.add(npc[i]);
                }
            }

            for (int i = 0; i < obj.length; i++) {
                if(obj[i] != null) {
                    entityList.add(obj[i]);
                }
            }


            for (int i = 0; i < monster.length; i++) {
                if(monster[i] != null) {
                    entityList.add(monster[i]);
                }
            }

            // SORT
            Collections.sort(entityList, new Comparator<Entity>() {

                @Override
                public int compare(Entity e1, Entity e2) {

                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            // DRAW ENTITEIS
            for(int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }
            // EMPTY ENTITY LIST
            entityList.clear();

            // UI
            ui.draw(g2);
        }

            // DEBUG
            if(debugMode == true) {
                long drawEnd = System.nanoTime();
                long passed = drawEnd - drawStart;
                g2.setColor(Color.white);
                g2.drawString("Draw Time: " + passed, 10, 400);
                System.out.println("Draw time: " + passed);
            }


            g2.dispose(); // saves mem
        
    }

    public void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }
    public void stopMusic() {

        sound.stop();
    }
    public void playSE(int i) {

        sound.setFile(i);
        sound.play();
    }

}
