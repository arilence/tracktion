package cm.smith.games.tracktion;

/**
 * Created by anthony on 2016-12-02.
 */

public interface MultiplayerServices {

    void setGameManager(cm.smith.games.tracktion.controllers.GameController game);
    void findGame(long role);
    void broadcastMessage();

}
