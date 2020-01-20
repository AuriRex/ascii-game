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

    }

    @Override
    public ATex[] setPreview() {
        // Quit doesn't need a preview
        return new ATex[] { null, null, null };
    }

    /**
     * set the GameMode reference to return to
     * @param mode GameMode
     */
	public void setReturnMode(GameMode mode) {
        return_mode = mode;
	}

}