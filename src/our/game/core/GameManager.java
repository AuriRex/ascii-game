package our.game.core;

import our.game.mode.Default;
import our.game.mode.Menu;

/**
 * Responsible for running the main programm loop and calling all of the necesarry functions.
 */
public class GameManager {

    // final int X;
    // final int Y;
    private Screen screen;
    boolean running = true;

    private static Default mode;

    public GameManager(int x, int y, Calibration c) {
        screen = new Screen(x, y);

        init();

        long time = 0;
        long oldTime = System.currentTimeMillis();
        long deltaTime = 0;

        int sleepTime = 200;
        int sleepCount = 0;

        // Main Programm Loop
        while (running) {

            // Loop thorugh all game objects and call draw function -> do this through gamemode!
            // screen.advanceAnimation();
            // screen.printReadyFrame();
            c.resetTop();
            mode.frameAdvance();
            screen.printReadyFrameDBG(deltaTime);

            // Makes sure the program runs at 5 frames per second
            while(sleepCount < sleepTime) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (System.currentTimeMillis() - oldTime >= sleepTime) {
                    break;
                }
            }
            

            time = System.currentTimeMillis();
            deltaTime = time - oldTime;
            oldTime = time;

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