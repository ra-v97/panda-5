package pl.edu.agh.panda5;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import pl.edu.agh.panda5.screens.GameScreen;
import pl.edu.agh.panda5.screens.MenuScreen;
import pl.edu.agh.panda5.utils.Constants;
import pl.edu.agh.panda5.utils.ScoreSerializer;


public class Panda5 extends Game {
	private Screen gameScreen;
	private Screen menuScreen;

	private boolean isPaused = false;
    private final ScoreSerializer serializer;

    public Panda5(){
    	serializer = new ScoreSerializer(Constants.SCORE_DATABASE_PATH);
    }

    public void startRun(){
		gameScreen = new GameScreen(this);
		setScreen(gameScreen);
	}

	@Override
	public void create() {

		menuScreen = new MenuScreen(this);
		setScreen(menuScreen);
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

}
