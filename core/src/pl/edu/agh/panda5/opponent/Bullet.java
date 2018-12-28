package pl.edu.agh.panda5.opponent;


import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import pl.edu.agh.panda5.application.GameObject;

import pl.edu.agh.panda5.utils.Constants;

public class Bullet extends GameObject implements Pool.Poolable {

    private boolean alive;

    public Bullet(Body body) {
        super(body);
        this.alive = false;
    }

    void initVertical(int level) {
        body.setTransform(Constants.BULLET_DEFAULT_POS_X,Constants.BULLET_DEFAULT_POS_Y[level],0f);
        alive = true;
    }

    void initHorizontal(float posX) {
        body.setTransform(posX,Constants.BOMB_POS_Y,0f);
        alive = true;
    }

    @Override
    public void reset() {
        body.setTransform(Constants.DUMPSTER_POS,0f);
        alive = false;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        body.setLinearVelocity(Constants.GAME_LINEAR_VELOCITY);
    }

    public static void freeDeadBullets(Array<Bullet> activeBullets,Pool<Bullet> bulletPool){
        Bullet item;
        int len = activeBullets.size;
        for (int i = len; --i >= 0;) {
            item = activeBullets.get(i);
            if (!item.alive ) {
                activeBullets.removeIndex(i);
                bulletPool.free(item);
            }
        }
    }
}
