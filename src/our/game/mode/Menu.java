package our.game.mode;

import java.util.ArrayList;

import our.game.core.GameManager;
import our.game.core.Reader;
import our.game.gameobjects.AnimationState;
import our.game.gameobjects.Card;
import our.game.gameobjects.GameObject;
import our.game.mode.picturepoker.PicturePoker;
import our.game.util.ATex;
import our.game.util.Tex;
import our.game.util.XFrame;

public class Menu extends GameMode {

    private ArrayList<GameMode> games = new ArrayList<GameMode>();

    public Menu() {

        games.add(new PicturePoker());
        Card exit = new Card("exit", 5, 22, Reader.read("./assets/cards/mode/exit_idle.atex"));
        // Card exit = new Card(0, 0, Reader.read("./assets/cards/mode/exit_idle.atex"));
        exit.setTex(AnimationState.IDLE, Reader.read("./assets/cards/mode/exit_idle.atex"));
        exit.setTex(AnimationState.HOVER, Reader.read("./assets/cards/mode/exit_hover.atex"));

        Tex at = Reader.read("./assets/cards/mode/card_idle.tex");
        Card card = new Card("card2", 5, 10, at);
        card.setTex(AnimationState.HOVER, at);

        gameObjectPool.add(exit);
        gameObjectPool.add(card);

    }

    @Override
    public ATex[] setPreview() {
        // Menu doesn't need a preview
        return new ATex[] { null, null, null };
    }

    /**
     * 
     * @param x = X Coordinate in Console Characters
     * @param y = Y Coordinate in Console Characters
     * @param g = GameObject
     * @return true
     */
    @Override
    public boolean onHoverInput(int x, int y, GameObject g) {
        GameManager.drawToScreen(1, 29, new Tex(new String[] { "" + g.getWidth() }));
        return true;
    }

    @Override
    public boolean onNoHoverInput(GameObject g) {
        return true;
    }

    @Override
    public boolean onClickInput(GameObject g, int x, int y) {
        if (g.UID.equals("exit")) {
            if (!g.isVisible())
                return false;

            if (inBounds(x, y, g)) {
                // Mouse is over our GameObject
                XFrame.exitWindow();
            }
        }
        if (g.UID.equals("card2")) {
            if (!g.isVisible())
                return false;

            if (inBounds(x, y, g)) {
                // Mouse is over our GameObject
                GameObject exit = getByUID("exit");
                exit.setVisible(!exit.isVisible());

            }
        }
        return false;
    }

}