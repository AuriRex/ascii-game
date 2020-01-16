package our.game.mode.memorymatch;

import java.util.Random;

import our.game.core.GameManager;
import our.game.core.Reader;
import our.game.gameobjects.AnimationState;
import our.game.gameobjects.Card;
import our.game.gameobjects.CardType;
import our.game.mode.GameMode;
import our.game.mode.Menu;
import our.game.util.ATex;
import our.game.util.Tex;

public class MemoryMatch extends GameMode {

    protected static MemoryMatch instance;

    public String title = "MemoryMatch";

    /*

    8 Cards
    4 Card Pairs
    6 Total Unique Cards

    -> choose 4 at random

    */

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

        setup();

    }

    private Card[] cards;

    private void setup() {

        cards = new Card[8];

        Random rng = new Random();

        int[] choosenCards = new int[4];

        for(int i = 0; i < 4; i ++) {
            int card = rng.nextInt(6);
            while (containsInt(choosenCards, card)) {
                card = rng.nextInt(6);
            }
            choosenCards[i] = card;
        }
        
        for(int i = 0; i < 8; i++) {

            int ci = (int) (i / 2);

            cards[i] = new Card(ci+"_"+(i%2), 4 + i * 12, 5, cardATex[choosenCards[ci]]) { // TODO: change tex
                private boolean mm_hidden = true;
                private CardType mm_type = CardType.values()[choosenCards[ci]];

                public boolean mm_isHidden() {
                    return mm_hidden;
                }

                public void mm_setHidden(boolean h) {
                    mm_hidden = h;
                }

                @Override
                public Tex getObjectTex() { // call this from gamemode's gameobject pool
                    if(mm_isHidden()) return Reader.read("./assets/cards/card_back.tex");
                    return texture.get(AnimationState.IDLE);
                }
            };

            cards[i].setTex(AnimationState.HOVER, Reader.read("./assets/cards/card_back.tex")); // TODO: change tex

            addObjectToPool(cards[i]);
        }

    }

    private boolean containsInt(int[] choosenCards, int item) {

        for (int obj : choosenCards) {
            if(obj == item) {
                return true;
            }
        }

        return false;
    }

    private ATex[] cardATex = new ATex[] { (ATex) Reader.read("./assets/cards/mode/global/upvote.tex"),
            (ATex) Reader.read("./assets/cards/mode/global/star.tex"),
            (ATex) Reader.read("./assets/cards/mode/global/heart.tex"),
            (ATex) Reader.read("./assets/cards/mode/global/flower.tex"),
            (ATex) Reader.read("./assets/cards/mode/global/cloud.tex"),
            (ATex) Reader.read("./assets/cards/mode/global/downvote.tex") };

    @Override
    public ATex[] setPreview() {
        // TODO Auto-generated method stub
        return new ATex[] { (ATex) Reader.read("./assets/cards/preview/memorymatch/idle.tex"),
                (ATex) Reader.read("./assets/cards/preview/memorymatch/hover.atex"),
                (ATex) Reader.read("./assets/cards/preview/memorymatch/click.tex") };
    }

}