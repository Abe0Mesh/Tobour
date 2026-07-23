package com.abe.tobour.object;

import java.io.*;

import javax.imageio.*;

import com.abe.tobour.*;
import com.abe.tobour.entity.*;


public class ObjBoots extends Entity{

    public ObjBoots(GamePanel gp) {

        super(gp);
        name = "Boots";
        down1 = setup("/objects/boots", gp.tileSize, gp.tileSize);

    }
}
