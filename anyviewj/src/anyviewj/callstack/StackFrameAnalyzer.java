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
	private String oldEvent = ""; //��Ϊ���յ����ظ���2�ζϵ��¼�
	private StackFrameAnalyzer() {}

	public static StackFrameAnalyzer getInstance( ConsoleCenter center ) {
		
		stackFrameInnerFrame = center.mainFrame.leftPartPane.debugPane.stackFrameInnerFrame;
		return analyzer;
	}

	/**
	 * VMEventManager�����������¼�����֪ͨ�����������
	 * @param e
	 */
	public void eventOccurred(Event e) {
		

//		û�н��յ�VMDepthEvent
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
//			��Ϊ����ܵ�2���ظ��Ķϵ��¼�
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
