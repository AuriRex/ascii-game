package our.game.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import our.game.util.ATex;
import our.game.util.Tex;

public class Reader {

    public static Tex read(String path) {
        BufferedReader reader;

        String raw_tex = "";
        int len = 0;
        // String[] tex = null;
        Tex[] texArray = null;

        try {
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            raw_tex = line;
            while (line != null) {
                if (len < line.length())
                    len = line.length();
                line = reader.readLine();
                if(line != null)
                    raw_tex += "\n" + line;
                // System.out.println(raw_tex);
                // try {
                //     Thread.sleep(1000);
                // } catch (InterruptedException e) {
                //     e.printStackTrace();
                // }
            }
            reader.close();
            // tex = raw_tex.split("\n");
            
            // TODO: Check that each Frame of the Animation has the same height -> append strings filled with spaces if neccesarry

            String[] raw_tex_split = raw_tex.split("\n\n");

            // "   sss\n    diij\n dsadsadasd"
            int ylen = 0;
            for(String s : raw_tex_split){
                if (ylen < s.split("\n").length) ylen = s.split("\n").length;
            }
            texArray = new Tex[raw_tex_split.length];

            // for(String s : raw_tex_split) {
            for(int i = 0; i < raw_tex_split.length; i++) {

                String[] tmp = raw_tex_split[i].split("\n");
                // new String array with len ylen
                // old values into new String[]
                String[] ytmp = new String[ylen];

                for (int k = 0; k < ytmp.length; k++){
                    try{
                        ytmp[k] = tmp[k];
                    } catch(IndexOutOfBoundsException ex) {
                        // Fill with one space
                        ytmp[k] = " ";
                    }
                }

                for (int k = 0; k < ytmp.length; k++){
                    if (ytmp[k].length() < len){
                        int tmp_ = len - ytmp[k].length();
                        for(int j = 0; j < tmp_; j++){
                            ytmp[k] += " ";
                        }
                    }
                    // System.out.println(tmp[i].length());
                }

                texArray[i] = new Tex(ytmp);

            }

            // for (int i = 0; i < tex.length; i++){
            //     if (tex[i].length() < len){
            //         int tmp = len - tex[i].length();
            //         for(int j= 0; j < tmp; j++){
            //             tex[i] += " ";
            //         }
            //     }
            // }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return (Tex) new ATex(texArray); // RETURN Tex or ATex Object
    }
}