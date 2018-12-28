package pl.edu.agh.panda5.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import pl.edu.agh.panda5.Panda5;
import pl.edu.agh.panda5.collider.CollisionDetector;
import pl.edu.agh.panda5.environment.Coin;
import pl.edu.agh.panda5.environment.Obstacle;
import pl.edu.agh.panda5.environment.Platform;
import pl.edu.agh.panda5.opponent.Hunter;
import pl.edu.agh.panda5.player.Player;
import pl.edu.agh.panda5.screens.GameOverScreen;
import pl.edu.agh.panda5.utils.*;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GameStage extends Stage {

    // This will be our viewport measurements while working with the debug renderer
    private static final int VIEWPORT_WIDTH = 20;
    private static final int VIEWPORT_HEIGHT = 13;

    private Panda5 game;

    private AbstractFactory factory;
    private World world;
    private Platform ground;
    private Player player;
    private Hunter arrowHunter;
    private Hunter bombHunter;

    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;
    private float accumulator2 = 0f;
    private float accumulator3 = 0f;

    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;

    private Random rand;
    private boolean onlyLowerPlatformLastTime = true;
    private Set<Coin> coins = new HashSet<>();

    public GameStage(Panda5 game) {
        this.game = game;

        world = new World(Constants.WORLD_GRAVITY, true);
        world.setContactListener(new CollisionDetector(this));

        factory = new GameObjectFactory(world);

        renderer = new Box2DDebugRenderer(); // TODO: Replace in final version
        setUpCamera();

        rand = new Random();

        setUpKeyboard();
        setUpGround();
        setUpPlayer();
        //setUpHunters();
        //spawnArrowHunter();
        //spawnBombHunter();
        //removeBombHunter();
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
        ground = factory.createGround();
        addActor(ground);
    }

    private void setUpPlayer() {
        player = factory.createPlayer();
        addActor(player);
    }

    private void setUpHunters(){
        arrowHunter = factory.createHunter(1,GameObjectType.ARROW_POWER);
        addActor(arrowHunter);
        bombHunter = factory.createHunter(0,GameObjectType.BOMB_POWER);
        addActor(bombHunter);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Fixed timestep
        accumulator += delta;
        accumulator2 += delta;
        accumulator3 += delta;


        while (accumulator >= TIME_STEP) {
            world.step(TIME_STEP, 6, 2);
            player.update(delta);
            isPlayerInBounds();
            accumulator -= TIME_STEP;
        }

        if(accumulator2 > Constants.PLATFORM_TIME_STEP) {
            spawnPlatforms();
            accumulator2 -= Constants.PLATFORM_TIME_STEP;
            //bombHunter.usePower();
            //arrowHunter.usePower();
        }

        if(accumulator3 > 2) {
            accumulator3 = 0;
        }

        removeUnusedCoins();
        // TODO: Implement interpolation
    }

    private void spawnPlatforms(){

        boolean[] generatePlatform = new boolean[3];
        if(!onlyLowerPlatformLastTime) {
            //40% chance to generate each platform
            generatePlatform[0] = rand.nextInt() % 100 <= Constants.PLATFORM_GENERATION_CHANCE;
            generatePlatform[1] = rand.nextInt() % 100 <= Constants.PLATFORM_GENERATION_CHANCE;
            generatePlatform[2] = rand.nextInt() % 100 <= Constants.PLATFORM_GENERATION_CHANCE;

            //if there is no platform generated choose one at random
            boolean oneGenerated = false;
            for (int i = 0; i < 3; ++i) {
                if (generatePlatform[i])
                    oneGenerated = true;
            }

            if (!oneGenerated)
                //Zero out sign bit so that nextInt returns only positive ints
                generatePlatform[(rand.nextInt() & Integer.MAX_VALUE) % 3] = true;
        } else {
            //40% chance to generate each platform
            generatePlatform[0] = rand.nextInt() % 100 <= Constants.PLATFORM_GENERATION_CHANCE;
            generatePlatform[1] = rand.nextInt() % 100 <= Constants.PLATFORM_GENERATION_CHANCE;

            //if there is no platform generated choose one at random
            boolean oneGenerated = false;
            for (int i = 0; i < 2; ++i) {
                if (generatePlatform[i])
                    oneGenerated = true;
            }

            if (!oneGenerated)
                //Zero out sign bit so that nextInt returns only positive ints
                generatePlatform[(rand.nextInt() & Integer.MAX_VALUE) % 2] = true;
        }

        onlyLowerPlatformLastTime = generatePlatform[0] && !generatePlatform[1] && !generatePlatform[2];

        for(int i = 0; i < 3; ++i) {

            //generate platform
            if(generatePlatform[i]) {
                Platform platform = factory.createPlatform(new Vector2(Constants.PLATFORM_DEFAULT_X,
                        Constants.PLATFORM_DEFAULT_Y[i]));
                addActor(platform);

                //generate obstacles
                if(rand.nextInt() % 100 <= Constants.OBSTACLE_GENERATION_CHANCE) {
                    Obstacle obstacle = factory.createObstacle(new Vector2(Constants.OBSTACLE_DEFAULT_X,
                            Constants.OBSTACLE_DEFAULT_Y[i]));
                    addActor(obstacle);
                }
            }


        }
        Coin coin = factory.createCoin(new Vector2(Constants.COIN_DEFAULT_X, Constants.COIN_DEFAULT_Y), 0);
        addActor(coin);
        coins.add(coin);
    }

    private void removeUnusedCoins() {
        coins.forEach(this::removeCoin);
    }

    private void removeCoin(Coin coin) {
        if (((GameObjectData) coin.getBody().getFixtureList().get(0).getUserData()).isFlaggedForDelete()) {
            coin.getBody().setTransform(Constants.DUMPSTER_POS, 0f);
            ((GameObjectData) coin.getBody().getFixtureList().get(0).getUserData()).setFlaggedForDelete(false);
        }
    }

    private void spawnArrowHunter(){
        arrowHunter.setSpawned();
        arrowHunter
                .getBody()
                .setTransform(Constants.HUNTER_DEFAULT_POS_X,Constants.HUNTER_DEFAULT_POS_Y[arrowHunter.getLevel()],0);
    }

    private void spawnBombHunter(){
        bombHunter.setSpawned();
        bombHunter
                .getBody()
                .setTransform(Constants.HUNTER_DEFAULT_POS_X,Constants.HUNTER_DEFAULT_POS_Y[bombHunter.getLevel()],0);
    }

    private void removeArrowHunter(){
        arrowHunter.deleteSpawn();
        arrowHunter
                .getBody()
                .setTransform(Constants.DUMPSTER_POS,0);
    }
    private void removeBombHunter(){
        bombHunter.deleteSpawn();
        bombHunter
                .getBody()
                .setTransform(Constants.DUMPSTER_POS,0);
    }

    private void isPlayerInBounds(){
        if(player.getBody().getPosition().x + Constants.RUNNER_WIDTH < 0 || player.getBody().getPosition().y + Constants.RUNNER_HEIGHT < 0) // TODO: Fix this someday
            gameOver();
    }


    private void gameOver(){
        game.pause();
        game.setScreen(new GameOverScreen(game));
    }

    @Override
    public void draw() {
        super.draw();
        renderer.render(world, camera.combined);
    }

    @Override
    public void dispose(){
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

    public void beginContact(Fixture a, Fixture b){

        GameObjectType aType = ((GameObjectData)a.getUserData()).getType();
        GameObjectType bType = ((GameObjectData)b.getUserData()).getType();

        if(aType == GameObjectType.FEET_SENSOR || bType == GameObjectType.FEET_SENSOR) {
            if(aType == GameObjectType.PLATFORM || bType == GameObjectType.PLATFORM ||
                    aType == GameObjectType.OBSTACLE || bType == GameObjectType.OBSTACLE) {
                player.landed();
            }
        }

        if(aType == GameObjectType.FEET_SENSOR || aType == GameObjectType.PLAYER) {
            if(((GameObjectData)b.getUserData()).isCoin()) {
                handlePlayerCoinContact(b);
            }
        } else if(bType == GameObjectType.FEET_SENSOR || bType == GameObjectType.PLAYER) {
            if (((GameObjectData)a.getUserData()).isCoin()) {
                handlePlayerCoinContact(a);
            }
        }

        if(aType == GameObjectType.PLAYER || bType == GameObjectType.PLAYER) {
            if(aType == GameObjectType.BULLET || bType == GameObjectType.BULLET ) {
                gameOver();
            }
        }
        if (aType == GameObjectType.BULLET || bType == GameObjectType.BULLET) {
            if(aType == GameObjectType.PLAYER || bType == GameObjectType.PLAYER) {
                gameOver();
            }else if(aType == GameObjectType.PLATFORM || bType == GameObjectType.PLATFORM ||
                    aType == GameObjectType.OBSTACLE || bType == GameObjectType.OBSTACLE){
                arrowHunter.verifyPowers();
                bombHunter.verifyPowers();
            }
        }
    }

    private void handlePlayerCoinContact(Fixture coin) {
        if (coin.getUserData() == GameObjectType.COIN0) {
            player.addPoints(Constants.COIN_VALUE[0]);
        } else if (coin.getUserData() == GameObjectType.COIN1) {
            player.addPoints(Constants.COIN_VALUE[1]);
        }  else if (coin.getUserData() == GameObjectType.COIN2) {
            player.addPoints(Constants.COIN_VALUE[2]);
        }

        ((GameObjectData)coin.getUserData()).setFlaggedForDelete(true);
    }

    public void endContact(Fixture a, Fixture b) {
        if(((GameObjectData)a.getUserData()).getType() == GameObjectType.FEET_SENSOR || ((GameObjectData)b.getUserData()).getType() == GameObjectType.FEET_SENSOR) {
            if((((GameObjectData)a.getUserData()).getType() == GameObjectType.PLATFORM) || (((GameObjectData)b.getUserData()).getType() == GameObjectType.PLATFORM) ||
                    (((GameObjectData)a.getUserData()).getType() == GameObjectType.OBSTACLE) || (((GameObjectData)b.getUserData()).getType() == GameObjectType.OBSTACLE)) {
                player.fall();
            }
        }
    }
}

