package maincontroller.notifications;

import java.util.ArrayList;

import events.Action;
import events.ExplosionAction;
import events.GameWinAction;
import events.LifeDecreaseAction;
import events.TeamActions;
import events.VisualObjectsActions;
import visual.VisualObject;

public class NotificationsActionsProcessor {

    // ! Attributes
    private NotificationsManager notificationsManager;

    // ! ProcessActions

    /**
     * Process a list of actions to execute
     * 
     * @param actions The list of actions to execute
     */
    public void processActions(ArrayList<Action> actions) {

        for (int i = 0; i < actions.size(); i++) {
            Action action = actions.get(i);

            if (action instanceof VisualObjectsActions) {
                this.processActionVisualObject((VisualObjectsActions) action);
            } else if (action instanceof TeamActions) {
                this.processActionTeam((TeamActions) action);
            }
        }

    }

    // ! ProcessActionsVisualObjects
    /**
     * Process an action of a visual object (life decrease, explosion, etc)
     * 
     * @param visualObjectsActions The action to process
     */
    private void processActionVisualObject(VisualObjectsActions visualObjectsActions) {
        boolean canMove = true;

        if (visualObjectsActions instanceof LifeDecreaseAction) {
            this.processActionLifeDecrease((LifeDecreaseAction) visualObjectsActions);
        } else if (visualObjectsActions instanceof ExplosionAction) {
            this.processActionExplosion((ExplosionAction) visualObjectsActions);

            canMove = false; // The object can't move
        }

        if (canMove) {
            this.updateVisualObjectPosition(visualObjectsActions.getVisualObject());
        }
    }

    /**
     * Process an action of life decrease of a visual object
     * 
     * @param lifeDecreaseAction The action to process
     */
    private void processActionLifeDecrease(LifeDecreaseAction lifeDecreaseAction) {
        this.decreaseLifeVisualObject(
                lifeDecreaseAction.getVisualObject(),
                lifeDecreaseAction.getLifeDowngrade()

        );
    }

    /**
     * Decrease the life of a visual object
     * 
     * @param visualObject  The visual object to decrease the life
     * @param lifeDowngrade The amount of life to decrease from the visual object
     */
    private void decreaseLifeVisualObject(VisualObject visualObject, float lifeDowngrade) {
        this.getNotificationsManager().decreaseLifeVisualObject(visualObject, lifeDowngrade);
    }

    /**
     * Process an action of explosion of a visual object
     * 
     * @param explosionAction The action to process
     */
    private void processActionExplosion(ExplosionAction explosionAction) {
        // TODO: Ask to the visual department about the kill visual object
        this.getNotificationsManager().processActionExplosion(explosionAction);
    }

    private void updateVisualObjectPosition(VisualObject visualObject) {
        this.getNotificationsManager().updateVisualObjectPosition(visualObject);
    }

    // ! ProcessActionsTeams

    /**
     * Process an action of a team (game win, etc)
     * 
     * @param teamActions The action to process
     */
    private void processActionTeam(TeamActions teamActions) {
        if (teamActions instanceof GameWinAction) {
            this.processActionGameWin((GameWinAction) teamActions);
        }
    }

    private void processActionGameWin(GameWinAction gameWinAction) {
        // TODO
    }

    // ! Getters and Setters
    /**
     * @return the notificationsManager
     */
    public NotificationsManager getNotificationsManager() {
        return notificationsManager;
    }

    /**
     * @param notificationsManager the notificationsManager to set
     */
    public void setNotificationsManager(NotificationsManager notificationsManager) {
        this.notificationsManager = notificationsManager;
    }

}
