package pl.edu.agh.panda5.environment;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import pl.edu.agh.panda5.utils.Constants;
import pl.edu.agh.panda5.utils.GameTextures;

public class GameBackground {

    //We use two backgounds for smooth image looping
    private Sprite[] bg;
    private int currentlyUsedBg;
    private float switchBgsTimeout;

    public GameBackground() {
        bg = new Sprite[2];
        bg[0] = new Sprite(new TextureRegion(GameTextures.BACKGROUND));
        bg[1] = new Sprite(new TextureRegion(GameTextures.BACKGROUND));
        currentlyUsedBg = 0;
        switchBgsTimeout = 0.0f;

        bg[0].setBounds((int)Constants.BACKGROUND_X, (int)Constants.BACKGROUND_Y,
                (int)Constants.BACKGROUND_WIDTH, (int)Constants.BACKGROUND_HEIGHT);

        bg[1].setBounds((int)(Constants.BACKGROUND_X + Constants.BACKGROUND_WIDTH), (int)Constants.BACKGROUND_Y,
                (int)Constants.BACKGROUND_WIDTH, (int)Constants.BACKGROUND_HEIGHT);

    }

    public void draw(Batch batch) {
        bg[0].draw(batch);
        bg[1].draw(batch);
    }

    public void update(float dt) {
        float x = bg[0].getX();
        x += dt * Constants.BACKGROUND_VELOCITY;
        bg[0].setX(x);
        x = bg[1].getX();
        x += dt * Constants.BACKGROUND_VELOCITY;
        bg[1].setX(x);
        switchBgsTimeout += dt;
        if(switchBgsTimeout > Constants.BACKGROUND_SWITCH_TIME) {
            currentlyUsedBg = (currentlyUsedBg + 1) % 2;
            bg[(currentlyUsedBg + 1) % 2].setPosition((int)(bg[currentlyUsedBg].getX() + Constants.BACKGROUND_WIDTH),
                    bg[currentlyUsedBg].getY());
            switchBgsTimeout -= Constants.BACKGROUND_SWITCH_TIME;
        }

    }
}
