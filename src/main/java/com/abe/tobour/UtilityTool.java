package com.abe.tobour;

import java.awt.*;
import java.awt.image.*;

public class UtilityTool {
    
    public BufferedImage scaleImage(BufferedImage original, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());// basically a blank canvas
        Graphics2D g2 = scaledImage.createGraphics(); // get something to draw with (like a paintbrush)
        g2.drawImage(original, 0, 0, width, height, null); // draw image onto blank canvas with scaling
        g2.dispose();

        return scaledImage;
    }
}
