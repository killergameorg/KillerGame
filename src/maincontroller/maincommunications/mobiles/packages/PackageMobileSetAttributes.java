package maincontroller.maincommunications.mobiles.packages;

import maincontroller.gameinfo.Team;

public class PackageMobileSetAttributes implements PackageMobileCommunications {

    // ! Attributes
    private String ip;
    private int id;
    private boolean isMaster;
    private Team team;

    // ! Constructor
    public PackageMobileSetAttributes(String ip, int id, boolean isMaster, Team team) {
        this.setIp(ip);
        this.setId(id);
        this.setMaster(isMaster);
        this.setTeam(team);
    }


    // ! Getters and Setters

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
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
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


    

}
