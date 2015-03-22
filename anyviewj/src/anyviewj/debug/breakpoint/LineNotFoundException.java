package anyviewj.debug.breakpoint;

/**
 * LineNotFoundException is thrown when the user gives an invalid
 * line number.
 *
 * @author  ltt
 */
public class LineNotFoundException extends Exception {
    /** silence the compiler warnings */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a LineNotFoundException with no message.
     */
    public LineNotFoundException() {
        super();
    } // LineNotFoundException

    /**
     * Constructs a LineNotFoundException with the given message.
     *
     * @param  s  Message.
     */
    public LineNotFoundException(String s) {
        super(s);
    } // LineNotFoundException
} // LineNotFoundException
