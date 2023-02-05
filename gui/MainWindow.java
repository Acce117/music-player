package gui;

import src.Explorer;
import utils.TreeModelCustomized;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;


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
    private int selectedTab;
    public MainWindow() throws IOException {
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
                    tabbedPane1.addTab("eeded",null,new JPanel(),null);
                }
            });
        }
        {//tree1 configuration
            tree1.setBackground(null);
            tree1.setModel(new TreeModelCustomized());
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        {//play button's configuration
            play = new JButton();
            play.setBackground(null);
            play.setBorder(null);
        }

        {//previous button's configuration
            previous = new JButton();
            previous.setBackground(null);
            previous.setBorder(null);
        }

        {//next button's configuration
            next = new JButton();
            next.setBackground(null);
            next.setBorder(null);
        }
    }
}
