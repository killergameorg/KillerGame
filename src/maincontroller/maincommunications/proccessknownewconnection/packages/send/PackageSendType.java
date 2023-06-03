package maincontroller.maincommunications.proccessknownewconnection.packages.send;

import maincontroller.maincommunications.proccessknownewconnection.packages.PackageProccessKnowNewConnection;

public class PackageSendType implements PackageProccessKnowNewConnection {

    // ! Attributes
    private TypeNewConnection typeNewConnection;

    // ! Constructor
    public PackageSendType(TypeNewConnection typeNewConnection) {
        this.setTypeNewConnection(typeNewConnection);
    }

    // ! Getters and Setters

    /**
     * @return the typeNewConnection
     */
    public TypeNewConnection getTypeNewConnection() {
        return typeNewConnection;
    }

    /**
     * @param typeNewConnection the typeNewConnection to set
     */
    public void setTypeNewConnection(TypeNewConnection typeNewConnection) {
        this.typeNewConnection = typeNewConnection;
    }

}
