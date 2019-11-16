package our.game.core;

import java.awt.Rectangle;

import our.game.util.MouseIn;
import our.game.util.ResourceManager;

public class Main {
    public static void main(String[] args) throws Exception {

        final int X = 128;
        final int Y = 32;

        Calibration c = new Calibration(X, Y); // 4:1 lol

        c.startCalibration();

        Rectangle rect = MouseIn.getConsoleWindow(); // Test stuff
        //TODO: check if calibration was sucessful
        Calibration.createInvis(rect.x, rect.y, rect.width, rect.height);

        ResourceManager.init();
        ResourceManager.instance.loadResources("./assets/");

        /*GameManager gm = */new GameManager(X, Y, c);
        //test

    }

}