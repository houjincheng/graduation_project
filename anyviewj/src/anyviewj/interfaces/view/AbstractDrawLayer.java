package anyviewj.interfaces.view;

/**
 * AbstractDrawLayer provides a default implementation of the DrawLayer
 * interface.
 *
 * @author  ltt
 */
public abstract class AbstractDrawLayer implements DrawLayer {
    /** True if this draw layer is actively affecting the text area. */
    private boolean active;
    /** True if this draw layer draws beyond the end of the line. */
    private boolean extendsEOL;

    /**
     * Indicates that this layer wants to affect the background color
     * beyond the end of the line of text.
     *
     * @return  true to extend past EOL, false otherwise.
     */
    @Override
	public boolean extendsEOL() {
        return extendsEOL;
    } // extendsEOL

    /**
     * Returns true if this draw layer wants to take part in the
     * current painting event.
     *
     * @return  true if active, false otherwise.
     */
    @Override
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
} // DrawLayer
