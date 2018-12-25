package pl.edu.agh.panda5.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import pl.edu.agh.panda5.application.GameObject;
import pl.edu.agh.panda5.environment.Obstacle;
import pl.edu.agh.panda5.environment.Platform;
import pl.edu.agh.panda5.opponent.Hunter;
import pl.edu.agh.panda5.player.Player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GameObjectFactory implements AbstractFactory, Runnable {

    private World world;
    private Map<GameObjectType, List<GameObject>> poll = new HashMap<>();
    private final int cacheSize = 3;
    private boolean running = true;

    public GameObjectFactory(World world){
        this.world = world;
        poll.put(GameObjectType.OBSTACLE, new LinkedList<>());
    }

    public void run(){
        while(running) {
            if (poll.get(GameObjectType.OBSTACLE).size() < cacheSize) {
                poll.get(GameObjectType.OBSTACLE).add(createObstaclePrototype());
            }
        }
    }

    public void terminate(){
        this.running = false;
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

    public Platform createPlatform(Vector2 position, float width, float height){

        BodyDef bodyDef = new BodyDef();
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
        Body body = world.createBody(bodyDef);
        body.createFixture(shape, Constants.ENEMY_DENSITY);
        body.resetMassData();
        body.setUserData(GameObjectType.HUNTER);
        shape.dispose();
        return new Hunter(body);
    }


    public Obstacle createObstacle(Vector2 position) {
        Obstacle obstacle = (Obstacle) poll.get(GameObjectType.OBSTACLE).remove(0);
        obstacle.getBody().setTransform(position.x, position.y, 0f);
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

        body.createFixture(fDef);
        body.setUserData(GameObjectType.OBSTACLE);
        shape.dispose();

        return new Obstacle(body);
    }
}
