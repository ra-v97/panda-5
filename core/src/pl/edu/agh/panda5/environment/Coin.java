package pl.edu.agh.panda5.environment;

import com.badlogic.gdx.physics.box2d.Body;
import pl.edu.agh.panda5.application.GameObject;
import pl.edu.agh.panda5.utils.Constants;
import pl.edu.agh.panda5.utils.GameObjectData;

public class Coin extends GameObject {
    private int value;

    public Coin(Body body, int value){
        super(body);
        this.value = value;
    }

    public Coin(Body body) {
        super(body);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        body.setLinearVelocity(Constants.GAME_LINEAR_VELOCITY);
    }

    public boolean isAvailable(){
        return !((GameObjectData)getBody().getFixtureList().get(0).getUserData()).isFlaggedForDelete();
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
