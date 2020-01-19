package our.game.gameobjects;

import our.game.util.ATex;

public class Preview {
    
    public final ATex idle;
    public final ATex hover;
    public final ATex extra;

    /**
     * 
     * @param idle Animated idle texture
     * @param hover Animated hover texture
     * @param extra Animated extra texture
     */
    public Preview(ATex idle, ATex hover, ATex extra) {
        this.idle = idle;
        this.hover = hover;
        this.extra = extra;
    }

    public void onClick() {

    }

}