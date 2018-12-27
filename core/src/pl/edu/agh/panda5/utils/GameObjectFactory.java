package pl.edu.agh.panda5.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import pl.edu.agh.panda5.application.GameObject;
import pl.edu.agh.panda5.environment.Coin;
import pl.edu.agh.panda5.environment.Obstacle;
import pl.edu.agh.panda5.environment.Platform;
import pl.edu.agh.panda5.opponent.*;
import pl.edu.agh.panda5.player.Player;

import static pl.edu.agh.panda5.utils.GameObjectType.*;

import java.util.*;

public class GameObjectFactory implements AbstractFactory {

    private World world;
    private Map<GameObjectType, List<GameObject>> poll = new HashMap<>();
    private final int obstacleCacheSize = 6;
    private final int platformCacheSize = 6;
    private final int[] coinCacheSize = {5, 2, 2};
    private int currentObstacle = 0;
    private int currentPlatform = 0;
    private int[] currentCoin = {0, 0, 0};

    public GameObjectFactory(World world){
        this.world = world;
        poll.put(GameObjectType.OBSTACLE, new ArrayList<>());
        poll.put(GameObjectType.PLATFORM, new ArrayList<>());
        poll.put(GameObjectType.COIN0, new ArrayList<>());
        poll.put(GameObjectType.COIN1, new ArrayList<>());
        poll.put(GameObjectType.COIN2, new ArrayList<>());

        while(poll.get(GameObjectType.OBSTACLE).size() < obstacleCacheSize) {
            poll.get(GameObjectType.OBSTACLE).add(createObstaclePrototype());
        }

        while(poll.get(GameObjectType.PLATFORM).size() < platformCacheSize) {
            poll.get(GameObjectType.PLATFORM).add(createPlatformPrototype(new Vector2(Constants.PLATFORM_DEFAULT_X,
                    Constants.PLATFORM_DEFAULT_Y[0]), Constants.PLATFORM_WIDTH, Constants.PLATFORM_HEIGHT));
        }

        while(poll.get(GameObjectType.COIN0).size() < coinCacheSize[0]) {
            poll.get(GameObjectType.COIN0).add(createCoinPrototype(0));
        }

        while(poll.get(GameObjectType.COIN1).size() < coinCacheSize[1]) {
            poll.get(GameObjectType.COIN1).add(createCoinPrototype(1));
        }

        while(poll.get(GameObjectType.COIN2).size() < coinCacheSize[2]) {
            poll.get(GameObjectType.COIN2).add(createCoinPrototype(2));
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

        Body body = createKinematicBodyPrototype(position.x, position.y, width, height,
                Constants.GROUND_DENSITY, new GameObjectData(PLATFORM));
        return new Platform(body);
    }

    public Hunter createHunter(int level, GameObjectType power)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(Constants.DUMPSTER_POS);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.HUNTER_WIDTH / 2, Constants.HUNTER_HEIGHT / 2);

        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;
        fDef.density = Constants.HUNTER_DENSITY;
        Body body = world.createBody(bodyDef);
        Fixture fixture = body.createFixture(fDef);
        fixture.setUserData(new GameObjectData(GameObjectType.HUNTER));
        shape.dispose();

        BodyDef bulletBodyDef = new BodyDef();
        PolygonShape bulletShape = new PolygonShape();
        FixtureDef bulletFixDef = new FixtureDef();
        Body bulletBody;
        Fixture bulletFixture;
        HunterPower hunterPower;

