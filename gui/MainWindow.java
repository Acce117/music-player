package gui;

import javazoom.jlgui.basicplayer.BasicPlayerException;
import src.Controller;
import src.MusicPlayer;
import utils.CellRendererCustomized;
import utils.PlaylistModel;
import utils.TreeModelCustomized;
import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.event.*;
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
    private JButton newPlaylist;
    private JPanel playlistPane;
    private JPanel auxPane1;
    private JButton stop;
    private JTable table1;
    private int selectedTab;

    private JPopupMenu popupMenu;

    private JMenu menu;
    final private MusicPlayer playerInstance;

    final private Controller controllerInstance;
    public MainWindow() throws IOException {
        this.playerInstance = MusicPlayer.getInstance();
        this.controllerInstance = Controller.getInstance();

        setContentPane(contentPane);
        setModal(true);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        {//optionPanel configuration
            tabbedPane.setBackground(null);
            auxPane1.setBackground(null);
            selectedTab = -1;
            directoryPane.setBackground(null);
            optionPanel.setBackground(null);
        }

        {//playlistPane configuration
            playlistPane.setBackground(null);

            newPlaylist.addActionListener(e -> {
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

            MouseListener ml = new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    if(SwingUtilities.isRightMouseButton(e)){
                        int selRow = searchTree.getRowForLocation(e.getX(), e.getY());
                        TreePath selPath = searchTree.getPathForLocation(e.getX(), e.getY());
                        searchTree.setSelectionPath(selPath);
                        if (selRow>-1){
                            searchTree.setSelectionRow(selRow);
                            fillMenu();
                            popupMenu.show(searchTree, e.getX(), e.getY());
                        }
                    }
                }
            };
            searchTree.addMouseListener(ml);
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

        getPopupMenu();

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

    private void getPopupMenu(){
        popupMenu = new JPopupMenu("Works");

        popupMenu.add(getPlaylistMenu());
    }

    private JMenu getPlaylistMenu(){
        menu = new JMenu("Add to playlist...");
        fillMenu();
        return menu;
    }

    private void fillMenu(){
        menu.removeAll();
        for(int i = 0; i<controllerInstance.getPlaylistCount(); i++){
            JMenuItem playlistOption = new JMenuItem(controllerInstance.getPlaylist(i).getName());
            
            playlistOption.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    controllerInstance.getPlaylist(playlistOption.getText()).addTrack((Path) searchTree.getLastSelectedPathComponent());
                }

            });

            menu.add(playlistOption);
        }
    }
}
