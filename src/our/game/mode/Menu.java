package our.game.mode;

import java.util.ArrayList;

import our.game.core.Reader;
import our.game.gameobjects.AnimationState;
import our.game.gameobjects.Card;
import our.game.gameobjects.GameObject;
import our.game.mode.picturepoker.PicturePoker;
import our.game.util.ATex;
import our.game.util.XFrame;

public class Menu extends GameMode {

    private ArrayList<GameMode> games = new ArrayList<GameMode>();

    public Menu() {

        games.add(new PicturePoker());
        Card exit = new Card("exit", 5, 22, Reader.read("./assets/cards/mode/exit_idle.atex"));
        // Card exit = new Card(0, 0, Reader.read("./assets/cards/mode/exit_idle.atex"));
        exit.setTex(AnimationState.IDLE, Reader.read("./assets/cards/mode/exit_idle.atex"));
        exit.setTex(AnimationState.HOVER, Reader.read("./assets/cards/mode/exit_hover.atex"));
        gameObjectPool.add(exit);

    }

    @Override
    public ATex[] setPreview() {
        // Menu doesn't need a preview
        return new ATex[] {null, null, null};
    }

    // /**
    //  * 
    //  * @param x = X Coordinate in Console Characters
    //  * @param y = Y Coordinate in Console Characters
    //  */
    // @Override
    // public void hoverInput(int x, int y) {

    // }

    // /**
    //  * Gets called once the cursor leaves the frame / console
    //  */
    // @Override
    // public void noHoverInput() {

    // }

    /**
     * Gets called on Mouse click
     * 
     * @param x = X Coordinate in Console Characters
     * @param y = Y Coordinate in Console Characters
     */
    @Override
    public void clickInput(int x, int y) {
        for(GameObject g : gameObjectPool) {
            if (g.UID.equals("exit")){
                if ((x >= g.x && x < g.x + g.width) && (y >= g.y && y < g.y + g.height)) {
                    // Mouse is over our GameObject
                    XFrame.exitWindow();
                }
            }
        }
    }

}