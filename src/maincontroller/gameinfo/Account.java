package maincontroller.gameinfo;

public class Account {

    // ! Attributes
    private static int idAccountCounter = 0;

    private int idAccount;
    private Team team;
    private String ip;
    private Joystick joystick;

    // ! Constructor
    public Account(Team team, String ip) {

        Account.setIdAccountCounter(Account.getIdAccountCounter() + 1);

        this.setIdAccount(Account.getIdAccountCounter());
        this.setTeam(team);
        this.setIp(ip);
        this.setJoystick(new Joystick());
    }

    // ! Getters and Setters

    /**
     * @return the team
     */
    public Team getTeam() {
        return team;
    }

    /**
     * @return the idAccountCounter
     */
    public static int getIdAccountCounter() {
        return idAccountCounter;
    }

    /**
     * @param idAccountCounter the idAccountCounter to set
     */
    public static void setIdAccountCounter(int idAccountCounter) {
        Account.idAccountCounter = idAccountCounter;
    }

    /**
     * @return the idAccount
     */
    public int getIdAccount() {
        return idAccount;
    }

    /**
     * @param idAccount the idAccount to set
     */
    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    /**
     * @param team the team to set
     */
    public void setTeam(Team team) {
        this.team = team;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the joystick
     */
    public Joystick getJoystick() {
        return joystick;
    }

    /**
     * @param joystick the joystick to set
     */
    public void setJoystick(Joystick joystick) {
        this.joystick = joystick;
    }

}
