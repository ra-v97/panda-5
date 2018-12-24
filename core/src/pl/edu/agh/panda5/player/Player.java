package pl.edu.agh.panda5.player;

import com.badlogic.gdx.physics.box2d.Body;
import pl.edu.agh.panda5.application.GameObject;
import pl.edu.agh.panda5.player.powerups.PowerUpEffect;
import pl.edu.agh.panda5.utils.Constants;

import java.util.List;


public class Player extends GameObject {
    private int points;
    private boolean jumping;
    private boolean dodging;

    private List<PowerUpEffect> activePowerUps;

    public Player(Body body) {
        super(body);
    }

    public void jump() {
        if (!(jumping||dodging)) {
            body.applyLinearImpulse(Constants.RUNNER_JUMPING_LINEAR_IMPULSE, body.getWorldCenter(), true);
            jumping = true;
        }
    }

    public void landed() {
        jumping = false;
    }

    public void dodge() {
        if (!jumping) {
            body.setTransform(Constants.DODGE_POSITION, (float) (-90f * (Math.PI / 180f)));
            dodging = true;
        }
    }

    public void stopDodge() {
        dodging = false;
        body.setTransform(Constants.RUNNING_POSITION, 0f);
    }

    public boolean isDodging() {
        return dodging;
    }
}
