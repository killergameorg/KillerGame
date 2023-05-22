package lobby.lobbyView;

import lobby.LOBBYSECTION;
import lobby.lobbyController.LobbyController;
import lobby.lobbyModel.GameRules;

import javax.swing.*;
import java.awt.*;

/**
 * Frame for the Lobby
 * 
 * @author Antoni Xavier Bascunana Sanchez
 * 
 */
public class LobbyView extends JFrame {

    private Viewer viewer;
    private LobbyController lobbyController;

    public LobbyView(LobbyController lobbyController) {
        this.lobbyController = lobbyController;
        viewer = new Viewer();
        setTitle("Lobby");
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        // setUndecorated(true);
        addComponents();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    /**
     * @param position
     * Method to display selected position by user
     */
    public void updatePos(LOBBYSECTION position) {
        for (int i = 0; i < viewer.labellist.size(); i++) {
            if (i == position.ordinal()) {
                viewer.getLabellist().get(i).setText("->" + viewer.paramlist[i]);
            } else {
                viewer.getLabellist().get(i).setText(viewer.paramlist[i]);
            }
        }
    }

    /**
     * Add Viewer components
     */
    public void addComponents() {
        // Delete background and add the one sent from the visual dep
        JLabel background = new JLabel(new ImageIcon("src/lobby/gameAssets/2space.jpg"));
        background.setLayout(new BorderLayout());
        // Game Parameters
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        centerPanel.add(viewer.lifes);
        centerPanel.add(viewer.bulletDamage);
        centerPanel.add(viewer.map);
        centerPanel.add(viewer.readypanel);

        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(Box.createVerticalStrut(300));
        // number of players waiting in lobby

        background.add(viewer.numplayers, BorderLayout.WEST);
        background.add(viewer.gameState, BorderLayout.EAST);
        background.add(centerPanel, BorderLayout.SOUTH);
        this.setContentPane(background);
    }

    /**
     * @param players
     * refresh number of players in game
     */
    public void refreshPlayerNum(int players) {
        viewer.getPlayersnum().setText(String.valueOf(players));
    }

    /**
     * @param players
     * Refresh values set by master
     */
    public void refreshMasterValues(GameRules gameRules) {
        viewer.getLifesnum().setText(String.valueOf(gameRules.getLife()));
        viewer.getBulletDamagenum().setText(String.valueOf(gameRules.getBulletDamage()));
        viewer.getMapnum().setText(String.valueOf(gameRules.getMap()));
    }

    // Getters setters
    public Viewer getViewer() {
        return viewer;
    }

    public void setViewer(Viewer viewer) {
        this.viewer = viewer;
    }

}
