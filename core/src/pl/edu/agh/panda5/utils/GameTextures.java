package pl.edu.agh.panda5.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class GameTextures {
    public static final Texture POWER_UP = new Texture(Gdx.files.internal("core/assets/powerup/powerups.png"));
    public static final Texture PLAYER = new Texture(Gdx.files.internal("core/assets/panda/panda.png"));
    public static final Texture OBSTACLE = new Texture(Gdx.files.internal("core/assets/terrain/boulder.png"));
    public static final Texture COIN = new Texture(Gdx.files.internal("core/assets/coins/coins.png"));
    public static final Texture PLATFORM = new Texture(Gdx.files.internal("core/assets/terrain/terrain.png"));
    public static final Texture BACKGROUND = new Texture(Gdx.files.internal("core/assets/terrain/bg_jungle.png"));
}
