package src;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class LobbyView extends JFrame implements Runnable {
    private int refreshmilis = 500;
    Viewer viewer;


    public LobbyView() {
        viewer = new Viewer();

        setTitle("Lobby");
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        //Método para que el proyecto cubra toda la pantalla
        //setUndecorated(true);
        addComponentes();
        setLocationRelativeTo(null);
        setVisible(true);


    }
    public void updateParam(int position){
        for (int i = 0; i < viewer.labellist.size(); i++) {
            if (i==position){
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
        JLabel background = new JLabel(new ImageIcon("src/gameAssets/2space.jpg"));
        background.setLayout(new BorderLayout());
//Logo
        JPanel logopanel = new JPanel();
        logopanel.setOpaque(false);
        JLabel logo = new JLabel("");
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


    public void refresh() {
        //Función de prueba para testear interfaz
        String[] state = new String[]{"Waiting for players", "Waiting for player configuration"};
        Random random = new Random();
        viewer.getLifesnum().setText(String.valueOf(random.nextInt(0, 100)));
        viewer.getStateLabel().setText(state[random.nextInt(0, state.length)]);
//Valor dado por el controlador
        updateParam(3);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(refreshmilis);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            refresh();

        }
    }

    //Getters setters
    public Viewer getViewer() {
        return viewer;
    }

    public void setViewer(Viewer viewer) {
        this.viewer = viewer;
    }

}
