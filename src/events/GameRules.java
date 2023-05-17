package events;

public class GameRules {

    private int life;
    private int bulletDamage;
    private int colisionDamage;
    private Maps map;

    // Constructor
    public GameRules(int life, int bulletDamage, int colisionDamage, Maps map) {
        this.life = life;
        this.bulletDamage = bulletDamage;
        this.map = map;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getColisionDamage() {
        return colisionDamage;
    }

    public void setColisionDamage(int colisionDamage) {
        this.colisionDamage = colisionDamage;
    }

    public int getBulletDamage() {
        return bulletDamage;
    }

    public void setBulletDamage(int bulletDamage) {
        this.bulletDamage = bulletDamage;
    }

}
