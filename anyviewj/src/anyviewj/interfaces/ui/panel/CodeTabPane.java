package anyviewj.interfaces.ui.panel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import anyviewj.console.ConsoleCenter;
import anyviewj.debug.breakpoint.Breakpoint;
import anyviewj.debug.breakpoint.ResolvableBreakpoint;
import anyviewj.debug.breakpoint.SourceNameBreakpoint;
import anyviewj.debug.manager.BreakpointManager;
import anyviewj.interfaces.ui.JavaProject;
import anyviewj.interfaces.ui.layout.CardLayout;
import anyviewj.interfaces.ui.manager.JavaProjectManager;

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
public class CodeTabPane extends JPanel {
    //文件选项卡面板
    public final JTabbedPane tabPane;
    //中央面板
    private JPanel centerPane = new JPanel(new CardLayout());
    //
    public final CodeTabPane owner;
    //控制中心
    public final ConsoleCenter center;

    public CodeTabPane(ConsoleCenter center,CodeTabPane owner,int tabPlacement, int tabLayoutPolicy) {
        super(new BorderLayout());
        this.center = center;
        this.owner = owner;
        this.add(centerPane,BorderLayout.CENTER);
        tabPane = new JTabbedPane(tabPlacement,tabLayoutPolicy);
        tabPane.addChangeListener(new TabChangeHandler());
        tabPane.addMouseListener(new TabMouseHandler());
        switch(tabPlacement){
            case SwingConstants.LEFT:
                add(tabPane,BorderLayout.WEST);
                break;
            case SwingConstants.RIGHT:
                add(tabPane,BorderLayout.EAST);
                break;
            case SwingConstants.BOTTOM:
                add(tabPane,BorderLayout.SOUTH);
                break;
            case SwingConstants.TOP:
            default:
                add(tabPane,BorderLayout.NORTH);
        }
        
        
    }

    /**
     * 添加控件到tab中
     * @param title String
     * @param icon Icon
     * @param component Component
     * @param tip String
     */
    public void addTab(String title, Icon icon, Component component, String tip) {
        InnerTabComponent itc = new InnerTabComponent(component);
        tabPane.addTab(title,icon,itc,tip);
        centerPane.add(component);
        if(tabPane.getSelectedComponent()==itc){
            ((CardLayout)centerPane.getLayout()).show(centerPane,component);
        }
    }

    public void addTab(String title, Icon icon, Component component) {
        addTab(title,icon,component,"");
    }


    public void addTab(String title, Component component) {
        addTab(title,null,component,"");
    }

    private void checkIndex(int index) {
        if (index < 0 || index > tabPane.getTabCount()) {
            throw new IndexOutOfBoundsException("Index: "+index+", Tab count: "+tabPane.getTabCount());
        }
    }
    /**
     * 删除控件
     * @param index int
     */
    public void removeTabAt(int index) {
        checkIndex(index);
        InnerTabComponent itc = (InnerTabComponent)tabPane.getComponentAt(index);
        tabPane.removeTabAt(index);
        centerPane.remove(itc.comp);
        itc = (InnerTabComponent)tabPane.getSelectedComponent();
        if(itc!=null) ((CardLayout)centerPane.getLayout()).show(centerPane,itc.comp);
    }

    public void removeAllTabs() {
        int tabCount = getTabCount();
        while (tabCount-- > 0) {
            removeTabAt(tabCount);
        }
    }
    
    public int getSelectedPrjIndex() {
    	int index = getSelectedIndex();
    	int num = index;
    	if (((JavaProjectManager)ConsoleCenter.projectManager).getOpenPrjCount() > 1) {
    		FilePane fp = (FilePane)getSelectedComponent();
    		String prjName = fp.source.getName();
    		for (int i = 0; i < index; ++i) {
    			fp = (FilePane)getTabComponentAt(i);
    			if(fp==null||prjName==null||fp.source==null)       //这里只做临时处理
    			{	
    				continue;
    			}
    				if (fp.source.getName().compareTo(prjName) != 0) {
    				--num;
    			}
    		}
    	}
    	return num;
    }

