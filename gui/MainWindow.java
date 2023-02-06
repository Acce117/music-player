package gui;

import javazoom.jlgui.basicplayer.BasicPlayerException;
import src.MusicPlayer;
import utils.CellRendererCustomized;
import utils.PlaylistModel;
import utils.TreeModelCustomized;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class MainWindow extends JDialog {
    private JPanel contentPane;
    private JProgressBar progressBar1;
    private JButton previous;
    private JButton play;
    private JButton next;
    private JLabel trackImage;
    private JTabbedPane optionPanel;
    private JPanel tabbedPane;
    private JTree searchTree;
    private JPanel directoryPane;
    private JTabbedPane playlists;
    private JButton button1;
    private JPanel playlistPane;
    private JPanel auxPane1;
    private JButton stop;
    private JTable table1;
    private int selectedTab;
    final private MusicPlayer playerInstance;
    public MainWindow() throws IOException {
        this.playerInstance = MusicPlayer.getInstance();

        setContentPane(contentPane);
        setModal(true);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        {//optionPanel configuration
            tabbedPane.setBackground(null);
            auxPane1.setBackground(null);
            selectedTab = -1;
            //optionPanel.setSelectedIndex(-1);

            directoryPane.setBackground(null);
            optionPanel.setBackground(null);
            /*optionPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    if(selectedTab != -1){
                        if(optionPanel.getSelectedIndex() == selectedTab){
                            optionPanel.setSelectedIndex(-1);
                        }
                    }
                    selectedTab = optionPanel.getSelectedIndex();
                }
            });*/
        }

        {//playlistPane configuration
            playlistPane.setBackground(null);

            button1.addActionListener(e -> {
                AskForPlaylistName ask = new AskForPlaylistName(playlists);
                ask.pack();
                ask.setVisible(true);
            });
        }
        {//tree1 configuration
            searchTree.setBackground(null);
            searchTree.setModel(new TreeModelCustomized());
            searchTree.setRootVisible(false);
            searchTree.setCellRenderer(new CellRendererCustomized());
            searchTree.addTreeSelectionListener(e -> {
                try {
                    Path file = (Path) searchTree.getSelectionPath().getLastPathComponent();
                    if(!Files.isDirectory(file) && Files.exists(file)) {
                        playerInstance.loadFile(file);
                        play.setEnabled(true);
                        stop.setEnabled(true);
                    }

                }
                catch (BasicPlayerException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
        play.addActionListener(e -> {
            try {
                if(playerInstance.isPlaying() == 1)
                    playerInstance.pause();
                else if(playerInstance.isPlaying() == 2)
                    playerInstance.resume();
                else
                    playerInstance.play();
            } catch (BasicPlayerException ex) {
                throw new RuntimeException(ex);
            }
        });

        table1.setModel(new PlaylistModel());
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        {//play button's configuration
            play = new JButton();
            play.setBackground(null);
            play.setBorder(null);
            play.setEnabled(false);
        }

        {//previous button's configuration
            previous = new JButton();
            previous.setBackground(null);
            previous.setBorder(null);
            previous.setEnabled(false);
        }

        {//next button's configuration
            next = new JButton();
            next.setBackground(null);
            next.setBorder(null);
            next.setEnabled(false);
        }

        {//stop button's configuration
            stop = new JButton();
            stop.setBackground(null);
            stop.setBorder(null);
            stop.setEnabled(false);
            stop.addActionListener(e -> {
                try {
                    playerInstance.stop();
                } catch (BasicPlayerException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
    }
}
