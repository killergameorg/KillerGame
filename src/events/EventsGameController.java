package events;

import java.util.ArrayList;

import lobby.Maps;
import lobby.lobbyModel.GameRules;
import maincontroller.MainGameController;
import maincontroller.gameinfo.Team;
import maincontroller.gameinfo.TeamName;

/**
 * The EventsGameController class is responsible for controlling game events in
 * the game.
 */
public class EventsGameController {
    
    private MainGameController mainGameController;
    private EventsModel eventsModel;
    

    public EventsGameController(MainGameController mainGameController){
        this.mainGameController = mainGameController;
        this.eventsModel = new EventsModel(this);
    }




    public Team contraryTeam(TeamName teamName) {
        return this.mainGameController.contraryTeam(teamName);
    }


    /*
     * @param gameRules : The game rules defined at the lobby
     * 
     * Calls the model's setter method to define which are
     * gonna be the game rules, to decide depending the event 
     * which rule take in account
     */

    public void setGameRules(GameRules gameRules) {
        this.eventsModel.setGameRules(gameRules);
    }

    /**
     * Processes the given event and returns a list of actions resulting from the
     * event processing.
     *
     * @param event Event object representing the event to be processed.
     * @return An ArrayList of Action objects resulting from the event
     *         processed.
     */

    public ArrayList<Action> proccessEvent(Event event) {
        return this.eventsModel.processEvent(event);
    }


    public Maps getMap(){
        return this.eventsModel.getMap();
    }
}
