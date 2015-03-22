package anyviewj.callstack.jtreetable;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;

/**
 * 
 * @author �����
 *
 */
public class MyTreeExpansionListener implements TreeExpansionListener
{

	private ArrayList<String> expandedTreePaths = new ArrayList<String>();
	
	/**
	 * ��һ���ڵ㱻�رյ�ʱ����Ҫ����Ͷ�Ӧ���ӽڵ��չ���ڵ���б���ɾ��
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
					i = 0;//ɾ��һ��Ԫ�ص�ʱ���б��ѷ����ı䣬��Ҫ��ͷ��ʼɨ��
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
