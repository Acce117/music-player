package src;

import java.io.IOException;

public class Explorer {
    private Node root;
    private static Explorer instance;

    private Explorer() throws IOException {
        this.root = new Node("C:\\Users");
    }

    public static Explorer getInstance() throws IOException {
        if(instance == null){
            instance = new Explorer();
        }
        return instance;
    }
}
