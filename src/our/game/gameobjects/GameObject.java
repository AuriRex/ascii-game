package our.game.gameobjects;

import our.game.util.ATex;

public class GameObject {

    public final int UID;

    // private static int gameobject_count = 0;

    // private static void d() {

    // }

    private ATex texture = null;

    public int x;
    public int y;

    public int width;
    public int height;

    public GameObject(int x, int y) {

        UID = 0; // TODO: not this

    }

    public void onHover(int x, int y) {

    }

    public void onMousePressed(int x, int y) {

    }

}