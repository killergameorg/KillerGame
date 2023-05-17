package events;

import java.util.ArrayList;

public class EventsGameController {
    private EventsModel eventsModel;

    public EventsGameController(EventsModel eventsModel){
        this.eventsModel = eventsModel;
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
    public ArrayList<Action> proccessEvent(Event event){
       return this.eventsModel.processEvent(event);
    }
}
