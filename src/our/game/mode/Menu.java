package our.game.mode;

import java.util.ArrayList;

import our.game.mode.picturepoker.PicturePoker;
import our.game.util.ATex;

public class Menu extends Default {

    private ArrayList<Default> games = new ArrayList<Default>();

    public Menu() {

        // prev = new Preview(null, null, null); // TODO

        games.add(new PicturePoker());

    }

    @Override
    public ATex[] setPreview() {
        // Menu doesn't need a preview
        return new ATex[] {null, null, null};
    }

    /**
     * 
     * @param x = X Coordinate in Console Characters
     * @param y = Y Coordinate in Console Characters
     */
    @Override
    public void hoverInput(int x, int y) {

    }

    /**
     * Gets called once the cursor leaves the frame / console
     */
    @Override
    public void noHoverInput() {

    }

    /**
     * Gets called on Mouse click
     * 
     * @param x = X Coordinate in Console Characters
     * @param y = Y Coordinate in Console Characters
     */
    @Override
    public void clickInput(int x, int y) {

    }

    

}