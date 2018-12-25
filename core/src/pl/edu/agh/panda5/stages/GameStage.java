package pl.edu.agh.panda5.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import pl.edu.agh.panda5.Panda5;
import pl.edu.agh.panda5.collider.CollisionDetector;
import pl.edu.agh.panda5.environment.Obstacle;
import pl.edu.agh.panda5.environment.Platform;
import pl.edu.agh.panda5.opponent.Hunter;
import pl.edu.agh.panda5.player.Player;
import pl.edu.agh.panda5.screens.GameOverScreen;
import pl.edu.agh.panda5.utils.*;

import java.util.ArrayList;
import java.util.List;

public class GameStage extends Stage {

    // This will be our viewport measurements while working with the debug renderer
    private static final int VIEWPORT_WIDTH = 20;
    private static final int VIEWPORT_HEIGHT = 13;

    private Panda5 game;

    private AbstractFactory factory;
    private World world;
    private Platform ground;
    private Player player;
    private Hunter hunter;

    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;
    private float accumulator2 = 0f;

    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;

    public GameStage(Panda5 game) {
        this.game = game;

        world = new World(Constants.WORLD_GRAVITY, true);
        world.setContactListener(new CollisionDetector(this));

        factory = new GameObjectFactory(world);
        new Thread((GameObjectFactory) factory).start();

        renderer = new Box2DDebugRenderer(); // TODO: Replace in final version
        setUpCamera();

        setUpKeyboard();
        setUpGround();
        setUpPlayer();
        setUpEnemy();
    }

    private void setUpKeyboard() {
        Gdx.input.setInputProcessor(this);
    }

    private void setUpCamera() {
        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        camera.update();
    }

    private void setUpGround() {
        ground = factory.createPlatform(new Vector2(Constants.GROUND_X, Constants.GROUND_Y),
                Constants.GROUND_WIDTH, Constants.GROUND_HEIGHT / 2);
        addActor(ground);
    }

    private void setUpPlayer() {
        player = factory.createPlayer();
        addActor(player);
    }

    private void setUpEnemy(){
        hunter = factory.createHunter(Constants.HUNTER_DEFAULT_POS);
        addActor(hunter);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Fixed timestep
        accumulator += delta;
        accumulator2 += delta;


        while (accumulator >= TIME_STEP) {
            world.step(TIME_STEP, 6, 2);
            player.update(delta);
            isPlayerInBounds();
            accumulator -= TIME_STEP;
        }

        if(accumulator2 > 3) {
            spawnObstacle();
            accumulator2 = 0;
        }
        // TODO: Implement interpolation
    }

    private void spawnObstacle(){
        Obstacle obstacle = factory.createObstacle(Constants.OBSTACLE_DEFAULT_POS);
        addActor(obstacle);
    }

    private void isPlayerInBounds(){
        if(player.getBody().getPosition().x + Constants.RUNNER_WIDTH < 0 || player.getBody().getPosition().y + Constants.RUNNER_HEIGHT < 0){
            game.pause();
            game.setScreen(new GameOverScreen(game));
        }
    }

    @Override
    public void draw() {
        super.draw();
        renderer.render(world, camera.combined);
    }

    @Override
    public void dispose(){
        ((GameObjectFactory) factory).terminate();
        super.dispose();
    }

    @Override
    public boolean keyDown (int keycode) {
        if(keycode == Input.Keys.UP){
            player.jump();
        } else if (keycode == Input.Keys.DOWN){
            player.dodge();
        }

        if(keycode == Input.Keys.RIGHT)
            player.moveRight();

        if(keycode == Input.Keys.LEFT)
            player.moveLeft();

        if(keycode == Input.Keys.P)
            game.pauseOrResume();

        if(keycode == Input.Keys.R) {
           game.dispose();
           game.create();
        }

        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.RIGHT)
            player.stopMovingRight();

        if(keycode == Input.Keys.LEFT)
            player.stopMovingLeft();

        if (player.isDodging()) {
            player.stopDodge();
        }

        return super.keyUp(keycode);
    }

    public void beginContact(Fixture a ,Fixture b){
        if((a.getUserData() == GameObjectType.FEET_SENSOR && b.getUserData() == GameObjectType.PLATFORM) ||
                (a.getUserData() == GameObjectType.PLATFORM && b.getUserData() == GameObjectType.FEET_SENSOR)){
            player.landed();
        }
    }
}

