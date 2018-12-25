package pl.edu.agh.panda5.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import pl.edu.agh.panda5.stages.GameStage;

public class GameScreen implements Screen {
    private GameStage stage;
    private boolean isPaused = false;

    public GameScreen() {
        stage = new GameStage();
    }

    public void render(float delta) {
        if (isPaused)
            delta = 0;
        //Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Update the stage
        stage.draw();
        stage.act(delta);
    }

    public void resize(int width, int height) {

    }

    public void show() {

    }

    public void hide() {

    }

    public void pause() {
        isPaused = true;
    }

    public void resume() {
        isPaused = false;
    }


    public void dispose() {
        stage.dispose();
    }

}