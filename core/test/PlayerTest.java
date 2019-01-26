import com.badlogic.gdx.physics.box2d.World;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.edu.agh.panda5.player.Player;
import pl.edu.agh.panda5.utils.Constants;
import pl.edu.agh.panda5.utils.GameObjectFactory;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(GdxTestRunner.class)
public class PlayerTest {
    private Player player;
    private World world;
    private GameObjectFactory factory;

    @Before
    public void setUp() {
        world = new World(Constants.WORLD_GRAVITY, true);
        factory = new GameObjectFactory(world, true);
        player = factory.createPlayer();

        assertNotNull(world);
        assertNotNull(factory);
        assertNotNull(player);
    }


    @Test
    public void addPointsTest() {
        assertEquals(0, player.getPoints());
        player.addPoints(10);
        assertEquals(10, player.getPoints());
        player.addPoints(-20);
        assertEquals(-10, player.getPoints());
    }

    @Test
    public void dodgeTest() {
        assertFalse(player.isDodging());
        player.dodge();
        player.update(1);
        assertTrue(player.isDodging());
        player.stopDodge();
        player.update(1);
        assertFalse(player.isDodging());
    }

    @Test
    public void jumpTest() {
        assertFalse(player.isJumping());
        player.update(1);
        player.jump();
        assertTrue(player.isJumping());
        player.landed();
        player.update(1);
        assertFalse(player.isJumping());
    }

    @Test
    public void jumpAndDodgeTest() {
        assertFalse(player.isJumping());
        player.update(1);
        player.jump();
        player.update(1);
        assertTrue(player.isJumping());
        player.dodge();
        player.update(1);
        assertFalse(player.isDodging());
        player.landed();
        player.update(1);
        assertFalse(player.isJumping());
        player.dodge();
        player.update(1);
        assertTrue(player.isDodging());
        player.stopDodge();
        player.update(1);
        assertFalse(player.isDodging());
    }

    @Test
    public void dodgeAndJumpTest() {
        assertFalse(player.isJumping());
        player.update(1);
        player.dodge();
        player.update(1);
        assertTrue(player.isDodging());
        player.jump();
        player.update(1);
        assertFalse(player.isJumping());
        player.stopDodge();
        player.update(1);
        assertFalse(player.isDodging());
        player.jump();
        player.update(1);
        assertTrue(player.isJumping());
        player.landed();
        player.update(1);
        assertFalse(player.isJumping());
    }

    @Test
    public void cannotDropWhileJumpingTest() {
        assertFalse(player.isJumping());
        player.update(1);
        player.jump();
        player.update(1);
        assertTrue(player.isJumping());
        player.drop();
        player.update(1);
        assertFalse(player.isDropping());
    }
}
