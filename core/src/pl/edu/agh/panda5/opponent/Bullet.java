package pl.edu.agh.panda5.opponent;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import pl.edu.agh.panda5.application.GameObject;

import pl.edu.agh.panda5.utils.Constants;
import pl.edu.agh.panda5.utils.GameObjectData;

public class Bullet extends GameObject implements Pool.Poolable {

    public Bullet(Body body) {
        super(body);
    }

    void initVertical(int level) {
        ((GameObjectData)this.body.getFixtureList().get(0).getUserData()).setFlaggedForDelete(false);
        body.setTransform(Constants.BULLET_DEFAULT_POS_X,Constants.BULLET_DEFAULT_POS_Y[level],0f);

    }

    void initHorizontal(float posX) {
        ((GameObjectData)this.body.getFixtureList().get(0).getUserData()).setFlaggedForDelete(false);
        body.setTransform(posX,Constants.BOMB_POS_Y,0f);
    }

    @Override
    public void reset() {
        ((GameObjectData) body.getFixtureList().get(0).getUserData()).setFlaggedForDelete(false);
        body.setTransform(Constants.DUMPSTER_POS,0f);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        body.setLinearVelocity(Constants.BULLET_SPEED);
    }

    public static void freeDeadBullets(Array<Bullet> activeBullets,Pool<Bullet> bulletPool){
        Bullet item;
        int len = activeBullets.size;
        for (int i = len; --i >= 0;) {
            item = activeBullets.get(i);
            if (((GameObjectData)item.getBody().getFixtureList().get(0).getUserData()).isFlaggedForDelete()) {
                activeBullets.removeIndex(i);
                bulletPool.free(item);
            }
        }
    }

}