        switch(power){
            case ARROW_POWER:
                bulletBodyDef.type = BodyDef.BodyType.KinematicBody;
                bulletBodyDef.position.set(Constants.DUMPSTER_POS);
                bulletBodyDef.linearVelocity.set(Constants.BULLET_SPEED,0f);
                bulletShape.setAsBox(Constants.BULLET_LENGTH,Constants.BULLET_HEIGHT);

                bulletFixDef.shape= bulletShape;
                bulletFixDef.density = Constants.BULLET_DENSITY;
                bulletBody = world.createBody(bulletBodyDef);
                bulletFixture = body.createFixture(bulletFixDef);
                bulletFixture.setUserData(new GameObjectData(GameObjectType.BULLET));
                shape.dispose();
                hunterPower = new ArrowPower(bulletBody);
                break;

            case BOMB_POWER:
                bulletBodyDef.type = BodyDef.BodyType.DynamicBody;
                bulletBodyDef.position.set(Constants.DUMPSTER_POS);
                shape.setAsBox(Constants.BOMB_SIZE,Constants.BOMB_SIZE);
                bulletFixDef.shape= bulletShape;
                bulletFixDef.density = Constants.BOMB_DENSITY;
                bulletBody = world.createBody(bulletBodyDef);
                bulletBody.setGravityScale(Constants.BOMB_GRAVITY_SCALE);
                bulletBody.resetMassData();
                bulletFixture = bulletBody.createFixture(bulletFixDef);
                bulletFixture.setUserData(new GameObjectData(GameObjectType.BULLET));
                shape.dispose();
                hunterPower = new BombPower(bulletBody);
                break;
             default:
                 throw new RuntimeException("Invalid power type");
        }
        return new Hunter(body,level,hunterPower);
    }

    public Obstacle createObstacle(Vector2 position) {
        Obstacle obstacle = (Obstacle) poll.get(OBSTACLE).get(currentObstacle);
        obstacle.getBody().setTransform(position.x, position.y, 0f);
        currentObstacle = (currentObstacle + 1) % obstacleCacheSize;
        return obstacle;
    }

    private Obstacle createObstaclePrototype(){

        Body body = createKinematicBodyPrototype(Constants.OBSTACLE_DEFAULT_X, Constants.OBSTACLE_DEFAULT_Y[0],
                Constants.OBSTACLE_WIDTH, Constants.OBSTACLE_HEIGHT, Constants.GROUND_DENSITY, new GameObjectData(OBSTACLE));
        return new Obstacle(body);
    }

    public Coin createCoin(Vector2 position, int type) {
        Coin coin;
        switch(type) {
            case 0:
                coin = (Coin) poll.get(GameObjectType.COIN0).get(currentCoin[type]);
                break;
            case 1:
                coin = (Coin) poll.get(GameObjectType.COIN1).get(currentCoin[type]);
                break;
            case 2:
                coin = (Coin) poll.get(GameObjectType.COIN2).get(currentCoin[type]);
                break;
            default:
                throw new RuntimeException("coin type can only be one of {0, 1, 2}");
        }
        currentCoin[type] = (currentCoin[type] + 1) % coinCacheSize[type];
        coin.getBody().setTransform(position.x, position.y, 0);
        return coin;
    }

    private Coin createCoinPrototype(int type) {
        GameObjectData objectType;
        int value = Constants.COIN_VALUE[type];
        switch(type) {
            case 0: objectType = new GameObjectData(GameObjectType.COIN0); break;
            case 1: objectType = new GameObjectData(GameObjectType.COIN1); break;
            case 2: objectType = new GameObjectData(GameObjectType.COIN2); break;
            default:
                throw new RuntimeException("coin type can only be one of {0, 1, 2}");
        }
        Body body = createKinematicBodyPrototype(Constants.COIN_DEFAULT_X, Constants.COIN_DEFAULT_Y,
                Constants.COIN_WIDTH, Constants.COIN_HEIGHT, Constants.COIN_DENSITY, objectType);

        return new Coin(body, value);
    }

    private Body createKinematicBodyPrototype(float x, float y, float width, float height,
                                              float density, GameObjectData type) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(x, y);
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, height);

        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;
        fDef.density = density;

        Fixture fix = body.createFixture(fDef);
        fix.setUserData(type);
        shape.dispose();

        return body;
    }


}
