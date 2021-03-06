package our.game.mode.picturepoker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import our.game.core.GameManager;
import our.game.core.Main;
import our.game.core.Reader;
import our.game.gameobjects.AnimationState;
import our.game.gameobjects.Card;
import our.game.gameobjects.CardType;
import our.game.mode.GameMode;
import our.game.mode.Menu;
import our.game.util.ATex;
import our.game.util.Tex;
import our.game.util.Timer;

public class PicturePoker extends GameMode {

    protected static PicturePoker instance;

    public String title = "PicturePoker";

    private int score = 0;
    private int i = 10;

    private ArrayList<CardType> enums = new ArrayList<CardType>();
    private HashMap<Card, Integer> changeCards = new HashMap<Card, Integer>();

    private ATex cardBack = (ATex) Reader.read("./assets/cards/card_back.tex");
    private Tex scoreTex = new Tex(
            new String[] { "Score: " + score, "Upvote  ", "Star    ", "Heart   ", "Flower  ", "Cloud   ", "Downvote" });

    private boolean clicked = false;

    private Card card_return;
    private Card confirm;
    private Card reset;

    //Overwriting Functions of the Card Datatype
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

    private Card[] dealerCards = new Card[] {
        new Card("5", 10, 3, (ATex) cardBack),
        new Card("6", 31, 3, (ATex) cardBack),
        new Card("7", 52, 3, (ATex) cardBack),
        new Card("8", 73, 3, (ATex) cardBack),
        new Card("9", 94, 3, (ATex) cardBack)
    };

    Card scoreCard = new Card("score", 7, 12, scoreTex);

    private ATex[] cardATex = new ATex[] {
        (ATex) Reader.read("./assets/cards/mode/global/upvote.tex"),
        (ATex) Reader.read("./assets/cards/mode/global/star.tex"),
        (ATex) Reader.read("./assets/cards/mode/global/heart.tex"),
        (ATex) Reader.read("./assets/cards/mode/global/flower.tex"),
        (ATex) Reader.read("./assets/cards/mode/global/cloud.tex"),
        (ATex) Reader.read("./assets/cards/mode/global/downvote.tex")
    };

    private ATex[] cardATexTurn = new ATex[] {
        ((ATex) Reader.read("./assets/cards/mode/global/upvote_turn.atex", true)).reversed(),
        ((ATex) Reader.read("./assets/cards/mode/global/star_turn.atex", true)).reversed(),
        ((ATex) Reader.read("./assets/cards/mode/global/heart_turn.atex", true)).reversed(),
        ((ATex) Reader.read("./assets/cards/mode/global/flower_turn.atex", true)).reversed(),
        ((ATex) Reader.read("./assets/cards/mode/global/cloud_turn.atex", true)).reversed(),
        ((ATex) Reader.read("./assets/cards/mode/global/downvote_turn.atex", true)).reversed()
    };

