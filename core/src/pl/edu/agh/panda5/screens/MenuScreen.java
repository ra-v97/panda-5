package pl.edu.agh.panda5.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import pl.edu.agh.panda5.Panda5;
import pl.edu.agh.panda5.stages.MenuStage;


public class MenuScreen implements Screen {

    private class MenuListener extends ClickListener{
        private final Button button;
        private MenuListener(Button button){
            this.button=button;
        }

        @Override
        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
            button.setWidth(button.getWidth() + 75);
        }

        @Override
        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
            button.setWidth(button.getWidth() - 75);
        }
    }

    private Panda5 game;
    private MenuStage stage;

    private Texture background;
    private Texture start_button;
    private Texture exit_button;
    private Texture score_button;

    public MenuScreen(Panda5 game) {
        this.game = game;
        background = new Texture("core/assets/background/menu_background_v2.png");
        start_button = new Texture("core/assets/buttons/play_menu_button.png");
        score_button = new Texture("core/assets/buttons/score_menu_button.png");
        exit_button = new Texture("core/assets/buttons/quit_menu_button.png");
        stage = new MenuStage(game);
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell our stage to do actions and draw itself
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.getBatch().end();
        stage.act(delta);
        stage.draw();
    }

    public void hide() {
    }

    public void pause() {
    }

    public void resume() {
    }

    public void show() {
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);

        ImageButton newGame = new ImageButton(new TextureRegionDrawable(new TextureRegion(start_button)));
        ImageButton scores = new ImageButton(new TextureRegionDrawable(new TextureRegion(score_button)));
        ImageButton exit = new ImageButton(new TextureRegionDrawable(new TextureRegion(exit_button)));

        table.add(newGame).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(scores).fillX().uniformX();
        table.row();
        table.add(exit).fillX().uniformX();

        newGame.addListener(new MenuListener(newGame) {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.startRun();
                return false;
            }
        });

        scores.addListener(new MenuListener(scores) {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new ScoreScreen(game));
                return false;
            }
        });

        exit.addListener(new MenuListener(exit) {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return false;
            }
        });
    }

    public void resize(int width, int height) {
    }

    public void dispose() {
        stage.dispose();
    }

}