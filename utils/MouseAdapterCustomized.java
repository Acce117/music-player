package utils;

import gui.MainWindow;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import src.Controller;
import src.MusicPlayer;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Path;
import java.util.Timer;
import java.util.TimerTask;

public class MouseAdapterCustomized extends MouseAdapter {

    private int count = 0;
    private Timer timer = new Timer("doubleClickTimer", false);
    private JTable tableReference;
    private JTabbedPane tabbedPane;
    private Controller controllerInstance;
    private MainWindow mainWindow;
    public MouseAdapterCustomized(MainWindow mainWindow){
        super();
        this.mainWindow = mainWindow;
        tableReference = mainWindow.getDefaultTable();
        controllerInstance = Controller.getInstance();
    }

    public MouseAdapterCustomized(JTable table, MainWindow mainWindow){
        this(mainWindow);
        tableReference = table;
    }
    @Override
    public void mouseClicked(final MouseEvent e) {
        count = e.getClickCount();
        Path track = controllerInstance.getPlaylist(mainWindow.getPlaylists().getSelectedIndex()).getTrack(tableReference.getSelectedRow());
        if (count == 1) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (count == 2) {
                        try {
                            MusicPlayer.getInstance().loadFile(track);
                            mainWindow.setPlayEnabled(true);
                            mainWindow.setPreviousEnabled(true);
                            mainWindow.setNextEnabled(true);
                            mainWindow.setStopEnabled(true);
                        } catch (BasicPlayerException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
                }, 400);
        }
    }
}
