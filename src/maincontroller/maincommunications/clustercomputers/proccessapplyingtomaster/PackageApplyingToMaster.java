package maincontroller.maincommunications.clustercomputers.proccessapplyingtomaster;

public class PackageApplyingToMaster {

    // ! Attributes
    private int id;
    private int valueToVote;

    // ! Constructor
    public PackageApplyingToMaster(int id, int valueToVote) {
        this.setId(id);
        this.setValueToVote(valueToVote);
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

    /**
     * @return the valueToVote
     */
    public int getValueToVote() {
        return valueToVote;
    }

    /**
     * @param valueToVote the valueToVote to set
     */
    public void setValueToVote(int valueToVote) {
        this.valueToVote = valueToVote;
    }

}
