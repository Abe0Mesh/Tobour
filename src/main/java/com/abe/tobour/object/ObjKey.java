package com.abe.tobour.object;

import java.io.*;

import javax.imageio.*;

public class ObjKey extends SuperObject{
    
    public ObjKey(){
        name = "Key";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));

        }catch(IOException e){
            e.printStackTrace();
        }

    }
}
