package anyviewj.interfaces.ui.panel;

import anyviewj.console.ConsoleCenter;
import anyviewj.debug.session.Session;
import anyviewj.interfaces.ui.Project;

/**
 * <p>Title: ��Ŀ������</p>
 *
 * <p>Description: ������Ŀ���</p>
 *
 * <p>Copyright: Copyright (c) 2007 gdut 1627</p>
 *
 * <p>Company: gdut 1627</p>
 *
 * @author cyf
 * @version 1.0
 */
public abstract class ProjectManager {

    //ȫ�ֿ�����������
    public final ConsoleCenter center;

    public ProjectManager(ConsoleCenter aCenter) {
        this.center = aCenter;
    }

    public abstract Project openProject(String prjName, Session session);
    public abstract Project OpenProject(String prjName);
    public abstract Project CloseProject();
    public abstract void StoreProject(Project prj);

    //��ȡ�Զ��幫�����
    public byte[] loadCommonLibrary(String name){
        return null;
    }

    //
    public Project getCurProject(){
        return null;
    }
}
