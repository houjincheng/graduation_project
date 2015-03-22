package anyviewj.callstack.jtreetable.node;

import java.util.ArrayList;

import com.sun.jdi.Type;
import com.sun.jdi.Value;


@Deprecated
public interface Node {

	public Object parent = null;
	public int id = -1;
	public String name = null;
	public Type type = null;
	public Value value = null;
	public ArrayList<Object> children = new ArrayList<Object>();
	public boolean expandable = false;
	public boolean expanded = false;
	
	public boolean addChild( Object child );
//	public boolean removeChild();
	public Object[] getChildren();
	
	public void setExpanded( boolean expanded );
	
	public void setExpandable( boolean expandable );
	
	
}
