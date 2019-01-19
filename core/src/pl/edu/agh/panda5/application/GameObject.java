package pl.edu.agh.panda5.application;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import pl.edu.agh.panda5.utils.AnimationPart;
import pl.edu.agh.panda5.utils.Constants;


public abstract class GameObject extends Actor {
    private AnimationPart currentFrame;
    private float animationTimeout = 0;

    protected Body body;
    protected Sprite sprite;

    public GameObject(Body body) {
        this.body = body;
    }

    public abstract void draw(Batch batch);

    @Override
    public void act(float dt) {
        if(sprite != null && currentFrame != null) {
            animationTimeout += dt;
            if (animationTimeout > currentFrame.timeout) {
                animationTimeout -= currentFrame.timeout;
                currentFrame = currentFrame.nextPart;
                sprite.setRegion(currentFrame.x, currentFrame.y, currentFrame.w, currentFrame.h);
            }
        }
    }

    public Body getBody(){
        return body;
    }

    protected void draw(Batch batch, Vector2 pos, Vector2 size) {
        pos.x *= Constants.PPM_X;
        pos.y *= Constants.PPM_Y;
        size.x *= Constants.PPM_X;
        size.y *= Constants.PPM_Y;
        sprite.setSize(size.x, size.y);
        sprite.setCenter(pos.x, pos.y);
        sprite.setRotation(0);

        sprite.draw(batch);
    }

    protected void resetAnimation(AnimationPart newFrame) {
        animationTimeout = 0.0f;
        currentFrame = newFrame;
        sprite.setRegion(currentFrame.x, currentFrame.y, currentFrame.w, currentFrame.h);
    }

}
