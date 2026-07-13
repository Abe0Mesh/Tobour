package com.abe.tobour.object;

import java.io.*;

import javax.imageio.*;

import com.abe.tobour.*;

public class ObjChest extends SuperObject {
    GamePanel gp;
    public ObjChest(GamePanel gp){
        
        this.gp = gp;

        name = "Chest";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);


        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
