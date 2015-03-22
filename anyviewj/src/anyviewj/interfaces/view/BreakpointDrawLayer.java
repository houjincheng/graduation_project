package anyviewj.interfaces.view;

import anyviewj.debug.source.SourceSource;
import anyviewj.debug.breakpoint.Breakpoint;
import anyviewj.debug.breakpoint.LocatableBreakpoint;
import anyviewj.debug.breakpoint.event.BreakpointEvent;
import anyviewj.debug.breakpoint.event.BreakpointListener;
import anyviewj.debug.manager.BreakpointManager;
import anyviewj.lang.ClassDefinition;
import com.sun.jdi.ReferenceType;
import java.awt.Color;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Class BreakpointDrawLayer is responsible for drawing color indicators
 * at the appropriate lines to indicate where breakpoints exist, and in
 * what state they are presently in.
 *
 * @author  ltt
 */
public class BreakpointDrawLayer extends AbstractGutterDrawLayer
    implements BreakpointListener {
    /** Our draw layer priority. */
    private static final int PRIORITY = 256;
    /** Source view text area we are attached to. */
    private SourceViewTextArea textArea;
    /** Collection of line numbers and their associated Colors. */
    private Hashtable lineColors;
    /** Classlines of the parsed source file. */
    private List classLines;
    /** Source file information. */
    private SourceSource sourceSrc;
    
    private int resolveline;

    /**
     * Constructs a breakpoint draw layer, attached to the given
     * source view text area.
     *
     * @param  area  source view text area.
     * @param  src   source file information.
     */
    public BreakpointDrawLayer(SourceViewTextArea area, SourceSource src) {
        super();
        textArea = area;
        sourceSrc = src;
        lineColors = new Hashtable();
        resolveline = 0;
        // Default to active in the general case.
        setActive(true);
    }

    /**
     * Invoked when a breakpoint has been added.
     *
     * @param  be  breakpoint event.
     */
    @Override
	public void breakpointAdded(BreakpointEvent be) {
    	System.out.println("breakpointAdded");
        setBreakpoint(be.getBreakpoint());
        textArea.repaintGutter();
    }

    /**
     * Invoked when a breakpoint has been modified.
     *
     * @param  be  breakpoint event.
     */
    @Override
	public void breakpointModified(BreakpointEvent be) {
        // Have to rebuild the entire list from scratch since we can't
        // rely on the breakpoint staying in the same location.
        setBreakpoints((BreakpointManager) be.getSource());
    }

    /**
     * Invoked when a breakpoint has been removed.
     *
     * @param  be  breakpoint event.
     */
    @Override
	public void breakpointRemoved(BreakpointEvent be) {
        // Is it a locatable breakpoint?
        Breakpoint bp = be.getBreakpoint();
        if (matches(bp)) {
            LocatableBreakpoint lbp = (LocatableBreakpoint) bp;
            int line = lbp.getLineNumber();
            if (line > 0) {
                // Use the breakpoint's line number.
                // This works for unresolved line breakpoints.
                setLineColor(line, null);
            }
            textArea.repaintGutter();
        }
    }

    /**
     * Returns the color that best represents the state of this breakpoint.
     * If the breakpoint is diabled, the color would be gray. On the other
     * hand, if the breakpoint is enabled and ready, the color would be
     * red. This is useful for those classes that want to visually
     * represent the breakpoint using some colored object.
     *
     * @param  bp  breakpoint to get color for.
     * @return  the breakpoint "color".
     */
    protected static Color getBreakpointColor(Breakpoint bp) {
        if (bp.hasExpired()) {
            return Color.red;
        } else if (!bp.isEnabled()) {
            return Color.gray;
        } else if (!bp.isResolved()) {
            return Color.blue;
        } else if (bp.isSkipping()) {
            return Color.yellow;
        } else {
            return Color.green;
        }
    }

    /**
     * Gets the priority level of this particular draw layer. Typically
     * each type of draw layer has its own priority. Lower values are
     * higher priority.
     *
     * @return  priority level.
     */
    @Override
	public int getPriority() {
        return PRIORITY;
    }

    /**
     * Returns true if this draw layer wants to take part in the
     * current painting event.
     *
     * @return  true if active, false otherwise.
     */
    @Override
	public boolean isActive() {
        // We're active if we have breakpoints.
        return super.isActive() && lineColors.size() > 0;
    }

    /**
     * Check if the Breakpoint is in the list of class definitions.
     *
     * @param  bp  breakpoint to check against.
     * @return  true if breakpoint matches the source.
     */
    protected boolean matches(Breakpoint bp) {
        // Check if the breakpoint has a location.
        if (bp instanceof LocatableBreakpoint) {
            LocatableBreakpoint lbp = (LocatableBreakpoint) bp;

            // Check if the source has the same information.
            String pkg = sourceSrc.getPackage();
            String src = sourceSrc.getName();
            if (lbp.matchesSource(pkg, src)) {
                return true;
            }

            if (classLines != null) {
                // Iterate our class definitions to find a match.
                String cname = "";
                ReferenceType clazz = lbp.getReferenceType();
                if (clazz != null) {
                    cname = clazz.name();
                }
                Iterator iter = classLines.iterator();
                while (iter.hasNext()) {
                    ClassDefinition cd = (ClassDefinition) iter.next();
                    String cdname = cd.getClassName();
                    if (cdname.equals(cname) || lbp.matchesClassName(cdname)) {
                        return true;
                    }
                }
            }
        }

        // Probably does not belong to us anyway.
        return false;
    }

    /**
     * Adds a line attribute to the source row header, appropriate for
     * the given breakpoint. Checks to make sure the breakpoint is set
     * in our file.
     *
     * @param  bp  breakpoint.
     */
    protected void setBreakpoint(Breakpoint bp) {
        if (matches(bp)) {
            LocatableBreakpoint lbp = (LocatableBreakpoint) bp;
            int line = lbp.getLineNumber();
            if (line > 0) {
                // Use the breakpoint's line number.
                // This works for unresolved line breakpoints.            	
                setLineColor(line, getBreakpointColor(bp));
            }
        }
    }

    /**
     * Iterate all existing breakpoints and create row header attributes
     * as appropriate.
     *
     * @param  bpman  BreakpointManager.
     */
    public void setBreakpoints(BreakpointManager bpman) {
        lineColors.clear();
        Iterator iter = bpman.breakpoints(true);
        Set s = bpman.breakpointsTable.keySet();
        for(Object key:s)
        {
        	Breakpoint bp = (Breakpoint)bpman.breakpointsTable.get(key);
        	setBreakpoint(bp);
        }
//        while (iter.hasNext()) {
//            setBreakpoint((Breakpoint) iter.next());
//        }
        textArea.repaintGutter();
    }

    /**
     * Set the list of class definitions.
     *
     * @param  lines  list of class definitions.
     */
    public void setClassDefinitions(List lines) {
        classLines = lines;
    }

    /**
     * Set the color for the given line.
     *
     * @param  line   Line number (one-based value).
     * @param  color  Color for this line, null to remove the existing
     *                color, if any.
     */
    protected void setLineColor(int line, Color color) {
        // 'line' is 1-based but our table is 0-based.
        //line--;
        if (color == null) {
            lineColors.remove(new Integer(line));
        } else {
            lineColors.put(new Integer(line), color);
        }
    }

    /**
     * Update the draw context by setting colors, fonts and possibly
     * other draw properties.
     *
     * @param  ctx   draw context.
     * @param  line  line number where drawing is presently taking place
     *               (zero-based value).
     */
    @Override
	public void updateContext(DrawContext ctx, int line) {
        Color c = (Color) lineColors.get(new Integer(line));
        if (c != null) {
            ctx.setBackColor(c);
        }
    }
}
