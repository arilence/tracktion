package cm.smith.games.tracktion.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import cm.smith.games.tracktion.MainGame;
import cm.smith.games.tracktion.MultiplayerServices;
import cm.smith.games.tracktion.PlayServices;
import cm.smith.games.tracktion.controllers.GameController;

public class DesktopLauncher implements PlayServices, MultiplayerServices {
	public static void main (String[] arg) {
		new DesktopLauncher();
	}

    public DesktopLauncher() {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title  = MainGame.GAME_TITLE;
        config.width  = MainGame.VIEW_WIDTH;
        config.height = MainGame.VIEW_HEIGHT;
        new LwjglApplication(new MainGame(this, this), config);
    }

    @Override
    public void signIn() {
        Gdx.app.log("DesktopLauncher", "Google Play Services not available on Desktop.");
    }

    @Override
    public void signOut() {
        Gdx.app.log("DesktopLauncher", "Google Play Services not available on Desktop.");
    }

    @Override
    public boolean isSignedIn() {
        return false;
    }

    @Override
    public void setGameManager(GameController game) {

    }

    @Override
    public void findGame(long role) {

    }

    @Override
    public void connectOnline() {

    }

    @Override
    public void showAchievements() {

    }
}
