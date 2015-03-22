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

//DebugFileTimeAction(��ʱ������)��ʵ�֣�
//2013��9��1��11:33:50
//1	������е��Ե����
//1.1	������ȷ
//1.2	�������
//1.2.1	����ǰ��ͨ�����루�����룬ʹ�õ���һ�ξ�������ļ���������ʱ���������һ�γɹ���class�ļ�������ɹ��������µ�class�ļ�������������
//1.2.2	���Կ�ʼ�����Դ�ָ��class�ļ����ɹ�����ִ�е��Թ��ܣ�������ֹ��
//1.3	����ʱ�쳣
//1.3.1	����ʽRuntimeExceptionʱ�����õ�����try/catch��������ת���������̣�����ִ�У�
//1.3.2	��û�еõ��������������Լ�throws�ģ�����������׳��ģ��������Ĭ�ϵô��������ǣ�װ���쳣���������Ĭ�ϻ��ƴ����쳣������һ��ExceptionEvent��anyviewj������BreakpointManager��Ĭ�Ͻ�UncaughtExceptionBreakpoint��Ϊ��һ��Breakpoint�����������ִ���ʱ��������ExceptionEvent�����н�����һ���֪ͨsession�����������
//1.4	�߼����ܴ���
//1.4.1	��Ҫ�ر�ע�⡰��ѭ�����������
//
//2	��Ƶ�˼·��
//2.1	DebugFileTimeActionʹ�õ���ģʽ��ơ�������Ϊ����Ҫ�����¼����ֻ࣬����ͨ���������������������ڲ�״̬���������ⲿ���¼�����ͨ������DebugFileTimeAction�ķ���������DebugFileTimeAction��ִ��ʲô���������ԣ����뱣֤�����¼�������ƵĶ���ͬһ��DebugFileTimeActionʵ��������һ���Ƕ�ʱ����ʱ����̬���ڸ������ļ�������ת����ֻ����һ����崦�ڱ�ѡ��״̬���������ֻ��ͬʱ��һ����ʱ���ԵĻỰ��Ҳ��ζ���޷����Զ��߳���Ŀ��
//2.2	�������δ����״̬������ʱ���ԣ�
//2.2.1	���ݵ�ǰ��������ȡ��Ӧ���ļ������Ĺ��̣�����ȡ�ù����µ�ȫ���ļ��������������
//2.2.2	�ɹ�����������󣬿�ʼ��ʱ�������񡣵��������ù��̵��౻����ʱ����ȡ��Ӧ��ķ��������������ࡢ�ӿڵȵģ�����ÿ����ĵ�һ�д��봦����һ��UndrawBreakpoint
//2.2.3	ִ�ж�ʱ��������ֱ����Ҫ����ͣ��ֹͣʱ��ȡ�����񣬲��������ⲿ���������޸ģ�ͬʱ���ڲ�״̬�ָ���������һ������
//2.3	��������Ѿ�����״̬��ʼ��ʱ���ԣ�
//2.3.1	���ݵ�ǰ��������ȡ��Ӧ���ļ������Ĺ��̣�����ȡ�ù����µ�ȫ���ļ������������ȡ������ȫ����������ÿ�������ĵ�һ�д��봦����һ��UndrawBreakpoint��
//2.3.2	�ɹ��ڵ�ǰ�����öϵ�󣬿�ʼ��ʱ�������񡣵��������ù��̵������౻����ʱ����ȡ��Ӧ��ķ��������������ࡢ�ӿڵȵģ�����ÿ�������ĵ�һ�д��봦����һ��UndrawBreakpoint
//2.3.3	ִ�ж�ʱ��������ֱ����Ҫ����ͣ��ֹͣʱ��ȡ�����񣬲��������ⲿ���������޸ģ�ͬʱ���ڲ�״̬�ָ���������һ������
//
//2.4	��Ҫ�������¼���
//�¼�
//ԭ��
//Ԥ��Ч��
//DebugTimeSelector(ʱ��ѡ����)
//�����ʱ���Ե��¼�ѡ�����������ö�ʱ���Ե�ʱ����
//ͨ����껬������ѡ��ʱ�䣬������ͷ�ʱ�����µ�ʱ��������DebugFileTimeAction 
//DebugFileAction����ͨ���ԣ�
//��ͨ���Զ��Ǵӳ�����ڴ����¿�ʼ�ģ����뵱ǰ�����ڶ�ʱ����״̬������Ҫ�ر���
//���۵�ǰ��ʱ���Դ���ʲô״̬����Ӧ��ǿ��Ϊ�ر�״̬
//pauseDebugAction(��ͣ)
//
//�ڵ�ǰλ������ֹͣ��ʱ���ԣ��Լ�ʹ�ڲ�״̬�ָ�����������������ע���߳�ͬ�����⣩
//stopDebugAction(ֹͣ)
//
//�رն�ʱ���ԣ����������ⲿ�����޸ģ��Լ�ʹ�ڲ�״̬�ָ�������������
//DebugFileTimeAction(����)
//1��	�ظ���������Դ��ڶ�ʱ����״̬��ͬ����Ŀ
//2��	�ڶ�ʱ���Թ���״̬�£��������������Ŀ
//1��	�ظ������Ч
//2��	�����û��ر��������ԻỰ
//����������������
//�ڶ�ʱ���Դ�����ͣ״̬ʱ�����ܵ����Щ��ť
//��Ӱ����Щ���ܵ�����ִ��
//�ϵ�
//�����й��������е����û���������Ч�ϵ�
//�ڵ�ǰλ������ֹͣ��ʱ���ԣ��Լ�ʹ�ڲ�״̬�ָ�����������������ע���߳�ͬ�����⣩
//�����������н���
//
//�رն�ʱ���ԣ����������ⲿ�����޸ģ��Լ�ʹ�ڲ�״̬�ָ�������������
//������������
//���磺����������ȱ�ݡ���Դ��������
//�رն�ʱ���ԣ����������ⲿ�����޸ģ��Լ�ʹ�ڲ�״̬�ָ�������������
//�쳣
//1��	����׽���쳣
//2��	û�в�׽���쳣
//3��	���׳��ٲ�׽���쳣
//4��	�Ȳ�׽���׳����쳣
//5��	�Զ����쳣

