package anyviewj.debug.actions;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

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
import anyviewj.interfaces.ui.JavaProject;
import anyviewj.interfaces.ui.drawer.FindMainMethod;
import anyviewj.interfaces.ui.manager.JavaProjectManager;
import anyviewj.interfaces.ui.panel.CodePane;
import anyviewj.interfaces.ui.panel.DebugTimeSelector;
import anyviewj.interfaces.ui.panel.FilePane;
import anyviewj.util.JVMArguments;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.Method;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.LaunchingConnector;
import com.sun.jdi.event.BreakpointEvent;
import com.sun.jdi.event.ClassPrepareEvent;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.ExceptionEvent;
import com.sun.jdi.event.VMDeathEvent;
import com.sun.jdi.event.VMDisconnectEvent;
import com.sun.jdi.event.VMStartEvent;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.EventRequestManager;
import com.sun.jdi.request.StepRequest;

//DebugFileTimeAction(定时调试器)的实现：
//2013年9月1日11:33:50
//1	程序进行调试的情况
//1.1	程序正确
//1.2	编码错误
//1.2.1	调试前需通过编译（不编译，使用的上一次经编译的文件），编译时，先清除上一次成功得class文件，编译成功后生产新的class文件，否则不生产；
//1.2.2	调试开始，尝试打开指定class文件，成功继续执行调试功能，否则终止。
//1.3	运行时异常
//1.3.1	程序方式RuntimeException时，若得到处理（try/catch），程序转到处理流程，正常执行；
//1.3.2	若没有得到处理，包括程序自己throws的，虚拟机自身抛出的，虚拟机的默认得处理流程是：装载异常处理机、按默认机制处理异常、发出一个ExceptionEvent。anyviewj在启动BreakpointManager会默认将UncaughtExceptionBreakpoint作为第一个Breakpoint，但遇到这种错误时，其会接收ExceptionEvent，进行解析，一般会通知session挂起虚拟机。
//1.4	逻辑功能错误
//1.4.1	需要特别注意“死循环”这种情况
//
//2	设计的思路：
//2.1	DebugFileTimeAction使用单例模式设计。这是因为其需要监听事件过多，只能是通过监听对象来被动调整内部状态，既是由外部的事件对象通过调用DebugFileTimeAction的方法来控制DebugFileTimeAction的执行什么操作，所以，必须保证所有事件对象控制的都是同一个DebugFileTimeAction实例。另外一点是定时调试时程序动态地在各个类文件面板间跳转，但只会有一个面板处于被选中状态，这决定了只会同时有一个定时调试的会话，也意味着无法调试多线程项目。
//2.2	从虚拟机未启动状态启动定时调试：
//2.2.1	根据当前代码面板获取相应类文件所属的工程，并获取该工程下的全部文件，启动虚拟机。
//2.2.2	成功启动虚拟机后，开始定时调试任务。当监听到该工程的类被加载时，获取相应类的方法（不包括父类、接口等的），在每个类的第一行代码处设置一个UndrawBreakpoint
//2.2.3	执行定时调试任务，直到被要求暂停或停止时，取消任务，并消除对外部环境做的修改，同时将内部状态恢复到处理下一次请求
//2.3	从虚拟机已经启动状态开始定时调试：
//2.3.1	根据当前代码面板获取相应类文件所属的工程，并获取该工程下的全部文件，从虚拟机获取这个类的全部方法，在每个方法的第一行代码处设置一个UndrawBreakpoint。
//2.3.2	成功在当前类设置断点后，开始定时调试任务。当监听到该工程的其他类被加载时，获取相应类的方法（不包括父类、接口等的），在每个方法的第一行代码处设置一个UndrawBreakpoint
//2.3.3	执行定时调试任务，直到被要求暂停或停止时，取消任务，并消除对外部环境做的修改，同时将内部状态恢复到处理下一次请求
//
//2.4	需要监听的事件：
//事件
//原因
//预期效果
//DebugTimeSelector(时间选择器)
//这个定时调试的事件选择器负责设置定时调试的时间间隔
//通过鼠标滑动划杆选择时间，在鼠标释放时，以新的时间间隔更新DebugFileTimeAction 
//DebugFileAction（普通调试）
//普通调试都是从程序入口处重新开始的，加入当前正处于定时调试状态，则需要关闭它
//无论当前定时调试处于什么状态，都应该强制为关闭状态
//pauseDebugAction(暂停)
//
//在当前位置立刻停止定时调试，以及使内部状态恢复到可重新启动。（注意线程同步问题）
//stopDebugAction(停止)
//
//关闭定时调试，并消除对外部做的修改，以及使内部状态恢复到可重新启动
//DebugFileTimeAction(自身)
//1、	重复点击，调试处于定时调试状态的同个项目
//2、	在定时调试工作状态下，点击调试其他项目
//1、	重复点击无效
//2、	警告用户关闭其他调试会话
//单步、步进、步出
//在定时调试处于暂停状态时，可能点击这些按钮
//不影响这些功能的正常执行
//断点
//在运行过程中运行到了用户设置了有效断点
//在当前位置立刻停止定时调试，以及使内部状态恢复到可重新启动。（注意线程同步问题）
//程序正常运行结束
//
//关闭定时调试，并消除对外部做的修改，以及使内部状态恢复到可重新启动
//虚拟机意外结束
//例如：虚拟机自身的缺陷、资源不够分配
//关闭定时调试，并消除对外部做的修改，以及使内部状态恢复到可重新启动
//异常
//1、	被捕捉的异常
//2、	没有捕捉的异常
//3、	先抛出再捕捉的异常
//4、	先捕捉再抛出的异常
//5、	自定义异常

