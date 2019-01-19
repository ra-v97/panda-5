package pl.edu.agh.panda5.player.powerups;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import pl.edu.agh.panda5.application.GameObject;
import pl.edu.agh.panda5.utils.Constants;

public class PowerUp extends GameObject {
    private PowerUpEffect effect;

    public PowerUp(Body body) {
        super(body);
    }

    public void setEffect(PowerUpEffect powerUpEffect) {
        this.effect = powerUpEffect;
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

    }
}
