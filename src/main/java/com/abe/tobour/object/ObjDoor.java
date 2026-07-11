package com.abe.tobour.object;

import java.io.*;

import javax.imageio.*;

public class ObjDoor extends SuperObject{
    
    public ObjDoor(){

        name = "Door";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));

        }catch(IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}
