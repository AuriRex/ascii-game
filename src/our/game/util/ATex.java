package our.game.util;

public class ATex extends Tex {

    private Tex[] texArray;

    private int frameCounter = 0;

    // private final boolean animated = true;

    private final int frames;

    public boolean reversed = false;

    public ATex(Tex[] texArray) {
        super(texArray[0].getFrame()); // set width & height

        this.texArray = texArray;
        this.frames = texArray.length;
    }

    @Override
    public boolean isAnimated() {
        return true;
    }

    @Override
    public String[] getFrame() {
        return texArray[frameCounter].getFrame();
    }

    @Override
    public Tex getTex() {
        return texArray[frameCounter];
    }

    @Override
    public void nextFrame() {
        if(reversed) {
            frameCounter--;
            if(frameCounter < 0) {
                frameCounter = frames-1;
            }
        } else {
            frameCounter++;
            if(frameCounter >= frames) {
                frameCounter = 0;
            }
        }
        
    }

    public Tex[] getTexArray() {
        return texArray;
    }

}