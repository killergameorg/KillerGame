package events;

import java.util.ArrayList;
import java.util.Map;

import lobby.lobbyModel.GameRules;
import maincontroller.gameinfo.Team;
import maincontroller.gameinfo.TeamName;
import visual.Bullet;
import visual.Maps;
import visual.PowerUp;
import visual.Ship;
import visual.VisualObject;

public class EventsModel {

    private EventsGameController eventsGameController;
    private GameRules gameRules;

    public EventsModel(EventsGameController eventsGameController) {
        this.eventsGameController = eventsGameController;
    }

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
            // Aquí calló un héroe
            // actions.addAll(processPowerUp(event.getFirstObject(),
            // event.getSecondObject()));
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

        if (visualObject2 instanceof Ship || visualObject2 instanceof Bullet) {
            if (!visualObject1.getTeam().equals(visualObject2.getTeam())) {
                actions.addAll(processLifeDecrease(visualObject2, visualObject1));
            }

        } else {
            actions.addAll(processLifeDecrease(visualObject2, visualObject1));
        }

        if (visualObject1 instanceof Ship || visualObject1 instanceof Bullet) {
            if (!visualObject2.getTeam().equals(visualObject1.getTeam())) {
                actions.addAll(processLifeDecrease(visualObject1, visualObject2));
            }

        } else {
            actions.addAll(processLifeDecrease(visualObject1, visualObject2));

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

                if (receiver instanceof Ship) {
                    Team teamEmisor = emisor.getTeam();
                    if (teamEmisor.getTeamName() == TeamName.MACHINE) {
                        if (receiver.getTeam().getTeamName() == TeamName.BLUE) {
                            teamEmisor = this.contraryTeam(TeamName.BLUE);
                        } else if (receiver.getTeam().getTeamName() == TeamName.RED) {
                            teamEmisor = this.contraryTeam(TeamName.RED);
                        }
                    }

                    actions.addAll(processPointWin(teamEmisor));
                }
            }

        } else if (emisor instanceof Bullet) {
            actions.add(new LifeDecreaseAction(receiver, this.gameRules.getBulletDamage()));

            if ((receiver.getLife() - this.gameRules.getBulletDamage()) <= 0) {
                actions.addAll((processDeath(receiver)));

                if (receiver instanceof Ship) {
                    Team teamEmisor = emisor.getTeam();
                    if (teamEmisor.getTeamName() == TeamName.MACHINE) {
                        if (receiver.getTeam().getTeamName() == TeamName.BLUE) {
                            teamEmisor = this.contraryTeam(TeamName.BLUE);
                        } else if (receiver.getTeam().getTeamName() == TeamName.RED) {
                            teamEmisor = this.contraryTeam(TeamName.RED);
                        }
                    }

                    actions.addAll(processPointWin(teamEmisor));
                }

            }

            actions.addAll((processDeath(emisor)));

        } else {
            actions.add(new LifeDecreaseAction(receiver, this.gameRules.getColisionDamage()));

            if ((receiver.getLife() - this.gameRules.getColisionDamage()) <= 0) {
                actions.addAll((processDeath(receiver)));

                if (receiver instanceof Ship) {
                    Team teamContraryReceiver = null;
                    if (receiver.getTeam().getTeamName() == TeamName.BLUE) {
                        teamContraryReceiver = this.contraryTeam(TeamName.BLUE);
                    } else if (receiver.getTeam().getTeamName() == TeamName.RED) {
                        teamContraryReceiver = this.contraryTeam(TeamName.RED);
                    }

                    actions.addAll(processPointWin(teamContraryReceiver));
                }
            }
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
    /*
     * public ArrayList<Action> processPowerUp(VisualObject powerUp, VisualObject
     * receiver) {
     * ArrayList<Action> actions = new ArrayList<>();
     * if (receiver instanceof Ship && powerUp instanceof PowerUp) {
     * if ((receiver.getLife() + this.gameRules.getPowerUpUpgrade() <= 100)) {
     * actions.add(new LifeIncreaseAction(powerUp,
     * this.gameRules.getPowerUpUpgrade()));
     * } else if ((receiver.getLife() + this.gameRules.getPowerUpUpgrade() > 100)) {
     * actions.add(new LifeIncreaseAction(powerUp, 100 - receiver.getLife()));
     * }
     * }
     * return actions;
     * }
     */

    /*
     * @param team : Team which earns one point
     * 
     * @return ArrayList : List of actions produced by the point win event
     */
    public ArrayList<Action> processPointWin(Team team) {
        ArrayList<Action> actions = new ArrayList<>();
        actions.add(new PointWinAction(team));

        if ((team.getScore() + 1) == this.gameRules.getWinScore().get(team.getTeamName())) {
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

    private Team contraryTeam(TeamName teamName) {
        return this.eventsGameController.contraryTeam(teamName);
    }
}
