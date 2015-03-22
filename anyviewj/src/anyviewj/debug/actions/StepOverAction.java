package anyviewj.debug.actions;

import anyviewj.console.ConsoleCenter;
import anyviewj.debug.manager.ContextManager;
import anyviewj.debug.session.Session;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.request.StepRequest;
import java.awt.event.ActionEvent;

/**
 * Implements the next instruction action.
 *
 * @author ltt
 */
public class StepOverAction extends AbstractDebugAction implements SessionAction {
    /** silence the compiler warnings */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new NextAction object with the default action
     * command string of "next".
     */
    public StepOverAction() {
        super("next");
    } // NextAction

    /**
     * Performs the next action.
     *
     * @param  event  action event
     */
    @Override
	public void actionPerformed(ActionEvent event) {
    	Session session = ConsoleCenter.getCurrentSession();
    	if(session==null)
    		return;

//      	  List list = ((JavaProjectManager) ConsoleCenter.projectManager).ClosedProject;
//      	  System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
//      	Project prj = ((JavaProjectManager) ConsoleCenter.projectManager).getCurProject();
//      	if(prj==null)
//      	{
//      	  System.out.println("-------------------------------");	
//      	  session.deactivate(false, this);	
//      	DebugProjectTimeAction.getDebugProjectTimeAction().deactive();
//      	System.out.println("-------------------------------  Closed!!");		
//      	}
      	
      	
      	  
      	 
      	if (session.isActive()) {
            // Get the current thread.
            ContextManager contextManager = (ContextManager)
                session.getManager(ContextManager.class);        	
            ThreadReference current = contextManager.getCurrentThread();   
            if (current == null) {
                //session.getUIAdapter().showMessage(
                //    UIAdapter.MESSAGE_ERROR,
                //    Bundle.getString("noCurrentThread"));
            } else {
                // Step a single line, over functions.
                if (Stepping.step(
                        session.getVM(), current, StepRequest.STEP_LINE,
                        StepRequest.STEP_OVER, false,
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
} // NextAction
