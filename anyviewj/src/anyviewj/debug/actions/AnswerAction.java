package anyviewj.debug.actions;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import anyviewj.console.ConsoleCenter;
import anyviewj.debug.breakpoint.Breakpoint;
import anyviewj.debug.breakpoint.ResolveException;
import anyviewj.debug.breakpoint.UndrawBreakpoint;
import anyviewj.debug.connect.VMConnection;
import anyviewj.debug.manager.BreakpointManager;
import anyviewj.debug.manager.ContextManager;
import anyviewj.debug.manager.PathManager;
import anyviewj.debug.manager.VMEventManager;
import anyviewj.debug.session.BasicSession;
import anyviewj.debug.session.Session;
import anyviewj.debug.session.SessionFrameMapper;
import anyviewj.interfaces.ui.Project;
import anyviewj.interfaces.ui.drawer.FindMainMethod;
import anyviewj.interfaces.ui.manager.JavaProjectManager;
import anyviewj.util.JVMArguments;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.LaunchingConnector;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.EventRequestManager;
import com.sun.jdi.request.StepRequest;

public class AnswerAction extends AbstractDebugAction{

	public List spList = new ArrayList();
	public static BreakpointManager bm = null;
	public String packName = null;
	
	public AnswerAction() {
		super("answer");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Session session = ConsoleCenter.getCurrentSession();
		
		if(!session.isActive())
			start(e);
		else
		{
			
                    session.resumeVM(this, true, true);
                    
			
		}
	}

