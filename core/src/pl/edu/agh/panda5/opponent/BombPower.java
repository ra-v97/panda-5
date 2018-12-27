package pl.edu.agh.panda5.opponent;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import pl.edu.agh.panda5.utils.Constants;

import java.util.Random;

public class BombPower implements HunterPower {

    private final Body bombBody;

    // array containing the active bullets.
    private final Array<Bullet> activeBombs = new Array<>();

    // bullet pool.
    private final Pool<Bullet> bombsPool = new Pool<Bullet>() {
        @Override
        protected Bullet newObject() {
            return new Bullet(bombBody);
        }
    };

    public BombPower(Body body){
        bombBody = body;
    }

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
