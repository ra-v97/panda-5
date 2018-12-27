package pl.edu.agh.panda5.opponent;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;
import pl.edu.agh.panda5.application.GameObject;
import pl.edu.agh.panda5.utils.Constants;

public class Bullet extends GameObject implements Pool.Poolable {

    public Vector2 position;
    public boolean alive;


    public Bullet(Body body) {
        super(body);
        this.alive = false;
    }

    public void init(float posX, float posY) {
        position.set(posX,  posY);
        alive = true;
    }

    @Override
    public void reset() {
        position.set(0,0);
        alive = false;
    }

    public void update (float delta) {

        // update bullet position
        position.add(Constants.ARROW_SPEED *delta*60, 0);

        this.getBody().setTransform(position,0f);

        if (isOutOfScreen()) alive = false;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    private boolean isOutOfScreen() {
        return this.getBody().getPosition().x < 0f;
    }
}
