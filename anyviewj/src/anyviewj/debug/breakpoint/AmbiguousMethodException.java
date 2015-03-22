package anyviewj.debug.breakpoint;

/**
 * AmbiguousMethodException is thrown when the user gives a method name
 * that has more than one match.
 *
 * @author  ltt
 */
public class AmbiguousMethodException extends Exception {
    /** silence the compiler warnings */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a AmbiguousMethodException with no message.
     */
    public AmbiguousMethodException() {
        super();
    } // AmbiguousMethodException

    /**
     * Constructs a AmbiguousMethodException with the given message.
     *
     * @param  s  Message.
     */
    public AmbiguousMethodException(String s) {
        super(s);
    } // AmbiguousMethodException
} // AmbiguousMethodException
