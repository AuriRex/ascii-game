package our.game.util;

import java.io.File;
import java.util.HashMap;

import our.game.gameobjects.GameObject;

public class ResourceManager {

    public static ResourceManager instance;

    private HashMap<String, ATex> textures;

    protected HashMap<Integer, GameObject> objectPool;

    private ResourceManager() {

        textures = new HashMap<String, ATex>();

    }

    public static void init() {
        instance = new ResourceManager();
    }

	public void loadResources(String string) {

        listFilesForFolder(new File(string));

    }
    
    public void listFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                System.out.println(fileEntry.getName());
                //TODO 
            }
        }
    }

}
