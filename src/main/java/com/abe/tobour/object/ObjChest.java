package com.abe.tobour.object;

import java.io.*;

import javax.imageio.*;

public class ObjChest extends SuperObject {
    
    public ObjChest(){

        name = "chest";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
