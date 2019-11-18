package our.game.core;

import java.awt.Rectangle;

import our.game.util.MouseIn;
// import our.game.util.ResourceManager;

public class Main {

    public final static int X = 128;
    public final static int Y = 32;

    public static void main(String[] args) throws Exception {

        Calibration c = new Calibration(X, Y); // 4:1

        c.startCalibration();

        Rectangle rect = null;

        //Fixing Console Debugging
        try {
            rect = MouseIn.getConsoleWindow();
            if(rect == null) rect = new Rectangle(0,0,10,10);
        }catch(Exception ex) {
            rect = new Rectangle(0,0,10,10);
        }

        Calibration.createInvis(rect.x, rect.y, rect.width, rect.height);
        

        // Unused
        // ResourceManager.init();
        // ResourceManager.instance.loadResources("./assets/");

        new GameManager(X, Y, c);

    }

}