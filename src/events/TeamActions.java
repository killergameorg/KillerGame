package events;

import maincontroller.gameinfo.Team;

/**
 * The TeamActions class is an abstract subclass of {@link Action} that
 * represents actions performed on a team.
 * It provides a common structure and functionality for team-specific actions.
 */

public abstract class TeamActions extends Action {

    // Attributes
    private Team team;

    // Constructor
    public TeamActions(Team team) {
        this.team = team;
    }

    // Getters and Setters
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

}
