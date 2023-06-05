package maincontroller.maincommunications.mobiles.packages;

import java.io.Serializable;

import maincontroller.gameinfo.Team;

public class PackageShipInfo implements Serializable {
    private static final long serialVersionUID = 2753167L;

    private float life;
    private Team team;

    public PackageShipInfo(float life, Team team) {
        this.life = life;
        this.team = team;
    }

    public float getLife() {
        return life;
    }

    /**
     * @return the team
     */
    public Team getTeam() {
        return team;
    }

}
