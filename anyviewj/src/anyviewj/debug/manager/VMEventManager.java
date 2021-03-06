package anyviewj.debug.manager;

//import com.bluemarsh.jswat.Log;
import anyviewj.callstack.StackFrameAnalyzer;
import anyviewj.debug.actions.DebugProjectTimeAction;
import anyviewj.debug.event.VMEventListener;
import anyviewj.debug.session.BasicSession;
import anyviewj.debug.session.Session;
import anyviewj.debug.session.event.SessionEvent;
import anyviewj.util.PriorityList;
import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.EventIterator;
import com.sun.jdi.event.EventSet;
import com.sun.jdi.event.EventQueue;
import java.util.logging.Logger;

import javax.swing.plaf.basic.BasicSeparatorUI;

/**
 * This class is responsible for maintaining a list of all the objects
 * interested in events sent from the back-end of the JPDA debugger.
 * Listeners registered for VM events are listed according to the event
 * they are interested in. Within each of these lists the listeners are
 * sorted in priority order. Those with a higher priority will be
 * notified of the event before those of a lower priority.
 *
 */

public class VMEventManager implements Manager, Runnable {
    /** A null array to be shared by all empty listener lists. */
    private static final Object[] NULL_ARRAY = new Object[0];
    /** Logger. */
    private static Logger logger;
    /** VM event queue. */
    private EventQueue eventQueue;
    /** True if we are connected to the debuggee VM. */
    private boolean vmConnected;
    /** Owning session. */
    private Session owningSession;
    /** The list of event class-listener pairs.  */
    private Object[] listenerList;

    static {
        // Initialize the logger.
        //logger = Logger.getLogger("com.bluemarsh.jswat.event");
        //com.bluemarsh.jswat.logging.Logging.setInitialState(logger);
    }

    /**
     * Creates a new VMEventManager object.
     */
    public VMEventManager() {
        listenerList = NULL_ARRAY;
    } // VMEventManager

    /**
     * Called when the Session has activated. This occurs when the
     * debuggee has launched or has been attached to the debugger.
     *
     * @param  sevt  session event.
     */
    @Override
	public void activated(SessionEvent sevt) {
        // Start the event handling thread. Continuously monitors
        // the VM for new events.
    	System.out.println("VMEventManager activated");
        eventQueue = sevt.getSession().getVM().eventQueue();
        vmConnected = true;
        new Thread(this, "event handler").start();
        //logger.info("event listening thread started");
    } // activated

    /**
     * Adds the given listener as a listener for events of the
     * given type. When an event of type <code>event</code> occurs,
     * all registered listeners for that type will be notified.
     *
     * @param  event     VM event to listen for.
     * @param  listener  Listener to add for event.
     * @param  priority  Priority for this listener (1-255), where
     *                   higher values give higher priority.
     */
    public synchronized void addListener(Class event, VMEventListener listener,
                                         int priority) {
        // Do the usual arguments checking.

        if ((priority > VMEventListener.PRIORITY_HIGHEST)
            || (priority < VMEventListener.PRIORITY_LOWEST)) {
            throw new IllegalArgumentException("priority out of range");
        }
        if (listener == null) {
            throw new IllegalArgumentException("listener cannot be null");
        }

        // Handle the special-case priorities.
       
        //if ((priority == VMEventListener.PRIORITY_BREAKPOINT)
        //    && !(listener instanceof Breakpoint)){
        //    throw new IllegalArgumentException(
        //        "priority only for breakpoints");
        //}
        if ((priority == VMEventListener.PRIORITY_SESSION)
            && !(listener instanceof Session)) {
            throw new IllegalArgumentException("priority only for Session");
        }

        PriorityList list = null;
        if (listenerList == NULL_ARRAY) {
            // If this is the first listener added, initialize the lists.
            list = new PriorityList();
            listenerList = new Object[] { event, list };
        } else {

            // Find the event in our list, if any.
            for (int i = listenerList.length - 2; i >= 0; i -= 2) {
                if (event == listenerList[i]) {
                    list = (PriorityList) listenerList[i + 1];
                }
            }
            if (list == null) {
                // This is a new event, create a new listener list.
                list = new PriorityList();
                // Copy the array and add the new listener list.
                int i = listenerList.length;
                Object[] tmp = new Object[i + 2];
                System.arraycopy(listenerList, 0, tmp, 0, i);
                tmp[i] = event;
                tmp[i + 1] = list;
                listenerList = tmp;
            }
        }
        // Add the listener to the event's listener list.
        list.add(listener, priority);
        //if (logger.isLoggable(Level.INFO)) {
        //    logger.info(
        //        "added " + Names.justTheName(event.getName())
        //        + " listener: "
        //        + Names.justTheName(listener.getClass().getName()));
        //}
    } // addListener

