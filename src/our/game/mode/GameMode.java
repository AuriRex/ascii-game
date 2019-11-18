package our.game.mode;

import java.util.ArrayList;

import our.game.core.GameManager;
import our.game.gameobjects.GameObject;
import our.game.gameobjects.Preview;
import our.game.util.ATex;

public abstract class GameMode implements GMMethods {

    public final Preview prev;

    public GameMode() {
        prev = createPreview(setPreview());
    }

    private Preview createPreview(ATex[] atex) {
        return new Preview(atex[0],atex[1],atex[2]);
    }

    /**
     * 
     * @return MUST return an ATex[] with 3 (three) elements!
     */
    public abstract ATex[] setPreview();

    protected ArrayList<GameObject> gameObjectPool = new ArrayList<GameObject>();

    public void preDraw() {

    }

    /**
     * Draws all GameObjects to the screen
     */
    public void draw() {
        for(GameObject g : gameObjectPool) {

            GameManager.drawToScreen(g);

        }
    }

    public void frameAdvance() {
        for(GameObject g : gameObjectPool) {
            g.nextFrame();
        }
    }

    /**
     * 
     * @param x = X Coordinate in Console Characters
     * @param y = Y Coordinate in Console Characters
     */
    public void hoverInput(int x, int y) {
        for(GameObject g : gameObjectPool) {
            if ((x >= g.x && x < g.x + g.width) && (y >= g.y && y < g.y + g.height)) {
                // Mouse is over our GameObject
                g.onHover(x, y);
            } else {
                g.onNoHover();
            }
        }
    }

    /**
     * Gets called once the cursor leaves the frame / console
     */
    public void noHoverInput() {
        for(GameObject g : gameObjectPool) {
            g.onNoHover();
        }
    }

    /**
     * Gets called on Mouse click
     * @param x = X Coordinate in Console Characters
     * @param y = Y Coordinate in Console Characters
     */
    public void clickInput(int x, int y) {
        for(GameObject g : gameObjectPool) {
            if ((x >= g.x && x < g.x + g.width) && (y >= g.y && y < g.y + g.height)) {
                // Mouse is over our GameObject
                g.onMousePressed(x, y);
            }
        }
    }

}