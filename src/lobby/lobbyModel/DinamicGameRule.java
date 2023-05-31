package lobby.lobbyModel;

import lobby.Maps;

public class DinamicGameRule {
    // Atributos
    // Data that can be modified
    private int life;
    private int bulletDamage;
    private Maps map;

    // Constructor
    public DinamicGameRule(int life, int bulletDamage, Maps map) {
        this.life = life;
        this.bulletDamage = bulletDamage;
        this.map = map;
    }

    // Getter y Setters
    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getBulletDamage() {
        return bulletDamage;
    }

    public void setBulletDamage(int bulletDamage) {
        this.bulletDamage = bulletDamage;
    }

    public Maps getMap() {
        return map;
    }

    public void setMap(Maps map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "DinamicGameRule [life=" + life + ", bulletDamage=" + bulletDamage + ", map=" + map + "]";
    }

    

}
