package our.game.core;

import our.game.util.Input;

class Calibration {

    int resolution_x;
    int resolution_y;
    public Calibration(int x, int y) {
        resolution_x = x;
        resolution_y = y;
    }

    public void startCalibration() {


        // ###        ###
        // #            #
        // #            #

        
        // #            #
        // #            #
        // ###        ###

        Screen.clearScreen();

        System.out.println("###" + getSpacing(resolution_x-6) + "###");
        
        for (int i = 0; i < 2; i++) 
            System.out.println("#" + getSpacing(resolution_x-2) + "#");
        
        for (int i = 0; i < resolution_y-6; i++) {
            if (i == resolution_y / 2 - 4) {
                System.out.println(centerText("Calibration mode...", resolution_x));
            } else if(i == resolution_y / 2 - 3) {
                System.out.println(centerText("Please resize your console window!", resolution_x));
            }else {
                System.out.println();
            }
           
        }
        
        
        for (int i = 0; i < 2; i++) 
            System.out.println("#" + getSpacing(resolution_x-2) + "#");
        
        System.out.print("###" + getSpacing(resolution_x-6) + "###");


        Input.confirm();

    }

    public String centerText(String txt, int x) {
        String spaces = "";        
        int i = 0;
        while (((x/2) - txt.length()/2) > i) {
            spaces += " ";
            i++;
        }
        return (spaces + txt);
    }

    public String getSpacing(int x) {
        String spaces = "";
        while (x > 0) {
            spaces += " ";
            x--;
        }
        return spaces;
    }

}