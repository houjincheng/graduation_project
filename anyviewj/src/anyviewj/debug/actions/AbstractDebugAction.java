package anyviewj.debug.actions;

import javax.swing.AbstractAction;

/**
 * Base action class which all other actions subclass. Provides some
 * utility functions needed by nearly all of the actions in JSwat.
 *
 * @author  Nathan Fiedler
 */
public abstract class AbstractDebugAction extends AbstractAction {

    /**
     * Creates a new JSwatAction command with the given
     * action command string.
     *
     * @param  name  action command string
     */
    public AbstractDebugAction(String name) {
        super(name);
    } // JSwatAction

    /**
     * Display an error message in a dialog.
     *
     * @param  o    Object with which to find the parent frame.
     *              Could be a subclass of EventObject or Component.
     * @param  msg  error message to be displayed.
     */
    public static void displayError(Object o, String msg) {
        //Session session = getSession(o);
        //UIAdapter adapter = session.getUIAdapter();
        //adapter.showMessage(UIAdapter.MESSAGE_ERROR, msg);
    } // displayError

    /**
     * Find the hosting frame for this object. Often used
     * when displaying dialogs which require a host frame.
     *
     * @param  o  Object with which to find the parent frame.
     *            Could be a subclass of EventObject or Component.
     * @return  hosting frame or null if none.
     */
    /*
    public static Frame getFrame(Object o) {
        // Use the SessionFrameMapper to get the Session for the object.
        return SessionFrameMapper.getOwningFrame(o);
    } // getFrame
    */
    /**
     * Finds the Session that is associated with the window
     * that contains the component that is the source of the
     * given object.
     *
     * @param  o  Object with which to find the parent frame.
     *            Could be a subclass of EventObject or Component.
     * @return  Session instance, or null if error.
     */
    /*
    public static Session getSession(Object o) {
        // Use the SessionFrameMapper to get the Session for the event.
        return SessionFrameMapper.getSessionForFrame(getFrame(o));
    } // getSession
    */
} // JSwatAction
