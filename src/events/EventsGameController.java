package events;

import java.util.ArrayList;

public class EventsGameController {
    private EventsModel eventsModel;

    public EventsGameController(EventsModel eventsModel){
        this.eventsModel = eventsModel;
    }

    public void setGameRules(GameRules gameRules){
        this.eventsModel.setGameRules(gameRules);
    }

    public ArrayList<Action> proccessEvent(Event event){
       return this.eventsModel.processEvent(event);
    }
}
