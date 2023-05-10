package lobby.lobbyModel;

import java.util.ArrayList;

import lobby.Account;
import lobby.MasterOrder;
import lobby.Team;

public class LobbyModel {

    // Atributos
    private int selectedRule;
    private ArrayList<Account> players;
    private int idNextPlayers;
    private int roomMasterId;
    private GameRules gameRules;

    // Constructor
    public LobbyModel() {
        this.selectedRule = 0;
        this.players = new ArrayList<Account>();
        this.idNextPlayers = 0;
        this.roomMasterId = -1;
        this.gameRules = new GameRules(100, 10, 0);
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
                        roomMasterId = -1;
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
            if (existingPlayer.getTeam() == Team.Red) {
                redTeam += 1;
            } else {
                blueTeam += 1;
            }
        }

        if (redTeam > blueTeam) {
            account.setTeam(Team.Blue);
        } else if (redTeam < blueTeam) {
            account.setTeam(Team.Red);
        } else if (redTeam == blueTeam) {
            if (Math.random() > 0.5) {
                account.setTeam(Team.Blue);
            } else {
                account.setTeam(Team.Red);
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
        this.selectedRule += 1;
    }

    public void beforeGameRulePosition(MasterOrder order) {
        this.selectedRule -= 1;
    }

    public void minusGameRuleValue(MasterOrder order) {

    }

    public void plusGameRuleValue(MasterOrder order) {

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

    public int getidNextPlayers() {
        return idNextPlayers;
    }

    public void setidNextPlayers(int idNextPlayers) {
        this.idNextPlayers = idNextPlayers;
    }

    public int getRoomMasterId() {
        return roomMasterId;
    }

    public void setRoomMasterId(int roomMasterId) {
        this.roomMasterId = roomMasterId;
    }

    public GameRules getGameRules() {
        return gameRules;
    }

    public void setGameRules(GameRules gameRules) {
        this.gameRules = gameRules;
    }

}
