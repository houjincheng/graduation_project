package anyviewj.console;


import java.security.*;
import java.io.FileDescriptor;
import java.net.InetAddress;

//import sun.security.util.SecurityConstants;
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
public class QuickSecurityManager extends SecurityManager {
    public QuickSecurityManager() {
        super();
    }

    @Override
	public void checkPermission(Permission perm) {
        //java.security.AccessController.checkPermission(perm);
    }

    @Override
	public void checkPermission(Permission perm, Object context) {
        if (context instanceof AccessControlContext) {
            ((AccessControlContext)context).checkPermission(perm);
        } else {
            throw new SecurityException();
        }
    }

    @Override
	public void checkCreateClassLoader() {
        //checkPermission(SecurityConstants.CREATE_CLASSLOADER_PERMISSION);
    }
    @Override
	public void checkAccess(Thread t) {
        if (t == null) {
            throw new NullPointerException("thread can't be null");
        }
        /*if (t.getThreadGroup() == rootGroup) {
            checkPermission(SecurityConstants.MODIFY_THREAD_PERMISSION);
        } else {
            // just return
        }*/
    }

    @Override
	public void checkAccess(ThreadGroup g) {
        if (g == null) {
            throw new NullPointerException("thread group can't be null");
        }
        /*if (g == rootGroup) {
            checkPermission(SecurityConstants.MODIFY_THREADGROUP_PERMISSION);
        } else {
            // just return
        }*/
    }

    @Override
	public void checkExit(int status) {
        //checkPermission(new RuntimePermission("exitVM"));
    }

    @Override
	public void checkExec(String cmd) {
        /*File f = new File(cmd);
        if (f.isAbsolute()) {
            checkPermission(new FilePermission(cmd,
                SecurityConstants.FILE_EXECUTE_ACTION));
        } else {
            checkPermission(new FilePermission("<<ALL FILES>>",
                SecurityConstants.FILE_EXECUTE_ACTION));
        }*/
    }

    @Override
	public void checkLink(String lib) {
        if (lib == null) {
            throw new NullPointerException("library can't be null");
        }
        //checkPermission(new RuntimePermission("loadLibrary."+lib));
    }

    /**
     * Throws a <code>SecurityException</code> if the
     * calling thread is not allowed to read from the specified file
     * descriptor.
     * <p>
     * This method calls <code>checkPermission</code> with the
     * <code>RuntimePermission("readFileDescriptor")</code>
     * permission.
     * <p>
     * If you override this method, then you should make a call to
     * <code>super.checkRead</code>
     * at the point the overridden method would normally throw an
     * exception.
     *
     * @param      fd   the system-dependent file descriptor.
     * @exception  SecurityException  if the calling thread does not have
     *             permission to access the specified file descriptor.
     * @exception  NullPointerException if the file descriptor argument is
     *             <code>null</code>.
     * @see        java.io.FileDescriptor
     * @see        #checkPermission(java.security.Permission) checkPermission
     */
    @Override
	public void checkRead(FileDescriptor fd) {
        if (fd == null) {
            throw new NullPointerException("file descriptor can't be null");
        }
        //checkPermission(new RuntimePermission("readFileDescriptor"));
    }

    @Override
	public void checkRead(String file) {
        //checkPermission(new FilePermission(file, SecurityConstants.FILE_READ_ACTION));
    }

    @Override
	public void checkRead(String file, Object context) {
        //checkPermission(new FilePermission(file, SecurityConstants.FILE_READ_ACTION), context);
    }

    @Override
	public void checkWrite(FileDescriptor fd) {
        if (fd == null) {
            throw new NullPointerException("file descriptor can't be null");
        }
       // checkPermission(new RuntimePermission("writeFileDescriptor"));

    }

    @Override
	public void checkWrite(String file) {
        //checkPermission(new FilePermission(file,  SecurityConstants.FILE_WRITE_ACTION));
    }

    @Override
	public void checkDelete(String file) {
        //checkPermission(new FilePermission(file, SecurityConstants.FILE_DELETE_ACTION));
    }

    @Override
	public void checkConnect(String host, int port) {
        if (host == null) {
            throw new NullPointerException("host can't be null");
        }
        /*if (!host.startsWith("[") && host.indexOf(':') != -1) {
            host = "[" + host + "]";
        }
        if (port == -1) {
            checkPermission(new SocketPermission(host,SecurityConstants.SOCKET_RESOLVE_ACTION));
        } else {
            checkPermission(new SocketPermission(host+":"+port,SecurityConstants.SOCKET_CONNECT_ACTION));
        }*/
    }

