package lobby.lobbyModel;

import lobby.LOBBYSECTION;
import lobby.Maps;
import lobby.MasterOrder;
import lobby.lobbyController.LobbyController;
import maincontroller.Account;
import maincontroller.Team;

import java.util.ArrayList;

public class LobbyModel {

    // Atributos
    private int selectedRule;
    private LOBBYSECTION lobbysection;
    private ArrayList<Account> players;
    private Long idNextPlayers;
    private Long roomMasterId;
    private GameRules gameRules;
    private int numChangeableRule;
    private LobbyController lobbyController;

    // Constructor
    public LobbyModel(LobbyController lobbyController) {
        this.lobbyController = lobbyController;
        this.selectedRule = 0;
        this.lobbysection = LOBBYSECTION.values()[selectedRule];
        this.players = new ArrayList<Account>();
        this.idNextPlayers = 0L;
        this.roomMasterId = -1L;
        this.gameRules = new GameRules(100, 10, Maps.map1);
        this.numChangeableRule = LOBBYSECTION.values().length;

        //TODO Aqui al crear el modelo debe pasar enum de que esta seleccionado Life
        // Tu metodo(lobbysection),
        lobbyController.getLobbyView().updatePos(this.lobbysection);
        lobbyController.getLobbyView().refreshMasterValues(this.gameRules);
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

        //TODO Aqui debe actualizar cantidad de jugador que hay
        // Tu metodo(player.size)
        lobbyController.getLobbyView().refreshPlayerNum(players.size());
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

        //TODO Aqui debe actualizar cantidad de jugador que hay
        // Tu metodo(player.size)
        lobbyController.getLobbyView().refreshPlayerNum(players.size());
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
                    beforeGameRulePosition();
                    break;
                case RIGHT:
                    nextGameRulePosition();
                    break;
                case OK:
                    plusGameRuleValue();
                    break;
                case BACK:
                    minusGameRuleValue();
                    break;
            }
        }
    }

    public void nextGameRulePosition() {
        this.selectedRule = Math.floorMod(selectedRule + 1, numChangeableRule);
        this.lobbysection = LOBBYSECTION.values()[selectedRule];

        //TODO Aqui debe pasar el enum
        // Tu metodo(lobbysection)
        lobbyController.getLobbyView().updatePos(this.lobbysection);
    }

    public void beforeGameRulePosition() {
        this.selectedRule = Math.floorMod(selectedRule - 1, numChangeableRule);
        this.lobbysection = LOBBYSECTION.values()[selectedRule];

        //TODO Aqui debe pasar el enum
        // Tu metodo(lobbysection)
        lobbyController.getLobbyView().updatePos(this.lobbysection);
    }

    public void minusGameRuleValue() {
        //Actualizar valores refresh()
        switch (this.lobbysection) {
            case LIFE:
                if (this.gameRules.getLife() > 0) {
                    this.gameRules.setLife(this.gameRules.getLife() - 5);
                }
                break;
            case BULLETDAMAGE:
                if (this.gameRules.getBulletDamage() > 0) {
                    this.gameRules.setBulletDamage(this.gameRules.getBulletDamage() - 5);
                }
                break;
            case MAP:
                int enumPosition = Maps.valueOf(this.gameRules.getMap().toString()).ordinal();
                this.gameRules.setMap(Maps.values()[Math.floorMod(enumPosition - 1, Maps.values().length)]);
                break;
            case GAMEREADY:
                this.lobbyController.startGame(gameRules, players);
                break;
        }
        //TODO Aqui te paso el game rule al actualizar
        // Tu metodo(gameRule)
        lobbyController.getLobbyView().refreshMasterValues(this.gameRules);
    }

    public void plusGameRuleValue() {
        //Actualizar valores refresh()
        switch (this.lobbysection) {
            case LIFE:
                this.gameRules.setLife(this.gameRules.getLife() + 5);
                break;
            case BULLETDAMAGE:
                this.gameRules.setBulletDamage(this.gameRules.getBulletDamage() + 5);
                break;
            case MAP:
                int enumPosition = Maps.valueOf(this.gameRules.getMap().toString()).ordinal();
                this.gameRules.setMap(Maps.values()[Math.floorMod(enumPosition + 1, Maps.values().length)]);
                break;
            case GAMEREADY:
                this.lobbyController.startGame(gameRules, players);
                break;
        }
        //TODO Aqui te paso el game rule al actualizar
        // Tu metodo(gameRule)
        lobbyController.getLobbyView().refreshMasterValues(this.gameRules);
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

    public LOBBYSECTION getDinamicRules() {
        return lobbysection;
    }

    public void setDinamicRules(LOBBYSECTION lobbysection) {
        this.lobbysection = lobbysection;
    }

    public LOBBYSECTION getLobbysection() {
        return lobbysection;
    }

    public void setLobbysection(LOBBYSECTION lobbysection) {
        this.lobbysection = lobbysection;
    }

    public int getNumChangeableRule() {
        return numChangeableRule;
    }

    public void setNumChangeableRule(int numChangeableRule) {
        this.numChangeableRule = numChangeableRule;
    }

    public LobbyController getLobbyController() {
        return lobbyController;
    }

    public void setLobbyController(LobbyController lobbyController) {
        this.lobbyController = lobbyController;
    }

}
