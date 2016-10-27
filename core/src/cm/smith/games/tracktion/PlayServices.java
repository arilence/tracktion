package cm.smith.games.tracktion;

/**
 * Created by anthony on 2016-09-16.
 */
public interface PlayServices
{
    void signIn();
    void signOut();
    boolean isSignedIn();

    void connectOnline();
    void setGameManager(GameController game);
    void findGame(long role);
}
