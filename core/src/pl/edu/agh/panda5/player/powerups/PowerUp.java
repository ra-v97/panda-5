package pl.edu.agh.panda5.player.powerups;

import com.badlogic.gdx.physics.box2d.Body;
import pl.edu.agh.panda5.application.GameObject;

public class PowerUp extends GameObject {
    private PowerUpEffect effect;

    public PowerUp(Body body){
        super(body);
    }
}