	public void start(ActionEvent e){
		final Frame topFrame = new Frame();
    	Session session = SessionFrameMapper.getSessionForFrame(SessionFrameMapper.getOwningFrame(e));
        
    	//String javaHome = session.getProperty("javaHome");
        //String jvmExecutable = session.getProperty("jvmExecutable");
        //String jvmOptions = session.getProperty("jvmOptions");
        //String mainClass = session.getProperty("mainClass");
        //String suspended = session.getProperty("startSuspended");
        String javaHome = null;
        String jvmExecutable = null;
        String jvmOptions = null;
        String mainClass = null;
        String suspended = null;
        boolean startSuspended = false;
        
        if (suspended != null && suspended.equalsIgnoreCase("true")) {
            startSuspended = true;
        }
        // Use the launching connector for default values.
        VirtualMachineManager vmm = Bootstrap.virtualMachineManager();
        LaunchingConnector connector = vmm.defaultConnector();
//        Returns the arguments accepted by this Connector and their default values. 
//        The keys of the returned map are string argument names.
        Map args = connector.defaultArguments();
        if (javaHome == null || javaHome.length() == 0) {
            javaHome = ((Connector.Argument) args.get("home")).value();
        }
        if (jvmExecutable == null || jvmExecutable.length() == 0) {
            jvmExecutable = ((Connector.Argument) args.get("vmexec")).value();
        }

        final JTextField optionsField = new JTextField(jvmOptions, 30);
        //对话框中间的“set -D Properties”按钮
        JButton button = new JButton(Bundle.getString("VMStart.setProps"));
        
        button.addActionListener(new ActionListener() {
                @Override
				public void actionPerformed(ActionEvent e) {
                	PropertiesDialog pd = new PropertiesDialog(topFrame, optionsField);
                    pd.pack();
                    pd.setLocationRelativeTo(topFrame);
                    pd.setResizable(false);
                    pd.setVisible(true);
                }
            });
        //String fake = choosingEntrance();
        //SourcePane sp = ((BasicSession)session).getCurrentSourcePane();
        ConsoleCenter.setCurrentSession(session);
        ConsoleCenter.setViewSession(session);
        String mainName = choosingEntrance((BasicSession)session);    
        
        
        if(mainName==null)
        	return;
        
       
        addOneBreakpoint(mainName,session);
        
        
        mainClass = mainName.substring(0, mainName.indexOf( "." ) );
        

;
             
             VMEventManager vmeman = (VMEventManager)
		               session.getManager(VMEventManager.class);
             ConsoleCenter.putProSessionMap(mainClass, session);

            jvmOptions = optionsField.getText();
//            startSuspended = ((JCheckBox) messages[9]).isSelected();
           	           
//            System.out.println("javaHome: "+javaHome+"   "+"jvmExecutable:  "+jvmExecutable+"  "+"jvmOptions:  "+jvmOptions+"  ---------------- ");
            
            //参数包含了class文件名
            JVMArguments jvmArgs = new JVMArguments(jvmOptions + ' ' + mainClass);
            PathManager pathman = (PathManager)
            		session.getManager(PathManager.class);
            
            String classpath = "J:\\AnyviewJ\\AnyviewJPrj\\bin;" + pathman.getClassPathAsString();
//            System.out.println("Classpath ="+classpath);
            
            jvmOptions = jvmArgs.normalizedOptions(classpath);
            
            // Could be getting a main class or jar file name here.
            mainClass = jvmArgs.stuffAfterOptions();
            if (session.isActive()) {
            	// Deactivate(使无效) current session.            
                session.deactivate(false, this);
//                hou 2013年9月1日9:08:07
//                notifyDebugFileTimeAction();
 	            //session.close(session);
             }
            
            VMConnection connection = null;
            try {
            	connection = VMConnection.buildConnection(
            			javaHome, jvmExecutable, jvmOptions, mainClass);
            }                
             catch (IllegalArgumentException iae) {
            //session.getUIAdapter().showMessage(
            //    UIAdapter.MESSAGE_ERROR,
            //    iae.getMessage());
                 return;
            }



        // Save the values to the session settings for later reuse.
        session.setProperty("javaHome", javaHome);
        session.setProperty("jvmExecutable", jvmExecutable);
        session.setProperty("jvmOptions", jvmArgs.parsedOptions());
        session.setProperty("mainClass", mainClass);
        session.setProperty("startSuspended",
                            String.valueOf(startSuspended));

        // Show a busy cursor while we launch the debuggee.
        //topFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        boolean launched = connection.launchDebuggee(session, true);
        //topFrame.setCursor(Cursor.getDefaultCursor());

        if (launched) {
            if (!startSuspended)
            {
                // Now that the Session has completely activated,
                // we may resume(重启) the debuggee VM.	            	
                session.resumeVM(this, false, false);
                
                ConsoleCenter.output( "!startSuspended\n" );


            }
        } else {
            //session.getUIAdapter().showMessage(
            //    UIAdapter.MESSAGE_ERROR,
            //    com.bluemarsh.jswat.Bundle.getString("vmLoadFailed"));
        }    
       // createClassPrepareRequest(session.getVM(),mainName);
	}
	
	private void createClassPrepareRequest( VirtualMachine vm , String mainName) {
		// TODO Auto-generated method stub
		EventRequestManager erm = vm.eventRequestManager();

		int ch = mainName.indexOf(".");
		
			ClassPrepareRequest classPrepareRequest = erm.createClassPrepareRequest();
		    classPrepareRequest.addClassFilter(mainName.substring(0,ch));
		    classPrepareRequest.addCountFilter(1);
		    classPrepareRequest.setSuspendPolicy(EventRequest.SUSPEND_ALL);
		    classPrepareRequest.enable();
		
	}

