package pl.edu.agh.panda5.opponent;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import pl.edu.agh.panda5.utils.AbstractFactory;
import pl.edu.agh.panda5.utils.Constants;
import pl.edu.agh.panda5.utils.GameObjectFactory;

import java.util.concurrent.CompletableFuture;

public class ArrowPower implements HunterPower {

    // array containing the active bullets.
    private final Array<Bullet> activeArrows = new Array<>();
    private AbstractFactory factory;
    private float shootTimeout = 0.0f;
    private int shotsLeft = 0;
    private int level;

    @Override
    public void setFactory(AbstractFactory factory) {
        this.factory = factory;
    }

    @Override
    public void use(int level){
        shootTimeout = Constants.HUNTER_SHOOTING_TIME_WINDOW;
        shotsLeft = 3;
        this.level = level;
    }

    @Override
    public void draw(Batch batch) {
        activeArrows.forEach(arrow -> arrow.draw(batch));
    }

    public void update(float dt) {
        if(shotsLeft > 0) {
            shootTimeout -= dt;
            if(shootTimeout < 0) {
                Bullet item = factory.getArrow();
                item.initVertical(level);
                activeArrows.add(item);

                shotsLeft--;
                shootTimeout += Constants.HUNTER_SHOOTING_TIME_WINDOW;
            }
        } else {
            shootTimeout = 0.0f;
        }
    }
}
