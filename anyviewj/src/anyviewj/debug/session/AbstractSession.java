package anyviewj.debug.session;

import anyviewj.debug.manager.BreakpointManager;
import anyviewj.debug.manager.Manager;
import anyviewj.debug.session.event.SessionListener;
import java.util.Hashtable;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;

public abstract class AbstractSession implements PreferenceChangeListener, Session {
    /** Logger. */
    private static Logger logger;
    /** List of SessionListener objects. */
    private SessionListenerList listenerList;
    /** Table of Manager objects keyed by their Class. Used to
     * retrieve managers from the Session. */
    private Hashtable managerTable;
    /** Preferences for this class. */
    private Preferences preferences;

    static {
        // Initialize the logger.
        //logger = Logger.getLogger("com.bluemarsh.jswat.Session");
        //com.bluemarsh.jswat.logging.Logging.setInitialState(logger);
    }

    /**
     * Returns the Logger instance for this class.
     *
     * @return  session logger.
     */
    protected static Logger logger() {
        return logger;
    }

    /**
     * Constructs an AbstractSession.
     */
    public AbstractSession() {
        //logger.info("creating listener list and manager table");
        listenerList = new SessionListenerList();
        managerTable = new Hashtable();
        preferences = Preferences.userRoot().node(
            "anyviewj/interpret/session");
        preferences.addPreferenceChangeListener(this);
    }

//    public void check(SessionListener listener){
//      if(listenerList.listenerList.contains(listener))
//    	  System.out.println("$$$$$$$$$$$$$$$$$$  Contain !!!  ");
//    }
    
    @Override
	public void addListener(SessionListener listener) {
    	//check(listener);
        listenerList.add(listener, this, isActive());
    }

    /**
     * Returns the session listener list.
     *
     * @return  session listener list.
     */
    protected SessionListenerList getListeners() {
        return listenerList;
    }

    @Override
	public Manager getManager(Class managerClass) {
        Manager manager = (Manager) managerTable.get(managerClass);
        if (manager == null) {
            try {
                // Create the Manager object.
                //logger.info("creating manager: " + managerClass.getName());
            	System.out.println( "creating manager: " + managerClass.getName() );
            	
                manager = (Manager) managerClass.newInstance();
                // Put the Manager in the table.
                managerTable.put(managerClass, manager);
                // Add the Manager as a SessionListener.
                addListener(manager);
            } catch (Exception e) {
                //getStatusLog().writeStackTrace(e);
                // Return the null Manager.
            }
        }
        return manager;
    }

    @Override
	public String getProperty(String key) {
        String defaultName = preferences.get("defaultName", "default");
        Preferences prefs = preferences.node(defaultName);
        String value = prefs.get(key, null);
        if (value == null) {
            // See if the value is a long one.
            value = prefs.get(key + ".1", null);
            if (value != null) {
                // It is long, pull all of the pieces together.
                StringBuffer buf = new StringBuffer(
                    Preferences.MAX_VALUE_LENGTH * 2);
                int ki = 1;
                do {
                    buf.append(value);
                    ki++;
                    value = prefs.get(key + '.' + ki, null);
                } while (value != null);
                value = buf.toString();
            }
        }
        return value;
    }

    @Override
	public String[] getPropertyKeys() {
        try {
            String defaultName = preferences.get("defaultName", "default");
            Preferences prefs = preferences.node(defaultName);
            return prefs.keys();
        } catch (BackingStoreException bse) {
            return null;
        }
    }

    @Override
	public void preferenceChange(PreferenceChangeEvent evt) {
        // We don't use this, but subclasses might.
    }

    /**
     * Returns the Preferences instance for this session.
     *
     * @return  preferences node.
     */
    protected Preferences preferences() {
        return preferences;
    }

    /**
     * Removes all managers that are attached to this session.
     */
    protected void removeAllManagers() {
        managerTable.clear();
    }

    @Override
	public void removeListener(SessionListener listener) {
        listenerList.remove(listener, this);
        if (listener instanceof Manager) {
            // Managers must be removed from the manager table.
            managerTable.remove(listener);
        }
    }

    @Override
	public String setProperty(String key, String value) {
        String old = getProperty(key);
        String defaultName = preferences.get("defaultName", "default");
        Preferences prefs = preferences.node(defaultName);
        int ki = 1;
        if (value == null) {
            prefs.remove(key);
        } else {
            int valueLen = value.length();
            if (valueLen > Preferences.MAX_VALUE_LENGTH) {
                // Chop the long value into manageable pieces.
                for (int index = 0; index < valueLen;
                     index += Preferences.MAX_VALUE_LENGTH, ki++) {
                    int end = index + Preferences.MAX_VALUE_LENGTH;
                    if (end > valueLen) {
                        end = valueLen;
                    }
                    prefs.put(key + '.' + ki, value.substring(index, end));
                }
                // Remove the original key, if any, so we are forced
                // to read the long value in getProperty().
                prefs.remove(key);
            } else {
                prefs.put(key, value);
            }
        }
        // Truncate the old long value values.
        String lkey = key + '.' + ki;
        while (prefs.get(lkey, null) != null) {
            prefs.remove(lkey);
            ki++;
            lkey = key + '.' + ki;
        }
        return old;
    }
    
    @Override
	public  void addManager(Class classname, BreakpointManager bkm)
    {
    	managerTable.put(classname, bkm);
    	
    }
}
