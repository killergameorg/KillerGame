package events;

import maincontroller.gameinfo.Team;

/**
 * The PointWinAction class represents an action that triggers a point win at
 * the specified team score.
 * It extends the {@link TeamActions} class.
 */
public class PointWinAction extends TeamActions {
    /**
     * Creates a new instance of the PointWinAction class with the specified team.
     *
     * @param team The team on which the point win action will be
     *             executed.
     */
    public PointWinAction(Team team) {
        super(team);
    }
}
