
package anyviewj.interfaces.view;

import anyviewj.console.ConsoleCenter;
import anyviewj.debug.manager.Manager;
import anyviewj.debug.session.Session;
import anyviewj.debug.session.event.SessionEvent;
import anyviewj.debug.session.event.SessionListener;
import anyviewj.debug.source.SourceSource;
import anyviewj.interfaces.ui.panel.CodePane;
import anyviewj.interfaces.ui.panel.RightPartPane;
//import com.bluemarsh.jswat.ui.UIAdapter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.SwingUtilities;
import java.lang.reflect.InvocationTargetException;

/**
 * The ViewManager class is responsible for maintaining the list of open
 * views. It also handles switching between the different display modes
 * for the views.
 *
 * @author  ltt
 */
public class ViewManager implements Manager, Runnable {
    /** The Session we belong to. */
    private Session owningSession;
    /** Table of open view objects, where the keys are the Views and the
     * values are the SourceSources. */
    private Hashtable viewToSourceMap;
    /** Table of open view objects, where the keys are the SourceSources
     * and the values are the Views. */
    private Hashtable sourceToViewMap;

    /**
     * Constructs a ViewManager.
     */
    public ViewManager() {
        viewToSourceMap = new Hashtable();
        sourceToViewMap = new Hashtable();
    } // ViewManager

    /**
     * Called when the Session has activated. This occurs when the
     * debuggee has launched or has been attached to the debugger.
     *
     * @param  sevt  session event.
     */
    @Override
	public void activated(SessionEvent sevt) {
        refreshViews();
    } // activated
    
    @Override
	public void setSession(Session e) {  
    	owningSession = e;
    	owningSession.addListener(this);
    } //setSession

    /**
     * Adds the view to this manager's list of open views.
     *
     * @param  view  view to be added to the display.
     * @param  src   source of the view.
     */
    public void addView(View view, SourceSource src) {
        viewToSourceMap.put(view, src);
        sourceToViewMap.put(src, view);
        if (view instanceof SessionListener) {
            owningSession.addListener((SessionListener) view);
        }
    } // addView

