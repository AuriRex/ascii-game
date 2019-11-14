package our.game.core;

import our.game.util.Input;
import our.game.util.MouseIn;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;
import our.game.util.XFrame;

import java.awt.Rectangle;

class Calibration {

    int resolution_x;
    int resolution_y;

    public Calibration(int x, int y) {
        resolution_x = x;
        resolution_y = y;
    }

    public void startCalibration() {

        // ###        ###
        // #            #
        // #            #

        // #            #
        // #            #
        // ###        ###

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

    }

    public String centerText(String txt, int x) {
        String spaces = "";
        int i = 0;
        while (((x / 2) - txt.length() / 2) > i) {
            spaces += " ";
            i++;
        }
        return (spaces + txt);
    }

    public String getSpacing(int x) {
        String spaces = "";
        while (x > 0) {
            spaces += " ";
            x--;
        }
        return spaces;
    }

    private static XFrame f;
    private static Dimension g;

    public static void createInvis(int x, int y, int width, int height) {
        f = new XFrame();
        f.setType(JFrame.Type.UTILITY);
        f.setLocation(x, y);
        f.setSize(width, height);
        g = new Dimension(width, height);
        f.setTitle("Not Visible in the Taskbar");
        f.setUndecorated(true);
        f.setBackground(new Color(255, 255, 255, 50));
        f.getRootPane().setOpaque(false);
        f.addMouseListener(f);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

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

    public void resetTop() {
        if (!f.hasFocus()) {
            Input.confirm(
                    " Press Enter to regain control!\n If it doesn't recognise your Enter retab into your console!");
            redraw();
            f.toFront();
        }
    }
}