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

public class AndroidLauncher extends AndroidApplication implements PlayServices, RoomUpdateListener,
        RealTimeMessageReceivedListener, RoomStatusUpdateListener {

	private GameHelper gameHelper;
    private cm.smith.games.tracktion.controllers.GameController gameController;
    final static int RC_WAITING_ROOM = 10002;
    private final static int REQUEST_ACHIEVEMENTS = 9003;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
        gameHelper.enableDebugLog(false);

        GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener()
        {
            @Override
            public void onSignInFailed(){ }

            @Override
            public void onSignInSucceeded(){ }
        };

        gameHelper.setup(gameHelperListener);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useImmersiveMode = true;
		initialize(new MainGame(this), config);
	}

    @Override
    protected void onStart()
    {
        super.onStart();
        gameHelper.onStart(this);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        gameHelper.onStop();
    }

    @Override
    public void setGameManager(cm.smith.games.tracktion.controllers.GameController game) {
        this.gameController = game;
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
        gameHelper.onActivityResult(requestCode, responseCode, data);
    }

    @Override
    public void signIn()
    {
        try
        {
            runOnUiThread(new Runnable()
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
            runOnUiThread(new Runnable()
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
    public void findGame(long role) {
        if(gameHelper.getApiClient().isConnected()) {
            // quick-start a game with 1 randomly selected opponent
            final int MIN_OPPONENTS = 1, MAX_OPPONENTS = 1;
            Bundle autoMatchCriteria = RoomConfig.createAutoMatchCriteria(MIN_OPPONENTS,
                    MAX_OPPONENTS, role);
            RoomConfig.Builder rtmConfigBuilder = RoomConfig.builder(this);
            rtmConfigBuilder.setMessageReceivedListener(this);
            rtmConfigBuilder.setRoomStatusUpdateListener(this);
            rtmConfigBuilder.setAutoMatchCriteria(autoMatchCriteria);

            // create room:
            Games.RealTimeMultiplayer.create(gameHelper.getApiClient(), rtmConfigBuilder.build());
        }
    }

    @Override
    public void showAchievements() {
        if (isSignedIn())
            startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), REQUEST_ACHIEVEMENTS);
    }

    @Override
    public void onRoomCreated(int statusCode, Room room) {
        if (statusCode != GamesStatusCodes.STATUS_OK) {
            // TODO: show error message, return to main screen.
        }

        Gdx.app.log("Multiplayer", "onRoomCreated");

        // get waiting room intent
        Intent i = Games.RealTimeMultiplayer.getWaitingRoomIntent(gameHelper.getApiClient(), room, Integer.MAX_VALUE);
        startActivityForResult(i, RC_WAITING_ROOM);
    }

    @Override
    public void onJoinedRoom(int statusCode, Room room) {
        if (statusCode != GamesStatusCodes.STATUS_OK) {
            // TODO: show error message, return to main screen.
        }

        Gdx.app.log("Multiplayer", "onJoinedRoom");

        // get waiting room intent
        Intent i = Games.RealTimeMultiplayer.getWaitingRoomIntent(gameHelper.getApiClient(), room, Integer.MAX_VALUE);
        startActivityForResult(i, RC_WAITING_ROOM);
    }

    @Override
    public void onLeftRoom(int statusCode, String s) {
        Gdx.app.log("Multiplayer", "onLeftroom");
    }

    @Override
    public void onRoomConnected(int statusCode, Room room) {
        if (statusCode != GamesStatusCodes.STATUS_OK) {
            // TODO: show error message, return to main screen.
        }
    }

    @Override
    public void onRealTimeMessageReceived(RealTimeMessage realTimeMessage) {
        Gdx.app.log("Multiplayer", "onRealTimeMessageReceived");
    }

    @Override
    public void onRoomConnecting(Room room) {
        Gdx.app.log("Multiplayer", "onRoomConnecting");
    }

    @Override
    public void onRoomAutoMatching(Room room) {
        Gdx.app.log("Multiplayer", "onRoomAutoMatching");
    }

    @Override
    public void onPeerInvitedToRoom(Room room, List<String> list) {
        Gdx.app.log("Multiplayer", "onPeerInvitedToRoom");
    }

    @Override
    public void onPeerDeclined(Room room, List<String> list) {
        Gdx.app.log("Multiplayer", "onPeerDeclined");
    }

    @Override
    public void onPeerJoined(Room room, List<String> list) {
        Gdx.app.log("Multiplayer", "onPeerJoined");
    }

    @Override
    public void onPeerLeft(Room room, List<String> list) {
        Gdx.app.log("Multiplayer", "onPeerLeft");
    }

    @Override
    public void onConnectedToRoom(Room room) {
        Gdx.app.log("Multiplayer", "onConnectedToRoom");
    }

    @Override
    public void onDisconnectedFromRoom(Room room) {
        Gdx.app.log("Multiplayer", "onDisconnectedFromRoom");

        // TODO: player left, stop game
    }

    @Override
    public void onPeersConnected(Room room, List<String> list) {
        Gdx.app.log("Multiplayer", "onPeersConnected");
    }

    @Override
    public void onPeersDisconnected(Room room, List<String> list) {
        Gdx.app.log("Multiplayer", "onPeersDiconnected");

        // TODO: player left, stop game
    }

    @Override
    public void onP2PConnected(String s) {
        Gdx.app.log("Multiplayer", "onP2PConnected");
    }

    @Override
    public void onP2PDisconnected(String s) {
        Gdx.app.log("Multiplayer", "onP2PDisconnected");

        // TODO: player left, stop game
    }
}
