package events;

import java.util.ArrayList;

public class EventsModel {
    private GameRules gameRules;

    public GameRules getGameRules() {
        return gameRules;
    }

    public void setGameRules(GameRules gameRules) {
        this.gameRules = gameRules;
    }

    public EventsModel(GameRules gameRules) {
        this.gameRules = gameRules;
    }

    // Process an Event given by the main Controller
    public ArrayList<Action> processEvent(Event event) {

        ArrayList<Action> actions = new ArrayList<>();

        if (event instanceof Colision) {
            actions.addAll(processColision(event.getFirstObject(), event.getSecondObject()));
        }

        return actions;
    }

    // Process a Colision Event
    public ArrayList<Action> processColision(VisualObject visualObject1, VisualObject visualObject2) {

        ArrayList<Action> actions = new ArrayList<>();

        if (visualObject1 instanceof Ship) {

            actions.addAll(processLifeDecrease(visualObject1, visualObject2));

        }

        if (visualObject2 instanceof Ship) {

            actions.addAll(processLifeDecrease(visualObject2, visualObject1));
        }

        return actions;
    }

    // Process a Life decrease Event
    public ArrayList<Action> processLifeDecrease(VisualObject emisor, VisualObject receiver) {
        ArrayList<Action> actions = new ArrayList<>();

        if (receiver instanceof Ship) {
            if (emisor instanceof Ship) {
                Action lifeDecrease = new LifeDecreaseAction(receiver, this.gameRules.getColisionDamage());
                actions.add(lifeDecrease);

                if ((receiver.getLife() - this.gameRules.getColisionDamage()) <= 0) {
                    actions.addAll((processDeath(receiver)));
                }

            } else if (emisor instanceof Bullet) {
                Action lifeDecrease = new LifeDecreaseAction(receiver, this.gameRules.getBulletDamage());
                actions.add(lifeDecrease);

                if ((receiver.getLife() - this.gameRules.getBulletDamage()) <= 0) {
                    actions.addAll((processDeath(receiver)));
                }

            }
        }

        return actions;
    }

    // Process a Death Event
    public ArrayList<Action> processDeath(VisualObject visualObject) {
        ArrayList<Action> actions = new ArrayList<>();
        if (visualObject instanceof Ship) {
            Action action = new ExplosionAction(visualObject);
            actions.add(action);
            // new PointWin
        } else if (visualObject instanceof Bullet) {
            Action action = new ExplosionAction(visualObject);
            actions.add(action);
        }
        // Action action = new DestructionAction(visualObject);
        return actions;

    }

    // Process a Shot Event
    // public ArrayList<Action> processShot(Bullet bullet, VisualObject receiver) {
    // ArrayList<Action> actions = new ArrayList<>();
    // if (receiver instanceof Ship) {
    // // if bullet not friendly
    // actions.addAll(processLifeDecrease(bullet, receiver,
    // this.gameRules.getBulletDamage()));
    // actions.addAll(processDeath(bullet));
    // }
    // return actions;
    // }

    // public static void main(String[] args) {
    // Event event = new Colision(new VisualObject(100), new VisualObject(50));

    // Ship ship1 = new Ship(100);
    // Ship ship2 = new Ship(100);

    // EventsModel em = new EventsModel(new GameRules(10));
    // em.processEvent(event);

    // System.out.println(em.processColision(ship1, ship2,
    // em.getGameRules()).toString());
    // }

}
