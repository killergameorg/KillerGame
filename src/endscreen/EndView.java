package endscreen ;
import endscreen.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndView extends JFrame  {
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
        setLocationRelativeTo(null);
        setVisible(true);
    }

   


    public void addComponents() {
        JLabel background = new JLabel(new ImageIcon("src/lobby/gameAssets/2space.jpg"));
        background.setLayout(new BorderLayout());
        JPanel southJLabel = new JPanel(new BorderLayout());
        
        southJLabel.add(viewer.team1,BorderLayout.CENTER);

       
        background.add(viewer.gameOver, BorderLayout.CENTER);
        background.add(viewer.team1, BorderLayout.WEST);
        background.add(viewer.team2, BorderLayout.EAST);
        background.add(southJLabel, BorderLayout.SOUTH);
        
        this.setContentPane(background);
    }

  
}
