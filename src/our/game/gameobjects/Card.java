package our.game.gameobjects;

import our.game.util.ATex;
import our.game.util.Tex;

public class Card extends GameObject {

    public Card(String uid, int x, int y, Tex idle) {
        super(uid, x, y, idle);
    }

    public Card(String uid, int x, int y, Tex idle, AnimationState as) {
        super(uid, x, y, idle, as);
    }

    protected CardType cardType;

    /**
     * @return the Card Objects CardType
     */
    public CardType getCardType() {
        return cardType;
    }

    /**
     * Sets the Card Objects CardType
     * @param cardType
     */
    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    /**
     * Called once an ATex Object reaches its last frame in its animation.
     * @param at The ATex Object that hit the last frame.
     */
    public void onLastFrameHit(ATex at) {
        AnimationState currentAS = getAnimationState();

        if( currentAS.equals(AnimationState.TURN_TO_FRONT) ) {
            setAnimationState(AnimationState.IDLE_FRONT);
        }

        if( currentAS.equals(AnimationState.TURN_TO_BACK) ) {
            setAnimationState(AnimationState.IDLE_BACK);
        }
    }

}