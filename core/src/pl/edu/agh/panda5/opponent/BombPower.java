package pl.edu.agh.panda5.opponent;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import pl.edu.agh.panda5.utils.AbstractFactory;
import pl.edu.agh.panda5.utils.GameObjectFactory;

import java.util.Random;

public class BombPower implements HunterPower {

    // array containing the active bullets.
    private final Array<Bullet> activeBombs = new Array<>();
    private AbstractFactory factory;

    @Override
    public void setFactory(AbstractFactory factory) {
        this.factory = factory;
    }

    @Override
    public void use(int level){
        for(int i = 0 ; i < 4 ; i++){
            Bullet item = factory.getBomb();
            float pos_X = new Random().nextFloat()*20+level;
            item.initHorizontal(pos_X);
            activeBombs.add(item);
        }
    }

    @Override
    public void draw(Batch batch) {
        activeBombs.forEach(bomb -> bomb.draw(batch));
    }

    @Override
    public void update(float dt) {

    }
}
