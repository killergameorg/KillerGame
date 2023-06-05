package visual;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Animation {

    public Boolean isRunning;
    public float durationSleep;
    public BufferedImage[] framesList;

    // * Constructor
    public Animation(Boolean isRunning, float durationSleep, BufferedImage[] framesList) {
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

    public float getDurationSleep() {
        return this.durationSleep;
    }

    public void setDurationSleep(float durationSleep) {
        this.durationSleep = durationSleep;
    }

    public BufferedImage[] getFramesList() {
        return this.framesList;
    }

    public void setFramesList(BufferedImage[] framesList) {
        this.framesList = framesList;
    }

    /* public void playAnimation(VisualObject visualObject, BufferedImage[] frameList) {
        Thread animationThread = new Thread(() -> {
            while (isRunning) {
                for (int i = 0; i < frameList.length; i++) {
                    BufferedImage frameToDraw = frameList[i];
                    Graphics graphics = visualObject.getGraphics(); // Obtener el objeto Graphics del VisualObject

                    // Dibujar la imagen en el objeto visual utilizando el objeto Graphics
                    graphics.drawImage(frameToDraw, visualObject.getPosition().getxPos(),
                            visualObject.getPosition().getyPos(), null);

                    // Actualizar la representación visual del objeto
                    visualObject.repaint();

                    try {
                        Thread.sleep(durationSleep); // Pausa durante la duración específica
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    } */

}
