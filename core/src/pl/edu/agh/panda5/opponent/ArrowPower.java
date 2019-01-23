package pl.edu.agh.panda5.opponent;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import pl.edu.agh.panda5.utils.GameObjectFactory;

import java.util.concurrent.CompletableFuture;

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
        CompletableFuture.runAsync(() -> {
            for (int i = 0; i<3;i++){
                spawnNewBullet(level);
                try{
                    Thread.sleep(200);
                }catch (Exception e){e.printStackTrace();}
            }
        });
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

    @Override
    public void draw(Batch batch) {
        activeArrows.forEach(arrow -> arrow.draw(batch));
    }
}
