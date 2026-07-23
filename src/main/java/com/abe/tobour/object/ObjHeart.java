package com.abe.tobour.object;

import java.io.*;

import javax.imageio.*;

import com.abe.tobour.*;
import com.abe.tobour.entity.*;

public class ObjHeart extends Entity{


    public ObjHeart(GamePanel gp){

        super(gp);
        name = "Heart";   
        image = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
        image2 = setup("/objects/heart_half", gp.tileSize, gp.tileSize);
        image3 = setup("/objects/heart_blank", gp.tileSize, gp.tileSize);


    }
}
