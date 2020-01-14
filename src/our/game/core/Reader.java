package our.game.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

import our.game.util.ATex;
import our.game.util.Tex;

public class Reader {

    private static HashMap<String, Tex> cache = new HashMap<String, Tex>();

    /**
     * Reads the contents of a file
     * @param path The path of the file to be read
     * @return Texture or Animated Texture of the read file
     */
    public static Tex read(String path) {
        return read(path, false, true);
    }

    /**
     * Reads the contents of a file
     * @param path The path of the file to be read
     * @param forceRead forces the file to be re-read
     * @return Texture or Animated Texture of the read file
     */
    public static Tex read(String path, boolean forceRead) {
        return read(path, forceRead, true);
    }

    /**
     * Reads the contents of a file
     * @param path The path of the file to be read
     * @param forceRead forces the file to be re-read
     * @param shouldCache Should the Tex element for this path remain in memorry ?
     * @return Texture or Animated Texture of the read file
     */
    public static Tex read(String path, boolean forceRead, boolean shouldCache) {

        if(!forceRead && cache.containsKey(path)) {
            Tex cachedTex = cache.get(path);

            if(cachedTex != null)
                return cachedTex;
        }

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

        Tex texToReturn = (Tex) new ATex(texArray);

        if(shouldCache) {
            cache.put(path, texToReturn);
        }

        return texToReturn;
    }


    public static BufferedImage readFont(String path) throws IOException {

        BufferedImage in = ImageIO.read(new File(path));
        return in;
    }

}