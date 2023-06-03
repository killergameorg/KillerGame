package maincontroller.maincommunications;

public class ServerSound {

    // ! Attributes
    private String ip;

    // ! Constructor
    public ServerSound(String ip) {
        this.setIp(ip);
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

}
