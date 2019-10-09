package our.game.util;

public class Tex {

    // private final boolean animated = false;

    private String[] tex;

    public final int height;
    public final int width;

    public Tex(String[] tex) {

        this.tex = tex;
        width = tex[0].length();
        height = tex.length;

    }

    public boolean isAnimated() {
        return false;
    }

    /**
     * @return Returns one Tex as String[]
     */
    public String[] getFrame() {
        return this.tex;
    }

    public Tex getTex() {
        return this;
    }

    public void nextFrame() {

    }


    public static boolean checkTex(ATex tex) {
        return checkTex((Tex) tex);
    }

    public static boolean checkTex(Tex tex) {
        // 4 x 4
        // [####,####,####,####]

        if(tex.isAnimated()) {
            ATex tmp = (ATex) tex;
            Tex[] texArray = tmp.getTexArray();
            for(Tex t : texArray) {
                String[] texFrame = t.getFrame();
                int last = texFrame[0].length();
                for (String s : texFrame) {
                    if(last != s.length()) return false;
                    last = s.length();
                }
            }
        } else {
            String[] texFrame = tex.getFrame();
            int last = texFrame[0].length();
            for (String s : texFrame) {
                if(last != s.length()) return false;
                last = s.length();
            }
        }

        return true;
    }

}