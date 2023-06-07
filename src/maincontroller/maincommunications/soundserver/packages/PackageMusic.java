package maincontroller.maincommunications.soundserver.packages;

public class PackageMusic implements PackageSoundServerConnection {

    // ! Getters and Setters
    private MusicType musicType;

    // ! Constructor
    public PackageMusic(MusicType musicType) {
        this.setMusicType(musicType);
    }

    // ! Getters and Setters

    public MusicType getMusicType() {
        return musicType;
    }

    public void setMusicType(MusicType musicType) {
        this.musicType = musicType;
    }
}
