package events;

/**
 * The TeamActions class is an abstract subclass of {@link Action} that
 * represents actions performed on a team.
 * It provides a common structure and functionality for team-specific actions.
 */

public abstract class TeamActions extends Action {
    private Team team;

    /**
     * Constructs a new TeamActions object with the specified team.
     *
     * @param team the team associated with the actions
     */

    public TeamActions(Team team) {
        this.team = team;
    }

    /**
     * Returns the team associated with the actions.
     *
     * @return the team associated with the actions
     */
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

}
