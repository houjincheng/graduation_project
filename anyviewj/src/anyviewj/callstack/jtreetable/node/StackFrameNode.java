package anyviewj.callstack.jtreetable.node;

import java.util.List;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.LocalVariable;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.StackFrame;

public class StackFrameNode extends AbstractNode{

	private StackFrame frame = null;
	
	public StackFrameNode( StackFrame f ) {

		this.frame = f;
		
		init();
	}

	private void init()
	{
//		this作为一个特殊的Field添加到当前帧
		addThisObject();
//		方法内的局部变量，包括参数
        List<LocalVariable> locals;
		try {
			locals = frame.visibleVariables();
			
	        for(LocalVariable local : locals)
	        {
	           addChild( new FieldNode( frame, local) );
	        }//end for
		} catch (AbsentInformationException e) {
			// AbsentInformationException - if there is no local variable information for this method. 

			e.printStackTrace();
		}
		setValue( String.valueOf( "Line: " + frame.location().lineNumber() ) );
		setName( frame.location().method().toString() );
//		System.out.println( "-------value = " + String.valueOf( frame.location().lineNumber() ) );
	}
	
	private void addThisObject()
	{
//		an ObjectReference, or null if the frame represents a native or static method. 
		ObjectReference thisObj = frame.thisObject();
		
		if ( thisObj != null )
		{
			addChild( new FieldNode(thisObj) );
		}
	}
}
