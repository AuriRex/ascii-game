package our.game.util;

public class FontManager {

    private static FontManager instance;

    public static FontManager getInstance() {
        return instance;
    }



    public FontManager() {

        if(instance != null) return;
        instance = this;



    }

    public void addFontPart(Font f) {
        
    }

}