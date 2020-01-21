package our.game.mode.memorymatch;

import java.util.Arrays;
import java.util.LinkedList;
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
import our.game.util.Timer;

public class MemoryMatch extends GameMode {

    protected static MemoryMatch instance;

    public final String title = "MemoryMatch";

    /*
    
    8 Cards
    4 Card Pairs
    6 Total Unique Cards
    
    -> choose 4 at random
    
    */

    public MemoryMatch() {

        if (instance != null)
            return;
        instance = this;

        Card card_return = new Card("exit", 110, 22, Reader.read("./assets/cards/mode/return_idle.atex")) {
            @Override
            public void onMousePressed(int x, int y) {
                GameManager.changeGameMode(Menu.instance);
            }
        };
        card_return.setTex(AnimationState.HOVER, Reader.read("./assets/cards/mode/exit_hover.atex"));

        addObjectToPool(card_return);

        setup();

    }

    private MMCard[] cards;

    private MMCard first = null;

    private int tries = 3;
    private int pairs = 0;

    private boolean g_lock = false;

    private Card reset;
    private Card win;
    private Card lose;
    private Card card_tries;
    private Tex tex_tries;

    /**
     * Resets the game state
     * @param rng Random
     */
    private void reset(Random rng) {
        tries = 3;
        card_tries.setTex(AnimationState.IDLE, new Tex(new String[] {"Tries left: "+tries}));
        pairs = 0;
        first = null;
        hideAll(true);
        cards = shuffleCards(cards, rng);
        redoPosition();
        lockAll(false);
        g_lock = false;

    }

    /**
     * Shuffles the Cards in an array
     * @param cards
     * @param rng
     * @return
     */
    private MMCard[] shuffleCards(MMCard[] cards, Random rng) {

        LinkedList<MMCard> l_cards = new LinkedList<MMCard>(Arrays.asList(cards));
        MMCard[] newcards = new MMCard[cards.length];
        int rand;

        for (int i = 0; i < cards.length; i++) {

            rand = rng.nextInt(l_cards.size());

            MMCard chosen_card = l_cards.get(rand);

            newcards[i] = chosen_card;

            l_cards.remove(chosen_card);

        }

        return newcards;
    }

    private int[] chooseCards(Random rng) {
        int[] chosenCards = new int[4];

        for (int i = 0; i < 4; i++) {
            int card = rng.nextInt(6);
            while (containsInt(chosenCards, card)) {
                card = rng.nextInt(6);
            }
            chosenCards[i] = card;
        }
        return chosenCards;
    }

    private void redoPosition() {
        for (int i = 0; i < 8; i++) {
            cards[i].setPos(calcX(i), calcY(i));
        }
    }

    private int calcX(int i) {
        int offset = 30;
        int card_spacing = 16;
        return offset + i * card_spacing - (int) (i / 4) * 4 * card_spacing;
    }

    private int calcY(int i) {
        int offset = 10;
        int card_spacing = 10;
        return offset + (int) (i / 4) * card_spacing;
    }

    private void setup() {

        Random rng = new Random();

        reset = new Card("reset", 110, 8, Reader.read("./assets/cards/reset.tex")) {
            @Override
            public void onHover(int x, int y) {
            }

            @Override
            public void onNoHover() {
            }

            @Override
            public void onMousePressed(int x, int y) {
                new Timer("reset_timer", 200, 0) {
                    @Override
                    public void run() {
                        reset(rng);
                    }
                };

                this.setVisible(false);
            }
        };

        reset.setVisible(false);

        addObjectToPool(reset);

        win = new Card("win", 28, 3, Reader.read("./assets/cards/mode/global/win.atex")) {
            @Override
            public void onHover(int x, int y) {
            }

            @Override
            public void onNoHover() {
            }

            @Override
            public void onMousePressed(int x, int y) {

                win.setVisible(false);

            }
        };

        win.setVisible(false);

        addObjectToPool(win);

        lose = new Card("lose", 28, 3, Reader.read("./assets/cards/mode/global/lose.atex")) {
            @Override
            public void onHover(int x, int y) {
            }

            @Override
            public void onNoHover() {
            }

            @Override
            public void onMousePressed(int x, int y) {

                lose.setVisible(false);

            }
        };

        lose.setVisible(false);

        addObjectToPool(lose);

        tex_tries = new Tex(new String[] {"Tries left: 3"});

        card_tries = new Card("score", 2, 12, tex_tries) {
            // Prevent AnimationState changes from occuring
            @Override
            public void onHover(int x, int y) { }
            @Override
            public void onNoHover() { }
            @Override
            public void onMousePressed(int x, int y) { }
        };
        addObjectToPool(card_tries);

        cards = new MMCard[8];
        tries = 3;
        pairs = 0;

        int[] choosenCards = chooseCards(rng);

        for (int i = 0; i < 8; i++) {

            int ci = (int) (i / 2);

            cards[i] = new MMCard(ci + "_" + (i % 2), calcX(i), calcY(i), cardATex[choosenCards[ci]],
                    AnimationState.IDLE_FRONT) {

                protected CardType cardType = CardType.values()[choosenCards[ci]];

                @Override
                public CardType getCardType() {
                    return cardType;
                }

                private void unlockAndHide() {
                    mm_lock = false;
                    mm_setHidden(true);
                    first.mm_lock = false;
                    first.mm_setHidden(true);
                    // setAnimationState(AnimationState.TURN_TO_BACK);
                    first = null;
                    g_lock = false;
                }

                @Override
                public void onHover(int x, int y) {
                }

                @Override
                public void onNoHover() {
                }

                @Override
                public void onMousePressed(int x, int y) {
                    if (g_lock)
                        return;
                    if (!mm_lock) {
                        mm_setHidden(!mm_isHidden());
                        if (!mm_isHidden()) {
                            if (first == null) {
                                first = this;
                                mm_lock = true;
                            } else {
                                if (first.getCardType().equals(this.cardType)) {
                                    mm_lock = true;
                                    first = null;
                                    pairs++;
                                    if (pairs >= 4) {
                                        endGame(GAME_WIN);
                                    }
                                } else {
                                    tries--;
                                    if (tries <= 0) {
                                        endGame(GAME_LOSE);
                                        return;
                                    }
                                    g_lock = true;
                                    new Timer("hide_timer", 2000, 0) {

                                        @Override
                                        public void run() {
                                            unlockAndHide();
                                        }

                                    };

                                }
                            }

                        } else {
                            first = null;
                        }

                    }

                    card_tries.setTex(AnimationState.IDLE, new Tex(new String[] {"Tries left: "+tries}));

                }
            };

            cards[i].setTex(AnimationState.IDLE_BACK, Reader.read("./assets/cards/card_back.tex"));
            cards[i].setTex(AnimationState.TURN_TO_BACK, cardTurnBackATex[choosenCards[ci]]);
            cards[i].setTex(AnimationState.TURN_TO_FRONT, cardTurnFrontATex[choosenCards[ci]]);

            cards[i].setAnimationState(AnimationState.IDLE_BACK);

            addObjectToPool(cards[i]);
        }

        cards = shuffleCards(cards, rng);
        redoPosition();

    }

