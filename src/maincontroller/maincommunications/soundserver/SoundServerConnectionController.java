package maincontroller.maincommunications.soundserver;

import maincontroller.maincommunications.MainGameCommunications;
import maincontroller.maincommunications.soundserver.packages.MusicType;
import maincontroller.maincommunications.soundserver.packages.PackageMusic;
import maincontroller.maincommunications.soundserver.packages.PackageSound;
import maincontroller.maincommunications.soundserver.packages.PackageSoundServerConnection;
import maincontroller.maincommunications.soundserver.packages.SoundType;
import maincontroller.maincommunications.typesofconnections.SoundServer;

public class SoundServerConnectionController {

    // ! Attributes
    private MainGameCommunications mainGameCommunications;

    private SoundServer soundServer;

    // ! Constructor
    public SoundServerConnectionController(MainGameCommunications mainGameCommunications) {
        this.setMainGameCommunications(mainGameCommunications);
    }

    // ! Methods
    public void playMusic(MusicType musicType) {
        this.sendPrivate(
                this.getSoundServer().getIp(),
                new PackageMusic(musicType)

        );
    }

    public void playSound(SoundType soundType) {
        this.sendPrivate(
                this.getSoundServer().getIp(),
                new PackageSound(soundType)

        );
    }

    public boolean removeConnection(String ip) {

        boolean found = false;

        if (this.getSoundServer().getIp().equals(ip)) {
            this.setSoundServer((SoundServer) null);
            found = true;
        }

        return found;
    }

    // ! Linking methods

    private void sendPrivate(String ip, PackageSoundServerConnection message) {
        this.getMainGameCommunications().sendPrivate(ip, message);
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
     * @return the soundServer
     */
    public SoundServer getSoundServer() {
        return soundServer;
    }

    /**
     * @param soundServer the soundServer to set
     */
    public void setSoundServer(SoundServer soundServer) {
        this.soundServer = soundServer;
    }

    public void setSoundServer(String ip) {
        this.soundServer = new SoundServer(ip);
    }

}
