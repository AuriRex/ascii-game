package our.game.util;

import java.awt.Graphics;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.Color;

import our.game.core.Screen;

public class XPanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public static XPanel instance;

    private final XFrame frame;

    public XPanel(XFrame frame) {

        this.frame = frame;

        if(instance != null) return;
        instance = this;

        setBackground(new Color(0,0,100));
        setPreferredSize(frame.getPanelDimension()); 
        setMinimumSize(frame.getPanelDimension());

    }

    /**
     * Draw the BufferedImage to the Panel
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage c = Screen.getBI();
        g.drawImage(c, 0, 0, null);
    }  

}