    /**
     * Called when the Session is about to be closed.
     *
     * @param  sevt  session event.
     */
    @Override
	public void closing(SessionEvent sevt) {
        owningSession = null;
        eventQueue = null;
    } // closing

    /**
     * Called when the Session has deactivated. The debuggee VM is no
     * longer connected to the Session.
     *
     * @param  sevt  session event.
     */
    @Override
	public void deactivated(SessionEvent sevt) {
    } // deactivated

    /**
     * Get the priority list matching the given event. Checks
     * the event's class and whether it "is an instance of" any
     * of the events in our list.
     *
     * @param  event  VM event to find in list.
     * @return  PriorityList if found, or null.
     */
    protected synchronized PriorityList getList(Object event) {
        for (int i = listenerList.length - 2; i >= 0; i -= 2) {
            if (((Class) listenerList[i]).isInstance(event)) {
                return (PriorityList) listenerList[i + 1];
            }
        }
        return null;
    } // getList

    /**
     * Called after the Session has added this listener to the Session
     * listener list.
     *
     * @param  session  the Session.
     */
    @Override
	public void opened(Session session) {
        owningSession = session;
    } // opened

    /**
     * Send the given event to the listeners on the list. The listeners
     * are notified in order of the priority enforced by the list.
     *
     * @param  event      event to process.
     * @param  listeners  list of listeners to handle event.
     * @return  true to resume VM, false to suspend VM.
     */
    protected boolean processEvent(Event event, PriorityList listeners) {
        // Process the listeners in priority order, as enforced
        // by the priority list object.
        boolean shouldResume = true;
        for (int i = 0; i < listeners.size(); i++) {
            VMEventListener vml = (VMEventListener) listeners.get(i);
            // Let the listener know about the event.
            try {
                // The listener will indicate if we should resume
                // the debuggee VM or not. All listeners must agree
                // to resume for the debuggee VM to run again.
//            	System.out.println("vml is : ----  "+vml.toString());
                  shouldResume &= vml.eventOccurred(event);
            } catch (VMDisconnectedException vmde) {
                throw vmde;
            } catch (Exception e) {
                //Log out = owningSession.getStatusLog();
                //out.writeStackTrace(e);
                //out.writeln("Event processing continuing...");
                // Assume that we should stop after processing the listeners.
                shouldResume = false;
            }
        }
        return shouldResume;
    } // processEvent

    /**
     * Removes the given listener from the event listener list.
     *
     * @param  event     VM event to listen for.
     * @param  listener  Listener to remove from list.
     */
    public synchronized void removeListener(Class event,
                                            VMEventListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("listener cannot be null");
        }
        // Is the event in our list?
        int index = -1;
        for (int i = listenerList.length - 2; i >= 0; i -= 2) {
            if (listenerList[i] == event) {
                index = i;
                break;
            }
        }

