package our.game.util;

import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import java.awt.Color;
import java.awt.Dimension;

import our.game.core.GameManager;
import our.game.core.Main;

public class XFrame extends JFrame implements MouseInputListener {

    private static final long serialVersionUID = 1L;

    private static XFrame instance = null;

    private static XFrame f;
    private XPanel xp;
    public static Dimension panel_dimension;

    public XFrame() {
        if (instance != null)
            return;
        instance = this;
        xp = new XPanel(this);
        getRootPane().getContentPane().add(xp);
        pack();
    }

    /**
     * Centers the text to be in the exact middle of the console after Calibration
     * 
     * @param txt The String to be placed in the middle
     * @param x   The width in characters
     * @return String txt with leading spaces
     */
    public String centerText(String txt, int x) {
        String spaces = "";
        int i = 0;
        while (((x / 2) - txt.length() / 2) > i) {
            spaces += " ";
            i++;
        }
        return (spaces + txt);
    }

    /**
     * Gets the number of spaces in a single console line
     * 
     * @param x The number of spaces in characters
     * @return String containing the spaces
     */
    public String getSpacing(int x) {
        String spaces = "";
        while (x > 0) {
            spaces += " ";
            x--;
        }
        return spaces;
    }

    /**
     * Creates the invisible clickable Frame
     * 
     * @param x      the x-coordinate of the window
     * @param y      the y-coordinate of the window
     * @param width  the width of the window
     * @param height the height of the window
     */
    public static void createWindow(int x, int y) {
        panel_dimension = new Dimension(x * 8, y * 16);

        f = new XFrame();
        // f.setType(JFrame.Type.UTILITY);
        // f.setSize(g);
        f.setLocationRelativeTo(null);
        f.setTitle("Ascii-Game");

        f.setResizable(false);
        // f.setUndecorated(true);
        f.getRootPane().getContentPane().setBackground(new Color(255, 255, 255, 200));
        // f.getRootPane().setOpaque(false);
        new Thread() {
            int[] lastPos;

            // long lastCheck = System.currentTimeMillis();
            public void run() {
                f.getRootPane().addMouseMotionListener(new MouseInputAdapter() {
                    @Override
                    public void mouseMoved(MouseEvent e) {
                        if(GameManager.instance == null)
                            return;
                        if (!GameManager.instance.isReady())
                            return;

                        int[] pos = XFrame.calcPos(e);
                        // Only trigger when the Position changes
                        if (!pos.equals(lastPos)) {
                            if (Main.debug)
                                GameManager.debug(pos[0], pos[1]);
                            GameManager.getModeInstance().hoverInput(pos[0], pos[1]);
                        }
                        lastPos = pos;
                    }
                });
                f.getRootPane().addMouseListener(f);
            }
        }.start();
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                GameManager.quit();

            }
            

        });
        // f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        f.setVisible(true);
    }

    /**
     * 
     */
    public Dimension getPanelDimension() {
		return panel_dimension;
	}

    /**
     * Force closes the game
     */
    public static void forceExitWindow() {
        instance.dispose();
        System.exit(0);
	}

    /**
     * Creates a Yes/No option to exit the game
     */
    @Deprecated
    public static void exitWindow() {
        int eingabe = JOptionPane.showConfirmDialog(null, "Wollen Sie wirklich beenden?", "Beenden",
                JOptionPane.YES_NO_OPTION);

        if (eingabe == 0) {
            forceExitWindow();
        }
    }

    public static void printReadyFrameDBG() {
        if (instance.xp != null)
            instance.xp.repaint();
    }

    /**
     * Calulates the position from pixel to characters
     * @param e Position of the mouse
     * @return int[] with the position as characters
     */
    public static int[] calcPos(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        double refx = XPanel.instance.getSize().getWidth() / our.game.core.Main.X;
        double refy = XPanel.instance.getSize().getHeight() / our.game.core.Main.Y;
        x = (int) (x / refx);
        y = (int) (y / refy);
        return new int[] { x, y };
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // int x = e.getX();
        // int y = e.getY();
        // double maxx = this.getSize().getWidth();
        //Creates an Option pane to ask the user if he/she wants to quit
        // if (maxx - x <= 50 && y < 30){
        //     exitWindow();

        // }

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