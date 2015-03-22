package anyviewj.interfaces.ui.panel;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.text.Element;

import anyviewj.console.ConsoleCenter;
import anyviewj.debug.manager.ContextManager;
import anyviewj.debug.source.event.ContextChangeEvent;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.Location;
import anyviewj.debug.source.event.ContextListener;
import anyviewj.interfaces.ui.JavaProject;
import anyviewj.interfaces.ui.drawer.DebuggingHighlighter;
import anyviewj.interfaces.ui.manager.JavaProjectManager;

public class SourceEditor extends JTextPane implements ContextListener{
	private static final long serialVersionUID = 3284288015832068764L;
	
	public DebuggingHighlighter lineHighlighter;
	private SourcePane sourcePane;
	
	
	public SourceEditor(SourcePane sourcePane) {
		super();
		this.sourcePane = sourcePane;
		
	}	
	@Override
	public boolean getScrollableTracksViewportWidth() {
		return false;
	}
	@Override
	public void setSize(Dimension d) {
		if(d.width<getParent().getSize().width) {
			d.width=getParent().getSize().width;
		}
		super.setSize(d);
	}
	
//	public void paint(Graphics g) {
//		super.paint(g);
//		if(lineHighlighter != null && lineHighlighter.isActive()) {
//			try {
//				lineHighlighter.colouring(getStyledDocument(), lineHighlighter.getHighlightStart(), lineHighlighter.getHighlightEnd());
//			} catch (BadLocationException e) {
//				e.printStackTrace();
//			}
//		}
//	}
	
	protected boolean matches(Location location) {
        try {
            StringBuffer filename = new StringBuffer();
//            String pkg = "com.alwaysmai";
            String pkg = sourcePane.sourceSrc.getPackage();
            if (pkg != null && pkg.length() > 0) {
                filename.append(pkg.replace('.', File.separatorChar));
                filename.append(File.separatorChar);
            }
//            filename.append("Test2.java");
            filename.append(sourcePane.sourceSrc.getName());
            JavaProject prj = (JavaProject)((JavaProjectManager)ConsoleCenter.projectManager).getCurProject();
//        
//             if(prj!=null)
//             {
//            	 if(prj.getCurFileIndex()<0)
//            	 {
//            		 String filename2 = sourcePane.getSourceSrc().getName();
                 	return filename.toString().equals(location.sourcePath());
//            	 }
//            		String fn = prj.fileList.get(prj.getCurFileIndex()).toString();
//            		return fn.toString().equals(location.sourcePath());
//             }
//            
//            return filename.toString().equals(location.sourcePath());
        } catch (AbsentInformationException aie) {
        	
            return false;
        }
    }
	
	
	public void showHighlight(final int line) {
        if (getVisibleRect().height <= 0) {
            Timer t = new Timer(10, new ActionListener() {
                    @Override
					public void actionPerformed(ActionEvent e) {
                        // Maybe the view is visible now.
                        showHighlight(line);
                    }
                });
            t.setRepeats(false);
            t.start();
        }

        SwingUtilities.invokeLater(new Runnable() {
                @Override
				public void run() {
                    if (lineHighlighter == null) {
                        // Text area is already gone, do nothing.
                        return;
                    }
                    // Set text highlight.
                    Element map = getDocument().getDefaultRootElement();
                    int p0 = map.getElement(line).getStartOffset();
                    int p1 = map.getElement(line).getEndOffset();
                    lineHighlighter.setHighlight(getStyledDocument(), p0, p1);
                    lineHighlighter.colouring(getStyledDocument(), p0, p1);
//                    repaint();
//	                    lineHighlighter.colouring(getStyledDocument(), p0, p1-p0);
                }
            });
    }
	
	public void removeHighlight() {
        SwingUtilities.invokeLater(new Runnable() {
                @Override
				public void run() {
                    if (lineHighlighter == null) {
                        // Text area is already gone, do nothing.
                    	lineHighlighter.setHighlight(getStyledDocument(), 0, 0);
                        return;
                    }
                    lineHighlighter.setHighlight(getStyledDocument(), 0, 0);
                }
            });
    }
	