//2013年9月6日20:04:41
//需要监听JavaProjectManager的closeProject方法
//以期至少在正在调试的工程被突然关闭时取消定时调试任务
//监听BreakPanel，不要显示UndrawBreakpoint
/**
 * 
 * 存在的问题：
 * 1、调试到catch语句后，程序会停顿很久，大概2秒，这个时候由于定时调试任务处于处于另一个线程下，
 * 所以在这段时间里，定时调试任务已经发出若干次但不请求，这样在视图上造成程序有若干条语句被跳过的错觉。
 * 实际原因是vm连续执行了这些请求，没有挂起。
 * 
 * 
 * @author hou
 * 
 */
public class DebugProjectTimeAction extends BaseAction {

	/**
	 * 单例模式设计
	 */
	private static final DebugProjectTimeAction debugProjectTimeAction = new DebugProjectTimeAction();
	private static final long serialVersionUID = 899272470936065890L;
	private static ConsoleCenter center = null;

	private Session session = null;
	private Timer timer = null;
	private boolean actived = false;  //标记定时调试的当前状态 true表示工作状态
	private int delay = DebugTimeSelector.DEFAULT_VALUE; //定时调试的时间间隔
	private List<String> spList = new ArrayList<String>(); //当期面板所属工程下的全部包名+类名
	private Set<String> classProcessed = new HashSet<String>(); //已经添加UndrawBreakpoint的该被调试工程下的类
	private JavaProject currentProject = null; //正在被执行定时调试的工程
	
	private VirtualMachine vm = null;
	private EventRequestManager erm = null;
	private BreakpointManager bpm = null;

	private List<String> entranceList = new ArrayList<String>();
	
	
	private DebugProjectTimeAction() {
		super("DebugFileTime");
	}

	public static DebugProjectTimeAction getDebugProjectTimeAction(
			ConsoleCenter aCenter) {

		center = aCenter;
		return debugProjectTimeAction;
	}

