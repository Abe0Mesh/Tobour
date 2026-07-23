package com.abe.tobour;

import java.awt.*;
import java.awt.image.*;
import java.text.*;

import com.abe.tobour.entity.*;
import com.abe.tobour.object.*;

public class UI {
    
    GamePanel gp;
    Graphics2D g2;
    Font arial40, arial80B;
    BufferedImage heart_full, heart_half, heart_blank;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0; // 0 : first screen 1: second screen 


    public UI(GamePanel gp) {

        this.gp = gp;
        arial40 = new Font("Arial",Font.PLAIN, 40);
        arial80B = new Font("Arial", Font.BOLD, 80);
    

        // CREATE HUD OBJ
        Entity heart = new ObjHeart(gp);

        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;


    }


    public void showMessage(String text) {

        message = text;
        messageOn = true;

    }
    public void draw(Graphics2D g2) { // TODO in the future make a bucnch of seperate methods to draw differnt ui stuff and add them into draw() like drawMap() drawInv() etc
       
        this.g2 = g2;

        g2.setFont(arial40);
        g2.setColor(Color.white);

        // TITLE STATE
        if(gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        // PLAY STATE
        if(gp.gameState == gp.playState) {
            drawPlayerLife();
            }
        // PAUSE STATE
        if(gp.gameState == gp.pauseState) {
            drawPlayerLife();
            drawPauseScreen();
        }
        // DIALOGUE STATE
        if(gp.gameState == gp.dialogueState) {
            drawPlayerLife();
            drawDialogueScreen();
        }

    }

    public void drawPlayerLife() {

        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        
        int i = 0;

        // draw blank hearts 
        while (i < gp.player.maxLife/2) {
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }

        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;
        

        // Drawing Current life
        while (i < gp.player.life) {
            g2.drawImage(heart_half, x, y, null); // 1 life == half heart
            i++;
            if (i < gp.player.life) {
                g2.drawImage(heart_full, x, y, null); // 2 lifes == full heart
            }
            i++;
            x += gp.tileSize;

        }
        
    }
    
    public void drawTitleScreen() {

        if (titleScreenState == 0)  {

            g2.setColor(new Color(128,53,23));
            g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);

            // TITLE NAME
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
            String text = "Tobour";
            int x = getXForCenteredText(text);
            int y = gp.tileSize * 3;

            // SHADOW
            g2.setColor(Color.black);
            g2.drawString(text, x + 5, y + 5);

            // MAIN COLOR
            g2.setColor(Color.white);
            g2.drawString(text, x , y);

            // image with title (Swap this with an important entity)
            x = gp.screenWidth/2 - (gp.tileSize);
            y += gp.tileSize * 2;
            g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);

            // MENU
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
            
            text = "NEW GAME";
            x = getXForCenteredText(text);
            y+= gp.tileSize*3.5;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "LOAD GAME";
            x = getXForCenteredText(text);
            y+= gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "QUIT";
            x = getXForCenteredText(text);
            y+= gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x-gp.tileSize, y);
            }
        }
        else if (titleScreenState == 1) {

            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "Who are you?";
            int x = getXForCenteredText(text);
            int y = gp.tileSize * 3;
            g2.drawString(text, x , y);

            text = "Start";
            x = getXForCenteredText(text);
            y += gp.tileSize * 2;
            g2.drawString(text, x , y);
            if (commandNum == 0) {
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "Back";
            x = getXForCenteredText(text);
            y += gp.tileSize * 2;
            g2.drawString(text, x , y);
            if (commandNum == 1) {
                g2.drawString(">", x-gp.tileSize, y);
            }

        }
    }

    public void drawPauseScreen() {

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text, x , y);

    }

    public void drawDialogueScreen() {

        //DIALOGUE WINDOW
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize * 4;

        int x = gp.tileSize * 2;
        int y = gp.tileSize * 7;

        drawSubWindow( x, y, width, height);

        x += gp.tileSize;
        y += gp.tileSize;

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;

        }
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        // Color c = new Color(128,53,23);
        Color c = new Color(129,38,12);
        // Color c = new Color(79,84,110,210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);

    }

    public int getXForCenteredText(String text) {
        // due to text point of center not being in middle have to do adjusting 
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth /2 - length/2 ;
        return x;
    }
}
