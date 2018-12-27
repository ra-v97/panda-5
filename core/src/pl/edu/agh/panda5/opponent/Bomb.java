package pl.edu.agh.panda5.opponent;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;
import pl.edu.agh.panda5.application.GameObject;

import pl.edu.agh.panda5.utils.Constants;

public class Bomb extends GameObject implements Pool.Poolable {

    public boolean alive;

    public Bomb(Body body) {
        super(body);
        this.alive = false;
    }

    public void init(float posX, float posY) {
        body.setTransform(posX,posY,0f);

        alive = true;
    }

    @Override
    public void reset() {
        body.setTransform(0f,0f,0f);
        alive = false;
    }

    public void update (float delta) {

        // update bullet position
        getBody().setTransform(getBody().getPosition().x+Constants.ARROW_SPEED *delta*60,getBody().getPosition().y, 0);

        if (isOutOfScreen()) alive = false;
    }
    @Override
    public void act(float delta) {
        super.act(delta);
        body.setLinearVelocity(Constants.GAME_LINEAR_VELOCITY);
    }
    private boolean isOutOfScreen() {
        return getBody().getPosition().x < 0f;
    }
}
