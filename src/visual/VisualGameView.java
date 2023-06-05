package visual;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import javax.swing.JFrame;

public class VisualGameView extends JFrame implements Runnable {

    // ! Attributes
    private Integer refreshMilis;
    private VisualGameController visualGameController;
    private Viewer viewer;
    private boolean isRunning;
    private Graphics gg;
    private BufferStrategy bs;
    private BackgroundManager bckgManager;

    // ! Constructor
    public VisualGameView(VisualGameController visualGameController) {
        this.visualGameController = visualGameController;
        this.isRunning = false;
        this.bckgManager = new BackgroundManager(this);
        this.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
    
        this.viewer = new Viewer(bckgManager.getBackground());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0; 
        gbc.fill = GridBagConstraints.BOTH;
        this.getContentPane().add(viewer, gbc);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
    }
    // ! Methods

    public void refresh(Graphics g) {
        if (visualGameController.getVisualGameModel().getVisualObjectsList().size() > 0) {
            for (VisualObject visualObject : visualGameController.getVisualGameModel().getVisualObjectsList()) {
                visualObject.drawObject(g);
            }
        } else {
            return;
        }
    }
    public int getScreenWidth(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return (int) screenSize.getWidth();
    }
    public int getScreenHeight(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return (int) screenSize.getHeight();
    }

    // Getters and Setters


    public VisualGameController getVisualGameController() {
        return visualGameController;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public Graphics getG() {
        return gg;
    }

    public void setG(Graphics gg) {
        this.gg = gg;
    }

    public BufferStrategy getBs() {
        return bs;
    }

    public void setBs(BufferStrategy bs) {
        this.bs = bs;
    }

    public void run() {

        viewer.createBufferStrategy(2);

        while (true) {
            // The main loop of the program. It is called every `refreshMilis` milliseconds. It draws
            // the objects on the screen using a double-buffer technique
            BufferStrategy bs = viewer.getBufferStrategy();
            Graphics gg = bs.getDrawGraphics();

            if (bs == null) {
                return;
            } else {
                viewer.drawBackground(gg);
                refresh(gg);
                bs.show();
                gg.dispose();
                try {
                    Thread.sleep(refreshMilis);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
    
}
