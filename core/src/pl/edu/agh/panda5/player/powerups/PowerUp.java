package pl.edu.agh.panda5.player.powerups;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import pl.edu.agh.panda5.application.GameObject;
import pl.edu.agh.panda5.utils.AnimationPart;
import pl.edu.agh.panda5.utils.Constants;
import pl.edu.agh.panda5.utils.GameTextures;

public class PowerUp extends GameObject {
    private PowerUpEffect effect;

    public PowerUp(Body body) {
        super(body);
        sprite = new Sprite(new TextureRegion(GameTextures.POWER_UP));
    }

    public void setEffect(PowerUpEffect powerUpEffect) {
        this.effect = powerUpEffect;
        if(powerUpEffect instanceof PowerUpBonusJump)
            resetAnimation(AnimationPart.POWER_UP_BONUS_JUMP);
        else if(powerUpEffect instanceof PowerUpCoinMagnet)
            resetAnimation(AnimationPart.POWER_UP_MAGNET);
        else
            resetAnimation(AnimationPart.POWER_UP_SHIELD);
    }

    public PowerUpEffect getEffect() {
        return this.effect;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        body.setLinearVelocity(Constants.GAME_LINEAR_VELOCITY);
    }

    public void draw(Batch batch) {
        Vector2 pos = body.getPosition();
        Vector2 size = new Vector2(
                Constants.POWER_UP_WIDTH * 2,
                Constants.POWER_UP_HEIGHT * 2);
        draw(batch, pos, size);
    }
}
