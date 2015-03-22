package anyviewj.interfaces.ui.panel;


//import anyviewj.common.io.*;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import java.awt.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JSplitPane;
import java.security.*;
import anyviewj.console.ConsoleCenter;
import anyviewj.debug.session.Session;
import anyviewj.interfaces.actions.CommandManager;
import anyviewj.interfaces.ui.SubMenus;
import anyviewj.console.QuickSecurityManager;


/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: gdut 1627</p>
 *
 * @author cyf
 * @version 1.0
 */
public class MainFrame extends JFrame {

    //全局控制中心引用
    public final ConsoleCenter center = new ConsoleCenter(this);
    //各控件
    public final JMenuBar mainMenuBar = new JMenuBar();//主菜单
    public final JToolBar mainToolBar = new JToolBar();//主工具栏
    public final JLabel statusBar = new JLabel();//主状态栏
    public final ToolButtons toolButtons = new ToolButtons(mainToolBar);//主工具栏按钮集合
    public final SubMenus subMenus = new SubMenus(mainMenuBar);//主菜单菜单集合
    public final JSplitPane mainSplitPane = new JSplitPane();//主分隔面板
    public final LeftPartPane leftPartPane = new LeftPartPane(center);//左面板
    public final RightPartPane rightPartPane = new RightPartPane(center);//右面板

    public MainFrame() {
        try {
            //setDefaultCloseOperation(EXIT_ON_CLOSE);
            dowithClosing();
            installSecurityManager();
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Component initialization.
     *
     * @throws java.lang.Exception
     */
    private void jbInit() throws Exception {
        //主窗体
        setSize(new Dimension(800, 600));
        setTitle("Anyview for Java");
        //ImageIcon mainIcon = new ImageIcon(anyviewj.interfaces.ui.MainFrame.class.getResource("AV1.gif"));
        //this.setIconImage(mainIcon.getImage());
        //主面板
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        //状态栏
        /*Border border_statusbar = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, SystemColor.control, new Color(165, 163, 151),
            SystemColor.control);
        statusBar.setBorder(border_statusbar);*/
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        statusBar.setDisplayedMnemonic('0');
        statusBar.setText(" ");
        contentPane.add(statusBar, BorderLayout.SOUTH);

        //初始化主分隔面板
        mainSplitPane.setBorder(null);
        mainSplitPane.setToolTipText("");
        mainSplitPane.setBottomComponent(null);
        mainSplitPane.setTopComponent(null);
        mainSplitPane.add(leftPartPane,JSplitPane.LEFT);//添加左面板
        mainSplitPane.add(rightPartPane,JSplitPane.RIGHT);//添加右面板
        mainSplitPane.setDividerLocation(220);
        mainSplitPane.setDividerSize(4);
        contentPane.add(mainSplitPane, java.awt.BorderLayout.CENTER);

        CommandManager cm = ConsoleCenter.commandManager;
        
        //工具栏初始化
        toolButtons.initToolButtons(cm);
        mainToolBar.setBorder(BorderFactory.createEtchedBorder(Color.white,new Color(165, 163, 151)));
        contentPane.add(mainToolBar, java.awt.BorderLayout.NORTH);

        //菜单初始化
        setJMenuBar(mainMenuBar);
        mainMenuBar.setBorder(BorderFactory.createEmptyBorder());
        subMenus.initSubMenus(cm);

        registerSystemIO();
    }

    private void registerSystemIO(){
        //SystemOutput output = new SystemOutput(rightPartPane.groupPane.output,false);
        //java.io.PrintStream out = new PrintStream(new BufferedOutputStream(output),true);
        //java.io.PrintStream out = new PrintStream(output,true);
        //System.setOut(out);
        //System.setErr(out);
    }

    private void dowithClosing(){
        addWindowListener(new java.awt.event.WindowAdapter(){
            @Override
			public void windowClosing(WindowEvent e){
            	System.out.println("WindowClosing");
                permitExit = true;                
                endSession(ConsoleCenter.getCurrentSession());
                System.exit(0);
            }
        });
    }

    
    public static void endSession(Session session) {
        endSession(session, true);
    } // endSession
    
    public static void endSession(Session session, boolean allowExit) {
        //UIAdapter uiAdapter = session.getUIAdapter();
        //uiAdapter.saveSettings();
        // Must deactivate session before destroying interface.
        if (session!=null && session.isActive()) {
            session.deactivate(false, session);
            session.close(session);
        }
        //uiAdapter.destroyInterface();        
        //openSessions.remove(session);
        //if (allowExit && openSessions.size() == 0) {
            // No more open sessions, notify the adapter so it
            // takes the appropriate action.
        //    uiAdapter.exit();
        //}
    } // endSession
    private volatile boolean permitExit = false;

    private class MySecurityManager extends QuickSecurityManager{
        @Override
		public void checkExit(int status) {
            if(!permitExit) throw new SecurityException();
        }
        @Override
		public void checkPermission(Permission perm){
            if(perm instanceof RuntimePermission && perm.getName().equals("setSecurityManager"))
                throw new SecurityException();
        }
    }

    private void installSecurityManager(){
        System.setSecurityManager(new MySecurityManager());
    }
    
	public void setPermitExit(boolean permitExit) {
		this.permitExit = permitExit;
	}


}
