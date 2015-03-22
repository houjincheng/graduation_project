package anyviewj.interfaces.ui.drawer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JTextPane;

public class GutterArea extends JTextPane{
	private static final long serialVersionUID = 6490300118068599473L;

	private BreakPointDrawer bpDrawer;
	
	
	private final int lineHeight = getFontMetrics(getFont()).getHeight();
	
	@Override
	public boolean getScrollableTracksViewportWidth() {
		return false;
	}
	@Override
	public void setSize(Dimension d) {
		if(d.width<getParent().getSize().width) {
			d.width=getParent().getSize().width;
		}
		super.setSize(d);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if(!isVisible()) {
			return;
		}

		
		if(bpDrawer != null && bpDrawer.getLineColors().size() > 0) {
			Rectangle clipRect = g.getClipBounds();
			int clipY = clipRect.y;
			int clipH = clipRect.height;
			int firstLine = (clipY / lineHeight) + 1;
			int lastLine = ((clipY + clipH) / lineHeight) + 1;
			Color lineColor = null;
			int y = 0;
			Graphics2D g2 = (Graphics2D)g;
			
			for(int line = firstLine; line <= lastLine; line++) {
				lineColor = bpDrawer.getLineColor(line);
				if(lineColor != null) {
					g2.setColor(lineColor);
					y = (line * lineHeight) + (lineHeight / 2);
					g2.fillOval(0,y,10,10);
				}
			}
		}
	}
	
	public BreakPointDrawer getBpDrawer() {
		return bpDrawer;
	}
	public void setBpDrawer(BreakPointDrawer bpDrawer) {
		this.bpDrawer = bpDrawer;
	}
}
