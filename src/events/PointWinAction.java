package events;

import maincontroller.gameinfo.Team;

/**
 * The PointWinAction class represents an action that triggers a point win at
 * the specified team score.
 * It extends the {@link TeamActions} class.
 */
public class PointWinAction extends TeamActions {
    
    // Constructor
    public PointWinAction(Team team) {
        super(team);
    }
}
