package anyviewj.debug.breakpoint.event;

import java.util.EventListener;


/**
 * The listener interface for receiving changes to groups.
 *
 * @author  Nathan Fiedler
 */
public interface GroupListener extends EventListener {

    /**
     * Invoked when a group has been added.
     *
     * @param  event  group change event
     */
    public void groupAdded(GroupEvent event);

    /**
     * Invoked when a group has been disabled.
     *
     * @param  event  group change event
     */
    public void groupDisabled(GroupEvent event);

    /**
     * Invoked when a group has been enabled.
     *
     * @param  event  group change event
     */
    public void groupEnabled(GroupEvent event);

    /**
     * Invoked when a group has been removed.
     *
     * @param  event  group change event
     */
    public void groupRemoved(GroupEvent event);
} // GroupListener
