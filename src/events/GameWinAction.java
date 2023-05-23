package events;

import maincontroller.gameinfo.Team;

/**
 * The GameWinAction class represents an action that triggers the game victory
 * of a team.
 * It extends the {@link TeamActions} class.
 */
public class GameWinAction extends TeamActions {
    /**
     * Creates a new instance of the GameWinAction class with the specified team.
     *
     * @param team The team on which the game win action will be
     *             executed.
     */
    public GameWinAction(Team team) {
        super(team);
    }
}
