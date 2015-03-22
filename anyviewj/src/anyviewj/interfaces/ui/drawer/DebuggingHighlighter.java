package anyviewj.interfaces.ui.drawer;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;

import anyviewj.interfaces.ui.panel.SourceEditor;
import anyviewj.interfaces.view.DrawContext;
import anyviewj.util.Defaults;

public class DebuggingHighlighter extends ColourfulDrawer{
	 /** Lowest priority value. */
    public static final int PRIORITY_LOWEST = 512;
    /** Highest priority value. */
    public static final int PRIORITY_HIGHEST = 64;
    /** Our draw layer priority. */
    private static final int PRIORITY = 128;
	/** Start of the text highlight. */
    private int highlightStart;
    /** End of the text highlight. */
    private int highlightEnd;
    /** Color used to highlight some area. */
    private Color highlightColor;
    
    /** True if this draw layer is actively affecting the text area. */
    private boolean active;
    /** True if this draw layer draws beyond the end of the line. */
    private boolean extendsEOL;

    public List lineList = new ArrayList(); 
    
    
    /**
     * Constructs a HighlightDrawLayer to highlight using the given
     * color.
     *
     * @param  color  highlight color.
     */
    public DebuggingHighlighter(JTextPane editor, Color color) {
    	super(editor,color);
        setExtendsEOL(true);
        Preferences prefs = Preferences.userRoot().node(
            "anyviewj/interfaces/view");
        updateColor(prefs);
        setActive(true);
    } // HighlightDrawLayer
  
    
    /**
     * Returns the highlighted text's end position. Return 0 if the
     * document is empty, or the value of dot if there is no highlight.
     *
     * @return  the end position >= 0
     */
    public int getHighlightEnd() {
        return highlightEnd;
    } // getHighlightEnd

    /**
     * Returns the highlighted text's start position. Return 0 if the
     * document is empty, or the value of dot if there is no highlight.
     *
     * @return  the start position >= 0
     */
    public int getHighlightStart() {
        return highlightStart;
    } // getHighlightStart

    /**
     * Sets the color to be used for highlighting.
     *
     * @param  color  color to use for highlighting.
     */
    public void setColor(Color color) {
        highlightColor = color;
    } // setColor

	public void reDrawBP(SourceEditor se){
		for(int i = 0;i<=lineList.size()-2;i+=2)
		{
			this.setHighlight(se.getStyledDocument(),(Integer)lineList.get(i), (Integer)lineList.get(i+1));
            this.colouring(se.getStyledDocument(),(Integer)lineList.get(i),(Integer)lineList.get(i+1));
		}
	}
    
    public Boolean checkClean(int begin, int end){
    	for(int i = 0;i<=lineList.size()-2;i+=2)
    	{
    		if(begin==(Integer)lineList.get(i)&&end==(Integer)lineList.get(i+1))
    			return false;
    	}
    	return true;
    }
    
    public void bpLineStore(int line, SourceEditor se){
    	lineList.add(se.getDocument().getDefaultRootElement().getElement(line).getStartOffset());
    	lineList.add(se.getDocument().getDefaultRootElement().getElement(line).getEndOffset());
    }
    
    public void bpLineDelete(int line, SourceEditor se)
    {
    	
    	int s = se.getDocument().getDefaultRootElement().getElement(line).getStartOffset();
        int e = se.getDocument().getDefaultRootElement().getElement(line).getEndOffset();
    	for(int i = lineList.size()-1;i>=0;i-=2)
    	{
    		if(s==(Integer)lineList.get(i-1)&&e==(Integer)lineList.get(i))
    		{
    			lineList.remove(i);
    			lineList.remove(i-1);
    			return ;
    		}
    	}
    }
    
    public void setHighlightCopy(StyledDocument doc, int start, int end) {
    	cleanColour(doc,start, end);
    } // setHighlightCopy
    
