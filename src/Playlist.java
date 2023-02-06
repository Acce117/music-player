package src;

import java.nio.file.Path;
import java.util.ArrayList;
public class Playlist {
    private ArrayList<Path> tracks;
    private int size;
    private String name;
    public Playlist(String name){
        this.name = name;
        this.tracks = new ArrayList<>();
        size = 0;
    }
    public Path getTrack(int index){
        return tracks.get(index);
    }

    public void addTrack(Path newTrack){
        this.tracks.add(newTrack);
        size++;
    }

    public void removeTrack(int index){
        this.tracks.remove(index);
        size--;
    }

    public int getSize(){
        return size;
    }
}
