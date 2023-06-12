package maincontroller.notifications;

import java.util.ArrayList;

import events.Action;
import events.ExplosionAction;
import events.GameWinAction;
import events.LifeDecreaseAction;
import events.MoveWindowVisualObjectAction;
import events.PointWinAction;
import events.TeamActions;
import events.VisualObjectAction;
import maincontroller.gameinfo.GameState;
import maincontroller.maincommunications.soundserver.packages.SoundType;
import visual.VisualObject;

public class NotificationsActionsProcessor {

    // ! Attributes
    private NotificationsManager notificationsManager;

    public NotificationsActionsProcessor(NotificationsManager notificationsManager) {
        this.setNotificationsManager(notificationsManager);
    }

    // ! ProcessActions

    /**
     * Process a list of actions to execute
     * 
     * @param actions The list of actions to execute
     * @throws Exception
     */
    public void processActions(ArrayList<Action> actions) throws Exception {

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
            canMove = false;

        } else if (visualObjectAction instanceof MoveWindowVisualObjectAction) {
            this.processActionMoveWindowVisualObject((MoveWindowVisualObjectAction) visualObjectAction);
            canMove = false;

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
        this.playSound(SoundType.HIT);
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
    private void decreaseLifeVisualObject(VisualObject visualObject, float lifeDowngrade) throws Exception {
        this.getNotificationsManager().decreaseLifeVisualObject(visualObject, lifeDowngrade);
    }

    /**
     * Process an action of explosion of a visual object
     * 
     * @param explosionAction The action to process
     */
    private void processActionExplosion(ExplosionAction explosionAction) {
        this.getNotificationsManager().processActionExplosion(explosionAction);
    }

    private void processActionMoveWindowVisualObject(MoveWindowVisualObjectAction moveWindowVisualObjectAction) {
        this.getNotificationsManager().processActionMoveWindowVisualObject(moveWindowVisualObjectAction);

    }

    private void updateVisualObjectPosition(VisualObject visualObject) {
        this.getNotificationsManager().updateVisualObjectPosition(visualObject);
    }

    private void setGameState(GameState gameState) {
        this.getNotificationsManager().setGameState(gameState);
    }

    private void tryApplyingToMaster() {
        this.getNotificationsManager().tryApplyingToMaster();
    }

    private void playSound(SoundType soundType) {
        this.getNotificationsManager().playSound(soundType);
    }

    // ! ProcessActionsTeams

    /**
     * Process an action of a team (game win, etc)
     * 
     * @param teamActions The action to process
     */
    private void processActionTeam(TeamActions teamActions) {

        if (teamActions instanceof PointWinAction) {
            this.processActionPointWin((PointWinAction) teamActions);

        } else if (teamActions instanceof GameWinAction) {
            this.processActionGameWin((GameWinAction) teamActions);
        }
    }

    private void processActionPointWin(PointWinAction pointWinAction) {
        this.getNotificationsManager().processActionPointWin(pointWinAction);
    }

    private void processActionGameWin(GameWinAction gameWinAction) {
        this.setGameState(GameState.GAME_END);
        this.tryApplyingToMaster();
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
