package events;

import java.util.ArrayList;
import java.util.Map;

import lobby.lobbyModel.GameRules;
import maincontroller.gameinfo.Team;
import visual.Bullet;
import visual.Maps;
import visual.PowerUp;
import visual.Ship;
import visual.VisualObject;

public class EventsModel {
    private GameRules gameRules;

    public GameRules getGameRules() {
        return gameRules;
    }

    public void setGameRules(GameRules gameRules) {
        this.gameRules = gameRules;
    }

    /*
     * @param event : Process the received event
     * 
     * @return ArrayList : List of actions produced by the event
     */

    public ArrayList<Action> processEvent(Event event) {

        ArrayList<Action> actions = new ArrayList<>();

        if (event instanceof Colision) {
            actions.addAll(processColision(event.getFirstObject(), event.getSecondObject()));
        }

        else if (event instanceof GetPowerUp) {
            actions.addAll(processPowerUp(event.getFirstObject(), event.getSecondObject()));
        }

        return actions;
    }

    /*
     * @param visualObject1 : Object 1 which suffers colision
     * 
     * @param visualObject2 : Object 2 which suffers colision
     * 
     * @return ArrayList : List of actions produced by the colision event
     */
    public ArrayList<Action> processColision(VisualObject visualObject1, VisualObject visualObject2) {

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

    /*
     * @param emisor : Object which causes the damage
     * 
     * @param receiver : Object which suffers the damage
     * 
     * @return ArrayList : List of actions produced by the life decrease event
     */
    public ArrayList<Action> processLifeDecrease(VisualObject emisor, VisualObject receiver) {
        ArrayList<Action> actions = new ArrayList<>();

        if (emisor instanceof Ship) {
            actions.add(new LifeDecreaseAction(receiver, this.gameRules.getColisionDamage()));

            if ((receiver.getLife() - this.gameRules.getColisionDamage()) <= 0) {
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

    /*
     * @param visualObject : Object which suffers death
     * 
     * @return ArrayList : List of actions produced by the death event
     */
    public ArrayList<Action> processDeath(VisualObject visualObject) {
        ArrayList<Action> actions = new ArrayList<>();
        actions.add(new ExplosionAction(visualObject));
        return actions;
    }

    /*
     * @param emisor : Object which has the hability to upgrade receiver ship
     * 
     * @param receiver : Object which gains an upgrade
     * 
     * @return ArrayList : List of actions produced by the power up event
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

    /*
     * @param team : Team which earns one point
     * 
     * @return ArrayList : List of actions produced by the point win event
     */
    public ArrayList<Action> processPointWin(Team team) {
        ArrayList<Action> actions = new ArrayList<>();
        actions.add(new PointWinAction(team));

        if ((team.getScore() + 1) == this.gameRules.getWinScore()) {
            actions.addAll(processGameWin(team));
        }
        return actions;
    }

    /*
     * @param team : Team which wins the game
     * 
     * @return ArrayList : List of actions produced by the game win event
     */
    public ArrayList<Action> processGameWin(Team team) {
        ArrayList<Action> actions = new ArrayList<>();
        actions.add(new GameWinAction(team));
        return actions;
    }




    public Maps getMap() {
        return this.gameRules.getMap();
    }

}
