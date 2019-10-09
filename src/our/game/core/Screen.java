package our.game.core;

import our.game.util.*;

class Screen {

    public static Screen screen = null;

    
    final int RESOLUTION_X;
    final int RESOLUTION_Y;
    final String[] clearFrame;
    String[] frame;
    String[] rframe;
    public Screen(int x, int y) {
        RESOLUTION_X = x;
        RESOLUTION_Y = y;

        // new String("128x32");
        frame = new String[y];
        screen = this;
        clearFrame = fillDefaultFrame();
        rframe = frame;
        // Tex test = Reader.read("./assets/defaultdance.anim");
        // System.out.println(test.getFrame()[0]);
        // System.out.println(test.getFrame()[1]);
        // System.out.println(test.getFrame()[2]);
        // System.out.println(test.getFrame()[3]);
        // System.out.println(test.getFrame()[4]);
        // System.out.println(Tex.checkTex(test));
        // Input.confirm();
        drawToScreen(30, 4, test);
        printFrame();
    }

    private String[] fillDefaultFrame() {
        for(int i = 0; i < RESOLUTION_Y; i++) {
            String s = "";
            for(int j = 0; j < RESOLUTION_X; j++)
              s += " ";
            frame[i] = s;
        }
        return frame;
    }

    String[] testString = {
        "xoxo",
        "oxox",
        "uzuz",
        "UwU ",
        "QwQ "
    };
    
    public boolean drawToScreen(int x, int y, Tex tex) {

        // x, y, w , h
        // x = 10
        // y = 4
        // ################################################################################################################################
        // ################################################################################################################################
        // ################################################################################################################################
        // ################################################################################################################################
        // ##########x#####################################################################################################################
        // ################################################################################################################################
        // #################


        //
        if(Tex.checkTex(tex)) {

            if(tex.isAnimated()) {
                // Is ATex
            }

            String[] texFrame = tex.getFrame();

            // zeichnen auf frame :)
            String currentLine = "";
            int ycount = 0;
            for (int i = 0; i < RESOLUTION_Y; i++) {
                currentLine = frame[i];
                
                if (i >= y && ycount < texFrame.length) {
                    String newLine = "";
                    char[] tmp = texFrame[ycount].toCharArray();
                    int xcount = 0;
                    int x_res_count = 0;
                    for(char c : currentLine.toCharArray()) {
                        if(x_res_count >= x && xcount < texFrame[0].length()) {
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

    public void printFrame() {
        for(String s : frame) {
            System.out.print("\n"+s);
        }
    }

    /**
     * Should be called after everything has been drawn to th frame
     * -> pushes frame to rframe
     */
    public void pushFrame() {
        rframe = frame;
    }

    public static void clearScreen() {
        for(int i = 0; i < 32; i++) System.out.println();
    }

	public void printReadyFrame() {
        for(String s : rframe) {
            System.out.print("\n"+s);
        }
    }
    
    Tex test = Reader.read("./assets/defaultdance.anim");
    // Tex test = Reader.read("./assets/default_sframe.tex");
    boolean test_b = true;
    public void printReadyFrameDBG() {
        if(test_b) {
            ((ATex) test).reversed = true;
            test_b = false;
        }
        clearFrame();
        drawToScreen(30, 4, test);
        test.nextFrame();
        pushFrame();
        for(String s : rframe) {
            System.out.print("\n"+s);
        }
	}

    private void clearFrame() {
        frame = clearFrame.clone();
    }

}