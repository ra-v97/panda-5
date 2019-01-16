package pl.edu.agh.panda5.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import pl.edu.agh.panda5.application.GameObject;
import pl.edu.agh.panda5.player.powerups.BasicPowerUpEffect;
import pl.edu.agh.panda5.player.powerups.PowerUpDecorator;
import pl.edu.agh.panda5.player.powerups.PowerUpEffect;
import pl.edu.agh.panda5.utils.Constants;
import pl.edu.agh.panda5.utils.GameObjectType;

import java.util.Map;


public class Player extends GameObject {
    private int points = 0;
    private PowerUpEffect powerUp;
    private boolean dodging;
    private boolean jumping;
    private boolean canBeKilledByShot;
    private boolean canJumpTwice;
    private float currentJumpTimeout;

    private boolean isMovingRight;
    private boolean isMovingLeft;

    public Player(Body body) {
        super(body);
        currentJumpTimeout = 0f;
        isMovingLeft = false;
        isMovingRight = false;
    }

    public void jump() {
        if (!(jumping || dodging) && currentJumpTimeout < 0) {
            body.applyLinearImpulse(Constants.RUNNER_JUMPING_LINEAR_IMPULSE, body.getWorldCenter(), true);
            jumping = true;
            currentJumpTimeout = Constants.RUNNER_JUMP_TIMEOUT;
        }
    }


    public boolean isJumping() {
        return jumping;
    }

    public void landed() {
        jumping = false;
    }

    public void fall() {
        jumping = true;
    }

    public void moveRight() {
        isMovingRight = true;
    }

    public void moveLeft() {
        isMovingLeft = true;
    }

    public void stopMovingRight() {
        isMovingRight = false;
    }

    public void stopMovingLeft() {
        isMovingLeft = false;
    }

    public void setImmortality() { canBeKilledByShot = false; }

    public void setPossibleToKill() {
        canBeKilledByShot = true;
    }

    public void doubleJumpOn() { canJumpTwice = true; }

    public void doubleJumpOff() { canJumpTwice = false; }

    public boolean canBeKilled() { return this.canBeKilledByShot; }

    public void update(float dt) {
        currentJumpTimeout -= dt;
        powerUp.applyEffect(dt);
        setVelocity();
    }

    public void dodge() {
        if (!jumping) {
            body.setTransform(body.getPosition().x, (float) (body.getPosition().y - (0.5 * (Constants.RUNNER_HEIGHT - Constants.RUNNER_WIDTH))), (float) (90f * (Math.PI / 180f)));
            dodging = true;
        }
    }

    public void stopDodge() {
        dodging = false;
        body.setTransform(body.getPosition().x, (float) (body.getPosition().y + (0.5 * (Constants.RUNNER_HEIGHT - Constants.RUNNER_WIDTH))), 0f);
    }

    public boolean isDodging() {
        return dodging;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public int getPoints() {
        return points;
    }

    public void addEffect(PowerUpEffect effect) {
        powerUp.decorate((PowerUpDecorator) effect);
    }

    public void setUpBasicEffect(Map<GameObjectType, Object> mutableObjects) {
        this.powerUp = new BasicPowerUpEffect(mutableObjects);
    }

    private void setVelocity() {
        Vector2 velocity = body.getLinearVelocity();
        Vector2 position = body.getPosition();
        velocity.x = 0f;
        if (isMovingRight && position.x < Constants.RIGHT_BORDER) {
            velocity.x += Constants.RUNNER_RUN_RIGHT_SPEED;
        }
        if (isMovingLeft) {
            velocity.x += Constants.RUNNER_RUN_LEFT_SPEED;
        }
        body.setLinearVelocity(velocity);
    }
}
