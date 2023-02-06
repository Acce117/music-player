package utils;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.DosFileAttributes;
import java.util.stream.Stream;

/*
TreeModelCustomized is the model which the implemented JTree class is working
it takes as root the path used by the Explorer class in the src package
 */
public class TreeModelCustomized implements TreeModel {
    private Path root;
    public TreeModelCustomized() throws IOException {
        this.root = Path.of("D:\\");
    }

    /*
    * getChildren(Object)
    * it takes an object parent, makes a Path with the parent and list the children contained in it
    * filter the list to leave the needed paths for the program
    * sort the list to show the files as would do it the default explorer
    * return the resulted list
    */
    private Object[] getChildren(Object parent) {
        Object children[] = new String[0];
        Stream<Path> aux;
        try {
            aux = Files.list(Path.of(parent.toString()));
            children = aux.filter(e -> {
                boolean result = false;
                try {
                    DosFileAttributes attrs = Files.readAttributes(e, DosFileAttributes.class);
                    result = !attrs.isSystem() && Files.isDirectory(e) || e.toString().endsWith("mp3");
                }
                catch (FileSystemException ex){

                }
                catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                return result;
            }).sorted((o1, o2) -> {
                int result = 0;
                if (!Files.isDirectory(o1) && Files.isDirectory(o2))
                    result = 1;
                if (Files.isDirectory(o1) && !Files.isDirectory(o2))
                    result = -1;
                return result;
            }).toArray();

        }
        catch (FileSystemException e){

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return children;
    }
    @Override
    public Object getRoot() {
        return this.root;
    }

    /*
    * getChild(Object, int)
    * is a method from the TreeModel interface
    * it takes an object parent and an index
    * ask for the parent's children to the getChildren method
    * return null if the children array is empty or the index is not valid
    * or an Object originally a Path if the child is found*/
    @Override
    public Object getChild(Object parent, int index) {
        Object children[]=getChildren(parent);
        Object result = null;

        if(children.length != 0 && index < children.length){
            result = children[index];
        }

        return result;
    }
    @Override
    public int getChildCount(Object parent) {
        Object children[]=getChildren(parent);

        return children.length;
    }

    /*
    * isLeaf(Object)
    * verifies if the Object node is a leaf
    */
    @Override
    public boolean isLeaf(Object node) {
        boolean result = false;
        if(node != null)
            result = !(Files.isDirectory(Path.of(node.toString())));
        return result;
    }
    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {}
    @Override
    public int getIndexOfChild(Object parent, Object child) {
        return 0;
    }
    @Override
    public void addTreeModelListener(TreeModelListener l) {}
    @Override
    public void removeTreeModelListener(TreeModelListener l) {}
}