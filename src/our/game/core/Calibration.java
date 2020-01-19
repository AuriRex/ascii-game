package our.game.core;

import our.game.util.Input;
import our.game.util.MouseIn;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import our.game.util.XFrame;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

public class Calibration {

    int resolution_x;
    int resolution_y;

    public static Calibration instance;

    /**
     * @param x sets the x-value
     * @param y sets the y-value
     */
    public Calibration(int x, int y) {

        if (instance != null)
            return;

        instance = this;

        resolution_x = x;
        resolution_y = y;
    }

    /**
     * Starts the Calibration to set the Console size to the desired x and y values
     */
    public void startCalibration() {

        /*
         * Creates "#" around the corners ### ### # # # #
         * 
         * # # # # ### ###
         */

        Screen.clearScreen();

        System.out.println("###" + getSpacing(resolution_x - 6) + "###");

        for (int i = 0; i < 2; i++)
            System.out.println("#" + getSpacing(resolution_x - 2) + "#");

        for (int i = 0; i < resolution_y - 6; i++) {
            if (i == resolution_y / 2 - 4) {
                System.out.println(centerText("Calibration mode...", resolution_x));
            } else if (i == resolution_y / 2 - 3) {
                System.out.println(centerText("Please resize your console window!", resolution_x));
            } else {
                System.out.println();
            }

        }

        for (int i = 0; i < 2; i++)
            System.out.println("#" + getSpacing(resolution_x - 2) + "#");

        System.out.print("###" + getSpacing(resolution_x - 6) + "###");

        Input.confirm();

        bufferOverflow();
    }

    /**
     * Centers the text to be in the exact middle of the console after Calibration
     * 
     * @param txt The String to be placed in the middle
     * @param x   The width in characters
     * @return String txt with leading spaces
     */
    public String centerText(String txt, int x) {
        String spaces = "";
        int i = 0;
        while (((x / 2) - txt.length() / 2) > i) {
            spaces += " ";
            i++;
        }
        return (spaces + txt);
    }

    /**
     * Gets the number of spaces in a single console line
     * 
     * @param x The number of spaces in characters
     * @return String containing the spaces
     */
    public String getSpacing(int x) {
        String spaces = "";
        while (x > 0) {
            spaces += " ";
            x--;
        }
        return spaces;
    }

    private static XFrame f;
    public static Dimension g;

    // private static long inputCheckCooldown = 150;

    // TODO: Move this out of calibration!
    /**
     * Creates the invisible clickable Frame
     * 
     * @param x      the x-coordinate of the window
     * @param y      the y-coordinate of the window
     * @param width  the width of the window
     * @param height the height of the window
     */
    public static void createWindow() {
        g = new Dimension(Main.X * 8, Main.Y * 16);

        f = new XFrame();
        // f.setType(JFrame.Type.UTILITY);
        // f.setSize(g);
        f.setLocationRelativeTo(null);
        f.setTitle("Ascii-Game");

        f.setResizable(false);
        // f.setUndecorated(true);
        f.getRootPane().getContentPane().setBackground(new Color(255, 255, 255, 200));
        // f.getRootPane().setOpaque(false);
        new Thread() {
            int[] lastPos;

            // long lastCheck = System.currentTimeMillis();
            public void run() {
                f.getRootPane().addMouseMotionListener(new MouseInputAdapter() {
                    @Override
                    public void mouseMoved(MouseEvent e) {
                        if (!GameManager.instance.isReady())
                            return;
                        // try {
                        // sleep(200); // TODO: don't maybe? - add cooldown instead of sleep maybe ?
                        // update: didn't quite work as intended :P
                        int[] pos = XFrame.calcPos(e);
                        // Only trigger when the Position changes
                        if (!pos.equals(
                                lastPos) /* && (System.currentTimeMillis() >= lastCheck + inputCheckCooldown) */) {
                            if (Main.debug)
                                GameManager.debug(pos[0], pos[1]);
                            GameManager.getModeInstance().hoverInput(pos[0], pos[1]);
                            // lastCheck = System.currentTimeMillis();
                        }
                        lastPos = pos;
                        // } catch (InterruptedException e1) {
                        // e1.printStackTrace();
                        // }
                    }
                });
                f.getRootPane().addMouseListener(f);
            }
        }.start();
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                GameManager.quit();

            }
            

        });
        // f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        f.setVisible(true);
    }

    /**
     * Redraws the Frame to fix the Console window
     */
    @Deprecated
    public void redraw() {
        Rectangle rect = MouseIn.getConsoleWindow();
        g.setSize(rect.width, rect.height);
        if (!(f.getSize().equals(g))) {
            startCalibration();
            g.setSize(rect.width, rect.height);
        }
        rect = MouseIn.getConsoleWindow();
        f.setLocation(rect.x, rect.y);
        f.setSize(rect.width, rect.height);
    }

    /**
     * Resets the Frame to be on top of the Console
     */
    @Deprecated
    public void resetTop() {
        if (!f.hasFocus()) {
            Input.confirm(
                    " Press Enter to regain control!\n If it doesn't recognise your Enter retab into your console!");
            redraw();
            f.toFront();
        }
    }

    /**
    * Overflows the buffer so the console doesn't stutter so hard in the beginning
    */
    public void bufferOverflow() {
        // for (int i = 0; i < 3000; i++) {
        //     System.out.println("\n");
        // }
    }
}