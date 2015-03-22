
package anyviewj.debug.source.event;

import java.util.EventListener;

/**
 * The listener interface for receiving changes in the current
 * debugger context. If the listener is looking for both location
 * information and thread information, then the listener should
 * save the thread change information when the thread change
 * event occurs. The following event will often be a location
 * change event.
 *
 */
public interface ContextListener extends EventListener {

    /**
     * Invoked when the current context has changed. The context
     * change event identifies which aspect of the context has
     * changed.
     *
     * @param  cce  context change event
     */
    public void contextChanged(ContextChangeEvent cce);
} // ContextListener
