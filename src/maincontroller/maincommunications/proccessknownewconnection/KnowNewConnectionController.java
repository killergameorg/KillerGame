package maincontroller.maincommunications.proccessknownewconnection;

import java.util.ArrayList;

import communications.ConnectionController;
import maincontroller.gameinfo.Team;
import maincontroller.maincommunications.ClusterComputer;
import maincontroller.maincommunications.MainGameCommunications;
import maincontroller.maincommunications.Mobile;
import maincontroller.maincommunications.ServerSound;
import maincontroller.maincommunications.mobiles.packages.GameStateTypes;
import maincontroller.maincommunications.mobiles.packages.PackageGameState;
import maincontroller.maincommunications.mobiles.packages.PackageShipMobile;
import maincontroller.maincommunications.proccessknownewconnection.packages.OptionsPackageAsk;
import maincontroller.maincommunications.proccessknownewconnection.packages.PackageAsk;
import maincontroller.maincommunications.proccessknownewconnection.packages.PackageKnowNewConnection;
import maincontroller.maincommunications.proccessknownewconnection.packages.PackageSendClusterAttributes;
import maincontroller.maincommunications.proccessknownewconnection.packages.PackageSendType;

// TODO: Tengo que intentar conectarme con el alrededor al principio
public class KnowNewConnectionController {

    // ! Attributes
    private MainGameCommunications mainGameCommunications;

    private ArrayList<String> knownConnections;

    // ! Constructor
    public KnowNewConnectionController(MainGameCommunications mainGameCommunications) {
        this.setMainGameCommunications(mainGameCommunications);
        this.setKnownConnections(new ArrayList<String>());
    }

    // ! Methods

    public void knowNewConnection(String ip) {
        this.getKnownConnections().add(ip);

        this.getConnectionController().sendPrivate(
                ip,
                new PackageAsk(OptionsPackageAsk.TYPE_DEVICE)

        );
    }

    // TODO: Refactor de este método usando un HashMap
    public void onIncomingMessage(String ip, PackageKnowNewConnection message) {

        if (message instanceof PackageAsk) {
            PackageAsk packageAsk = (PackageAsk) message;

            if (packageAsk.getOptionsPackageAsk() == OptionsPackageAsk.TYPE_DEVICE) {
                this.getConnectionController().sendPrivate(
                        ip,
                        new PackageSendType(TypeNewConnection.CLUSTER)

                );

            } else if (packageAsk.getOptionsPackageAsk() == OptionsPackageAsk.CLUSTER_ATTRIBUTES) {

                this.getConnectionController().sendPrivate(
                        ip,
                        new PackageSendClusterAttributes(this.getMyId())

                );
            }

        } else if (message instanceof PackageSendType) {

            PackageSendType packageSendType = (PackageSendType) message;

            if (packageSendType.getTypeNewConnection() == TypeNewConnection.CLUSTER) {
                this.getConnectionController().sendPrivate(
                        ip,
                        new PackageAsk(OptionsPackageAsk.CLUSTER_ATTRIBUTES)

                );

            } else if (packageSendType.getTypeNewConnection() == TypeNewConnection.MOBILE) {

                Mobile mobile = this.createMobile(ip);
                this.addMobiles(mobile);

                this.getConnectionController().sendPrivate(
                        ip,
                        new PackageShipMobile(
                                mobile.getAccount().getIdAccount(),
                                mobile.getAccount().isMaster(),
                                new PackageGameState(GameStateTypes.LOBBY)

                        )

                );

                this.getKnownConnections().remove(ip);

            } else if (packageSendType.getTypeNewConnection() == TypeNewConnection.SERVER_SOUND) {

                this.setServerSound(new ServerSound(ip));

                // TODO: Tengo que hablar con Sergio por si quisiera que le enviara algún
                // TODO: atributo al principio de la conexión o no

                this.getKnownConnections().remove(ip);
            }

        } else if (message instanceof PackageSendClusterAttributes) {

            this.addClusterComputer(this.createClusterComputer(
                    (PackageSendClusterAttributes) message,
                    ip

            ));

            this.getKnownConnections().remove(ip);

        }

    }

    private ConnectionController getConnectionController() {
        return this.getMainGameCommunications().getConnectionController();
    }

    private int getMyId() {
        return this.getMainGameCommunications().getMyId();
    }

    private ClusterComputer createClusterComputer(
            PackageSendClusterAttributes packageSendClusterAttributes,
            String ip

    ) {

        return new ClusterComputer(
                packageSendClusterAttributes.getId(),
                ip

        );
    }

    private Mobile createMobile(String ip) {
        return new Mobile(ip, Team.searchTeamWithMinAccounts());
    }

    private void addClusterComputer(ClusterComputer clusterComputer) {
        this.getMainGameCommunications().addClusterComputer(clusterComputer);
    }

    private void addMobiles(Mobile mobile) {
        this.getMainGameCommunications().addMobile(mobile);
    }

    private void setServerSound(ServerSound serverSound) {
        this.getMainGameCommunications().setServerSound(serverSound);
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
     * @return the knownConnections
     */
    public ArrayList<String> getKnownConnections() {
        return knownConnections;
    }

    /**
     * @param knownConnections the knownConnections to set
     */
    public void setKnownConnections(ArrayList<String> knownConnections) {
        this.knownConnections = knownConnections;
    }

}
