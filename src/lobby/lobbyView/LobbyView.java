package lobby.lobbyView;

import lobby.lobbyController.LobbyController;
import lobby.lobbyModel.GameRules;
import lobby.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LobbyView extends JFrame implements ActionListener {

    private Viewer viewer;
    private LobbyController lobbyController;
    private Image image;
    private Timer timer;
    private int y;
    private BufferedImage backgroundImage;
    private Image[] backgroundImages;

    /**
     * Frame for the Lobby
     * 
     * @author Antoni Xavier Bascunana Sanchez
     * 
     */
    public LobbyView(LobbyController lobbyController) {
        backgroundImages = new Image[4];
        try {
            backgroundImages[0] = ImageIO.read(new File("src/visual/img/backgrounds/theme1/background5.png"));
            backgroundImages[1] = ImageIO.read(new File("src/visual/img/backgrounds/theme2/background5.png"));
            backgroundImages[2] = ImageIO.read(new File("src/visual/img/backgrounds/theme3/background5.png"));
            backgroundImages[3] = ImageIO.read(new File("src/visual/img/backgrounds/theme4/background5.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        timer = new Timer(50, this);
        this.lobbyController = lobbyController;
        viewer = new Viewer();
        setTitle("Lobby");
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        addComponents();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (image != null) {
            Graphics2D g2d = (Graphics2D) g;
            int halfScreen = (getWidth() - image.getWidth(rootPane)) / 2;
            g2d.drawImage(image, halfScreen, y, rootPane);
        }
    }

    public void update(Graphics g) {
        Graphics offScreen;
        Image offImage = null;
        Dimension d = getSize();

        offImage = createImage(d.width, d.height);
        offScreen = offImage.getGraphics();
        offScreen.setColor(getBackground());
        offScreen.fillRect(0, 0, d.width, d.height);
        offScreen.setColor(getForeground());
        paint(offScreen);
        g.drawImage(offImage, 0, 0, rootPane);
    }

    /**
     * @param position
     *                 Method to display selected position by user
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

    /*
     * Add Viewer components
     * 
     */
    public void addComponents() {

        this.backgroundImage = (BufferedImage) backgroundImages[0];

        JPanel background = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(),
                        this);
            }
        };

        background.setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(viewer.lifes);
        centerPanel.add(viewer.bulletDamage);
        centerPanel.add(viewer.map);
        centerPanel.add(viewer.readypanel);
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(Box.createVerticalStrut(300));

        background.add(viewer.numplayers, BorderLayout.WEST);
        background.add(viewer.gameState, BorderLayout.EAST);
        background.add(centerPanel, BorderLayout.SOUTH);

        this.setContentPane(background);
    }

    public void chageBaground(Image imagen) {
        this.backgroundImage = (BufferedImage) imagen;
        repaint();
    }

    /**
     * @param players
     *                refresh number of players in game
     */
    public void refreshPlayerNum(int players) {
        viewer.getPlayersnum().setText(String.valueOf(players));
    }

    public void refreshMasterValues(GameRules gameRules) {
        viewer.getLifesnum().setText(String.valueOf(gameRules.getDinamicGameRule().getLife()));
        viewer.getBulletDamagenum().setText(String.valueOf(gameRules.getDinamicGameRule().getBulletDamage()));
        viewer.getMapnum().setText(String.valueOf(gameRules.getDinamicGameRule().getMap()));

        int indexMap = Maps.valueOf(gameRules.getDinamicGameRule().getMap().toString()).ordinal();
        chageBaground(backgroundImages[indexMap]);

    }

    private void KillDemon() {
        image = null;
        timer.stop();
        update(getGraphics());
    }

    public void invoqueDemon() {
        y = 1000;
        image = new ImageIcon("src/lobby/gameAssets/demon.png").getImage();
        timer.start();
    }

    public Viewer getViewer() {
        return viewer;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setViewer(Viewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        y = y - 50;
        update(getGraphics());
        if (y == -200) {
            KillDemon();
        }
    }

}
