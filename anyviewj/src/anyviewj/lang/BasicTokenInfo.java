package anyviewj.lang;

/**
 * This class provides a basic implementation of the <code>TokenInfo</code>
 * interface.
 *
 * @author  ltt
 */
public class BasicTokenInfo implements TokenInfo {
    /** Offset into the character buffer to the start of the token. */
    private int startOffset;
    /** Offset into the character buffer to the end of the token. */
    private int endOffset;
    /** Length of the token in characters. */
    private int length;

    /**
     * Constructs a BasicTokenInfo object using the given length and offset.
     *
     * @param  offset  offset to the start of the token.
     * @param  length  length of the token string.
     */
    public BasicTokenInfo(int offset, int length) {
        startOffset = offset;
        endOffset = offset + length;
        this.length = length;
    } // BasicTokenInfo

    /**
     * Get the length of this token in characters.
     *
     * @return  length of the token.
     */
    @Override
	public int getLength() {
        return length;
    } // getLength

    /**
     * Get the character offset within the document of the last
     * character in this token.
     *
     * @return  last character offset.
     */
    public int getEndOffset() {
        return endOffset;
    } // getEndOffset

    /**
     * Get the character offset within the document of the first
     * character in this token.
     *
     * @return  first character offset.
     */
    @Override
	public int getStartOffset() {
        return startOffset;
    } // getStartOffset

    /**
     * Returns a string representation of this.
     *
     * @return  a String.
     */
    @Override
	public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("offset=");
        buf.append(startOffset);
        buf.append(", length=");
        buf.append(length);
        return buf.toString();
    } // toString
} // BasicTokenInfo
