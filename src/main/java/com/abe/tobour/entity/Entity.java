package com.abe.tobour.entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.*;

import com.abe.tobour.*;


public class Entity {

    GamePanel gp;

    public BufferedImage up1, up2, down1,down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;

    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public Rectangle attackArea = new Rectangle(0,0,0,0); // atack area will be dynamic so default 0's
    public int solidAreaDefaultX, solidAreaDefaultY; 
    String dialogues[] = new String[20];
    int dialogueIndex = 0;
    public BufferedImage image, image2, image3;

    // State
    public int worldX, worldY; 
    public boolean collision = false;
    public boolean collisionOn = false;
    public String direction = "down";
    public boolean invincible = false;
    public int spriteNum = 1;
    boolean attacking = false;  

    // COUNTER
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    public int spriteCounter = 0;




    // CHARACTER STATUS
    public String name;
    public int type; // 0 = player, 1 = npc, 2 = monster
    public int maxLife;
    public int life;
    public int speed;


    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() {} // basically a abstract class

    public void update() {
        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if (this.type == 2 & contactPlayer == true) {
            // If this is a monster and it contacts a player
            if (gp.player.invincible == false) {
                gp.player.life -=1;
                gp.player.invincible = true;
            }
        }

        // If collision is false, player can move
        if (collisionOn == false) {
            switch(direction){
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }
        }
        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNum == 1) {
                spriteNum = 2;
            }
            else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        if (invincible == true) {
            invincibleCounter ++;
            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    

    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        // Draw the objects on the screen
        // refers to where on the screen the tile is 
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // Only draws tiles that can be seen on the players screen
        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
            worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && 
            worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && 
            worldY - gp.tileSize < gp.player.worldY + gp.player.screenY)
        {
            switch(direction){
                case "up":
                    if (spriteNum == 1) image = up1;
                    if (spriteNum == 2) image = up2;
                    break;
                case "down":
                    if (spriteNum == 1) image = down1;
                    if (spriteNum == 2) image = down2;
                    break;
                case "left":
                    if (spriteNum == 1) image = left1;
                    if (spriteNum == 2) image = left2;
                    break;
                case "right":
                    if (spriteNum == 1) image = right1;
                    if (spriteNum == 2) image = right2;
                    break;
            }

            if (invincible == true) {
                // transparent during damage cooldown
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            }

            g2.drawImage(image, screenX, screenY, null);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));


        }
    }

    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);

        }catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void speak() {
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++; 
        if (dialogues[dialogueIndex] == null) dialogueIndex = 0;

        switch(gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }

    }



}