	public void removeHighlightCopy(final int line) {
        SwingUtilities.invokeLater(new Runnable() {
                @Override
				public void run() {
                    if (lineHighlighter == null) {
                        // Text area is already gone, do nothing.
                    	lineHighlighter.setHighlight(getStyledDocument(), 0, 0);
                        return;
                    }
                    Element map = getDocument().getDefaultRootElement();
                    int p0 = map.getElement(line).getStartOffset();
                    int p1 = map.getElement(line).getEndOffset();
                    lineHighlighter.setHighlightCopy(getStyledDocument(), p0, p1);
                }
            });
    }
	
//	private void scrollToLine(int line) {
//		System.out.println("SourceEditor scrollToLine 1 ");
//		FontMetrics metrics = getFontMetrics(getFont());
//		int lineHeight = metrics.getHeight();
//		Rectangle aRect = getVisibleRect();
//		aRect.x = 0;
//		aRect.y = line * lineHeight;
//		aRect.height = lineHeight;
//		System.out.println("line = " + line + " lineHeight = " + lineHeight + " aRect = " + aRect.toString());
//		System.out.println("SourceEditor scrollToLine 2 ");
//		scrollRectToVisible(aRect);
//		System.out.println("SourceEditor scrollToLine 3 ");
//	}
	
	public void scrollToLine(final int line) {
        if (line > 0) {

            if (getVisibleRect().height <= 0) {
                // Apparently the window has not been realized.
                Timer t = new Timer(10, new ActionListener() {
                        @Override
						public void actionPerformed(ActionEvent e) {
                            // Maybe the view is visible now.
                            scrollToLine(line);
                        }
                    });
                t.setRepeats(false);
                t.start();
            }

            // Scroll the text area to this line (on the AWT thread).
            SwingUtilities.invokeLater(new Runnable() {

                    @Override
					public void run() {
                        Rectangle visible = getVisibleRect();
                        // Compute the upper and lower bounds of what
                        // is acceptable.
                        int upper = visible.y + (visible.height / 3);
                        int lower = visible.y - (visible.height / 2);

                        // Get the font height value and create a rectangle
                        // used for scrolling the text area.
                        FontMetrics metrics = getFontMetrics(getFont());
                        int fontHeight = metrics.getHeight();

                        // Compute the point to scroll to.
                        visible.y = (line - 1) * fontHeight;
                        // Subtract half to center the line in the view.
                        visible.y -= (visible.height / 2);

                        // See if we really should scroll the text area.
                        if ((visible.y < lower) || (visible.y > upper)) {
                            // Check that we're not scrolling past the end.
                            int newbottom = visible.y + visible.height;
                            int textheight = getHeight();
                            if (newbottom > textheight) {
                                visible.y -= (newbottom - textheight);
                            }
                            // Perform the text area scroll.
                            System.out.println("SourceEditor scrollToLine 2 ");
                            scrollRectToVisible(visible);
                            System.out.println("SourceEditor scrollToLine 3 ");
                        }
                    }
                });
        }
    } // scrollToLine
	
	
	@Override
	public void contextChanged(ContextChangeEvent cce) {
		ContextManager cmgr = (ContextManager) cce.getSource();
        Location loc = cmgr.getCurrentLocation();
        // See if this event belongs to our source file.
        if(loc != null && matches(loc)) {
        	int line = loc.lineNumber();
        	showHighlight(line - 1);
            scrollToLine(line);
        } else {
        	removeHighlight();
        }
	}
	public DebuggingHighlighter getLineHighlighter() {
		return lineHighlighter;
	}
	public void setLineHighlighter(DebuggingHighlighter lineHighlighter) {
		this.lineHighlighter = lineHighlighter;
	}
	
	
}
