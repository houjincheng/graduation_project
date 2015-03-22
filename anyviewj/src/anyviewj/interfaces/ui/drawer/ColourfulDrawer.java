package anyviewj.interfaces.ui.drawer;

import java.awt.Color;
import java.util.Set;

import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import anyviewj.interfaces.ui.panel.StringUtil;

public abstract class ColourfulDrawer {
	private static final Set<String> keywords = StringUtil.splitAsStringSet(
			"abstract|assert|boolean|break|byte|case|catch|char|class|const|" +
			"continue|default|do|double|else|enum|extends|final|finally|float|" +
			"for|if|goto|implements|import|instanceof|int|interface|long|native|new|" +
			"package|private|protected|public|return|short|static|strictfp|super|switch|" +
			"synchronized|this|throw|throws|transient|try|void|volatile|while|const|goto");
	
	protected final JTextPane editor;
	
	public Style keywordStyle;
	public Style normalStyle;
	public Style highlightKWStyle;
	public Style highlightNMStyle;
	
	public ColourfulDrawer(JTextPane editor) {
		this.editor = editor;
		keywordStyle = ((StyledDocument) editor.getDocument()).addStyle(
				"Keyword_Style", null);
		normalStyle = ((StyledDocument) editor.getDocument()).addStyle(
				"Keyword_Style", null);
		highlightKWStyle = ((StyledDocument) this.editor.getDocument()).addStyle(
				"highlightKWStyle", null);
		highlightNMStyle = ((StyledDocument) this.editor.getDocument()).addStyle(
				"highlightNMStyle", null);
        StyleConstants.setBackground(highlightKWStyle, Color.GRAY);
        StyleConstants.setBackground(highlightNMStyle, Color.GRAY);
        StyleConstants.setForeground(highlightKWStyle, new Color(127, 0, 85));
		StyleConstants.setForeground(keywordStyle, new Color(127, 0, 85));
		StyleConstants.setBold(highlightKWStyle, true);
		StyleConstants.setBold(keywordStyle, true);
		StyleConstants.setForeground(normalStyle, Color.BLACK);
		StyleConstants.setForeground(highlightNMStyle, Color.BLACK);
	}
	
	public ColourfulDrawer(JTextPane editor, Color color) {
		this.editor = editor;
		keywordStyle = ((StyledDocument) editor.getDocument()).addStyle(
				"Keyword_Style", null);
		normalStyle = ((StyledDocument) editor.getDocument()).addStyle(
				"Keyword_Style", null);
		highlightKWStyle = ((StyledDocument) this.editor.getDocument()).addStyle(
				"highlightKWStyle", null);
		highlightNMStyle = ((StyledDocument) this.editor.getDocument()).addStyle(
				"highlightNMStyle", null);
        StyleConstants.setBackground(highlightKWStyle, color);
        StyleConstants.setBackground(highlightNMStyle, color);
        StyleConstants.setForeground(highlightKWStyle, new Color(127, 0, 85));
		StyleConstants.setForeground(keywordStyle, new Color(127, 0, 85));
		StyleConstants.setBold(highlightKWStyle, true);
		StyleConstants.setBold(keywordStyle, true);
		StyleConstants.setForeground(normalStyle, Color.BLACK);
		StyleConstants.setForeground(highlightNMStyle, Color.BLACK);
	
	}
	
	
	public abstract void colouring(StyledDocument doc, int pos, int len);
	
	public int colouringCharacter(StyledDocument doc, int pos)
			throws BadLocationException {
		SwingUtilities.invokeLater(new ColouringTask(doc, pos, 1, normalStyle));
		pos++;
		return pos;
	}
	
	
	/**
	 * 对单词进行着色, 并返回单词结束的下标.
	 * 
	 * @param doc
	 * @param pos
	 * @return
	 * @throws BadLocationException
	 */
	public int colouringWord(StyledDocument doc, int pos)
			throws BadLocationException {
		int wordEnd = indexOfWordEnd(doc, pos);
		String word = doc.getText(pos, wordEnd - pos);

		if (keywords.contains(word)) {
			// 如果是关键字, 就进行关键字的着色, 否则使用普通的着色.
			// 这里有一点要注意, 在insertUpdate和removeUpdate的方法调用的过程中, 不能修改doc的属性.
			// 但我们又要达到能够修改doc的属性, 所以把此任务放到这个方法的外面去执行.
			// 实现这一目的, 可以使用新线程, 但放到swing的事件队列里去处理更轻便一点.
			SwingUtilities.invokeLater(new ColouringTask(doc, pos, wordEnd - pos, keywordStyle));
		} else {
			SwingUtilities.invokeLater(new ColouringTask(doc, pos, wordEnd - pos, normalStyle));
		}

		return wordEnd;
	}
	
