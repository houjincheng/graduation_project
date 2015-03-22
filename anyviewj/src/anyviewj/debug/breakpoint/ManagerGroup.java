package anyviewj.debug.breakpoint;

import anyviewj.debug.session.Session;
import java.util.prefs.Preferences;

/**
 * Special subclass of BreakpointGroup that has a reference to
 * the Session object. It is created by the breakpoint manager
 * and holds a reference to the Session that created the manager.
 * Other breakpoint groups and breakpoints contained within this
 * group will then have easy access to the owning Session.
 *
 * @author  ltt
 */
public class ManagerGroup extends BreakpointGroup {
    /** The group's name. */
    protected static final String DEFAULT_NAME = "Default";
    /** Owning session. */
    protected Session session;

    /**
     * Constructs a ManagerGroup object.
     *
     * @param  session  Session that we belong to.
     */
    public ManagerGroup(Session session) {
        super(DEFAULT_NAME);
        this.session = session;
    } // ManagerGroup
    
    public ManagerGroup() {
        super(DEFAULT_NAME);       
    } // ManagerGroup

    /**
     * Returns a reference to the owning Session.
     *
     * @return  owning Session.
     */
    @Override
	public Session getSession() {
        return session;
    } // getSession
    
    
    @Override
	public void setSession(Session session) {
        this.session = session;
    } // setSession

    /**
     * Reads the breakpoint properties from the given preferences node.
     *
     * @param  prefs  Preferences node from which to initialize this
     *                breakpoint.
     * @return  true if successful, false otherwise.
     */
    @Override
	public boolean readObject(Preferences prefs) {
        if (!super.readObject(prefs)) {
            return false;
        }
        // Make sure our name is always the default.
        setName(DEFAULT_NAME);
        return true;
    } // readObject

    /**
     * Returns a string representation of this.
     *
     * @return  string of this.
     */
    @Override
	public String toString() {
        return "ManagerGroup";
    } // toString
} // ManagerGroup