    @Override
	public void checkConnect(String host, int port, Object context) {
        if (host == null) {
            throw new NullPointerException("host can't be null");
        }
        /*if (!host.startsWith("[") && host.indexOf(':') != -1) {
            host = "[" + host + "]";
        }
        if (port == -1)
            checkPermission(new SocketPermission(host,
                SecurityConstants.SOCKET_RESOLVE_ACTION),
                context);
        else
            checkPermission(new SocketPermission(host+":"+port,
                SecurityConstants.SOCKET_CONNECT_ACTION),
                context);*/
    }

    @Override
	public void checkListen(int port) {
        if (port == 0) {
            //checkPermission(SecurityConstants.LOCAL_LISTEN_PERMISSION);
        } else {
            //checkPermission(new SocketPermission("localhost:"+port,SecurityConstants.SOCKET_LISTEN_ACTION));
        }
    }

    @Override
	public void checkAccept(String host, int port) {
        if (host == null) {
            throw new NullPointerException("host can't be null");
        }
        /*if (!host.startsWith("[") && host.indexOf(':') != -1) {
            host = "[" + host + "]";
        }
        checkPermission(new SocketPermission(host+":"+port,
            SecurityConstants.SOCKET_ACCEPT_ACTION));*/
    }

    @Override
	public void checkMulticast(InetAddress maddr) {
        /*String host = maddr.getHostAddress();
        if (!host.startsWith("[") && host.indexOf(':') != -1) {
            host = "[" + host + "]";
        }
        checkPermission(new SocketPermission(host,
            SecurityConstants.SOCKET_CONNECT_ACCEPT_ACTION));*/
    }

    @Override
	public void checkPropertiesAccess() {
        //checkPermission(new PropertyPermission("*", SecurityConstants.PROPERTY_RW_ACTION));
    }

    @Override
	public void checkPropertyAccess(String key) {
        //checkPermission(new PropertyPermission(key,SecurityConstants.PROPERTY_READ_ACTION));
    }

    @Override
	public boolean checkTopLevelWindow(Object window) {
        if (window == null) {
            throw new NullPointerException("window can't be null");
        }
        /*try {
            checkPermission(SecurityConstants.TOPLEVEL_WINDOW_PERMISSION);
            return true;
        } catch (SecurityException se) {
            // just return false
        }
        return false;*/
        return true;
    }

    @Override
	public void checkPrintJobAccess() {
        //checkPermission(new RuntimePermission("queuePrintJob"));
    }

    @Override
	public void checkSystemClipboardAccess() {
        //checkPermission(SecurityConstants.ACCESS_CLIPBOARD_PERMISSION);
    }

    @Override
	public void checkAwtEventQueueAccess() {
        //checkPermission(SecurityConstants.CHECK_AWT_EVENTQUEUE_PERMISSION);
    }

    @Override
	public void checkPackageAccess(String pkg) {
        if (pkg == null) {
            throw new NullPointerException("package name can't be null");
        }
    }

    @Override
	public void checkPackageDefinition(String pkg) {
        if (pkg == null) {
            throw new NullPointerException("package name can't be null");
        }
    }

    @Override
	public void checkSetFactory() {
        //checkPermission(new RuntimePermission("setFactory"));
    }

    @Override
	public void checkMemberAccess(Class<?> clazz, int which) {
        if (clazz == null) {
            throw new NullPointerException("class can't be null");
        }
    }

    /**
     * Determines whether the permission with the specified permission target
     * name should be granted or denied.
     *
     * <p> If the requested permission is allowed, this method returns
     * quietly. If denied, a SecurityException is raised.
     *
     * <p> This method creates a <code>SecurityPermission</code> object for
     * the given permission target name and calls <code>checkPermission</code>
     * with it.
     *
     * <p> See the documentation for
     * <code>{@link java.security.SecurityPermission}</code> for
     * a list of possible permission target names.
     *
     * <p> If you override this method, then you should make a call to
     * <code>super.checkSecurityAccess</code>
     * at the point the overridden method would normally throw an
     * exception.
     *
     * @param target the target name of the <code>SecurityPermission</code>.
     *
     * @exception SecurityException if the calling thread does not have
     * permission for the requested access.
     * @exception NullPointerException if <code>target</code> is null.
     * @exception IllegalArgumentException if <code>target</code> is empty.
     *
     * @since   JDK1.1
     * @see        #checkPermission(java.security.Permission) checkPermission
     */
    @Override
	public void checkSecurityAccess(String target) {
        //checkPermission(new SecurityPermission(target));
    }
}
