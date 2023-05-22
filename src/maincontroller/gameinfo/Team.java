package maincontroller.gameinfo;

public class Team {

    // ! Attributes
    private TeamName teamName;
    private int score;

    // ! Constructor
    public Team(TeamName teamName) {
        this.setTeamName(teamName);
        this.setScore(0);
    }

    // ! Getters and Setters
    /**
     * @return the teamName
     */
    public TeamName getTeamName() {
        return teamName;
    }

    /**
     * @param teamName the teamName to set
     */
    public void setTeamName(TeamName teamName) {
        this.teamName = teamName;
    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

}
