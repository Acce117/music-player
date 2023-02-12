package src;

import utils.TreeModelCustomized;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Playlist {
    private ArrayList<Object> tracks;
    private int size;
    private String name;
    private int actualTrack;
    public Playlist(String name){
        this.name = name;
        this.tracks = new ArrayList<>();
        size = 0;
        actualTrack = 0;
    }


    public Path getTrack(int index){
        if(index >= size || index < 0){
            throw new IndexOutOfBoundsException();
        }
        actualTrack = index;
        return (Path)tracks.get(index);
    }

    public void addTrack(Path newTrack) throws IOException {
        if(Files.isDirectory(newTrack)){
            arrayProcess(newTrack);
        }
        else{
            this.tracks.add(newTrack);
            size++;
        }
    }

    public void removeTrack(int index){
        this.tracks.remove(index);
        size--;
    }

    public int getSize(){
        return size;
    }

    private void arrayProcess(Path newTrack) throws IOException {
        Object[] aux =TreeModelCustomized.getInstance().getChildren(newTrack);
        Object[] newTracks = Arrays.stream(aux).filter(path-> !Files.isDirectory((Path) path)).toArray();

        addTracks(newTracks);
    }

    public void addTracks(Object[] newTracks){
        this.tracks.addAll(List.of(newTracks));
        size = size + newTracks.length;
    }
    public String getName() {
        return name;
    }

    public int getActualTrack() {
        return actualTrack;
    }
}
