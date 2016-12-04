package cm.smith.games.tracktion;

import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIApplication;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import cm.smith.games.tracktion.MainGame;
import cm.smith.games.tracktion.controllers.GameController;

public class IOSLauncher extends IOSApplication.Delegate implements PlayServices, MultiplayerServices {
    @Override
    protected IOSApplication createApplication() {
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        return new IOSApplication(new MainGame(this, this), config);
    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }

    @Override
    public void setGameManager(GameController game) {

    }

    @Override
    public void findGame(long role) {

    }

    @Override
    public void signIn() {

    }

    @Override
    public void signOut() {

    }

    @Override
    public boolean isSignedIn() {
        return false;
    }

    @Override
    public void connectOnline() {

    }

    @Override
    public void showAchievements() {

    }
}