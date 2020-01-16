package our.game.gameobjects;

import java.util.HashMap;

import our.game.util.ATex;
import our.game.util.Tex;

public class GameObject {

    public final String UID;

    protected HashMap<AnimationState, Tex> texture = new HashMap<AnimationState, Tex>();

    protected int x;
    protected int y;

    protected int width;
    protected int height;

    protected AnimationState state = AnimationState.IDLE;

    private boolean isVisible = true;
    private boolean pauseInvisibleFrameAdvance = true;

    private boolean hidden = false;
    private boolean change = true;

    public GameObject(String uid, int x, int y) {
        UID = uid;
        this.x = x;
        this.y = y;
    }

    public GameObject(String uid, int x, int y, Tex idle) {
        UID = uid;
        this.x = x;
        this.y = y;
        this.width = idle.width;
        this.height = idle.height;
        texture.put(AnimationState.IDLE, idle);
    }

    /**
     * Returns this GameObjects X Position
     * @return X Position in Console Characters
     */
    public int getX() {
        return x;
    }

    /**
     * Returns this GameObjects Y Position
     * @return Y Position in Console Characters
     */
    public int getY() {
        return y;
    }

    /**
     * Returns this GameObjects Width
     * @return Width in Console Characters
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns this GameObjects Height
     * @return Height in Console Characters
     */
    public int getHeight() {
        return height;
    }

    public Tex getObjectTex() { // call this from gamemode's gameobject pool
        return texture.get(state);
    }

    /**
     * Set the visibility State of the GameObject
     * @param vis Is the GameObject visible
     */
    public void setVisible(boolean vis) {
        isVisible = vis;
    }

    public void setTex(AnimationState s, Tex tex) {
        texture.put(s, tex);
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
     * Get's called every frame
     */
    public void frameAdvance() {
        nextFrame();
    }

    /**
     * Advances the current animations frame counter.
     */
    public void nextFrame() {
        Tex temp = texture.get(state);
        if (temp.isAnimated())
            ((ATex) temp).nextFrame();
    }

    /**
     * If the Object is Visible
     * @return objects Visibility state
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * If true stops the GameObject from executing its frameAdvance() function if it's not visible.
     */
    public void setPauseInvisibleFrameAdvance(boolean pifa) {
        pauseInvisibleFrameAdvance = pifa;
    }

    /**
     * If the GameObject should stop its code execution when it's not visible
     * @return objects frameAdvance() execution state.
     */
    public boolean shouldPauseInvisibleFrameAdvance() {
        return pauseInvisibleFrameAdvance;
    }

    /**
     * Wether the GameObject should execute its input / frameAdvance functions
     * @return boolean execution state
     */
    public boolean shouldExecute() {
        if (!isVisible()) {
            if (!shouldPauseInvisibleFrameAdvance())
                return true;
        } else {
            return true;
        }

        return false;
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean getChange() {
        return change;
    }

    public void setChange(boolean change) {
        this.change = change;
    }

    public boolean getHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

}