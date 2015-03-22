package anyviewj.debug.actions;

//import com.bluemarsh.jswat.ui.UIAdapter;
import anyviewj.console.ConsoleCenter;
import anyviewj.debug.manager.ContextManager;
import anyviewj.debug.session.Session;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.request.StepRequest;
import java.awt.event.ActionEvent;

/**
 * Implements the step action.
 *
 * @author  ltt
 */
public class StepIntoAction extends AbstractDebugAction implements SessionAction {
    /** silence the compiler warnings */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new StepAction object with the default action
     * command string of "step".
     */
    public StepIntoAction() {
        super("stepInto");
    } // StepAction

    /**
     * Performs the step action.
     *
     * @param  event  action event
     */
    @Override
	public void actionPerformed(ActionEvent event) {
    	Session session = ConsoleCenter.getCurrentSession();
        if (session.isActive()) {
            // Get the current thread.
        	
        	ContextManager contextManager= (ContextManager)
                    session.getManager(ContextManager.class);
            ThreadReference current = contextManager.getCurrentThread();
            //ThreadReference current = (ThreadReference)(session.getVM().allThreads().get(0));
            if (current == null) {
                //session.getUIAdapter().showMessage(
                //    UIAdapter.MESSAGE_ERROR,
                //    Bundle.getString("noCurrentThread"));

            } else {
                // Step a single line, into functions.
                if (Stepping.step(
                        session.getVM(), current, StepRequest.STEP_LINE,
                        StepRequest.STEP_INTO, false,
                        session.getProperty("excludes"))) {
                    // Must use the Session to (quietly) resume the VM.
                    session.resumeVM(this, true, true);
                }
            }
        }
    } // actionPerformed

    /**
     * Returns true to indicate that this action should be disabled
     * when the debuggee is resumed.
     *
     * @return  true to disable, false to leave as-is.
     */
    @Override
	public boolean disableOnResume() {
        return true;
    } // disableOnResume

    /**
     * Returns true to indicate that this action should be disabled
     * when the debuggee is suspended.
     *
     * @return  true to disable, false to leave as-is.
     */
    @Override
	public boolean disableOnSuspend() {
        return false;
    } // disableOnSuspend

    /**
     * Returns true to indicate that this action should be disabled
     * while the session is active, and enabled when the session
     * is not active. This is the opposite of how SessionActions
     * normally behave.
     *
     * @return  true to disable when active, false to enable.
     */
    @Override
	public boolean disableWhenActive() {
        return false;
    } // disableWhenActive
} // StepAction
