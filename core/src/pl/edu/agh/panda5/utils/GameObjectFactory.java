package pl.edu.agh.panda5.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import pl.edu.agh.panda5.application.GameObject;
import pl.edu.agh.panda5.environment.Obstacle;
import pl.edu.agh.panda5.environment.Platform;
import pl.edu.agh.panda5.opponent.Bullet;
import pl.edu.agh.panda5.opponent.Hunter;
import pl.edu.agh.panda5.player.Player;

import java.util.*;

import static pl.edu.agh.panda5.utils.GameObjectType.BULLET;

public class GameObjectFactory implements AbstractFactory {

    private World world;
    private Map<GameObjectType, List<GameObject>> poll = new HashMap<>();
    private final int obstacleCacheSize = 4;
    private final int platformCacheSize = 6;
    private int currentObstacle = 0;
    private int currentPlatform = 0;

    public GameObjectFactory(World world){
        this.world = world;
        poll.put(GameObjectType.OBSTACLE, new ArrayList<>());
        poll.put(GameObjectType.PLATFORM, new ArrayList<>());

        while(poll.get(GameObjectType.OBSTACLE).size() < obstacleCacheSize) {
            poll.get(GameObjectType.OBSTACLE).add(createObstaclePrototype());
        }

        while(poll.get(GameObjectType.PLATFORM).size() < platformCacheSize) {
            poll.get(GameObjectType.PLATFORM).add(createPlatformPrototype(new Vector2(Constants.PLATFORM_DEFAULT_X,
                    Constants.PLATFORM_Y[0]), Constants.PLATFORM_WIDTH, Constants.PLATFORM_HEIGHT));
        }
    }

    public Player createPlayer(){

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

        PolygonShape feetShape = new PolygonShape();
        feetShape.setAsBox(Constants.RUNNER_FEET_WIDTH / 2f, Constants.RUNNER_FEET_HEIGHT / 2f,
                new Vector2(Constants.RUNNER_FEET_X / 2f, Constants.RUNNER_FEET_Y / 2f), 0);
        fDef.shape = feetShape;
        fDef.isSensor = true;
        Fixture feet = body.createFixture(fDef);
        feet.setUserData(GameObjectType.FEET_SENSOR);

        return new Player(body);
    }

    public Platform createGround() {
        return createPlatformPrototype(new Vector2(Constants.GROUND_X, Constants.GROUND_Y), Constants.GROUND_WIDTH,
                Constants.GROUND_HEIGHT);
    }

    public Platform createPlatform(Vector2 position) {
        Platform platform = (Platform) poll.get(GameObjectType.PLATFORM).get(currentPlatform);
        platform.getBody().setTransform(position.x, position.y, 0f);
        currentPlatform = (currentPlatform + 1) % platformCacheSize;
        return platform;
    }

    private Platform createPlatformPrototype(Vector2 position, float width, float height) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(position);
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, height);

        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;
        fDef.density = Constants.GROUND_DENSITY;

        Fixture fixture = body.createFixture(fDef);
        fixture.setUserData(GameObjectType.PLATFORM);
        body.setUserData(GameObjectType.PLATFORM);
        shape.dispose();

        return new Platform(body);
    }

    public Hunter createHunter(Vector2 position) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(position);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.HUNTER_WIDTH / 2, Constants.HUNTER_HEIGHT / 2);

        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;
        fDef.density = Constants.ENEMY_DENSITY;
        Body body = world.createBody(bodyDef);
        Fixture fixture = body.createFixture(fDef);
        fixture.setUserData(GameObjectType.HUNTER);
        shape.dispose();

        return new Hunter(body);
    }

    public Bullet createBullet(Vector2 velocity,int level){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(Constants.HUNTER_DEFAULT_POS.x,(float)(Constants.GROUND_Y + Constants.GROUND_HEIGHT + 0.2*Constants.RUNNER_HEIGHT+Constants.HUNTER_HEIGHT*0.5));
        bodyDef.linearVelocity.set(-velocity.x,velocity.y);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.ARROW_LENGTH,Constants.ARROW_HEIGHT);

        FixtureDef fDef = new FixtureDef();
        fDef.shape= shape;
        fDef.density = Constants.ARROW_DENSITY;
        Body body = world.createBody(bodyDef);
        Fixture fixture = body.createFixture(fDef);
        fixture.setUserData(GameObjectType.BULLET);
        shape.dispose();

        return new Bullet(body);

    }

    public Obstacle createObstacle(Vector2 position) {
        Obstacle obstacle = (Obstacle) poll.get(GameObjectType.OBSTACLE).get(currentObstacle);
        obstacle.getBody().setTransform(position.x, position.y, 0f);
        currentObstacle = (currentObstacle + 1) % obstacleCacheSize;
        return obstacle;
    }


    private Obstacle createObstaclePrototype(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(Constants.OBSTACLE_DEFAULT_POS);
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.OBSTACLE_WIDTH, Constants.OBSTACLE_HEIGHT);

        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;
        fDef.density = Constants.GROUND_DENSITY;

        Fixture fix = body.createFixture(fDef);
        fix.setUserData(GameObjectType.OBSTACLE);
        shape.dispose();

        return new Obstacle(body);
    }
}
