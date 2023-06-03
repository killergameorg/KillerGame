package maincontroller.maincommunications.proccessknownewconnection.packages;

import maincontroller.maincommunications.proccessknownewconnection.TypeNewConnection;

public class PackageSendType extends PackageKnowNewConnection {

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
