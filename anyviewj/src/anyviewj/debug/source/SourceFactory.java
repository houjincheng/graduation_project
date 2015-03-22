package anyviewj.debug.source;

import anyviewj.debug.manager.PathManager;

import com.sun.jdi.ReferenceType;
import java.io.File;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Class SourceFactory constructs SourceSource objects of the appropriate
 * type, based on the information that is given to its create methods.
 *
 * @author  ltt
 */
public class SourceFactory {
    /** The one instance of this class. */
    private static SourceFactory theInstance;

    static {
        theInstance = new SourceFactory();
    }

    /**
     * This class cannot be instantiated.
     */
    private SourceFactory() {
    } // SourceFactory

    /**
     * Constructs an instance of a SourceSource of the appropriate type
     * for the given arguments.
     *
     * @param  zfile  zip file.
     * @param  entry  zip entry within the zip file.
     * @return  source source object.
     */
    public SourceSource create(ZipFile zfile, ZipEntry entry) {
        SourceSource src = new ZipSource(zfile, entry);
        String entryName = entry.getName();
        // Convert the name to the local file system form.
        // Note that we assume the zip file is the root of the
        // source path for this entry.
        entryName = new File(entryName).getParent();
        // Convert the path to a package name.
        if (entryName != null) {
            entryName = entryName.replace(File.separatorChar, '.');
            src.setPackage(entryName);
        }
        return src;
    } // create

    /**
     * Constructs an instance of a SourceSource of the appropriate type
     * for the given arguments.
     *
     * @param  file  the file containing the source.
     * @param  mngr  path manager to determine package name; may be null.
     * @return  source source object.
     */
    public SourceSource create(File file, PathManager mngr) {
        return create(file.getParent(), file.getName(), mngr);
    } // create

    /**
     * Constructs an instance of a SourceSource of the appropriate type
     * for the given arguments.
     *
     * @param  path  path to file; may be null.
     * @param  name  name of file.
     * @param  mngr  path manager to determine package name; may be null.
     * @return  source source object.
     */
    public SourceSource create(String path, String name, PathManager mngr) {
        File file;
        if (path == null) {
            file = new File(name);
        } else {
            file = new File(path, name);
        }
        SourceSource src = new FileSource(file);
        int idx = name.lastIndexOf(File.separatorChar);
        if (idx > 0) {
            // If name has a file separator, then it must be based on
            // the classpath or sourcepath, which implies it is a
            // package name.
            String pkg = name.substring(0, idx);
            pkg = pkg.replace(File.separatorChar, '.');
            src.setPackage(pkg);
        } else if (mngr != null) {
            // Else, use the PathManager to see if it lies within the
            // classpath or sourcepath.
            String pkg = mngr.findPackageName(file.getParent());
            if (pkg != null) {
                src.setPackage(pkg);
            }
        }
        return src;
    } // create

    /**
     * Constructs an instance of a SourceSource of the appropriate type
     * for the given arguments.
     *
     * @param  clazz  reference type.
     * @return  source source object.
     */
    public SourceSource create(ReferenceType clazz) {
        SourceSource src = new ByteCodeSource(clazz);
        String name = clazz.name();
        // Strip off the last part and use the rest as a package name.
        int idx = name.lastIndexOf('.');
        if (idx > 0) {
            String pkg = name.substring(0, idx);
            src.setPackage(pkg);
        }
        return src;
    } // create

    /**
     * Returns the instance of this class.
     *
     * @return  a SourceFactory instance.
     */
    public static SourceFactory getInstance() {
        return theInstance;
    } // getInstance
} // SourceFactory
