package anyviewj.debug.breakpoint;

import com.sun.jdi.ReferenceType;

/**
 * Interface LocatableBreakpoint is implemented by those breakpoints
 * have a location associated with them. This includes method and line
 * breakpoints, but not exception or watch breakpoints.
 *
 * @author  ltt
 */
public interface LocatableBreakpoint {

    /**
     * Return the name of the class that this breakpoint is located in.
     * This could be a fully-qualified class name or a wild-carded name
     * pattern containing a single asterisk (e.g. "*.cname").
     *
     * @return  Class name if known, null if not.
     */
    String getClassName();

    /**
     * Retrieve the line number associated with this breakpoint. Not all
     * breakpoints will have a particular line associated with them
     * (such as method breakpoints). In such cases, this method will
     * return -1.
     *
     * @return  line number of breakpoint, if applicable; -1 if not.
     */
    int getLineNumber();

    /**
     * Returns the name of the package for the class this breakpoint is
     * set within. May not be known, in which case null is returned.
     *
     * @return  package name, or null if unknown.
     */
    String getPackageName();

    /**
     * Locatable breakpoints are expected to be set in classes, hence
     * they should have a <code>ReferenceType</code>. However, the
     * caller should check for a return value of null.
     *
     * @return  reference type, or null if not set.
     */
    ReferenceType getReferenceType();

    /**
     * Returns the name of the source file for the class this breakpoint
     * is set within. May not be known, in which case null is returned.
     *
     * @return  source name, or null if unknown.
     */
    String getSourceName();

    /**
     * Compares the fully-qualified class name with the specification of
     * this breakpoint. If the two names match, taking wildcards into
     * consideration, then this method returns true.
     *
     * @param  name  fully-qualified class name to compare to.
     * @return  true if names match, false otherwise.
     */
    boolean matchesClassName(String name);

    /**
     * Compares the name of the package and the name of the source file
     * with those given as arguments. Nulls do not constitute a match.
     *
     * @param  pkg  name of package (with dot separators).
     * @param  src  name of source file.
     * @return  true if matches this breakpoint's information.
     */
    boolean matchesSource(String pkg, String src);
} // LocatableBreakpoint
