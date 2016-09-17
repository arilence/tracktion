package cm.smith.games.tracktion.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import cm.smith.games.tracktion.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width  = MainGame.GAME_WIDTH;
		config.height = MainGame.GAME_HEIGHT;
		new LwjglApplication(new MainGame(), config);
	}
}
