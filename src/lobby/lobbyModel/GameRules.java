package lobby.lobbyModel;

public class GameRules {

    // Atributos
    // Datos que se puede modificar
    private int life;
    private int bulletDamage;
    private int map;
   
    // Datos estaticos
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

    // Constructor
    public GameRules(int life, int bulletDamage, int map) {
        // Datos que se puede modificar
        this.life = life;
        this.bulletDamage = bulletDamage;
        this.map = map;
        // Datos estaticos
        this.defaultDamage=10;
        this.maxPlayer = 8;
        this.endTime = 300;
        this.numTeam = 2;
        this.bulletSpeed = 1; // Hay que preguntar
        this.shootSpeed = 200;
        this.friendlyFire = false;
        this.gameMode = "Team deathmatch";
        this.deltaAngle = 0.1;
        this.acc = 0.1;
        this.increaseSpeed = 0.1;
        this.maxSpeed = 7.0;
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

    public int getMap() {
        return map;
    }

    public void setMap(int map) {
        this.map = map;
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

    public int getDefaultDamage() {
        return defaultDamage;
    }

    public void setDefaultDamage(int defaultDamage) {
        this.defaultDamage = defaultDamage;
    }



    
}
