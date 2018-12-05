package application;

import com.jme3.app.SimpleApplication;
import com.jme3.math.ColorRGBA;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

/**
 * Main class for PandaGame app
 * Used for starting the program
 *
 */
public class Main extends SimpleApplication{

    /**
     * Starts the game.
     *
     * @param args app arguments
     *
     */
    public static void main(String[] args) {
        System.out.println(new Main().getGreeting());

        Main main = new Main();
        main.start();
    }

    /**
     * @return Greeting message
     */
    public String getGreeting() {

        return "Hello world.";
    }

    @Override
    public void simpleInitApp() {
        Box box = new Box(1, 1, 1);
        Geometry geom = new Geometry("Simple Box", box);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Red);
        geom.setMaterial(mat);
        rootNode.attachChild(geom);
    }
}
