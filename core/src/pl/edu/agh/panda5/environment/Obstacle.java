package pl.edu.agh.panda5.environment;

import com.badlogic.gdx.physics.box2d.Body;
import pl.edu.agh.panda5.application.GameObject;
import pl.edu.agh.panda5.utils.Constants;

public class Obstacle extends GameObject {
    private int width;

    public Obstacle(Body body){
        super(body);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        body.setLinearVelocity(Constants.ENEMY_LINEAR_VELOCITY);
    }
}
