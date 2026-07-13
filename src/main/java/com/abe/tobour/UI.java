package com.abe.tobour;

import java.awt.*;
import java.awt.image.*;
import java.text.*;

import com.abe.tobour.object.*;

public class UI {
    
    GamePanel gp;
    Font arial40, arial80B;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;

    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp) {

        this.gp = gp;
        arial40 = new Font("Arial",Font.PLAIN, 40);
        arial80B = new Font("Arial", Font.BOLD, 80);
        ObjKey key = new ObjKey(gp);
        keyImage = key.image;
    }

    public void showMessage(String text) {

        message = text;
        messageOn = true;

    }
    public void draw(Graphics2D g2) { // TODO in the future make a bucnch of seperate methods to draw differnt ui stuff and add them into draw() like drawMap() drawInv() etc
        if (gameFinished == true) {
            // END GAME
            g2.setFont(arial40);
            g2.setColor(Color.white);
            String text;
            int textLength;
            int x;
            int y;

            text = "You found the tresuare!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 - (gp.tileSize * 3);
            g2.drawString(text, x, y);

            text = "Your time is :" + dFormat.format(playTime) + "!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + (gp.tileSize * 4);
            g2.drawString(text, x, y);

            g2.setFont(arial80B);
            g2.setColor(Color.yellow);
            text = "Congrats!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + (gp.tileSize * 2);
            g2.drawString(text, x, y);

            gp.gameThread = null; // this stops the thread killing the game


        }
        else {
            g2.setFont(arial40);
            g2.setColor(Color.white);
            g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
            g2.drawString("x " + gp.player.hasKey, 74, 50);
            
            // PLAY TIME
            playTime +=(double) 1/60;
            g2.drawString("Time:  " + dFormat.format(playTime), gp.tileSize * 11, 65);

            // MESSAGE
            if(messageOn == true) {
                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message, gp.tileSize/2, gp.tileSize * 5); // using tile sizes to set where message is shown
    
                messageCounter++;
    
                if(messageCounter > 120) { // text disapears after 120 frames / 2 seconds
                    messageCounter = 0;
                    messageOn = false;
                }
            }

        }

    }
}
