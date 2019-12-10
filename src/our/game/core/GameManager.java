package our.game.core;

import our.game.gameobjects.GameObject;
import our.game.mode.GameMode;
import our.game.mode.Menu;
import our.game.util.Tex;

/**
 * Responsible for running the main programm loop and calling all of the necesarry functions.
 */
public class GameManager {

    // final int X;
    // final int Y;
    private Screen screen;
    boolean running = true;

    private GameMode mode;

    public static GameManager instance;

    public GameManager(int x, int y) {

        if(instance != null) return;

        instance = this;
        screen = new Screen(x, y);

        init();

        long time = 0;
        long oldTime = System.currentTimeMillis();
        long deltaTime = 0;

        int sleepTime = 200;
        int sleepCount = 0;

        // Main Programm Loop
        while (running) {

            // Loop thorugh all game objects and call draw function -> do this through
            // gamemode!
            screen.clearFrame();
            Calibration.instance.resetTop();
            mode.preDraw();
            mode.draw();
            if(Main.debug) {
                screen.debugHud(deltaTime);
                screen.drawDebugText(0,30, "X:"+d_x+" Y:"+d_y);
            }
            mode.frameAdvance();
            screen.pushFrame();
            screen.printReadyFrame();
            // screen.printReadyFrameDBG(deltaTime);

            // Makes sure the program runs at 5 frames per second
            while (sleepCount < sleepTime) {
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
    /**
     * Draws a GameObject to the screen
     * @param g Gameobject with x, y and tex
     */
    public static void drawToScreen(GameObject g) {
        drawToScreen(g.getX(), g.getY(), g.getObjectTex());
    }

    /**
     * Draws a Tex to the screen
     * @param x x-coordinate for the drawing
     * @param y y-coordinate for the drawing
     * @param tex texture to be drawn
     */
    public static void drawToScreen(int x, int y, Tex tex) {
        instance.screen.drawToScreen(x, y, tex);
    }

    /**
     * Returns the current running GameMode Instance.
     * @return GameMode Instance
     */
    public static GameMode getModeInstance() { // TODO: Redo all methods that call this one and handle null!
        return instance.mode;
    }

    private static int d_x = 0;
    private static int d_y = 0;

    public static void debug(int x, int y) {
        d_x = x;
        d_y = y;
    }

    private void init() {

        mode = new Menu();

        // Screen.clearScreen();
        // System.out.println("Initialized!");
    }

    /**
     * Changes the GameMode
     * @param gm GameMode
     */
	public static void changeGameMode(GameMode gm) {

        if(!instance.mode.onGameModeChange(gm)) {
            gm.loadCanceled(instance.mode);
            return;
        }

        instance.mode.unload();

        instance.mode = gm;

	}

}