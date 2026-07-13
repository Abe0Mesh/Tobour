package com.abe.tobour.tile;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;

import com.abe.tobour.*;

public class TileManager {
    
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(GamePanel gp){
        this.gp = gp;

        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/world01.txt");
    }

    public void getTileImage(){

        setup(0, "grass", false);
        setup(1, "wall", true);
        setup(2, "water", true);
        setup(3, "earth", false);
        setup(4, "tree", true);
        setup(5, "sand", false);

    }

    public void setup(int index, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png")); 
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePathToMap){

        try {

            InputStream is = getClass().getResourceAsStream(filePathToMap);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) { // 0 indexed / < instead of <= as we are using arr
                String line = br.readLine();

                while(col < gp.maxWorldCol) {
                    String numbers[] = line.split(" "); // reading txt file nums one by one by separating them by their whitespace gaps

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            br.close();


        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){
        // each tile is 48x48 so x and y gap/vals should match that (48 cuz its 16x16 resized)

        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            // This loops goes through the numbered map .txt file top to bottom and draw's coresponding images
            int tileNum = mapTileNum[worldCol][worldRow]; //tileNum is used to match corsesponding img num

            // TODO: Come back and review this logic the camera still a bit to wrap around
            // world cords refers to where on the world map the tile is 
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;

            // refers to where on the screen the tile is 
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // Only draws tiles that can be seen on the players screen
            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && 
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && 
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY)
            {
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);

            }
        

            worldCol++;

            if (worldCol == gp.maxWorldCol){
                worldCol = 0;
                worldRow++;

            }
        }

    }
    
}
