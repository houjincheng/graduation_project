package anyviewj.interfaces.ui.panel;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import anyviewj.console.ConsoleCenter;
import anyviewj.interfaces.resource.FormResource;
import anyviewj.interfaces.resource.IconResource;

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
public class ProjectPane extends JPanel {
	//控制中心
    public final ConsoleCenter center;
    //项目管理面板--主选项卡面板
    public final JTabbedPane mainTab = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
    //所有项目面板
    public final ProjectGroupPane allProjectPane = new ProjectGroupPane();
    //当前项目面板
    public final CurProjectPane curProjectPane;
    
    public ProjectPane(ConsoleCenter center) {
        super(new BorderLayout());
        this.center = center;
        curProjectPane = new CurProjectPane(center);
        FormResource fr = center.resourceManager.getFormResource();
        IconResource ir = center.resourceManager.getIconResource();

        mainTab.addTab(fr.getNames(FormResource.PROJECT_INFOS_STRUCT),
                                    null,//ir.getIcon(ir.TABPANE_DEPART),
                                    curProjectPane,
                                    fr.getTips(FormResource.PROJECT_INFOS_STRUCT));
        mainTab.addTab(fr.getNames(FormResource.PROJECT_INFOS_DOCUMENT),
                       ir.getIcon(IconResource.TABPANE_DEPART),
                       new JPanel(),
                       fr.getTips(FormResource.PROJECT_INFOS_DOCUMENT));

        mainTab.setSelectedIndex(0);
        add(mainTab,BorderLayout.CENTER);
     
        
    }
    
    
}
