package pl.edu.agh.panda5.stages;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import pl.edu.agh.panda5.environment.Platform;
import pl.edu.agh.panda5.player.Player;
import pl.edu.agh.panda5.utils.AbstractFactory;
import pl.edu.agh.panda5.utils.GameObjectFactory;
import pl.edu.agh.panda5.utils.WorldUtils;

public class GameStage extends Stage {

    // This will be our viewport measurements while working with the debug renderer
    private static final int VIEWPORT_WIDTH = 20;
    private static final int VIEWPORT_HEIGHT = 13;

    private AbstractFactory factory;
    private World world;
    private Platform ground;
    private Player player;

    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;

    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;

    public GameStage() {
        factory = new GameObjectFactory();
        world = WorldUtils.createWorld();
        setUpGround();
        setUpPlayer();
        renderer = new Box2DDebugRenderer(); // TODO: Delete in final version
        setUpCamera();
    }

    private void setUpCamera() {
        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        camera.update();
    }

    private void setUpGround() {
        ground = factory.createPlatform(world);
        addActor(ground);
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

        while (accumulator >= delta) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }

        // TODO: Implement interpolation

    }

    @Override
    public void draw() {
        super.draw();
        renderer.render(world, camera.combined);
    }

}

