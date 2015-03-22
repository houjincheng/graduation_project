package anyviewj.debug.breakpoint.event;

import java.util.EventObject;
import anyviewj.debug.breakpoint.Breakpoint;

/**
 * An event which indicates that a breakpoint has changed status.
 *
 * @author  ltt
 */
public class BreakpointEvent extends EventObject {
    /** silence the compiler warnings */
    private static final long serialVersionUID = 1L;
    /** The breakpoint added event type. */
    public static final int TYPE_ADDED = 1;
    /** The breakpoint enabled event type. */
    public static final int TYPE_MODIFIED = 2;
    /** The breakpoint removed event type. */
    public static final int TYPE_REMOVED = 3;
    /** The breakpoint that changed.
     * @serial */
    protected Breakpoint bp;
    /** The type of breakpoint change.
     * @serial */
    protected int type;

    /**
     * Constructs a new BreakpointEvent.
     *
     * @param  source  Source of this event.
     * @param  bp      Breakpoint that changed.
     * @param  type    Type of breakpoint change.
     */
    public BreakpointEvent(Object source, Breakpoint bp, int type) {
        super(source);
        this.bp = bp;
        this.type = type;
    } // BreakpointEvent

    /**
     * Get the breakpoint that changed.
     *
     * @return  breakpoint request.
     */
    public Breakpoint getBreakpoint() {
        return bp;
    } // getBreakpoint

    /**
     * Get the breakpoint change type.
     *
     * @return  breakpoint change type (one of TYPE_* from BreakpointEvent).
     */
    public int getType() {
        return type;
    } // getType

    /**
     * Returns a String representation of this BreakpointEvent.
     *
     * @return  string representation of this BreakpointEvent.
     */
    @Override
	public String toString() {
        return "BreakpointEvent=[source=" + getSource() +
            ", breakpoint=" + bp +
            ", type=" + type + "]";
    } // toString
} // BreakpointEvent
