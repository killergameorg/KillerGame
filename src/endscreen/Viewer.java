package endscreen;

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
        addGameScreen() ;
        addTeam1();
        addTeam2();
        addTeam1Score();
     
    }
   
    public void addTeam1(){
        team1 = new JPanel();
        team1.setSize(100, 100);
        team1Label = new JLabel("Team1");
        team1Label.setForeground(Color.white);
        team1Label.setFont(customFont());
        team1Label.setForeground(Color.white);
        team1.add(team1Label);
        team1.setOpaque(false);
    }
     
    public void addTeam1Score(){
       team1Score = new JLabel("0");
       team1Score.setSize(50, 50);
       team1Score.setForeground(Color.white);
    team1Score.setOpaque(false);
    }
    public void addTeam2(){
        team2 = new JPanel();
        team2.setSize(100, 100);
    
        team2Label = new JLabel("Team2");
        team2Label.setForeground(Color.white);
        team2Label.setFont(customFont());
        team2Label.setForeground(Color.white);
        team2.add(team2Label);
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
     * Method to set custom text font for resources
     */
    public Font customFont() {
        Font font = null;
        try {
            //Font file set to test the interface.
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
