package maincontroller.maincommunications.clustercomputers;

import lobby.lobbyModel.GameRules;
import maincontroller.maincommunications.MainGameCommunications;
import maincontroller.maincommunications.clustercomputers.proccessapplyingtomaster.ApplyingToMasterController;
import maincontroller.maincommunications.clustercomputers.proccessapplyingtomaster.PackageApplyingToMaster;
import maincontroller.maincommunications.packages.PackageStartGame;

public class ClusterCommunicationsController {

    // ! Attributes
    private MainGameCommunications mainGameCommunications;

    private ApplyingToMasterController applyingToMasterController;

    // ! Constructor
    public ClusterCommunicationsController(
            MainGameCommunications mainGameCommunications,
            int timeToWaitForVotesFromConfig

    ) {

        this.setMainGameCommunications(mainGameCommunications);

        this.setApplyingToMasterController(
                new ApplyingToMasterController(
                        this,
                        timeToWaitForVotesFromConfig

                )

        );
    }

    // ! Methods

    public void tryApplyingToMaster() {
        this.getApplyingToMasterController().tryApplyingToMaster();
    }

    public void onIncomingMessage(
            String ip,
            PackageApplyingToMaster packageApplyingToMaster

    ) {
        this.getApplyingToMasterController().onIncomingMessage(
                ip,
                packageApplyingToMaster

        );
    }

    public void sendBroadcastClusterComputers(Object object) {
        this.getMainGameCommunications().sendBroadcastClusterComputers(object);
    }

    public int getMyId() {
        return this.getMainGameCommunications().getMyId();
    }

    public void startLobby() {
        this.getMainGameCommunications().startLobby();
    }

    public void setMaster() {
        this.getMainGameCommunications().setMaster();
    }

    public void setSlave() {
        this.getMainGameCommunications().setSlave();
    }

    public void notifyAllStartGame(GameRules gameRules) {
        this.sendBroadcastClusterComputers(new PackageStartGame(gameRules));
    }

    // ! Getters and Setters

    /**
     * @return the applyingToMasterController
     */
    public ApplyingToMasterController getApplyingToMasterController() {
        return applyingToMasterController;
    }

    /**
     * @param applyingToMasterController the applyingToMasterController to set
     */
    public void setApplyingToMasterController(ApplyingToMasterController applyingToMasterController) {
        this.applyingToMasterController = applyingToMasterController;
    }

    /**
     * @return the mainGameCommunications
     */
    public MainGameCommunications getMainGameCommunications() {
        return mainGameCommunications;
    }

    /**
     * @param mainGameCommunications the mainGameCommunications to set
     */
    public void setMainGameCommunications(MainGameCommunications mainGameCommunications) {
        this.mainGameCommunications = mainGameCommunications;
    }

}
