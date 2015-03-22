package anyviewj.interfaces.ui;
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
public abstract class Project {
    //项目名
    public final String projectPath;
    public final String shortName;

    public Project(String prjPath,String shortName) {
        this.projectPath = prjPath;
        this.shortName = shortName;
    }

    /**
     * 读取用户类数据
     * @param name String
     * @return byte[]
     */
    public abstract byte[] loadUserClass(String name);

    /**
     * 读取源文件
     * @param name String
     * @return FileItem
     */
    //public abstract FileItem loadUserFileItem(String name);

}
