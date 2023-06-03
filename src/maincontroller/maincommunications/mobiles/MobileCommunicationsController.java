package maincontroller.maincommunications.mobiles;

import java.util.ArrayList;

import maincontroller.gameinfo.Account;
import maincontroller.gameinfo.GameState;
import maincontroller.gameinfo.Team;
import maincontroller.maincommunications.MainGameCommunications;
import maincontroller.maincommunications.mobiles.packages.PackageJoystick;
import maincontroller.maincommunications.mobiles.packages.PackageMobileCommunications;
import maincontroller.maincommunications.mobiles.packages.PackageMobileSetAttributes;
import maincontroller.maincommunications.mobiles.packages.PackageShipMobile;
import maincontroller.maincommunications.typesofconnections.Mobile;

public class MobileCommunicationsController {

    // ! Attributes

    private MainGameCommunications mainGameCommunications;

    private ArrayList<Mobile> mobiles;
    private int idMobileMaster;

    // ! Constructor
    public MobileCommunicationsController(MainGameCommunications mainGameCommunications) {
        this.setMainGameCommunications(mainGameCommunications);

        this.setMobiles(new ArrayList<Mobile>());
    }

    // ! Methods

    public void onIncomingMessage(String ip, PackageMobileCommunications packageMobileCommunications) {

        if (packageMobileCommunications instanceof PackageMobileSetAttributes) {
            this.searchAndSetMobile((PackageMobileSetAttributes) packageMobileCommunications);

        } else if (packageMobileCommunications instanceof PackageShipMobile) {
            PackageShipMobile packageShipMobile = (PackageShipMobile) packageMobileCommunications;

            if (

            (this.getGameState() == GameState.GAME &&
                    packageShipMobile.getMessage() instanceof PackageJoystick) ||

                    (this.getGameState() == GameState.LOBBY &&
                            this.isMobileMaster(packageShipMobile.getAccountId()) &&
                            packageShipMobile.getMessage() instanceof PackageJoystick)

            ) {

                PackageJoystick packageJoystick = (PackageJoystick) packageShipMobile.getMessage();

                this.notifyJoystick(
                        packageShipMobile.getAccountId(),
                        packageJoystick

                );

            }

        }

    }

    public void addMobileInLobbyMaster(String ip) {
        Mobile mobile = this.addMobile(ip, Team.searchTeamWithMinAccounts());
        this.notifyNumberOfMobiles(this.getMobiles().size());
        this.sendFloodMobile(mobile);
    }

    private void sendFloodMobile(Mobile mobile) {

        PackageMobileSetAttributes packageMobileSetAttributes = new PackageMobileSetAttributes(
                mobile.getIp(),
                mobile.getAccount().getIdAccount(),
                mobile.getAccount().isMaster(),
                mobile.getAccount().getTeam()

        );

        // TODO: Tengo que avisar al departamento de móvil que les llegará este objeto,
        // TODO: tienen que comprobar si es su misma IP
        this.sendFlood(packageMobileSetAttributes);

    }

    public synchronized Mobile addMobile(String ip) {
        Mobile mobile = new Mobile(ip);
        this.getMobiles().add(mobile);
        return mobile;
    }

    private synchronized Mobile addMobile(String ip, Team team) {

        Mobile mobile = new Mobile(ip, team);
        this.getMobiles().add(mobile);
        Team.getAccountsForTeams().put(
                team,
                Team.getAccountsForTeams().get(team) + 1

        );

        if (this.getMobiles().size() == 1) {
            mobile.getAccount().setMaster(true);
            this.setIdMobileMaster(mobile.getAccount().getIdAccount());

        } else {
            mobile.getAccount().setMaster(false);
        }

        return mobile;
    }

    private void searchAndSetMobile(PackageMobileSetAttributes packageMobileSetAttributes) {

        for (int i = 0; i < this.getMobiles().size(); i++) {
            Mobile mobile = this.getMobiles().get(i);

            if (mobile.getIp() == packageMobileSetAttributes.getIp()) {

                mobile.setAccount(new Account(
                        packageMobileSetAttributes.getId(),
                        packageMobileSetAttributes.isMaster(),
                        packageMobileSetAttributes.getTeam()

                ));

            }
        }

    }

    public boolean removeConnection(String ip) {

        boolean removed = false;
        int i = 0;
        while (i < this.getMobiles().size() && !removed) {
            if (this.getMobiles().get(i).getIp().equals(ip)) {
                this.getMobiles().remove(i);
                removed = true;
            }
            i++;
        }

        return removed;
    }

    // ! Linking methods

    private void sendFlood(PackageMobileCommunications packageMobileCommunications) {
        this.getMainGameCommunications().sendFlood(packageMobileCommunications);
    }

    public void notifyNumberOfMobiles(int numberOfMobiles) {
        this.getMainGameCommunications().notifyNumberOfMobiles(numberOfMobiles);
    }

    public void notifyNumberOfMobiles() {
        this.getMainGameCommunications().notifyNumberOfMobiles(
                this.getMobiles().size()

        );
    }

    private GameState getGameState() {
        return this.getMainGameCommunications().getGameState();
    }

    public boolean isMobileMaster(int idAccount) {
        return this.getIdMobileMaster() == idAccount;
    }

    private void notifyJoystick(int idAccount, PackageJoystick packageJoystick) {
        this.getMainGameCommunications().notifyJoystick(idAccount, packageJoystick);
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
     * @return the mobiles
     */
    public ArrayList<Mobile> getMobiles() {
        return mobiles;
    }

    /**
     * @param mobiles the mobiles to set
     */
    public void setMobiles(ArrayList<Mobile> mobiles) {
        this.mobiles = mobiles;
    }

    /**
     * @return the idMobileMaster
     */
    public int getIdMobileMaster() {
        return idMobileMaster;
    }

    /**
     * @param idMobileMaster the idMobileMaster to set
     */
    public void setIdMobileMaster(int idMobileMaster) {
        this.idMobileMaster = idMobileMaster;
    }

}
