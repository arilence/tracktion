package cm.smith.games.tracktion;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesActivityResultCodes;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import com.google.example.games.basegameutils.GameHelper;

import java.util.List;

public class AndroidLauncher extends AndroidApplication {

    public final static int RC_WAITING_ROOM = 10002;
    public final static int REQUEST_ACHIEVEMENTS = 9003;

    GoogleAccountAdapter accountAdapter;
    MultiplayerAdapter multiplayerAdapter;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        GameHelper gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
        gameHelper.enableDebugLog(false);

        GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener()
        {
            @Override
            public void onSignInFailed(){}

            @Override
            public void onSignInSucceeded(){}
        };

        gameHelper.setup(gameHelperListener);

        accountAdapter = new GoogleAccountAdapter(this, gameHelper);
        multiplayerAdapter = new MultiplayerAdapter(this, gameHelper);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useImmersiveMode = true;
		initialize(new MainGame(accountAdapter, multiplayerAdapter), config);
	}

    @Override
    protected void onStart()
    {
        super.onStart();
        multiplayerAdapter.gameHelper.onStart(this);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        multiplayerAdapter.gameHelper.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data)
    {
        switch (requestCode) {
            case RC_WAITING_ROOM:
                // we got the result from the "waiting room" UI.
                if (responseCode == RESULT_OK) {
                    // ready to start playing
                    Gdx.app.log("Multiplayer", "Starting game (waiting room returned OK).");
                    // TODO: start game here
                    multiplayerAdapter.gameController.isGameRunning = true;
                } else if (responseCode == GamesActivityResultCodes.RESULT_LEFT_ROOM) {
                    // player indicated that they want to leave the room
                    Gdx.app.log("Multiplayer", "Left Room");
                    // TODO: stop game here
                } else if (responseCode == RESULT_CANCELED) {
                    // Dialog was cancelled (user pressed back key, for instance). In our game,
                    // this means leaving the room too. In more elaborate games, this could mean
                    // something else (like minimizing the waiting room UI).
                    Gdx.app.log("Multiplayer", "Canceled Room");
                    // TODO: stop game here
                }
                break;
        }

        super.onActivityResult(requestCode, responseCode, data);
        multiplayerAdapter.gameHelper.onActivityResult(requestCode, responseCode, data);
    }
}
