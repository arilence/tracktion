package cm.smith.games.tracktion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import com.google.example.games.basegameutils.GameHelper;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import cm.smith.games.tracktion.controllers.GameController;
import cm.smith.games.tracktion.entities.Vehicle;

/**
 * Created by anthony on 2016-12-02.
 */

public class MultiplayerAdapter implements MultiplayerServices, RoomUpdateListener, RoomStatusUpdateListener,
        RealTimeMessageReceivedListener {

    /**
     * Protocol Configuration
     * Lengths are in number of bytes
     */
    private byte[]msgBuffer = new byte[Packet.proBufferLength];

    public AndroidApplication application;
    public GameHelper gameHelper;
    public cm.smith.games.tracktion.controllers.GameController gameController;

    private String roomId = null;
    private String myId = null;
    private ArrayList<Participant> participants = null;

    public MultiplayerAdapter(AndroidApplication application, GameHelper gameHelper) {
        this.application = application;
        this.gameHelper = gameHelper;
    }

    public void updateRoom(Room room) {
        if (room != null) {
            participants = room.getParticipants();
        }
    }

    @Override
    public void disconnect() {
        gameHelper.disconnect();
    }

    @Override
    public void setGameManager(cm.smith.games.tracktion.controllers.GameController game) {
        this.gameController = game;
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
    public void broadcastMessage() {
        Packet sendingPacket = new Packet();
        sendingPacket.time = gameController.time;
        sendingPacket.state = gameController.getState();
        sendingPacket.role = gameController.getRole();
        sendingPacket.isAccelerating = gameController.hud.isAccelerateDown;
        sendingPacket.isTurningLeft = gameController.hud.isLeftDown;
        sendingPacket.isTurningRight = gameController.hud.isRightDown;
        sendingPacket.vehiclePosX = gameController.vehicle.transformComponent.pos.x;
        sendingPacket.vehiclePosY = gameController.vehicle.transformComponent.pos.y;
        sendingPacket.vehicleHeading = gameController.vehicle.body.getAngle();

        if (gameController.getRole() == GameController.ROLE.DRIVER) {
            sendingPacket.voteRetry = gameController.restartDriver;
        } else if (gameController.getRole() == GameController.ROLE.BUILDER) {
            sendingPacket.voteRetry = gameController.restartBuilder;
        }

        msgBuffer = sendingPacket.toBytes();

        // Send to every other participant.
        for (Participant p : participants) {
            if (p.getParticipantId().equals(myId))
                continue;
            if (p.getStatus() != Participant.STATUS_JOINED)
                continue;

            switch (gameController.getState()) {
                case PRE_GAME:
                    Games.RealTimeMultiplayer.sendUnreliableMessage(gameHelper.getApiClient(), msgBuffer,
                            roomId, p.getParticipantId());
                    break;
                case PLAYING:
                    Games.RealTimeMultiplayer.sendUnreliableMessage(gameHelper.getApiClient(), msgBuffer,
                            roomId, p.getParticipantId());
                    break;
                case DEAD:
                    if (gameController.getRole() == GameController.ROLE.DRIVER && !gameController.sentCrashMsg) {
                        Games.RealTimeMultiplayer.sendReliableMessage(gameHelper.getApiClient(), null, msgBuffer,
                                roomId, p.getParticipantId());
                        gameController.sentCrashMsg = true;
                    }
                    break;
                case GAME_OVER:
                    break;
                case DISCONNECT:
                    if (gameController.getRole() == GameController.ROLE.DRIVER && !gameController.sentDisconnectMsg) {
                        Games.RealTimeMultiplayer.sendReliableMessage(gameHelper.getApiClient(), null, msgBuffer,
                                roomId, p.getParticipantId());
                        gameController.sentDisconnectMsg = true;
                        gameHelper.disconnect();
                    }
                    break;
            }
        }
    }

    @Override
    public void onRealTimeMessageReceived(RealTimeMessage realTimeMessage) {
//        Gdx.app.log("Multiplayer", "onRealTimeMessageReceived");

        byte[] buf = realTimeMessage.getMessageData();
        String sender = realTimeMessage.getSenderParticipantId();
        Packet incomingPacket = Packet.parsePacket(buf);
//        Gdx.app.log("Multiplayer", "Message received: " + (char) buf[0] + "/" + (int) buf[1]);
//        Gdx.app.log("Multiplayer", "Time: " + Float.toString(incomingPacket.time));

        float incomingTime = incomingPacket.time;
        if (incomingPacket.state == GameController.STATE.PRE_GAME) {
            if (incomingPacket.role == GameController.ROLE.DRIVER) {
                if (this.gameController.time > incomingTime) {
                    this.gameController.time = incomingTime;
                }
            }
        }
        else if (incomingPacket.state == GameController.STATE.PLAYING) {
            if (incomingPacket.role == GameController.ROLE.DRIVER) {
                if (this.gameController.time < incomingTime) {
                    this.gameController.time = incomingTime;
                }

                this.gameController.vehicle.body.setTransform(incomingPacket.vehiclePosX, incomingPacket.vehiclePosY, incomingPacket.vehicleHeading);
                this.gameController.vehicle.body.setLinearVelocity(incomingPacket.vehicleVelX, incomingPacket.vehicleVelY);
            }
        }
        else if (incomingPacket.state == GameController.STATE.DEAD) {
            if (incomingPacket.role == GameController.ROLE.DRIVER) {
                if (this.gameController.getState() == GameController.STATE.PLAYING) {
                    this.gameController.vehicle.setDead(true);
                    this.gameController.time = incomingTime;
                }
            }
        }
        else if (incomingPacket.state == GameController.STATE.GAME_OVER) {
            Gdx.app.log("Multiplayer", "GAMEOVER");
            if (incomingPacket.role == GameController.ROLE.DRIVER) {
                if (this.gameController.time > incomingTime) {
                    this.gameController.time = incomingTime;
                }
                this.gameController.restartDriver = incomingPacket.voteRetry;
            }
            if (incomingPacket.role == GameController.ROLE.BUILDER) {
                this.gameController.restartBuilder = incomingPacket.voteRetry;
            }
        }
        else if (incomingPacket.state == GameController.STATE.DISCONNECT) {
            if (incomingPacket.role == GameController.ROLE.DRIVER) {
                this.gameHelper.disconnect();
            }
        }
    }

    @Override
    public void onRoomCreated(int statusCode, Room room) {
        if (statusCode != GamesStatusCodes.STATUS_OK) {
            // TODO: show error message, return to main screen.
            gameController.peerFailed = true;
        }

        Gdx.app.log("Multiplayer", "onRoomCreated");
        roomId = room.getRoomId();

        // get waiting room intent
        Intent i = Games.RealTimeMultiplayer.getWaitingRoomIntent(gameHelper.getApiClient(), room, Integer.MAX_VALUE);
        application.startActivityForResult(i, AndroidLauncher.RC_WAITING_ROOM);
    }

    @Override
    public void onJoinedRoom(int statusCode, Room room) {
        if (statusCode != GamesStatusCodes.STATUS_OK) {
            // TODO: show error message, return to main screen.
            gameController.peerFailed = true;
        }

        Gdx.app.log("Multiplayer", "onJoinedRoom");
        updateRoom(room);

        // get waiting room intent
        Intent i = Games.RealTimeMultiplayer.getWaitingRoomIntent(gameHelper.getApiClient(), room, Integer.MAX_VALUE);
        application.startActivityForResult(i, AndroidLauncher.RC_WAITING_ROOM);
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
        updateRoom(room);
    }

    @Override
    public void onRoomConnecting(Room room) {
        Gdx.app.log("Multiplayer", "onRoomConnecting");
        updateRoom(room);
    }

    @Override
    public void onRoomAutoMatching(Room room) {
        Gdx.app.log("Multiplayer", "onRoomAutoMatching");
        updateRoom(room);
    }

    @Override
    public void onPeerInvitedToRoom(Room room, List<String> list) {
        Gdx.app.log("Multiplayer", "onPeerInvitedToRoom");
        updateRoom(room);
    }

    @Override
    public void onPeerDeclined(Room room, List<String> list) {
        Gdx.app.log("Multiplayer", "onPeerDeclined");
        updateRoom(room);
    }

    @Override
    public void onPeerJoined(Room room, List<String> list) {
        Gdx.app.log("Multiplayer", "onPeerJoined");
        updateRoom(room);
    }

    @Override
    public void onPeerLeft(Room room, List<String> list) {
        Gdx.app.log("Multiplayer", "onPeerLeft");
        updateRoom(room);
    }

    @Override
    public void onConnectedToRoom(Room room) {
        Gdx.app.log("Multiplayer", "onConnectedToRoom");

        participants = room.getParticipants();
        myId = room.getParticipantId(Games.Players.getCurrentPlayerId(gameHelper.getApiClient()));

        if (roomId == null) {
            roomId = room.getRoomId();
        }
    }

    @Override
    public void onDisconnectedFromRoom(Room room) {
        Gdx.app.log("Multiplayer", "onDisconnectedFromRoom");
        roomId = null;

        // TODO: player left, stop game
        gameController.peerDisconnected = true;
    }

    @Override
    public void onPeersConnected(Room room, List<String> list) {
        Gdx.app.log("Multiplayer", "onPeersConnected");
        updateRoom(room);
    }

    @Override
    public void onPeersDisconnected(Room room, List<String> list) {
        Gdx.app.log("Multiplayer", "onPeersDiconnected");
        updateRoom(room);

        // TODO: player left, stop game
        gameController.peerDisconnected = true;
    }

    @Override
    public void onP2PConnected(String s) {
        Gdx.app.log("Multiplayer", "onP2PConnected");
    }

    @Override
    public void onP2PDisconnected(String s) {
        Gdx.app.log("Multiplayer", "onP2PDisconnected");

        // TODO: player left, stop game
        gameController.peerDisconnected = true;
    }
}
