package lobby.lobbyView;

import lobby.LOBBYSECTION;
import lobby.lobbyController.LobbyController;
import lobby.lobbyModel.GameRules;

import javax.swing.*;
import java.awt.*;

public class LobbyView extends JFrame implements Runnable {

    Viewer viewer;
    private LobbyController lobbyController;


    public LobbyView(LobbyController lobbyController) {
        this.lobbyController=lobbyController;
        viewer = new Viewer();
        setTitle("Lobby");
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        //Método para que el proyecto cubra toda la pantalla
        //setUndecorated(true);
        addComponentes();
        setLocationRelativeTo(null);
        setVisible(true);



    }
    public void updatePos(LOBBYSECTION position){
        for (int i = 0; i < viewer.labellist.size(); i++) {
            if (i==position.ordinal()){
                viewer.getLabellist().get(i).setText("->"+viewer.paramlist[i]);
            }
            else {
                viewer.getLabellist().get(i).setText(viewer.paramlist[i]);
            }
        }
    }
    /*  public void setPos(){
          position++;
          if (position > viewer.getLabellist().size()-1){
              position=0;
          }
      }*/
    public void addComponentes() {
        // Borrar fondo y añadir el que nos pasen desde visuales
        JLabel background = new JLabel(new ImageIcon("src//lobby//gameAssets//2space.jpg"));
        background.setLayout(new BorderLayout());
//Parámetros de partida
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));


        centerPanel.add(viewer.lifes);
        centerPanel.add(viewer.bulletDamage);
        centerPanel.add(viewer.map);
        centerPanel.add(viewer.readypanel);


        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(Box.createVerticalStrut(300));
//Número de jugadores actuales e indicación de si se puede iniciar la partida

        background.add(viewer.numplayers, BorderLayout.WEST);
        background.add(viewer.gameState, BorderLayout.EAST);
        background.add(centerPanel, BorderLayout.SOUTH);
        this.setContentPane(background);
    }

    public void refreshPlayerNum(int players){
        viewer.getPlayersnum().setText(String.valueOf(players));
    }
    public void refreshMasterValues(GameRules gameRules) {
        //Función de prueba para testear interfaz
        viewer.getLifesnum().setText(String.valueOf(gameRules.getLife()));
        viewer.getBulletDamagenum().setText(String.valueOf(gameRules.getBulletDamage()));
        viewer.getMapnum().setText(String.valueOf(gameRules.getMap()));

    }

    @Override
    public void run() {
    }

    //Getters setters
    public Viewer getViewer() {
        return viewer;
    }

    public void setViewer(Viewer viewer) {
        this.viewer = viewer;
    }

}
