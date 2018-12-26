package pl.edu.agh.panda5.opponent;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

public class ArrowPower implements HunterPower {

    // array containing the active bullets.
    private final Array<Bullet> activeArrows = new Array<>();

    // bullet pool.
    private final Pool<Bullet> bulletPool = Pools.get(Bullet.class);

    public void update(float delta) {

        // if you want to spawn a new bullet:
        Bullet item = bulletPool.obtain();
        item.init(2, 2);
        activeArrows.add(item);

    }

    @Override
    public void use(){}
}
