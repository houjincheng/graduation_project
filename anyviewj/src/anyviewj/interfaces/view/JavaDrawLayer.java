
package anyviewj.interfaces.view;

import anyviewj.util.Defaults;
import anyviewj.util.SkipList;
import java.awt.Color;
import java.util.prefs.Preferences;

/**
 * JavaDrawLayer is responsible for syntax colorizing Java source code.
 *
 * @author  ltt
 */
public class JavaDrawLayer extends AbstractDrawLayer {
    /** Our draw layer priority. */
    private static final int PRIORITY = 256;
    /** The color for drawing characters. */
    private static Color characterColor;
    /** The color for drawing comments. */
    private static Color commentColor;
    /** The color for drawing identifiers. */
    private static Color identifierColor;
    /** The color for drawing keywords. */
    private static Color keywordColor;
    /** The color for drawing literals. */
    private static Color literalColor;
    /** The color for drawing numbers. */
    private static Color numberColor;
    /** The color for drawing primitives. */
    private static Color primitiveColor;
    /** The color for drawing strings. */
    private static Color stringColor;
    /** List of token info objects. */
    private SkipList tokenList;

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
     * Sets the list of Java tokens. If <code>tokens</code> is non-null
     * then this draw layer will set itself active; otherwise the layer
     * will become inactive.
     *
     * @param  tokens  the set of Java tokens.
     */
    void setTokens(SkipList tokens) {
        if (tokens == null) {
            // Nothing to do if no tokens.
            setActive(false);
        } else {
            setActive(true);
        }
        tokenList = tokens;
    } // setTokens

    /**
     * The user preferences have changed and the preferred colors
     * may have been modified. Update appropriately.
     *
     * @param  prefs  view Preferences node.
     * @throws  NumberFormatException
     *          if the specified color is improperly encoded.
     */
    static void updateColors(Preferences prefs) throws NumberFormatException {
        String color = prefs.get(
            "colors.character",
            (String) Defaults.VIEW_COLORS.get("colors.character"));
        characterColor = Color.decode(color);
        color = prefs.get(
            "colors.comment",
            (String) Defaults.VIEW_COLORS.get("colors.comment"));
        commentColor = Color.decode(color);
        color = prefs.get(
            "colors.identifier",
            (String) Defaults.VIEW_COLORS.get("colors.identifier"));
        identifierColor = Color.decode(color);
        color = prefs.get(
            "colors.keyword",
            (String) Defaults.VIEW_COLORS.get("colors.keyword"));
        keywordColor = Color.decode(color);
        color = prefs.get(
            "colors.literal",
            (String) Defaults.VIEW_COLORS.get("colors.literal"));
        literalColor = Color.decode(color);
        color = prefs.get(
            "colors.number",
            (String) Defaults.VIEW_COLORS.get("colors.number"));
        numberColor = Color.decode(color);
        color = prefs.get(
            "colors.primitive",
            (String) Defaults.VIEW_COLORS.get("colors.primitive"));
        primitiveColor = Color.decode(color);
        color = prefs.get(
            "colors.string",
            (String) Defaults.VIEW_COLORS.get("colors.string"));
        stringColor = Color.decode(color);
    } // updateColors

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
    @Override
	public int updateContext(DrawContext ctx, int offset) {
        // Search for a token at the given offset.
        JavaTokenInfo info = (JavaTokenInfo)
            tokenList.searchLeastSmaller(offset);
        
        // Did we find a correct match?
        if ((info != null)
            && (info.getStartOffset() <= offset)
            && (info.getEndOffset() > offset)) {

            // Modify the draw context appropriately.
            int type = info.getTokenType();

            switch (type) {
            case JavaTokenInfo.TOKEN_KEYWORD:
                ctx.setForeColor(keywordColor);
                break;

            case JavaTokenInfo.TOKEN_COMMENT:
                ctx.setForeColor(commentColor);
                break;

            case JavaTokenInfo.TOKEN_PRIMITIVE:
                ctx.setForeColor(primitiveColor);
                break;

            case JavaTokenInfo.TOKEN_NUMBER:
                ctx.setForeColor(numberColor);
                break;

            case JavaTokenInfo.TOKEN_IDENTIFIER:
                ctx.setForeColor(identifierColor);
                break;

            case JavaTokenInfo.TOKEN_CHARACTER:
                ctx.setForeColor(characterColor);
                break;

            case JavaTokenInfo.TOKEN_STRING:
                ctx.setForeColor(stringColor);
                break;

            case JavaTokenInfo.TOKEN_LITERAL:
                ctx.setForeColor(literalColor);
                break;
            default:
                // Do nothing.
                break;
            }
        } else {

            // Have to find the next token for the next update offset.
            info = (JavaTokenInfo) tokenList.searchNextLarger(offset);
            if (info == null) {
                return Integer.MAX_VALUE;
            } else {
                return info.getStartOffset();
            }
        }

        // The next time we'd like to change is just after this token.
        return info.getStartOffset() + info.getLength();
    } // updateContext
} // JavaDrawLayer
