package our.game.util;

import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.MouseInputListener;

import our.game.core.GameManager;

public class XFrame extends JFrame implements MouseInputListener {



    private static final long serialVersionUID = 1L;

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        double maxx = this.getSize().getWidth();
        //Creates an Option pane to ask the user if he/she wants to quit
        if (maxx - x <= 50 && y < 30){
            int eingabe = JOptionPane.showConfirmDialog(null,"Wollen Sie wirklich beenden?",
                                                        "Beenden",JOptionPane.YES_NO_OPTION);

            if (eingabe == 0){
                this.dispose();
                System.exit(0);
            }

        }

        int[] pos = calcPos(e);
        GameManager.getModeInstance().clickInput(pos[0], pos[1]);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    /**
     * Calulates the position from pixel to characters
     * @param e Position of the mouse
     * @return int[] with the position as characters
     */
    private int[] calcPos(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        double refx = this.getSize().getWidth() / our.game.core.Main.X;
        double refy = this.getSize().getHeight() / our.game.core.Main.Y;
        x = (int) (x / refx);
        y = (int) (y / refy);
        return new int[]{x, y};
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        int[] pos = calcPos(e);
        GameManager.getModeInstance().hoverInput(pos[0], pos[1]);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        GameManager.getModeInstance().noHoverInput();
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    
}