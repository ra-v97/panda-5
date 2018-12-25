package pl.edu.agh.panda5;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import pl.edu.agh.panda5.screens.GameScreen;

public class Panda5 extends Game {
	private Screen gameScreen;
	private boolean isPaused = false;

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

	@Override
	public void dispose () {
		gameScreen.dispose();
	}


	public String getGreeting() {
		return "Hello world.";
	}
}
