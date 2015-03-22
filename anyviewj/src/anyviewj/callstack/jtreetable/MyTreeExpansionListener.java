package anyviewj.callstack.jtreetable;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;

/**
 * 
 * @author 侯锦城
 *
 */
public class MyTreeExpansionListener implements TreeExpansionListener
{

	private ArrayList<String> expandedTreePaths = new ArrayList<String>();
	
	/**
	 * 当一个节点被关闭的时候，需要将其和对应得子节点从展开节点的列表中删除
	 */
	@Override
	public void treeCollapsed(TreeExpansionEvent arg0)
	{
		String path = arg0.getPath().toString()
				.substring( 0, arg0.getPath().toString().length() - 1 );
		
		synchronized ( expandedTreePaths ) {
			
			for ( int i=0; i<expandedTreePaths.size(); i++ )
			{
				String tmp = expandedTreePaths.get( i );
				if ( tmp.startsWith( path ) )
				{
					expandedTreePaths.remove( tmp );
					System.out.println( "remove " + tmp);
					i = 0;//删除一个元素的时候，列表已发生改变，需要重头开始扫描
				}

			}
		}
		
	}

	@Override
	public void treeExpanded(TreeExpansionEvent arg0) {
		
		expandedTreePaths.add( arg0.getPath().toString() );
		System.out.println( "add" + arg0.getPath().toString() );
	}
	
	public ArrayList<String> getExpandedTreePaths()
	{
		return expandedTreePaths;
	}
}
