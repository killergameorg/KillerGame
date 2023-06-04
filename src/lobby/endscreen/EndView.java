package lobby.endscreen;

import javax.swing.*;

import lobby.endscreen.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndView extends JFrame {
    Viewer viewer;

    /**
     * Frame for the Lobby
     * 
     * @author Antoni Xavier Bascunana Sanchez
     * 
     */
    public EndView() {
        viewer = new Viewer();
        setTitle("GAME OVER");
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        addComponents();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void addComponents() {
        JLabel background = new JLabel(new ImageIcon("src/lobby/gameAssets/2space.jpg"));
        background.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(viewer.team1, BorderLayout.WEST);
        topPanel.add(viewer.team2, BorderLayout.EAST);
        topPanel.setOpaque(false);

        background.add(topPanel, BorderLayout.NORTH);
        background.add(viewer.gameOver, BorderLayout.CENTER);

        this.setContentPane(background);
    }

    public Viewer getViewer() {
        return viewer;
    }

    public void setViewer(Viewer viewer) {
        this.viewer = viewer;
    }
    

}
