package src;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Node {
    Path root;
    ArrayList<Node> sons;

    public Node(String path) throws IOException {
        this.root = Path.of(path);
        fillSons();
    }

    private void fillSons() throws IOException {
        try(var files = Files.list(root)){
            files.forEach(f-> {
                try {
                    sons.add(new Node(f.toString()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
