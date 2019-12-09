package our.game.mode;

import java.util.ArrayList;

import our.game.core.GameManager;
import our.game.gameobjects.GameObject;
import our.game.gameobjects.Preview;
import our.game.util.ATex;

public abstract class GameMode implements GMMethods {

    public final Preview prev;

    public final int PREV_IDLE = 0;
    public final int PREV_HOVER = 1;
    public final int PREV_CLICK = 2;

    public String title = "Base";

    public GameMode() {
        prev = createPreview(setPreview());
    }

    private Preview createPreview(ATex[] atex) {
        return new Preview(atex[0], atex[1], atex[2]);
    }

    /**
     * 
     * @return MUST return an ATex[] with 3 (three) elements! [Idle, Hover, Klick]
     */
    public abstract ATex[] setPreview();

    protected ArrayList<GameObject> gameObjectPool = new ArrayList<GameObject>();

    /**
     * Gets called before changing gamemode 
     */
    public void load() {

    }

    /**
     * Called before the GameMode changes
     * @param gm GameMode to change to
     * @return false to cancel GameMode change
     */
    public boolean onGameModeChange(GameMode gm) {
        return true;
    }

    public void unload() {

    }

    /**
     * Called if the current GameMode canceled the GameMOde change
     * @param mode GameMode that canceled the change
     */
    public void loadCanceled(GameMode mode) {

	}

    public void preDraw() {

    }

    /**
     * Draws all GameObjects to the screen
     */
    public void draw() {
        for (GameObject g : gameObjectPool) {

            if (g.isVisible())
                GameManager.drawToScreen(g);

        }
    }

    /**
     * Get's called every frame
     */
    public void frameAdvance() {
        for (GameObject g : gameObjectPool) {
            // Only execute the GameObjects code when 
            if (g.shouldExecute()) {
                g.frameAdvance();
            }

        }
    }

    /**
    * Search through all GameObjects for one with a specific UID
    * @param uid The UID to search for
    * @return a GameObject or null!
    */
    public GameObject getByUID(String uid) {
        for (GameObject g : gameObjectPool) {
            if (g.UID.equals(uid))
                return g;
        }
        return null;
    }

    /**
     * 
     * @param x = X Coordinate in Console Characters
     * @param y = Y Coordinate in Console Characters
     */
    public void hoverInput(int x, int y) {
        for (GameObject g : gameObjectPool) {
            if (g.shouldExecute() && onHoverInput(x, y, g)) {
                if (inBounds(x, y, g)) {
                    // Mouse is over our GameObject
                    g.onHover(x, y);
                } else {
                    g.onNoHover();
                }
            }
        }
    }

    /**
     * Hover Input hook
     * @param x 
     * @param y
     * @param g GameObject g
     * @return boolean - true to call onHover() for the given GameObject
     */
    public boolean onHoverInput(int x, int y, GameObject g) {
        return true;
    }

    /**
     * Gets called once the cursor leaves the frame / console
     */
    public void noHoverInput() {
        for (GameObject g : gameObjectPool) {
            if (g.shouldExecute() && onNoHoverInput(g)) {
                g.onNoHover();
            }
        }
    }

    /**
     * No Hover Input hook
     * @param g GameObject g
     * @return boolean - true to call onNoHover() for the given GameObject
     */
    public boolean onNoHoverInput(GameObject g) {
        return true;
    }

    /**
     * Check if the x and y coordinates are inside the GameObjects bounds
     * @param x Points X coordinate
     * @param y Points Y coordinate
     * @param g GameObject
     * @return true if x & y is inside the GameObjects Bounds
     */
    public boolean inBounds(int x, int y, GameObject g) {
        return inBounds(x, y, g.getX(), g.getY(), g.getWidth(), g.getHeight());
    }

    /**
     * Check if the x and y coordinates are inside the (gx, gy, gx + gw, gy + gh) bounds
     * @param x Points X coordinate
     * @param y Points Y coordinate
     * @param gx Top left boundary X Position
     * @param gy Top left boundary Y Position
     * @param gw Width of the boundary
     * @param gh Height of the boundary
     * @return true if x & y are inside the boundary
     */
    public boolean inBounds(int x, int y, int gx, int gy, int gw, int gh) {
        if ((x >= gx && x < gx + gw) && (y >= gy && y < gy + gh))
            return true;
        return false;
    }

    /**
     * Gets called on Mouse click
     * @param x = X Coordinate in Console Characters
     * @param y = Y Coordinate in Console Characters
     * Changes the default GameObjects Animation State to CLICK
     */
    public void clickInput(int x, int y) {
        for (GameObject g : gameObjectPool) {
            if (inBounds(x, y, g)) {
                // Mouse is over our GameObject
                if (onClickInput(g, x, y))
                    g.onMousePressed(x, y); // Changes the default GameObjects Animation Sate to CLICK
            }
        }
    }

    /**
     * Click input hook
     * @param g = affected GameObject
     * @param x = X Coordinate in Console Characters
     * @param y = Y Coordinate in Console Characters
     * @return boolean - true to call onMousePressed(x,y) on the given GameObject
     */
    public boolean onClickInput(GameObject g, int x, int y) {
        return true;
    }

}