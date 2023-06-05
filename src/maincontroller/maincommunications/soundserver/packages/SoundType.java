package maincontroller.maincommunications.soundserver.packages;

public enum SoundType {
    EXPLOSION("explosion.mp3"),
    LASER("laser.mp3");

    private final String fileName;

    SoundType(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
