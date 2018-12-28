import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import pl.edu.agh.panda5.player.Player;
import pl.edu.agh.panda5.utils.Constants;
import pl.edu.agh.panda5.utils.GameObjectData;
import pl.edu.agh.panda5.utils.GameObjectType;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest extends GameTest {
    private static Player player;
    private static World world;

    @BeforeAll
    public static void setUp() {
        world = new World(Constants.WORLD_GRAVITY, true);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(Constants.RUNNER_X, Constants.RUNNER_Y));
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.RUNNER_WIDTH / 2, Constants.RUNNER_HEIGHT / 2);
        Body body = world.createBody(bodyDef);
        FixtureDef fDef = new FixtureDef();
        fDef.friction = 0.0f;
        fDef.shape = shape;
        fDef.density = Constants.RUNNER_DENSITY;
        Fixture fix = body.createFixture(fDef);
        fix.setUserData(new GameObjectData(GameObjectType.PLAYER));
        body.setGravityScale(Constants.RUNNER_GRAVITY_SCALE);
        body.resetMassData();
        body.setFixedRotation(true);
        shape.dispose();

        PolygonShape feetShape = new PolygonShape();
        feetShape.setAsBox(Constants.RUNNER_FEET_WIDTH / 2f, Constants.RUNNER_FEET_HEIGHT / 2f,
                new Vector2(Constants.RUNNER_FEET_X / 2f, Constants.RUNNER_FEET_Y / 2f), 0);
        fDef.shape = feetShape;
        fDef.isSensor = true;
        Fixture feet = body.createFixture(fDef);
        feet.setUserData(new GameObjectData(GameObjectType.FEET_SENSOR));
        player = new Player(body);
        assertNotNull(world);
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
