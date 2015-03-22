package anyviewj.interfaces.ui.panel;

import anyviewj.debug.session.Session;
import javax.swing.JComponent;

/**
 * Panel defines the methods for all panels to implement.
 *
 * @author  ltt
 */
public interface Panel {

    /**
     * Returns a reference to the UI component which can be added to the
     * user interface component tree.
     *
     * @return  interface component.
     */
    JComponent getUI();

    /**
     * Update the display in the panel. Use the given session to fetch
     * any necessary data.
     *
     * @param  session  owning Session.
     */
    void refresh(Session session);

    /**
     * Update the display in the panel at some point in the near future.
     */
    void refreshLater();
} // Panel
