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
    //��Ŀ��
    public final String projectPath;
    public final String shortName;

    public Project(String prjPath,String shortName) {
        this.projectPath = prjPath;
        this.shortName = shortName;
    }

    /**
     * ��ȡ�û�������
     * @param name String
     * @return byte[]
     */
    public abstract byte[] loadUserClass(String name);

    /**
     * ��ȡԴ�ļ�
     * @param name String
     * @return FileItem
     */
    //public abstract FileItem loadUserFileItem(String name);

}
