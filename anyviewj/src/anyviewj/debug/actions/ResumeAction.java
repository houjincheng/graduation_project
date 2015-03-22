package anyviewj.debug.actions;

import anyviewj.console.ConsoleCenter;
import anyviewj.debug.session.Session;
import java.awt.event.ActionEvent;

/**
 * Implements the Virtual Machine resume action. It doesn't do
 * much except contact the running VM and resume it.
 *
 * @author  ltt
 */
public class ResumeAction extends AbstractDebugAction implements SessionAction {
    /** silence the compiler warnings */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new VMResumeAction object with the default action
     * command string of "vmResume".
     */
    public ResumeAction() {
        super("vmResume");
    } // VMResumeAction

    /**
     * Performs the virtual machine resume action. Finds the
     * appropriate VM and resumes it.
     *
     * @param  event  action event
     */
    @Override
	public void actionPerformed(ActionEvent event) {
        // get the VM and have it resume execution
        Session session = ConsoleCenter.getCurrentSession();
        try {
            session.resumeVM(this, false, false);
        } catch (IllegalStateException ise) {
            // ignore it
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
} // VMResumeAction
