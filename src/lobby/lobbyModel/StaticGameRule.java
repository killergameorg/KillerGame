package lobby.lobbyModel;

public class StaticGameRule {
    // Static data
    // Data that can't be modified
    private int defaultDamage;
    private int maxPlayer;
    private int endTime;
    private int numTeam;
    private int bulletSpeed;
    private int shootSpeed;
    private boolean friendlyFire;
    private String gameMode;
    private double deltaAngle;
    private double acc;
    private double increaseSpeed;
    private double maxSpeed;

    // Contructor
    public StaticGameRule() {
        this.defaultDamage = 10;
        this.maxPlayer = 8;
        this.endTime = 300;
        this.numTeam = 2;
        this.bulletSpeed = 1;
        this.shootSpeed = 200;
        this.friendlyFire = false;
        this.gameMode = "Team deathmatch";
        this.deltaAngle = 0.1;
        this.acc = 0.1;
        this.increaseSpeed = 0.1;
        this.maxSpeed = 7.0;
    }

    // Getter y Setters
    public int getDefaultDamage() {
        return defaultDamage;
    }

    public void setDefaultDamage(int defaultDamage) {
        this.defaultDamage = defaultDamage;
    }

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public void setMaxPlayer(int maxPlayer) {
        this.maxPlayer = maxPlayer;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getNumTeam() {
        return numTeam;
    }

    public void setNumTeam(int numTeam) {
        this.numTeam = numTeam;
    }

    public int getBulletSpeed() {
        return bulletSpeed;
    }

    public void setBulletSpeed(int bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
    }

    public int getShootSpeed() {
        return shootSpeed;
    }

    public void setShootSpeed(int shootSpeed) {
        this.shootSpeed = shootSpeed;
    }

    public boolean isFriendlyFire() {
        return friendlyFire;
    }

    public void setFriendlyFire(boolean friendlyFire) {
        this.friendlyFire = friendlyFire;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public double getDeltaAngle() {
        return deltaAngle;
    }

    public void setDeltaAngle(double deltaAngle) {
        this.deltaAngle = deltaAngle;
    }

    public double getAcc() {
        return acc;
    }

    public void setAcc(double acc) {
        this.acc = acc;
    }

    public double getIncreaseSpeed() {
        return increaseSpeed;
    }

    public void setIncreaseSpeed(double increaseSpeed) {
        this.increaseSpeed = increaseSpeed;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

}
