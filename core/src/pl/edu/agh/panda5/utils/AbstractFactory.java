package pl.edu.agh.panda5.utils;

import com.badlogic.gdx.math.Vector2;
import pl.edu.agh.panda5.environment.Coin;
import pl.edu.agh.panda5.environment.Obstacle;
import pl.edu.agh.panda5.environment.Platform;
import pl.edu.agh.panda5.opponent.Hunter;
import pl.edu.agh.panda5.player.Player;

public interface AbstractFactory {
    Player createPlayer();
    Platform createGround();
    Platform createPlatform(Vector2 position);
    Hunter createHunter(Vector2 position);
    Obstacle createObstacle(Vector2 position);
    Coin createCoin(Vector2 position, int type);
}
