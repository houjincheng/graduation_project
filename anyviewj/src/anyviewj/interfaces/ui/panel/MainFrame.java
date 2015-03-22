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

    //ȫ�ֿ�����������
    public final ConsoleCenter center = new ConsoleCenter(this);
    //���ؼ�
    public final JMenuBar mainMenuBar = new JMenuBar();//���˵�
    public final JToolBar mainToolBar = new JToolBar();//��������
    public final JLabel statusBar = new JLabel();//��״̬��
    public final ToolButtons toolButtons = new ToolButtons(mainToolBar);//����������ť����
    public final SubMenus subMenus = new SubMenus(mainMenuBar);//���˵��˵�����
    public final JSplitPane mainSplitPane = new JSplitPane();//���ָ����
    public final LeftPartPane leftPartPane = new LeftPartPane(center);//�����
    public final RightPartPane rightPartPane = new RightPartPane(center);//�����

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
        //������
        setSize(new Dimension(800, 600));
        setTitle("Anyview for Java");
        //ImageIcon mainIcon = new ImageIcon(anyviewj.interfaces.ui.MainFrame.class.getResource("AV1.gif"));
        //this.setIconImage(mainIcon.getImage());
        //�����
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        //״̬��
        /*Border border_statusbar = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, SystemColor.control, new Color(165, 163, 151),
            SystemColor.control);
        statusBar.setBorder(border_statusbar);*/
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        statusBar.setDisplayedMnemonic('0');
        statusBar.setText(" ");
        contentPane.add(statusBar, BorderLayout.SOUTH);

        //��ʼ�����ָ����
        mainSplitPane.setBorder(null);
        mainSplitPane.setToolTipText("");
        mainSplitPane.setBottomComponent(null);
        mainSplitPane.setTopComponent(null);
        mainSplitPane.add(leftPartPane,JSplitPane.LEFT);//��������
        mainSplitPane.add(rightPartPane,JSplitPane.RIGHT);//��������
        mainSplitPane.setDividerLocation(220);
        mainSplitPane.setDividerSize(4);
        contentPane.add(mainSplitPane, java.awt.BorderLayout.CENTER);

        CommandManager cm = ConsoleCenter.commandManager;
        
        //��������ʼ��
        toolButtons.initToolButtons(cm);
        mainToolBar.setBorder(BorderFactory.createEtchedBorder(Color.white,new Color(165, 163, 151)));
        contentPane.add(mainToolBar, java.awt.BorderLayout.NORTH);

        //�˵���ʼ��
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
