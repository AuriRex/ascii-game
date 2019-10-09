package our.game.core;

import our.game.util.MouseIn;

public class Main {
    public static void main(String[] args) throws Exception {

        final int X = 128;
        final int Y = 32;

        Calibration c = new Calibration(X, Y); // 4:1 lol

        new MouseIn(); // Test stuff

        c.startCalibration();

        /*GameManager gm = */new GameManager(X, Y);

    }
}