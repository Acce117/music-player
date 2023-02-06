package run;

import gui.MainWindow;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.io.IOException;
public class Main {
    public static void main(String[] args) throws IOException{
        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        MainWindow dialog = new MainWindow();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
