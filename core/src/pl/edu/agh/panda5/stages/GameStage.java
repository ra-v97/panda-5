package pl.edu.agh.panda5.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import pl.edu.agh.panda5.Panda5;
import pl.edu.agh.panda5.collider.CollisionDetector;
import pl.edu.agh.panda5.environment.Coin;
import pl.edu.agh.panda5.environment.Obstacle;
import pl.edu.agh.panda5.environment.Platform;
import pl.edu.agh.panda5.opponent.Hunter;
import pl.edu.agh.panda5.player.Player;
import pl.edu.agh.panda5.player.powerups.PowerUp;
import pl.edu.agh.panda5.screens.GameOverScreen;
import pl.edu.agh.panda5.screens.GameScreen;
import pl.edu.agh.panda5.screens.MenuScreen;
import pl.edu.agh.panda5.utils.*;

import java.util.*;

public class GameStage extends Stage {

    private Panda5 game;

    private AbstractFactory factory;
    private ScoreSerializer serializer;
    private World world;
    private Platform ground;
    private Player player;
    private Hunter arrowHunter;
    private Hunter bombHunter;
    private IdentityHashMap<Platform, Platform> platforms = new IdentityHashMap<>();
    private IdentityHashMap<Obstacle, Obstacle> obstacles = new IdentityHashMap<>();

    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;
    private float accumulator2 = 0f;
    private float accumulator3 = 0f;

    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;

    private Random rand;
    private boolean onlyLowerPlatformLastTime = true;
    private Set<Coin> coins = new HashSet<>();
    private Set<PowerUp> powerUps = new HashSet<>();

    private Map<GameObjectType, Object> mutableObjects = new HashMap<>();
    private Label scores;
    private boolean pointsSaved;

    public GameStage(Panda5 game) {
        this.game = game;

        world = new World(Constants.WORLD_GRAVITY, true);
        world.setContactListener(new CollisionDetector(this));

        factory = new GameObjectFactory(world);
        serializer = game.getSerializer();
        renderer = new Box2DDebugRenderer(); // TODO: Replace in final version
        setUpCamera();

        rand = new Random();

        setUpKeyboard();
        setUpGround();
        setUpPlayer();
        setUpHunters();
        setUpMutableObjects();
        setUpPointsCounter();
    }

    private void setUpKeyboard() {
        Gdx.input.setInputProcessor(this);
    }

    private void setUpCamera() {
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        camera.update();
    }

    private void setUpGround() {
        ground = factory.createGround();
        addActor(ground);

        world.setContactFilter(new ContactFilter() {

            @Override
            public boolean shouldCollide (Fixture a, Fixture b) {
                if (((GameObjectData) a.getUserData()).getType() == GameObjectType.PLATFORM &&
                        ((GameObjectData) b.getUserData()).getType() == GameObjectType.PLAYER) {
                    Vector2 position = b.getBody().getPosition();
                    return (position.y - Constants.RUNNER_HEIGHT/2 > a.getBody().getPosition().y);
                } else if (((GameObjectData) a.getUserData()).getType() == GameObjectType.PLAYER &&
                        ((GameObjectData) b.getUserData()).getType() == GameObjectType.PLATFORM) {
                    Vector2 position = a.getBody().getPosition();
                    return (position.y - Constants.RUNNER_HEIGHT/2 > b.getBody().getPosition().y);
                } else{
                    return true;
                }
            }
        });

    }

    private void setUpPlayer() {
        player = factory.createPlayer();
        player.setUpBasicEffect(mutableObjects);
        addActor(player);
    }

    private void setUpHunters() {
        arrowHunter = factory.createHunter(0, GameObjectType.ARROW_POWER);
        addActor(arrowHunter);
        bombHunter = factory.createHunter(0, GameObjectType.BOMB_POWER);
        addActor(bombHunter);
        removeArrowHunter();
        removeBombHunter();
    }

    private void setUpMutableObjects() {
        mutableObjects.put(GameObjectType.PLAYER,player);
        mutableObjects.put(GameObjectType.COINS,coins);
    }

    private void setUpPointsCounter(){
        Label.LabelStyle textStyle;
        BitmapFont font = new BitmapFont();

        textStyle = new Label.LabelStyle();
        textStyle.font = font;

        scores = new Label("Score: 0",textStyle);
        scores.setBounds(Constants.SCORE_LABEL_POSITION_X,
                Constants.SCORE_LABEL_POSITION_Y,
                Constants.SCORE_LABEL_WIDTH,
                Constants.SCORE_LABEL_HEIGHT);
        scores.setFontScale(1.2f);
        addActor(scores);
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
            removeUnusedBullets();
            updateScore(player.getPoints());
            accumulator -= TIME_STEP;
        }

        if (accumulator2 > Constants.PLATFORM_TIME_STEP) {
            spawnPlatforms();
            accumulator2 -= Constants.PLATFORM_TIME_STEP;
        }

        if (accumulator3 >= 4) {
            arrowHunter.usePower();
            //bombHunter.usePower();
            accumulator3 = 0;
        }

        removeUnusedCoins();
        removeUnusedPowerUps();

