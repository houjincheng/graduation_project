package anyviewj.callstack.jtreetable;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import anyviewj.callstack.jtreetable.node.AbstractNode;
import anyviewj.callstack.jtreetable.node.StackFrameNode;
  
  
public class MyDefaultTreeCellRenderer extends DefaultTreeCellRenderer   
{  
      
	private JTree tree = null;
	private JTable table = null;
    /**
	 * 
	 */
	private static final long serialVersionUID = 8373486937780373629L;

    public   MyDefaultTreeCellRenderer( JTree tree, JTable table )   
    {     
    	this.tree = tree;
    	
    	this.table = table;
    	
    	
    }     
      
      
    /** 
     * @author hou
     * 重载getTreeCellRendererComponent，设置图标和文本 
     * @param tree 
     * @param value 
     * @param selected 
     * @param expanded 
     * @param leaf 
     * @param row 
     * @param hasFocus 
     * @return Compenent 
     */  
    public Component getTreeCellRendererComponent
    (JTree tree, Object value, boolean selected, boolean expanded, 
    		boolean leaf, int row, boolean hasFocus)     
    {     
    	//设置图片和文字  
    	String iconPath = "";
        ImageIcon userIcon  = null;    
        String str = null;  
        JLabel label = new JLabel();  
        super.getTreeCellRendererComponent(tree,value,selected,expanded,leaf,row,hasFocus);     
        AbstractNode node = ( AbstractNode )value;   
        
        if ( node instanceof StackFrameNode )
        //栈帧节点
        {
        	if ( expanded )
        	{
        		
        		iconPath = "src/anyviewj/callstack/jtreetable/resource/StackFrameNodeOpened.gif";
        	}
        	else
        	{
        		iconPath = "src/anyviewj/callstack/jtreetable/resource/StackFrameNodeClosed.gif";
        	}
        }
        else //域节点
        {
        	if ( node.getName().equals( "this" ) )
        	//当前this对象
        	{
            	if ( expanded )
            	{
            		
            		iconPath = "src/anyviewj/callstack/jtreetable/resource/FieldNode_ThisOpened.gif";
            	}
            	else
            	{
            		iconPath = "src/anyviewj/callstack/jtreetable/resource/FieldNode_ThisClosed.gif";
            	}
        	}
        	else
        	{
        		if ( leaf )
        		{
        			iconPath = "src/anyviewj/callstack/jtreetable/resource/FieldNode_Leaf.gif";
        		}
        		else
        		{
        			iconPath = "src/anyviewj/callstack/jtreetable/resource/FieldNode_NotLeaf.gif";
        		}
        	}
        }
        	
        str = node.getName(); 
  
        label.setIcon( new ImageIcon( iconPath ) );  
        label.setText(str); 
//        Dimension size = new Dimension( tree.getSize().width, tree.getRowHeight());
//        label.setPreferredSize( size );
        return label; 
    }  

}  
