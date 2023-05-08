package src;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class LobbyView extends JFrame implements Runnable {
    private int refreshmilis;
    Viewer viewer;

    public LobbyView() {
        viewer = new Viewer();
        this.refreshmilis = 500;
        setTitle("Lobby");
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        // Método para que el proyecto cubra toda la pantalla
        setUndecorated(true);
        addComponentes();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public void addComponentes() {
        // Borrar fondo y añadir el que nos pasen desde visuales
        JLabel background = new JLabel(new ImageIcon("src/gameAssets/2space.jpg"));
        background.setLayout(new BorderLayout());
        // Logo
        JPanel logopanel = new JPanel();
        logopanel.setOpaque(false);

        // Parámetros de partida
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        centerPanel.add(viewer.lifes);
        centerPanel.add(viewer.bulletDamage);
        centerPanel.add(viewer.map);

        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(Box.createVerticalStrut(300));
        // Número de jugadores actuales e indicación de si se puede iniciar la partida

        background.add(viewer.numplayers, BorderLayout.WEST);
        background.add(viewer.gameState, BorderLayout.EAST);
        background.add(centerPanel, BorderLayout.SOUTH);
        this.setContentPane(background);
    }

    public void refresh() {
        // Función de prueba para testear interfaz
        String[] state = new String[] { "Waiting for players", "Waiting for master to start the game" };
        Random random = new Random();
        viewer.getLifesnum().setText(String.valueOf(random.nextInt(0, 100)));
        viewer.getStateLabel().setText(state[random.nextInt(0, state.length)]);
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

    // Getters setters
    public Viewer getViewer() {
        return viewer;
    }

    public void setViewer(Viewer viewer) {
        this.viewer = viewer;
    }

}
