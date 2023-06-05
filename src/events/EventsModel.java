package events;

import java.util.ArrayList;

import lobby.Maps;
import lobby.lobbyModel.GameRules;
import maincontroller.gameinfo.Team;
import maincontroller.gameinfo.TeamName;
import visual.Bullet;
import visual.SpaceShip;
import visual.VisualObject;

/**
 * The EventsModel class represents the model component responsible for
 * processing events in the game.
 */
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
            // Aquí calló un héroe
            // actions.addAll(processPowerUp(event.getFirstObject(),
            // event.getSecondObject()));
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

        if (visualObject2 instanceof SpaceShip || visualObject2 instanceof Bullet) {
            if (!visualObject1.getTeam().equals(visualObject2.getTeam())) {
                actions.addAll(processLifeDecrease(visualObject2, visualObject1));
            }

        } else {
            actions.addAll(processLifeDecrease(visualObject2, visualObject1));
        }

        if (visualObject1 instanceof SpaceShip || visualObject1 instanceof Bullet) {
            if (!visualObject2.getTeam().equals(visualObject1.getTeam())) {
                actions.addAll(processLifeDecrease(visualObject1, visualObject2));
            }

        } else {
            actions.addAll(processLifeDecrease(visualObject1, visualObject2));

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

        if (emisor instanceof SpaceShip) {
            actions.add(new LifeDecreaseAction(receiver, this.gameRules.getStaticGameRule().getColisionDamage()));

            if ((receiver.getLife() - this.gameRules.getStaticGameRule().getColisionDamage()) <= 0) {
                actions.addAll((processDeath(receiver)));

                if (receiver instanceof SpaceShip) {
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
            actions.add(new LifeDecreaseAction(receiver, this.gameRules.getDinamicGameRule().getBulletDamage()));

            if ((receiver.getLife() - this.gameRules.getDinamicGameRule().getBulletDamage()) <= 0) {
                actions.addAll((processDeath(receiver)));

                if (receiver instanceof SpaceShip) {
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
            actions.add(new LifeDecreaseAction(receiver, this.gameRules.getStaticGameRule().getColisionDamage()));

            if ((receiver.getLife() - this.gameRules.getStaticGameRule().getColisionDamage()) <= 0) {
                actions.addAll((processDeath(receiver)));

                if (receiver instanceof SpaceShip) {
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

        if ((team.getScore() + 1) == this.gameRules.getStaticGameRule().getWinScore().get(team.getTeamName())) {
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

    public Maps getMap() {
        return this.gameRules.getDinamicGameRule().getMap();
    }

    private Team contraryTeam(TeamName teamName) {
        return this.eventsGameController.contraryTeam(teamName);
    }
}
