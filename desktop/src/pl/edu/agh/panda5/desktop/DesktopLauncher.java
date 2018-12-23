package pl.edu.agh.panda5.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import pl.edu.agh.panda5.Panda5;
import pl.edu.agh.panda5.utils.Constants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(Constants.APP_WIDTH, Constants.APP_HEIGHT);
		new Lwjgl3Application(new Panda5(), config);
	}
}