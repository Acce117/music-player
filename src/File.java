package src;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class File {
    Path root;
    public File(String path) throws IOException {
        this.root = Path.of(path);
    }
    public Path getRoot(){
        return this.root;
    }

}