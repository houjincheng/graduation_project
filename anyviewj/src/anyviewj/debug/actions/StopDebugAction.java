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
//        	hou 2013��8��7��12:02:26
//        	�����ʱ���Դ��ڻ״̬����ȡ����ʱ�������񣬷������κβ���
        	notifyDebugFileTimeAction();
            //session.close(session);
        }
    } // actionPerformed
    /**
     * DebugFileTimeAction��ʱ���Բ��õ���ģʽ��ƣ�
     * ����ȡ����ʵ���밴ť�ϵ���ͬһ��ʵ����
     * ִ�д˲����Ľ���ǣ������ʱ���Դ��ڻ״̬������ֹ���ٴε����ʱ����ʱ���ӵ�ǰλ�ü���ִ�У���
     * �����ڷǻ״̬����ִ���κ���Ч������
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
