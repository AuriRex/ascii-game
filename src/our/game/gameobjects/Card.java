package our.game.gameobjects;

import our.game.util.ATex;
import our.game.util.Tex;

public class Card extends GameObject {

    public Card(String uid, int x, int y, Tex idle) {
        super(uid, x, y, idle);
        // width = idle.width;
        // height = idle.height;
    }

    public Card(String uid, int x, int y, Tex idle, AnimationState as) {
        super(uid, x, y, idle, as);
    }

    protected CardType cardType;

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

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