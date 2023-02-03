package gui;

import javax.swing.*;

public class MainWindow extends JDialog {
    private JPanel contentPane;
    private JProgressBar progressBar1;
    private JButton previous;
    private JButton play;
    private JButton next;

    public MainWindow() {
        setContentPane(contentPane);
        setModal(true);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