        // If so, remove the listener from the list.
        if (index != -1) {
            PriorityList list = (PriorityList) listenerList[index + 1];
            list.remove(listener);
            //if (logger.isLoggable(Level.INFO)) {
            //    logger.info(
            //        "removed " + Names.justTheName(event.getName())
            //        + " listener: "
            //        + Names.justTheName(listener.getClass().getName()));
            //}
        }
        // Note that we never remove the priority list from our array.
        // Why bother when we may just have to re-add it anyway?
    } // removeListener

    /**
     * Called when the debuggee is about to be resumed.
     *
     * @param  sevt  session event.
     */
    @Override
	public void resuming(SessionEvent sevt) {
    } // resuming

    /**
     * Start waiting for events from the back-end of the JPDA debugger.
     * When events occur the notifications will be sent out to all of
     * the registered listeners. This thread dies automatically when
     * the debuggee VM disconnects from the debugger.
     *
     * @see #activate
     * @see #deactivate
     */
    @Override
	public void run() {
        // Run until we get interrupted or the VM disconnects.
        while (true) {
            try {
                // ..wait for events to happen.
                EventSet set = eventQueue.remove();
                //System.out.println("eventQueue size ="+set.size());
                EventIterator iter = set.eventIterator();
                boolean shouldResume = true;
                while (iter.hasNext()) {
                    Event event = iter.next();
//                    System.out.println("received event:"+event);
                    
//                  hou
                    notifyStackPane( event );
//                    hou 2013年8月14日11:26:47
                    notifyDebugFileTimeAction( event );
                    

                    
                    String es = event.toString();
                    if(es.indexOf("java.lang.Thread.exit") > 0){
                    	//System.out.println("catch Thread");
                    	this.owningSession.getVM().resume();
                    }
                    //if (logger.isLoggable(Level.INFO)) {
                    //    logger.info("received event: " + event);
                    //}

                    // Notify the appropriate listeners of the event.
                    PriorityList list = getList(event);
                    if (list != null) {
                        // processEvent() returns true if we should resume.
                        shouldResume &= processEvent(event, list);
                        //if (logger.isLoggable(Level.INFO)) {
                        //    logger.info("processed event: " + event);
                        //}
                    }
                    else
                    {
                    }
                }
                if (shouldResume) {
                    // Resume only if everyone said it was okay to go.
                    set.resume();
                    //logger.info("resuming VM through event set");
                }
            } catch (InterruptedException ie) {
                // Nothing left to do but leave.
                break;
            } catch (VMDisconnectedException vmde) {
                // Notify the session and break out.
                owningSession.disconnected(this);
                break;
            } catch (Exception e) {
            	e.printStackTrace();
                // We do not care what FindBugs says about this catch.
                // We absolutely cannot terminate this loop -- catch the
                // exception, report it, and continue reading events.
                //Log out = owningSession.getStatusLog();
                //out.writeStackTrace(e);
            }
        }
    } // run

    /**
     * hou 2013年8月14日11:26:25
     * 这是一个不安全的做法，其事实上是赋予了DebugFileTimeAction最高优先级。
     * 因为假如让DebugFileTimeAction实现VMEventListener,
     * 其最多只获得一个DEFAULT权限，这最终造成了线程不同步。
     * 因为拥有最高权限，可能对程序造成极大破坏，能做的是其自我限制。
     * @param e
     */
    private void notifyDebugFileTimeAction( Event e )
    {
    	DebugProjectTimeAction.getDebugProjectTimeAction().eventOccurred( e );
    }
    
    private void notifyStackPane( Event e )
    {
    	StackFrameAnalyzer
    	.getInstance( ( ( BasicSession )owningSession ).getConsoleCenter() )
    	.eventOccurred( e );
    }
    /**
     * Called when the debuggee has been suspended.
     *
     * @param  sevt  session event.
     */
    @Override
	public void suspended(SessionEvent sevt) {
    } // suspended
    
    
    @Override
	public void setSession(Session e) {
    } //setSession
} // VMEventManager
