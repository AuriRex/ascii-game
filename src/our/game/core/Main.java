package our.game.core;

import java.awt.Rectangle;

import our.game.util.MouseIn;
// import our.game.util.ResourceManager;
import our.game.util.Tex;

public class Main {

    public final static int X = 128;
    public final static int Y = 32;
	protected static boolean debug = true; // TODO add option to disable / enable

    public static void main(String[] args) throws Exception {

        // Tex t = Reader.read("./assets/cards/mode/exit_idle.atex");

        // System.err.println(t.width);

        // if (X == 128)
        //     return;

        Calibration c = new Calibration(X, Y); // 4:1

        c.startCalibration();
        c.bufferOverflow();

        Rectangle rect = null;

        //Fixing Console Debugging
        try {
            rect = MouseIn.getConsoleWindow();
            if (rect == null)
                rect = new Rectangle(0, 0, 100, 100);
        } catch (Exception ex) {
            rect = new Rectangle(0, 0, 100, 100);
        }

        Calibration.createInvis(rect.x, rect.y, rect.width, rect.height);

        // Unused
        // ResourceManager.init();
        // ResourceManager.instance.loadResources("./assets/");

        new GameManager(X, Y, c);

    }

}