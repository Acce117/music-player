package utils;

import src.Controller;
import src.Playlist;

import javax.swing.table.DefaultTableModel;
import java.nio.file.Files;
import java.nio.file.Path;
public class PlaylistModel extends DefaultTableModel {
    private static int instancesCount = 0;
    private final int actualPlaylist;
    private String[] row;

    public PlaylistModel(){
        super();
        actualPlaylist = instancesCount;
        instancesCount++;
        row = new String[1];
        addColumn("Tracks");
    }

    public void update(){
        setRowCount(0);
        Playlist playlist = Controller.getInstance().getPlaylist(actualPlaylist);

        for(int i = 0; i< playlist.getSize(); i++){
            row[0] = playlist.getTrack(i).getFileName().toString();
            addRow(row);
        }
    }

    public void addRow(Path track) {
        if(Files.isDirectory(track))
            update();
        else {
            row[0] = track.getFileName().toString();
            super.addRow(row);
        }
    }
    public static int getInstancesCount(){
        return instancesCount;
    }

    @Override
    public boolean isCellEditable(int row, int column){
        return false;
    }
}
