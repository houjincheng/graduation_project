package anyviewj.interfaces.ui.panel;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import anyviewj.callstack.cell.DebugInnerFrame;
import anyviewj.callstack.cell.StackFrameInnerFrame;
import anyviewj.console.ConsoleCenter;
import anyviewj.interfaces.resource.FormResource;
import anyviewj.interfaces.resource.IconResource;

public class DebugPane  extends JPanel{
	
	public final ConsoleCenter center;
	public final JDesktopPane desktop = new JDesktopPane();
    //子窗口
    public final StackFrameInnerFrame stackFrameInnerFrame;
	public DataStructPanel dataStructPanel = new DataStructPanel();
    //控制中心
	public DebugPane(ConsoleCenter center) {
		// TODO Auto-generated constructor stub
		super(new BorderLayout());
		this.center = center;
		
		FormResource fr = center.resourceManager.getFormResource();
        IconResource ir = center.resourceManager.getIconResource();

        stackFrameInnerFrame = new StackFrameInnerFrame(fr.getNames(FormResource.DEBUG_MANAGER_STACKFRAME),center);
		//add(dataStructPanel.getUI(), BorderLayout.CENTER);
        
        DebugInnerFrame jif1 = new DebugInnerFrame(fr.getNames(FormResource.DEBUG_MANAGER_DATASTRUCT),center){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void clear() {
				// TODO Auto-generated method stub
				
			}};

		ConsoleCenter.addViewListeners(dataStructPanel);
		jif1.addScrollComponent(dataStructPanel.getUI());

        //初始化桌面面板
        desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
        desktop.setBackground(Color.GRAY);
        add(desktop,BorderLayout.CENTER);
        
        initSubFrames(stackFrameInnerFrame,0);
        initSubFrames(jif1, 1);
	}

    /**
     * 每次运行前都必须将以前的数据清空
     */
    public void clear(){

    }


    private void initSubFrames(DebugInnerFrame frame,int pos){
        frame.setSize(236,210);
        desktop.add(frame);
        if(pos==0) frame.setLocation(0,0);
        else if(pos==1) frame.setLocation(236,0);
        else if(pos==2) frame.setLocation(0,210);
        else frame.setLocation(236,210);
    }

}
