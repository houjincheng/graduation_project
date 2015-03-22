package anyviewj.interfaces.view;

import anyviewj.lang.BasicTokenInfo;

/**
 * Class JavaTokenInfo holds token information for Java source code.
 *
 * @author  ltt
 */
public class JavaTokenInfo extends BasicTokenInfo {
    /** Indicates that the token is a Java keyword. */
    public static final int TOKEN_KEYWORD = 1;
    /** Indicates that the token is a comment. */
    public static final int TOKEN_COMMENT = 2;
    /** Indicates that the token is a primitive type. */
    public static final int TOKEN_PRIMITIVE = 3;
    /** Indicates that the token is a number. */
    public static final int TOKEN_NUMBER = 4;
    /** Indicates that the token is a Java identifier. */
    public static final int TOKEN_IDENTIFIER = 5;
    /** Indicates that the token is a character. */
    public static final int TOKEN_CHARACTER = 6;
    /** Indicates that the token is a string. */
    public static final int TOKEN_STRING = 7;
    /** Indicates that the token is a literal (e.g. "null"). */
    public static final int TOKEN_LITERAL = 8;
    /** One fo the TOKEN_* constants. */
    private int tokenType;

    /**
     * Constructs a JavaTokenInfo with the given token type.
     *
     * @param  offset  offset to the start of the token.
     * @param  length  length of the token string.
     * @param  token   token type (one of the TOKEN_* constants).
     */
    public JavaTokenInfo(int offset, int length, int token) {
        super(offset, length);
        tokenType = token;
    } // JavaTokenInfo

    /**
     * Returns this token's type.
     *
     * @return  one of the TOKEN_* constants.
     */
    public int getTokenType() {
        return tokenType;
    } // getTokenType

    /**
     * Returns a string representation of this.
     *
     * @return  a String.
     */
    @Override
	public String toString() {
        StringBuffer buf = new StringBuffer(super.toString());
        buf.append(", type=");
        switch (tokenType) {
        case TOKEN_KEYWORD:
            buf.append("KWD");
            break;

        case TOKEN_COMMENT:
            buf.append("CMT");
            break;

        case TOKEN_PRIMITIVE:
            buf.append("PRM");
            break;

        case TOKEN_NUMBER:
            buf.append("NBR");
            break;

        case TOKEN_IDENTIFIER:
            buf.append("IDR");
            break;

        case TOKEN_CHARACTER:
            buf.append("CHR");
            break;

        case TOKEN_STRING:
            buf.append("STR");
            break;

        case TOKEN_LITERAL:
            buf.append("LIT");
            break;
        default:
            buf.append("UNK");
            break;
        }
        return buf.toString();
    } // toString
} // JavaTokenInfo
