package our.game.mode;

// This is a test :)
interface GMMethods {

    public void frameAdvance();

    /**
     * 
     * @param x X Coordinate in Console Characters
     * @param y Y Coordinate in Console Characters
     */
    public void hoverInput(int x, int y);

    /**
     * Gets called once the cursor leaves the frame / console
     */
    public void noHoverInput();

    /**
     * Gets called on Mouse click
     * @param x = X Coordinate in Console Characters
     * @param y = Y Coordinate in Console Characters
     */
    public void clickInput(int x, int y);

}