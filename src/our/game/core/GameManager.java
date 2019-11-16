package our.game.core;

import our.game.mode.Default;
import our.game.mode.Menu;

public class GameManager {

    // final int X;
    // final int Y;
    private Screen screen;
    boolean running = true;

    private static Default mode;

    public GameManager(int x, int y, Calibration c) {
        screen = new Screen(x, y);

        init();

        while (running) {

            // Loop thorugh all game objects and call draw function
            // screen.advanceAnimation();
            // screen.printReadyFrame();
            c.resetTop();
            mode.frameAdvance();
            screen.printReadyFrameDBG();

            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public static Default getModeInstance() {
        return mode;
    }

    private void init() {

        mode = new Menu();

        // Screen.clearScreen();
        // System.out.println("Initialized!");
    }

}