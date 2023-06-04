package maincontroller.maincommunications.clustercomputers;

import java.util.ArrayList;

import maincontroller.gameinfo.GameState;
import maincontroller.maincommunications.MainGameCommunications;
import maincontroller.maincommunications.clustercomputers.packages.PackageClusterCommunications;
import maincontroller.maincommunications.clustercomputers.proccessapplyingtomaster.ApplyingToMasterController;
import maincontroller.maincommunications.clustercomputers.proccessapplyingtomaster.packages.PackageApplyingToMaster;
import maincontroller.maincommunications.typesofconnections.ClusterComputer;

public class ClusterCommunicationsController {

    // ! Attributes
    private MainGameCommunications mainGameCommunications;

    private ApplyingToMasterController applyingToMasterController;

    private ArrayList<ClusterComputer> clusterComputers;

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

        this.setClusterComputers(new ArrayList<ClusterComputer>());

    }

    // ! Methods

    public void onIncomingMessage(
            String ip,
            PackageClusterCommunications packkPackageClusterCommunications

    ) {

        if (packkPackageClusterCommunications instanceof PackageApplyingToMaster) {
            this.getApplyingToMasterController().onIncomingMessage(
                    ip,
                    (PackageApplyingToMaster) packkPackageClusterCommunications

            );

        }
    }

    public boolean removeConnection(String ip) {

        boolean removed = false;
        int i = 0;
        while (i < this.getClusterComputers().size() && !removed) {
            if (this.getClusterComputers().get(i).getIp().equals(ip)) {
                this.getClusterComputers().remove(i);
                removed = true;
            }
            i++;
        }

        return removed;
    }

    // ! Linking methods

    public void addClusterComputer(int id, String ip) {
        this.getClusterComputers().add(new ClusterComputer(id, ip));
    }

    public void tryApplyingToMaster() {
        this.getApplyingToMasterController().tryApplyingToMaster();
    }

    public void sendFlood(PackageClusterCommunications packageClusterCommunications) {
        this.getMainGameCommunications().sendFlood(packageClusterCommunications);
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

    public void setGameState(GameState gameState) {
        this.getMainGameCommunications().setGameState(gameState);
    }

    // ! Getters and Setters

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

    /**
     * @return the clusterComputers
     */
    public ArrayList<ClusterComputer> getClusterComputers() {
        return clusterComputers;
    }

    /**
     * @param clusterComputers the clusterComputers to set
     */
    public void setClusterComputers(ArrayList<ClusterComputer> clusterComputers) {
        this.clusterComputers = clusterComputers;
    }

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

}