    public static final int GAME_LOSE = 0;
    public static final int GAME_WIN = 1;

    private void lockAll(boolean hide) {
        for (MMCard c : cards) {
            c.mm_lock = hide;
        }
    }

    private void hideAll(boolean hide) {
        for (MMCard c : cards) {
            c.mm_setHidden(true);
        }
    }

    private void banner_flyin(Card card) {

        card.setPos(card.getX(), 3 + 19);
        card.setVisible(true);
        new Timer("card_flyin", 200, 20) {
            @Override
            public void run() {
                new Timer("card_display", 4000, 0) {
                    @Override
                    public void run() {
                        new Timer("card_flyout", 100, 20) {
                            @Override
                            public void run() {
                                card.setVisible(false);
                                card.setPos(card.getX(), card.getY() + 10);
                            }

                            @Override
                            public void runInterval() {
                                card.setPos(card.getX(), card.getY() - 2);
                            }
                        };

                    }
                };
            }

            @Override
            public void runInterval() {
                card.setPos(card.getX(), card.getY() - 2);
            }
        };

    }

    private void endGame(int state) {
        switch (state) {
        case GAME_WIN:
            lockAll(true);
            reset.setVisible(true);
            banner_flyin(win);
            break;
        case GAME_LOSE:
            lockAll(true);
            reset.setVisible(true);
            banner_flyin(lose);
            break;
        }
    }

    private boolean containsInt(int[] choosenCards, int item) {

        for (int obj : choosenCards) {
            if (obj == item) {
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

    private ATex[] cardTurnBackATex = new ATex[] { (ATex) Reader.read("./assets/cards/mode/global/upvote_turn.atex"),
            (ATex) Reader.read("./assets/cards/mode/global/star_turn.atex"),
            (ATex) Reader.read("./assets/cards/mode/global/heart_turn.atex"),
            (ATex) Reader.read("./assets/cards/mode/global/flower_turn.atex"),
            (ATex) Reader.read("./assets/cards/mode/global/cloud_turn.atex"),
            (ATex) Reader.read("./assets/cards/mode/global/downvote_turn.atex") };

    private ATex[] cardTurnFrontATex = new ATex[] {
            ((ATex) Reader.read("./assets/cards/mode/global/upvote_turn.atex", true)).reversed(),
            ((ATex) Reader.read("./assets/cards/mode/global/star_turn.atex", true)).reversed(),
            ((ATex) Reader.read("./assets/cards/mode/global/heart_turn.atex", true)).reversed(),
            ((ATex) Reader.read("./assets/cards/mode/global/flower_turn.atex", true)).reversed(),
            ((ATex) Reader.read("./assets/cards/mode/global/cloud_turn.atex", true)).reversed(),
            ((ATex) Reader.read("./assets/cards/mode/global/downvote_turn.atex", true)).reversed() };

    @Override
    public ATex[] setPreview() {
        return new ATex[] { (ATex) Reader.read("./assets/cards/preview/memorymatch/idle.tex"),
                (ATex) Reader.read("./assets/cards/preview/memorymatch/hover.atex"),
                (ATex) Reader.read("./assets/cards/preview/memorymatch/click.tex") };
    }

}