    /**
     * Sets the start and end offsets of the text to be highlighted.
     *
     * @param  start  start offset of the highlight.
     * @param  end    end offset of the highlight.
     */
    public void setHighlight(StyledDocument doc, int start, int end) {
    	//先清除上次画的背景
    	if(checkClean(highlightStart,highlightEnd))
      	cleanColour(doc, highlightStart, highlightEnd);
        highlightStart = start;
        highlightEnd = end;
        if (start == end) {
            setActive(false);
        } else {
            setActive(true);
        }
    } // setHighlight

    /**
     * Update the draw context by setting colors, fonts and possibly
     * other draw properties. After making the changes, the draw
     * layer should return of the offset at which it would like to
     * update the context again. This is an efficiency heuristic.
     *
     * @param  ctx     draw context.
     * @param  offset  offset into character buffer indicating where
     *                 drawing is presently taking place.
     * @return  offset into character buffer at which this draw
     *          layer would like to update the draw context again.
     *          In other words, how long this updated context is valid
     *          for in terms of characters in the buffer.
     */
    public int updateContext(DrawContext ctx, int offset) {
        if (offset < highlightStart) {
            return highlightStart;
        }
        // If this comparison is just > we loop forever because we
        // fail to advance past the last character of the highlight.
        if (offset >= highlightEnd || highlightColor == null) {
            return Integer.MAX_VALUE;
        }
        ctx.setBackColor(highlightColor);
        return highlightEnd;
    } // updateContext
    
    /**
     * Indicates that this layer wants to affect the background color
     * beyond the end of the line of text.
     *
     * @return  true to extend past EOL, false otherwise.
     */
    public boolean extendsEOL() {
        return extendsEOL;
    } // extendsEOL

    /**
     * Returns true if this draw layer wants to take part in the
     * current painting event.
     *
     * @return  true if active, false otherwise.
     */
    public boolean isActive() {
        return active;
    } // isActive

    /**
     * Controls the active state of this draw layer.
     *
     * @param  active  true to be active, false to be inactive.
     */
    public void setActive(boolean active) {
        this.active = active;
    } // setActive

    /**
     * Sets the extends end-of-line property.
     *
     * @param  extendsEOL  true to extend past the end of the line,
     *                     false otherwise.
     */
    public void setExtendsEOL(boolean extendsEOL) {
        this.extendsEOL = extendsEOL;
    } // setExtendsEOL
    
    public int getPriority() {
        return PRIORITY;
    }
    
    void updateColor(Preferences prefs) throws NumberFormatException {
        String defaultColor = (String) Defaults.VIEW_COLORS.get(
            "colors.highlight");
        String color = prefs.get("colors.highlight", defaultColor);
        setColor(Color.decode(color));
    }

	public Color getHighlightColor() {
		return highlightColor;
	}

	@Override
	public void colouring(StyledDocument doc, int startPos, int endPos) {
		try {
			if((highlightStart == highlightEnd) || startPos == endPos) {
				
			} else {
//				System.out.println("original !!!!!!!  : "+startPos+"  "+endPos);
				colouringRows(doc, startPos, endPos);
			} 
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	public void reColouringWords(StyledDocument doc, int startPos, int endPos, String what, Style style)
			throws BadLocationException {
//		
		if( startPos == endPos)
		{	
		    return;
		}
		int start = indexOfWordStart(doc, startPos);
		int end;
		if(getCharAt(doc,startPos)!='}')
		 end = indexOfWordEnd(doc, endPos);
		else
		end = start+1;
		char ch;
		if(what=="normalWords")
		 SwingUtilities.invokeLater(new ColouringTask(doc, startPos, endPos - startPos, style));
		while (start < end) {
			ch = getCharAt(doc, start);
			if (Character.isLetter(ch) || ch == '_') {
				int wordEnd = indexOfWordEnd(doc, start);
				String word = doc.getText(start, wordEnd - start);
				if (getKeywords().contains(word)) {
					if(what=="normalWords")
					SwingUtilities.invokeLater(new ColouringTask(doc, start, wordEnd - start, this.keywordStyle));
					else
					{
						SwingUtilities.invokeLater(new ColouringTask(doc, start, wordEnd - start, style));
					    keywordStyle = style;
					}
				}
				start = wordEnd;
				
			} else {
				start++;
			}
		}
		return ;

	}
	
}
