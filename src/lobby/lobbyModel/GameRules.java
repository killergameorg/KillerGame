package lobby.lobbyModel;

import lobby.Maps;

public class GameRules {

    private DinamicGameRule dinamicGameRule;
    private StaticGameRule staticGameRule;

    // Constructor
    public GameRules() {
        this.dinamicGameRule=new DinamicGameRule(100, 10, Maps.MAP_1);
        this.staticGameRule=new StaticGameRule();
    }

    // Getter y Setters
    public DinamicGameRule getDinamicGameRule() {
        return dinamicGameRule;
    }

    public void setDinamicGameRule(DinamicGameRule dinamicGameRule) {
        this.dinamicGameRule = dinamicGameRule;
    }

    public StaticGameRule getStaticGameRule() {
        return staticGameRule;
    }

    public void setStaticGameRule(StaticGameRule staticGameRule) {
        this.staticGameRule = staticGameRule;
    }

}