package pl.edu.agh.panda5.opponent;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import pl.edu.agh.panda5.application.GameObject;
import pl.edu.agh.panda5.utils.AnimationPart;
import pl.edu.agh.panda5.utils.Constants;
import pl.edu.agh.panda5.utils.GameTextures;

import java.util.Random;


public class Hunter extends GameObject {
    private HunterPower power;
    private int level;
    private boolean isSpawned;

    public Hunter(Body body,int level,HunterPower power){
        super(body);
        this.level =level;
        this.power = power;
        this.isSpawned = false;

        if(power instanceof BombPower) {
            sprite = new Sprite(new TextureRegion(GameTextures.BOMB_ENEMY));
            resetAnimation(AnimationPart.BOMB_HUNTER_IDLE);
        } else if(power instanceof ArrowPower) {
            sprite = new Sprite(new TextureRegion(GameTextures.ARROW_ENEMY));
            resetAnimation(AnimationPart.ARROW_HUNTER_IDLE);
        }
    }

    public void usePower(){
        if(isSpawned){
            power.use(level);
            if(power instanceof BombPower) {
                resetAnimation(AnimationPart.BOMB_HUNTER_ATTACK_1);
            } else if(power instanceof ArrowPower) {
                resetAnimation(AnimationPart.ARROW_HUNTER_ATTACK_1);
            }
        }
    }

    public void setSpawned(){
        this.isSpawned  = true;
    }
    public void deleteSpawn(){
        this.isSpawned  = false;
    }

    public int getLevel(){
        return level;
    }

    public void setLevel(int level){
        if(level < 1 || level >3){
            throw new RuntimeException("Invalid level of hunter");
        }
        this.level = level;

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        Random random = new Random();
        random.nextFloat();
    }

    public void draw(Batch batch) {
        Vector2 pos = body.getPosition();
        Vector2 size = new Vector2(
                Constants.HUNTER_WIDTH,
                Constants.HUNTER_HEIGHT
        );
        sprite.setFlip(true, false);
        draw(batch, pos, size);
        power.draw(batch);
    }

    public void update(float dt){
        power.update(dt);
    }
}
