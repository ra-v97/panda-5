package pl.edu.agh.panda5;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import pl.edu.agh.panda5.screens.GameScreen;

public class Panda5 extends Game {

	private Screen gameScreen;

	@Override
	public void create() {
		gameScreen = new GameScreen(this);
		setScreen(gameScreen);
	}

	public void pause(){
		if(this.getScreen() == gameScreen)
			gameScreen.pause();
	}

	public void resume(){
		if(this.getScreen() == gameScreen)
			gameScreen.resume();
	}
	
	@Override
	public void dispose () {
		gameScreen.dispose();
	}

	public String getGreeting() {
		return "Hello world.";
	}
}
