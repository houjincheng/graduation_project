package anyviewj.interfaces.ui.drawer;

import java.util.Set;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.StyledDocument;

import anyviewj.console.ConsoleCenter;
import anyviewj.debug.session.BasicSession;
import anyviewj.interfaces.ui.panel.SourceEditor;
import anyviewj.interfaces.ui.panel.SourcePane;
import anyviewj.interfaces.ui.panel.StringUtil;

public class InvalidBreakpointJudger extends ColourfulDrawer{
	
	public SourceEditor se;
	public Set<String> keywords;
	
	
	 public InvalidBreakpointJudger(JTextPane editor) {
		super(editor);
		// TODO Auto-generated constructor stub
		keywords = super.getKeywords();
		se = (SourceEditor) editor;
	}
    
	 public int findHead(int lastline, SourcePane sp) throws BadLocationException{
		 int temp = 0;
		 String name = sp.sourceSrc.getName();
			name = name.substring(0, name.indexOf( "." ) );
		 for(;temp<=lastline;temp++){
			 
			 Element map = se.getDocument().getDefaultRootElement();
	         int p0 = map.getElement(temp).getStartOffset();
	         int p1 = map.getElement(temp).getEndOffset();
	         StyledDocument doc = se.getStyledDocument();
	         int start = 0;
	         int end = 0;
	         Set<String> classword = StringUtil.splitAsStringSet("public|class|implements|extends|"+name);
			 
	         
	         
	         
	         try {
				start = indexOfWordStart(doc, p0);
				if(getCharAt(doc,p0)!='}')
			 		 end = indexOfWordEnd(doc,p1);
			 		else
			 		end = start+1;
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			char ch;
			int classcheck = 0;
			while (start < end) {
				ch = getCharAt(doc, start);
				if (Character.isLetter(ch) || ch == '_') {
					int wordEnd = indexOfWordEnd(doc, start);
					String word = doc.getText(start, wordEnd - start);
					if(classword.contains(word))
					{
						classcheck++;
						if(classcheck>=3)
							break;
					}
					start = wordEnd;
					
				} else {
					start++;
				}
			} 
			 if(classcheck>=3)
			 {
				// System.out.println("IIIIIIIIIIIIIIIIIIIIIIIIIIIII  "+temp);
				 break;
			 }
			 
		 }
		 
		 return temp;
	 }
	 
	 
	public Boolean JudgeInvalid(int line) throws BadLocationException{
		SourcePane sp =  ((BasicSession)ConsoleCenter.getCurrentSession()).getCurrentSourcePane();
		int lastLine =  sp.editorPanel.bpal.getLineCount(sp.editorPanel.rowArea);
		se = (SourceEditor) sp.editorPanel.editor;
		String name = sp.sourceSrc.getName();
		name = name.substring(0, name.indexOf( "." ) );
		int beforeHead = 0;
		  try {
				beforeHead = findHead(lastLine-2,sp);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		 if(line>=lastLine-2)
		 {
			 System.out.println("out Of boundries!!!!!!!!!!");
			 return true;
		 }
		 if(line<=beforeHead)
		 {
			 System.out.println("out Of boundries!!!!!!!!!!");
			 return true;
		 } 
		 Element map = se.getDocument().getDefaultRootElement();
         int p0 = map.getElement(line).getStartOffset();
         int p1 = map.getElement(line).getEndOffset();
         StyledDocument doc = se.getStyledDocument();
         int start = 0;
         int end = 0;
         Set<String> mainword = StringUtil.splitAsStringSet("public|void|main|static");

		 
         
         
         
         try {
			start = indexOfWordStart(doc, p0);
			if(getCharAt(doc,p0)!='}')
		 		 end = indexOfWordEnd(doc,p1);
		 		else
		 		end = start+1;
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		char ch;
		Boolean blank = true;
		int check = 0;
		while (start < end) {
			ch = getCharAt(doc, start);
			if(ch=='}'||ch=='{')
				blank=false;
			if (Character.isLetter(ch) || ch == '_') {
				blank = false;
				int wordEnd = indexOfWordEnd(doc, start);
				String word = doc.getText(start, wordEnd - start);
				if (mainword.contains(word)) {
                     check++;
				}
				start = wordEnd;
				
			} else {
				start++;
			}
		} 
         
        if(blank)
        	{
        	 System.out.println("is blank? ! : "+blank);
        	 return true;
        	}
        else if(check>=4)
        {
         System.out.println("main ? : "+check);
         return true;
        } 
//		try {
//			start = indexOfWordStart(doc,p0);
//			 end = indexOfWordEnd(doc,p1);
//		} catch (BadLocationException e) {
//			// TODO Auto-generated catch block
//			 System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  "+p0+" "+p1+" "+line);
//		}
//         //if(start==end)
//        	 System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  "+start+" "+end+" ");
         return false;
	};
	 
	 
	@Override
	public void colouring(StyledDocument doc, int pos, int len) {
		// TODO Auto-generated method stub
		
	}}
