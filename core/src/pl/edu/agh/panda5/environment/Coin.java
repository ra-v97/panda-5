package pl.edu.agh.panda5.environment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import pl.edu.agh.panda5.application.GameObject;
import pl.edu.agh.panda5.utils.AnimationPart;
import pl.edu.agh.panda5.utils.Constants;
import pl.edu.agh.panda5.utils.GameObjectData;
import pl.edu.agh.panda5.utils.GameTextures;

public class Coin extends GameObject {
    private int value;

    public Coin(Body body, int value){
        super(body);
        this.value = value;
        sprite = new Sprite(new TextureRegion(GameTextures.COIN));
        if(Constants.COIN_VALUE[0] == value) resetAnimation(AnimationPart.COIN_BRONZE_1);
        else if(Constants.COIN_VALUE[1] == value) resetAnimation(AnimationPart.COIN_SILVER_1);
        else resetAnimation(AnimationPart.COIN_GOLD_1);
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

    public void draw(Batch batch) {
        Vector2 pos = body.getPosition();
        Vector2 size = new Vector2(
                Constants.COIN_WIDTH * 2,
                Constants.COIN_HEIGHT * 2
        );
        draw(batch, pos, size);
    }
}
