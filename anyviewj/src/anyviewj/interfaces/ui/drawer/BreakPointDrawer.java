package anyviewj.interfaces.ui.drawer;

import java.awt.Color;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JTextPane;

import anyviewj.debug.breakpoint.Breakpoint;
import anyviewj.debug.breakpoint.LocatableBreakpoint;
import anyviewj.debug.breakpoint.UndrawBreakpoint;
import anyviewj.debug.breakpoint.event.BreakpointEvent;
import anyviewj.debug.manager.BreakpointManager;
import anyviewj.debug.source.SourceSource;
import anyviewj.lang.ClassDefinition;

import com.sun.jdi.ReferenceType;
import anyviewj.debug.breakpoint.event.BreakpointListener;
public class BreakPointDrawer implements BreakpointListener {
	/** The drawer priority. */
    private static final int PRIORITY = 256;
    /** Lowest priority value. */
    public static final int PRIORITY_LOWEST = 512;
    /** Highest priority value. */
    public static final int PRIORITY_HIGHEST = 64;
    
	/** The rowArea which be attached to */
	private JTextPane rowArea;
	
	private Hashtable<Integer, Color> lineColors;
	
	private List classLines;
	
	private SourceSource sourceSrc;
	
	private boolean active;
	
	public BreakPointDrawer(JTextPane rowArea, SourceSource sourceSrc) {
		super();
		this.rowArea = rowArea;
		this.sourceSrc = sourceSrc;
		lineColors = new Hashtable<Integer, Color>();
		setActive(true);
	}
	
	@Override
	public void breakpointAdded(BreakpointEvent event) {
		
		setBreakpoint(event.getBreakpoint());
		repaintRowArea();
	}

	@Override
	public void breakpointModified(BreakpointEvent event) {
		setBreakpoints((BreakpointManager) event.getSource());
	}

	@Override
	public void breakpointRemoved(BreakpointEvent event) {
		
		// Is it a locatable breakpoint?
        Breakpoint bp = event.getBreakpoint();
        if (matches(bp)) {
            LocatableBreakpoint lbp = (LocatableBreakpoint) bp;
            int line = lbp.getLineNumber();
            if (line > 0) {
                // Use the breakpoint's line number.
                // This works for unresolved line breakpoints.
                setLineColor(line, null);
            }
            repaintRowArea();
        }
	}
	
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
	
	protected void setBreakpoint(Breakpoint bp) {
        if (matches(bp)) {
            LocatableBreakpoint lbp = (LocatableBreakpoint) bp;
            int line = lbp.getLineNumber();
            if (line > 0) {
                // Use the breakpoint's line number.
                // This works for unresolved line breakpoints.
//                setLineColor(line, getBreakpointColor(bp));
            	
//            	hou 2013Äê8ÔÂ31ÈÕ17:16:05
            	if ( bp instanceof UndrawBreakpoint )
            	{
            		setLineColor(line, null);
            	}
            	else
            	{
            		setLineColor(line, getBreakpointColor(bp));
            		
            	}
            }
        }
    }
	
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
        repaintRowArea();
    }
	
	public void setClassDefinitions(List lines) {
        classLines = lines;
    }
	
	protected void setLineColor(int line, Color color) {
        // 'line' is 1-based but our table is 0-based.
        line--;
        if (color == null) {
            lineColors.remove(new Integer(line));
        } else {
            lineColors.put(new Integer(line), color);
        }
    }
	
	void repaintRowArea() {
		rowArea.repaint();
    }

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public static int getPriority() {
		return PRIORITY;
	}

	public Hashtable<Integer, Color> getLineColors() {
		return lineColors;
	}

	public void setLineColors(Hashtable<Integer, Color> lineColors) {
		this.lineColors = lineColors;
	}
	
	public Color getLineColor(Integer line) {
		return lineColors.get(line);
	}

}