    /**
     * Called when the Session is about to be closed.
     *
     * @param  sevt  session event.
     */
    @Override
	public void closing(SessionEvent sevt) {
        // Remove all of the open source views as session listeners.
        Enumeration views = viewToSourceMap.keys();
        while (views.hasMoreElements()) {
            View view = (View) views.nextElement();
            if (view instanceof SessionListener) {
                owningSession.removeListener((SessionListener) view);
            }
        }
        owningSession = null;
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
     * Retrieves the view for the given source. If no view has been
     * opened for this source, return null.
     *
     * @param  src  source object.
     * @return  open view for source, or null if none.
     */
    public View getView(SourceSource src) {
        return (View) sourceToViewMap.get(src);
    } // getView

    /**
     * Returns an enumeration over the set of open views.
     *
     * @return  enumeration of open views.
     */
    public Enumeration getViews() {
        return viewToSourceMap.keys();
    } // getViews

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
     * Refresh the open views immediately.
     */
    public void refreshViews() {
        // Manually refresh all of the open views. Better this than
        // having the views do it themselves; in that case our
        // openFile() causes the view refresh() to be invoked twice,
        // which is dumb.
        if (SwingUtilities.isEventDispatchThread()) {
            run();
        } else {
            SwingUtilities.invokeLater(this);
        }
    } // refreshViews

    /**
     * Remove the given source view from the list.
     *
     * @param  view  source view object to remove.
     */
    public void removeView(View view) {
        if (view instanceof SessionListener) {
            owningSession.removeListener((SessionListener) view);
        }
        sourceToViewMap.remove(viewToSourceMap.remove(view));
    } // removeView

    /**
     * Called when the debuggee is about to be resumed.
     *
     * @param  sevt  session event.
     */
    @Override
	public void resuming(SessionEvent sevt) {
    } // resuming

    /**
     * Refresh the open views.
     */
    @Override
	public void run() {
        Enumeration views = viewToSourceMap.keys();
        while (views.hasMoreElements()) {
            View view = (View) views.nextElement();
            SourceSource src = (SourceSource) viewToSourceMap.get(view);
            try {
                view.refresh(src, 0);
            } catch (IOException ioe) {
                //owningSession.getUIAdapter().showMessage(
                //    UIAdapter.MESSAGE_WARNING, ioe.toString());
            }
        }
    } // run

    /**
     * Called when the debuggee has been suspended.
     *
     * @param  sevt  session event.
     */
    @Override
	public void suspended(SessionEvent sevt) {
    } // suspended
    
    /**
     * Show the given file in the appropriate view and make the
     * given line visible in that view.
     *
     * @param  src    source to be displayed.
     * @param  line   one-based line to be made visible, or zero for
     *                a reasonable default.
     * @param  count  number of lines to display, or zero for a
     *                reasonable default. Some adapters will ignore
     *                this value if, for instance, they utilize a
     *                scrollable view.
     * @return  true if successful, false if error.
     */
    public boolean showFile(final SourceSource src, final int line,
                            int count) {
        if (!src.exists()) {
            return false;
        }

        Runnable runnable = new Runnable() {
                @Override
				public void run() {
                    if (!showFile0(src, line)) {
                        throw new RuntimeException("showFile failed");
                    }
                }
            };
        if (SwingUtilities.isEventDispatchThread()) {
            try {
                runnable.run();
            } catch (RuntimeException re) {
                //statusLog.writeStackTrace(re);
                return false;
            }
        } else {
            try {
                SwingUtilities.invokeAndWait(runnable);
            } catch (InterruptedException ie) {
                return false;
            } catch (InvocationTargetException ite) {
                //statusLog.writeStackTrace(ite);
                return false;
            }
        }
        return true;
    } // showFile

    /**
     * Show the given file in the appropriate view and make the
     * given line visible in that view.
     *
     * @param  src   source to be displayed.
     * @param  line  line to be made visible.
     * @return  true if successful, false if error.
     */
    protected boolean showFile0(SourceSource src, int line) {
        boolean success = true;
        String errorMsg = null;
        
        View view = getView(src);
        RightPartPane rp = ConsoleCenter.getMainFrame().rightPartPane;
        CodePane codePane = rp.getCodePane();
        if (view != null) {
//            try {
//                codePane.selectView(view);
//            } catch (ViewException ve) {
//                errorMsg = "GraphicalAdapter.showFile() error: "
//                    + ve.getCause();
//            }
            view.scrollToLine(line);
        } else {

            // Show a busy cursor while we open the view.
            //mainWindow.setCursor(Cursor.getPredefinedCursor(
            //    Cursor.WAIT_CURSOR));
            // Let the factory do the hard work.
            ViewFactory factory = ViewFactory.getInstance();
            view = factory.create(src);
            addView(view, src);
            try {
                view.refresh(src, line);
            } catch (IOException ioe) {
                success = false;
                errorMsg = "GraphicalAdapter.showFile() error: " + ioe;
            }

            if (success) {
//                try {
//                    codePane.addView(view);
//                } catch (ViewException ve) {
//                    success = false;
//                    errorMsg = "GraphicalAdapter.showFile() error: "
//                        + ve.getCause();
//                }
            } else {
                removeView(view);
            }
            //mainWindow.setCursor(Cursor.getDefaultCursor());
        }

        // Not really a property, but it works the same.
        // Note that the values must be different for an event to fire.
        if (success) {
            //getChangeSupport().firePropertyChange("fileOpened", null, src);
        } else {
            //ourSession.getUIAdapter().showMessage(MESSAGE_WARNING, errorMsg);
        }
        return success;
    } // showFile0

} // ViewManager
