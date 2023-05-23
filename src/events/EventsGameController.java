package events;

import java.util.ArrayList;

import lobby.lobbyModel.GameRules;

/**
 * The EventsGameController class is responsible for controlling game events in
 * the game.
 */
public class EventsGameController {
    private EventsModel eventsModel;

    /**
     * Creates a new instance of the EventsGameController class with the specified
     * EventsModel.
     *
     * @param eventsModel EventsModel instance to be associated with the
     *                    controller.
     */
    public EventsGameController(EventsModel eventsModel) {
        this.eventsModel = eventsModel;
    }

    /**
     * Sets the game rules to decide at the game which actions do.
     *
     * @param gameRules Represents the game rules to be set defined at the lobby.
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
}
