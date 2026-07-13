package com.abe.tobour.object;

import java.io.*;

import javax.imageio.*;

import com.abe.tobour.*;


public class ObjBoots extends SuperObject{
    GamePanel gp;

    public ObjBoots(GamePanel gp) {

        this.gp = gp;

        name = "Boots";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/boots.png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
