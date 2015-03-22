package anyviewj.interfaces.ui.listener;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;

import anyviewj.console.ConsoleCenter;
import anyviewj.debug.breakpoint.Breakpoint;
import anyviewj.debug.breakpoint.LineBreakpoint;
import anyviewj.debug.breakpoint.LocatableBreakpoint;
import anyviewj.debug.breakpoint.ResolveException;
import anyviewj.debug.breakpoint.SourceNameBreakpoint;
import anyviewj.debug.manager.BreakpointManager;
import anyviewj.debug.session.Session;
import anyviewj.debug.session.SessionFrameMapper;
import anyviewj.interfaces.ui.Bundle;
import anyviewj.interfaces.ui.UIAdapter;
import anyviewj.interfaces.ui.drawer.DebuggingHighlighter;
import anyviewj.interfaces.ui.drawer.InvalidBreakpointJudger;
import anyviewj.interfaces.ui.panel.CodeTabPane;
import anyviewj.interfaces.ui.panel.SourceEditor;
import anyviewj.interfaces.ui.panel.TextEditor;
import anyviewj.interfaces.view.ViewException;

public class BreakPointActionListener implements MouseListener {
	private JTextPane rowArea;

	private TextEditor editorPanel;

	private int lastClickedLine;
	private Breakpoint lastClickedBreakpoint;
	private String lastClickedClass;
	
	public BreakpointManager bpm;
	public CodeTabPane cd;
	public Map<Integer,DebuggingHighlighter> mm = new HashMap<Integer,DebuggingHighlighter>();
	public DebuggingHighlighter dbg;
	public InvalidBreakpointJudger judger;
	
	public void setCD(CodeTabPane cd2){
		this.cd = cd2;
	}
	

	public BreakPointActionListener(TextEditor editorPanel) {
		this.editorPanel = editorPanel;
		this.rowArea = editorPanel.rowArea;
		dbg = new DebuggingHighlighter(editorPanel.editor, Color.PINK);
		judger = new InvalidBreakpointJudger(editorPanel.editor);
	}

	public int getLineCount(JTextPane tp) {
		// tp是一个JTextPane
		Element map = tp.getDocument().getDefaultRootElement();
		return map.getElementCount();
	}

	/**
	 * Turns a view coordinate into a one-based line number.
	 * 
	 * @param pt
	 *            Point within the view coordinates.
	 * @return One-based line number corresponding to the point. If the returned
	 *         value is -1 then there was an error.
	 */
	public int viewToLine(Point pt) {
		// Deal with out of bounds.
		if (pt.y > rowArea.getHeight()) {
			return getLineCount(rowArea);
		}

		// Use point to determine line number.
		FontMetrics metrics = rowArea.getFontMetrics(rowArea.getFont());
		int lineHeight = metrics.getHeight();
		return (pt.y / lineHeight) + 1;
	} // viewToLine 

