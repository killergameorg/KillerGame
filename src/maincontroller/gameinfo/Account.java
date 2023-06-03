package maincontroller.gameinfo;

import visual.VisualObject;

public class Account {

    // ! Attributes
    private static int idAccountCounter = 0;

    private int idAccount;
    private boolean isMaster;
    private Team team;
    private Joystick joystick;

    private VisualObject visualObject;

    // ! Constructor
    public Account(Team team) {

        Account.setIdAccountCounter(Account.getIdAccountCounter() + 1);

        this.setIdAccount(Account.getIdAccountCounter());
        this.setMaster(false);

        this.setTeam(team);
        this.setJoystick(new Joystick());

        this.setVisualObject(null);

    }

    public Account(int idAccount, boolean isMaster, Team team) {
        this.setIdAccount(idAccount);
        this.setMaster(isMaster);
        this.setTeam(team);
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

    /**
     * @return the isMaster
     */
    public boolean isMaster() {
        return isMaster;
    }

    /**
     * @param isMaster the isMaster to set
     */
    public void setMaster(boolean isMaster) {
        this.isMaster = isMaster;
    }

    /**
     * @return the visualObject
     */
    public VisualObject getVisualObject() {
        return visualObject;
    }

    /**
     * @param visualObject the visualObject to set
     */
    public void setVisualObject(VisualObject visualObject) {
        this.visualObject = visualObject;
    }

}
