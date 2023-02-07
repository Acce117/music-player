package gui;

import src.Controller;
import utils.Validator;

import javax.swing.*;
import java.awt.*;

public class AskForPlaylistName extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JLabel errorMessage;
    public AskForPlaylistName(JTabbedPane tabbedPane) {
        setContentPane(contentPane);
        contentPane.setSize(200, 200);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(e -> {
            try{
                String name = textField1.getText();
                Validator.emptyStringCheck(name);

                tabbedPane.addTab(name,null, getNewPanel(),null);
                Controller.getInstance().addPlaylist(name);
                dispose();
            }
            catch(Exception ex){
                errorMessage.setText(ex.getMessage());
            }
        });

        buttonCancel.addActionListener(e -> dispose());
    }

    private JPanel getNewPanel(){
        JPanel newPanel = new JPanel();
        newPanel.add(getScrollPane());

        return newPanel;
    }

    private JScrollPane getScrollPane() {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.add(getTable());

        return scrollPane;
    }

    private JTable getTable() {
        JTable table = new JTable();
        table.setFillsViewportHeight(true);

        return table;
    }
}