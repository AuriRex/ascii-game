package our.game.util;

import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.MouseInputListener;

public class XFrame extends JFrame implements MouseInputListener {



    private static final long serialVersionUID = 1L;

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        double maxx = this.getSize().getWidth();
        if (maxx - x <= 50 && y < 30){

            int eingabe = JOptionPane.showConfirmDialog(null,"Wollen Sie wirklich beenden?",
                                                        "Beenden",JOptionPane.YES_NO_OPTION);

            if (eingabe == 0){
                this.dispose();
                System.exit(0);
            }

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
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