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
    //��ѡ����
    public final JTabbedPane mainTab = new JTabbedPane(JTabbedPane.BOTTOM,JTabbedPane.SCROLL_TAB_LAYOUT);
    //��Ŀ���������
    public final ProjectPane projectPane;
    //���������
    public final DebugPane debugPane;

    private ConsoleCenter center;

    public LeftPartPane(ConsoleCenter center) {
        super(new BorderLayout());
        this.center = center;
        FormResource fr = center.resourceManager.getFormResource();
        IconResource ir = center.resourceManager.getIconResource();
        //��ʼ����Ŀ���͵������
        projectPane = new ProjectPane(center);
        debugPane = new DebugPane(center);
        //��ѡ����ѡ��
        mainTab.addTab(fr.getNames(FormResource.PROJECT_INFOS),
                       null,//ir.getIcon(ir.TABPANE_DEPART)
                       projectPane);
        mainTab.addTab(fr.getNames(FormResource.DEBUG_MANAGER),
                       null,//ir.getIcon(ir.TABPANE_DEPART),
                       debugPane);
        //����������ѡ�
        add(mainTab,BorderLayout.CENTER);

        //����һЩ��Ӧ�¼�
        performActions();
    }

    private void performActions(){
        //�ָ���Ŀ�����״̬�����ָ����ķָ�״̬
        mainTab.addChangeListener(new MainTabChangeListener());
    }

    //�ָ���Ŀ�����״̬�����ָ����ķָ�״̬
    private class MainTabChangeListener implements ChangeListener{
        private int[] locations = new int[]{220,-310};
        private double[] weights = new double[]{0.0,1.0};
        private boolean[] adjustDividers = new boolean[]{false,false};//���true�Ļ�,weights����Ϊ0��1
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
