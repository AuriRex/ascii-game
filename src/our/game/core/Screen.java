package our.game.core;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
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

    public static BufferedImage getBI() {
        return bufferedImage;
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
        g.fillRect(0, 0, Main.X * 8 - 1, Main.Y * 16 - 1);
        g.dispose();

        clearFrame = fillDefaultFrame();
        rframe = frame;

        // drawToScreen(30, 4, test);
        printReadyFrame();
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

    /**
     * Should be called after everything has been drawn to the frame -> pushes frame
     * to rframe (readyFrame)
     */
    public void pushFrame() {
        if (testf == null)
            try {
                testf = new Font(Reader.readFont("./assets/fonts/default_ascii_32_90.png"), 30);
            } catch (IOException e) {
                e.printStackTrace();
            }
        rframe = frame;

        for(int iy = 0; iy < Main.Y*16; iy++) {
            for(int ix = 0; ix < Main.X*8; ix++) {

                int posx = (ix / 8);
                int posy = (iy / 16);

                char curChar = rframe[posy].charAt(posx);
                int rgb = testf.getPixel(curChar-32, ix, iy);
                bufferedImage.setRGB(ix, iy, rgb);

            }
        }


        
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
        for (String s : rframe) {
            System.out.print("\n" + s);
        }

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
    }

    public void debugHud(long deltaTime) {

        drawToScreen(0, 0, new Tex(new String[] {
                "################################################################################################################################" }));
        drawToScreen(0, 31, new Tex(new String[] {
                "################################################################################################################################" }));

        if (deltaTime != 0)
            drawToScreen(1, 0, new Tex(new String[] { " Framerate: " + (int) (1000 / deltaTime) + " " }));

    }

    public void drawDebugText(int x, int y, String txt) {
        drawToScreen(x, y, new Tex(new String[] { txt }));
    }

}