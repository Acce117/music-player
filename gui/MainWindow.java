package gui;

import javazoom.jlgui.basicplayer.BasicPlayerException;
import src.Controller;
import src.MusicPlayer;
import src.Playlist;
import utils.CellRendererCustomized;
import utils.MouseAdapterCustomized;
import utils.PlaylistModel;
import utils.TreeModelCustomized;
import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;
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
    private JTable defaultTable;
    private JScrollPane defaultScrollPane;
    private JPanel defaultPanel;
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
            //selectedTab = -1;
            directoryPane.setBackground(null);
            optionPanel.setBackground(null);
        }

        {//playlistPane configuration
            playlistPane.setBackground(null);

            newPlaylist.addActionListener(e -> {
                AskForPlaylistName ask = new AskForPlaylistName(this);
                ask.pack();
                ask.setVisible(true);
            });
        }

        {//tree1 configuration
            searchTree.setBackground(null);
            searchTree.setModel(TreeModelCustomized.getInstance());
            searchTree.setRootVisible(false);
            searchTree.setCellRenderer(new CellRendererCustomized());

            MouseListener ml = new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        int selRow = searchTree.getRowForLocation(e.getX(), e.getY());
                        TreePath selPath = searchTree.getPathForLocation(e.getX(), e.getY());
                        searchTree.setSelectionPath(selPath);
                        if (selRow > -1) {
                            searchTree.setSelectionRow(selRow);
                            popupMenu.show(searchTree, e.getX(), e.getY());
                        }
                    }
                    else{
                        try {
                            Path file = (Path) searchTree.getSelectionPath().getLastPathComponent();
                            if (!Files.isDirectory(file) && Files.exists(file)) {
                                playerInstance.loadFile(file);
                                play.setEnabled(true);
                                stop.setEnabled(true);
                            }

                        } catch (BasicPlayerException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            };
            searchTree.addMouseListener(ml);
        }

        {//trackImage configuration
            paintTrackImageLabel();
        }

        getPopupMenu();

        {//defaultTable configuration
            defaultTable.setModel(new PlaylistModel());
            defaultTable.addMouseListener(new MouseAdapterCustomized(this));
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        {//play button's configuration
            play = new JButton();
            play.setBackground(null);
            play.setSize(15, 25);
            play.setBorder(null);
            play.setEnabled(false);
            ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("\\png\\pngwing.com (6).png")));
            play.setIcon(new ImageIcon(image.getImage().getScaledInstance(play.getWidth(), play.getHeight(), Image.SCALE_SMOOTH)));
            play.addActionListener(e -> {
                try {
                    if (playerInstance.isPlaying() == 1)
                        playerInstance.pause();
                    else if (playerInstance.isPlaying() == 2)
                        playerInstance.resume();
                    else
                        playerInstance.play();
                } catch (BasicPlayerException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }

        {//previous button's configuration
            previous = new JButton();
            previous.setBackground(null);
            previous.setSize(20, 18);
            previous.setBorder(null);
            previous.setEnabled(false);
            ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("\\png\\pngwing.com (11).png")));
            previous.setIcon(new ImageIcon(image.getImage().getScaledInstance(previous.getWidth(), previous.getHeight(), Image.SCALE_SMOOTH)));
            previous.addActionListener(e -> {
                try {
                    Playlist playlist = controllerInstance.getActualPlaylist();
                    int track = playlist.getActualTrack();
                    playerInstance.loadFile(playlist.getTrack(track - 1));
                    playerInstance.play();
                }
                catch (BasicPlayerException ex) {
                    throw new RuntimeException(ex);
                }
                catch(IndexOutOfBoundsException ex){
                    JPopupMenu popupMessage = new JPopupMenu();
                    popupMessage.setSize(100, 25);
                    JLabel label = new JLabel(" Start of the playlist ");
                    popupMessage.add(label);
                    popupMessage.show(trackImage, trackImage.getWidth()/2 - popupMessage.getWidth()/2, trackImage.getHeight() - popupMessage.getHeight());
                }

            });
        }

        {//next button's configuration
            next = new JButton();
            next.setBackground(null);
            next.setSize(20, 18);
            next.setBorder(null);
            next.setEnabled(false);
            ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("\\png\\pngwing.com (10).png")));
            next.setIcon(new ImageIcon(image.getImage().getScaledInstance(next.getWidth(), next.getHeight(), Image.SCALE_SMOOTH)));
            next.addActionListener(e -> {
                try {
                    Playlist playlist = controllerInstance.getActualPlaylist();
                    int trackIndex = playlist.getActualTrack();
                    Path track = playlist.getTrack(trackIndex+1);
                    playerInstance.loadFile(track);
                    playerInstance.play();
                }
                catch (BasicPlayerException ex) {
                    throw new RuntimeException(ex);
                }catch(IndexOutOfBoundsException ex){
                    JPopupMenu popupMessage = new JPopupMenu();
                    popupMessage.setSize(100, 25);
                    JLabel label = new JLabel(" End of the playlist ");
                    popupMessage.add(label);
                    popupMessage.show(trackImage, trackImage.getWidth()/2 - popupMessage.getWidth()/2, trackImage.getHeight() - popupMessage.getHeight());
                }
            });
        }


        {//stop button's configuration
            stop = new JButton();
            stop.setBackground(null);
            stop.setSize(25, 25);
            stop.setBorder(null);
            stop.setEnabled(false);
            stop.addActionListener(e -> {
                try {
                    playerInstance.stop();
                } catch (BasicPlayerException ex) {
                    throw new RuntimeException(ex);
                }
            });
            ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("\\png\\pngwing.com (7).png")));
            stop.setIcon(new ImageIcon(image.getImage().getScaledInstance(stop.getWidth(), stop.getHeight(), Image.SCALE_SMOOTH)));

        }
    }

    private void getPopupMenu() {
        popupMenu = new JPopupMenu("Works");

        popupMenu.add(getPlaylistMenu());
    }

    private JMenu getPlaylistMenu() {
        menu = new JMenu("Add to playlist...");

        fillDefaultMenu();
        return menu;
    }

    private void fillDefaultMenu() {
        JMenuItem playlistOption = new JMenuItem(controllerInstance.getPlaylist(0).getName());
        playlistOption.addActionListener(e -> {
            Path newTrack = (Path) searchTree.getLastSelectedPathComponent();
            try {
                controllerInstance.getPlaylist(playlistOption.getText()).addTrack(newTrack);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            ((PlaylistModel) (defaultTable.getModel())).addRow(newTrack);
        });

        menu.add(playlistOption);
    }

    private void paintTrackImageLabel() {
        ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("\\png\\pngwing.com (3).png")));
        trackImage.setIcon(new ImageIcon(image.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH)));
    }

    public JTable getDefaultTable(){
        return defaultTable;
    }

    public JTabbedPane getPlaylists(){
        return playlists;
    }

    public JTree getSearchTree(){
        return searchTree;
    }

    public JMenu getMenu(){
        return menu;
    }

    public void setPlayEnabled(boolean value) {
        play.setEnabled(value);
    }

    public void setPreviousEnabled(boolean value) {
        previous.setEnabled(value);
    }

    public void setNextEnabled(boolean value) {
        next.setEnabled(value);
    }

    public void setStopEnabled(boolean value) {
        stop.setEnabled(value);
    }
}
