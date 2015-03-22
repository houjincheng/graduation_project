package anyviewj.interfaces.ui.panel;

import anyviewj.console.ConsoleCenter;
import anyviewj.debug.session.Session;
import anyviewj.interfaces.ui.Project;

/**
 * <p>Title: 项目管理器</p>
 *
 * <p>Description: 管理项目相关</p>
 *
 * <p>Copyright: Copyright (c) 2007 gdut 1627</p>
 *
 * <p>Company: gdut 1627</p>
 *
 * @author cyf
 * @version 1.0
 */
public abstract class ProjectManager {

    //全局控制中心引用
    public final ConsoleCenter center;

    public ProjectManager(ConsoleCenter aCenter) {
        this.center = aCenter;
    }

    public abstract Project openProject(String prjName, Session session);
    public abstract Project OpenProject(String prjName);
    public abstract Project CloseProject();
    public abstract void StoreProject(Project prj);

    //读取自定义公共类库
    public byte[] loadCommonLibrary(String name){
        return null;
    }

    //
    public Project getCurProject(){
        return null;
    }
}
