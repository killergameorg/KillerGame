package maincontroller.maincommunications.proccessknownewconnection;

import java.util.ArrayList;

import maincontroller.maincommunications.MainGameCommunications;
import maincontroller.maincommunications.proccessknownewconnection.packages.PackageProccessKnowNewConnection;
import maincontroller.maincommunications.proccessknownewconnection.packages.ask.OptionsPackageAsk;
import maincontroller.maincommunications.proccessknownewconnection.packages.ask.PackageAsk;
import maincontroller.maincommunications.proccessknownewconnection.packages.send.PackageSendClusterAttributes;
import maincontroller.maincommunications.proccessknownewconnection.packages.send.PackageSendNewMobileToLobbyMaster;
import maincontroller.maincommunications.proccessknownewconnection.packages.send.PackageSendType;
import maincontroller.maincommunications.proccessknownewconnection.packages.send.TypeNewConnection;

public class KnowNewConnectionController {

    // ! Attributes
    private MainGameCommunications mainGameCommunications;

    private ArrayList<String> knownConnections;

    private int sleepWhileKnowConnections;

    // ! Constructor
    public KnowNewConnectionController(
            MainGameCommunications mainGameCommunications,
            int sleepWhileKnowConnections

    ) {

        this.setMainGameCommunications(mainGameCommunications);

        this.setKnownConnections(new ArrayList<String>());

        this.setSleepWhileKnowConnections(sleepWhileKnowConnections);
    }

    // ! Methods

    public void knowNewConnection(String ip) {
        this.getKnownConnections().add(ip);

        this.sendPrivate(
                ip,
                new PackageAsk(OptionsPackageAsk.TYPE_DEVICE)

        );
    }

    public void onIncomingMessage(String ip, PackageProccessKnowNewConnection message) {

        if (message instanceof PackageAsk) {
            PackageAsk packageAsk = (PackageAsk) message;

            if (packageAsk.getOptionsPackageAsk() == OptionsPackageAsk.TYPE_DEVICE) {
                this.sendPrivate(
                        ip,
                        new PackageSendType(TypeNewConnection.CLUSTER)

                );

            } else if (packageAsk.getOptionsPackageAsk() == OptionsPackageAsk.CLUSTER_ATTRIBUTES) {

                this.sendPrivate(
                        ip,
                        new PackageSendClusterAttributes(this.getMyId())

                );
            }

        } else if (message instanceof PackageSendType) {

            PackageSendType packageSendType = (PackageSendType) message;

            if (packageSendType.getTypeNewConnection() == TypeNewConnection.SERVER_SOUND) {

                this.setSoundServer(ip);

                // TODO: Tengo que hablar con Sergio por si quisiera que le enviara algún
                // TODO: atributo al principio de la conexión o no

                this.getKnownConnections().remove(ip);

            } else if (packageSendType.getTypeNewConnection() == TypeNewConnection.CLUSTER) {
                this.sendPrivate(
                        ip,
                        new PackageAsk(OptionsPackageAsk.CLUSTER_ATTRIBUTES)

                );

            } else if (packageSendType.getTypeNewConnection() == TypeNewConnection.MOBILE) {

                if (this.iAmMaster()) {
                    this.addMobileInLobbyMaster(ip);

                    // TODO: Aquí ya estoy en el Lobby Master, tendré que settear el ID de la
                    // TODO: Account y hacer un Float con el, tanto el móvil como el PC que tenga la
                    // TODO: account tienen que recibir ese mensaje y guardarlo como atributo

                } else {
                    this.addMobile(ip);
                    this.sendFlood(new PackageSendNewMobileToLobbyMaster(ip));
                }

                // Only remove the connection if ip is in the known connections
                this.getKnownConnections().remove(ip);

            }

        } else if (message instanceof PackageSendClusterAttributes) {

            PackageSendClusterAttributes packageSendClusterAttributes = (PackageSendClusterAttributes) message;

            this.addClusterComputer(
                    packageSendClusterAttributes.getId(),
                    ip

            );

            this.getKnownConnections().remove(ip);

        } else if (message instanceof PackageSendNewMobileToLobbyMaster) {

            PackageSendNewMobileToLobbyMaster packageSendNewMobileToLobbyMaster = (PackageSendNewMobileToLobbyMaster) message;

            if (this.iAmMaster()) {
                this.addMobileInLobbyMaster(packageSendNewMobileToLobbyMaster.getIp());
            }

        }

    }

    // ! Linking methods

    private void sendPrivate(String ip, PackageProccessKnowNewConnection message) {
        this.getMainGameCommunications().sendPrivate(ip, message);
    }

    private void sendFlood(PackageProccessKnowNewConnection message) {
        this.getMainGameCommunications().sendFlood(message);
    }

    private int getMyId() {
        return this.getMainGameCommunications().getMyId();
    }

    private void setSoundServer(String ip) {
        this.getMainGameCommunications().setSoundServer(ip);
    }

    private void addMobile(String ip) {
        this.getMainGameCommunications().addMobile(ip);
    }

    private void addClusterComputer(int id, String ip) {
        this.getMainGameCommunications().addClusterComputer(id, ip);
    }

    private boolean iAmMaster() {
        return this.getMainGameCommunications().iAmMaster();
    }

    private void addMobileInLobbyMaster(String ip) {
        this.getMainGameCommunications().addMobileInLobbyMaster(ip);
    }

    public boolean removeConnection(String ip) {
        return this.getKnownConnections().remove(ip);
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

    /**
     * @return the sleepWhileKnowConnections
     */
    public int getSleepWhileKnowConnections() {
        return sleepWhileKnowConnections;
    }

    /**
     * @param sleepWhileKnowConnections the sleepWhileKnowConnections to set
     */
    public void setSleepWhileKnowConnections(int sleepWhileKnowConnections) {
        this.sleepWhileKnowConnections = sleepWhileKnowConnections;
    }

}
