/*********************************************************************
 *
 *      Copyright (C) 1999-2005 Nathan Fiedler
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * $Id: VMSuspendAction.java 1813 2005-07-17 05:55:46Z nfiedler $
 *
 ********************************************************************/

package anyviewj.debug.actions;

import java.awt.event.ActionEvent;

import anyviewj.debug.session.Session;

/**
 * Implements the Virtual Machine suspend action. It doens't do
 * much except contact the running VM and suspend it.
 *
 * @author  Nathan Fiedler
 */
public class PauseDebugAction extends BaseAction implements SessionAction {
    /** silence the compiler warnings */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new VMSuspendAction object with the default action
     * command string of "vmSuspend".
     */
    public PauseDebugAction() {
        super("vmSuspend");
    } // VMSuspendAction

    /**
     * Performs the virtual machine suspend action. Finds the
     * appropriate VM and suspends it.
     *
     * @param  event  action event
     */
    @Override
	public void actionPerformed(ActionEvent event) {
        // ask the user for the class name
        Session session = getSession(event);
        try {
//        	hou 2013年8月7日12:02:26
//        	如果定时调试处于活动状态，则取消定时调试任务，否则不作任何操作
        	notifyDebugFileTimeAction();
            session.suspendVM(this);
            
        } catch (IllegalStateException ise) {
            // ignore it
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
        return true;
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
} // VMSuspendAction
