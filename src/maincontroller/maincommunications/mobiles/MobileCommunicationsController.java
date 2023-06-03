package maincontroller.maincommunications.mobiles;

import java.util.ArrayList;

import maincontroller.gameinfo.Account;
import maincontroller.gameinfo.GameState;
import maincontroller.maincommunications.MainGameCommunications;
import maincontroller.maincommunications.Mobile;
import maincontroller.maincommunications.mobiles.packages.PackageJoystick;
import maincontroller.maincommunications.mobiles.packages.PackageShipInfo;
import maincontroller.maincommunications.mobiles.packages.PackageShipMobile;
import visual.Ship;

// TODO: Si diese tiempo me gustaría pasar aquí el ArrayList de Accounts y todo lo relacionado con ellos (Métodos y demás)
public class MobileCommunicationsController {

    // ! Attributes
    private MainGameCommunications mainGameCommunications;

    // ! Constructor
    public MobileCommunicationsController(MainGameCommunications mainGameCommunications) {
        this.setMainGameCommunications(mainGameCommunications);
    }

    // ! Methods

    public void sendPrivateMobile(String ip, PackageShipMobile packageShipMobile) {
        this.getMainGameCommunications().sendPrivateMobile(ip, packageShipMobile);
    }

    public void onIncomingMessage(String ip, PackageShipMobile packageShipMobile) throws Exception {

        Account account = this.getAccountWithAccountId(packageShipMobile.getAccountId());

        if (this.getGameState() == GameState.LOBBY &&
                this.checkIfMaster(account)) {

            if (packageShipMobile.getMessage() instanceof PackageJoystick) {
                PackageJoystick packageJoystick = (PackageJoystick) packageShipMobile.getMessage();

                this.getMainGameCommunications().notifyJoystick(
                        account,
                        packageJoystick

                );

            }

        } else if (packageShipMobile.getMessage() instanceof PackageJoystick) {
            PackageJoystick packageJoystick = (PackageJoystick) packageShipMobile.getMessage();

            this.getMainGameCommunications().notifyJoystick(
                    account,
                    packageJoystick

            );
        }

    }

    private boolean checkIfMaster(Account account) {
        ArrayList<Mobile> mobiles = this.getMainGameCommunications().getMobiles();

        boolean masterFound = false;
        int i = 0;
        while (i < mobiles.size() && !masterFound) {
            Mobile mobile = mobiles.get(i);

            if (mobile.getAccount().getIdAccount() == account.getIdAccount()) {
                masterFound = true;
            }

            i++;
        }

        return masterFound;
    }

    public void searchNewMaster() {
        ArrayList<Mobile> mobiles = this.getMainGameCommunications().getMobiles();

        Mobile mobileMaster = null;
        int masterId = -1;

        for (int i = 0; i < mobiles.size(); i++) {
            Mobile mobile = mobiles.get(i);

            if (mobileMaster == null && masterId == -1) {
                masterId = mobile.getAccount().getIdAccount();
                mobileMaster = mobile;
            } else if (mobile.getAccount().getIdAccount() < masterId) {
                masterId = mobile.getAccount().getIdAccount();
                mobileMaster = mobile;
            }

        }

        if (mobileMaster != null && masterId != -1) {
            mobileMaster.getAccount().setMaster(true);
            // TODO: Informar al móvil que ahora es master
        }

    }

    private GameState getGameState() {
        return this.getMainGameCommunications().getGameState();
    }

    private Account getAccountWithAccountId(int accountId) throws Exception {
        ArrayList<Mobile> mobiles = this.getMainGameCommunications().getMobiles();

        Account account = null;

        int i = 0;
        while (i < mobiles.size() && account == null) {
            Mobile mobile = mobiles.get(i);

            if (mobile.getAccount().getIdAccount() == accountId) {
                account = mobile.getAccount();
            }

            i++;
        }

        if (account == null) {
            throw new Exception("Account not found MobileCommunicationsController.getAccountWithId()");
        }

        return account;
    }

    private Mobile getMobileWithAccountId(int accountId) throws Exception {
        ArrayList<Mobile> mobiles = this.getMainGameCommunications().getMobiles();

        Mobile mobile = null;

        int i = 0;
        while (i < mobiles.size() && mobile == null) {
            Mobile mobileAux = mobiles.get(i);

            if (mobileAux.getAccount().getIdAccount() == accountId) {
                mobile = mobileAux;
            }

            i++;
        }

        if (mobile == null) {
            throw new Exception("Mobile not found MobileCommunicationsController.getMobileWithAccountId()");
        }

        return mobile;
    }

    public void decreaseLifeShip(Ship ship, int lifeDowngrade) throws Exception {

        Mobile mobile = this.getMobileWithAccountId(ship.getAccountId());

        this.sendPrivateMobile(
                mobile.getIp(),
                new PackageShipMobile(
                        mobile.getAccount().getIdAccount(),
                        mobile.getAccount().isMaster(),
                        new PackageShipInfo(
                                ship.getLife() - lifeDowngrade,
                                mobile.getAccount().getTeam()

                        )

                )

        );

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

}
