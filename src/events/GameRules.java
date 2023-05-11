package events;

public class GameRules {
    private int colisionDamage;
    private int bulletDamage;
    private int numPlayers;
    private int endTime;
    private int bulletSpeed;
    private double acc;
    private int speedIncrease;
    private boolean friendlyFire;

    public int getColisionDamage() {
        return colisionDamage;
    }

    public int getBulletDamage() {
        return bulletDamage;
    }

    public GameRules(int colisionDamage) {
        this.colisionDamage = colisionDamage;
    }
}
