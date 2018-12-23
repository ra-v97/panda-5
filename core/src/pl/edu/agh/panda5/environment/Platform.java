package pl.edu.agh.panda5.environment;

import com.badlogic.gdx.physics.box2d.Body;
import pl.edu.agh.panda5.application.GameObject;

public class Platform extends GameObject {
    private int width;

    public Platform(Body body){
        super(body);
    }
}
