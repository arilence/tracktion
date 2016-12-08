package cm.smith.games.tracktion;

import com.badlogic.gdx.Gdx;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import cm.smith.games.tracktion.controllers.GameController;

/**
 * Created by anthony on 2016-12-03.
 */

public class Packet {

    public static final int proBufferLength = 60;
    private final int proStateLength = 1;
    private final int proRoleLength = 1;
    private final int proRemainingTimeLength = 4;
    private final int proIsAccelLength = 1;
    private final int proIsTurningLeftLength = 1;
    private final int proIsTurningRightLength = 1;
    private final int proVehiclePosX = 4;
    private final int proVehiclePosY = 4;
    private final int proVehicleVelX = 4;
    private final int proVehicleVelY = 4;
    private final int proVehicleHeading = 4;

    public GameController.STATE state;
    public GameController.ROLE role;
    public float time;
    public boolean isAccelerating;
    public boolean isTurningLeft;
    public boolean isTurningRight;
    public float vehiclePosX;
    public float vehiclePosY;
    public float vehicleVelX;
    public float vehicleVelY;
    public float vehicleHeading;
    public boolean voteRetry = false;

    public boolean isNewTrack = false;
    public float trackPosX;
    public float trackPosY;

    public static byte[] float2ByteArray(float value) {
        return ByteBuffer.allocate(4).putFloat(value).array();
    }

    public static byte [] long2ByteArray (long value)
    {
        return ByteBuffer.allocate(8).putLong(value).array();
    }

    public static float byte2Float(byte[] value) {
        return ByteBuffer.wrap(value).order(ByteOrder.BIG_ENDIAN).getFloat();
    }

    public static long byte2Long(byte[] value) {
        return ByteBuffer.wrap(value).order(ByteOrder.BIG_ENDIAN).getLong();
    }

    public static byte[] insertByteArray(int startingIndex, byte[] container, byte[] values) {
        int innerCount = 0;
        for (int i = startingIndex; innerCount < values.length; i++) {
            container[i] = values[innerCount];
            innerCount++;
        }

        return container;
    }

    public static byte[] splitByteArray(byte[] array, int start, int finish) {
        byte[] newArray = new byte[finish - start + 1];
        int innerCount = 0;
        for (int i = start; i <= finish; i++) {
            newArray[innerCount] = array[i];
            innerCount++;
        }

        return newArray;
    }

    public static Packet parsePacket(byte[] data) {
        Packet newPacket = new Packet();
        newPacket.state = GameController.STATE.get(data[0]);

        long role = byte2Long(splitByteArray(data, 1, 8));
        newPacket.role = GameController.ROLE.get(role);

        float time = byte2Float(splitByteArray(data, 9, 12));
        newPacket.time = time;

        if (newPacket.role == GameController.ROLE.DRIVER) {
            byte accelerating = splitByteArray(data, 13, 13)[0];
            byte turningLeft = splitByteArray(data, 14, 14)[0];
            byte turningRight = splitByteArray(data, 15, 15)[0];
            newPacket.isAccelerating = (accelerating == 1);
            newPacket.isTurningLeft = (turningLeft == 1);
            newPacket.isTurningRight = (turningRight == 1);
            newPacket.vehiclePosX = byte2Float(splitByteArray(data, 16, 23));
            newPacket.vehiclePosY = byte2Float(splitByteArray(data, 24, 31));
            newPacket.vehicleVelX = byte2Float(splitByteArray(data, 32, 39));
            newPacket.vehicleVelY = byte2Float(splitByteArray(data, 40, 47));
            newPacket.vehicleHeading = byte2Float(splitByteArray(data, 48, 55));
            byte retry = splitByteArray(data, 56, 56)[0];
            newPacket.voteRetry = (retry == 1);
        }

        if (newPacket.role == GameController.ROLE.BUILDER) {
            byte newPiece = splitByteArray(data, 13, 13)[0];
            newPacket.isNewTrack = (newPiece == 1);
            newPacket.trackPosX = byte2Float(splitByteArray(data, 14, 21));
            newPacket.trackPosY = byte2Float(splitByteArray(data, 22, 29));
        }

        return newPacket;
    }

    public byte[] toBytes() {
        byte[] byteMsg = new byte[Packet.proBufferLength];

        byteMsg[0] = this.state.getValue();
        byteMsg = insertByteArray(1, byteMsg, long2ByteArray(this.role.getValue()));
        byteMsg = insertByteArray(9, byteMsg, float2ByteArray(this.time));

        if (role == GameController.ROLE.DRIVER) {
            byteMsg[13] = (byte)((isAccelerating) ? 1 : 0);
            byteMsg[14] = (byte)((isTurningLeft) ? 1 : 0);
            byteMsg[15] = (byte)((isTurningRight) ? 1 : 0);
            byteMsg = insertByteArray(16, byteMsg, float2ByteArray(this.vehiclePosX));
            byteMsg = insertByteArray(24, byteMsg, float2ByteArray(this.vehiclePosY));
            byteMsg = insertByteArray(32, byteMsg, float2ByteArray(this.vehicleVelX));
            byteMsg = insertByteArray(40, byteMsg, float2ByteArray(this.vehicleVelY));
            byteMsg = insertByteArray(48, byteMsg, float2ByteArray(this.vehicleHeading));
            byteMsg[56] = (byte)((voteRetry) ? 1 : 0);
        }

        if (role == GameController.ROLE.BUILDER) {
            byteMsg[13] = (byte)((isNewTrack) ? 1 : 0);
            byteMsg = insertByteArray(14, byteMsg, float2ByteArray(this.trackPosX));
            byteMsg = insertByteArray(22, byteMsg, float2ByteArray(this.trackPosY));
        }

        return byteMsg;
    }

}
