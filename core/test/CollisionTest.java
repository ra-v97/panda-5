import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import pl.edu.agh.panda5.collider.CollisionDetector;
import pl.edu.agh.panda5.environment.Coin;
import pl.edu.agh.panda5.environment.Platform;
import pl.edu.agh.panda5.opponent.Bullet;
import pl.edu.agh.panda5.player.Player;
import pl.edu.agh.panda5.player.powerups.BasicPowerUpEffect;
import pl.edu.agh.panda5.player.powerups.PowerUp;
import pl.edu.agh.panda5.player.powerups.PowerUpShield;
import pl.edu.agh.panda5.stages.GameStage;
import pl.edu.agh.panda5.utils.Constants;
import pl.edu.agh.panda5.utils.GameObjectFactory;
import pl.edu.agh.panda5.utils.GameObjectType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(GdxTestRunner.class)
public class CollisionTest {
    private Player player;
    private World world;
    private GameObjectFactory factory;
    private GameStage stage;
    private boolean gameOver = false;

    @Before
    public void setUp() {
        world = new World(Constants.WORLD_GRAVITY, true);
        factory = new GameObjectFactory(world, true);
        player = factory.createPlayer();


        stage = Mockito.mock(GameStage.class);
        Mockito.when(stage.getPlayer()).thenAnswer(a -> player);
        Mockito.doAnswer(a -> gameOver = true).when(stage).gameOver();

        world.setContactListener(new CollisionDetector(stage));

        assertNotNull(world);
        assertNotNull(factory);
        assertNotNull(player);
    }


    @Test
    public void dropTest() {
        Platform ground = factory.createGround();
        world.step(1f/60f,5,5);
        assertTrue(player.getBody().getPosition().y > ground.getBody().getPosition().y);
        player.drop();
        for(int i = 0; i < 100; i++)
            world.step(1f/60f,8,3);
        assertFalse(player.getBody().getPosition().y > ground.getBody().getPosition().y);
    }


    @Test
    public void arrowHitTest() {
        Method method = null;
        try {
            method = factory.getClass().getDeclaredMethod("createArrowBody");
            method.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        Body body = null;
        try {
            body = (Body) method.invoke(factory);
        } catch (IllegalAccessException | InvocationTargetException | NullPointerException e ) {
            e.printStackTrace();
        }

        Bullet arrow = new Bullet(body);
        arrow.getBody().setTransform(player.getBody().getPosition(), 0);
        world.step(1f/60f,5,5);
        assertTrue(gameOver);
        gameOver = false;
    }

    @Test
    public void bombHitTest() {
        Method method = null;
        try {
            method = factory.getClass().getDeclaredMethod("createBombBody");
            method.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        Body body = null;
        try {
            body = (Body) method.invoke(factory);
        } catch (IllegalAccessException | InvocationTargetException | NullPointerException e ) {
            e.printStackTrace();
            return;
        }

        Bullet arrow = new Bullet(body);
        arrow.getBody().setTransform(player.getBody().getPosition(), 0);
        world.step(1f/60f,5,5);
        assertTrue(gameOver);
        gameOver = false;
    }


    @Test
    public void coinTest() {
        int points = player.getPoints();

        Method method = null;
        try {
            method = factory.getClass().getDeclaredMethod("createCoinPrototype", int.class);
            method.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        Coin coin = null;
        try {
            coin = (Coin) method.invoke(factory, 0);
        } catch (IllegalAccessException | InvocationTargetException | NullPointerException e ) {
            e.printStackTrace();
            return;
        }

        coin.getBody().setTransform(player.getBody().getPosition(), 0);
        world.step(1f/60f,5,5);
        assertTrue(player.getPoints() > points);
    }

    @Test
    public void powerUpTest() {
        assertNull(player.getEffect());

        Method method = null;
        try {
            method = factory.getClass().getDeclaredMethod("createPowerUpPrototype");
            method.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        PowerUp powerUp;
        try {
            powerUp = (PowerUp) method.invoke(factory);
        } catch (IllegalAccessException | InvocationTargetException | NullPointerException e ) {
            e.printStackTrace();
            return;
        }
        powerUp.setEffect(new PowerUpShield());
        Set<PowerUp> set = new HashSet<>();
        set.add(powerUp);
        Mockito.when(stage.getPowerUps()).thenAnswer(a -> set);

        Map<GameObjectType, Object> map = new HashMap<>();
        map.put(GameObjectType.PLAYER,player);
        player.setUpBasicEffect(map);

        powerUp.getBody().setTransform(player.getBody().getPosition(), 0);
        world.step(1f/60f,5,5);

        assertNotNull(player.getEffect());

    }


}
