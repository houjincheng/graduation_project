package anyviewj.debug.manager;

import anyviewj.debug.session.event.SessionListener;


/**
 * The Manager interface defines the API required by all manager objects
 * in JSwat. Managers are used to control a subset of features in JSwat,
 * such as breakpoints, source files, debugging context, etc. This Manager
 * API makes it easy for the Session class to deal with several managers at
 * once, and to handle future Managers.
 *
 * <p>Try to avoid circular dependencies between Managers when possible.
 * Unpredictable behavior can occur if one manager's init calls on
 * a second manager, which calls on the first manager (which has not
 * completed its initialization).</p> 
 * 
 */
public interface Manager extends SessionListener {
} // Manager
