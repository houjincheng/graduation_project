package anyviewj.debug.actions;

import anyviewj.debug.session.Session;
import anyviewj.console.ConsoleCenter;
import java.awt.event.ActionEvent;

/**
 * Implements the Virtual Machine close action. It doens't do much
 * except contact the running VM and close it.
 *
 * @author ltt
 */
public class StopDebugAction extends AbstractDebugAction implements SessionAction {
    /** silence the compiler warnings */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new VMCloseAction object with the default action
     * command string of "vmClose".
     */
    public StopDebugAction() {
        super("vmClose");
    } // VMCloseAction

    /**
     * Performs the virtual machine close action. Finds the
     * appropriate VM and closes it.
     *
     * @param  event  action event
     */
    @Override
	public void actionPerformed(ActionEvent event) {
        Session session = ConsoleCenter.getCurrentSession();
        if (session.isActive()) {
            // Just disconnect with the debuggee VM.
            // If the VM was running before we connected,
            // it will remain running.
            session.deactivate(false, this);
//        	hou 2013年8月7日12:02:26
//        	如果定时调试处于活动状态，则取消定时调试任务，否则不作任何操作
        	notifyDebugFileTimeAction();
            //session.close(session);
        }
    } // actionPerformed
    /**
     * DebugFileTimeAction定时调试采用单例模式设计，
     * 所以取到的实例与按钮上的是同一个实例。
     * 执行此操作的结果是：如果定时调试处于活动状态，则被终止（再次点击定时调试时，从当前位置继续执行），
     * 若处于非活动状态，则不执行任何有效操作。
     */
    private void notifyDebugFileTimeAction()
    {
    	DebugProjectTimeAction.getDebugProjectTimeAction().deactive();
    }
    
    /**
     * Returns true to indicate that this action should be disabled
     * when the debuggee is resumed.
     *
     * @return  true to disable, false to leave as-is.
     */
    @Override
	public boolean disableOnResume() {
        return false;
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
} // VMCloseAction
