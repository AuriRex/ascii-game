package our.game.core;

import java.awt.Rectangle;

import our.game.util.MouseIn;

public class Main {
    public static void main(String[] args) throws Exception {

        final int X = 128;
        final int Y = 32;

        Calibration c = new Calibration(X, Y); // 4:1 lol

        Rectangle rect = MouseIn.getConsoleWindow(); // Test stuff

        c.startCalibration();
        c.createInvis(rect.x, rect.y, rect.width, rect.height);

        /*GameManager gm = */new GameManager(X, Y);

    }

}