package anyviewj.interfaces.view;

/**
 * A DrawLayer is responsible for altering the graphics context in a
 * manner appropriate for the token that is about to be drawn.
 *
 * @author  ltt
 */
public interface DrawLayer {
    // The lower the priority number, the higher the precedence.
    // Otherwise we would have to reverse the list in order for the
    // higher precedence draw layers to draw last.

    /** Lowest priority value. */
    public static final int PRIORITY_LOWEST = 512;
    /** Highest priority value. */
    public static final int PRIORITY_HIGHEST = 64;

    /**
     * Indicates that this layer wants to affect the background color
     * beyond the end of the line of text.
     *
     * @return  true to extend past EOL, false otherwise.
     */
    boolean extendsEOL();

    /**
     * Gets the priority level of this particular draw layer. Typically
     * each type of draw layer has its own priority. Lower values are
     * higher priority.
     *
     * @return  priority level.
     */
    int getPriority();

    /**
     * Returns true if this draw layer wants to take part in the current
     * painting event.
     *
     * @return  true if active, false otherwise.
     */
    boolean isActive();

    /**
     * Update the draw context by setting colors, fonts and possibly
     * other draw properties. After making the changes, the draw layer
     * should return of the offset at which it would like to update the
     * context again. This is an efficiency heuristic.
     *
     * @param  ctx     draw context.
     * @param  offset  offset into character buffer indicating where
     *                 drawing is presently taking place.
     * @return  offset into character buffer at which this draw
     *          layer would like to update the draw context again.
     *          In other words, how long this updated context is valid
     *          for in terms of characters in the buffer.
     */
    int updateContext(DrawContext ctx, int offset);
} // DrawLayer
