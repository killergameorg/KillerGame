package lobby.lobbyView;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Viewer extends JPanel {

    JPanel numplayers;
    JPanel lifes;
    JPanel bulletDamage;
    JPanel map;
    JPanel gameState;
    // Valor puesto por el gamemaster
    JLabel playersnum;
    JLabel lifesnum;
    JLabel bulletDamagenum;
    JLabel stateLabel;
    JLabel mapnum;

    Viewer() {
        addNumPlayers();
        addGameState();
        addLifes();
        addBulletDamage();
        addMap();
    }

    private void addLifes() {
        lifes = new JPanel();
        lifes.setSize(50, 50);
        JLabel lifeLabel = new JLabel("Lifes:");
        lifeLabel.setForeground(Color.white);
        lifeLabel.setFont(customFont());
        lifesnum = new JLabel("0");
        lifesnum.setForeground(Color.white);
        lifesnum.setFont(customFont());
        lifes.add(lifeLabel);
        lifes.add(lifesnum);
        lifes.setOpaque(false);
    }

    private void addBulletDamage() {
        bulletDamage = new JPanel();
        bulletDamage.setSize(50, 50);
        JLabel bulletlabel = new JLabel("BulletDamage:");
        bulletlabel.setForeground(Color.white);
        bulletlabel.setFont(customFont());

        bulletDamagenum = new JLabel("0");
        bulletDamagenum.setForeground(Color.white);
        bulletDamagenum.setFont(customFont());
        bulletDamage.add(bulletlabel);
        bulletDamage.add(bulletDamagenum);
        bulletDamage.setOpaque(false);
    }

    private void addMap() {
        map = new JPanel();
        map.setSize(50, 50);
        JLabel lifeLabel = new JLabel("Map:");
        lifeLabel.setForeground(Color.white);
        lifeLabel.setFont(customFont());
        mapnum = new JLabel("0");
        mapnum.setForeground(Color.white);
        mapnum.setFont(customFont());
        map.add(lifeLabel);
        map.add(mapnum);
        map.setOpaque(false);
    }

    private void addGameState() {
        gameState = new JPanel();
        gameState.setSize(50, 50);
        stateLabel = new JLabel("Waiting for players");
        stateLabel.setForeground(Color.white);
        stateLabel.setFont(customFont());
        gameState.add(stateLabel);
        gameState.setOpaque(false);
    }

    private void addNumPlayers() {
        numplayers = new JPanel();
        numplayers.setSize(50, 50);
        JLabel numPlayersLabel = new JLabel("Number of players:");
        numPlayersLabel.setForeground(Color.white);
        numPlayersLabel.setFont(customFont());
        playersnum = new JLabel("0");
        playersnum.setForeground(Color.white);
        playersnum.setFont(customFont());
        numplayers.add(numPlayersLabel);
        numplayers.add(playersnum);
        numplayers.setOpaque(false);
    }

    private Font customFont() {
        Font font = null;
        try {
            // Archivo de fuente puesto para testear la interfaz.
            // Fuente:https://www.dafont.com/polybius1981.font
            String fontPath = "src/gameAssets/poly.ttf";
            String absoluteFontPath = getClass().getClassLoader().getResource(fontPath).getPath();
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(absoluteFontPath));
            font = customFont.deriveFont(Font.BOLD, 50);
        } catch (Exception e) {
            // If the font file cannot be loaded, use a fallback font
            font = new Font("Arial", Font.BOLD, 50);

        }
        return font;
    }

    // Getters setters
    public JLabel getPlayersnum() {
        return playersnum;
    }

    public void setPlayersnum(JLabel playersnum) {
        this.playersnum = playersnum;
    }

    public JLabel getLifesnum() {
        return lifesnum;
    }

    public void setLifesnum(JLabel lifesnum) {
        this.lifesnum = lifesnum;
    }

    public JLabel getBulletDamagenum() {
        return bulletDamagenum;
    }

    public void setBulletDamagenum(JLabel bulletDamagenum) {
        this.bulletDamagenum = bulletDamagenum;
    }

    public JLabel getStateLabel() {
        return stateLabel;
    }

    public void setStateLabel(JLabel stateLabel) {
        this.stateLabel = stateLabel;
    }

    public JLabel getMapnum() {
        return mapnum;
    }

    public void setMapnum(JLabel mapnum) {
        this.mapnum = mapnum;
    }
}
