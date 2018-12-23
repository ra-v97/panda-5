package pl.edu.agh.panda5.utils;

import com.badlogic.gdx.physics.box2d.World;
import pl.edu.agh.panda5.environment.Platform;
import pl.edu.agh.panda5.player.Player;

public interface AbstractFactory {
    Player createPlayer(World world);
    Platform createPlatform(World world);
}
