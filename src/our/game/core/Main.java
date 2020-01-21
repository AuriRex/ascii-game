package our.game.core;

public class Main {

    public final static int X = 128;
    public final static int Y = 32;
    public static boolean debug = false; // TODO add option to disable / enable

    public static void main(String[] args) throws Exception {

        // UNUSED
        // ResourceManager.init();
        // ResourceManager.instance.loadResources("./assets/");
        new GameManager(X, Y);

    }

}