	public void addOneBreakpoint(String mainName,Session session){
		int lineNum = findLineNumber(mainName);
		int ch = mainName.indexOf("/");
		BreakpointManager bpm = (BreakpointManager) session
				.getManager(BreakpointManager.class);
		this.bm = bpm;

		UndrawBreakpoint bp = null;
		try {
			bp = new UndrawBreakpoint(packName,"Main.java", lineNum+2);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {

			bpm.addNewBreakpoint( bp );
		
		} catch (ResolveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int findLineNumber(String mainName){
		
		
		JavaProjectManager jpm = ((JavaProjectManager)ConsoleCenter.projectManager);
		List fileList = new ArrayList();
		
		boolean c1 = false;
		boolean c2 = false;
		int count = 0;
		int locate = 0;
		
		for(int i = 0;i<jpm.allFileList.size();i++)
    	{
    	 File file = new File((String) jpm.allFileList.get(i));

    	 if(file.getName().equals("Main.java"))
    	 {
    		 mainName = file.getName();
    		 int x = file.getParent().lastIndexOf("\\");
    		 packName = file.getParent().substring(x+1,file.getParent().length());
    
    	 }
    	 else
    		 continue;
    	 
    	 int ch = mainName.indexOf("/");
//    	 if(!file.getName().equals(mainName.substring(ch+1)))
//    		 continue;
         FileInputStream is = null;
    	 if(!file.exists())
    	 {
    		 System.out.println("File not exist!!!");
    		 continue;
    	 }

		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}//file.source.sourceSrc.getInputStream();
       
        
    	 InputStreamReader isr = new InputStreamReader(is);
    	 BufferedReader br = new BufferedReader(isr);
         try {
        	while(br.read()!=-1){
        		String s = br.readLine();
        		count++;
			if(s!=null){
//        	if(s.contains("main")&&s.contains("static")&&s.contains("void"))
//        	{
//        	  c1 = true;
//        	}
				System.out.println("----------------------------  :  "+s+"    lineNum : "+count);
        	if(s.contains("for")&&s.contains("temp"))
        	{
        		c2 = true;
        	}
        	if(c2&&s.contains("i"))
        	{
        		locate = count;
        		System.out.println("----------------------------  :  "+locate);
        		return locate+3;
        	}
		  }
         }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
         
    	}
		return -1;
	}
	
	
	public int sourcePaneGroup(){
    	
    	JavaProjectManager jpm = ((JavaProjectManager)ConsoleCenter.projectManager);
    	
    	
    	if(jpm==null)
    		return 0;
    	List copy = new ArrayList();
    	for(int x = 0;x<jpm.prjList.size();x++)
    	{
    		copy.add(jpm.prjList.get(x));
    	}
    	jpm.prjList.clear();

    	for(int i = 0;i<copy.size();i++)
    	{
    		jpm.setprjList((Project) copy.get(i));
    	}
    	FindMainMethod fm = new FindMainMethod(jpm);
        spList.clear();
    	spList = fm.getFileList();
    	return 1;
    }
	
	public String choosingEntrance(BasicSession se){
		
		
		
		sourcePaneGroup();
		if(spList.isEmpty())
		{
			String message = "未找到任何程序入口!";
			String title = "警告";
			int optionType = JOptionPane.DEFAULT_OPTION;
			int messageType = JOptionPane.ERROR_MESSAGE;
			JOptionPane.showConfirmDialog(ConsoleCenter.mainFrame.rightPartPane.codePane, message, title, optionType, messageType);
			return null;
		}
		Object[] a = new Object[spList.size()];
		int temp = 0;
		for(int i = 0;i<spList.size();i++)
		{				
			a[i] = spList.get(i);
//			if(((String)spList.get(i)).equals(se.getCurrentSourcePane().sourceSrc.getName()))
//				{
//				  temp = i;
//				}
		}
		Object xChooser = JOptionPane.showInputDialog(null,"请选择程序入口","程序入口选择",0,null,a,a[temp]);
	    return ((String)xChooser);
	}
   
	public static void dective(){
		if(bm==null)
			return;
		Object[] list = bm.breakpointsTable.values().toArray();
		for ( int i=0; i < list.length; i++  )
		{
			Breakpoint bp = (Breakpoint) list[i];
			if ( bp instanceof UndrawBreakpoint )
			{
				bm.removeBreakpoint( bp );
			}
		}
	}
	

	
}
