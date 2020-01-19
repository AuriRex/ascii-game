package our.game.mode;

import our.game.core.GameManager;
import our.game.core.Reader;
import our.game.gameobjects.AnimationState;
import our.game.gameobjects.Card;
import our.game.util.ATex;
import our.game.util.Tex;
import our.game.util.XFrame;

public class Quit extends GameMode {

    public String title = "Quit";

    public static Quit instance;

    private GameMode return_mode;

    public Quit() {

        if (instance != null)
            return;

        instance = this;

        Card exit = new Card("exit", 49, 12, Reader.read("./assets/cards/mode/exit_idle.atex")) {

            @Override
            public void onMousePressed(int x, int y) {

                XFrame.forceExitWindow();

            }

        };
        exit.setTex(AnimationState.HOVER, Reader.read("./assets/cards/mode/exit_hover.atex"));

        
        Tex at = Reader.read("./assets/cards/mode/card_idle.tex");
        Card card = new Card("card2", 68, 12, at) {
            @Override
            public void onMousePressed(int x, int y) {

                if(return_mode != null) {
                    GameManager.changeGameMode(return_mode);
                } else GameManager.menu();

            }
        };
        card.setTex(AnimationState.HOVER, at);


        addObjectToPool(exit);
        addObjectToPool(card);
        /*
         * // Timer Test new Timer("timer_1", 5000, 1000) {
         * 
         * @Override public void run() { System.out.println("Timer1 finished!"); }
         * 
         * @Override public void runInterval() { System.out.println("Timer1 interval!");
         * }
         * 
         * @Override public void onDestroy() {
         * System.out.println("Timer1 has been destroyed!"); } };
         * 
         * new Timer("timer_2", 4000, 0) {
         * 
         * @Override public void run() { System.out.println("Timer2 finished!");
         * Timer.destroy("timer_1"); }
         * 
         * @Override public void runInterval() { System.out.println("Timer2 interval!");
         * } };
         */

        // gameObjectPool.add(card);

    }

    @Override
    public ATex[] setPreview() {
        // Quit doesn't need a preview
        return new ATex[] { null, null, null };
    }

	public void setReturnMode(GameMode mode) {
        return_mode = mode;
	}

    /**
     * 
     * @param g
     * @param x
     * @param y
     * @return
     */
    // @Override
    // public boolean onClickInput(GameObject g, int x, int y) {
    //     if (g.UID.equals("exit")) {
    //         if (!g.isVisible())
    //             return false;

    //         if (inBounds(x, y, g)) {
    //             // Mouse is over our GameObject
    //             XFrame.exitWindow();
    //         }
    //         return false;
    //     }
    //     if (g.UID.equals("card2")) {
    //         if (!g.isVisible())
    //             return false;

    //         if (inBounds(x, y, g)) {
    //             // Mouse is over our GameObject
    //             GameObject exit = getByUID("exit"); // TODO: Optimize, cache GameObject reference instead of searching
    //                                                 // for it everytime.
    //             exit.setVisible(!exit.isVisible());

    //         }
    //         return false;
    //     }
    //     return true;
    // }

}