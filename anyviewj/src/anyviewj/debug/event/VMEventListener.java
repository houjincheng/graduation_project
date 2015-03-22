

package anyviewj.debug.event;

import com.sun.jdi.event.Event;
import java.util.EventListener;


public interface VMEventListener extends EventListener {
    /** Value for which no priority is higher. */
    public static final int PRIORITY_HIGHEST = 1023;
    /** Value for a Breakpoint listener. */
    public static final int PRIORITY_BREAKPOINT = 1023;
    /** Value for Session listener. */
    public static final int PRIORITY_SESSION = 511;
    /** Value for a high priority listener. */
    public static final int PRIORITY_HIGH = 255;
    /** Value for a default priority listener. */
    public static final int PRIORITY_DEFAULT = 127;
    /** Value for a low priority listener. */
    public static final int PRIORITY_LOW = 1;
    /** Value for which no priority is lower. */
    public static final int PRIORITY_LOWEST = 1;

    /**
     * Invoked when a VM event has occurred.
     *
     * @param  e  VM event
     * @return  true if debuggee VM should be resumed, false otherwise.
     */
    public boolean eventOccurred(Event e);
} // VMEventListener
