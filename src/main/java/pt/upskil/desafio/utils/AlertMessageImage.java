package pt.upskil.desafio.utils;

public enum AlertMessageImage {
    // Alert message image
    FAILURE("/imagens/img-error.png", "/imagens/minions-error-audio1.mp3"),
    SUCCESS("/imagens/minion_success.png", "/imagens/minions-banana-song.mp3");

    private final String imageURL;
    private final String audioURL;


    AlertMessageImage(String imageURL, String audioURL) {
        this.imageURL = imageURL;
        this.audioURL= audioURL;
    }

    public String getImageURL() {
        return imageURL;
    }
    public String getAudioURL() {
        return audioURL;
    }

}
