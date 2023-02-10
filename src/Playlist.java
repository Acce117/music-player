package src;

import utils.TreeModelCustomized;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

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
        Stream<Object> aux = Arrays.stream(TreeModelCustomized.getInstance().getChildren(newTrack)).filter(path->{
            boolean result = !Files.isDirectory((Path) path);
            return result;
        });
        Object[] aux2 = aux.toArray();
        Path newTracks[] = Arrays.copyOf(aux2, aux2.length, Path[].class);

        addTracks(newTracks);
    }
    public void addTracks(Path [] newTracks){
        this.tracks.addAll(List.of(newTracks));
        size = size + newTracks.length;
        for(Path path: tracks){
            System.out.println(path.toString());
        }
    }

    public String getName() {
        return name;
    }
}
