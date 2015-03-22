package anyviewj.interfaces.ui.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.swing.JTextPane;
import javax.swing.text.Caret;
import javax.swing.text.Element;

import anyviewj.console.ConsoleCenter;
import anyviewj.debug.breakpoint.Breakpoint;
import anyviewj.debug.breakpoint.ResolvableBreakpoint;
import anyviewj.debug.breakpoint.SourceNameBreakpoint;
import anyviewj.debug.manager.BreakpointManager;
import anyviewj.debug.session.Session;
import anyviewj.interfaces.ui.panel.SourceEditor;
import anyviewj.interfaces.ui.panel.TextEditor;

public class NewCaretListener implements KeyListener{
    protected int firstLine;
    public int currentLine;
    public int count;
    
    public Boolean check;
    
    public TextEditor editorPanel;
    public JTextPane rowArea;
    
    public NewCaretListener(TextEditor editorPanel){
    	this.editorPanel = editorPanel;
		this.rowArea = editorPanel.rowArea;
		check = true;
    }
    
//	@Override
//	public void caretUpdate(CaretEvent e) {
//		// TODO Auto-generated method stub
//		if(check)
//		{
//			check = false;
//			Element map = editorPanel.editor.getDocument().getDefaultRootElement();
//			SourceEditor se = (SourceEditor) editorPanel.editor;
//			Caret car = se.getCaret();
//			
//			System.out.println("--------  "+car.getMagicCaretPosition()+"  "+se.getCaretPosition());
//                System.out.println("    "+e.getDot()+"  "+e.getMark()+" "+map.getElementIndex(e.getDot()));
//		}
//	}




	
	public void BreakpointReload(){
		//先移除先前加入的无效的断点
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
	    for(Object x : key)
	    {
	    	
	    	Breakpoint bp = (Breakpoint)ht.get(x);
	    	if(bp instanceof ResolvableBreakpoint)
	    	{   
	    		if(firstLine+1<((SourceNameBreakpoint)bp).lineNum)
	    		{
	    		 //System.out.println("    "+((SourceNameBreakpoint)bp).lineNum+" sdsdsd\n "+count+"   "+(((SourceNameBreakpoint)bp).lineNum+count+1));
	    	     bpList.add(bp);
	    	     newBp.add(((SourceNameBreakpoint)bp).lineNum+count+1);
	    		}
	    	}	    	
	    }
	    if(newBp.isEmpty())
	    	return;
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
//	    key = ht.keySet();
//	    for(Object a: key)
//	    {
//	    	Breakpoint bp = (Breakpoint)ht.get(a);
//	    	if(bp instanceof ResolvableBreakpoint)
//	    	{   
//	    		
//              System.out.println("   "+bp.toString());
//
//	    	}	    	
//	    }
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()!=KeyEvent.VK_ENTER)
			return;
		Element map = editorPanel.editor.getDocument().getDefaultRootElement();
		SourceEditor se = (SourceEditor) editorPanel.editor;
		Caret car = se.getCaret();
		if(check)
		{
			//check = false;			
			firstLine = map.getElementIndex(car.getDot());			
		}
		currentLine = map.getElementIndex(car.getDot());		
		count = currentLine-firstLine;
		int p0 = map.getElement(currentLine).getStartOffset();
		int p1 = map.getElement(currentLine).getEndOffset();
		editorPanel.bpal.dbg.cleanColour(editorPanel.editor.getStyledDocument(),p0, p1);
		System.out.println("--------  firstLine "+firstLine+"  currentLine:  "+(currentLine+2));
	    BreakpointReload();
//	    reDrawHighlight(car,map);
//	    editorPanel.bpal.dbg.reDrawBP((SourceEditor) editorPanel.editor);
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
		    	 System.out.println("++++++++++++  : "+"  "+((SourceNameBreakpoint)bp).lineNum+"  "+( map.getElementIndex(car.getDot())+2));
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
	
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
 