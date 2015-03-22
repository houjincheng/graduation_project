package anyviewj.debug.breakpoint;

/**
 * InvalidArgumentTypeException is thrown when the specified argument
 * type was invalid or unrecognized.
 *
 * @author  ltt
 */
public class InvalidArgumentTypeException extends Exception {
    /** silence the compiler warnings */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a InvalidArgumentTypeException with no message.
     */
    public InvalidArgumentTypeException() {
        super();
    } // InvalidArgumentTypeException

    /**
     * Constructs a InvalidArgumentTypeException with the given message.
     *
     * @param  s  Message.
     */
    public InvalidArgumentTypeException(String s) {
        super(s);
    } // InvalidArgumentTypeException
} // InvalidArgumentTypeException
