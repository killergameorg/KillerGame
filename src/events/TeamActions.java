package events;

public abstract class TeamActions extends Action {
    private Team team;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public TeamActions(Team team) {
        this.team = team;
    }
    
}