	public static DebugProjectTimeAction getDebugProjectTimeAction() {

		return debugProjectTimeAction;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if ( ( ( JavaProjectManager )( ConsoleCenter.projectManager ) ).getOpenPrjCount() == 0 )
//			没有打开的工程
		{
			return;
		}
		if (actived && (this.session == getSession(e)))
		// 当前session已经处于定时调试状态时，重复点击无效
		{
			return;
		}
		

		this.session = getSession(e);
        System.out.println("99999999999999999999999999999999999999");
		timingDebug();
	}
	/**
	 * 根据当期虚拟机是否已经启动来进行吊事调试
	 */
	private void timingDebug()
	{
		if (!session.isActive() )
		// 虚拟机还没有启动，需要先启动虚拟机，一旦启动，即可自动执行定时调试任务
		{
			startVM();
		} 
		else 
		{
//			获取当前正在调试的项目下的全部类，保存到spList中,返回的是当前正在运行的类
			int selectedFileIndex = sourcePaneGroup();
			
			vm = this.session.getVM();
			this.actived = true;
			
			
			addUndrawBreakpoints( spList.get(selectedFileIndex  ) );
			
			
			createClassPrepareRequest();
		}
	}
	/**
	 * 关闭定时调试
	 */
	public void deactive()
	{
//		设置当前正在被调试的项目为null
		this.currentProject = null;
		
//		使内部状态恢复到关闭状态并可接受下一次请求
		if ( this.actived == false )
		{
			return;
		}
		if ( this.timer == null )
		{
			this.actived = false;
			return;
		}
		cancelTask();
		this.actived = false;
		
//		清除掉全部添加的虚拟断点，否则将会在界面上被画出来
		Object[] list = bpm.breakpointsTable.values().toArray();
		for ( int i=0; i < list.length; i++  )
		{
			Breakpoint bp = (Breakpoint) list[i];
			if ( bp instanceof UndrawBreakpoint )
			{
				bpm.removeBreakpoint( bp );
			}
		}
		spList.clear();
		classProcessed.clear();
		
	}
	/**
	 * 启动定时调试任务
	 */
	public void startTask() {
		System.out.println( "startTask" );
		timer = new Timer();

		if (delay < 50)
		// 当延迟过短，程序可能还没执行完上一步，定时器就会发出第二次请求，这将导致抛出异常
		{
			delay = 50;
		}
		timer.schedule(new TimingDebug(), delay, delay);
		actived = true;
	}
	/**
	 * 停止定时调试
	 */
	public void cancelTask() {
		
		
		if ((timer == null) || (actived == false)) {
			this.timer = null;
			this.actived = false;
			return;
		}
		System.out.println( "public void cancelTask()" );
		timer.cancel();
		timer = null;
		this.actived = false;
	}
	private void processBreakpointEvent( Event e )
	{
        // We only register for BreakpointPrepareEvent, so  need to check.
        // Let's see if this event was brought on by a UndrawBreakpoint.
        EventRequest eventRequest = e.request();        
        Object o = eventRequest.getProperty("breakpoint"); 
        if (o != null && o instanceof UndrawBreakpoint ) {
//        	这是this添加的，是为了让程序运行到方法中去，但不要停止定时任务
        	return;
        }
//        遇到用户添加的断点要停下来
        deactive();
        
	}
	/**
	 * 如果被加载的是调试工程下的类，并且这个类还没有添加UndrawBreapoint时，添加断点
	 * @param e
	 */
	private void processClassPrepareEvent( Event e )
	{

		ClassPrepareEvent preparedClass = (ClassPrepareEvent) e;
		String preparedClassName = ( preparedClass.referenceType().name() );
		if ( spList.contains( preparedClassName ) 
				&& !classProcessed.contains( preparedClassName ) ) 
//			是当前调试项目下的自定义类并且是还没有经过处理的
		{		
			addUndrawBreakpoints(preparedClassName);
		}

	}
	/**
	 * 从虚拟机获取到这个类的方法，在每个类的第一行代码加一个断点
	 * @param preparedClassName 要添加断点的类，包名+类名
	 */
	private void addUndrawBreakpoints( String preparedClassName )
	{
		List<ReferenceType> classesByName = vm
				.classesByName(preparedClassName);
		if (classesByName == null || classesByName.size() == 0) {
//			在暂停定时调试状态下，直接定时调试不同项目时，会发生这种情况
			System.out.println("No class found");
			JOptionPane.showMessageDialog
			(null, "请先关闭其他调试会话", "", JOptionPane.WARNING_MESSAGE );
			this.actived = false;
			return;
		}
		
		classProcessed.add( preparedClassName );
		System.out.println( preparedClassName + " prepared!");
		
		ReferenceType rt = classesByName.get(0);
		
		List<Method> methods = rt.methods();
		if ( ( methods == null ) || ( methods.size() == 0 ) )
		{
			System.out.println("No method found");
			return;
		}

		bpm = (BreakpointManager) session
				.getManager(BreakpointManager.class);
		
		
		for ( Method method : methods )
		{
			try {
				String packageName = "";
				String fileName = preparedClassName;
				int lineNumber = -1;
				UndrawBreakpoint bp = null;
				if ( preparedClassName.contains( "." ) )
				{
					packageName = preparedClassName.substring( 0, preparedClassName.lastIndexOf( '.' ) );
					fileName = preparedClassName
							.substring( preparedClassName.lastIndexOf( '.' ), preparedClassName.length() );
				}
				fileName = preparedClassName  + ".java";
				
				if ( (method.location() == null) || ( method.location().codeIndex() == -1 ) )
				{
					return;
				}
				lineNumber = method.location().lineNumber();
				bp = new UndrawBreakpoint(packageName, fileName, lineNumber);
				bpm.addNewBreakpoint( bp );
				
			} catch (ResolveException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		if ( this.timer == null )
		{
			startTask();
		}
	
	}
	/**
	 * 监听被调试工程下每个类的加载
	 */
	private void createClassPrepareRequest()
	{
		vm = this.session.getVM();
		erm = vm.eventRequestManager();

		for ( String str : spList )
		{
			ClassPrepareRequest classPrepareRequest = erm.createClassPrepareRequest();
		    classPrepareRequest.addClassFilter( str );
		    classPrepareRequest.addCountFilter(1);
		    classPrepareRequest.setSuspendPolicy(EventRequest.SUSPEND_ALL);
		    classPrepareRequest.enable();
		}
	}
	public void eventOccurred(Event e)
	{
//		System.out.println("e = " + e);
		if ( actived == false )
		{
			return;
		}
		if ( e instanceof VMStartEvent )
		{
			createClassPrepareRequest();
			return;
		}


		if (e instanceof ClassPrepareEvent) 
		{
			processClassPrepareEvent( e );
			return;
		}
		if (e instanceof BreakpointEvent)
		{
			processBreakpointEvent( e );
			return;
		}
        if(( e.toString()).indexOf("java.lang.Thread.exit") > 0){
//        	在单线程程序中，这意味着程序运行完
        	System.out.println("catch Thread");
			
			deactive();
			return;
        }
//        if( ( ( e instanceof StepEvent ) 
//        		&& ( e.toString()).indexOf("java.lang.Throwable") > 0 )){
////        	遇到没有捕捉的异常
//        	System.out.println("noCatch Exception");
//			
//			deactive();
//			return;
//        }
		if ((e instanceof VMDisconnectEvent) || (e instanceof VMDeathEvent)
				|| (e instanceof ExceptionEvent)) {
			// 遇到特定事件或者异常，停止任务
			deactive();
			return;
		}

	}


	private class TimingDebug extends TimerTask {

		/**
		 * 由于任务的执行发生在新的线程里，在某些情况下AnyviewJd的其他部分在处理某个事件时需要很长时间，
		 * 这段等待的时间里，已经发出了若干个Step请求，在视图上造成跳过几条语句错觉，
		 */
		@Override
		public void run() {
			 stepOver();
//			stepInto();
		}
		private void stepInto() {
			System.out.println("step into ");
			if (session.isActive()) {
				// Get the current thread.
				ContextManager contextManager = (ContextManager) session
						.getManager(ContextManager.class);
				ThreadReference current = contextManager.getCurrentThread();
				if (current == null) {
					// 此时可能上一次的单步还未结束，不做处理
				} else {
					// Step a single line, into functions.
					if (Stepping.step(session.getVM(), current,
							StepRequest.STEP_LINE, StepRequest.STEP_INTO, false,
							session.getProperty("excludes"))) {
						// Must use the Session to (quietly) resume the VM.
						session.resumeVM(this, true, true);
					}
				}
			}
		}
		private void stepOver() {
			 System.out.println( "stepOver" );
			if (session.isActive()) {
				// Get the current thread.
				ContextManager contextManager = (ContextManager) session
						.getManager(ContextManager.class);
				ThreadReference current = contextManager.getCurrentThread();
				if (current == null) {
					// 此时可能上一次的单步还未结束，不做处理
				} else {
					// Step a single line, over functions.
					if (Stepping.step(session.getVM(), current,
							StepRequest.STEP_LINE, StepRequest.STEP_OVER,
							false, session.getProperty("excludes"))) {
						// Must use the Session to (quietly) resume the VM.
						session.resumeVM(this, true, true);
					}
				}
			}
		}
	}

	
	
	/**
	 * 根据当前代码面板获取其所属的项目工程，
	 * 进而获取同一项目下的全部源码文件
	 */
	private int sourcePaneGroup()
	{
		
		CodePane cp = ConsoleCenter.mainFrame.rightPartPane.codePane;
		JavaProjectManager jpm = ((JavaProjectManager)ConsoleCenter.projectManager);    	
    	FindMainMethod fm = new FindMainMethod(jpm);
    	this.entranceList = fm.getFileList();
	
    	FilePane fp  = ( FilePane )ConsoleCenter.mainFrame.rightPartPane.codePane.fileTabPane.getSelectedComponent();
		if(fp==null)
			return 0;
		String filePath = fp.source.getFilePath();
		String fileName = filePath.substring( filePath.indexOf( "\\src\\" ) + 5, filePath.length() );
		
//		currentProject = ( JavaProject )fp.getPrj();
		ArrayList< String > fl = (ArrayList<String>) this.entranceList;
		int selectedFile = -1;
		spList.clear();
		
		fileName = fileName.replace('\\', '/');
		for ( int i=0; i<jpm.allFileList.size(); i++ )
		{
			String name = (String)jpm.allFileList.get( i );
			String str = name.substring(name.indexOf("src\\")+4,name.length());
			str = str.replace('\\', '/');
			if ( str.compareTo( fileName ) == 0 )
			{
				selectedFile = i;
			}
			String tmp = str.replace( "\\", "." );
			
//			去掉“.java”
			tmp = tmp.substring(0, tmp.length() - 5 );
			spList.add( tmp );
		}

		
		return selectedFile;
	}
	/**
	 * 在当前处于非调试状态时才启动虚拟机
	 */
	public void startVM() {

		final Frame topFrame = new Frame();
		String javaHome = null;
		String jvmExecutable = null;
		String jvmOptions = null;
//		String suspended = null;
		boolean startSuspended = false;

//		if (suspended != null && suspended.equalsIgnoreCase("true")) {
//			startSuspended = true;
//		}
		// Use the launching connector for default values.
		VirtualMachineManager vmm = Bootstrap.virtualMachineManager();
		LaunchingConnector connector = vmm.defaultConnector();
		// Returns the arguments accepted by this Connector and their default
		// values.
		// The keys of the returned map are string argument names.
		Map args = connector.defaultArguments();
		if (javaHome == null || javaHome.length() == 0) {
			javaHome = ((Connector.Argument) args.get("home")).value();
		}
		if (jvmExecutable == null || jvmExecutable.length() == 0) {
			jvmExecutable = ((Connector.Argument) args.get("vmexec")).value();
		}

		final JTextField optionsField = new JTextField(jvmOptions, 30);
		JButton button = new JButton(Bundle.getString("VMStart.setProps"));

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PropertiesDialog pd = new PropertiesDialog(topFrame,
						optionsField);
				pd.pack();
				pd.setLocationRelativeTo(topFrame);
				pd.setResizable(false);
				pd.setVisible(true);
			}
		});
		
		
		ConsoleCenter.setCurrentSession(session);
		ConsoleCenter.setViewSession(session);
		String mainClassName = choosingEntrance((BasicSession) session);
		if ( mainClassName == null )
		{
			return;
		}
		 mainClassName = mainClassName.substring(0, mainClassName.indexOf( "." ) );
		VMEventManager vmeman = (VMEventManager) session
				.getManager(VMEventManager.class);
		ConsoleCenter.putProSessionMap(mainClassName, session);
		jvmOptions = optionsField.getText();

