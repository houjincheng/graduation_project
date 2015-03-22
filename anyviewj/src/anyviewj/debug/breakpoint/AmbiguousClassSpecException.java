package anyviewj.debug.breakpoint;

/**
 * AmbiguousClassSpecException is thrown when the user gives a class
 * specification that has more than one match.
 *
 * @author ltt
 */
public class AmbiguousClassSpecException extends Exception {
    /** silence the compiler warnings */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a AmbiguousClassSpecException with no message.
     */
    public AmbiguousClassSpecException() {
        super();
    } // AmbiguousClassSpecException

    /**
     * Constructs a AmbiguousClassSpecException with the given message.
     *
     * @param  s  Message.
     */
    public AmbiguousClassSpecException(String s) {
        super(s);
    } // AmbiguousClassSpecException
} // AmbiguousClassSpecException