	/**
	 * 监听器被添加到了行号显示器上
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		((SourceEditor)editorPanel.editor).setLineHighlighter(dbg);
		if (!e.isConsumed()) {
			// Yes, create/disable/delete breakpoint at this line.
            			
			// Get the class at this line. 通过计算获取行号。
			lastClickedLine = viewToLine(e.getPoint());
			
			// Find the breakpoint the user clicked on.
			Session session = SessionFrameMapper.getSessionForEvent(e);
			BreakpointManager bpman = (BreakpointManager) session
					.getManager(BreakpointManager.class);		
				      
			
	            			
			
			
			try {
//				通过行号lastClickedLine比对，找出是否有这个行号的断点存在
				lastClickedBreakpoint = findBreakpoint(bpman);
			} catch (ViewException ve) {
//				发出信息：断点处是空代码行
				/*session.getUIAdapter().showMessage(UIAdapter.MESSAGE_WARNING,
						Bundle.getString("SourceViewPopup.nonCodeLine"));*/
				return;
			}
			
			System.out.println("add breakpoint: " + lastClickedLine);
								

			if (lastClickedBreakpoint == null) {
				// No breakpoint at this location, add a new one.
				
				Boolean judge = false;
				try {
					judge = judger.JudgeInvalid(lastClickedLine-1);
					 if(judge)
					 {
						String message = "无效断点!";
						String title = "警告";
						int optionType = JOptionPane.DEFAULT_OPTION;
						int messageType = JOptionPane.ERROR_MESSAGE;
						JOptionPane.showConfirmDialog(editorPanel.editor, message, title, optionType, messageType);
						return;
					 }
					
				} 
				catch (BadLocationException x) 
				{
					// TODO Auto-generated catch block	x.printStackTrace();}
				
				}
				
				addBreakpoint(session, bpman);

												
				
			} else {
				// Breakpoint exists.
				if (lastClickedBreakpoint.isEnabled()) {
					// Breakpoint is enabled, disable it.
					bpman.disableBreakpoint(lastClickedBreakpoint);
					Set key = mm.keySet();
					for(Object a: key)
					{
					 if(lastClickedLine==(Integer)a)
					  {
						 ((SourceEditor)editorPanel.editor).setLineHighlighter(mm.get(a));
						 dbg.bpLineDelete(lastClickedLine-1, (SourceEditor)editorPanel.editor);
					     ((SourceEditor)editorPanel.editor).removeHighlightCopy(lastClickedLine-1);
					  }
					 }
					
				} else {
					// Breakpoint is disabled, remove it.
					bpman.removeBreakpoint(lastClickedBreakpoint);
				}
			}
			ConsoleCenter.mainFrame.rightPartPane.codePane.fileTabPane.updateTabInfo(bpman);
			ConsoleCenter.mainFrame.rightPartPane.groupPane.breakPane.refresh(session);
			editorPanel.owner.dbgER.lineList = dbg.lineList;

		}
	}
	
	private Breakpoint addBreakpoint(Session session, BreakpointManager bpman) {
		//DebuggingHighlighter dbg = new DebuggingHighlighter(editorPanel.editor, Color.GRAY);
		// ((SourceEditor)editorPanel.editor).setLineHighlighter(dbg);
		 dbg.bpLineStore(lastClickedLine-1, (SourceEditor)editorPanel.editor);
		 ((SourceEditor)editorPanel.editor).showHighlight(lastClickedLine-1);
		 mm.put(lastClickedLine,dbg);
			if (lastClickedClass == null) {
			// Fall back on default behavior.
			return addSourceNameBreakpoint(session, bpman);
		} else {
			return addLineBreakpoint(session, bpman);
		}			
	}

	protected Breakpoint addLineBreakpoint(Session session, BreakpointManager bpman) {

		try {
			String fname = editorPanel.owner.sourceSrc.getName();
			Breakpoint bp = new LineBreakpoint(lastClickedClass, fname,
					lastClickedLine);
			bpman.addNewBreakpoint(bp);
			return bp;
		} catch (ClassNotFoundException cnfe) {
			session.getUIAdapter().showMessage(
					UIAdapter.MESSAGE_ERROR,
					Bundle.getString("AddBreak.invalidClassMsg") + ' '
							+ lastClickedClass);
			return null;
		} catch (ResolveException re) {
			session.getUIAdapter().showMessage(UIAdapter.MESSAGE_ERROR,
					re.errorMessage());
			return null;
		}
	} // addBreakpoint

	protected Breakpoint addSourceNameBreakpoint(Session session,
			BreakpointManager bpman) {

		try {
			Breakpoint bp = new SourceNameBreakpoint(editorPanel.owner.sourceSrc.getPackage(),
					editorPanel.owner.sourceSrc.getName(), lastClickedLine);
			

			
//			下面这2行代码是测试用的，应该及时删除
//			DebugTest1 dt = new DebugTest1( bp );
//			dt.perform();
//			
			
			
			bpman.addNewBreakpoint(bp);
			return bp;
		} catch (ClassNotFoundException cnfe) {
			session.getUIAdapter().showMessage(
					UIAdapter.MESSAGE_ERROR,
					Bundle.getString("AddBreak.invalidClassMsg") + ' '
							+ editorPanel.owner.sourceSrc.getPackage() + '.'
							+ editorPanel.owner.sourceSrc.getName());
			return null;
		} catch (ResolveException re) {
			session.getUIAdapter().showMessage(UIAdapter.MESSAGE_ERROR,
					re.errorMessage());
			return null;
		}
	} // addBreakpoint

	protected Breakpoint findBreakpoint(BreakpointManager bpman)
			throws ViewException {
		String pkgname = editorPanel.owner.sourceSrc.getPackage();
		String srcname = editorPanel.owner.sourceSrc.getName();
		
		System.out.println( "bpman.getDefaultGroup() : " + bpman.getDefaultGroup() );
		Iterator iter = bpman.getDefaultGroup().breakpoints(true);
		
		while (iter.hasNext()) {
			Breakpoint bp = (Breakpoint) iter.next();
			if (bp instanceof LocatableBreakpoint) {
				LocatableBreakpoint lbp = (LocatableBreakpoint) bp;
				if (lbp.getLineNumber() == lastClickedLine
						&& lbp.matchesSource(pkgname, srcname)) {
					return bp;
				}
			}
		}
		return null;
	} // findBreakpoint
	
//	protected Breakpoint findBreakpoint(BreakpointManager bpman)
//	        throws ViewException {
//	        if (classLines == null) {
//	            // Fall back on the default behavior.
//	            return super.findBreakpoint(bpman);
//	        }
//
//	        // Get the class at this line.
//	        lastClickedClass = ClassDefinition.findClassForLine(
//	            classLines, lastClickedLine);
//	        if (lastClickedClass == null) {
//	            throw new NonCodeLineException();
//	        }
//
//	        // Query existing set of breakpoints for this class and line.
//	        return bpman.getBreakpoint(lastClickedClass, lastClickedLine);
//	    } // findBreakpoint

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
//		System.out.println("mouse enter");
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
	public BreakpointManager reBPM(){
		return bpm;
	}
}
