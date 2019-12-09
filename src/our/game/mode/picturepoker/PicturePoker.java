package our.game.mode.picturepoker;

import our.game.core.GameManager;
import our.game.core.Reader;
import our.game.gameobjects.AnimationState;
import our.game.gameobjects.Card;
import our.game.mode.GameMode;
import our.game.mode.Menu;
import our.game.util.ATex;

public class PicturePoker extends GameMode {

    public String title = "PicturePoker";

    public PicturePoker() {

        Card card_return = new Card("exit", 110, 22, Reader.read("./assets/cards/mode/return_idle.atex"))  {

            @Override
            public void onMousePressed(int x, int y) {
                GameManager.changeGameMode(Menu.instance);
            }

        };
        // exit.setTex(AnimationState.IDLE, Reader.read("./assets/cards/mode/exit_idle.atex"));
        card_return.setTex(AnimationState.HOVER, Reader.read("./assets/cards/mode/exit_hover.atex"));

        gameObjectPool.add(card_return);

    }

    // @Override
    // public void frameAdvance() {

    // }

    @Override
    public ATex[] setPreview() {
        // Filled the array with real ATex objects!
        return new ATex[] { (ATex) Reader.read("./assets/cards/preview/picturepoker/idle.tex"), (ATex) Reader.read("./assets/cards/preview/picturepoker/hover.atex"), (ATex) Reader.read("./assets/cards/preview/picturepoker/click.tex") };
    }

}