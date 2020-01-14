package our.game.core;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;

import our.game.core.Main;
import our.game.util.*;

public class Screen {

    public static Screen screen = null;

    final int RESOLUTION_X;
    final int RESOLUTION_Y;
    final String[] clearFrame;
    String[] frame;
    String[] rframe;

    static BufferedImage bufferedImage;
    final BufferedImage bufferedImageClear;
    static BufferedImage bufferedImageReady;

    public static BufferedImage getBI() {
        return bufferedImageReady;
    }

    /**
     * creates and prints the new Frame
     * 
     * @param x width of the screen
     * @param y height of the screen
     */
    public Screen(int x, int y) {
        RESOLUTION_X = x;
        RESOLUTION_Y = y;

        frame = new String[y];
        screen = this;

        bufferedImage = new BufferedImage(Main.X * 8, Main.Y * 16, BufferedImage.TYPE_INT_RGB);

        // TODO: Move this to draw function lol
        Graphics g = bufferedImage.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, Main.X * 8, Main.Y * 16);
        g.dispose();

        bufferedImageClear = deepCopy(bufferedImage);
        bufferedImageReady = deepCopy(bufferedImage);

        clearFrame = fillDefaultFrame();
        rframe = frame;

        // drawToScreen(30, 4, test);
        printReadyFrame();
    }

    static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    /**
     * The default frame to be drawn (empty frame)
     * 
     * @return the frame
     */
    private String[] fillDefaultFrame() {
        for (int i = 0; i < RESOLUTION_Y; i++) {
            String s = "";
            for (int j = 0; j < RESOLUTION_X; j++)
                s += " ";
            frame[i] = s;
        }
        return frame;
    }

    /**
     * Draws the checked tex to the screen
     * 
     * @param x   width of the screen
     * @param y   height of the screen
     * @param tex to be drawn
     * @return true if the frame is good
     */
    public boolean drawToScreen(int x, int y, Tex tex) {

        if (Tex.checkTex(tex)) {

            String[] texFrame = tex.getFrame();

            // draw on frame
            String currentLine = "";
            int ycount = 0;
            for (int i = 0; i < RESOLUTION_Y; i++) {
                currentLine = frame[i];

                if (i >= y && ycount < texFrame.length) {
                    String newLine = "";
                    char[] tmp = texFrame[ycount].toCharArray();
                    int xcount = 0;
                    int x_res_count = 0;
                    for (char c : currentLine.toCharArray()) {
                        if (x_res_count >= x && xcount < texFrame[0].length()) {
                            newLine += tmp[xcount];
                            xcount++;
                        } else {
                            newLine += c;
                        }
                        x_res_count++;
                    }
                    ycount++;
                    frame[i] = newLine;
                }
            }

            return true;
        }

        return false;
    }

    Font testf;

    int a = 0;

    boolean new_algo = true;

    public void draw() {
        if (testf == null) {
            try {
                testf = new Font(Reader.readFont("./assets/fonts/default_ascii_32_127.png"), 30);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(new_algo)
        for(int iy = 0; iy < Main.Y; iy++) {
            for(int ix = 0; ix < Main.X; ix++) {

                char curChar = rframe[iy].charAt(ix);

                if(curChar != ' ') {

                    for(int posy = 0; posy < 16; posy++) {
                        for(int posx = 0; posx < 8; posx++) {
                            
                            int rgb = testf.getPixel(curChar-32, ix * 8 + posx, iy * 16 + posy);
                            // System.out.println(rgb);
                            if(rgb != -65281) // Filter out transparency (RGB: 255, 0, 255) -> Magenta
                                bufferedImage.setRGB(ix * 8 + posx, iy * 16 + posy, rgb);

                        }
                    }

                    

                }

            }
        }
        else
        for(int iy = 0; iy < Main.Y*16; iy++) {
            for(int ix = 0; ix < Main.X*8; ix++) {

                int posx = (ix / 8);
                int posy = (iy / 16);

                char curChar = rframe[posy].charAt(posx);

                if(curChar != ' ') {

                    int rgb = testf.getPixel(curChar-32, ix, iy);
                    // System.out.println(rgb);
                    if(rgb != -65281) // Filter out transparency (RGB: 255, 0, 255) -> Magenta
                        bufferedImage.setRGB(ix, iy, rgb);

                }

            }
        }
        
    }

    /**
     * Should be called after everything has been drawn to the frame -> pushes frame
     * to rframe (readyFrame)
     */
    public void pushFrame() {
        
        rframe = frame;

        bufferedImageReady = deepCopy(bufferedImage);
        
        // Graphics g = bufferedImage.getGraphics();
    }

    /**
     * Clears the screen
     */
    public static void clearScreen() {
        for (int i = 0; i < 32; i++)
            System.out.println();

    }

    /**
     * Prints rframe to the screen
     */
    public void printReadyFrame() {
        // for (String s : rframe) {
        //     System.out.print("\n" + s);
        // }

        XFrame.printReadyFrameDBG();
    }

    // Tex test = Reader.read("./assets/fu.tex");
    // Tex test = Reader.read("./assets/defaultdance.atex");
    // Tex test = Reader.read("./assets/cards/mode/exit_idle.atex");
    // Tex test = Reader.read("./assets/cards/mode/exit_hover.atex");
    // Tex test = Reader.read("./assets/default_sframe.tex");
    Tex test;
    boolean test_b = true;

    /**
     * Debug option for printing the rframe
     * @param deltaTime
     */
    @Deprecated
    public void printReadyFrameDBG(long deltaTime) {
        if (test_b) {
            ((ATex) test).reversed = false;
            test_b = false;
        }
        clearFrame();
        drawToScreen(0, 0, new Tex(new String[] { "dTime: " + deltaTime }));
        drawToScreen(30, 4, test);
        test.nextFrame();
        pushFrame();
        for (String s : rframe) {
            System.out.print("\n" + s);
        }
    }

    /**
     * replaces the frame with an empty frame
     */
    void clearFrame() {
        frame = clearFrame.clone();

        if(bufferedImage != null) {
            Graphics g = bufferedImage.getGraphics();

            g.setColor(Color.WHITE);
            g.fillRect(0, 0, Main.X * 8 - 1, Main.Y * 16 - 1);
            g.dispose();
        }
    }

    private int p_deltatime = 0;
    private long last_dt = 0;

    public void debugHud(long deltaTime) {

        drawToScreen(0, 0, new Tex(new String[] {
                "################################################################################################################################" }));
        drawToScreen(0, 31, new Tex(new String[] {
                "################################################################################################################################" }));
        
        p_deltatime += deltaTime;

        if (p_deltatime > 500) {
            last_dt = deltaTime;
            p_deltatime = 0;
        }
        if(last_dt != 0)
            drawToScreen(1, 0, new Tex(new String[] { " Framerate: " + (int) (1000 / last_dt) + " " }));
            

    }

    public void drawDebugText(int x, int y, String txt) {
        drawToScreen(x, y, new Tex(new String[] { txt }));
    }

}