package visual;

import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicBoolean;

public class Animation {

    public Boolean isRunning;
    public double durationSleep;
    public BufferedImage[] framesList;

    // * Constructor
    public Animation(Boolean isRunning, double durationSleep, BufferedImage[] framesList) {
        this.isRunning = isRunning;
        this.durationSleep = durationSleep;
        this.framesList = framesList;
    }

    public Boolean isIsRunning() {
        return this.isRunning;
    }

    public Boolean getIsRunning() {
        return this.isRunning;
    }

    public void setIsRunning(Boolean isRunning) {
        this.isRunning = isRunning;
    }

    public double getDurationSleep() {
        return this.durationSleep;
    }

    public void setDurationSleep(double durationSleep) {
        this.durationSleep = durationSleep;
    }

    public BufferedImage[] getFramesList() {
        return this.framesList;
    }

    public void setFramesList(BufferedImage[] framesList) {
        this.framesList = framesList;
    }

    public void playAnimation(VisualObject visualObject, Graphics g) {
        Thread animationThread = new Thread(() -> {

            final AtomicBoolean running = new AtomicBoolean(isRunning); // Variable final para capturar el valor de
            while (running.get()) { // Utilizar la variable capturada
                for (int i = 0; i < framesList.length; i++) {
                    BufferedImage frameToDraw = framesList[i];

                    // Dibujar la imagen en el objeto visual utilizando el objeto Graphics
                    g.drawImage(frameToDraw, (int) visualObject.getPosition().getxPos(),
                            (int) visualObject.getPosition().getyPos(), null);

                    // Actualizar la representación visual del objeto
                    // g.repaint();

                    try {
                        Thread.sleep((long) durationSleep); // Pausa durante la duración específica
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                running.set(false);
            }
        });
        animationThread.start();
    }

}
