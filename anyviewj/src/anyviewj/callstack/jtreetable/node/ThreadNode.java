package anyviewj.callstack.jtreetable.node;

import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.StackFrame;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.VoidType;

public class ThreadNode extends AbstractNode{

	
	private ThreadReference threadReference = null;
	
	public ThreadNode( ThreadReference threadReference ) {
		
		this.threadReference = threadReference;
		
		init();
		addChildren();
		
	}
	private void addChildren()
	{
		try
		{
			for ( StackFrame f : threadReference.frames() )
			{
				addChild(new StackFrameNode( f ) );
			}
		}
		catch (IncompatibleThreadStateException e1) 
		{
			e1.printStackTrace();
		}
	}

	private void init()
	{
		setName( threadReference.name() );		
		setValue( "#0" );
		setType( threadReference.type() );
	}
}
