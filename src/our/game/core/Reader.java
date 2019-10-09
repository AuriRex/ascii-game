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
            
            raw_tex = raw_tex.substring(1);

            String[] raw_tex_split = raw_tex.split("\n\n");

            texArray = new Tex[raw_tex_split.length];

            // for(String s : raw_tex_split) {
            for(int i = 0; i < raw_tex_split.length; i++) {

                String[] tmp = raw_tex_split[i].split("\n");

                for (int k = 0; k < tmp.length; k++){
                    if (tmp[k].length() < len){
                        int tmp_ = len - tmp[k].length();
                        for(int j = 0; j < tmp_; j++){
                            tmp[k] += " ";
                        }
                    }
                    System.out.println(tmp[i].length());
                }

                texArray[i] = new Tex(tmp);

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