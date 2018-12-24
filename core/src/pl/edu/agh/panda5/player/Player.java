package pl.edu.agh.panda5.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import pl.edu.agh.panda5.application.GameObject;
import pl.edu.agh.panda5.player.powerups.PowerUpEffect;
import pl.edu.agh.panda5.utils.Constants;

import java.util.List;


public class Player extends GameObject {
    private int points;
    private List<PowerUpEffect> activePowerUps;

    private boolean jumping;
    private Body feet;
    private float currentJumpTimeout;

    public Player(Body body, Body feet){
        super(body);
        this.feet = feet;
        currentJumpTimeout = 0f;
    }

    public void jump() {
        if (!jumping && currentJumpTimeout < 0) {
            body.applyLinearImpulse(Constants.RUNNER_JUMPING_LINEAR_IMPULSE, body.getWorldCenter(), true);
            jumping = true;
            currentJumpTimeout = Constants.RUNNER_JUMP_TIMEOUT;
        }
    }

    public void moveRight() {
        Vector2 velocity = body.getLinearVelocity();
        velocity.x = Constants.RUNNER_RUN_RIGHT_SPEED;
        body.setLinearVelocity(velocity);
    }

    public void moveLeft() {
        Vector2 velocity = body.getLinearVelocity();
        velocity.x = Constants.RUNNER_RUN_LEFT_SPEED;
        body.setLinearVelocity(velocity);
    }

    public void stop() {
        Vector2 velocity = body.getLinearVelocity();
        velocity.x = 0f;
        body.setLinearVelocity(velocity);
    }

    public void update(float dt) {
        currentJumpTimeout -= dt;
    }

    public void landed() {
        jumping = false;
    }

}
