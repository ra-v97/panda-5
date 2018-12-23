package pl.edu.agh.panda5;

import com.badlogic.gdx.Game;
import pl.edu.agh.panda5.screens.GameScreen;

public class Panda5 extends Game {

	
	@Override
	public void create () {
		setScreen(new GameScreen());
	}

	
	@Override
	public void dispose () {
	}

	public String getGreeting() {
		return "Hello world.";
	}
}
