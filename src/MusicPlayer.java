package src;

import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;


import javax.swing.*;
import java.nio.file.Path;

public class MusicPlayer {
    private BasicPlayer player;
    private byte isPlaying;
    private static MusicPlayer instance;
    private MusicPlayer(){
        player = new BasicPlayer();
        isPlaying = 0;
    }

    public static MusicPlayer getInstance(){
        if(instance == null){
            instance = new MusicPlayer();
        }
        return instance;
    }

    public void play() throws BasicPlayerException {
        player.play();
        isPlaying = 1;
    }

    public void stop() throws BasicPlayerException {
        player.stop();
        isPlaying = 0;
    }

    public void pause() throws BasicPlayerException{
        player.pause();
        isPlaying = 2;
    }
    public void resume() throws BasicPlayerException{
        player.resume();
        isPlaying = 1;
    }
    public void loadFile(Path file) throws BasicPlayerException{
        player.open(file.toFile());
        isPlaying = 0;
    }

    public byte isPlaying() {
        return isPlaying;
    }
}
