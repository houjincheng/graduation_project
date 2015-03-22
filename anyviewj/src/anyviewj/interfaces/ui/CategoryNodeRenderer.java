/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package anyviewj.interfaces.ui;

import anyviewj.interfaces.ui.panel.CurProjectPane.MyTreeNode;

import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author y
 */
public class CategoryNodeRenderer extends DefaultTreeCellRenderer{
    
    @Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
						  boolean sel,
						  boolean expanded,
						  boolean leaf, int row,
						  boolean hasFocus){
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,  
                row, hasFocus);
        
        MyTreeNode node = (MyTreeNode) value;
        
        if(node.isFolder()){
            if(expanded){
                setIcon(getOpenIcon());
            }else{
                setIcon(getClosedIcon());
            }
        }else{
            setIcon(getLeafIcon());
        }
        
        return this;
        
    }
    
}
