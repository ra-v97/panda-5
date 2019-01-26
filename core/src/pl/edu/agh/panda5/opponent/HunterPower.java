package pl.edu.agh.panda5.opponent;

import com.badlogic.gdx.graphics.g2d.Batch;
import pl.edu.agh.panda5.utils.AbstractFactory;

public interface HunterPower {

     void draw(Batch batch);
     void use(int level);
     void setFactory(AbstractFactory factory);
     void update(float dt);
}
