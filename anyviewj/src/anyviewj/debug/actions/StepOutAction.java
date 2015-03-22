package anyviewj.debug.actions;

import java.awt.event.ActionEvent;

import anyviewj.debug.manager.ContextManager;
import anyviewj.debug.session.Session;

import com.sun.jdi.ThreadReference;
import com.sun.jdi.request.StepRequest;

/**
 * 调试时的方法步出的动作。2013年8月6日15:25:23 这里还有一个没有解决的问题是：当退后到最顶层的时候，继续点击步出按钮时，
 * 没有明确的处理策略。
 * 目前的处理策略是：全部执行完这个最顶层的方法。
 * 例如：退到main方法时，则执行完整个main方法。
 * 
 * @author hou
 */
public class StepOutAction extends BaseAction implements SessionAction {
	/** silence the compiler warnings */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new StepAction object with the default action command string of
	 * "step".
	 */
	public StepOutAction() {
		super("stepOut");
	} // StepAction

	/**
	 * Performs the step action.
	 * 
	 * @param event
	 *            action event
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		Session session = getSession(event);
		if (session.isActive()) {
			// Get the current thread.
			ContextManager contextManager = (ContextManager) session
					.getManager(ContextManager.class);
			ThreadReference current = contextManager.getCurrentThread();
			if (current == null) {
				System.out.println( "current == null " );

			} else {
				// Step a single line, out of functions.
				if (Stepping.step(session.getVM(), current,
						StepRequest.STEP_LINE, StepRequest.STEP_OUT, false,
						session.getProperty("excludes"))) {
					// Must use the Session to (quietly) resume the VM.
					session.resumeVM(this, true, true);
				}
			}
		}
	} // actionPerformed

	/**
	 * Returns true to indicate that this action should be disabled when the
	 * debuggee is resumed.
	 * 
	 * @return true to disable, false to leave as-is.
	 */
	@Override
	public boolean disableOnResume() {
		return true;
	} // disableOnResume

	/**
	 * Returns true to indicate that this action should be disabled when the
	 * debuggee is suspended.
	 * 
	 * @return true to disable, false to leave as-is.
	 */
	@Override
	public boolean disableOnSuspend() {
		return false;
	} // disableOnSuspend

	/**
	 * Returns true to indicate that this action should be disabled while the
	 * session is active, and enabled when the session is not active. This is
	 * the opposite of how SessionActions normally behave.
	 * 
	 * @return true to disable when active, false to enable.
	 */
	@Override
	public boolean disableWhenActive() {
		return false;
	} // disableWhenActive
} // StepAction
