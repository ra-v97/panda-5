package pl.edu.agh.panda5.environment;

import com.badlogic.gdx.physics.box2d.Body;
import pl.edu.agh.panda5.application.GameObject;

public class Coin extends GameObject {
    private int value;

    public Coin(Body body){
        super(body);
    }
}
