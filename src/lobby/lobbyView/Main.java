package lobby.lobbyView;

public class Main {
    public static void main(String[] args) {
        new Thread(new LobbyView()).start();
    }
}