package maincontroller.maincommunications.soundserver;

import communications.ConnectionController;
import maincontroller.maincommunications.MainGameCommunications;
import maincontroller.maincommunications.soundserver.packages.MusicType;
import maincontroller.maincommunications.soundserver.packages.SoundType;

// TODO: Si da tiempo quiero traer el atributo del SoundServer aquí
public class SoundServerConnectionController {

    // ! Attributes
    private MainGameCommunications mainGameCommunications;

    // ! Constructor
    public SoundServerConnectionController(MainGameCommunications mainGameCommunications) {
        this.setMainGameCommunications(mainGameCommunications);
    }

    // ! Methods

    public void playMusic(MusicType musicType) {
        this.sendPrivate(musicType.getFileName());
    }

    public void playSound(SoundType soundType) {
        this.sendPrivate(soundType.getFileName());
    }

    private void sendPrivate(Object object) {
        this.getConnectionController().sendPrivate(
                this.getMainGameCommunications().getServerSound().getIp(),
                object

        );
    }

    public void notifyAllStartGame() {
        // TODO: Preguntar a Sergio si quiere que le avise de otra manera
        this.playMusic(MusicType.COMBAT);
    }

    public void onIncomingMessage(String ip, Object object) {

        // TODO: Preguntar a Sergio si me va a enviar algo en algún momento (OJO, esto
        // TODO: puedo usarlo para el envío de atributos en el proceso de conocerse
        // entre
        // TODO: conexiones)

    }

    private ConnectionController getConnectionController() {
        return this.getMainGameCommunications().getConnectionController();
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

}
