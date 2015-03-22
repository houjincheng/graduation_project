package anyviewj.interfaces.ui.listener;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JColorChooser;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextPane;
import javax.swing.text.Element;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import anyviewj.interfaces.ui.drawer.DebuggingHighlighter;
import anyviewj.interfaces.ui.panel.SourceEditor;
import anyviewj.interfaces.ui.panel.TextEditor;

public class SEActionListener implements MouseListener {
    private TextEditor editorPanel;
    private JTextPane rowArea;
    
    public JPopupMenu menu = new JPopupMenu();
    private JMenuItem keyColor = new JMenuItem("改变关键字颜色");
    private JMenuItem normalColor = new JMenuItem("改变非关键字颜色");
    private JMenuItem backgroundColor = new JMenuItem("改变背景颜色");
    private JMenuItem font = new JMenuItem("改变字体样式");
    
    
    public SEActionListener(TextEditor te){
    	this.editorPanel = te;
    	this.rowArea = te.rowArea;
    	
    	keyColor.addActionListener(new ActionListener(){
    		@Override
			public void actionPerformed(ActionEvent e){
    		   reChangeColor("keyWords");
    		}
    	});
    	
    	normalColor.addActionListener(new ActionListener(){
    		@Override
			public void actionPerformed(ActionEvent e){
    		   reChangeColor("normalWords");
    		}
    	});
    	
    	menu.add(keyColor);
    	menu.add(normalColor);
    	menu.add(backgroundColor);
    	menu.add(font);
    }
    
    public void reChangeColor(String type){
    	JColorChooser jc = new JColorChooser();
		Color color = JColorChooser.showDialog(editorPanel,"选择你想要的颜色", null);
		DebuggingHighlighter highlighter = editorPanel.owner.dbgER;
		SourceEditor se = (SourceEditor)editorPanel.editor;
		int lastLine = editorPanel.bpal.getLineCount(rowArea);
		Style style = ((StyledDocument) se.getDocument()).addStyle(
				"xStyle"+color.toString(), null);
		    StyleConstants.setBackground(style, Color.WHITE);
			StyleConstants.setBold(style, true);
			StyleConstants.setForeground(style, color);
		for(int i = 0;i<=lastLine;i++)
		{
			 Element map = se.getDocument().getDefaultRootElement();
			 if(map.getElement(i)==null)
			 {
				 continue;
			 }
             int p0 = map.getElement(i).getStartOffset();
             int p1 = map.getElement(i).getEndOffset();
             
             try{
              highlighter.reColouringWords(se.getStyledDocument(),p0,p1,type,style);
             }
             catch(Exception x){
            	 x.printStackTrace();
             }
		}
    }
    
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getButton()!=MouseEvent.BUTTON3)
			return;
		menu.show(editorPanel, e.getPoint().x, e.getPoint().y);
		
	}

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
//		System.out.println("Mouse enter TextEditor Pane");
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
