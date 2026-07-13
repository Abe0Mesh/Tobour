package com.abe.tobour.object;

import java.awt.*;
import java.awt.image.*;

import com.abe.tobour.*;

public class SuperObject {
    
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0,0,48,48); // collision hit box, if I want to update individual items collision hitbox then go into child class for that item and update this data
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    UtilityTool uTool = new UtilityTool();

    public void draw(Graphics2D g2, GamePanel gp) {
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
            g2.drawImage(image, screenX, screenY, null);

        }
    }
}
