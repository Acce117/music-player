package src;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class Explorer{
    private Directory root;
    private static Explorer instance;

    private Explorer() throws IOException {
        this.root = new Directory("D:\\Pictures");
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
}
