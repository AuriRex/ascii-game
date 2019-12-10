package our.game.util;

import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.MouseInputListener;

import our.game.core.GameManager;

public class XFrame extends JFrame implements MouseInputListener {

    private static final long serialVersionUID = 1L;

    private static XFrame instance = null;

    private XPanel xp;

    public XFrame() {
        if(instance != null) return;
        instance = this;
        xp = new XPanel();
        getRootPane().getContentPane().add(xp);
    }

    /**
     * Creates a Yes/No option to exit the game
     */
    public static void exitWindow() {
        //TODO replace this with yes/no cards
        int eingabe = JOptionPane.showConfirmDialog(null,"Wollen Sie wirklich beenden?",
        "Beenden",JOptionPane.YES_NO_OPTION);

        if (eingabe == 0) {
            instance.dispose();
            System.exit(0);
        }
    }

    public static void printReadyFrameDBG() {
        if(instance.xp != null) instance.xp.repaint();
    }

    /**
     * Calulates the position from pixel to characters
     * @param e Position of the mouse
     * @return int[] with the position as characters
     */
    public static int[] calcPos(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        double refx = instance.getSize().getWidth() / our.game.core.Main.X;
        double refy = instance.getSize().getHeight() / our.game.core.Main.Y;
        x = (int) (x / refx);
        y = (int) (y / refy);
        return new int[]{x, y};
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        double maxx = this.getSize().getWidth();
        //Creates an Option pane to ask the user if he/she wants to quit
        if (maxx - x <= 50 && y < 30){
            exitWindow();

        }

        int[] pos = calcPos(e);
        GameManager.getModeInstance().clickInput(pos[0], pos[1]);
    }

    @Override
    public void mousePressed(MouseEvent e) {  
    }

    @Override
    public void mouseReleased(MouseEvent e) {     
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
    
}