    public int getSelectedIndex() {
        return tabPane.getSelectedIndex();
    }

    public Component getSelectedComponent() {
        InnerTabComponent itc = (InnerTabComponent)tabPane.getSelectedComponent();
        if(itc!=null){
            return itc.comp;
        }
        return null;
    }

    public int getTabCount() {
        return tabPane.getTabCount();
    }

    public int getTabComponentIndex(Component c){
        InnerTabComponent itc;
        for(int i=tabPane.getTabCount()-1;i>=0;i--){
            itc = (InnerTabComponent)tabPane.getComponentAt(i);
            if(itc.comp == c) return i;
        }
        return -1;
    }

    public Component getTabComponentAt(int index){
        checkIndex(index);
        InnerTabComponent itc = (InnerTabComponent)tabPane.getComponentAt(index);
        return itc.comp;
    }

    public void setSelectedIndex(int index) {
    	
//        if (index >= 0 && index < tabPane.getSelectedIndex()) {
    	if (index >= 0 && index < getTabCount()) {
            checkIndex(index);
            tabPane.setSelectedIndex(index);
//            InnerTabComponent itc = (InnerTabComponent)tabPane.getComponentAt(index);
            ((CardLayout)centerPane.getLayout()).show(centerPane,((InnerTabComponent)tabPane.getComponentAt(index)).comp);
            
   //         updateTabInfo();
        }
    }

    public void setSelectedComponent(final Component c) {
    	SwingUtilities.invokeLater(new Runnable(){
    		@Override
    		public void run() {
        InnerTabComponent itc = new InnerTabComponent();
        for(int i=tabPane.getTabCount()-1;i>=0;i--){       
//            itc = (InnerTabComponent)tabPane.getComponentAt(i);

            if(((InnerTabComponent)tabPane.getComponentAt(i)).comp==c){
//            	 System.out.println("super    :  !!!!!!!!!!!!  "+tabPane.getTabCount()+"  "+i);
            	tabPane.setSelectedIndex(i);
                break;
            }
         }
		
			
		}
    	
      });
    }

    private void showTab(Component c){
        ((CardLayout)centerPane.getLayout()).show(centerPane,c);
    }

    /////////////////////////////////////////////////////////////////////////////
    private class TabChangeHandler implements ChangeListener{
        @Override
		public void stateChanged(ChangeEvent e){
            InnerTabComponent itc = (InnerTabComponent)tabPane.getSelectedComponent();
            if(itc!=null) showTab(itc.comp);
        }
    }

    /////////////////////////////////////////////////////////////////////////////
    public interface ClickIconListener extends EventListener{
        void clickIcon(ClickIconEvent e);
    }

    public class ClickIconEvent extends EventObject {
        public final int index;
        public ClickIconEvent(Object source,int index) {
            super(source);
            this.index = index;
        }
    }

    public void addClickIconListener(ClickIconListener l) {
        listenerList.add(ClickIconListener.class, l);
    }

    public void removeClickIconListener(ClickIconListener l) {
        listenerList.remove(ClickIconListener.class, l);
    }

