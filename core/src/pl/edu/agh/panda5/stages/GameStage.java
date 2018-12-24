package pl.edu.agh.panda5.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import pl.edu.agh.panda5.collider.CollisionDetector;
import pl.edu.agh.panda5.environment.Platform;
import pl.edu.agh.panda5.player.Player;
import pl.edu.agh.panda5.utils.*;

public class GameStage extends Stage {

    // This will be our viewport measurements while working with the debug renderer
    private static final int VIEWPORT_WIDTH = 20;
    private static final int VIEWPORT_HEIGHT = 13;

    private AbstractFactory factory;
    private World world;
    private Platform ground;
    private Platform tmpPlatform;
    private Player player;

    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;

    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;

    public GameStage() {
        factory = new GameObjectFactory();
        world = WorldUtils.createWorld();
        world.setContactListener(new CollisionDetector(this));
        setUpGround();
        setUpPlayer();
        setUpAdditionalPlatform();
        renderer = new Box2DDebugRenderer(); // TODO: Replace in final version
        setUpCamera();
        setUpKeyboard();
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
        ground = factory.createPlatform(world, new Vector2(Constants.GROUND_X, Constants.GROUND_Y),
                Constants.GROUND_WIDTH, Constants.GROUND_HEIGHT / 2);
        addActor(ground);
    }

    private void setUpAdditionalPlatform() {
        tmpPlatform = factory.createPlatform(world, new Vector2(2f, 5.5f), 1, 1);
        addActor(tmpPlatform);
    }

    private void setUpPlayer() {
        player = factory.createPlayer(world);
        addActor(player);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Fixed timestep
        accumulator += delta;

        while (accumulator >= TIME_STEP) {
            world.step(TIME_STEP, 6, 2);
            player.update(delta);
            accumulator -= TIME_STEP;
        }

        // TODO: Implement interpolation

    }

    @Override
    public void draw() {
        super.draw();
        renderer.render(world, camera.combined);
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

        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.RIGHT)
            player.stop();

        if(keycode == Input.Keys.LEFT)
            player.stop();

        if (player.isDodging()) {
            player.stopDodge();
        }

        return super.keyUp(keycode);
    }

    public void beginContact(Body a , Body b){
        if((a.getUserData() == GameObjectType.PLAYER && b.getUserData() == GameObjectType.PLATFORM) ||
                (a.getUserData() == GameObjectType.PLATFORM && b.getUserData() == GameObjectType.PLAYER)){
            player.landed();
        }
    }
}

