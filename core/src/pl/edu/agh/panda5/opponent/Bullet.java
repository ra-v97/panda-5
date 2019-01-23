package pl.edu.agh.panda5.opponent;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.sun.deploy.config.VerboseDefaultConfig;
import pl.edu.agh.panda5.application.GameObject;

import pl.edu.agh.panda5.utils.AnimationPart;
import pl.edu.agh.panda5.utils.Constants;
import pl.edu.agh.panda5.utils.GameObjectData;
import pl.edu.agh.panda5.utils.GameTextures;

public class Bullet extends GameObject implements Pool.Poolable {

    public Bullet(Body body) {
        super(body);
        sprite = new Sprite(new TextureRegion(GameTextures.BOMB));
        resetAnimation(AnimationPart.BOMB);
    }

    void initVertical(int level) {
        sprite.setRotation(180.0f);
        ((GameObjectData)this.body.getFixtureList().get(0).getUserData()).setFlaggedForDelete(false);
        body.setTransform(Constants.BULLET_DEFAULT_POS_X,Constants.BULLET_DEFAULT_POS_Y[level],0f);

    }

    void initHorizontal(float posX) {
        sprite.setRotation(270.0f);
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

    public void draw(Batch batch) {
        Vector2 pos = body.getPosition();
        Vector2 size = new Vector2(
                Constants.BOMB_SIZE * 10,
                Constants.BOMB_SIZE * 5
        );
        draw(batch, pos, size);
    }

}
