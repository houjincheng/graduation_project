package anyviewj.callstack;

import anyviewj.callstack.cell.StackFrameInnerFrame;
import anyviewj.console.ConsoleCenter;

import com.sun.jdi.ThreadReference;
import com.sun.jdi.event.BreakpointEvent;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.LocatableEvent;
import com.sun.jdi.event.StepEvent;
import com.sun.jdi.event.VMDeathEvent;
import com.sun.jdi.event.VMDisconnectEvent;
import com.sun.jdi.event.VMStartEvent;

public class StackFrameAnalyzer{

	
	
	private static final StackFrameAnalyzer analyzer = new StackFrameAnalyzer();
	
	private static StackFrameInnerFrame stackFrameInnerFrame = null;
	private String oldEvent = ""; //因为接收到了重复的2次断点事件
	private StackFrameAnalyzer() {}

	public static StackFrameAnalyzer getInstance( ConsoleCenter center ) {
		
		stackFrameInnerFrame = center.mainFrame.leftPartPane.debugPane.stackFrameInnerFrame;
		return analyzer;
	}

	/**
	 * VMEventManager发出的所有事件都会通知调用这个方法
	 * @param e
	 */
	public void eventOccurred(Event e) {
		

//		没有接收到VMDepthEvent
//		System.out.println( "========e ====" + e );
//		if ( e.toString().compareTo( oldEvent )  == 0 ) 
//		{
//			return;
//		}
		if ( (  e instanceof BreakpointEvent )
				|| ( e instanceof StepEvent
						&& !( e.toString().equals( "StepEvent@java.lang.Thread:671 in thread main" ) ) ) )
		{
			if ( ( e.toString() ).compareTo( oldEvent ) == 0 )
//			因为会接受到2次重复的断点事件
			{
				return;
			}
			ThreadReference threadReference = ( ( LocatableEvent )e ).thread();
			
			if ( threadReference != null )
			{
				stackFrameInnerFrame.showStackFrames(threadReference);
			}
			oldEvent = e.toString();
			System.out.println( "-----e----" + e );
		}
		if ( ( ( e instanceof VMDisconnectEvent ) ) || 
				( e instanceof VMStartEvent ) ||
				( e instanceof VMDeathEvent ) 
				|| ( e.toString().equals( "StepEvent@java.lang.Thread:671 in thread main" ) ) )
		{
			stackFrameInnerFrame.clear();
		}
	}
}
