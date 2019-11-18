package our.game.util;

import java.awt.Rectangle;
import com.sun.jna.platform.*;

public class MouseIn {

    /**
     * searches for PowerShell window and creates a rectange with the exact coordinates
     * @return rectangle with coordinates of the PowerShell window
     */
    public static Rectangle getConsoleWindow() {
        //Find Powershell Window
        Rectangle rect = null;
        for (DesktopWindow desktopWindow : WindowUtils.getAllWindows(true)) {
            if (desktopWindow.getTitle().contains("PowerShell")) {
                //TODO fix multiple open PowerShell windows bug
                rect = desktopWindow.getLocAndSize();
            }
        }

        return rect;
    }
    

}