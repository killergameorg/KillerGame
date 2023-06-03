package maincontroller.maincommunications.soundserver.packages;

public class PackageSound implements PackageSoundServerConnection{

    // ! Getters and Setters
    private SoundType soundType;

    // ! Constructor
    public PackageSound(SoundType soundType) {
        this.setSoundType(soundType);
    }

    // ! Getters and Setters

    public SoundType getSoundType() {
        return soundType;
    }

    public void setSoundType(SoundType soundType) {
        this.soundType = soundType;
    }
}
