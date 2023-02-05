package run;

import gui.MainWindow;
import com.formdev.flatlaf.FlatLightLaf;
import src.Explorer;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        MainWindow dialog = new MainWindow();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
        /*Path path = Path.of("D:\\Multimedia");
        ArrayList<Path> a = new ArrayList<>();
        try (var files = Files.walk(path)) {
            files.forEach(s-> {
                if (Files.isDirectory(s) || s.toString().endsWith("mp3")){
                    a.add(Path.of(s.toString()));
                    a.trimToSize();
                }
            });

            //System.out.println(files.toString());
        }
        for(Path s:a){
            System.out.println(s);
        }*/
        /*try (var files = Files.newDirectoryStream(path, "*.txt")) {
            files.forEach(System.out::println);
            System.out.println("x");
        }
        System.out.println(Files.exists(path));*/
        //Stream a = Files.list(path);

        /*Explorer a = Explorer.getInstance();
        ArrayList<Path> list = a.listRecursively();
        for(Path p: list){
            System.out.println(p);
        }*/
        /*Stream<Path> a=Files.list(path);

        System.out.println(a.toArray().length);*/

    }
}