		// 参数包含了class文件名
		JVMArguments jvmArgs = new JVMArguments(jvmOptions + ' ' + mainClassName);
		PathManager pathman = (PathManager) session
				.getManager(PathManager.class);

		String classpath = "J:\\AnyviewJ\\AnyviewJPrj\\bin;"
				+ pathman.getClassPathAsString();

		jvmOptions = jvmArgs.normalizedOptions(classpath);

		// Could be getting a main class or jar file name here.
		mainClassName = jvmArgs.stuffAfterOptions();
		if (session.isActive()) {
			// Deactivate(使无效) current session.
			session.deactivate(false, this);
			// session.close(session);
		}

		VMConnection connection = null;

		try {
//			hou 2013年8月29日17:02:27
			this.actived = true;
			connection = VMConnection.buildConnection(javaHome, jvmExecutable,
					jvmOptions, mainClassName);
		} catch (IllegalArgumentException iae) {
			return;
		}

		// Display the options and classname that the launcher is
		// actually going to use.
		// Log out = session.getStatusLog();
		// out.writeln(connection.loadingString());

		// Save the values to the session settings for later reuse.
		session.setProperty("javaHome", javaHome);
		session.setProperty("jvmExecutable", jvmExecutable);
		session.setProperty("jvmOptions", jvmArgs.parsedOptions());
		session.setProperty("mainClass", mainClassName);
		session.setProperty("startSuspended", String.valueOf(startSuspended));
		