        // TODO: Implement interpolation
    }

    private void updateScore(int score){
        scores.setText("Score: "+score);
    }

    private void spawnPlatforms() {

        boolean[] generatePlatform = new boolean[3];
        if (!onlyLowerPlatformLastTime) {
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

        for (int i = 0; i < 3; ++i) {

            //generate platform
            if (generatePlatform[i]) {
                Platform platform = factory.createPlatform(new Vector2(Constants.PLATFORM_DEFAULT_X,
                        Constants.PLATFORM_DEFAULT_Y[i]));
                addActor(platform);
                platforms.put(platform, platform);

                //generate obstacles
                if (rand.nextInt() % 100 <= Constants.OBSTACLE_GENERATION_CHANCE) {
                    Obstacle obstacle = factory.createObstacle(new Vector2(Constants.OBSTACLE_DEFAULT_X,
                            Constants.OBSTACLE_DEFAULT_Y[i]));
                    addActor(obstacle);
                    obstacles.put(obstacle, obstacle);
                }
            }


        }
        spawnCoin(new Vector2(Constants.COIN_DEFAULT_X, Constants.COIN_DEFAULT_Y), GameObjectType.COIN0);
        spawnPowerUp(1);
    }

    private void spawnCoin(Vector2 position, GameObjectType type){
        Coin coin = factory.createCoin(position, type);
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

    private void spawnArrowHunter(int level) {
        arrowHunter.setLevel(level);
        arrowHunter.setSpawned();
        arrowHunter
                .getBody()
                .setTransform(Constants.HUNTER_DEFAULT_POS_X, Constants.HUNTER_DEFAULT_POS_Y[arrowHunter.getLevel()], 0);
    }

    private void spawnBombHunter(int level) {
        bombHunter.setLevel(level);
        bombHunter.setSpawned();
        bombHunter
                .getBody()
                .setTransform(Constants.HUNTER_DEFAULT_POS_X, Constants.HUNTER_DEFAULT_POS_Y[bombHunter.getLevel()], 0);
    }

    private void removeArrowHunter() {
        arrowHunter.deleteSpawn();
        arrowHunter
                .getBody()
                .setTransform(Constants.DUMPSTER_POS, 0);
    }

    private void removeBombHunter() {
        bombHunter.deleteSpawn();
        bombHunter
                .getBody()
                .setTransform(Constants.DUMPSTER_POS, 0);
    }

    private void removeUnusedBullets() {
        arrowHunter.verifyPowers();
        bombHunter.verifyPowers();
    }

    private void spawnPowerUp(int level) {
        PowerUp powerUp = factory.createPowerUp(new Vector2(Constants.POWER_UP_DEFAULT_X, Constants.POWER_UP_DEFAULT_Y[level]));
        addActor(powerUp);
        powerUps.add(powerUp);
    }

    private void removeUnusedPowerUps() {
        powerUps.forEach(this::removePowerUp);
    }

    private void removePowerUp(PowerUp powerUp) {
        if (((GameObjectData) powerUp.getBody().getFixtureList().get(0).getUserData()).isFlaggedForDelete() ||
                powerUp.getBody().getPosition().x < 0) {
            powerUp.getBody().setTransform(Constants.DUMPSTER_POS, 0f);
            ((GameObjectData) powerUp.getBody().getFixtureList().get(0).getUserData()).setFlaggedForDelete(false);
        }
    }

    private void isPlayerInBounds() {
        if (player.getBody().getPosition().x + Constants.RUNNER_WIDTH < 0 || player.getBody().getPosition().y + Constants.RUNNER_HEIGHT < 0) // TODO: Fix this someday
            gameOver();
    }

    public void resetPoints(){
        pointsSaved =false;
    }

    public void gameOver() {
        game.pause();
        if(!pointsSaved){
            serializer.addNewScore(player.getPoints());
            pointsSaved = true;
        }
        game.setScreen(new GameOverScreen(game));
    }

    @Override
    public void draw() {
        super.draw();
        Batch batch = getBatch();
        batch.begin();
        ground.draw(batch);
        player.draw(batch);
        platforms.forEach((platform, t) -> platform.draw(batch));
        obstacles.forEach((obstacle, t) -> obstacle.draw(batch));
        coins.forEach(coin -> coin.draw(batch));
        batch.end();
        renderer.render(world, camera.combined);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.UP) {
            player.jump();
        } else if (keycode == Input.Keys.CONTROL_RIGHT) {
            player.dodge();
        }

        if(keycode == Input.Keys.DOWN)
            player.drop();

        if (keycode == Input.Keys.RIGHT)
            player.moveRight();

        if (keycode == Input.Keys.LEFT)
            player.moveLeft();

        if (keycode == Input.Keys.P)
            game.pauseOrResume();

        if (keycode == Input.Keys.R) {
            game.dispose();
            game.setScreen(new GameScreen(game));
        }

        if (keycode == Input.Keys.ENTER || keycode == Input.Keys.ESCAPE) {
            game.dispose();
            game.setScreen(new MenuScreen(game));
        }

        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.RIGHT)
            player.stopMovingRight();

        if (keycode == Input.Keys.LEFT)
            player.stopMovingLeft();

        if (player.isDodging()) {
            player.stopDodge();
        }

        return super.keyUp(keycode);
    }

    public Player getPlayer(){
        return player;
    }

    public Set<PowerUp> getPowerUps(){
        return powerUps;
    }
}

