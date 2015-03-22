package anyviewj.debug.source;

import java.io.InputStream;

/**
 * SourceSource provides a means of reading program code through an
 * input stream. Related information is provided through accessor
 * methods to facilitate displaying the program code.
 *
 * @author  ltt
 */
public interface SourceSource {

    /**
     * Attempts to ensure that the referenced source actually exists.
     *
     * @return  true if source exists, false if not found.
     */
    boolean exists();

    /**
     * Get the input stream for reading the source object.
     *
     * @return  input stream to the source object.
     */
    InputStream getInputStream();

    /**
     * Get the full name of the source object. This may be the path and
     * file name of a file (may or may not be a canonical path), the
     * fully-qualified name of a class, or a zip entry and file name.
     * This may be the same as the short name.
     *
     * @return  long name of source object.
     */
    String getLongName();

    /**
     * Returns just the name of the source object, not including any
     * prefix such as a path or package name.
     *
     * @return  name of source object.
     */
    String getName();

    /**
     * Returns the name of the package for the class that this source
     * object represents, if available.
     *
     * @return  package name, or null if not applicable.
     */
    String getPackage();

    /**
     * Returns the complete path to the source file, if the source
     * object is stored in a file that is not an archive.
     *
     * @return  file path, or null if not applicable.
     */
    String getPath();

    /**
     * Indicates if this source object represents byte code, as
     * opposed to source code of a high-level language.
     *
     * @return  true if byte code, false otherwise.
     */
    boolean isByteCode();

    /**
     * Sets the name of the package for the class that this source
     * object represents.
     *
     * @param  pkg  package name.
     */
    void setPackage(String pkg);
} // SourceSource
