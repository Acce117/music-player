package gui;

import javazoom.jlgui.basicplayer.BasicPlayerException;
import src.MusicPlayer;
import utils.CellRendererCustomized;
import utils.TreeModelCustomized;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JTree tree1;
    private JPanel directoryPane;
    private JTabbedPane tabbedPane1;
    private JButton button1;
    private JPanel playlistPane;
    private JPanel auxPane1;
    private JButton stop;
    private int selectedTab;

    private MusicPlayer playerInstance;
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

            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //TODO a window to ask for the name of the playlist
                    tabbedPane1.addTab("eeded",null,new JPanel(),null);
                }
            });
        }
        {//tree1 configuration
            tree1.setBackground(null);
            tree1.setModel(new TreeModelCustomized());
            tree1.setRootVisible(false);
            tree1.setCellRenderer(new CellRendererCustomized());
            tree1.addTreeSelectionListener(new TreeSelectionListener() {
                @Override
                public void valueChanged(TreeSelectionEvent e) {
                    //System.out.println(tree1.getSelectionPath().getLastPathComponent());
                    try {
                        Path file = (Path) tree1.getSelectionPath().getLastPathComponent();
                        if(!Files.isDirectory(file)) {
                            playerInstance.loadFile(file);
                            play.setEnabled(true);
                            stop.setEnabled(true);
                        }
                        //playerInstance.play();
                    }
                    catch (BasicPlayerException ex) {
                        //throw new RuntimeException(ex);
                        ex.printStackTrace();
                        //System.out.println(ex.getMessage());
                    }
                }
            });
        }
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });
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
            stop.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        playerInstance.stop();
                    } catch (BasicPlayerException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }
    }
}
