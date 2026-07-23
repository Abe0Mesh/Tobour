package com.abe.tobour.object;

import java.io.*;

import javax.imageio.*;

import com.abe.tobour.*;
import com.abe.tobour.entity.*;

public class ObjChest extends Entity {

    public ObjChest(GamePanel gp){

        super(gp);
        name = "Chest";
        down1 = setup("/objects/chest", gp.tileSize, gp.tileSize);


    }
}