//2013��9��6��20:04:41
//��Ҫ����JavaProjectManager��closeProject����
//�������������ڵ��ԵĹ��̱�ͻȻ�ر�ʱȡ����ʱ��������
//����BreakPanel����Ҫ��ʾUndrawBreakpoint
/**
 * 
 * ���ڵ����⣺
 * 1�����Ե�catch���󣬳����ͣ�ٺܾã����2�룬���ʱ�����ڶ�ʱ���������ڴ�����һ���߳��£�
 * ���������ʱ�����ʱ���������Ѿ��������ɴε���������������ͼ����ɳ�������������䱻�����Ĵ����
 * ʵ��ԭ����vm����ִ������Щ����û�й���
 * 
 * 
 * @author hou
 * 
 */
public class DebugProjectTimeAction extends BaseAction {

	/**
	 * ����ģʽ���
	 */
	private static final DebugProjectTimeAction debugProjectTimeAction = new DebugProjectTimeAction();
	private static final long serialVersionUID = 899272470936065890L;
	private static ConsoleCenter center = null;

	private Session session = null;
	private Timer timer = null;
	private boolean actived = false;  //��Ƕ�ʱ���Եĵ�ǰ״̬ true��ʾ����״̬
	private int delay = DebugTimeSelector.DEFAULT_VALUE; //��ʱ���Ե�ʱ����
	private List<String> spList = new ArrayList<String>(); //����������������µ�ȫ������+����
	private Set<String> classProcessed = new HashSet<String>(); //�Ѿ����UndrawBreakpoint�ĸñ����Թ����µ���
	private JavaProject currentProject = null; //���ڱ�ִ�ж�ʱ���ԵĹ���
	
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
//			û�д򿪵Ĺ���
		{
			return;
		}
		if (actived && (this.session == getSession(e)))
		// ��ǰsession�Ѿ����ڶ�ʱ����״̬ʱ���ظ������Ч
		{
			return;
		}
		

