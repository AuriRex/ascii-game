package our.game.gameobjects;

import our.game.util.ATex;

public class Preview {
    
    public final ATex idle;
    public final ATex hover;
    public final ATex click;

    /**
     * 
     * @param idle Animated idle texture
     * @param hover Animated hover texture
     * @param click Animated click texture
     */
    public Preview(ATex idle, ATex hover, ATex click) {
        this.idle = idle;
        this.hover = hover;
        this.click = click;
    }
}