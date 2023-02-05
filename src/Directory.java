package src;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.DosFileAttributes;
import java.util.ArrayList;

public class Directory extends File{
    ArrayList<File> sons;
    public Directory(String path) throws IOException {
        super(path);
        this.sons = new ArrayList<>();
        fillSons();
    }

    private void fillSons() throws IOException {
        try(var files = Files.list(root)){
            files.forEach(f-> {
                try {
                    DosFileAttributes attrs = Files.readAttributes(f, DosFileAttributes.class);
                    if(!attrs.isSystem()) {
                        if (Files.isDirectory(f))
                            this.sons.add(new Directory(f.toString()));
                        else
                            this.sons.add(new File(f.toString()));
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    public ArrayList<Path> listRecursively() {
        ArrayList<Path> list = new ArrayList<>();
        list.add(this.root);
        for(File f: sons){
            if(f instanceof Directory)
                list.addAll(((Directory) f).listRecursively());
            else
                list.add(f.getRoot());
        }

        return list;
    }

    public ArrayList<File> listSons() {
        return sons;
    }
}
