package anyviewj.debug.source;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;

/**
 * Class FileSource is a concrete implementation of SourceSource in
 * which the source is backed by a <code>java.io.File</code> instance.
 *
 * @author  Nathan Fiedler
 */
public class FileSource extends AbstractSource {
    /** The file that contains the source object. */
    private File fileSource;

    /**
     * Construct a FileSource using the given path and filename.
     *
     * @param  name  path and filename.
     */
    public FileSource(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name must be non-null");
        }
        fileSource = new File(name);
    } // FileSource

    /**
     * Constructs a FileSource object for the given File.
     *
     * @param  src  file source.
     */
    public FileSource(File src) {
        if (src == null) {
            throw new IllegalArgumentException("src must be non-null");
        }
        fileSource = src;
    } // FileSource

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param  o  the reference object with which to compare.
     * @return  true if this object is the same as the obj argument;
     *          false otherwise.
     */
    @Override
	public boolean equals(Object o) {
        if (o instanceof FileSource) {
            FileSource ofs = (FileSource) o;
            return ofs.fileSource.equals(fileSource);
        }
        return false;
    } // equals

    /**
     * Attempts to ensure that the referenced source actually exists.
     *
     * @return  true if source exists, false if not found.
     */
    @Override
	public boolean exists() {
        return fileSource.exists();
    } // exists

    /**
     * Get the input stream for reading the source code. This may
     * fail, in which case it will return a <code>null</code>.
     *
     * @return  input stream to the source code, or null if error.
     */
    @Override
	public InputStream getInputStream() {
        try {
            return new FileInputStream(fileSource);
        } catch (FileNotFoundException fnfe) {
            return null;
        }
    } // getInputStream

    /**
     * Get the full name of the source object. This may be the path and
     * file name of a file (may or may not be a canonical path), the
     * fully-qualified name of a class, or a zip entry and file name.
     * This may be the same as the short name.
     *
     * @return  long name of source object.
     */
    @Override
	public String getLongName() {
        String name = getPath();
        return name == null ? getName() : name;
    } // getLongName

    /**
     * Returns just the name of the source file, not including the path
     * to the file, if any.
     *
     * @return  name of source.
     */
    @Override
	public String getName() {
        return fileSource.getName();
    } // getName

    /**
     * Returns the complete path to the source file, if the source
     * object is stored in a file that is not an archive.
     *
     * @return  file path, or null if not applicable.
     */
    @Override
	public String getPath() {
        try {
            return fileSource.getCanonicalPath();
        } catch (IOException ioe) {
            // In this case, the path would be useless anyway.
            return null;
        }
    } // getPath

    /**
     * Returns a hash code value for the object.
     *
     * @return  a hash code value for this object.
     */
    @Override
	public int hashCode() {
        // Hopefully the file is resolved to the canonical form
        // before the hashcode is calculated.
        return fileSource.hashCode();
    } // hashCode

    /**
     * Indicates if this source object represents byte code, as
     * opposed to source code of a high-level language.
     *
     * @return  true if byte code, false otherwise.
     */
    @Override
	public boolean isByteCode() {
        return false;
    } // isByteCode
} // FileSource
