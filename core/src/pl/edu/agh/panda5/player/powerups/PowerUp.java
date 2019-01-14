package pl.edu.agh.panda5.player.powerups;

import com.badlogic.gdx.physics.box2d.Body;
import pl.edu.agh.panda5.application.GameObject;
import pl.edu.agh.panda5.utils.Constants;

public class PowerUp extends GameObject {
    private PowerUpEffect effect;

    public PowerUp(Body body){
        super(body);
    }

    public void setEffect(PowerUpEffect powerUpEffect){
        this.effect = powerUpEffect;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        body.setLinearVelocity(Constants.GAME_LINEAR_VELOCITY);
    }
}
