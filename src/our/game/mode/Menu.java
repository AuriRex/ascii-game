package our.game.mode;

import java.util.ArrayList;

import our.game.core.GameManager;
import our.game.core.Reader;
import our.game.gameobjects.AnimationState;
import our.game.gameobjects.Card;
import our.game.gameobjects.GameObject;
import our.game.gameobjects.Preview;
import our.game.mode.memorymatch.MemoryMatch;
import our.game.mode.picturepoker.PicturePoker;
import our.game.util.ATex;
import our.game.util.Tex;
import our.game.util.XFrame;

public class Menu extends GameMode {

    private ArrayList<GameMode> games = new ArrayList<GameMode>();

    public String title = "Menu";

    public static Menu instance;

    public Menu() {

        if (instance != null)
            return;

        instance = this;

        games.add(new PicturePoker());
        games.add(new MemoryMatch());

        Card exit = new Card("exit", 110, 22, Reader.read("./assets/cards/mode/exit_idle.atex"));
        // Card exit = new Card(0, 0, Reader.read("./assets/cards/mode/exit_idle.atex"));
        exit.setTex(AnimationState.IDLE, Reader.read("./assets/cards/mode/exit_idle.atex"));
        exit.setTex(AnimationState.HOVER, Reader.read("./assets/cards/mode/exit_hover.atex"));

        Tex at = Reader.read("./assets/cards/mode/card_idle.tex");
        Card card = new Card("card2", 5, 10, at);
        card.setTex(AnimationState.HOVER, at);

        int x = 5;
        for (GameMode gm : games) {
            Preview pv = gm.prev;
            Card card_prev = new Card(gm.title, x, 10, pv.idle) {

                @Override
                public void onMousePressed(int x, int y) {
                    GameManager.changeGameMode(gm);
                }

            };
            card_prev.setTex(AnimationState.HOVER, pv.hover);
            card_prev.setTex(AnimationState.CLICK, pv.click);

            addObjectToPool(card_prev);
            x += 15;
        }

        addObjectToPool(exit);
        // gameObjectPool.add(card);

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
        String[] str = new String[1];
        str[0] = "" + g.getWidth();
        GameManager.drawToScreen(1, 29, new Tex(str));
        return true;
    }

    /**
     * @param g = GameObject
     * @return true
     */
    @Override
    public boolean onNoHoverInput(GameObject g) {
        return true;
    }

    /**
     * 
     * @param g
     * @param x
     * @param y
     * @return
     */
    @Override
    public boolean onClickInput(GameObject g, int x, int y) {
        if (g.UID.equals("exit")) {
            if (!g.isVisible())
                return false;

            if (inBounds(x, y, g)) {
                // Mouse is over our GameObject
                XFrame.exitWindow();
            }
            return false;
        }
        if (g.UID.equals("card2")) {
            if (!g.isVisible())
                return false;

            if (inBounds(x, y, g)) {
                // Mouse is over our GameObject
                GameObject exit = getByUID("exit"); // TODO: Optimize, cache GameObject reference instead of searching for it everytime.
                exit.setVisible(!exit.isVisible());

            }
            return false;
        }
        return true;
    }

}