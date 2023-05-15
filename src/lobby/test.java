package lobby;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import lobby.lobbyController.LobbyController;
import lobby.lobbyModel.LobbyModel;
import lobby.lobbyView.LobbyView;

public class test {
    public static void main(String[] args) {
        LobbyController lobbyController = new LobbyController(MasterStatus.LobbyMaster);
        LobbyView lobbyView = new LobbyView(lobbyController);
        lobbyController.setLobbyView(lobbyView);
        LobbyModel lobbyModel = new LobbyModel(lobbyController);
        lobbyController.setLobbyModel(lobbyModel);

        lobbyView.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_UP) {
                    lobbyModel.beforeGameRulePosition();
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    lobbyModel.nextGameRulePosition();
                } else if (keyCode == KeyEvent.VK_LEFT) {
                    lobbyModel.minusGameRuleValue();
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    lobbyModel.plusGameRuleValue();
                }
            }
        });
    }
}
