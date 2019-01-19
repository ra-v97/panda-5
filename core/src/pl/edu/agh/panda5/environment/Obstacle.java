package pl.edu.agh.panda5.environment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import pl.edu.agh.panda5.application.GameObject;
import pl.edu.agh.panda5.utils.Constants;

public class Obstacle extends GameObject {
    private int width;

    public Obstacle(Body body){
        super(body);
        sprite = new Sprite(new Texture(Gdx.files.internal("core/assets/terrain/boulder.png")));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        body.setLinearVelocity(Constants.GAME_LINEAR_VELOCITY);
    }

    public void draw(Batch batch) {
        Vector2 pos = body.getPosition();
        Vector2 size = new Vector2(Constants.OBSTACLE_WIDTH * 2, Constants.OBSTACLE_HEIGHT * 2);
        draw(batch, pos, size);
    }
}