    protected void fireClickIcon(int index) {
        Object[] listeners = listenerList.getListenerList();
        ClickIconEvent event = null;
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==ClickIconListener.class) {
                // Lazily create the event:
                if (event == null) event = new ClickIconEvent(this,index);
                ((ClickIconListener)listeners[i+1]).clickIcon(event);
            }
        }
    }

    public void updateTabInfo() {
    	FilePane fp = (FilePane)getSelectedComponent();
    	if (fp == null) {
    		JavaProject prj = (JavaProject)((JavaProjectManager)ConsoleCenter.projectManager).getCurProject();
    		if (prj == null) {
    			ConsoleCenter.mainFrame.setTitle("Anyview for Java");
    		}
    		else {
    			String prjName = prj.shortName.substring(0, prj.shortName.lastIndexOf('.'));
    			ConsoleCenter.mainFrame.setTitle("Anyview for Java - " + prjName);
    		}
    		return ;
    	}
    	String name = fp.source.getFilePath();
    	int index = name.indexOf("\\src");
    	int idx = name.lastIndexOf('\\', index - 1);
    	name = name.substring(idx + 1, name.length());
    	name = name.replace('\\', '/');
    	ConsoleCenter.mainFrame.setTitle("Anyview for Java - " + name);
    	JavaProject prj = (JavaProject)((JavaProjectManager)ConsoleCenter.projectManager).findProject(fp.source.getName());
    	if (prj != null) {
    		prj.setCurFileIndex(getSelectedIndex());
    		prj.setPrjFileIndex(getSelectedPrjIndex());
    	}
    }
    
    public void updateTabInfo(BreakpointManager bpm) {
    	Breakpoint bp = null;
    	SourceNameBreakpoint sbp = null;
    	JavaProject prj = (JavaProject)((JavaProjectManager)ConsoleCenter.projectManager).getCurProject();
    	List xl = ConsoleCenter.mainFrame.rightPartPane.codePane.fpaneList;
    	
    	if(prj==null)
    		return;
    	
    	Set key = bpm.breakpointsTable.keySet();
    	for(Object s: key)
    	{
    		bp = (Breakpoint)bpm.breakpointsTable.get(s);
    	 if (bp instanceof ResolvableBreakpoint)
    	 {
    		 sbp = (SourceNameBreakpoint)bp;
     	     break;
    	 }
    	}
    	
    	if(sbp==null)
    		return;

    	String name = sbp.srcname;
    	int index = name.indexOf("\\src");
    	int idx = name.lastIndexOf('\\', index - 1);
    	name = name.substring(idx + 1, name.length());
    	name = name.replace('\\', '/');
    	ConsoleCenter.mainFrame.setTitle("Anyview for Java - " + name);
    	//prj = (JavaProject)((JavaProjectManager)center.projectManager).findProject(name);
    	   for(int i = 0 ;i<xl.size();i++)
           {
              String sname = ((FilePane)xl.get(i)).source.editorPanel.owner.sourceSrc.getName();
              if(sname.equals(sbp.srcname))
              {
            	  prj.setCurFileIndex((i));
	              return;
              }
           }
//    	if (prj != null) {
//    		((JavaProject)prj).setCurFileIndex(getSelectedIndex());
//    		((JavaProject)prj).setPrjFileIndex(getSelectedPrjIndex());
//    	}
    }
    
    public void notSave(SourcePane sp){
    	tabPane.setTitleAt(getSelectedIndex(),"* "+sp.editorPanel.getName());
    }
    
    private class TabMouseHandler extends MouseAdapter{
        @Override
		public void mouseClicked(MouseEvent e){
            int x = e.getX();
            int y = e.getY();
            int index = tabPane.indexAtLocation(x, y);
            if ((index >= 0) && (index < tabPane.getTabCount()) && tabPane.getIconAt(index)!=null){           	
                Rectangle rect = tabPane.getBoundsAt(index);
                rect.x += 6;
                rect.y += 2;
                if(index != tabPane.getSelectedIndex()){
                    x -= 3;
                    y -= 2;
                }  
                updateTabInfo();
                if ((x >= rect.x) && (x <= rect.x + 16) &&
                    (y >= rect.y) && (y <= rect.y + 16)) {
                    fireClickIcon(index);
                }
                
            }
        }
    }

    private static final Dimension defaultDimension = new Dimension(1,1);
    public class InnerTabComponent extends Component{
        private Component comp;
        
        public InnerTabComponent(){
        	
        }
        
        public InnerTabComponent(Component c){
            comp = c;
        }
        @Override
		public Dimension getPreferredSize(){
            return defaultDimension;
        }
    }

}
