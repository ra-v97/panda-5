package pl.edu.agh.panda5.environment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import pl.edu.agh.panda5.application.GameObject;
import pl.edu.agh.panda5.utils.Constants;

public class Platform extends GameObject {
    private int width;
    private boolean isGround;

    public Platform(Body body, boolean isGround){
        super(body);
        this.isGround = isGround;
        sprite = new Sprite(new Texture(Gdx.files.internal("core/assets/terrain/terrain.png")));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        body.setLinearVelocity(Constants.GAME_LINEAR_VELOCITY);
    }

    public void draw(Batch batch) {
        Vector2 pos = body.getPosition();
        Vector2 size;
        if(!isGround) size = new Vector2(Constants.PLATFORM_WIDTH * 2, Constants.PLATFORM_HEIGHT * 2);
        else size = new Vector2(Constants.GROUND_WIDTH * 2, Constants.GROUND_HEIGHT * 2);
        draw(batch, pos, size);
    }
}
