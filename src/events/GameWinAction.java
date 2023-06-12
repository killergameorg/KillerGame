package events;

import maincontroller.gameinfo.Team;

/**
 * The GameWinAction class represents an action that triggers the game victory
 * of a team.
 * It extends the {@link TeamActions} class.
 */
public class GameWinAction extends TeamActions {
    
    // Constructor 
    public GameWinAction(Team team) {
        super(team);
    }
}
