package src;

import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

import java.nio.file.Path;

public class MusicPlayer {
    private BasicPlayer player;

    private static MusicPlayer instance;

    private MusicPlayer(){
        player = new BasicPlayer();
    }

    public static MusicPlayer getInstance(){
        if(instance == null){
            instance = new MusicPlayer();
        }
        return instance;
    }

    public void play() throws BasicPlayerException {
        player.play();
    }

    public void stop() throws BasicPlayerException {
        player.stop();
    }

    public void loadFile(Path file) throws BasicPlayerException {
        player.open(file.toFile());
    }
}