		// Show a busy cursor while we launch the debuggee.
		// topFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		boolean launched = connection.launchDebuggee(session, true);
		// topFrame.setCursor(Cursor.getDefaultCursor());


		if (launched) {
			if (!startSuspended) {
				// Now that the Session has completely activated,
				// we may resume(重启) the debuggee VM.
				session.resumeVM(this, false, false);
			}
		} else {
//			启动失败
			this.actived = false;
		}
	} 

	public String choosingEntrance(BasicSession se) {
		sourcePaneGroup();
		
		if(entranceList.isEmpty())
		{
			String message = "未找到任何程序入口!";
			String title = "警告";
			int optionType = JOptionPane.DEFAULT_OPTION;
			int messageType = JOptionPane.ERROR_MESSAGE;
			JOptionPane.showConfirmDialog(ConsoleCenter.mainFrame.rightPartPane.codePane, message, title, optionType, messageType);
			return null;
		}
		Object[] a = new Object[this.entranceList.size()];
		int temp = 0;
		for (int i = 0; i < this.entranceList.size(); i++) {
			a[i] = this.entranceList.get(i);
//			System.out.println("++++++++++++++  :  "+se.getCurrentSourcePane().sourceSrc.getName()+"   "+((String) spList.get(i)));
//			if (((String) this.entranceList.get(i))
//					.equals(se.getCurrentSourcePane().sourceSrc.getName())) {
//				temp = i;
//			}
		}
		Object xChooser = JOptionPane.showInputDialog(null, "请选择程序入口",
				"程序入口选择", 0, null, a, a[temp]);
		
//		String ss = ((String)a[temp]).substring(0, ((String)a[temp]).length() - 5 );
		return ((String)xChooser);
	}

	public void setCenter(ConsoleCenter center) {
		DebugProjectTimeAction.center = center;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		
		if ( delay < 50 )
		{
			delay = 50;
		}
		this.delay = delay;
	}

	public boolean isActived() {
		return actived;
	}
	public JavaProject getCurrentProject() {
		return currentProject;
	}
}

