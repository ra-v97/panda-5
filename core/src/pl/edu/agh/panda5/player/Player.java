package pl.edu.agh.panda5.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import pl.edu.agh.panda5.application.GameObject;
import pl.edu.agh.panda5.player.powerups.BasicPowerUpEffect;
import pl.edu.agh.panda5.player.powerups.PowerUpDecorator;
import pl.edu.agh.panda5.player.powerups.PowerUpEffect;
import pl.edu.agh.panda5.utils.AnimationPart;
import pl.edu.agh.panda5.utils.Constants;
import pl.edu.agh.panda5.utils.GameObjectType;
import pl.edu.agh.panda5.utils.GameTextures;

import java.util.Map;


public class Player extends GameObject {
    private int points = 0;
    private PowerUpEffect powerUp;
    private boolean dodging = false;
    private boolean jumping = false;
    private boolean dropping = false;
    private boolean canBeKilledByShot = true;
    private boolean canJumpTwice = false;
    private boolean secondJumpDone = false;
    private float currentJumpTimeout = 0f;
    private boolean isMovingRight = false;
    private boolean isMovingLeft = false;

    public Player(Body body) {
        super(body);
        sprite = new Sprite(new TextureRegion(GameTextures.PLAYER));
        resetAnimation(AnimationPart.PANDA_WALK_1);
    }

    public void jump() {
        if (((!jumping && currentJumpTimeout < 0)||(canJumpTwice && !secondJumpDone)) && !dodging) {
            body.applyLinearImpulse(
                    0f,
                    Constants.RUNNER_JUMPING_LINEAR_IMPULSE_Y-body.getLinearVelocity().y,
                    body.getWorldCenter().x,
                    body.getWorldCenter().y,
                    true);

            if(jumping && canJumpTwice && !secondJumpDone) secondJumpDone = true;
            jumping = true;
            currentJumpTimeout = Constants.RUNNER_JUMP_TIMEOUT;
            resetAnimation(AnimationPart.PANDA_JUMP_1);
        }
    }

    public boolean isJumping() {
        return jumping;
    }

    public void landed() {
        jumping = false;
        secondJumpDone = false;
        resetAnimation(AnimationPart.PANDA_WALK_1);
    }

    public void drop() { dropping = true; }
    public void stopDrop() { dropping = false; }
    public boolean isDropping() { return dropping; }

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

    public void draw(Batch batch) {
        Vector2 pos = body.getPosition();
        Vector2 size = new Vector2(
                Constants.RUNNER_WIDTH,
                Constants.RUNNER_HEIGHT
        );
        draw(batch, pos, size);
    }
}
