package pl.edu.agh.panda5.opponent;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface HunterPower {

     void draw(Batch batch);
     void use(int level);
     void tideUp();
}