    public PicturePoker() {

        if (instance != null)
            return;

        instance = this;

        card_return = new Card("exit", 110, 22, Reader.read("./assets/cards/mode/return_idle.atex")) {

            @Override
            public void onMousePressed(int x, int y) {
                GameManager.changeGameMode(Menu.instance);
            }
        };

        confirm = new Card("confirm", 28, 13, Reader.read("./assets/cards/mode/global/confirm.tex")) {

            @Override
            public void onMousePressed(int x, int y) {

                PicturePoker.instance.changeCard();
            }
        };
        addObjectToPool(confirm);

        reset = new Card("reset", 110, 12, Reader.read("./assets/cards/reset.tex")) {

            @Override
            public void onMousePressed(int x, int y) {

                resetGame();
            }
        };
        addObjectToPool(reset);
        reset.setVisible(false);

        reset.setTex(AnimationState.HOVER, Reader.read("./assets/cards/reset.tex"));

        confirm.setTex(AnimationState.HOVER, Reader.read("./assets/cards/mode/global/confirm.tex"));

        card_return.setTex(AnimationState.HOVER, Reader.read("./assets/cards/mode/exit_hover.atex"));

        scoreCard.setTex(AnimationState.IDLE, scoreTex);
        scoreCard.setTex(AnimationState.HOVER, scoreTex);

        addObjectToPool(scoreCard);

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

    /**
     * First time setup of the cards
     */
    public void setCards() {

        Collections.shuffle(enums);

        int i = 0;
        for (Card c : cards) {
            CardType temp = enums.get(i);
            c.setCardType(temp);
            c.setTex(AnimationState.IDLE, cardATex[temp.ordinal()]);
            c.setTex(AnimationState.HOVER, cardATex[temp.ordinal()]);
            i++;
            addObjectToPool(c);
        }

        for (Card c : dealerCards) {
            c.setTex(AnimationState.IDLE, (ATex) cardBack);
            c.setTex(AnimationState.HOVER, (ATex) cardBack);
            addObjectToPool(c);
        }

    }

    /**
     * Queues chance of the cards
     * @param c The specific card
     * @param id The id of the card to be set with in enums
     */
    public void queueChange(Card c, int id) {

        if (!changeCards.containsKey(c) && c.getChange()) {
            c.setChange(false);
            c.setPos(c.getX(), c.getY() - 2);
            changeCards.put(c, id);
        } else if (changeCards.containsKey(c)) {
            c.setChange(true);
            c.setPos(c.getX(), c.getY() + 2);
            changeCards.remove(c);
        }

    }

    /**
     * Chances the queued cards, also sets the Dealers cards
     */
    public void changeCard() {
        if (clicked)
            return;

        clicked = true;
        for (Card c : changeCards.keySet()) {
            c.setPos(c.getX(), c.getY() + 2);
            int card = enums.get(changeCards.get(c)).ordinal();
            c.setTex(AnimationState.IDLE, cardATex[card]);
            c.setTex(AnimationState.HOVER, cardATex[card]);
            c.setChange(true);
            c.setCardType(enums.get(changeCards.get(c)));
        }
        changeCards.clear();

        //Used for a slow turning of the cards
        new Timer("Timer_1", 3000, 500) {
            @Override
            public void runInterval() {
                CardType temp = enums.get(i);
                dealerCards[i - 10].setCardType(temp);
                dealerCards[i - 10].setTex(AnimationState.TURN_TO_FRONT, cardATexTurn[temp.ordinal()]);
                dealerCards[i - 10].setTex(AnimationState.IDLE_FRONT, cardATex[temp.ordinal()]);
                dealerCards[i - 10].setAnimationState(AnimationState.TURN_TO_FRONT);
                dealerCards[i - 10].setTex(AnimationState.IDLE, cardATex[temp.ordinal()]);
                dealerCards[i - 10].setTex(AnimationState.HOVER, cardATex[temp.ordinal()]);
                i++;
            }
        };
        i = 10;

        new Timer("timer_2", 4000, 0) {
            @Override
            public void run() {

                if (Main.debug) {

                    System.out.println("Player Cards:");
                    for (Card c : cards) {
                        System.out.println(c.UID + c.getCardType());
                    }
                    System.out.println("Dealer Cards:");
                    for (Card c : dealerCards) {
                        System.out.println(c.UID + c.getCardType());

                    }
                }
                compareCards();
            }
        };
    }

    /**
     * Logic to compare the cards of the user and the dealer
     */
    public void compareCards() {

        int ups = 0;
        int stars = 0;
        int hearts = 0;
        int flowers = 0;
        int cloud = 0;
        int down = 0;
        for (int j = cards.length - 1; j >= 0; j--) {
            for (int k = 0; k <= j; k++) {
                if (cards[k].getCardType().toString().equals(cards[j].getCardType().toString())) {
                    if (!(j == k)) {
                        if (cards[k].getCardType().equals(CardType.UPVOTE))
                            ups += 1;
                        else if (cards[k].getCardType().equals(CardType.STAR))
                            stars += 1;
                        else if (cards[k].getCardType().equals(CardType.HEART))
                            hearts += 1;
                        else if (cards[k].getCardType().equals(CardType.FLOWER))
                            flowers += 1;
                        else if (cards[k].getCardType().equals(CardType.CLOUD))
                            cloud += 1;
                        else if (cards[k].getCardType().equals(CardType.DOWNVOTE))
                            down += 1;
                    }
                }
            }
        }
        int dups = 0;
        int dstars = 0;
        int dhearts = 0;
        int dflowers = 0;
        int dcloud = 0;
        int ddown = 0;
        for (int j = dealerCards.length - 1; j >= 0; j--) {
            for (int k = 0; k <= j; k++) {
                if (dealerCards[k].getCardType().toString().equals(dealerCards[j].getCardType().toString())) {
                    if (!(j == k)) {
                        if (dealerCards[k].getCardType().equals(CardType.UPVOTE))
                            dups += 1;
                        else if (dealerCards[k].getCardType().equals(CardType.STAR))
                            dstars += 1;
                        else if (dealerCards[k].getCardType().equals(CardType.HEART))
                            dhearts += 1;
                        else if (dealerCards[k].getCardType().equals(CardType.FLOWER))
                            dflowers += 1;
                        else if (dealerCards[k].getCardType().equals(CardType.CLOUD))
                            dcloud += 1;
                        else if (dealerCards[k].getCardType().equals(CardType.DOWNVOTE))
                            ddown += 1;
                    }
                }
            }
        }
        Collections.shuffle(enums);
        if (Main.debug) {
            String a = String.format("PLAYER:\nUpvote: %d, Star: %d, Heart: %d, Flower: %d, Cloud: %d, Downvote: %d",
                    ups, stars, hearts, flowers, cloud, down);
            String b = String.format("DEALER:\nUpvote: %d, Star: %d, Heart: %d, Flower: %d, Cloud: %d, Downvote: %d",
                    dups, dstars, dhearts, dflowers, dcloud, ddown);
            System.out.println(a + "\n" + b);
        }

        if ((ups + stars + hearts + flowers + cloud + down) > (dups + dstars + dhearts + dflowers + dcloud + ddown)) {
            confirm.setTex(AnimationState.IDLE, (ATex) Reader.read("./assets/cards/mode/global/win.tex"));
            confirm.setTex(AnimationState.HOVER, (ATex) Reader.read("./assets/cards/mode/global/win.tex"));
            score++;
        } else if ((ups + stars + hearts + flowers + cloud + down) < (dups + dstars + dhearts + dflowers + dcloud
                + ddown)) {
            confirm.setTex(AnimationState.IDLE, (ATex) Reader.read("./assets/cards/mode/global/defeat.tex"));
            confirm.setTex(AnimationState.HOVER, (ATex) Reader.read("./assets/cards/mode/global/defeat.tex"));
            if (score > 0)
                score--;
        } else {
            int dppoints = 0;
            int ddpoints = 0;
            for (int j = 0; j < cards.length; j++) {
                if ((ups + stars + hearts + flowers + cloud + down) > 0) {
                    if (ups > 0 && cards[j].getCardType().equals(CardType.UPVOTE))
                        dppoints += 20;
                    if (stars > 0 && cards[j].getCardType().equals(CardType.STAR))
                        dppoints += 10;
                    if (hearts > 0 && cards[j].getCardType().equals(CardType.HEART))
                        dppoints += 5;
                    if (flowers > 0 && cards[j].getCardType().equals(CardType.FLOWER))
                        dppoints += 3;
                    if (cloud > 0 && cards[j].getCardType().equals(CardType.CLOUD))
                        dppoints += 2;
                    if (down > 0 && cards[j].getCardType().equals(CardType.DOWNVOTE))
                        dppoints += 1;
                } else {
                    if ((ups + stars + hearts + flowers + cloud + down) == 0 || (dups + dstars + dhearts + dflowers
                            + dcloud + ddown) == (ups + stars + hearts + flowers + cloud + down)) {
                        if (cards[j].getCardType().equals(CardType.UPVOTE))
                            dppoints += 20;
                        if (cards[j].getCardType().equals(CardType.STAR))
                            dppoints += 10;
                        if (cards[j].getCardType().equals(CardType.HEART))
                            dppoints += 5;
                        if (cards[j].getCardType().equals(CardType.FLOWER))
                            dppoints += 3;
                        if (cards[j].getCardType().equals(CardType.CLOUD))
                            dppoints += 2;
                        if (cards[j].getCardType().equals(CardType.DOWNVOTE))
                            dppoints += 1;
                    }
                }
            }
            for (int j = 0; j < dealerCards.length; j++) {
                if ((dups + dstars + dhearts + dflowers + dcloud + ddown) > 0) {
                    if (dups > 0 && dealerCards[j].getCardType().equals(CardType.UPVOTE))
                        ddpoints += 20;
                    if (dstars > 0 && dealerCards[j].getCardType().equals(CardType.STAR))
                        ddpoints += 10;
                    if (dhearts > 0 && dealerCards[j].getCardType().equals(CardType.HEART))
                        ddpoints += 5;
                    if (dflowers > 0 && dealerCards[j].getCardType().equals(CardType.FLOWER))
                        ddpoints += 3;
                    if (dcloud > 0 && dealerCards[j].getCardType().equals(CardType.CLOUD))
                        ddpoints += 2;
                    if (ddown > 0 && dealerCards[j].getCardType().equals(CardType.DOWNVOTE))
                        ddpoints += 1;
                } else {
                    if (((dups + dstars + dhearts + dflowers + dcloud + ddown) == 0) || (dups + dstars + dhearts
                            + dflowers + dcloud + ddown) == (ups + stars + hearts + flowers + cloud + down)) {
                        if (dealerCards[j].getCardType().equals(CardType.UPVOTE))
                            ddpoints += 20;
                        if (dealerCards[j].getCardType().equals(CardType.STAR))
                            ddpoints += 10;
                        if (dealerCards[j].getCardType().equals(CardType.HEART))
                            ddpoints += 5;
                        if (dealerCards[j].getCardType().equals(CardType.FLOWER))
                            ddpoints += 3;
                        if (dealerCards[j].getCardType().equals(CardType.CLOUD))
                            ddpoints += 2;
                        if (dealerCards[j].getCardType().equals(CardType.DOWNVOTE))
                            ddpoints += 1;
                    }
                }
            }

            if (dppoints > ddpoints) {
                confirm.setTex(AnimationState.IDLE, (ATex) Reader.read("./assets/cards/mode/global/win.tex"));
                confirm.setTex(AnimationState.HOVER, (ATex) Reader.read("./assets/cards/mode/global/win.tex"));
                score++;
            } else if (ddpoints > dppoints) {
                confirm.setTex(AnimationState.IDLE, (ATex) Reader.read("./assets/cards/mode/global/defeat.tex"));
                confirm.setTex(AnimationState.HOVER, (ATex) Reader.read("./assets/cards/mode/global/defeat.tex"));
                if (score > 0)
                    score--;
            } else {
                confirm.setTex(AnimationState.IDLE, (ATex) Reader.read("./assets/cards/mode/global/draw.tex"));
                confirm.setTex(AnimationState.HOVER, (ATex) Reader.read("./assets/cards/mode/global/draw.tex"));
            }
        }
        scoreTex = new Tex(new String[] { "Score: " + score, "Upvote  ", "Star    ", "Heart   ", "Flower  ", "Cloud   ",
                "Downvote" });
        scoreCard.setTex(AnimationState.IDLE, scoreTex);
        scoreCard.setTex(AnimationState.HOVER, scoreTex);

        reset.setVisible(true);
    }

    /**
     * Resets the game to the start mode, keeps the score
     */
    public void resetGame() {

        for (Card c : dealerCards) {
            c.setTex(AnimationState.IDLE, (ATex) cardBack);
            c.setTex(AnimationState.HOVER, (ATex) cardBack);
            // c.setTex(AnimationState.CLICK, (ATex) cardBack);
        }
        int i = 0;
        for (Card c : cards) {
            CardType temp = enums.get(i);
            c.setCardType(temp);
            c.setTex(AnimationState.IDLE, cardATex[temp.ordinal()]);
            c.setTex(AnimationState.HOVER, cardATex[temp.ordinal()]);
            // c.setTex(AnimationState.CLICK, cardATex[temp.ordinal()]);
            i++;
            c.setChange(true);
        }
        confirm.setTex(AnimationState.IDLE, Reader.read("./assets/cards/mode/global/confirm.tex"));
        confirm.setTex(AnimationState.HOVER, Reader.read("./assets/cards/mode/global/confirm.tex"));
        clicked = false;
        reset.setVisible(false);
    }
}
