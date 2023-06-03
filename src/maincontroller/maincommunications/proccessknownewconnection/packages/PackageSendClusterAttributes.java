package maincontroller.maincommunications.proccessknownewconnection.packages;

public class PackageSendClusterAttributes extends PackageKnowNewConnection {

    // ! Attributes
    private int id;

    // ! Constructor
    public PackageSendClusterAttributes(int id) {
        this.setId(id);
    }

    // ! Getters and Setters

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

}
