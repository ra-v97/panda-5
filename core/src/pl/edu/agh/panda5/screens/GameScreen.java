package pl.edu.agh.panda5.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import pl.edu.agh.panda5.Panda5;
import pl.edu.agh.panda5.stages.GameStage;

public class GameScreen implements Screen {
    private Panda5 game;
    private GameStage stage;
    private boolean isPaused = false;

    public GameScreen(Panda5 game) {
        this.game = game;
        stage = new GameStage(game);
    }

    public void render(float delta) {
        if (isPaused)
            delta = 0;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell our stage to do actions and draw itself
        stage.getBatch().begin();
        stage.getBatch().end();
        stage.act(delta);
        stage.draw();
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