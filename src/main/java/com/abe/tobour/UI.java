package com.abe.tobour;

import java.awt.*;
import java.awt.image.*;
import java.text.*;

import com.abe.tobour.object.*;

public class UI {
    
    GamePanel gp;
    Graphics2D g2;
    Font arial40, arial80B;
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
    }

    public void showMessage(String text) {

        message = text;
        messageOn = true;

    }
    public void draw(Graphics2D g2) { // TODO in the future make a bucnch of seperate methods to draw differnt ui stuff and add them into draw() like drawMap() drawInv() etc
       
        this.g2 = g2;

        g2.setFont(arial40);
        g2.setColor(Color.white);

        if(gp.gameState == gp.playState) {
            // TODO add play state logic
        }

        if(gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }

    }
    
    public void drawPauseScreen() {

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text, x , y);

    }

    public int getXForCenteredText(String text) {
        // due to text point of center not being in middle have to do adjusting 
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth /2 - length/2 ;
        return x;
    }
}
