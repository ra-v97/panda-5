import com.badlogic.gdx.physics.box2d.World;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.edu.agh.panda5.player.Player;
import pl.edu.agh.panda5.utils.Constants;
import pl.edu.agh.panda5.utils.GameObjectFactory;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;


public class PlayerTest extends GameTest {
    private static Player player;
    private static World world;
    private static GameObjectFactory factory;

    @BeforeAll
    public static void setUp() {
        try {
            System.out.println(new java.io.File( "." ).getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        assertTrue(player.isDodging());
        player.stopDodge();
        assertFalse(player.isDodging());
    }

    @Test
    public void jumpTest() {
        assertFalse(player.isJumping());
        player.update(1);
        player.jump();
        assertTrue(player.isJumping());
        player.landed();
        assertFalse(player.isJumping());
    }

    @Test
    public void jumpAndDodgeTest() {
        assertFalse(player.isJumping());
        player.update(1);
        player.jump();
        assertTrue(player.isJumping());
        player.dodge();
        assertFalse(player.isDodging());
        player.landed();
        assertFalse(player.isJumping());
        player.dodge();
        assertTrue(player.isDodging());
        player.stopDodge();
        assertFalse(player.isDodging());
    }

    @Test
    public void dodgeAndJumpTest() {
        assertFalse(player.isJumping());
        player.update(1);
        player.dodge();
        assertTrue(player.isDodging());
        player.jump();
        assertFalse(player.isJumping());
        player.stopDodge();
        assertFalse(player.isDodging());
        player.jump();
        assertTrue(player.isJumping());
        player.landed();
        assertFalse(player.isJumping());
    }
}
