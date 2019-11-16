package our.game.mode;

import our.game.gameobjects.Preview;
import our.game.util.ATex;

public abstract class Default implements GMMethods {

    public final Preview prev;

    public Default() {
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

    public void frameAdvance() {
        //TODO implement stuff if we need it lmao
    }

    /**
     * 
     * @param x = X Coordinate in Console Characters
     * @param y = Y Coordinate in Console Characters
     */
    public void hoverInput(int x, int y) {
        
    }

    /**
     * Gets called once the cursor leaves the frame / console
     */
    public void noHoverInput() {

    }

    /**
     * Gets called on Mouse click
     * @param x = X Coordinate in Console Characters
     * @param y = Y Coordinate in Console Characters
     */
    public void clickInput(int x, int y) {

    }

}