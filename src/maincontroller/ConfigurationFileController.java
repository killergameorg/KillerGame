package maincontroller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationFileController {

    // ! Attributes
    private String path;

    private int id;
    private int sleepWhileKnowConnections;
    private int timeToWaitForVotesFromConfig;

    // ! Constructor
    public ConfigurationFileController(String path) throws FileNotFoundException, IOException {
        this.setPath(path);

        this.loadConfigurationFile();
    }

    // ! Methods

    private void loadConfigurationFile() throws FileNotFoundException, IOException {
        Properties properties = new Properties();

        properties.load(new FileInputStream(new File(this.getPath())));

        this.setId(Integer.parseInt(properties.getProperty("id")));
        this.setSleepWhileKnowConnections(Integer.parseInt(properties.getProperty("sleepWhileKnowConnections")));
        this.setTimeToWaitForVotesFromConfig(Integer.parseInt(properties.getProperty("timeToWaitForVotesFromConfig")));

    }

    // ! Getters and Setters
    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

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

    /**
     * @return the timeToWaitForVotesFromConfig
     */
    public int getTimeToWaitForVotesFromConfig() {
        return timeToWaitForVotesFromConfig;
    }

    /**
     * @param timeToWaitForVotesFromConfig the timeToWaitForVotesFromConfig to set
     */
    public void setTimeToWaitForVotesFromConfig(int timeToWaitForVotesFromConfig) {
        this.timeToWaitForVotesFromConfig = timeToWaitForVotesFromConfig;
    }

}
