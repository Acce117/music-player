package utils;

import sun.swing.DefaultLookup;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.nio.file.Path;

/*
* CellRendererCustomized extends from DefaultTreeCellRenderer
* implements a single change in the getTreeCellRendererComponent method
* as long we work with Path, JTree renders the name of the file with the whole path
* in CellRendererCustomized we take just the file name to render the JTree
* this change appears in line 31
* */
public class CellRendererCustomized extends DefaultTreeCellRenderer {
    private boolean isDropCell;
    private JTree tree;

    public CellRendererCustomized(){
        super();
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean sel,
                                                  boolean expanded,
                                                  boolean leaf, int row,
                                                  boolean hasFocus) {
        String         stringValue = tree.convertValueToText(((Path)value).getFileName(), sel,
                expanded, leaf, row, hasFocus);             /*-------------------------*/

        this.tree = tree;
        this.hasFocus = hasFocus;
        setText(stringValue);

        Color fg = null;
        isDropCell = false;

        JTree.DropLocation dropLocation = tree.getDropLocation();
        if (dropLocation != null
                && dropLocation.getChildIndex() == -1
                && tree.getRowForPath(dropLocation.getPath()) == row) {

            Color col = DefaultLookup.getColor(this, ui, "Tree.dropCellForeground");
            if (col != null) {
                fg = col;
            } else {
                fg = getTextSelectionColor();
            }

            isDropCell = true;
        } else if (sel) {
            fg = getTextSelectionColor();
        } else {
            fg = getTextNonSelectionColor();
        }

        setForeground(fg);

        Icon icon = null;
        if (leaf) {
            icon = getLeafIcon();
        } else if (expanded) {
            icon = getOpenIcon();
        } else {
            icon = getClosedIcon();
        }

        if (!tree.isEnabled()) {
            setEnabled(false);
            LookAndFeel laf = UIManager.getLookAndFeel();
            Icon disabledIcon = laf.getDisabledIcon(tree, icon);
            if (disabledIcon != null) icon = disabledIcon;
            setDisabledIcon(icon);
        } else {
            setEnabled(true);
            setIcon(icon);
        }
        setComponentOrientation(tree.getComponentOrientation());

        selected = sel;

        return this;
    }
}
