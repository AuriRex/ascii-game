package our.game.util;

import java.awt.Graphics;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.Color;

import our.game.core.Calibration;
import our.game.core.Screen;

public class XPanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public static XPanel instance;

    public XPanel() {

        if(instance != null) return;
        instance = this;

        setBackground(new Color(0,0,100));
        setPreferredSize(Calibration.g); 
        setMinimumSize(Calibration.g); // TODO: Change this

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage c = Screen.getBI();
        g.drawImage(c, 0, 0, null);
        // Draw Text
        // g.setColor(Color.WHITE);
        // g.drawString("This is my custom Panel!",10,20);

    }  

}