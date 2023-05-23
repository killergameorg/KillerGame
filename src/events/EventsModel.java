package events;

import java.util.ArrayList;

import lobby.lobbyModel.GameRules;
import maincontroller.gameinfo.Team;
import visual.Bullet;
import visual.PowerUp;
import visual.Ship;
import visual.VisualObject;

/**
 * The EventsModel class represents the model component responsible for
 * processing events in the game.
 */
public class EventsModel {
    private GameRules gameRules;

    /**
     * Retrieves the game rules defined at the lobby.
     *
     * @return The GameRules object representing the game rules.
     */
    public GameRules getGameRules() {
        return gameRules;
    }

    public void setGameRules(GameRules gameRules) {
        this.gameRules = gameRules;
    }

    /**
     * Processes the given event and returns a list of actions resulting from the
     * event processing.
     *
     * @param event TheEvent object representing the event to be processed.
     * @return An ArrayList of Action objects resulting from the event processing.
     */
    public ArrayList<Action> processEvent(Event event) {

        ArrayList<Action> actions = new ArrayList<>();

        if (event instanceof Collision) {
            actions.addAll(processCollision(event.getFirstObject(), event.getSecondObject()));
        }

        else if (event instanceof GetPowerUp) {
            actions.addAll(processPowerUp(event.getFirstObject(), event.getSecondObject()));
        }

        return actions;
    }

    /**
     * Processes a collision event between two visual objects and returns a list of
     * actions resulting from the collision.
     *
     * @param visualObject1 The first visual object involved in the collision.
     * @param visualObject2 The second visual object involved in the collision.
     * @return An ArrayList of Action objects resulting from the collision.
     */
    public ArrayList<Action> processCollision(VisualObject visualObject1, VisualObject visualObject2) {

        ArrayList<Action> actions = new ArrayList<>();

        if (!visualObject1.getTeam().equals(visualObject2.getTeam())) {
            if (visualObject1 instanceof Ship) {

                actions.addAll(processLifeDecrease(visualObject1, visualObject2));

            }

            if (visualObject2 instanceof Ship) {

                actions.addAll(processLifeDecrease(visualObject2, visualObject1));
            }
        }

        return actions;
    }

    /**
     * Processes a life decrease event for a visual object and returns a list of
     * actions resulting from the life decrease.
     *
     * @param emisor   The visual object causing the life decrease.
     * @param receiver The visual object whose life is being decreased.
     * @return An ArrayList of Action objects resulting from the life decrease.
     */
    public ArrayList<Action> processLifeDecrease(VisualObject emisor, VisualObject receiver) {
        ArrayList<Action> actions = new ArrayList<>();

        if (emisor instanceof Ship) {
            actions.add(new LifeDecreaseAction(receiver, this.gameRules.getCollisionDamage()));

            if ((receiver.getLife() - this.gameRules.getCollisionDamage()) <= 0) {
                actions.addAll((processDeath(receiver)));
                actions.addAll(processPointWin(emisor.getTeam()));
            }

        } else if (emisor instanceof Bullet) {
            actions.add(new LifeDecreaseAction(receiver, this.gameRules.getBulletDamage()));

            if ((receiver.getLife() - this.gameRules.getBulletDamage()) <= 0) {
                actions.addAll((processDeath(receiver)));
                actions.addAll(processPointWin(emisor.getTeam()));
            }

            actions.addAll((processDeath(emisor)));
        }

        return actions;
    }

    /**
     * Processes a death event for a visual object and returns a list of actions
     * resulting from the death.
     *
     * @param visualObject The visual object that has to die.
     * @return An ArrayList of Action objects resulting from the death.
     */
    public ArrayList<Action> processDeath(VisualObject visualObject) {
        ArrayList<Action> actions = new ArrayList<>();
        actions.add(new ExplosionAction(visualObject));
        return actions;
    }

    /**
     * Processes a power-up event for a visual object and returns a list of actions
     * resulting from the power-up obtain.
     *
     * @param powerUp  The power-up object.
     * @param receiver The visual object receiving the power-up.
     * @return An ArrayList of Action objects resulting from the power-up.
     */
    public ArrayList<Action> processPowerUp(VisualObject powerUp, VisualObject receiver) {
        ArrayList<Action> actions = new ArrayList<>();
        if (receiver instanceof Ship && powerUp instanceof PowerUp) {
            if ((receiver.getLife() + this.gameRules.getPowerUpUpgrade() <= 100)) {
                actions.add(new LifeIncreaseAction(powerUp, this.gameRules.getPowerUpUpgrade()));
            } else if ((receiver.getLife() + this.gameRules.getPowerUpUpgrade() > 100)) {
                actions.add(new LifeIncreaseAction(powerUp, 100 - receiver.getLife()));
            }
        }
        return actions;
    }

    /**
     * Processes a point win event for a team and returns a list of actions
     * resulting from the point win.
     *
     * @param team The team that has scored a point.
     * @return An ArrayList of Action objects resulting from the point win.
     */
    public ArrayList<Action> processPointWin(Team team) {
        ArrayList<Action> actions = new ArrayList<>();
        actions.add(new PointWinAction(team));

        if ((team.getScore() + 1) == this.gameRules.getWinScore()) {
            actions.addAll(processGameWin(team));
        }
        return actions;
    }

    /**
     * Processes a game win event for a team and returns a list of actions resulting
     * from the game win.
     *
     * @param team The team that has won the game.
     * @return An ArrayList of Action objects resulting from the game win.
     */
    public ArrayList<Action> processGameWin(Team team) {
        ArrayList<Action> actions = new ArrayList<>();
        actions.add(new GameWinAction(team));
        return actions;
    }

}
