package pl.edu.agh.panda5.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import pl.edu.agh.panda5.environment.Platform;
import pl.edu.agh.panda5.player.Player;

public class GameObjectFactory implements AbstractFactory {

    public Player createPlayer(World world){

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
        body.createFixture(fDef);
        body.setGravityScale(Constants.RUNNER_GRAVITY_SCALE);
        body.resetMassData();
        body.setFixedRotation(true);
        body.setUserData(GameObjectType.PLAYER);
        shape.dispose();

        return new Player(body, null);
    }

    public Platform createPlatform(World world, Vector2 position, float width, float height){

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, height);
        body.createFixture(shape, Constants.GROUND_DENSITY);
        body.setUserData(GameObjectType.PLATFORM);
        shape.dispose();

        return new Platform(body);
    }
}
