package anyviewj.interfaces.ui.panel;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AbstractDocument.DefaultDocumentEvent;
import javax.swing.text.BadLocationException;
import javax.swing.undo.UndoManager;

import anyviewj.interfaces.ui.drawer.GutterArea;
import anyviewj.interfaces.ui.drawer.SyntaxHighlighter;
import anyviewj.interfaces.ui.listener.BreakPointActionListener;
import anyviewj.interfaces.ui.listener.NewCaretListener;
import anyviewj.interfaces.ui.listener.SEActionListener;

public class TextEditor extends JPanel {
	private static final long serialVersionUID = 3732144123023317064L;
	
	public final SourcePane owner;
	
	//
    private JScrollPane scrollpaneMain = null;
    private JScrollPane scrollpane = null;
    
	//��ʾ�кŵ��ı���
//    public final JTextPane rowArea = new JTextPane();
    public final JTextPane rowArea;
    //�ı��༭��
//    public final JTextPane editor = new JTextPane();
    public final JTextPane editor;
	//��������
    public UndoManager um = new UndoManager();
    
    public  BreakPointActionListener bpal;
    public  SEActionListener seal; 
    public  NewCaretListener ncl;
    
	public TextEditor(SourcePane owner) {
		super(new BorderLayout());
		this.owner = owner;
		//��ʼ���༭��
		
		rowArea = new GutterArea();
        rowArea.setEditable(false);
        editor = new SourceEditor(owner);
        scrollpane = new JScrollPane(editor,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollpaneMain = new JScrollPane(rowArea,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollpane.setVerticalScrollBar(scrollpaneMain.getVerticalScrollBar());
        add(scrollpaneMain, BorderLayout.WEST);
        add(scrollpane, BorderLayout.CENTER);
		
        editor.getDocument().addDocumentListener(new SyntaxHighlighter(this));
 //       if(null != owner && null != owner.sourceSrc && null != owner.sourceSrc.getPackage()) {
        	//�����򿪵��ļ�������Ӷϵ�
            bpal = new BreakPointActionListener(this);
            bpal.setCD(owner.owner);
        	rowArea.addMouseListener(bpal);
        	ncl = new NewCaretListener(this);
            editor.addKeyListener(ncl);
        	
 //       }
		
		rowArea.setText(" 1 \n");
		
		seal = new SEActionListener(this);
		editor.addMouseListener(seal);
		
	}
	
	/**
	 * �����ı��༭���ĳ�ʼ�ı�Ϊstr������ӳ���������� <br>
	 * ��ʱ���Զ����ó�������ʼ�� <br>
	 * 
	 */
	public void setTextAndAddUndoable(String str) {
		editor.setText(str);
		editor.getDocument().addUndoableEditListener(new UndoableEditListener() {
			
			@Override	
			public void undoableEditHappened(UndoableEditEvent e) {
				if(!DocumentEvent.EventType.CHANGE.equals(((DefaultDocumentEvent)(e.getEdit())).getType())) {
					um.addEdit(e.getEdit());
				}
			}
		});
	}
	
	public boolean undo() {
    	boolean result = false;
    	if(um.canUndo()) {
    		um.undo();
    		result = true;
    	}
    	return result;
    }
    
    public boolean redo() {
    	boolean result = false;
    	if(um.canRedo()) {
    		um.redo();
    		result = true;
    	}
    	return result;
    }
    
    public void copy() {
    	this.editor.copy();
    }
    
    public void cut() {
    	this.editor.cut();
    }
    
    public void paste() {
    	this.editor.paste();
    }
    
    public void delete() {
    	int start = this.editor.getSelectionStart();
    	int end = this.editor.getSelectionEnd();
    	if(start == 0 && end == 0)
    		return;
    	try {
			String textString = this.editor.getText(0, start) + this.editor.getText(end, editor.getText().length()-end);
			this.editor.setText(textString);
		} catch (BadLocationException e) {
			e.printStackTrace();
		} 
    }

    public void selectAll() {
    	this.editor.selectAll();
    }
    
}
