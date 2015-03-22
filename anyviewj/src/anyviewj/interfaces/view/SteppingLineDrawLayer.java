package anyviewj.interfaces.view;

import anyviewj.util.Defaults;
import java.awt.Color;
import java.util.prefs.Preferences;

/**
 * Class SteppingLineDrawLayer is responsible for drawing the highlight
 * on current stepping line within the text area.
 *
 * @author  ltt
 */
public class SteppingLineDrawLayer extends HighlightDrawLayer {
    /** Our draw layer priority. */
    private static final int PRIORITY = 128;

    /**
     * Constructs a SteppingLineDrawLayer to highlight using the default
     * color.
     */
    public SteppingLineDrawLayer() {
        super(new Color(128, 128, 255));
        setExtendsEOL(true);
        Preferences prefs = Preferences.userRoot().node(
            "com/bluemarsh/jswat/view");
        updateColor(prefs);
    } // SteppingLineDrawLayer

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

    /**
     * The user preferences have changed and the preferred colors may
     * have been modified. Update appropriately.
     *
     * @param  prefs  view Preferences node.
     * @throws  NumberFormatException
     *          if the specified color is improperly encoded.
     */
    void updateColor(Preferences prefs) throws NumberFormatException {
        String defaultColor = (String) Defaults.VIEW_COLORS.get(
            "colors.highlight");
        String color = prefs.get("colors.highlight", defaultColor);
        setColor(Color.decode(color));
    } // updateColor
} // SteppingLineDrawLayer
