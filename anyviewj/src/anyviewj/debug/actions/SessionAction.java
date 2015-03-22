package anyviewj.debug.actions;

/**
 * Marks an action that is ineffective when the Session is inactive.
 * This is the interface which actions implement when they want their
 * corresponding UI elements to be enabled and disabled when the
 * session is activated and deactivated.
 *
 * <p>Note that there is only ever one instance of any particular
 * action. It is not the action that is being disabled, it is the
 * corresponding menu item or toolbar button.</p>
 *
 * @author  ltt
 */
public interface SessionAction {

    /**
     * Returns true to indicate that this action should be disabled
     * when the debuggee is resumed.
     *
     * @return  true to disable, false to enable.
     */
    public boolean disableOnResume();

    /**
     * Returns true to indicate that this action should be disabled
     * when the debuggee is suspended.
     *
     * @return  true to disable, false to enable.
     */
    public boolean disableOnSuspend();

    /**
     * Returns true to indicate that this action should be disabled
     * while the session is active, and enabled when the session
     * is not active. This is the opposite of how SessionActions
     * normally behave.
     *
     * @return  true to disable when active, false to enable.
     */
    public boolean disableWhenActive();
} // SessionAction
