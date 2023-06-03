package maincontroller.notifications;

import java.util.ArrayList;

import events.Action;
import events.ExplosionAction;
import events.GameWinAction;
import events.LifeDecreaseAction;
import events.TeamActions;
import events.VisualObjectAction;
import visual.VisualObject;

public class NotificationsActionsProcessor {

    // ! Attributes
    private NotificationsManager notificationsManager;

    // ! ProcessActions

    /**
     * Process a list of actions to execute
     * 
     * @param actions The list of actions to execute
     * @throws Exception
     */
    public void processActions(ArrayList<Action> actions) throws Exception {
        // TODO: Me molaría ponerme con Juan para ver todas las Acciones resultantes y
        // TODO: que cree que podría saltar por ello (Sonidos, efectos, etc)

        for (int i = 0; i < actions.size(); i++) {
            Action action = actions.get(i);

            if (action instanceof VisualObjectAction) {
                this.processActionVisualObject((VisualObjectAction) action);
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
     * @throws Exception
     */
    private void processActionVisualObject(VisualObjectAction visualObjectAction) throws Exception {
        boolean canMove = true;

        if (visualObjectAction instanceof LifeDecreaseAction) {
            this.processActionLifeDecrease((LifeDecreaseAction) visualObjectAction);
        } else if (visualObjectAction instanceof ExplosionAction) {
            this.processActionExplosion((ExplosionAction) visualObjectAction);

            canMove = false; // The object can't move
        }

        if (canMove) {
            this.updateVisualObjectPosition(visualObjectAction.getVisualObject());
        }
    }

    /**
     * Process an action of life decrease of a visual object
     * 
     * @param lifeDecreaseAction The action to process
     * @throws Exception
     */
    private void processActionLifeDecrease(LifeDecreaseAction lifeDecreaseAction) throws Exception {

        // TODO: Sonido que no existe y podría molar this.playSound(SoundType.???)

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
     * @throws Exception
     */
    private void decreaseLifeVisualObject(VisualObject visualObject, int lifeDowngrade) throws Exception {
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
