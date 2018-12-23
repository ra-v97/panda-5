package pl.edu.agh.panda5.opponent;

import com.badlogic.gdx.physics.box2d.Body;
import pl.edu.agh.panda5.application.GameObject;


public class Hunter extends GameObject {
    private HunterPower power;

    public Hunter(Body body){
        super(body);
    }

    public void usePower(){}
}
