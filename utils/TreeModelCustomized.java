package utils;

import src.Explorer;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.DosFileAttributes;
import java.util.stream.Stream;

public class TreeModelCustomized implements TreeModel {
    private Path root;
    public TreeModelCustomized() throws IOException {
        this.root = Explorer.getInstance().getRoot();
    }

    private Object[] getChildren(Object parent){
        Object children[]= new Object[0];
        Stream<Path> aux;
        try {
            aux = Files.list(Path.of(parent.toString()));
            children = aux.filter(e-> {
                try {
                    DosFileAttributes attrs = Files.readAttributes(e, DosFileAttributes.class);
                    return !attrs.isSystem() && Files.isDirectory(e);// || e.toString().endsWith("mp3");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }).toArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return children;
    }

    @Override
    public Object getRoot() {
        return this.root.getRoot();
    }

    @Override
    public Object getChild(Object parent, int index) {
        Object children[]=getChildren(parent);
        Object result = null;
        java.io.File a = null;
        if(children.length != 0 && index < children.length){
            result = children[index];
        }
        if(result != null)
            a = new File(result.toString());
        return a;
    }

    @Override
    public int getChildCount(Object parent) {
        Object children[]=getChildren(parent);

        return children.length;
    }

    @Override
    public boolean isLeaf(Object node) {
        boolean result = false;
        if(node != null)
            result = !(Files.isDirectory(Path.of(node.toString())));
        return result;
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {

    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        return 0;
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {

    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {

    }
}
