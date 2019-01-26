package pl.edu.agh.panda5.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import pl.edu.agh.panda5.Panda5;
import pl.edu.agh.panda5.stages.MenuStage;

import java.util.List;
import java.util.ArrayList;

public class ScoreScreen implements Screen {
    private Panda5 game;
    private MenuStage stage;

    private Texture background;
    private Texture backTexture;
    private Texture listTexture;
    private TextButton backButton;

    private List<String> topList = new ArrayList<>();

    public ScoreScreen(Panda5 game) {
        this.game = game;
        background = new Texture("core/assets/background/menu_background_v2.png");
        backTexture = new Texture("core/assets/buttons/basic_button.png");
        listTexture = new Texture("core/assets/buttons/score_menu_list_v2.png");

        stage = new MenuStage(game);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.getBatch().end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void show() {

        TextButton.TextButtonStyle playButtonStyle = new TextButton.TextButtonStyle();
        BitmapFont bFont = new BitmapFont();
        playButtonStyle.font = bFont;
        playButtonStyle.fontColor=Color.BLACK;
        playButtonStyle.up = new TextureRegionDrawable(new TextureRegion(backTexture));


        backButton =  new TextButton("Go Back", playButtonStyle);
        backButton.setPosition(5 , 5);
        stage.addActor(backButton);

        backButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MenuScreen(game));
                return false;
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                backButton.setPosition(backButton.getX()+20,backButton.getY());
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                backButton.setPosition(backButton.getX()-20,backButton.getY());
            }
        });

        topList = stage.showTopTen();

        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);

        TextButton.TextButtonStyle labelStyle = new TextButton.TextButtonStyle();
        labelStyle.font = bFont;
        labelStyle.fontColor=Color.WHITE;
        labelStyle.up = new TextureRegionDrawable(new TextureRegion(listTexture));


        table.add(new TextButton("HIGH SCORES",labelStyle)).fillX().uniformX();
        table.row().pad(20, 0, 4, 0);
        for(String s : topList){
            table.add(new TextButton(s,labelStyle)).fillX().uniformX();
            table.row().pad(4, 0, 4, 0);
        }


    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
