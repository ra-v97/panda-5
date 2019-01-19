package pl.edu.agh.panda5.opponent;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import pl.edu.agh.panda5.application.GameObject;

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
    }

    public void usePower(){
        if(isSpawned){
            power.use(level);
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

    public void verifyPowers(){
        power.tideUp();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        Random random = new Random();
        random.nextFloat();
    }

    public void draw(Batch batch) {

    }

}
