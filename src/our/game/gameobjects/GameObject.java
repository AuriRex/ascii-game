package our.game.gameobjects;

import java.util.HashMap;

import our.game.util.ATex;
import our.game.util.Tex;

public class GameObject {

    public final String UID;
    
    private HashMap<AnimationState, Tex> texture = new HashMap<AnimationState, Tex>();

    public int x;
    public int y;

    public int width;
    public int height;

    AnimationState state = AnimationState.IDLE;

    public GameObject(String uid, int x, int y) {
        UID = uid;
        this.x = x;
        this.y = y;
    }

    public GameObject(String uid, int x, int y, Tex idle) {
        UID = uid;
        this.x = x;
        this.y = y;
        texture.put(AnimationState.IDLE, idle);
    }

    public void setTex(AnimationState s, Tex tex) {
        texture.put(s, tex);
    }

    public Tex getObjectTex() { // call this from gamemode's gameobject pool
        return texture.get(state);
    }

    /**
     * Gets called everytime the Mouse moves.
     * @param x position in console characters
     * @param y position in console characters
     */
    public void onHover(int x, int y) {
        state = AnimationState.HOVER;
    }

    /**
     * Gets called once the Mouse leaves the frame
     */
    public void onNoHover() {
        state = AnimationState.IDLE;
    }

    /**
     * Gets called once the Mouse is pressed on the frame
     * @param x position in console characters
     * @param y position in console characters
     */
    public void onMousePressed(int x, int y) {
        state = AnimationState.CLICK;
    }

    /**
     * Advances the current animations frame counter.
     */
	public void nextFrame() {
        Tex temp = texture.get(state);
        if(temp.isAnimated()) ((ATex) temp).nextFrame();
	}

}