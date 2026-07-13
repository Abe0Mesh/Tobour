package com.abe.tobour;

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
        gp.obj[0] = new ObjKey(gp);
        gp.obj[0].worldX = 23 * gp.tileSize;
        gp.obj[0].worldY = 7 * gp.tileSize;

        gp.obj[1] = new ObjKey(gp);
        gp.obj[1].worldX = 23 * gp.tileSize;
        gp.obj[1].worldY = 40 * gp.tileSize;

        gp.obj[2] = new ObjKey(gp);
        gp.obj[2].worldX = 38 * gp.tileSize;
        gp.obj[2].worldY = 8 * gp.tileSize;

        gp.obj[3] = new ObjDoor(gp);
        gp.obj[3].worldX = 10 * gp.tileSize;
        gp.obj[3].worldY = 11 * gp.tileSize;

        gp.obj[4] = new ObjDoor(gp);
        gp.obj[4].worldX = 8 * gp.tileSize;
        gp.obj[4].worldY = 28 * gp.tileSize;

        gp.obj[5] = new ObjDoor(gp);
        gp.obj[5].worldX = 12 * gp.tileSize;
        gp.obj[5].worldY = 22 * gp.tileSize;

        gp.obj[6] = new ObjChest(gp);
        gp.obj[6].worldX = 10 * gp.tileSize;
        gp.obj[6].worldY = 7 * gp.tileSize;

        gp.obj[7] = new ObjBoots(gp);
        gp.obj[7].worldX = 37 * gp.tileSize;
        gp.obj[7].worldY = 42 * gp.tileSize;

    }
}
