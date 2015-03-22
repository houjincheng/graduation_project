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
 * <p>Description:���ܹ�����ָ���ڵ��ԭ�������Tree�����ڹ�������ϣ�������JTable�� </p>
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
		    	decorateNewTreeTable( newTreeTable, oldTreeTable ); //չ��ԭ����չ���Ľڵ�
		    	StackFrameInnerFrame.oldTreeTable = newTreeTable;
		    	
			}
		});		
    }

    /**
     * �������ͨ��SelectionModel������
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
//    	�ҵ����汻չ���Ľڵ�ļ���������������������½�tree����Ĺ���������ӵ�
    	{
    		if ( l instanceof MyTreeExpansionListener )
    		{
    			myListener = (MyTreeExpansionListener) l;
    		}
    	}
    	
    	for ( String path : myListener.getExpandedTreePaths() )
//    	���������µ�tree��չ��ÿһ���û�չ���Ľڵ㣬�������ڵ����
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
//         ȷ��·�������е�·�������չ�������һ��·��������⣩���������Ա���ʾ��·����ʶ�Ľڵ㡣 
//    	newTree.scrollRowToVisible( 2 ); 
//        �����б�ʶ����Ŀ��ֱ����ʾ������ 
    	for ( int i=0; i<newTree.getVisibleRowCount(); i++ )
//    		����չ���ϲ�ڵ�
    	{
    		TreePath tmp = newTree.getPathForRow( i );
    		
    		if ( ( tmp != null )
    				&& path.startsWith( tmp.toString().substring( 0, tmp.toString().length() - 1 ) ) )
    		{
    			newTree.expandPath( tmp );
    		}
    		if ( ( tmp != null ) 
    				&& ( path.compareTo( tmp.toString() ) == 0 ))
//    		�ҵ��ڵ�
    		{
    			break;
    		}
    		
    	}
    }
    /**
     * ÿ������ǰ�����뽫��ǰ���������
     */
    public void clear(){

    	removeScrollComponent( this.oldTreeTable );
    	this.oldTreeTable = null;
    }

}
