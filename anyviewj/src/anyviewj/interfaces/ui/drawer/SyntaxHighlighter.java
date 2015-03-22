package anyviewj.interfaces.ui.drawer;

import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.StyledDocument;

import anyviewj.interfaces.ui.listener.LineRemover;
import anyviewj.interfaces.ui.panel.TextEditor;

/**
 * 当文本输入区的有字符插入或者删除时, 进行高亮.
 * 
 * 要进行语法高亮, 文本输入组件的document要是styled document才行. 所以不要用JTextArea. 可以使用JTextPane.
 * 
 */
public class SyntaxHighlighter extends ColourfulDrawer implements DocumentListener {
	private int oldLines = 1;
	StringBuilder str = new StringBuilder();
	private JTextPane rowArea;
	public LineRemover lr; 
	
    public int getLineCount(JTextPane tp) {
        //tp是一个JTextPane
        Element map = tp.getDocument().getDefaultRootElement();
        return map.getElementCount();
    }

	public SyntaxHighlighter(TextEditor editorPanel){
		super(editorPanel.editor);
		this.rowArea = editorPanel.rowArea;
		lr = new LineRemover(editorPanel);
	}
	
	@Override
	public void colouring(StyledDocument doc, int pos, int len) {
		// 取得插入或者删除后影响到的单词.
		// 例如"public"在b后插入一个空格, 就变成了:"pub lic", 这时就有两个单词要处理:"pub"和"lic"
		// 这时要取得的范围是pub中p前面的位置和lic中c后面的位置
		try {
			int start = indexOfWordStart(doc, pos);
			int end = indexOfWordEnd(doc, pos + len);
	
			char ch;
		
			while (start < end) {
				
				ch = getCharAt(doc, start);
				if (Character.isLetter(ch) || ch == '_') {
					// 如果是以字母或者下划线开头, 说明是单词
					// pos为处理后的最后一个下标
					start = colouringWord(doc, start);
				}/* else if(ch == '/') {
					ch = getCharAt(doc, start+1);
					if(getCharAt(doc, start+1) == '/' || ( (start -1 > 0) && getCharAt(doc, start-1) == '/')) {
						start = colouringRows(doc, start, end);
					} else {
						SwingUtilities.invokeLater(new ColouringTask(doc, start, 1,
								normalStyle));
						++start;
					}
				} */else {
					start = colouringCharacter(doc, start);
				}
			}
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		int lines = getLineCount(editor);
		if(oldLines != lines) { 
//			System.out.println("监听事件，removeUpdate line = " + lines);
			str = new StringBuilder();
			for(int i=1; i<=lines; i++) {
				str.append(" " + i +" \n");
			}
			rowArea.setText(str.toString());
//			rowArea.setSize(new Dimension(80, rowArea.getHeight()));
			oldLines = lines;
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
			int lines = getLineCount(editor);
			if(oldLines != lines) {
//				System.out.println("监听事件，removeUpdate line = " + lines);
				str = new StringBuilder();
				for(int i=1; i<=lines; i++) {
					str.append(" " + i +" \n");
				}
				rowArea.setText(str.toString());
//				rowArea.setSize(new Dimension(80, rowArea.getHeight()));
				oldLines = lines;
			}
			colouring((StyledDocument) e.getDocument(), e.getOffset(),
					e.getLength());
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
			int lines = getLineCount(editor);
//			Font font = new Font(Font.SANS_SERIF,Font.BOLD,100);
//			rowArea.setFont(font);
			if(oldLines != lines) { 
//				System.out.println("监听事件，removeUpdate line = " + lines);
				str = new StringBuilder();
				lr.countLine();
				for(int i=1; i<=lines; i++) {
					str.append(" " + i +" \n");
				}
				rowArea.setText(str.toString());
				//rowArea.setSize(new Dimension(80, rowArea.getHeight()));
				oldLines = lines;
			}
			// 因为删除后光标紧接着影响的单词两边, 所以长度就不需要了
			//colouring((StyledDocument) e.getDocument(), e.getOffset(), 0);
	}

}