// package anyviewj.debug.actions;
//
// import java.awt.event.ActionEvent;
// import java.util.List;
// import java.util.Map;
// import java.util.Timer;
// import java.util.TimerTask;
//
// import anyviewj.console.ConsoleCenter;
// import anyviewj.debug.actions.Stepping;
// import anyviewj.debug.connect.VMConnection;
// import anyviewj.debug.event.VMEventListener;
// import anyviewj.debug.manager.ContextManager;
// import anyviewj.debug.manager.PathManager;
// import anyviewj.debug.manager.VMEventManager;
// import anyviewj.debug.session.BasicSession;
// import anyviewj.debug.session.Session;
// import anyviewj.interfaces.ui.JavaProject;
// import anyviewj.interfaces.ui.manager.JavaProjectManager;
// import anyviewj.interfaces.ui.panel.DebugTimeSelector;
// import anyviewj.interfaces.ui.panel.SourcePane;
// import anyviewj.util.JVMArguments;
//
// import com.sun.jdi.Bootstrap;
// import com.sun.jdi.Method;
// import com.sun.jdi.ReferenceType;
// import com.sun.jdi.ThreadReference;
// import com.sun.jdi.VirtualMachine;
// import com.sun.jdi.VirtualMachineManager;
// import com.sun.jdi.connect.Connector;
// import com.sun.jdi.connect.Connector.Argument;
// import com.sun.jdi.connect.LaunchingConnector;
// import com.sun.jdi.event.BreakpointEvent;
// import com.sun.jdi.event.ClassPrepareEvent;
// import com.sun.jdi.event.Event;
// import com.sun.jdi.event.ExceptionEvent;
// import com.sun.jdi.event.VMDeathEvent;
// import com.sun.jdi.event.VMDisconnectEvent;
// import com.sun.jdi.request.StepRequest;
//
// /**
// * 需要监听的：
// * DebugTimeSelector、UncaughtException、pause、stop、各种VMEvent
// * @author hou
// *
// */
// public class DebugFileTimeAction extends BaseAction implements
// VMEventListener {
//
// /**
// *
// */
// private static final DebugFileTimeAction debugFileTimeAction = new
// DebugFileTimeAction();
// private static final long serialVersionUID = 899272470936065890L;
// private static ConsoleCenter center = null;
//
//
// private Session session = null;
// private Timer timer = null;
// private boolean actived = false;
// private int delay = DebugTimeSelector.DEFAULT_VALUE;
//
//
//
// String mainClassName = null;
//
//
// private DebugFileTimeAction()
// {
// super("DebugFileTime");
// }
//
// public static DebugFileTimeAction getDebugFileTimeAction( ConsoleCenter
// aCenter ) {
//
// center = aCenter;
// return debugFileTimeAction;
// }
// public static DebugFileTimeAction getDebugFileTimeAction( ) {
//
// return debugFileTimeAction;
// }
//
// @Override
// public void actionPerformed(ActionEvent e) {
//
// if ( actived && ( this.session == getSession( e ) ) )
// // 当前session已经处于定时调试状态时，重复点击无效
// {
// return;
// }
//
// this.session = getSession(e);
//
// // 虚拟机一旦启动就会产生事件，所以需要先向管理器注册需要监听的VMEvent
// // 当取消任务时，应该取消全部监听
// listenVMEvents();
//
// if (!session.isActive())
// // 虚拟机还没有启动，需要先启动虚拟机
// {
// startVM();
//
// }
// startTask();
// }
//
// public void startTask()
// {
// timer = new Timer();
//
// if ( delay <= 50 )
// // 当延迟过短，程序可能还没执行完上一步，定时器就会发出第二次请求，这将导致抛出异常
// {
// delay = 50;
// }
// timer.schedule(new TimingDebug(), 250, delay );
// actived = true;
// }
//
// public void cancelTask()
// {
// if ( ( timer == null ) || ( actived == false ) )
// {
// return;
// }
// timer.cancel();
// cancelListened();
// actived = false;
// }
//
// private void listenVMEvents() {
//
// VMEventManager vmman = (VMEventManager) session
// .getManager(VMEventManager.class);
//
// vmman.addListener(ClassPrepareEvent.class, this,
// VMEventListener.PRIORITY_DEFAULT);
// vmman.addListener(BreakpointEvent.class, this,
// VMEventListener.PRIORITY_DEFAULT);
// vmman.addListener(ExceptionEvent.class, this,
// VMEventListener.PRIORITY_DEFAULT);
// vmman.addListener(VMDisconnectEvent.class, this,
// VMEventListener.PRIORITY_DEFAULT);
// vmman.addListener(VMDeathEvent.class, this,
// VMEventListener.PRIORITY_DEFAULT);
// }
//
// private void cancelListened() {
// // 取消对虚拟机的监听
// VMEventManager vmman = (VMEventManager) session
// .getManager(VMEventManager.class);
//
// vmman.removeListener(ClassPrepareEvent.class, this);
// vmman.removeListener(BreakpointEvent.class, this);
// vmman.removeListener(ExceptionEvent.class, this);
// vmman.removeListener(VMDisconnectEvent.class, this);
// vmman.removeListener(VMDeathEvent.class, this);
//
// }
//
// @Override
// public boolean eventOccurred(Event e) {
// // 遇到断点或者异常，停止任务
// System.out.println( "e = " + e );
// if ( ( e instanceof BreakpointEvent )
// || ( e instanceof VMDisconnectEvent )
// || ( e instanceof VMDeathEvent )
// || ( e instanceof ExceptionEvent ) )
// {
// cancelTask();
// }
// // if ( e instanceof ExceptionEvent )
// // {
// // System.out.println( "gfjk" );
// // }
// // if ( e instanceof BreakpointEvent )
// // {
// // System.out.println( "gfkgnfkgnf" );
// // }
// return true;
// }
//
//
//
// private class TimingDebug extends TimerTask {
//
// @Override
// public void run() {
// // stepOver();
// stepInto();
// }
//
// private void stepOver() {
// // System.out.println( "TimingDebugAction" );
// if (session.isActive()) {
// // Get the current thread.
// ContextManager contextManager = (ContextManager) session
// .getManager(ContextManager.class);
// ThreadReference current = contextManager.getCurrentThread();
// if (current == null) {
// // 此时可能上一次的单步还未结束，不做处理
// } else {
// // Step a single line, over functions.
// if (Stepping.step(session.getVM(), current,
// StepRequest.STEP_LINE, StepRequest.STEP_OVER,
// false, session.getProperty("excludes"))) {
// // Must use the Session to (quietly) resume the VM.
// session.resumeVM(this, true, true);
// }
// }
// }
// }
// private void stepInto()
// {
// System.out.println( "step into " );
// if (session.isActive()) {
// // Get the current thread.
// ContextManager contextManager = (ContextManager) session
// .getManager(ContextManager.class);
// ThreadReference current = contextManager.getCurrentThread();
// if (current == null) {
// // 此时可能上一次的单步还未结束，不做处理
// } else {
// // Step a single line, into functions.
// if (Stepping.step(session.getVM(), current,
// StepRequest.STEP_LINE, StepRequest.STEP_INTO,
// false, session.getProperty("excludes"))) {
// // Must use the Session to (quietly) resume the VM.
// session.resumeVM(this, true, true);
// }
// }
// }
// }
// }
// private void startVM() {
//
// SourcePane sourcePane = ((BasicSession) session).getCurrentSourcePane();
// if (sourcePane.sourceSrc == null
// || sourcePane.sourceSrc.getPackage() == null) {
// // 单独打开的文件不能调试
// return;
// }
//
// String mainClass = null;
// if (sourcePane.sourceSrc.getPackage() != null
// && !(new String("")).equals(sourcePane.sourceSrc.getPackage())) {
// mainClass = (sourcePane.sourceSrc.getPackage() + "." + sourcePane.sourceSrc
// .getName());
// } else {
// mainClass = sourcePane.sourceSrc.getName();
// }
//
// JavaProject prj = (JavaProject) ((JavaProjectManager) center.projectManager)
// .findProject(sourcePane.getName());
// String a = mainClass.substring(0, mainClass.indexOf(".java"));
// String b = a.replace('.', '\\');
// String classPath = prj.getClassPath() + "\\" + b + ".class";
// // if(!hasMainMethod(classPath)) {
// // //没有包含主函数的文件不能调试
// // return;
// // }
//
// mainClass = mainClass.substring(0, mainClass.indexOf(".java"));
//
// debugFile(mainClass, this);
//
// } // actionPerformed
//
// private void debugFile(String mainClass, Object source) {
// String javaHome = session.getProperty("javaHome");
// String jvmExecutable = session.getProperty("jvmExecutable");
// String jvmOptions = session.getProperty("jvmOptions");
//
// String suspended = session.getProperty("startSuspended");
// boolean startSuspended = false;
// if (suspended != null && suspended.equalsIgnoreCase("true")) {
// startSuspended = true;
// }
// // Use the launching connector for default values.
// VirtualMachineManager vmm = Bootstrap.virtualMachineManager();
// LaunchingConnector connector = vmm.defaultConnector();
// Map<String, Argument> args = connector.defaultArguments();
// if (javaHome == null || javaHome.length() == 0) {
// javaHome = ((Connector.Argument) args.get("home")).value();
// }
// if (jvmExecutable == null || jvmExecutable.length() == 0) {
// jvmExecutable = ((Connector.Argument) args.get("vmexec")).value();
// }
// JVMArguments jvmArgs = new JVMArguments(jvmOptions + ' ' + mainClass);
// PathManager pathman = (PathManager) session
// .getManager(PathManager.class);
// String classpath = pathman.getClassPathAsString();
// // pathman.setSourcePath(sourcepath);
// jvmOptions = jvmArgs.normalizedOptions(classpath);
// // Could be getting a main class or jar file name here.
// mainClass = jvmArgs.stuffAfterOptions();
//
// if (session.isActive()) {
// // Deactivate current session.
// session.deactivate(false, source);
// }
//
// VMConnection connection = null;
// try {
// connection = VMConnection.buildConnection(javaHome, jvmExecutable,
// jvmOptions, mainClass);
// } catch (IllegalArgumentException iae) {
// iae.printStackTrace();
// return;
// }
//
// // Save the values to the session settings for later reuse.
// session.setProperty("javaHome", javaHome);
// session.setProperty("jvmExecutable", jvmExecutable);
// session.setProperty("jvmOptions", jvmArgs.parsedOptions());
// session.setProperty("mainClass", mainClass);
// session.setProperty("startSuspended", String.valueOf(startSuspended));
//
// // Show a busy cursor while we launch the debuggee.
// boolean launched = connection.launchDebuggee(session, true);
//
// mainClassName = mainClass;
//
// if (launched) {
// if (!startSuspended) {
// // Now that the Session has completely activated,
// // we may resume the debuggee VM.
// session.resumeVM(source, false, false);
// }
// } else {
// // session.getUIAdapter().showMessage(
// // UIAdapter.MESSAGE_ERROR,
// // anyviewj.debuger.Bundle.getString("vmLoadFailed"));
// }
// }
// public void setCenter(ConsoleCenter center) {
// this.center = center;
// }
// public int getDelay() {
// return delay;
// }
//
// public void setDelay(int delay) {
// this.delay = delay;
// }
//
// public boolean isActived() {
// return actived;
// }
//
// }
