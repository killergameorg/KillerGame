package maincontroller;

public class Account {

    // ! Attributes
    private Long id;
    private Team team;
    private String ip;
    private Joystick joystick;

    // ! Constructor
    public Account(Team team, String ip) {
        this.setId(0L); // TODO
        this.setTeam(team);
        this.setIp(ip);
        this.setJoystick(new Joystick());
    }

    // ! Getters and Setters
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the team
     */
    public Team getTeam() {
        return team;
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
