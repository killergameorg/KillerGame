package maincontroller.maincommunications.soundserver.packages;

public enum MusicType {
    COMBAT("combat.mp3"),
    CALMA("calma.wav");

    // MUSIC3(SoundFile.SOUND3);
    private final String fileName;

    MusicType(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
