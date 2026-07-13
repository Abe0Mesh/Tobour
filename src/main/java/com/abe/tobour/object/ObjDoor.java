package com.abe.tobour.object;

import java.io.*;

import javax.imageio.*;

import com.abe.tobour.*;

public class ObjDoor extends SuperObject{
    GamePanel gp;
    public ObjDoor(GamePanel gp){

        this.gp = gp;
        
        name = "Door";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);


        }catch(IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}
