package our.game.mode.picturepoker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import our.game.core.GameManager;
import our.game.core.Reader;
import our.game.gameobjects.AnimationState;
import our.game.gameobjects.Card;
import our.game.gameobjects.CardType;
import our.game.mode.GameMode;
import our.game.mode.Menu;
import our.game.util.ATex;
import our.game.util.Timer;

public class PicturePoker extends GameMode {

    protected static PicturePoker instance;

    public String title = "PicturePoker";

    private ArrayList<CardType> enums = new ArrayList<CardType>();
    private HashMap<Card, Integer> changeCards = new HashMap<Card, Integer>();

    private ATex cardBack = (ATex) Reader.read("./assets/cards/card_back.tex");

    private Card[] cards = new Card[] { new Card("0", 10, 21, (ATex) Reader.read("./assets/cards/dummy.tex")) {

        @Override
        public void onMousePressed(int x, int y) {
            queueChange(this, 5);
        }
    }, new Card("1", 31, 21, (ATex) Reader.read("./assets/cards/dummy.tex")) {

        @Override
        public void onMousePressed(int x, int y) {
            queueChange(this, 6);
        }
    }, new Card("2", 52, 21, (ATex) Reader.read("./assets/cards/dummy.tex")) {

        @Override
        public void onMousePressed(int x, int y) {
            queueChange(this, 7);
        }
    }, new Card("3", 73, 21, (ATex) Reader.read("./assets/cards/dummy.tex")) {

        @Override
        public void onMousePressed(int x, int y) {
            queueChange(this, 8);
        }
    }, new Card("4", 94, 21, (ATex) Reader.read("./assets/cards/dummy.tex")) {

        @Override
        public void onMousePressed(int x, int y) {
            queueChange(this, 9);
        }
    } };

    private Card[] dealerCards = new Card[] { new Card("5", 10, 3, (ATex) cardBack),
            new Card("6", 31, 3, (ATex) cardBack), new Card("7", 52, 3, (ATex) cardBack),
            new Card("8", 73, 3, (ATex) cardBack), new Card("9", 94, 3, (ATex) cardBack) };

    private ATex[] cardATex = new ATex[] { (ATex) Reader.read("./assets/cards/mode/global/upvote.tex"),
            (ATex) Reader.read("./assets/cards/mode/global/star.tex"),
            (ATex) Reader.read("./assets/cards/mode/global/heart.tex"),
            (ATex) Reader.read("./assets/cards/mode/global/flower.tex"),
            (ATex) Reader.read("./assets/cards/mode/global/cloud.tex"),
            (ATex) Reader.read("./assets/cards/mode/global/downvote.tex") };

    public PicturePoker() {

        if (instance != null)
            return;

        instance = this;

        Card card_return = new Card("exit", 110, 22, Reader.read("./assets/cards/mode/return_idle.atex")) {

            @Override
            public void onMousePressed(int x, int y) {
                GameManager.changeGameMode(Menu.instance);
            }
        };

        Card confirm = new Card("confirm", 28, 13, Reader.read("./assets/cards/mode/global/confirm.tex")) {

            @Override
            public void onMousePressed(int x, int y) {

                PicturePoker.instance.changeCard();
            }
        };
        addObjectToPool(confirm);

        confirm.setTex(AnimationState.HOVER, Reader.read("./assets/cards/mode/global/confirm.tex"));
        confirm.setTex(AnimationState.CLICK, Reader.read("./assets/cards/mode/global/confirm.tex"));

        card_return.setTex(AnimationState.HOVER, Reader.read("./assets/cards/mode/exit_hover.atex"));

        addObjectToPool(card_return);

        for (CardType c : CardType.values()) {
            enums.addAll(Arrays.asList(c, c, c, c, c));
        }

        setCards();

    }

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
            c.setCardType(temp);
            c.setTex(AnimationState.IDLE, cardATex[temp.ordinal()]);
            c.setTex(AnimationState.HOVER, cardATex[temp.ordinal()]);
            c.setTex(AnimationState.CLICK, cardATex[temp.ordinal()]);
            i++;
            addObjectToPool(c);
        }

        for (Card c : dealerCards) {
            c.setTex(AnimationState.IDLE, (ATex) cardBack);
            c.setTex(AnimationState.HOVER, (ATex) cardBack);
            c.setTex(AnimationState.CLICK, (ATex) cardBack);
            addObjectToPool(c);
        }

    }

    public void queueChange(Card c, int id) {

        if (!changeCards.containsKey(c) && c.getChange()) {
            c.setChange(false);
            c.setPos(c.getX(), c.getY() - 2);
            changeCards.put(c, id);
        } else {
            c.setChange(true);
            c.setPos(c.getX(), c.getY() + 2);
            changeCards.remove(c);
        }

    }

    public void changeCard() {

        for (Card c : changeCards.keySet()) {
            c.setPos(c.getX(), c.getY() + 2);
            int card = enums.get(changeCards.get(c)).ordinal();
            c.setTex(AnimationState.IDLE, cardATex[card]);
            c.setTex(AnimationState.HOVER, cardATex[card]);
            c.setTex(AnimationState.CLICK, cardATex[card]);
            c.setChange(true);
            c.setCardType(enums.get(changeCards.get(c)));
        }

        int i = 10;
        for (Card c : dealerCards) {
            if (!changeCards.containsKey(c)) {
                changeCards.put(c, Integer.parseInt(c.UID));
            }

            CardType temp = enums.get(i);
            c.setCardType(temp);
            c.setTex(AnimationState.IDLE, cardATex[temp.ordinal()]);
            c.setTex(AnimationState.HOVER, cardATex[temp.ordinal()]);
            c.setTex(AnimationState.CLICK, cardATex[temp.ordinal()]);
            i++;

        }

        System.out.println("Player Cards:");
        for (Card c : cards) {
            System.out.println(c.UID + c.getCardType());
        }
        System.out.println("Dealer Cards:");
        for (Card c : dealerCards) {
            System.out.println(c.UID + c.getCardType());;
        }

        new Timer("timer_1", 3000, 0) {
            @Override
            public void run() {
                for (Card c : dealerCards) {
                    c.setTex(AnimationState.IDLE, (ATex) cardBack);
                    c.setTex(AnimationState.HOVER, (ATex) cardBack);
                    c.setTex(AnimationState.CLICK, (ATex) cardBack);
                }
                int i = 0;
                for (Card c : cards) {
                    CardType temp = enums.get(i);
                    c.setCardType(temp);
                    c.setTex(AnimationState.IDLE, cardATex[temp.ordinal()]);
                    c.setTex(AnimationState.HOVER, cardATex[temp.ordinal()]);
                    c.setTex(AnimationState.CLICK, cardATex[temp.ordinal()]);
                    i++;
                }
            }
        };

    }

    public void compareCards() {

    }

}