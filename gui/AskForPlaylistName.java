package gui;

import src.Controller;
import utils.MouseAdapterCustomized;
import utils.PlaylistModel;
import utils.Validator;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;

public class AskForPlaylistName extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JLabel errorMessage;
    private JPanel newPanel;
    private JScrollPane scrollPane;
    private JTable table;

    private JTabbedPane tabbedPane;
    private MainWindow mainWindow;
    public AskForPlaylistName(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.tabbedPane = mainWindow.getPlaylists();
        setContentPane(contentPane);
        contentPane.setSize(200, 200);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(e -> {
            try {
                //Validate the name
                String name = textField1.getText();
                Validator.emptyStringCheck(name);

                //add the new playlist to the Controller
                tabbedPane.addTab(name, null, getNewPanel(), null);
                Controller.getInstance().addPlaylist(name);
                tabbedPane.setSelectedIndex(tabbedPane.getSelectedIndex() + 1);
                //
                JMenuItem menuItem = new JMenuItem(name);
                menuItem.addActionListener(e1 -> {
                    Path newTrack = (Path) mainWindow.getSearchTree().getLastSelectedPathComponent();
                    try {
                        Controller.getInstance().getPlaylist(name).addTrack(newTrack);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    ((PlaylistModel) table.getModel()).addRow(newTrack);
                });
                mainWindow.getMenu().add(menuItem);
                dispose();
            } catch (Exception ex) {
                errorMessage.setText(ex.getMessage());
            }
        });

        buttonCancel.addActionListener(e -> dispose());
    }

    private JPanel getNewPanel() {
        newPanel = new JPanel();
        newPanel.setLayout(new CardLayout());
        newPanel.add(getScrollPane());

        return newPanel;
    }

    private JScrollPane getScrollPane() {
        scrollPane = new JScrollPane();
        scrollPane.setSize(newPanel.getSize());
        scrollPane.setViewportView(getTable());
        return scrollPane;
    }

    private JTable getTable() {
        table = new JTable();
        table.setModel(new PlaylistModel());
        table.setSize(scrollPane.getSize());
        table.setFillsViewportHeight(true);
        table.addMouseListener(new MouseAdapterCustomized(table, mainWindow));

        return table;
    }

}