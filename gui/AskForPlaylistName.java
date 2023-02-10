package gui;

import src.Controller;
import utils.PlaylistModel;
import utils.Validator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public AskForPlaylistName(JTabbedPane tabbedPane, JMenu menu, JTree searchTree) {
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
                menuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Path newTrack = (Path) searchTree.getLastSelectedPathComponent();
                        try {
                            Controller.getInstance().getPlaylist(name).addTrack(newTrack);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        ((PlaylistModel) table.getModel()).addRow(newTrack);
                    }
                });
                menu.add(menuItem);
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

        return table;
    }

}