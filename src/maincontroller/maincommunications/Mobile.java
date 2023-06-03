package maincontroller.maincommunications;

import maincontroller.gameinfo.Account;
import maincontroller.gameinfo.Team;

public class Mobile {

    // ! Attributes

    private String ip;
    private Account account;

    // ! Constructor

    public Mobile(String ip, Team team) {
        this.setIp(ip);
        this.setAccount(new Account(team));
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
     * @return the account
     */
    public Account getAccount() {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(Account account) {
        this.account = account;
    }

}
