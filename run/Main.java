package run;

import gui.MainWindow;
import com.formdev.flatlaf.FlatLightLaf;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import org.tritonus.share.sampled.file.TAudioFileFormat;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Main{
    static BasicPlayer player = new BasicPlayer();
    public static void main(String[] args) throws IOException, BasicPlayerException, UnsupportedAudioFileException {
        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        MainWindow dialog = new MainWindow();
        dialog.pack();
        dialog.setVisible(true);

        System.exit(0);

        /*player.open(new File("D:\\Multimedia\\Cubanos\\Habana Blues\\Habana Blues - Habana Blues.mp3"));
        AudioFileFormat a = AudioSystem.getAudioFileFormat(new File("D:\\Multimedia\\Cubanos\\Habana Blues\\Habana Blues – En Todas Partes.mp3"));
        Map<String, Object> p = ((TAudioFileFormat) a).properties();

        Long micro = (Long) p.get("duration");
        int c = (int)(micro/1000);
        int d = (c/1000);
        System.out.println(d);*/
    }
}
