package com.abe.tobour;

import com.abe.tobour.entity.*;
import com.abe.tobour.object.*;
import com.abe.tobour.tile.*;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable{
    
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
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    //FPS
    int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[10]; // we can display as many objs that are in this list, if player picks up obj then a slot opens up


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
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        player.update();

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g); 

        Graphics2D g2 = (Graphics2D)g;

        // TILE
        tileM.draw(g2); // Tiles need to be drawn before players or they will cover player

        // OBJECTS
        for(int i = 0; i < obj.length; i++) {
            // We scan the object arr one by one 
            if (obj[i] != null) {
                // we check if the index contains a obj obj (lol)
                obj[i].draw(g2, this);
            }

        }

        // PLAYER
        player.draw(g2);

        g2.dispose(); // saves mem

    }
}
