package src;

import javazoom.jlgui.basicplayer.BasicPlayerException;
import java.nio.file.Path;
import java.util.ArrayList;

public class Controller {
    private ArrayList<Playlist> playlists;
    private static Controller instance;
    final private MusicPlayer musicPlayer = MusicPlayer.getInstance();
    private Playlist actualPlaylist;
    private int playlistCount;
    private Controller(){
        actualPlaylist = null;
        playlists = new ArrayList<>();
        playlists.add(new Playlist("Default"));
        playlistCount = 1;
    }

    public static Controller getInstance(){
        if(instance == null){
            instance = new Controller();
        }

        return instance;
    }

    public void loadTrack(int playlist, int trackIndex) throws BasicPlayerException {
        Path track = playlists.get(playlist).getTrack(trackIndex);
        musicPlayer.loadFile(track);
        actualPlaylist = playlists.get(playlist);
    }

    public Playlist getPlaylist(int indexPlaylist) {
        actualPlaylist = playlists.get(indexPlaylist);
        return actualPlaylist;
    }

    public Playlist getPlaylist(String name){
        Playlist playlist = getPlaylist(0);
        int index = 1;
        while(!playlist.getName().equals(name)){
            playlist = getPlaylist(index++);
        }
        actualPlaylist = playlist;
        return playlist;
    }
    public void addPlaylist(String name){
        playlists.add(new Playlist(name));
        playlistCount++;
    }

    public void removePlaylist(int index){
        playlists.remove(index);
        playlistCount--;
    }

    public int getPlaylistCount(){
        return playlistCount;
    }

    public Playlist getActualPlaylist() {
        return actualPlaylist;
    }
}
