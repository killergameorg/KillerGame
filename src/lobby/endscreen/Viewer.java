package lobby.endscreen;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**
 * All the components for the LobbyView
 * 
 * @author Antoni Xavier Bascunana Sanchez
 * 
 */
public class Viewer extends JPanel {
    JPanel gameOver;
    JPanel team2;
    JPanel team1;
    private JLabel gameLabel;
    private JLabel team1Label;
    private JLabel team2Label;

    JLabel team1Score;
    JLabel team2Score;
    private JButton restart;

    Viewer() {
        addGameScreen();
        addTeam1Score();
        addTeam1();
        addTeam2();

    }

    public void addTeam1() {
        team1 = new JPanel(new BorderLayout());
        team1.setSize(100, 100);
        team1Label = new JLabel("Team1");
        team1Label.setForeground(Color.white);
        team1Label.setFont(customFont());
        team1Label.setForeground(Color.white);
        team1.add(team1Label, BorderLayout.NORTH);
        team1.add(addTeam1Score(), BorderLayout.CENTER);
        team1.setOpaque(false);
    }

    public JLabel addTeam1Score() {
        team1Score = new JLabel("0");
        team1Score.setSize(100, 100);
        team1Score.setFont(customFont());
        team1Score.setForeground(Color.white);
        team1Score.setOpaque(false);
        return team1Score;

    }

    public JLabel addTeam2Score() {
        team2Score = new JLabel("0");
        team2Score.setSize(100, 100);
        team2Score.setFont(customFont());
        team2Score.setForeground(Color.white);
        team2Score.setOpaque(false);
        return team2Score;

    }

    public void addTeam2() {
        team2 = new JPanel(new BorderLayout());
        team2.setSize(100, 100);
        team2Label = new JLabel("Team1");
        team2Label.setForeground(Color.white);
        team2Label.setFont(customFont());
        team2Label.setForeground(Color.white);
        team2.add(team2Label, BorderLayout.NORTH);
        team2.add(addTeam1Score(), BorderLayout.EAST);
        team2.setOpaque(false);
    }

    public void addGameScreen() {
        gameOver = new JPanel();
        gameOver.setSize(100, 100);

        gameLabel = new JLabel("Game Over");
        gameLabel.setForeground(Color.white);
        gameLabel.setFont(customFont());
        gameLabel.setForeground(Color.red);
        gameOver.add(gameLabel);
        gameOver.setOpaque(false);
    }

    /**
     * @return Font
     *         Method to set custom text font for resources
     */
    public Font customFont() {
        Font font = null;
        try {
            // Font file set to test the interface.
            String fontPath = "lobby/gameAssets/poly.ttf";
            String absoluteFontPath = getClass().getClassLoader().getResource(fontPath).getPath();
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(absoluteFontPath));
            font = customFont.deriveFont(Font.BOLD, 100);
        } catch (Exception e) {
            // If the font file cannot be loaded, use a fallback font
            font = new Font("Arial", Font.BOLD, 100);

        }
        return font;
    }

}
