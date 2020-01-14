package our.game.mode.memorymatch;

import our.game.core.GameManager;
import our.game.core.Reader;
import our.game.gameobjects.AnimationState;
import our.game.gameobjects.Card;
import our.game.mode.GameMode;
import our.game.mode.Menu;
import our.game.util.ATex;

public class MemoryMatch extends GameMode {

    protected static MemoryMatch instance;

    public String title = "MemoryMatch";

    public MemoryMatch() {

        if(instance != null) return;
        instance = this;

        Card card_return = new Card("exit", 110, 22, Reader.read("./assets/cards/mode/return_idle.atex")) {
            @Override
            public void onMousePressed(int x, int y) {
                GameManager.changeGameMode(Menu.instance);
            }
        };
        card_return.setTex(AnimationState.HOVER, Reader.read("./assets/cards/mode/exit_hover.atex"));
        card_return.setTex(AnimationState.CLICK, Reader.read("./assets/cards/mode/return_idle.atex"));

        addObjectToPool(card_return);

    }

    @Override
    public ATex[] setPreview() {
        // TODO Auto-generated method stub
        return new ATex[] { (ATex) Reader.read("./assets/cards/preview/memorymatch/idle.tex"),
                (ATex) Reader.read("./assets/cards/preview/memorymatch/hover.atex"),
                (ATex) Reader.read("./assets/cards/preview/memorymatch/click.tex") };
    }

}