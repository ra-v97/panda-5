package pl.edu.agh.panda5.utils;

import com.badlogic.gdx.math.Vector2;

public class Constants {

    public static final int APP_WIDTH = 800;
    public static final int APP_HEIGHT = 480;

    public static final Vector2 WORLD_GRAVITY = new Vector2(0, -10);

    //For removed objects
    public static final Vector2 DUMPSTER_POS = new Vector2(500f, 500f);

    public static final float GROUND_X = 0;
    public static final float GROUND_Y = 0;
    public static final float GROUND_WIDTH = 35f;
    public static final float GROUND_HEIGHT = 2f;
    public static final float GROUND_DENSITY = 0f;
    public static final float RIGHT_BORDER = 17f;
    public static final float RUNNER_X = 2;
    public static final float RUNNER_Y = GROUND_Y + GROUND_HEIGHT + 2.1f;
    public static final float RUNNER_WIDTH = 1f;
    public static final float RUNNER_HEIGHT = 2f;
    public static final float RUNNER_DENSITY = 0.5f;
    public static final float RUNNER_GRAVITY_SCALE = 7f;
    public static final Vector2 RUNNER_JUMPING_LINEAR_IMPULSE = new Vector2(0, 30f);
    public static final float RUNNER_RUN_RIGHT_SPEED = 10f;
    public static final float RUNNER_RUN_LEFT_SPEED = -10f;
    public static final float RUNNER_JUMP_TIMEOUT = 0.05f;

    public static final float RUNNER_FEET_X = 0f;
    public static final float RUNNER_FEET_Y = -2f;
    public static final float RUNNER_FEET_WIDTH = RUNNER_WIDTH * 0.9f;
    public static final float RUNNER_FEET_HEIGHT = 0.2f;

    public static final Vector2 GAME_LINEAR_VELOCITY = new Vector2(-10f, 0);

    public static final float PLATFORM_WIDTH = 3f;
    public static final float PLATFORM_HEIGHT = 0.2f;
    public static final float PLATFORM_DEFAULT_X = 25f;
    public static final float PLATFORM_TIME_STEP = 1.5f;
    public static final float[] PLATFORM_DEFAULT_Y = {0f, 5f, 10f};
    public static final int PLATFORM_GENERATION_CHANCE = 10;

    public static final float OBSTACLE_WIDTH = 0.7f;
    public static final float OBSTACLE_HEIGHT = 0.55f;
    public static final float OBSTACLE_DEFAULT_X = 25f;
    public static final float[] OBSTACLE_DEFAULT_Y = {
            PLATFORM_DEFAULT_Y[0] + PLATFORM_HEIGHT + OBSTACLE_HEIGHT,
            PLATFORM_DEFAULT_Y[1] + PLATFORM_HEIGHT + OBSTACLE_HEIGHT,
            PLATFORM_DEFAULT_Y[2] + PLATFORM_HEIGHT + OBSTACLE_HEIGHT
    };
    public static final int OBSTACLE_GENERATION_CHANCE = 20;

    public static final float HUNTER_DENSITY = RUNNER_DENSITY;
    public static final float HUNTER_WIDTH = 1f;
    public static final float HUNTER_HEIGHT = 1f;
    public static final float HUNTER_DEFAULT_POS_X = 19f;
    public static final float HUNTER_POS_Y_OFFSET = 1f;
    public static final float HUNTER_DEFAULT_POS_Y[] = {
            PLATFORM_DEFAULT_Y[0] + PLATFORM_HEIGHT + HUNTER_POS_Y_OFFSET,
            PLATFORM_DEFAULT_Y[1] + PLATFORM_HEIGHT + HUNTER_POS_Y_OFFSET,
            PLATFORM_DEFAULT_Y[2] + PLATFORM_HEIGHT + HUNTER_POS_Y_OFFSET
    };

    public static final Vector2 BULLET_SPEED = new Vector2(-17f,0);
    public static final float BULLET_DENSITY = 1f;
    public static final float BULLET_HEIGHT = 0.01f;
    public static final float BULLET_LENGTH = 0.2f;
    public static final float BULLET_DEFAULT_POS_X = HUNTER_DEFAULT_POS_X+0.05f;
    public static final float BULLET_POS_Y_OFFSET = (float)0.5* HUNTER_HEIGHT;
    public static final float BULLET_DEFAULT_POS_Y[] = {
            HUNTER_DEFAULT_POS_Y[0] + BULLET_POS_Y_OFFSET,
            HUNTER_DEFAULT_POS_Y[1] + BULLET_POS_Y_OFFSET,
            HUNTER_DEFAULT_POS_Y[2] + BULLET_POS_Y_OFFSET
    };

    public static final float BOMB_SIZE = 0.1f;
    public static final float BOMB_DENSITY = GROUND_DENSITY;
    public static final float BOMB_POS_Y= 15f;

    public static final float COIN_WIDTH = 0.1f;
    public static final float COIN_HEIGHT = 0.1f;
    public static final float COIN_DEFAULT_X = 25f;
    public static final float COIN_DEFAULT_Y = 3f;
    public static final float COIN_DENSITY = GROUND_DENSITY;

    public static final int[] COIN_VALUE = {1, 3, 5};

    public static final float POWER_UP_WIDTH = 0.15f;
    public static final float POWER_UP_HEIGHT = 0.15f;
    public static final float POWER_UP_DENSITY = GROUND_DENSITY;
    public static final float POWER_UP_Y_OFFSET = 1f;
    public static final float POWER_UP_DEFAULT_X = 22f;
    public static final float[] POWER_UP_DEFAULT_Y = {
            PLATFORM_DEFAULT_Y[0] + PLATFORM_HEIGHT + POWER_UP_Y_OFFSET ,
            PLATFORM_DEFAULT_Y[1] + PLATFORM_HEIGHT + POWER_UP_Y_OFFSET ,
            PLATFORM_DEFAULT_Y[2] + PLATFORM_HEIGHT + POWER_UP_Y_OFFSET
    };
    public static final float DEFAULT_POWER_UP_TIMEOUT = 20.0f;
}
