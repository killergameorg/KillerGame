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
    private int powerUpUpgrade;
    private int winScore;

    public int getColisionDamage() {
        return colisionDamage;
    }

    public int getBulletDamage() {
        return bulletDamage;
    }

    public int getPowerUpUpgrade() {
        return powerUpUpgrade;
    }

    public int getWinScore() {
        return winScore;
    }

    public GameRules(int colisionDamage,int bulletDamage, int powerUpUpgrade,int winScore) {
        this.colisionDamage = colisionDamage;
        this.bulletDamage = bulletDamage;
        this.powerUpUpgrade = powerUpUpgrade;
        this.winScore=winScore;
    }
}
