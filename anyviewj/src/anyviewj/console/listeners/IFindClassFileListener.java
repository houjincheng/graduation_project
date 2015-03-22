package anyviewj.console.listeners;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007 gdut 1627</p>
 *
 * <p>Company: gdut 1627</p>
 *
 * @author cyf
 * @version 1.0
 */
public interface IFindClassFileListener {
    byte[] findClassFile(String fileName);
    String findLibraryPath(String libname);
}
