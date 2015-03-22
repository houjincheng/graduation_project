package anyviewj.interfaces.ui.panel;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
 * @author f
 * @version 1.0
 */
public class LeftPartPane extends JPanel{
    //主选项卡面板
    public final JTabbedPane mainTab = new JTabbedPane(JTabbedPane.BOTTOM,JTabbedPane.SCROLL_TAB_LAYOUT);
    //项目管理器面板
    public final ProjectPane projectPane;
    //调试器面板
    public final DebugPane debugPane;

    private ConsoleCenter center;

    public LeftPartPane(ConsoleCenter center) {
        super(new BorderLayout());
        this.center = center;
        FormResource fr = center.resourceManager.getFormResource();
        IconResource ir = center.resourceManager.getIconResource();
        //初始化项目面板和调试面板
        projectPane = new ProjectPane(center);
        debugPane = new DebugPane(center);
        //主选项卡添加选项
        mainTab.addTab(fr.getNames(FormResource.PROJECT_INFOS),
                       null,//ir.getIcon(ir.TABPANE_DEPART)
                       projectPane);
        mainTab.addTab(fr.getNames(FormResource.DEBUG_MANAGER),
                       null,//ir.getIcon(ir.TABPANE_DEPART),
                       debugPane);
        //主面板添加主选项卡
        add(mainTab,BorderLayout.CENTER);

        //处理一些相应事件
        performActions();
    }

    private void performActions(){
        //恢复项目或调试状态下主分隔面板的分隔状态
        mainTab.addChangeListener(new MainTabChangeListener());
    }

    //恢复项目或调试状态下主分隔面板的分隔状态
    private class MainTabChangeListener implements ChangeListener{
        private int[] locations = new int[]{220,-310};
        private double[] weights = new double[]{0.0,1.0};
        private boolean[] adjustDividers = new boolean[]{false,false};//如果true的话,weights不能为0或1
        private int idx = 0;
        private int mainSplitPaneSize = 0;

        @Override
		public void stateChanged(ChangeEvent e){
            JSplitPane sp = ConsoleCenter.mainFrame.mainSplitPane;
            int width = sp.getWidth();
            int index = mainTab.getSelectedIndex();
            int location = locations[idx];
            if(location > 0){
                locations[idx] = sp.getDividerLocation();
            }else{
                locations[idx] = sp.getDividerLocation() - width;
            }
            location = locations[index];
            if(mainSplitPaneSize!=width){
                mainSplitPaneSize = width;
                if (adjustDividers[index]) {
                    location = (int) (width * weights[index]);
                }
            }
            if(location > 0){
                sp.setDividerLocation(location);
            }else{
                sp.setDividerLocation(width+location);
            }
            sp.setResizeWeight(weights[index]);
            idx = index;
        }
    }

//    public static void main(String[] args) {
//
//    }
}
