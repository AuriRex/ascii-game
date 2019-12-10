package our.game.util;

import java.awt.image.BufferedImage;

public class Font {

    private final int length;
    private final int asciiOffset;

    private final BufferedImage fontRaw;

    public Font(BufferedImage img, int asciiOffset) {

        length = img.getWidth() / 8;
        this.asciiOffset = asciiOffset;
        fontRaw = img;

    }

    public int length() {
        return length;
    }

    public int getOffset() {
        return asciiOffset;
    }

    /**
     * 
     * @param id
     * @param xi value
     * @param yi value
     * @return
     */
    public int getPixel(int id, int xi, int yi) {

        int xoffset = id * 8;

        if(xoffset > length*8 - 16) {
            xoffset = length*8 - 8;
        }

        int xpos = xi % 8;
        int ypos = yi % 16;

        return fontRaw.getRGB(xoffset + xpos, ypos);
    }

}