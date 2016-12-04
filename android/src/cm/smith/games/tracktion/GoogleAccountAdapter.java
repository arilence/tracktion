package cm.smith.games.tracktion;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;

/**
 * Created by anthony on 2016-12-02.
 */

public class GoogleAccountAdapter implements PlayServices {

    public AndroidApplication application;
    public GameHelper gameHelper;

    public GoogleAccountAdapter(AndroidApplication application, GameHelper gameHelper) {
        this.application = application;
        this.gameHelper = gameHelper;
    }

    @Override
    public void signIn()
    {
        try
        {
            application.runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    gameHelper.beginUserInitiatedSignIn();
                }
            });
        }
        catch (Exception e)
        {
            Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
        }
    }

    @Override
    public void signOut()
    {
        try
        {
            application.runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    gameHelper.signOut();
                }
            });
        }
        catch (Exception e)
        {
            Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
        }
    }

    @Override
    public boolean isSignedIn()
    {
        return gameHelper.isSignedIn();
    }

    @Override
    public void connectOnline() {
        if (!gameHelper.getApiClient().isConnected()) {
            gameHelper.getApiClient().connect();
        }
    }

    @Override
    public void showAchievements() {
        if (isSignedIn())
            application.startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), AndroidLauncher.REQUEST_ACHIEVEMENTS);
    }
}
