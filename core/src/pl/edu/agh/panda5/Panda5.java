package pl.edu.agh.panda5;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import pl.edu.agh.panda5.screens.GameScreen;
import pl.edu.agh.panda5.utils.Constants;
import pl.edu.agh.panda5.utils.ScoreSerializer;


public class Panda5 extends Game {
	private Screen gameScreen;
	private boolean isPaused = false;
    private final ScoreSerializer serializer;

    public Panda5(){
            serializer = new ScoreSerializer(Constants.SCORE_DATABASE_PATH);
    }

	@Override
	public void create() {
		gameScreen = new GameScreen(this);
		setScreen(gameScreen);
	}

	public void pauseOrResume(){
		if(this.getScreen() == gameScreen) {
			if(isPaused) {
				gameScreen.resume();
				isPaused = false;
			} else {
				gameScreen.pause();
				isPaused = true;
			}
		}
	}

	public ScoreSerializer getSerializer(){
        return serializer;
    }

	@Override
	public void dispose () {
		gameScreen.dispose();
	}

}
