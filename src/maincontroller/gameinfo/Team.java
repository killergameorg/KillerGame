package maincontroller.gameinfo;

import java.io.Serializable;
import java.util.HashMap;

public class Team implements Serializable {

    // ! Attributes

    // TODO: Cuando se creen los 2 Teams entiendo que el el modelo, se deberá añadir al HashMap
    private static HashMap<Team, Integer> accountsForTeams = new HashMap<Team, Integer>();

    private TeamName teamName;
    private int score;

    // ! Constructor
    public Team(TeamName teamName) {
        this.setTeamName(teamName);
        this.setScore(0);
    }

    // ! Methods
    public static Team searchTeamWithMinAccounts() {

        Team teamWithMinAccounts = null;

        for (Team team : Team.getAccountsForTeams().keySet()) {
            if (teamWithMinAccounts == null) {
                teamWithMinAccounts = team;

            } else if (Team.getAccountsForTeams().get(team) < Team.getAccountsForTeams().get(teamWithMinAccounts)) {
                teamWithMinAccounts = team;
            }
        }

        return teamWithMinAccounts;
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

    /**
     * @return the accountsForTeams
     */
    public static HashMap<Team, Integer> getAccountsForTeams() {
        return accountsForTeams;
    }

    /**
     * @param accountsForTeams the accountsForTeams to set
     */
    public static void setAccountsForTeams(HashMap<Team, Integer> accountsForTeams) {
        Team.accountsForTeams = accountsForTeams;
    }

}
