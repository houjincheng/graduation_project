package anyviewj.callstack.jtreetable;

import anyviewj.callstack.jtreetable.node.AbstractNode;
import anyviewj.callstack.jtreetable.node.ThreadNode;

import com.sun.jdi.ThreadReference;
import com.sun.jdi.Type;

/**
 * StackFrameModel is a TreeTableModel representing a hierarchical file 
 * system. Nodes in the FileSystemModel are FileNodes which, when they 
 * are directory nodes, cache their children to avoid repeatedly querying 
 * the real file system. 
 * 
 *
 * @author hou
 */

public class StackFrameModel extends AbstractTreeTableModel 
                             implements TreeTableModel {

    // Names of the columns.
    static protected String[]  cNames = {"Name", "Value", "Type"};

    // Types of the columns.
    static protected Class[]  cTypes = {TreeTableModel.class, String.class, String.class };


//    public StackFrameModel() { 
//	super(new FileNode(new File(File.separator))); 
//    }
    public StackFrameModel( ThreadReference threadReference ) { 
    	super(new ThreadNode(threadReference) ); 
    }

    //
    // Some convenience methods. 
    //

//    protected File getFile(Object node) {
//	FileNode fileNode = ((FileNode)node); 
//	return fileNode.getFile();       
//    }

    protected Object[] getChildren(Object node) {

    	return ( ( AbstractNode )node).getChildren(); 
    }
//    protected Object[] getChildren(Object node) {
//    	FileNode fileNode = ((FileNode)node); 
//    	return fileNode.getChildren(); 
//    }

    //
    // The TreeModel interface
    //

    public int getChildCount(Object node) { 
	Object[] children = getChildren(node); 
	return (children == null) ? 0 : children.length;
    }

    public Object getChild(Object node, int i) { 
	return getChildren(node)[i]; 
    }

    // The superclass's implementation would work, but this is more efficient. 
    
    //change by ydl ×¢ÊÍµô isLeaf()
//    public boolean isLeaf(Object node) 
//    {
//    	return ( getChildCount(node) == 0 )? true : false; 
//    }

    //
    //  The TreeTableNode interface. 
    //

    public int getColumnCount() {
	return cNames.length;
    }

    public String getColumnName(int column) {
	return cNames[column];
    }

    public Class getColumnClass(int column) {
	return cTypes[column];
    }
 
    public Object getValueAt(Object node, int column) {
//	File file = getFile(node); 
	try {
	    switch(column)
	    {
	    case 0:
		return getName( node );
	    case 1:
		return getValue( node );
	    case 2:
		return getType( node );
	    }
	}
	catch  (SecurityException se) { }
   
	return null; 
    }
    
    
    private String getName(  Object node )
    {
    	String tmp = ( ( AbstractNode )node ).getName();
//    	System.out.println( "-------name = " + tmp + "-----" );
    	return ( tmp == null )? "" : tmp; 
    	
    }
    private String getValue(  Object node )
    {
    	String value = ( ( AbstractNode )node ).getValue();
    	
    	return ( value == null )? "" : value;
    }
    private String getType(  Object node )
    {
    	Type type = ( ( AbstractNode )node ).getType(); 
    	
    	return ( type == null )? ((AbstractNode) node).getTypeName() : type.name(); 
    }
    
}



