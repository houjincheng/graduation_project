
package anyviewj;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import anyviewj.debug.session.BasicSession;
import anyviewj.debug.session.Session;
import anyviewj.debug.session.SessionActionAdapter;
import anyviewj.debug.session.SessionFrameMapper;
import anyviewj.interfaces.ui.panel.MainFrame;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: gdut 1627</p>
 *
 * @author ltt
 * @version 1.0
 */
public class AnyviewJApp {
	
    boolean packFrame = false;

    /**
     * Terminate the given Session. This is the same as calling
     * <code>endSession(session, true)</code> (allow the JVM to
     * exit).
     *
     * @param  session  Session to be ended.
     */
    public static void endSession(Session session) {
    	if (session.isActive()) {
            session.deactivate(false, session);
        }
        session.close(session);
    } // endSession
    /**
     * Construct and show the application.
     */
    public AnyviewJApp() {
        MainFrame frame = new MainFrame();
        // Validate frames that have preset sizes
        // Pack frames that have useful preferred size info, e.g. from their layout
        if (packFrame) {
            frame.pack();
        } else {
            frame.validate();
        }
//        hou 2013Äê5ÔÂ20ÈÕ22:04:16
        AnyviewJApp.createSession(frame);
        // Center the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        frame.setLocation((screenSize.width - frameSize.width) / 2,
                          (screenSize.height - frameSize.height) / 2);
        frame.setVisible(true);
        
        
    }   

    public static Session createSession(JFrame frame) {
    	System.out.println("public static Session createSession(JFrame frame)");
    	Session session = new BasicSession();
    	session.init(((MainFrame)frame).center);
    	SessionActionAdapter saa = new SessionActionAdapter();
        session.addListener(saa);
//        try{
//        	session.addManager(Class.forName("anyviewj.debug.manager.BreakpointManager"),ConsoleCenter.getcurrentBKManager());
//        }
//        catch(Exception e){
//        	e.printStackTrace();
//        }
        session.initComplete();
        SessionFrameMapper.addFrameSessionMapping(frame, session);
    	return session;
    }
    /**
     * Application entry point.
     *
     * @param args String[]
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
			public void run() {
                try {
                    System.out.println(UIManager.getSystemLookAndFeelClassName());
                    UIManager.setLookAndFeel(UIManager.
                                             getSystemLookAndFeelClassName());
                    //com.sun.java.swing.plaf.windows.WindowsLookAndFeel
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                new AnyviewJApp();
            }
        });
    }
}
