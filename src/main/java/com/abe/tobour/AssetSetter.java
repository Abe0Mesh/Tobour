package com.abe.tobour;

import com.abe.tobour.entity.*;
import com.abe.tobour.object.*;

public class AssetSetter {
    // This class sets up what assets/objects we will have in our obj list and where they will be on the map and their type
    GamePanel gp;
    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }
    // TODO: i'm thinking of making a helper function as its quite repetiitive and I want to be able to see objects easier with one line instead of 3
    public void setObject() {
        // so here we add the obj to the list of objects then we set the cords of where it will be on the map (e.g = new ObjKey() this sets that elemt to a key)
        // we do a int * tileSize where the number is the 'block' that it will be on and tileSize is the actual size of the tile giving us the cord as the product

        
    }

    public void setNPC() {

        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize * 21;
        gp.npc[0].worldY = gp.tileSize * 21;
    }
}
