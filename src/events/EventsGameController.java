package events;

import java.util.ArrayList;

import lobby.lobbyModel.GameRules;
import maincontroller.MainGameController;
import maincontroller.gameinfo.Team;
import maincontroller.gameinfo.TeamName;
import visual.Maps;

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
    public void setGameRules(GameRules gameRules){
        this.eventsModel.setGameRules(gameRules);
    }

    /*
     * @param event : Event produced at the game and detected by the main controller
     * 
     * @return ArrayList of actions produced by the event 
     * 
     * Calls the model's processEvent method and analyze what type of event it is,
     * dependening on this and the conditions contained at the event and setted 
     * at the game rules, will send a list of actions to execute as a result of this event
     */
    public ArrayList<Action> processEvent(Event event){
        return this.eventsModel.processEvent(event);
    }


    public Maps getMap(){
        return this.eventsModel.getMap();
    }
}
