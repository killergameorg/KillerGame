package maincontroller.maincommunications.typesofconnections;

public class SoundServer {

    // ! Attributes
    private String ip;

    // ! Constructor
    public SoundServer(String ip) {
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
