package anyviewj.interfaces.view;

import anyviewj.util.Defaults;
import anyviewj.console.ConsoleCenter;
import anyviewj.debug.session.Session;
import anyviewj.debug.source.SourceSource;
import anyviewj.debug.manager.ContextManager;
import anyviewj.debug.manager.BreakpointManager;
import anyviewj.debug.session.event.SessionEvent;
import anyviewj.debug.session.event.SessionListener;
import anyviewj.debug.source.event.ContextChangeEvent;
import anyviewj.debug.source.event.ContextListener;
//import com.bluemarsh.jswat.ui.UIAdapter;
import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.Location;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * Defines the SourceView class which will be responsible for displaying
 * the source file on the screen. This class displays a row header down
 * the left side of the source view, indicating the line numbers. A
 * popup menu is attached to the view for managing breakpoints.
 *
 * @author  ltt
 */
public class SourceView extends AbstractView
        implements PreferenceChangeListener, SessionListener, ContextListener {
    /** Our preferences node. */
    protected Preferences preferences;
    /** Popup menu for managing breakpoints. Listens to text component. */
    protected SourceViewPopup popupMenu;
    /** Session to which we belong. Set in <code>init()</code>. */
    protected Session owningSession;
    /** Gutter draw layer for showing breakpoint locations and states. */
    protected BreakpointDrawLayer breakpointDrawLayer;
    /** Scrollable component for text component. */
    //private JScrollPane viewScroller;
    private JPanel  viewScroller;
    /** Width of tabs in characters. Defaults to 8. */
    private int tabSize;
    /** Original view content, with any tab characters. */
    private char[] contentWithTabs;
    /** The source of the view content. */
    private SourceSource sourceSrc;

    /**
     * Creates a SourceView object.
     *
     * @param  src  source object to be displayed.
     */
    public SourceView(SourceSource src) {
        super(src.getName());
        sourceSrc = src;

        // Create the text component        
        textComponent = new SourceViewTextArea(viewContent);  
        viewScroller = new JPanel();        
        
        GridBagLayout gridbag = new GridBagLayout(); 
        GridBagConstraints c = new GridBagConstraints();
        viewScroller.setLayout(gridbag);
        //加入显示行号面板        
        viewScroller.add(textComponent,new GBC(0,0).setFill(GridBagConstraints.BOTH)); 
        //加入显示代码面板        
        viewScroller.add(textComponent.srcollPane,new GBC(1,0).  
                setFill(GridBagConstraints.BOTH).setIpad(70, 90).setWeight(80, 100));   
        
        textComponent.addDrawLayer(lineHighlighter);
        breakpointDrawLayer = new BreakpointDrawLayer(textComponent, src);
        textComponent.addGutterLayer(breakpointDrawLayer);
        //textComponent.addTooltipProducer(new VariableTooltipProducer());

        // Hook into the preferences system.
        preferences = Preferences.userRoot().node("com/bluemarsh/jswat/view");
        preferences.addPreferenceChangeListener(this);
        setPreferences();

        // Set up the scroller for the text component.
        //viewScroller = new JScrollPane(textComponent);
      
        // Create the breakpoint popup menu gadget for the text component.
        popupMenu = new SourceViewPopup(src);
        textComponent.addMouseListener(popupMenu);
    }

    /**
     * Called when the Session has activated. This occurs when the
     * debuggee has launched or has been attached to the debugger.
     *
     * @param  sevt  session event.
     */
    @Override
	public void activated(SessionEvent sevt) {
        // Register for debugger events.
    	System.out.println("go away !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//        ContextManager cmgr = (ContextManager)
//            sevt.getSession().getManager(ContextManager.class);
//        cmgr.addContextListener(this);
    }
    
    @Override
	public void setSession(Session session) {
          owningSession = session; 
    }

    /**
     * Called when the Session is about to be closed.
     *
     * @param  sevt  session event.
     */
    @Override
	public void closing(SessionEvent sevt) {
        BreakpointManager bpman = (BreakpointManager)
            sevt.getSession().getManager(BreakpointManager.class);
        bpman.removeBreakListener(breakpointDrawLayer);
        preferences.removePreferenceChangeListener(this);
        owningSession = null;
        viewScroller = null;
        textComponent = null;
        //popupMenu = null;
    }

    /**
     * Invoked when the current context has changed. The context change
     * event identifies which aspect of the context has changed.
     *
     * @param  cce  context change event
     */
    @Override
	public void contextChanged(ContextChangeEvent cce) {
    	  System.out.println("ADAAHHHHHHHHHHHHHAAAAAAAAAAAA: inSV ");
        ContextManager cmgr = (ContextManager) cce.getSource();
        Location loc = cmgr.getCurrentLocation();
        // See if this event belongs to our source file.
        if (loc != null && matches(loc)) {
            int line = loc.lineNumber();
            // Show where the pc is located.
            // Minus one for some reason.
            showHighlight(line - 1);
            scrollToLine(line);
        } else {
            // Clear the current stepping line.
            removeHighlight();
        }
    }

    /**
     * Called when the Session has deactivated. The debuggee VM is no
     * longer connected to the Session.
     *
     * @param  sevt  session event.
     */
    @Override
	public void deactivated(SessionEvent sevt) {
        ContextManager cmgr = (ContextManager)
            sevt.getSession().getManager(ContextManager.class);
        cmgr.removeContextListener(this);
        // Clear the current stepping line.
        removeHighlight();
        //if (logger.isLoggable(Level.INFO)) {
        //    logger.info("setting current line to 0 in " + viewTitle);
        //}
    }

    /**
     * Returns the long version of title of this view. This may be a
     * file name and path, a fully-qualified class name, or whatever is
     * appropriate for the type of view.
     *
     * @return  long view title.
     */
    @Override
	public String getLongTitle() {
        return sourceSrc.getLongName();
    }

    /**
     * Returns a reference to the UI component.
     *
     * @return  UI component object
     */
    @Override
	public JComponent getUI() {
        return viewScroller;
    }

    /**
     * Check if the Location falls within the displayed source file.
     *
     * @param  location  Location to check.
     * @return  true if location source name equals our filename.
     */
    protected boolean matches(Location location) {
        try {
            StringBuffer filename = new StringBuffer();
            String pkg = sourceSrc.getPackage();
            if (pkg != null && pkg.length() > 0) {
                filename.append(pkg.replace('.', File.separatorChar));
                filename.append(File.separatorChar);
            }
            filename.append(sourceSrc.getName());
            return filename.toString().equals(location.sourcePath());
        } catch (AbsentInformationException aie) {
            return false;
        }
    }

    /**
     * Called after the Session has added this listener to the Session
     * listener list.
     *
     * @param  session  the Session.
     */
    @Override
	public void opened(Session session) {
        owningSession = session;
        //BreakpointManager bpman = (BreakpointManager)
        //    session.getManager(BreakpointManager.class);
        BreakpointManager bpman = ConsoleCenter.getcurrentBKManager();
        bpman.addBreakListener(breakpointDrawLayer);
    }

    /**
     * This method gets called when a preference is added, removed or
     * when its value is changed.
     *
     * @param  evt  A PreferenceChangeEvent object describing the event
     *              source and the preference that has changed.
     */
    @Override
	public void preferenceChange(PreferenceChangeEvent evt) {
        // Load the preferences.
        setPreferences();

        // Ugly hack to deal with tab width changes.
        viewContent = SourceContent.replaceTabs(contentWithTabs, tabSize);

        // Reload the text contents.
        if (viewContent != null) {
            setTextContent();
        }
    }

    /**
     * Read the input stream text into the text component. The view must
     * be added to the Session as a session listener before calling this
     * method.
     *
     * @param  src   source to read data from.
     * @param  line  line to make visible.
     * @throws  IOException
     *          if an I/O error occurs in reading the input stream.
     */
    @Override
	public void refresh(SourceSource src, int line) throws IOException {
        // We assume we are given sources with input streams.
        InputStream is = src.getInputStream();
        if (is == null) {
            // However, we do not assume they are valid.
            return;
        }
        // Average Java source is under 32K characters.
        CharArrayWriter caw = new CharArrayWriter(32768);
        char[] buffer = new char[8192];
        InputStreamReader isr = new InputStreamReader(is);
        int bytesRead = isr.read(buffer);
        while (bytesRead > 0) {
            caw.write(buffer, 0, bytesRead);
            bytesRead = isr.read(buffer);
        }
        contentWithTabs = caw.toCharArray();
        // Convert the tabs to spaces because that causes problems for
        // searching and the text area width estimation.
        viewContent = SourceContent.replaceTabs(contentWithTabs, tabSize);
        isr.close();
        caw.close();
        setTextContent();

        //if (logger.isLoggable(Level.INFO)) {
        //    logger.info("source view refreshed for " + viewTitle);
        //}
        refreshPost();
        scrollToLine(line);
    }

    /**
     * Code to be executed after the refresh() method has completed.
     */
    protected void refreshPost() {
        // Set all the breakpoint locations.
        //BreakpointManager bpman = (BreakpointManager)
        //    owningSession.getManager(BreakpointManager.class);
    	BreakpointManager bpman = ConsoleCenter.getcurrentBKManager();
        breakpointDrawLayer.setBreakpoints(bpman);
        //if (logger.isLoggable(Level.INFO)) {
       //     logger.info("breakpoints and other line attributes set for "
       //                 + viewTitle);
       // }
    }

    /**
     * Called when the debuggee is about to be resumed.
     *
     * @param  sevt  session event.
     */
    @Override
	public void resuming(SessionEvent sevt) {
    }

    /**
     * Called to update this view's preferences, either when this object
     * is constructed or when the preferences change.
     */
    protected void setPreferences() {
        // Notify the colorizers of possible changes.
        try {
            lineHighlighter.updateColor(preferences);
        } catch (NumberFormatException nfe) {
            //owningSession.getUIAdapter().showMessage(UIAdapter.MESSAGE_ERROR,
            //                                         nfe.toString());
        }

        // Set the font size of the text component.
        // Do this before setting the tab size.
        int fontSize = preferences.getInt("fontSize",
                                          Defaults.VIEW_FONT_SIZE);
        String family = preferences.get("fontFamily",
                                        Defaults.VIEW_FONT_FAMILY);
        Font font = new Font(family, Font.PLAIN, fontSize);
        // Changing the font causes a revalidation.
        textComponent.setFont(font);

        // Set the tab width of the text area.
        tabSize = preferences.getInt("tabWidth", Defaults.VIEW_TAB_WIDTH);
    }

    /**
     * Set the content of the text component using the text defined
     * by <code>viewContent</code>. This implementation applies styles
     * to the Java source to colorize it syntactically.
     */
    protected void setTextContent() {
        SourceContent doc = new SourceContent(viewContent);
        // Force a repaint.
        textComponent.setContent(doc);
    }

    /**
     * Called when the debuggee has been suspended.
     *
     * @param  sevt  session event.
     */
    @Override
	public void suspended(SessionEvent sevt) {
    }
    
    
}

class GBC extends GridBagConstraints  
{  
   //初始化左上角位置  
   public GBC(int gridx, int gridy)  
   {  
      this.gridx = gridx;  
      this.gridy = gridy;  
   }  
  
   //初始化左上角位置和所占行数和列数  
   public GBC(int gridx, int gridy, int gridwidth, int gridheight)  
   {  
      this.gridx = gridx;  
      this.gridy = gridy;  
      this.gridwidth = gridwidth;  
      this.gridheight = gridheight;  
   }  
  
   //对齐方式  
   public GBC setAnchor(int anchor)  
   {  
      this.anchor = anchor;  
      return this;  
   }  
  
   //是否拉伸及拉伸方向  
   public GBC setFill(int fill)  
   {  
      this.fill = fill;  
      return this;  
   }  
  
   //x和y方向上的增量  
   public GBC setWeight(double weightx, double weighty)  
   {  
      this.weightx = weightx;  
      this.weighty = weighty;  
      return this;  
   }  
  
   //外部填充  
   public GBC setInsets(int distance)  
   {  
      this.insets = new Insets(distance, distance, distance, distance);  
      return this;  
   }  
  
   //外填充  
   public GBC setInsets(int top, int left, int bottom, int right)  
   {  
      this.insets = new Insets(top, left, bottom, right);  
      return this;  
   }  
  
   //内填充  
   public GBC setIpad(int ipadx, int ipady)  
   {  
      this.ipadx = ipadx;  
      this.ipady = ipady;  
      return this;  
   }  
   
}