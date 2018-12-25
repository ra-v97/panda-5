package pl.edu.agh.panda5.opponent;

import com.badlogic.gdx.physics.box2d.Body;
import pl.edu.agh.panda5.application.GameObject;
import pl.edu.agh.panda5.utils.Constants;


public class Hunter extends GameObject {
    private HunterPower power;

    public Hunter(Body body){
        super(body);
    }

    public void usePower(){}

    @Override
    public void act(float delta) {
        super.act(delta);
        body.setLinearVelocity(Constants.ENEMY_LINEAR_VELOCITY);
    }
}
