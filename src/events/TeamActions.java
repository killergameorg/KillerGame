package events;

import maincontroller.gameinfo.Team;

public abstract class TeamActions extends Action {
    private Team team;

    public TeamActions(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

}
