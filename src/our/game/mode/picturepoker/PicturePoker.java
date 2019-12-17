package our.game.mode.picturepoker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import our.game.core.GameManager;
import our.game.core.Reader;
import our.game.gameobjects.AnimationState;
import our.game.gameobjects.Card;
import our.game.mode.GameMode;
import our.game.mode.Menu;
import our.game.util.ATex;

public class PicturePoker extends GameMode {

    protected static PicturePoker instance;

    public String title = "PicturePoker";

    private ArrayList<CardType> enums = new ArrayList<CardType>();
    private HashMap<Card, Integer> changeCards = new HashMap<Card, Integer>();

    private Card[] cards = new Card[] { new Card("0", 10, 13, (ATex) Reader.read("./assets/cards/dummy.tex")) {

        @Override
        public void onMousePressed(int x, int y) {
            queueChange(this, 5);
        }
    }, new Card("1", 31, 13, (ATex) Reader.read("./assets/cards/dummy.tex")) {

        @Override
        public void onMousePressed(int x, int y) {
            queueChange(this, 6);
        }
    }, new Card("2", 52, 13, (ATex) Reader.read("./assets/cards/dummy.tex")) {

        @Override
        public void onMousePressed(int x, int y) {
            queueChange(this, 7);
        }
    }, new Card("3", 73, 13, (ATex) Reader.read("./assets/cards/dummy.tex")) {

        @Override
        public void onMousePressed(int x, int y) {
            queueChange(this, 8);
        }
    }, new Card("4", 94, 13, (ATex) Reader.read("./assets/cards/dummy.tex")) {

        @Override
        public void onMousePressed(int x, int y) {
            queueChange(this, 9);
        }
    } };

    private ATex[] cardATex = new ATex[] { (ATex) Reader.read("./assets/cards/mode/picturepoker/upvote.tex"),
            (ATex) Reader.read("./assets/cards/mode/picturepoker/star.tex"),
            (ATex) Reader.read("./assets/cards/mode/picturepoker/heart.tex"),
            (ATex) Reader.read("./assets/cards/mode/picturepoker/flower.tex"),
            (ATex) Reader.read("./assets/cards/mode/picturepoker/cloud.tex"),
            (ATex) Reader.read("./assets/cards/mode/picturepoker/downvote.tex") };

    public PicturePoker() {

        instance = this;

        Card card_return = new Card("exit", 110, 22, Reader.read("./assets/cards/mode/return_idle.atex")) {

            @Override
            public void onMousePressed(int x, int y) {
                GameManager.changeGameMode(Menu.instance);
            }
        };

        for (CardType c : CardType.values()) {
            enums.addAll(Arrays.asList(c, c, c, c, c));
        }

        if (our.game.core.Main.debug) {
            Card shuffle = new Card("shuffle", 31, 22, Reader.read("./assets/cards/dummy.tex")) {

                @Override
                public void onMousePressed(int x, int y) {

                    PicturePoker.instance.setCards();
                }
            };
            Card confirm = new Card("confirm", 52, 22, Reader.read("./assets/cards/dummy.tex")) {

                @Override
                public void onMousePressed(int x, int y) {

                    PicturePoker.instance.changeCard();
                }
            };
            addObjectToPool(shuffle);
            addObjectToPool(confirm);
            shuffle.setTex(AnimationState.HOVER, Reader.read("./assets/cards/dummy.tex"));
            shuffle.setTex(AnimationState.CLICK, Reader.read("./assets/cards/dummy.tex"));
            confirm.setTex(AnimationState.HOVER, Reader.read("./assets/cards/dummy.tex"));
            confirm.setTex(AnimationState.CLICK, Reader.read("./assets/cards/dummy.tex"));

        }

        // exit.setTex(AnimationState.IDLE, Reader.read("./assets/cards/mode/exit_idle.atex"));
        card_return.setTex(AnimationState.HOVER, Reader.read("./assets/cards/mode/exit_hover.atex"));

        addObjectToPool(card_return);

        for (Card c : cards) {
            addObjectToPool(c);
        }

        setCards();

    }

    // @Override
    // public void frameAdvance() {

    // }

    @Override
    public ATex[] setPreview() {
        // Filled the array with real ATex objects!
        return new ATex[] { (ATex) Reader.read("./assets/cards/preview/picturepoker/idle.tex"),
                (ATex) Reader.read("./assets/cards/preview/picturepoker/hover.atex"),
                (ATex) Reader.read("./assets/cards/preview/picturepoker/click.tex") };
    }

    public void setCards() {

        Collections.shuffle(enums);
        int i = 0;
        for (Card c : cards) {
            CardType temp = enums.get(i);
            c.setTex(AnimationState.IDLE, cardATex[temp.ordinal()]);
            c.setTex(AnimationState.HOVER, cardATex[temp.ordinal()]);
            c.setTex(AnimationState.CLICK, cardATex[temp.ordinal()]);
            i++;
        }

    }

    public void queueChange(Card c, int id) {

        changeCards.put(c, id);

    }

    public void changeCard() {

        if (!changeCards.isEmpty()) {
            for (Card c : changeCards.keySet()) {
                c.setTex(AnimationState.IDLE, cardATex[enums.get(changeCards.get(c)).ordinal()]);
                c.setTex(AnimationState.HOVER, cardATex[enums.get(changeCards.get(c)).ordinal()]);
                c.setTex(AnimationState.CLICK, cardATex[enums.get(changeCards.get(c)).ordinal()]);
            }
            changeCards.clear();
        }
    }

}
