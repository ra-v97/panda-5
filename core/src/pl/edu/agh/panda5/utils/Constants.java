package pl.edu.agh.panda5.utils;

import com.badlogic.gdx.math.Vector2;

public class Constants {

    public static final int APP_WIDTH = 800;
    public static final int APP_HEIGHT = 480;

    public static final Vector2 WORLD_GRAVITY = new Vector2(0, -10);

    public static final float GROUND_X = 0;
    public static final float GROUND_Y = 0;
    public static final float GROUND_WIDTH = 25f;
    public static final float GROUND_HEIGHT = 2f;
    public static final float GROUND_DENSITY = 0f;

    public static final float RUNNER_X = 2;
    public static final float RUNNER_Y = GROUND_Y + GROUND_HEIGHT + 0.1f;
    public static final float RUNNER_WIDTH = 1f;
    public static final float RUNNER_HEIGHT = 2f;
    public static final float RUNNER_DENSITY = 0.5f;
    public static final float RUNNER_GRAVITY_SCALE = 3f;
    public static final Vector2 RUNNER_JUMPING_LINEAR_IMPULSE = new Vector2(0, 13f);
    public static final float RUNNER_RUN_RIGHT_SPEED = 10f;
    public static final float RUNNER_RUN_LEFT_SPEED = -10f;
    public static final float RUNNER_JUMP_TIMEOUT = 2f; //TODO: teraz jest ustawione 2s; zamienić na ok 50ms

    public static final float RUNNER_FEET_X = 0f;
    public static final float RUNNER_FEET_Y = -2f;
    public static final float RUNNER_FEET_WIDTH = 0.5f;
    public static final float RUNNER_FEET_HEIGHT = 0.1f;

}
