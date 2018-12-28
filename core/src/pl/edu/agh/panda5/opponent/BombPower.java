package pl.edu.agh.panda5.opponent;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import java.util.Random;

public class BombPower implements HunterPower {

    // array containing the active bullets.
    private final Array<Bullet> activeBombs = new Array<>();

    // bullet pool.
    private final Pool<Bullet> bombsPool = new Pool<Bullet>() {
        @Override
        protected Bullet newObject() {
            return new Bullet();
        }
    };

    @Override
    public void use(int level){
        Bullet item = bombsPool.obtain();
        float pos_X = new Random().nextFloat()*20+level;
        item.initHorizontal(pos_X);
        activeBombs.add(item);
    }

    @Override
    public void tideUp(){
        Bullet.freeDeadBullets(this.activeBombs,this.bombsPool);
    }
}
