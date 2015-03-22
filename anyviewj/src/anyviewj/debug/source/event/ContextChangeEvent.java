
package anyviewj.debug.source.event;

import java.util.EventObject;

/**
 * An event which indicates that the debugger context has changed. This
 * includes the current thread, current stack frame, and current
 * stepping location.
 *
 */
public class ContextChangeEvent extends EventObject {
    /** silence the compiler warnings */
    private static final long serialVersionUID = 1L;
    /** The current thread changed event type. */
    public static final int TYPE_THREAD = 0x01;
    /** The current frame changed event type. */
    public static final int TYPE_FRAME = 0x02;
    /** The current location changed event type. */
    public static final int TYPE_LOCATION = 0x04;
    /** The type flags of this context change.
     * @serial */
    private int types;
    /** True if this event is expected to be brief in duration. */
    private boolean isBrief;

    /**
     * Constructs a new ContextChangeEvent.
     *
     * @param  source  Source of this event.
     * @param  types   A set of type flags.
     * @param  brief   true if expected to be brief in duration.
     */
    public ContextChangeEvent(Object source, int types, boolean brief) {
        super(source);
        this.types = types;
        isBrief = brief;
    } // ContextChangeEvent

    /**
     * Indicates if this event marks the beginning of a brief change.
     * That is, in a short amount of time the state is expected to
     * change again.
     *
     * @return  true if event is brief; false otherwise.
     */
    public boolean isBrief() {
        return isBrief;
    } // isBrief

    /**
     * Compares the type of this event to the given argument and returns
     * true if they match. This event may match more than one type of
     * event. For instance, if a thread change occurs, the frame and
     * location will also change at the same time.
     *
     * @param  type  One of <code>THREAD</code>,
     *                      <code>FRAME</code>, or
     *                      <code>LOCATION</code>.
     * @return  true if this event is of the given type
     */
    public boolean isType(int type) {
        return (this.types & type) > 0;
    } // isType

    /**
     * Returns a String representation of this ContextChangeEvent.
     *
     * @return  A String representation of this ContextChangeEvent.
     */
    @Override
	public String toString() {
        StringBuffer buf = new StringBuffer("ContextChange=[source=");
        buf.append(getSource());
        buf.append(", types=");
        if ((types & TYPE_THREAD) > 0) {
            buf.append("<thread>");
        }
        if ((types & TYPE_FRAME) > 0) {
            buf.append("<frame>");
        }
        if ((types & TYPE_LOCATION) > 0) {
            buf.append("<location>");
        }
        buf.append(", brief=");
        buf.append(isBrief);
        buf.append(']');
        return buf.toString();
    } // toString
} // ContextChangeEvent