		this.session = getSession(e);
        System.out.println("99999999999999999999999999999999999999");
		timingDebug();
	}
	/**
	 * ���ݵ���������Ƿ��Ѿ����������е��µ���
	 */
	private void timingDebug()
	{
		if (!session.isActive() )
		// �������û����������Ҫ�������������һ�������������Զ�ִ�ж�ʱ��������
		{
			startVM();
		} 
		else 
		{
//			��ȡ��ǰ���ڵ��Ե���Ŀ�µ�ȫ���࣬���浽spList��,���ص��ǵ�ǰ�������е���
			int selectedFileIndex = sourcePaneGroup();
			
			vm = this.session.getVM();
			this.actived = true;
			
			
			addUndrawBreakpoints( spList.get(selectedFileIndex  ) );
			
			
			createClassPrepareRequest();
		}
	}
	/**
	 * �رն�ʱ����
	 */
	public void deactive()
	{
//		���õ�ǰ���ڱ����Ե���ĿΪnull
		this.currentProject = null;
		
//		ʹ�ڲ�״̬�ָ����ر�״̬���ɽ�����һ������
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
		
//		�����ȫ����ӵ�����ϵ㣬���򽫻��ڽ����ϱ�������
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
	 * ������ʱ��������
	 */
	public void startTask() {
		System.out.println( "startTask" );
		timer = new Timer();

		if (delay < 50)
		// ���ӳٹ��̣�������ܻ�ûִ������һ������ʱ���ͻᷢ���ڶ��������⽫�����׳��쳣
		{
			delay = 50;
		}
		timer.schedule(new TimingDebug(), delay, delay);
		actived = true;
	}
	/**
	 * ֹͣ��ʱ����
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
//        	����this��ӵģ���Ϊ���ó������е�������ȥ������Ҫֹͣ��ʱ����
        	return;
        }
//        �����û���ӵĶϵ�Ҫͣ����
        deactive();
        
	}
	/**
	 * ��������ص��ǵ��Թ����µ��࣬��������໹û�����UndrawBreapointʱ����Ӷϵ�
	 * @param e
	 */
	private void processClassPrepareEvent( Event e )
	{

		ClassPrepareEvent preparedClass = (ClassPrepareEvent) e;
		String preparedClassName = ( preparedClass.referenceType().name() );
		if ( spList.contains( preparedClassName ) 
				&& !classProcessed.contains( preparedClassName ) ) 
//			�ǵ�ǰ������Ŀ�µ��Զ����ಢ���ǻ�û�о��������
		{		
			addUndrawBreakpoints(preparedClassName);
		}

	}
	/**
	 * ���������ȡ�������ķ�������ÿ����ĵ�һ�д����һ���ϵ�
	 * @param preparedClassName Ҫ��Ӷϵ���࣬����+����
	 */
	private void addUndrawBreakpoints( String preparedClassName )
	{
		List<ReferenceType> classesByName = vm
				.classesByName(preparedClassName);
		if (classesByName == null || classesByName.size() == 0) {
//			����ͣ��ʱ����״̬�£�ֱ�Ӷ�ʱ���Բ�ͬ��Ŀʱ���ᷢ���������
			System.out.println("No class found");
			JOptionPane.showMessageDialog
			(null, "���ȹر��������ԻỰ", "", JOptionPane.WARNING_MESSAGE );
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
	 * ���������Թ�����ÿ����ļ���
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
//        	�ڵ��̳߳����У�����ζ�ų���������
        	System.out.println("catch Thread");
			
			deactive();
			return;
        }
//        if( ( ( e instanceof StepEvent ) 
//        		&& ( e.toString()).indexOf("java.lang.Throwable") > 0 )){
////        	����û�в�׽���쳣
//        	System.out.println("noCatch Exception");
//			
//			deactive();
//			return;
//        }
		if ((e instanceof VMDisconnectEvent) || (e instanceof VMDeathEvent)
				|| (e instanceof ExceptionEvent)) {
			// �����ض��¼������쳣��ֹͣ����
			deactive();
			return;
		}

	}


	private class TimingDebug extends TimerTask {

		/**
		 * ���������ִ�з������µ��߳����ĳЩ�����AnyviewJd�����������ڴ���ĳ���¼�ʱ��Ҫ�ܳ�ʱ�䣬
		 * ��εȴ���ʱ����Ѿ����������ɸ�Step��������ͼ��������������������
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
					// ��ʱ������һ�εĵ�����δ��������������
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
					// ��ʱ������һ�εĵ�����δ��������������
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
	 * ���ݵ�ǰ��������ȡ����������Ŀ���̣�
	 * ������ȡͬһ��Ŀ�µ�ȫ��Դ���ļ�
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
			
//			ȥ����.java��
			tmp = tmp.substring(0, tmp.length() - 5 );
			spList.add( tmp );
		}

		
		return selectedFile;
	}
	/**
	 * �ڵ�ǰ���ڷǵ���״̬ʱ�����������
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

		// ����������class�ļ���
		JVMArguments jvmArgs = new JVMArguments(jvmOptions + ' ' + mainClassName);
		PathManager pathman = (PathManager) session
				.getManager(PathManager.class);

		String classpath = "J:\\AnyviewJ\\AnyviewJPrj\\bin;"
				+ pathman.getClassPathAsString();

		jvmOptions = jvmArgs.normalizedOptions(classpath);

		// Could be getting a main class or jar file name here.
		mainClassName = jvmArgs.stuffAfterOptions();
		if (session.isActive()) {
			// Deactivate(ʹ��Ч) current session.
			session.deactivate(false, this);
			// session.close(session);
		}

		VMConnection connection = null;

		try {
//			hou 2013��8��29��17:02:27
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
				// we may resume(����) the debuggee VM.
				session.resumeVM(this, false, false);
			}
		} else {
//			����ʧ��
			this.actived = false;
		}
	} 

	public String choosingEntrance(BasicSession se) {
		sourcePaneGroup();
		
		if(entranceList.isEmpty())
		{
			String message = "δ�ҵ��κγ������!";
			String title = "����";
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
		Object xChooser = JOptionPane.showInputDialog(null, "��ѡ��������",
				"�������ѡ��", 0, null, a, a[temp]);
		
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
// * ��Ҫ�����ģ�
// * DebugTimeSelector��UncaughtException��pause��stop������VMEvent
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
// // ��ǰsession�Ѿ����ڶ�ʱ����״̬ʱ���ظ������Ч
// {
// return;
// }
//
// this.session = getSession(e);
//
// // �����һ�������ͻ�����¼���������Ҫ���������ע����Ҫ������VMEvent
// // ��ȡ������ʱ��Ӧ��ȡ��ȫ������
// listenVMEvents();
//
// if (!session.isActive())
// // �������û����������Ҫ�����������
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
// // ���ӳٹ��̣�������ܻ�ûִ������һ������ʱ���ͻᷢ���ڶ��������⽫�����׳��쳣
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
// // ȡ����������ļ���
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
// // �����ϵ�����쳣��ֹͣ����
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
// // ��ʱ������һ�εĵ�����δ��������������
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
// // ��ʱ������һ�εĵ�����δ��������������
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
// // �����򿪵��ļ����ܵ���
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
// // //û�а������������ļ����ܵ���
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
