package our.game.util;

import java.awt.Rectangle;
import com.sun.jna.platform.*;

public class MouseIn {
    
    public MouseIn() {

        //Find IntelliJ IDEA Window
        Rectangle rect = null;
        for (DesktopWindow desktopWindow : WindowUtils.getAllWindows(true)) {
            if (desktopWindow.getTitle().contains("PowerShell")) {
                rect = desktopWindow.getLocAndSize();
            }
        }


        System.out.println(rect);

    }
    

}