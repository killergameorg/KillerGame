package maincontroller.maincommunications.packages;

public class PackageRemoveConnection implements PackageMainCommunications {

    // ! Attributes
    private String ip;

    // ! Constructor
    public PackageRemoveConnection(String ip) {
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
