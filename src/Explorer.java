package src;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Explorer{
    private Directory root;
    private static Explorer instance;

    private Explorer() throws IOException {
        this.root = new Directory("D:\\");
    }

    public ArrayList<Path> listRecursively(){
        ArrayList<Path> list = root.listRecursively();

        return list;
    }
    public static Explorer getInstance() throws IOException {
        if(instance == null){
            instance = new Explorer();
        }
        return instance;
    }

    public Path getRoot() {
        return this.root.getRoot();
    }
}
