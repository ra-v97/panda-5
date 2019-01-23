package pl.edu.agh.panda5.utils;

import com.badlogic.gdx.math.Vector2;
import pl.edu.agh.panda5.environment.Coin;
import pl.edu.agh.panda5.environment.Obstacle;
import pl.edu.agh.panda5.environment.Platform;
import pl.edu.agh.panda5.opponent.Bullet;
import pl.edu.agh.panda5.opponent.Hunter;
import pl.edu.agh.panda5.player.Player;
import pl.edu.agh.panda5.player.powerups.PowerUp;

public interface AbstractFactory {
    Player createPlayer();
    Platform createGround();
    Platform createPlatform(Vector2 position);
    Hunter createHunter(int level, GameObjectType power);
    Obstacle createObstacle(Vector2 position);
    Coin createCoin(Vector2 position, GameObjectType type);
    PowerUp createPowerUp(Vector2 position);
    Bullet getArrow();
    Bullet getBomb();
}
