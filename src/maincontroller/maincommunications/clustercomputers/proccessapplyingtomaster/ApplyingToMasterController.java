package maincontroller.maincommunications.clustercomputers.proccessapplyingtomaster;

import java.util.HashMap;
import java.util.Random;

import maincontroller.gameinfo.GameState;
import maincontroller.gameinfo.Team;
import maincontroller.gameinfo.TeamName;
import maincontroller.maincommunications.clustercomputers.ClusterCommunicationsController;
import maincontroller.maincommunications.clustercomputers.proccessapplyingtomaster.packages.PackageApplyingToMaster;

public class ApplyingToMasterController implements Runnable {

    // ! Attributes
    private ClusterCommunicationsController clusterCommunicationsController;

    private HashMap<Integer, Integer> idsAndVotes;
    private int timeToWaitForVotesFromConfig;

    // ! Constructor
    public ApplyingToMasterController(
            ClusterCommunicationsController clusterCommunicationsController,
            int timeToWaitForVotesFromConfig

    ) {
        this.setClusterCommunicationsController(clusterCommunicationsController);

        this.setIdsAndVotes(new HashMap<Integer, Integer>());
        this.setTimeToWaitForVotesFromConfig(timeToWaitForVotesFromConfig);
    }

    // ! Runnable
    @Override
    public void run() {

        int idMaster = -1;
        while (idMaster == -1) {

            this.sendApplyingToMaster();

            try {
                Thread.sleep(this.getTimeToWaitForVotesFromConfig());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            idMaster = this.checkVotes();

            if (idMaster == -1) {

                try {
                    Thread.sleep(this.getTimeToWaitForVotesFromConfig());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

        if (idMaster == this.getMyId()) {
            this.setMaster();

            if (this.getGameState() == GameState.UNDEFINED) {
                this.createTeams();
                this.startLobby();
            } else if (this.getGameState() == GameState.GAME_END) {
                this.startEndGame();
            }

        } else {
            this.setSlave();
        }

        this.setGameState(GameState.LOBBY);

    }

    // ! Methods

    public void tryApplyingToMaster() {
        new Thread(this).start();
    }

    private void sendApplyingToMaster() {
        this.sendFlood(
                new PackageApplyingToMaster(
                        this.getMyId(),
                        new Random().nextInt(1, 1000000000)

                )

        );
    }

    public void onIncomingMessage(
            String ip,
            PackageApplyingToMaster packageApplyingToMaster

    ) {

        this.getIdsAndVotes().put(
                packageApplyingToMaster.getId(),
                packageApplyingToMaster.getValueToVote()

        );

    }

    private int checkVotes() {

        int idMaster = -1;
        int votesMaster = -1;
        boolean minRepeat = false;

        for (int id : this.getIdsAndVotes().keySet()) {

            if (idMaster == -1 && votesMaster == -1) {
                idMaster = id;
                votesMaster = this.getIdsAndVotes().get(id);

            } else if (this.getIdsAndVotes().get(id) < votesMaster) {
                idMaster = id;
                votesMaster = this.getIdsAndVotes().get(id);

                if (minRepeat) {
                    minRepeat = false;
                }

            } else if (this.getIdsAndVotes().get(id) == votesMaster) {
                minRepeat = true;
            }

        }

        if (minRepeat) {
            idMaster = -1;
        }

        this.getIdsAndVotes().clear();

        return idMaster;
    }

    private void createTeams() {
        for (int i = 0; i < TeamName.values().length; i++) {
            Team.getAccountsForTeams().put(
                    new Team(TeamName.values()[i]),
                    0

            );

        }
    }

    // ! Linking Methods

    private void sendFlood(PackageApplyingToMaster packageApplyingToMaster) {
        this.getClusterCommunicationsController().sendFlood(
                packageApplyingToMaster

        );
    }

    private int getMyId() {
        return this.getClusterCommunicationsController().getMyId();
    }

    private void setMaster() {
        this.getClusterCommunicationsController().setMaster();
    }

    private void setSlave() {
        this.getClusterCommunicationsController().setSlave();
    }

    private void startLobby() {
        this.getClusterCommunicationsController().startLobby();
    }

    private void setGameState(GameState gameState) {
        this.getClusterCommunicationsController().setGameState(gameState);
    }

    private GameState getGameState() {
        return this.getClusterCommunicationsController().getGameState();
    }

    private void startEndGame() {
        this.getClusterCommunicationsController().startEndGame();
    }

    // ! Getters and Setters

    /**
     * @return the idsAndVotes
     */
    public HashMap<Integer, Integer> getIdsAndVotes() {
        return idsAndVotes;
    }

    /**
     * @param idsAndVotes the idsAndVotes to set
     */
    public void setIdsAndVotes(HashMap<Integer, Integer> idsAndVotes) {
        this.idsAndVotes = idsAndVotes;
    }

    /**
     * @return the timeToWaitForVotesFromConfig
     */
    public int getTimeToWaitForVotesFromConfig() {
        return timeToWaitForVotesFromConfig;
    }

    /**
     * @param timeToWaitForVotesFromConfig the timeToWaitForVotesFromConfig to set
     */
    public void setTimeToWaitForVotesFromConfig(int timeToWaitForVotesFromConfig) {
        this.timeToWaitForVotesFromConfig = timeToWaitForVotesFromConfig;
    }

    /**
     * @return the clusterCommunicationsController
     */
    public ClusterCommunicationsController getClusterCommunicationsController() {
        return clusterCommunicationsController;
    }

    /**
     * @param clusterCommunicationsController the clusterCommunicationsController to
     *                                        set
     */
    public void setClusterCommunicationsController(ClusterCommunicationsController clusterCommunicationsController) {
        this.clusterCommunicationsController = clusterCommunicationsController;
    }

}