	public int cleanColour(StyledDocument doc, int startPos, int endPos) {
		try {
			int start = indexOfWordStart(doc, startPos);
			int end = indexOfWordEnd(doc, endPos);
			char ch;
			SwingUtilities.invokeLater(new ColouringTask(doc, startPos, endPos - startPos, normalStyle));
			
			while (start < end) {
				ch = getCharAt(doc, start);
				if (Character.isLetter(ch) || ch == '_') {
					int wordEnd = indexOfWordEnd(doc, start);
					String word = doc.getText(start, wordEnd - start);
					if (keywords.contains(word)) {
						SwingUtilities.invokeLater(new ColouringTask(doc, start, wordEnd - start, keywordStyle));
					}
					start = wordEnd;
					
				} else {
					start++;
				}
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		return endPos;
	}
	
	/**
	 * 对单词进行着色, 并返回单词结束的下标.
	 * 
	 * @param doc
	 * @param pos
	 * @return
	 * @throws BadLocationException
	 */
	public int colouringRows(StyledDocument doc, int startPos, int endPos)
			throws BadLocationException {
		int start = indexOfWordStart(doc, startPos);
		int end = indexOfWordEnd(doc, endPos);
		char ch;
		SwingUtilities.invokeLater(new ColouringTask(doc, startPos, endPos - startPos, highlightNMStyle));
		
		while (start < end) {
			ch = getCharAt(doc, start);
			if (Character.isLetter(ch) || ch == '_') {
				int wordEnd = indexOfWordEnd(doc, start);
				String word = doc.getText(start, wordEnd - start);

				if (keywords.contains(word)) {
					SwingUtilities.invokeLater(new ColouringTask(doc, start, wordEnd - start, highlightKWStyle));
				}
				start = wordEnd;
				
			} else {
				start++;
			}
		}
		return endPos;
	}
	
	/**
	 * 取得在文档中下标在pos处的字符.
	 * 
	 * 如果pos为doc.getLength(), 返回的是一个文档的结束符, 不会抛出异常. 如果pos<0, 则会抛出异常.
	 * 所以pos的有效值是[0, doc.getLength()]
	 * 
	 * @param doc
	 * @param pos
	 * @return
	 * @throws BadLocationException
	 */
	public char getCharAt(Document doc, int pos) throws BadLocationException {
		return doc.getText(pos, 1).charAt(0);
	}

	/**
	 * 取得下标为pos时, 它所在的单词开始的下标. Â±wor^dÂ± (^表示pos, Â±表示开始或结束的下标)
	 * 
	 * @param doc
	 * @param pos
	 * @return
	 * @throws BadLocationException
	 */
	public int indexOfWordStart(Document doc, int pos)
			throws BadLocationException {
		// 从pos开始向前找到第一个非单词字符.
		int i = pos;
		for (; i > 0 && isWordCharacter(doc, i - 1); --i)
			;

		return i;
	}
	
	/**
	 * 取得下标为pos时, 它所在的单词结束的下标. Â±wor^dÂ± (^表示pos, Â±表示开始或结束的下标)
	 * 
	 * @param doc
	 * @param pos
	 * @return
	 * @throws BadLocationException
	 */
	public int indexOfWordEnd(Document doc, int pos)
			throws BadLocationException {
		// 从pos开始向前找到第一个非单词字符.
		int i = pos;
		for (; isWordCharacter(doc, i); ++i)
			;

		return i;
	}
	
	/**
	 * 取得下标为pos时, 它所在的行结束的下标.
	 * 
	 * @param doc
	 * @param pos
	 * @return
	 * @throws BadLocationException
	 */
	public int indexOfRowEnd(Document doc, int pos, int end)
			throws BadLocationException {
		// 从pos开始向前找到第一个非单词字符.
		pos += doc.getText(pos, end-pos).indexOf('\n');
		return pos;
	}

	/**
	 * 如果一个字符是字母, 数字, 下划线, 则返回true.
	 * 
	 * @param doc
	 * @param pos
	 * @return
	 * @throws BadLocationException
	 */
	public boolean isWordCharacter(Document doc, int pos)
			throws BadLocationException {
		char ch = getCharAt(doc, pos);
		if (Character.isLetter(ch) || Character.isDigit(ch) || ch == '_') {
			return true;
		}
		return false;
	}
	
	public Set<String> getKeywords(){
		return ColourfulDrawer.keywords;
	}
	
	
	/**
	 * 完成着色任务
	 * 
	 * @author Biao
	 * 
	 */
	public class ColouringTask implements Runnable {
		private StyledDocument doc;
		private Style style;
		private int pos;
		private int len;

		public ColouringTask(StyledDocument doc, int pos, int len, Style style) {
			this.doc = doc;
			this.pos = pos;
			this.len = len;
			this.style = style;
		}

		@Override
		public void run() {
			try {
				// 这里就是对字符进行着色
				doc.setCharacterAttributes(pos, len, style, true);
			} catch (Exception e) {
			}
		}
	}
	
}
