
package anyviewj.interfaces.view;

import java.awt.Color;

/**
 * Class ErrorDrawLayer is responsible for drawing the highlight on the
 * line within the text area that contains an error.
 *
 * @author ltt
 */
public class ErrorDrawLayer extends HighlightDrawLayer {
    /** Our draw layer priority. */
    private static final int PRIORITY = 192;

    /**
     * Constructs a ErrorDrawLayer to highlight using the default color.
     */
    public ErrorDrawLayer() {
        super(new Color(255, 128, 128));
        setExtendsEOL(true);
    } // ErrorDrawLayer

    /**
     * Gets the priority level of this particular draw layer. Typically
     * each type of draw layer has its own priority. Lower values are
     * higher priority.
     *
     * @return  priority level.
     */
    @Override
	public int getPriority() {
        return PRIORITY;
    } // getPriority
} // ErrorDrawLayer
