package lobby;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import lobby.endscreen.*;
import lobby.lobbyController.LobbyController;
import lobby.lobbyModel.LobbyModel;
import lobby.lobbyView.LobbyView;
import maincontroller.gameinfo.Team;
import maincontroller.gameinfo.TeamName;

public class test {
    public static void main(String[] args) {
        // Para que funcione necesita quitar applingtomaster en el constructor;
        LobbyController lobbyController = new LobbyController();
        LobbyView lobbyView = new LobbyView(lobbyController);
        lobbyController.setLobbyView(lobbyView);
        LobbyModel lobbyModel = new LobbyModel(lobbyController);
        lobbyController.setLobbyModel(lobbyModel);

        Team t1= new Team(TeamName.RED);
        t1.setScore(100);
        Team t2= new Team(TeamName.BLUE);
        t2.setScore(500);
        lobbyController.startEndView(t1,t2);

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

                } else if (keyCode == KeyEvent.VK_ENTER) {
                    if (Integer.parseInt(lobbyController.getLobbyView().getViewer().getLifesnum().getText()) <= Integer
                            .parseInt(lobbyController.getLobbyView().getViewer().getBulletDamagenum().getText())) {
                        lobbyView.invoqueDemon();
                    }
                }
            }
        });
        
    }
}
