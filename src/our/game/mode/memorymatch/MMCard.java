package our.game.mode.memorymatch;

import our.game.gameobjects.AnimationState;
import our.game.gameobjects.Card;
import our.game.gameobjects.CardType;
import our.game.util.Tex;

public class MMCard extends Card {

    public MMCard(String uid, int x, int y, Tex idle) {
        super(uid, x, y, idle);
    }

    public MMCard(String uid, int x, int y, Tex idle, AnimationState as) {
        super(uid, x, y, idle, as);
    }

    protected boolean mm_hidden = true;

    protected boolean mm_lock = false;

    @Override
    public CardType getCardType() {
        return cardType;
    }

    public boolean mm_isHidden() {
        return mm_hidden;
    }

    public void mm_setHidden(boolean h) {
        mm_hidden = h;
        if(h) {
            if(!getAnimationState().equals(AnimationState.TURN_TO_BACK) || !getAnimationState().equals(AnimationState.IDLE_BACK))
                setAnimationState(AnimationState.TURN_TO_BACK);
        } else {
            if(!getAnimationState().equals(AnimationState.TURN_TO_FRONT) || !getAnimationState().equals(AnimationState.IDLE_FRONT))
                setAnimationState(AnimationState.TURN_TO_FRONT);
        }
    }

}