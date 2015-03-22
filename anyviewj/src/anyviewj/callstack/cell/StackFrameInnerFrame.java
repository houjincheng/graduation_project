package anyviewj.callstack.cell;

import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.TreePath;

import anyviewj.callstack.jtreetable.JTreeTable;
import anyviewj.callstack.jtreetable.MyTreeExpansionListener;
import anyviewj.callstack.jtreetable.StackFrameModel;
import anyviewj.console.ConsoleCenter;

import com.sun.jdi.ThreadReference;

/**
 * <p>Title: </p>
 *
 * <p>Description:不能滚动到指定节点的原因可能是Tree不是在滚动面板上，而是在JTable中 </p>
 *
 * <p>Copyright: Copyright (c) 2007 gdut 1627</p>
 *
 * <p>Company: gdut 1627</p>
 *
 * @author cyf
 * @version 1.0
 */
public class StackFrameInnerFrame extends DebugInnerFrame {
	
	private static JTreeTable oldTreeTable = null;
    public StackFrameInnerFrame(String title, ConsoleCenter center) {
        super(title, center);
    }
    
    public void showStackFrames( final ThreadReference threadReference )
    {
    	SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {

		    	JTreeTable newTreeTable = new JTreeTable( new StackFrameModel(threadReference) );
		    	StackFrameInnerFrame.this.removeScrollComponent( StackFrameInnerFrame.oldTreeTable );
		    	StackFrameInnerFrame.this.addScrollComponent( newTreeTable );
		    	decorateNewTreeTable( newTreeTable, oldTreeTable ); //展开原来被展开的节点
		    	StackFrameInnerFrame.oldTreeTable = newTreeTable;
		    	
			}
		});		
    }

    /**
     * 或许可以通过SelectionModel来重做
     * @param newTreeTable
     * @param oldTreeTable
     */
    private void decorateNewTreeTable( JTreeTable newTreeTable, JTreeTable oldTreeTable )
    {
    	if ( ( oldTreeTable == null ) || ( newTreeTable == null ) )
    	{
    		return;
    	}
    	JTree oldTree = ( JTree )oldTreeTable.getTreeTableCellRenderer();
    	JTree newTree = ( JTree )newTreeTable.getTreeTableCellRenderer();
    	
    	TreeExpansionListener[] treeExpansionListeners = oldTree.getTreeExpansionListeners();
    	MyTreeExpansionListener myListener = null;
    	
    	for ( TreeExpansionListener l : treeExpansionListeners )
//    	找到保存被展开的节点的监听器，这个监听器是在新建tree对象的构造器中添加的
    	{
    		if ( l instanceof MyTreeExpansionListener )
    		{
    			myListener = (MyTreeExpansionListener) l;
    		}
    	}
    	
    	for ( String path : myListener.getExpandedTreePaths() )
//    	遍历，在新的tree中展开每一个用户展开的节点，如果这个节点存在
    	{
    		expandNode( path, newTree );
    	}

    }
    /**
     * 
     * @param path
     * @param newTree
     */
    private void expandNode( String path, JTree newTree )
    {
//    	void makeVisible(TreePath path) 
//    	newTree.setScrollsOnExpand( true );//
//    	 void scrollPathToVisible(TreePath path) 
//         确保路径中所有的路径组件均展开（最后一个路径组件除外）并滚动，以便显示该路径标识的节点。 
//    	newTree.scrollRowToVisible( 2 ); 
//        滚动行标识的条目，直到显示出来。 
    	for ( int i=0; i<newTree.getVisibleRowCount(); i++ )
//    		依次展开上层节点
    	{
    		TreePath tmp = newTree.getPathForRow( i );
    		
    		if ( ( tmp != null )
    				&& path.startsWith( tmp.toString().substring( 0, tmp.toString().length() - 1 ) ) )
    		{
    			newTree.expandPath( tmp );
    		}
    		if ( ( tmp != null ) 
    				&& ( path.compareTo( tmp.toString() ) == 0 ))
//    		找到节点
    		{
    			break;
    		}
    		
    	}
    }
    /**
     * 每次运行前都必须将以前的数据清空
     */
    public void clear(){

    	removeScrollComponent( this.oldTreeTable );
    	this.oldTreeTable = null;
    }

}
