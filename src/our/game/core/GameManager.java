package our.game.core;

class GameManager {

    // final int X;
    // final int Y;
    private Screen screen;
    boolean running = true;

    public GameManager(int x, int y) {
        screen = new Screen(x, y);

        init();

        while (running) {

            // Loop thorugh all game objects and call draw function
            // screen.advanceAnimation();
            // screen.printReadyFrame();
            screen.printReadyFrameDBG();
            Calibration.resetTop();
            
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    private void init() {
        // Screen.clearScreen();
        // System.out.println("Initialized!");
    }




}