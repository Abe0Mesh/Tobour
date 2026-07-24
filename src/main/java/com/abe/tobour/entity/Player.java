package com.abe.tobour.entity;

import com.abe.tobour.*;

import java.awt.*;
import java.awt.image.*;


public class Player extends Entity{
    
    KeyHandler keyH;

    // Player position set in the center of screen
    public final int screenX;
    public final int screenY;
    int standCounter = 0;

    public Player(GamePanel gp, KeyHandler keyH){

        super(gp);
        
        this.keyH = keyH;

        //Center of the screen (where player is)
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle(8, 16, 32, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        attackArea.width = 36;
        attackArea.height = 36;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }

    public void setDefaultValues(){
        // where the player is standing on the world, defaults to center of the map
        worldX = gp.tileSize * 23;   
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";

        // PLAYER STATUS
        maxLife = 6;
        life = maxLife;
    }

    public void getPlayerImage(){

        up1 = setup("/player/up1", gp.tileSize, gp.tileSize);
        up2 = setup("/player/up2",gp.tileSize, gp.tileSize);
        down1 = setup("/player/down1", gp.tileSize, gp.tileSize);
        down2 = setup("/player/down2", gp.tileSize, gp.tileSize);
        left1 = setup("/player/left1", gp.tileSize, gp.tileSize);
        left2 = setup("/player/left2", gp.tileSize, gp.tileSize);
        right1 = setup("/player/right1", gp.tileSize, gp.tileSize);
        right2 = setup("/player/right2", gp.tileSize, gp.tileSize);
    }

    public void getPlayerAttackImage() {

        attackUp1 = setup("/player/boy_attack_up_1", gp.tileSize, gp.tileSize * 2);
        attackUp2 = setup("/player/boy_attack_up_2", gp.tileSize, gp.tileSize * 2);
        attackDown1 = setup("/player/boy_attack_down_1", gp.tileSize, gp.tileSize * 2);
        attackDown2 = setup("/player/boy_attack_down_2", gp.tileSize, gp.tileSize * 2);
        attackLeft1 = setup("/player/boy_attack_left_1", gp.tileSize * 2, gp.tileSize);
        attackLeft2 = setup("/player/boy_attack_left_2", gp.tileSize * 2, gp.tileSize);
        attackRight1 = setup("/player/boy_attack_right_1", gp.tileSize * 2, gp.tileSize);
        attackRight2 = setup("/player/boy_attack_right_2", gp.tileSize * 2, gp.tileSize);
        
    }



    public void update(){

        if (attacking == true) {
            attacking();
        }

        else if(keyH.upPressed == true || keyH.downPressed == true ||
            keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true) {

            if(keyH.upPressed == true){
                direction = "up";
            }
            else if (keyH.downPressed == true){
                direction = "down";
            }
            else if (keyH.leftPressed == true) {
                direction = "left";
            }
            else if (keyH.rightPressed == true){
                direction = "right";
            }

            // check tile collision
            collisionOn = false;
            gp.cChecker.checkTile(this);
            
            //  CHECK OBJ COLLISION
            int objIndex = gp.cChecker.checkObject(this, true); // this returns the index of the obj that was collided with
            pickUpObject(objIndex); // this uses the index to add logic to object interaction/collision

            // CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // CHECK MONSTER COLLISION
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contractMonster(monsterIndex);


            // CHECK EVENT 
            gp.eHandler.checkEvent();


            // If collision is false, player can move
            if (collisionOn == false && keyH.enterPressed == false) {
                switch(direction){
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            gp.keyH.enterPressed = false;

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
        }
        else {
            // sets player back to normal if they arnt moving
            standCounter++;
            if(standCounter == 30) {
                spriteNum = 1;
                standCounter = 0;
            }
        }

        // Cool down time between player taking damage so they dont just instanly die from contact
        if (invincible == true) {
            invincibleCounter ++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }


    }

    public void attacking() {
        
        spriteCounter++;

        if (spriteCounter <=5) {
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;

            // saving data so we temp set players hitbox to the area of the weapon
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // Adjust players world X/Y for attack area
            switch(direction) {
                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.width; break;
                case "right": worldX += attackArea.width; break;
            }
            
            // attackArea becomes solid area
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            // check monster col with updated cord variables
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex);

            // After checking collision, restore og data
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if (spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void pickUpObject(int index) {

        if(index != 999) { // index 999 means no obj touched
            
            
        }
    }

    public void interactNPC(int i) {
        if (gp.keyH.enterPressed == true) {
            if (i != 999) {
                // Only interacts with NPC if collision + pressing enter
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
                
    
            }
            else {
                gp.playSE(7);
                attacking = true;
                
            }
        }

    }

    public void contractMonster(int i) {
        if (i != 999) {

            if (invincible == false) {
                gp.playSE(6);
                life -= 1;
                invincible = true;
            }
        }
    }
    public void damageMonster(int i) {

        if (i != 999) {

            if (gp.monster[i].invincible == false) {

                gp.playSE(5);
                gp.monster[i].life -= 1;    
                gp.monster[i].invincible = true;
                gp.monster[i].damageReaction();

                if (gp.monster[i].life <= 0) {
                    gp.monster[i].dying = true;
                }
            }
        }
 
    }

    @Override
    public void draw(Graphics2D g2){
        // g2.setColor(Color.white);

        // g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;
        // temp to adjust for attacking which uses 16x32/ two tile pngs ( due to center beign at the top left of img)
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch(direction){
            case "up":
                if(attacking == false) {
                    if (spriteNum == 1) image = up1;
                    if (spriteNum == 2) image = up2;
                }
                if (attacking == true) {
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) image = attackUp1;
                    if (spriteNum == 2) image = attackUp2;
                }

                break;
            case "down":
                if (attacking == false) {
                    if (spriteNum == 1) image = down1;
                    if (spriteNum == 2) image = down2;
                }
                if (attacking == true) {
                    if (spriteNum == 1) image = attackDown1;
                    if (spriteNum == 2) image = attackDown2;
                }
                break;
            case "left":
                if (attacking == false) {
                    if (spriteNum == 1) image = left1;
                    if (spriteNum == 2) image = left2;
                }
                if (attacking == true) {
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) image = attackLeft1;
                    if (spriteNum == 2) image = attackLeft2;
                }
                break;
            case "right":
                if (attacking == false) {
                    if (spriteNum == 1) image = right1;
                    if (spriteNum == 2) image = right2;
                }
                if (attacking == true) {
                    if (spriteNum == 1) image = attackRight1;
                    if (spriteNum == 2) image = attackRight2;
                }

                break;
        }

        if (invincible == true) {
            // transparent during damage cooldown
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null);

        // reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));


        // DEBUG, highlight player hitbox
        if(gp.debugMode) {
            g2.setColor(Color.red);
            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);

            // invincible timer
            g2.setFont(new Font("Arial", Font.PLAIN, 26));
            g2.setColor(Color.white);
            g2.drawString("Invincible:" + invincibleCounter, 10, 400);
        }

    }
}
