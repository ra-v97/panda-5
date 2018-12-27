package pl.edu.agh.panda5.opponent;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class ArrowPower implements HunterPower {

    private final Body arrowBody;

    // array containing the active bullets.
    private final Array<Bullet> activeArrows = new Array<>();

    // bullet pool.
    private final Pool<Bullet> bulletPool = new Pool<Bullet>() {
        @Override
        protected Bullet newObject() {
            return new Bullet(arrowBody);
        }
    };

    public ArrowPower(Body body){
        arrowBody = body;
    }

    @Override
    public void use(int level){
        spawnNewBullet(level);
    }

    private void spawnNewBullet(int level){
        Bullet item = bulletPool.obtain();
        item.initVertical(level);
        activeArrows.add(item);
    }

    @Override
    public void tideUp(){
        Bullet.freeDeadBullets(this.activeArrows,this.bulletPool);
    }
}
