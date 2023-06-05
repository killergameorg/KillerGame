package maincontroller.maincommunications.typesofconnections;

public class ClusterComputer {

    // ! Attributes
    private int id;
    private String ip;

    // ! Constructor
    public ClusterComputer(int id, String ip) {
        this.setId(id);
        this.setIp(ip);
    }

    // ! Getters and Setters

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

}
