package our.game.util;

public class ATex extends Tex {

    private Tex[] texArray;

    private int frameCounter = 0;

    // private final boolean animated = true;

    private final int frames;

    public boolean reversed = false;

    /**
     * Animated Tex
     * @param texArray Tex[] consisting of individual animation frames
     */
    public ATex(Tex[] texArray) {
        super(texArray[0].getFrame()); // set width & height

        this.texArray = texArray;
        this.frames = texArray.length;
    }

    @Override
    public boolean isAnimated() {
        return true;
    }

    /**
     * @return Returns the String[] from the Tex Object at the current framecount
     */
    @Override
    public String[] getFrame() {
        return texArray[frameCounter].getFrame();
    }

     /**
     * @return Returns a Tex Object at the current framecount
     */
    @Override
    public Tex getTex() {
        return texArray[frameCounter];
    }

    private boolean frame_isLast = false;

    /**
     * increments the frame counter
     */
    @Override
    public void nextFrame() {
        frame_isLast = false;
        if(reversed) {
            frameCounter--;
            if(frameCounter < 0) {
                frameCounter = frames-1;
                frame_isLast = true;
            }
        } else {
            frameCounter++;
            if(frameCounter >= frames) {
                frameCounter = 0;
                frame_isLast = true;
            }
        }
        
    }

    /**
     * Returns true if the last frame of the animation was displayed
     * @return boolean
     */
    public boolean isOnLastFrame() {
        return frame_isLast;
    }

    public Tex[] getTexArray() {
        return texArray;
    }

	public ATex reversed() {
        frameCounter = frames - 1;
        this.reversed = !this.reversed;
		return this;
	}

}