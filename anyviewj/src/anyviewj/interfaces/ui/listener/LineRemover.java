package anyviewj.interfaces.ui.listener;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.swing.JTextPane;

import anyviewj.console.ConsoleCenter;
import anyviewj.debug.breakpoint.Breakpoint;
import anyviewj.debug.breakpoint.ResolvableBreakpoint;
import anyviewj.debug.breakpoint.SourceNameBreakpoint;
import anyviewj.debug.manager.BreakpointManager;
import anyviewj.debug.session.Session;
import anyviewj.interfaces.ui.panel.SourceEditor;
import anyviewj.interfaces.ui.panel.TextEditor;

import javax.swing.text.Caret;
import javax.swing.text.Element;

public class LineRemover {
	 protected int firstLine;
	    public int currentLine;
	    public int count;
	    
	    public Boolean check;
	    
	    public TextEditor editorPanel;
	    public JTextPane rowArea;
	
	public LineRemover(TextEditor editorPanel){
		this.editorPanel = editorPanel;
		this.rowArea = editorPanel.rowArea;
	}
	
	public void countLine(){
		Element map = editorPanel.editor.getDocument().getDefaultRootElement();
		SourceEditor se = (SourceEditor) editorPanel.editor;
		Caret car = se.getCaret();
		firstLine =  map.getElementIndex(car.getDot());//所得为删除行后光标所在行减一
		currentLine =  map.getElementIndex(car.getDot());
		count = currentLine - firstLine+1;
		System.out.println("RRRRRRRRr  : "+ ( map.getElementIndex(car.getDot())+2));
		BreakpointReload();
		reDrawHighlight(car,map);
	}
	
	public void reDrawHighlight(Caret car, Element map){
		Session session = ConsoleCenter.getCurrentSession();
		if(session==null)
			return;
		BreakpointManager bkman = (BreakpointManager)session.getManager(BreakpointManager.class);
		if(bkman!=null)
		{
		 Hashtable ht = bkman.breakpointsTable; 	
		 if(ht.isEmpty())
		    	return;
		 Set key = ht.keySet();
		 for(Object x : key)
		 {
			 Breakpoint bp = (Breakpoint)ht.get(x);
		     if(bp instanceof ResolvableBreakpoint)
		     {
		    	 if(( map.getElementIndex(car.getDot())+1)==((SourceNameBreakpoint)bp).lineNum) 
		    	 {
//		    		 editorPanel.bpal.dbg.reDrawBP((SourceEditor) editorPanel.editor); 
		    		 int p0 = map.getElement(map.getElementIndex(car.getDot())).getStartOffset();
		    		 int p1 = map.getElement(map.getElementIndex(car.getDot())).getEndOffset();
		    		 //editorPanel.bpal.dbg.setHighlight(editorPanel.editor.getStyledDocument(),p0,p1);
		    		 editorPanel.bpal.dbg.colouring(editorPanel.editor.getStyledDocument(),p0,p1);
		    	 }
		     }
		 }
	  }
	}
	
	public void BreakpointReload(){
		Session session = ConsoleCenter.getCurrentSession();
		if(session==null)
			return;
		BreakpointManager bkman = (BreakpointManager)session.getManager(BreakpointManager.class);
	    Hashtable ht = bkman.breakpointsTable;
	    if(ht.isEmpty())
	    	return;
	    Set key = ht.keySet();
	    List<Breakpoint> bpList = new ArrayList();
	    List newBp = new ArrayList();
	    Breakpoint removeBp = null;
	    for(Object x : key)
	    {
	    	
	    	Breakpoint bp = (Breakpoint)ht.get(x);
	    	if(bp instanceof ResolvableBreakpoint)
	    	{   
	    		if(firstLine+2==((SourceNameBreakpoint)bp).lineNum)
	    		{
	    			removeBp = bp;
	    		}
	    		if(firstLine+1<((SourceNameBreakpoint)bp).lineNum)
	    		{
	    		 //System.out.println("    "+((SourceNameBreakpoint)bp).lineNum+" sdsdsd\n "+count+"   "+(((SourceNameBreakpoint)bp).lineNum+count+1));
	    	     bpList.add(bp);
	    	     newBp.add(((SourceNameBreakpoint)bp).lineNum-count);
	    		}
	    	}	    	
	    }
	    if(newBp.isEmpty())
	    	return;
	    if(removeBp!=null)
	    	bkman.removeBreakpoint(removeBp);
	    for(int i = 0;i<bpList.size();i++)
	    {
	    	bkman.removeBreakpoint(bpList.get(i));
	    }
	    BreakPointActionListener bpl = editorPanel.bpal;
	    for(int i = 0;i<newBp.size();i++)
	    {
	    	
	    	try {
	    		//bpl.addBreakpoint((Integer)newBp.get(i)-1,session, bkman);
				Breakpoint bp = new SourceNameBreakpoint(editorPanel.owner.sourceSrc.getPackage(),
						editorPanel.owner.sourceSrc.getName(), (Integer)newBp.get(i));
				bkman.addNewBreakpoint(bp);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    ConsoleCenter.mainFrame.rightPartPane.groupPane.breakPane.refresh(session);
	}
	
}
