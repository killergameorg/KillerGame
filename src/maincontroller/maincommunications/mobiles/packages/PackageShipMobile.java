package maincontroller.maincommunications.mobiles.packages;

import java.io.Serializable;

public class PackageShipMobile implements PackageMobileCommunications, Serializable {
    private static final long serialVersionUID = 9133567L;

    // TODO: Tengo que avisar al departamento de móvil que será necesario que tengan
    // TODO: la IP aquí para cuando el LobbyMaster les ponga un Id
    private String ip;
    private int accountId;
    private boolean isMaster;
    private Object message;

    public PackageShipMobile(int accountId, boolean isMaster, Object message) {
        this.accountId = accountId;
        this.isMaster = isMaster;
        this.message = message;
    }

    public PackageShipMobile(String ip, int accountId, boolean isMaster, Object message) {
        this.ip = ip;
        this.accountId = accountId;
        this.isMaster = isMaster;
        this.message = message;
    }

    /**
     * @return the accountId
     */
    public int getAccountId() {
        return accountId;
    }

    /**
     * @return the isMaster
     */
    public boolean isMaster() {
        return isMaster;
    }

    /**
     * @return the message
     */
    public Object getMessage() {
        return message;
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
