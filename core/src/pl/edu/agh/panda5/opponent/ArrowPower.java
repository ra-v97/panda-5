package pl.edu.agh.panda5.opponent;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import pl.edu.agh.panda5.utils.GameObjectFactory;

public class ArrowPower implements HunterPower {

    // array containing the active bullets.
    private final Array<Bullet> activeArrows = new Array<>();

    // bullet pool.
    private final Pool<Bullet> bulletPool = new Pool<Bullet>() {
        @Override
        protected Bullet newObject() {
            return new Bullet(GameObjectFactory.createArrowBody());
        }

    };

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
