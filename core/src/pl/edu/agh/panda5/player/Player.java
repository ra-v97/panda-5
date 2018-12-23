package pl.edu.agh.panda5.player;

import com.badlogic.gdx.physics.box2d.Body;
import pl.edu.agh.panda5.application.GameObject;
import pl.edu.agh.panda5.player.powerups.PowerUpEffect;
import pl.edu.agh.panda5.utils.Constants;

import java.util.List;


public class Player extends GameObject {
    private int points;
    private boolean jumping;
    private List<PowerUpEffect> activePowerUps;

    public Player(Body body){
        super(body);
    }

    public void jump() {
        if (!jumping) {
            body.applyLinearImpulse(Constants.RUNNER_JUMPING_LINEAR_IMPULSE, body.getWorldCenter(), true);
            jumping = true;
        }
    }

    public void landed() {
        jumping = false;
    }

}
