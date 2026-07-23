package com.abe.tobour.object;

import java.io.*;

import javax.imageio.*;

import com.abe.tobour.*;
import com.abe.tobour.entity.*;

public class ObjKey extends Entity{

    public ObjKey(GamePanel gp){

        super(gp);


        name = "Key";
        down1 = setup("/objects/key", gp.tileSize, gp.tileSize);





    }
}
