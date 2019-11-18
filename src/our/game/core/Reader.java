package our.game.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import our.game.util.ATex;
import our.game.util.Tex;

public class Reader {
    /**
     * Reads the contents of a file
     * @param path The path of the file to be read
     * @return Texture or Animated Texture of the read file
     */
    public static Tex read(String path) {
        BufferedReader reader;

        String raw_tex = "";
        int len = 0;
        Tex[] texArray = null;

        try {
            //reads line after line from the param path
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            raw_tex = line;
            while (line != null) {
                if (len < line.length())
                    len = line.length();
                line = reader.readLine();
                if(line != null)
                    raw_tex += "\n" + line;
            }
            reader.close();
            
            // TODO: Maybe check that each Frame of the Animation has the same height -> append strings filled with spaces if neccesarry

            String[] raw_tex_split = raw_tex.split("\n\n");

            int ylen = 0;
            for(String s : raw_tex_split){
                if (ylen < s.split("\n").length) ylen = s.split("\n").length;
            }
            texArray = new Tex[raw_tex_split.length];

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
                }

                texArray[i] = new Tex(ytmp);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return (Tex) new ATex(texArray);
    }
}