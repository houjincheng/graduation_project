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
 * ���ı������������ַ��������ɾ��ʱ, ���и���.
 * 
 * Ҫ�����﷨����, �ı����������documentҪ��styled document����. ���Բ�Ҫ��JTextArea. ����ʹ��JTextPane.
 * 
 */
public class SyntaxHighlighter extends ColourfulDrawer implements DocumentListener {
	private int oldLines = 1;
	StringBuilder str = new StringBuilder();
	private JTextPane rowArea;
	public LineRemover lr; 
	
    public int getLineCount(JTextPane tp) {
        //tp��һ��JTextPane
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
		// ȡ�ò������ɾ����Ӱ�쵽�ĵ���.
		// ����"public"��b�����һ���ո�, �ͱ����:"pub lic", ��ʱ������������Ҫ����:"pub"��"lic"
		// ��ʱҪȡ�õķ�Χ��pub��pǰ���λ�ú�lic��c�����λ��
		try {
			int start = indexOfWordStart(doc, pos);
			int end = indexOfWordEnd(doc, pos + len);
	
			char ch;
		
			while (start < end) {
				
				ch = getCharAt(doc, start);
				if (Character.isLetter(ch) || ch == '_') {
					// ���������ĸ�����»��߿�ͷ, ˵���ǵ���
					// posΪ���������һ���±�
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
//			System.out.println("�����¼���removeUpdate line = " + lines);
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
//				System.out.println("�����¼���removeUpdate line = " + lines);
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
//				System.out.println("�����¼���removeUpdate line = " + lines);
				str = new StringBuilder();
				lr.countLine();
				for(int i=1; i<=lines; i++) {
					str.append(" " + i +" \n");
				}
				rowArea.setText(str.toString());
				//rowArea.setSize(new Dimension(80, rowArea.getHeight()));
				oldLines = lines;
			}
			// ��Ϊɾ�����������Ӱ��ĵ�������, ���Գ��ȾͲ���Ҫ��
			//colouring((StyledDocument) e.getDocument(), e.getOffset(), 0);
	}

}
