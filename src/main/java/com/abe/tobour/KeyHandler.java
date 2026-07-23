package com.abe.tobour;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    private int previousGameState;

    private boolean debugToggleRequested = false; // debug mode is 'p' button ATP

    // Key Hanlder only detects the debug button was toggled
    public boolean consumeDebugToggleRequest() {
        // Check button state, reset it then return its state
            boolean request = debugToggleRequested;
            debugToggleRequested = false;
            return request;
    }

    public KeyHandler(GamePanel gp) {

        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // TITLE STATE
        if (gp.gameState == gp.titleState) {

            if (gp.ui.titleScreenState == 0) {

                if (code == KeyEvent.VK_W){
                    gp.ui.commandNum--;
                    if (gp.ui.commandNum < 0) {
                        gp.ui.commandNum = 2;
                    }
                }

                if (code == KeyEvent.VK_S){
                    gp.ui.commandNum++;
                    if (gp.ui.commandNum > 2) {
                        gp.ui.commandNum = 0;
                    }
                }
                if (code == KeyEvent.VK_ENTER) {
                    if (gp.ui.commandNum == 0) {
                        gp.ui.titleScreenState = 1;
                        //gp.playMusic(0);
                    } 
                    if (gp.ui.commandNum == 1) {

                    }
                    if (gp.ui.commandNum == 2) {
                        System.exit(0);
                    }
                    
                }
            }
            else if (gp.ui.titleScreenState == 1) {

                if (code == KeyEvent.VK_W){
                    gp.ui.commandNum--;
                    if (gp.ui.commandNum < 0) {
                        gp.ui.commandNum = 1;
                    }
                }

                if (code == KeyEvent.VK_S){
                    gp.ui.commandNum++;
                    if (gp.ui.commandNum > 1) {
                        gp.ui.commandNum = 0;
                    }
                }
                if (code == KeyEvent.VK_ENTER) {
                    if (gp.ui.commandNum == 0) {
                        gp.gameState = gp.playState;
                    } 
                    if (gp.ui.commandNum == 1) {
                        gp.ui.titleScreenState = 0;
                    }
                    
                }
                
            }
        }

        // PLAY STATE
        else if (gp.gameState == gp.playState) { 
            if (code == KeyEvent.VK_W){
                upPressed = true;
            }

            if (code == KeyEvent.VK_S){
                downPressed = true;
            }

            if (code == KeyEvent.VK_A){
                leftPressed = true;
            }

            if (code == KeyEvent.VK_D){
                rightPressed = true;
            }
            if (code == KeyEvent.VK_ESCAPE){
                previousGameState = gp.gameState;
                gp.gameState = gp.pauseState;
            }
            if (code == KeyEvent.VK_ENTER){
                enterPressed = true;
            }

            // DEBUG
            if(code == KeyEvent.VK_P) {
                // setting debug button state
                debugToggleRequested = true;
            }
        }
        // PAUSE STATE 
        else if (gp.gameState == gp.pauseState) {

            if (code == KeyEvent.VK_ESCAPE){
                gp.gameState = previousGameState;
            }
            
        }

        // DIALOGUE
        else if (gp.gameState == gp.dialogueState) {
            if (code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.playState;

            }
            else if (code == KeyEvent.VK_ESCAPE){
                previousGameState = gp.gameState;
                gp.gameState = gp.pauseState;
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W){
            upPressed = false;
        }

        if (code == KeyEvent.VK_S){
            downPressed = false;
        }

        if (code == KeyEvent.VK_A){
            leftPressed = false;
        }

        if (code == KeyEvent.VK_D){
            rightPressed = false;
        }    }

    
}
