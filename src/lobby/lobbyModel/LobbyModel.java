package lobby.lobbyModel;

import java.util.ArrayList;

import maincontroller.Account;
import lobby.LOBBYSECTION;
import lobby.MasterOrder;
import maincontroller.Team;

public class LobbyModel {

    // Atributos
    private int selectedRule;
    private LOBBYSECTION lobbysection;
    private ArrayList<Account> players;
    private Long idNextPlayers;
    private Long roomMasterId;
    private GameRules gameRules;
    private int[] gameRulesValues;

    // Constructor
    public LobbyModel() {
        this.selectedRule = 0;
        this.lobbysection = LOBBYSECTION.values()[0];
        this.players = new ArrayList<Account>();
        this.idNextPlayers = 0L;
        this.roomMasterId = -1L;
        this.gameRulesValues = new int[] { 100, 10, 0 };
    }

    // Metodos
    public void addAccount(Account account) {
        if (account != null && players.size() < gameRules.getMaxPlayer()) {
            account.setId(idNextPlayers);
            idNextPlayers += 1;
            if (account.getId() == 0 || roomMasterId == -1) {
                roomMasterId = account.getId();
            }
            randomTeam(account);
            players.add(account);
        }

    }

    public void removeAccount(Account account) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getId() == account.getId()) {
                if (players.get(i).getId() == roomMasterId) {
                    if (players.size() > 1) {
                        roomMasterId = players.get(i + 1).getId();
                    } else {
                        roomMasterId = -1L;
                    }
                }
                players.remove(i);
            }
        }
    }

    public void randomTeam(Account account) {
        int redTeam = 0;
        int blueTeam = 0;
        for (Account existingPlayer : players) {
            if (existingPlayer.getTeam() == Team.RED) {
                redTeam += 1;
            } else {
                blueTeam += 1;
            }
        }

        if (redTeam > blueTeam) {
            account.setTeam(Team.BLUE);
        } else if (redTeam < blueTeam) {
            account.setTeam(Team.RED);
        } else if (redTeam == blueTeam) {
            if (Math.random() > 0.5) {
                account.setTeam(Team.BLUE);
            } else {
                account.setTeam(Team.RED);
            }
        }
    }

    public void reciveMasterMsg(Account account, MasterOrder order) {
        if (account.getId() == roomMasterId) {
            switch (order) {
                case LEFT:
                    beforeGameRulePosition(order);
                    break;
                case RIGHT:
                    nextGameRulePosition(order);
                    break;
                case OK:
                    plusGameRuleValue(order);
                    break;
                case BACK:
                    minusGameRuleValue(order);
                    break;
            }
        }
    }

    public void nextGameRulePosition(MasterOrder order) {
        this.selectedRule = Math.floorMod(selectedRule + 1, gameRulesValues.length);
    }

    public void beforeGameRulePosition(MasterOrder order) {
        this.selectedRule = Math.floorMod(selectedRule - 1, gameRulesValues.length);
    }

    public void minusGameRuleValue(MasterOrder order) {
        if (lobbysection == LOBBYSECTION.MAP) {
            gameRulesValues[selectedRule] = Math.floorMod(gameRulesValues[selectedRule] - 1, gameRulesValues.length);
        } else {
            gameRulesValues[selectedRule] -= 1;
        }
    }

    public void plusGameRuleValue(MasterOrder order) {
        if (lobbysection == LOBBYSECTION.MAP) {
            gameRulesValues[selectedRule] = Math.floorMod(gameRulesValues[selectedRule] + 1, gameRulesValues.length);
        } else {
            gameRulesValues[selectedRule] += 1;
        }
    }

    // Getter y Setters
    public int getSelectedRule() {
        return selectedRule;
    }

    public void setSelectedRule(int selectedRule) {
        this.selectedRule = selectedRule;
    }

    public ArrayList<Account> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Account> players) {
        this.players = players;
    }

    public GameRules getGameRules() {
        return gameRules;
    }

    public void setGameRules(GameRules gameRules) {
        this.gameRules = gameRules;
    }

    public Long getIdNextPlayers() {
        return idNextPlayers;
    }

    public void setIdNextPlayers(Long idNextPlayers) {
        this.idNextPlayers = idNextPlayers;
    }

    public Long getRoomMasterId() {
        return roomMasterId;
    }

    public void setRoomMasterId(Long roomMasterId) {
        this.roomMasterId = roomMasterId;
    }

    public int[] getGameRulesValues() {
        return gameRulesValues;
    }

    public void setGameRulesValues(int[] gameRulesValues) {
        this.gameRulesValues = gameRulesValues;
    }

    public LOBBYSECTION getDinamicRules() {
        return lobbysection;
    }

    public void setDinamicRules(LOBBYSECTION lobbysection) {
        this.lobbysection = lobbysection;
    }

}
