package edu.metrostate.stackoverflow.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import edu.metrostate.stackoverflow.game.SpaceDodge;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.foregroundFPS = 60;
		config.height = 720;
		config.width = 1280;
		new LwjglApplication(new SpaceDodge(), config);
	}
}
