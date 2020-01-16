package our.game.gameobjects;

import our.game.util.Tex;

public class Card extends GameObject {

    public Card(String uid, int x, int y, Tex idle) {
        super(uid, x, y, idle);
        // width = idle.width;
        // height = idle.height;
    }

    protected CardType cardType;

    public CardType getCardType() {
        return cardType;
    }

}