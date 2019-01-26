package pl.edu.agh.panda5.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pl.edu.agh.panda5.Panda5;
import pl.edu.agh.panda5.utils.Constants;


public class GameOverScreen implements Screen{
    private Panda5 game;
    private int points;


    public GameOverScreen(Panda5 game, int points) {
        this.game = game;
        this.points = points;
    }

    public void render(float delta) {
        //Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        SpriteBatch batch = new SpriteBatch();
        BitmapFont font = new BitmapFont();
        String fontText = "Game Over. You scored " + points;

        if(points == 1)
            fontText += " point";
        else
            fontText += " points";

        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, fontText);

        batch.begin();
        font.draw(batch, layout, Constants.APP_WIDTH / 2f - layout.width/2, Constants.APP_HEIGHT / 2f); // TODO: Fix, screen size is not constant
        batch.end();
    }



    public void resize(int width, int height) {

    }

    public void show() {

    }

    public void hide() {

    }

    public void pause() {
    }

    public void resume() {
    }


    public void dispose() {
    }

}
