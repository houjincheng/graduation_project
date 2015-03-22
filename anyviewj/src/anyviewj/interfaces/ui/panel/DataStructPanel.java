package anyviewj.interfaces.ui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JPanel;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.ArrayType;
import com.sun.jdi.Field;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.InconsistentDebugInfoException;
import com.sun.jdi.InvalidStackFrameException;
import com.sun.jdi.LocalVariable;
import com.sun.jdi.NativeMethodException;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.StackFrame;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.Value;

import anyviewj.console.ConsoleCenter;
import anyviewj.datastructs.drawer.BaseDrawer;
import anyviewj.datastructs.drawer.BinaryTreeDrawer;
import anyviewj.datastructs.drawer.LinkListDrawer;
import anyviewj.debug.manager.ContextManager;
import anyviewj.debug.session.Session;
import anyviewj.debug.session.event.SessionEvent;
import anyviewj.debug.source.event.ContextChangeEvent;
import anyviewj.debug.source.event.ContextListener;

public class DataStructPanel extends AbstractPanel implements ContextListener,
		MouseListener {

//	private JScrollPane uicomp;

	// ���ݽṹ�������
	private static final String[] DSTag = { "", "BinaryTree", "Graph",
			"HashTable", "Heap", "LList", "CList", "DuLList", "DuCList",
			"LStack", "VStack", "LQueue", "VQueue", "GList", "BiThreadTree",
			"CLTree", "LinearHashTable", "MGraph", "VList", "CSTree", "CTree",
			"GList2" };
	private static final Integer[] mode = { 1, 1, 5, 2, 6, 2, 7, 3, 8, 3, 2, 4,
			9, 5, 10, 5, 11, 5, 12, 5, 13, 6, 15, 7, 16, 8, 19, 9, 20, 11, 21,
			10 };
	/*
	 * lmk : mmMap:���ݽṹ ģʽ��MODE�� ������Ӧ�� �ֲ�����ģʽ��MODE��
	 * ��ӳ�䣬���ܣ�ͨ�����ݽṹ��ģʽ���������ֲ�������Ӧ�ĵ�ģʽ
	 */
	private Map<Integer, Integer> mmMap;
	// ���뻭ͼ�����
	private JTabbedPane tabbedPane;

	private List<ObjectReference> dsObjectReference;
	// �ֲ�����������Ӧֵ��ӳ��
	private Map<String, ObjectReference> lvMap;
	// �ֲ����������������ݽṹ��ӳ��
	private static Map<String, ObjectReference> ldMap;
	// lmk:ȫ�ֱ���������Ӧֵ��ӳ��
	private Map<String, ObjectReference> gvMap;
	// lmk:ȫ�ֱ��������������ݽṹ��ӳ��
	private Map<String, ObjectReference> gdMap;
	// ���ݽṹ����ͼ����ӳ��
	private static Map<ObjectReference, BaseDrawer> dsMap;

	// lmk:�ֲ�������������������ӳ��,����ldMap���н�
	private Map<String, ObjectReference> lcMap;

	// lmk:�ֲ�������������������ӳ��,����gdMap���н�
	private Map<String, ObjectReference> gcMap;

	// AlwaysMai����¼ǰһ���ľֲ�����������Ӧֵ��ӳ��
	private static Map<String, ObjectReference> beforeLvMap = new HashMap<String, ObjectReference>();
	// AlwaysMai����¼ǰһ���ľֲ����������������ݽṹ��ӳ��
	private static Map<String, ObjectReference> beforeLdMap = new HashMap<String, ObjectReference>();
	//
	private static Map<String, ObjectReference> traceMap = new HashMap<String, ObjectReference>();
	
	//zhang:(08-05) ����ʽ�˵�
    private JPopupMenu jPopupMenu1 = new JPopupMenu();
    //zhang:(08-05) ����ʽ�˵���
    private JMenuItem setting = new JMenuItem("����");
    //zhang:(08-05) ��ǰ����
    private BaseDrawer curbd ;

    //zhang: (08-06)����ǰ�Ĵ�С
    private int befsize = 0;
    //zhang:(08-06)�������������menu()�����ö��
    private int count = 0;
    //zhang:(08-07)�ж��Ƿ������ô�С�ĶԻ���
    private boolean isOpen = false;
    //zhang:(08-12)color name to color
    private static Map<String , Color> scMap = new HashMap<String, Color>();
    //zhang:(08-13)���͵����ֵ
    private int MaxSize = 50;
    //zhang:(08-13)���͵���Сֵ
    private int MinSize = 20;
    //zhang(08-13)Ĭ�ϴ�С
    private int sizeDef = MinSize;
    //zhang:(08-13)Ĭ��������ɫ
    private Color fcDef = new Color(0,0,0);//��ɫ
    //zhang��(08-13)Ĭ�Ͻ�����ɫ
    private Color ncDef = new Color(0,204,255);//��ɫ
    //zhang:(08-13)Ĭ�ϼ�ͷ����ɫ
    private Color ccDef = new Color(204,0,0);//��ɫ
    //zhang:(08-14)��ʱ��Ŵ�С
    private int size = sizeDef;
     //zhang��(08-06)�û����õĴ�С
    public int cursize = MinSize;////
    //zhang:(08-14)��ʱ����������ɫ
    private Color fc = new Color(0,0,0);
    //zhang:(08-14)��ʱ��Ž�����ɫ
    private Color nc = new Color(0,204,255);
    //zhang:(08-14)��ʱ��ż�ͷ����ɫ
    private Color cc = new Color(204,0,0);
    //zhang:(08-14)������յĴ�С
    private int sizeF = size;
    //zhang:(08-14)������յ�������ɫ
    private Color fcF = fc;
    //zhang:(08-14)������յĽ����ɫ
    private Color ncF = nc;
    //zhang:(08-14)������յļ�ͷ��ɫ
    private Color ccF = cc;
    //zhang:(08-13)���ô���
    private JDialog window = new JDialog();
    //zhang:(08-13)����
    private JSlider s;
      //wcb:(20121129)
   // private SentData d;
//    private VariableTraceAction f;
    //
    private JMenuItem varShow = new JMenuItem("����");
    private JDialog varwindow = new JDialog(); 
    
    
	public DataStructPanel() {

		// uicomp.setLayout(new BorderLayout());

		// ��ʼ��
		tabbedPane = new JTabbedPane();
//		uicomp = new JScrollPane(tabbedPane);
		dsObjectReference = new LinkedList<ObjectReference>();
		mmMap = new HashMap<Integer, Integer>(); /* lmk: mmMap��ʼ�� */
		for (int i = 0; i < mode.length / 2; i++)
			mmMap.put(mode[i * 2], mode[i * 2 + 1]);
		lvMap = new HashMap<String, ObjectReference>();
		ldMap = new HashMap<String, ObjectReference>();
		gvMap = new HashMap<String, ObjectReference>();
		gdMap = new HashMap<String, ObjectReference>();
		dsMap = new HashMap<ObjectReference, BaseDrawer>();
		lcMap = new HashMap<String, ObjectReference>(); // lmk
		gcMap = new HashMap<String, ObjectReference>(); // lmk
		
		menuinits();
		// wcb:(20121129)
//		 f = new VariableTraceAction();
	}
	
	
	public void menuinits(){
		jPopupMenu1.add(setting);
		window.add(setting());
        setting.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("����");
//                Image image = new ImageIcon(com.bluemarsh.jswat.ui.views.DataStructView.class.getResource("debug_option.gif")).getImage();
//                window.setIconImage(image);
                window.setTitle("����");
                
                window.setSize(400, 300);
                window.setResizable(false);
                window.setAlwaysOnTop(true);
                window.setVisible(true);
                window.setLocationRelativeTo(null);//zhang:(08-13)���þ���
            }
        });
        
        jPopupMenu1.add(varShow);
        varwindow.add(settingvar());
        varShow.addActionListener(new ActionListener(){
        	@Override
			public void actionPerformed(ActionEvent e){
        		varwindow.setTitle("���ٱ���");
        		
        		varwindow.setSize(400, 300);
                varwindow.setResizable(false);
                varwindow.setAlwaysOnTop(true);
                varwindow.setVisible(true);
                varwindow.setLocationRelativeTo(null);//zhang:(08-13)���þ���
        		
                Set<String> s = lvMap.keySet();
                String[] a; 
                a = new String[s.size()];
                s.toArray(a);
                jlist.setListData(a);
        	}
        });
	}
	public void menu(){
        
        ((JPanel)curbd).addMouseListener(new MouseAdapter(){
            public void mousePreesed(MouseEvent e){
                showPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e){
                showPopup(e);
            }
        });
    }
	
	private void showPopup(java.awt.event.MouseEvent evt){
        if(evt.isPopupTrigger()){
            jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }
	private Object[] selected;
	private JList jlist;
	private JPanel varpanel;
	public JPanel settingvar(){
		if(varpanel != null) return varpanel;
		varpanel = new JPanel();
		jlist = new JList();
//		JComboBox jb = new JComboBox();
//		ListModel lm = jlist.getModel();
//		DefaultListModel dlm;
//		if(lm != null) dlm = (DefaultListModel) lm;
//		else{
//			dlm = new DefaultListModel();
//		}
//		dlm.clear();
//		for(Object sa1:sa){
//			dlm.addElement(sa1);
//		}
//		jlist.setModel(dlm);
		
		varpanel.setLayout(new BorderLayout());
		varpanel.add(new JScrollPane(jlist), BorderLayout.CENTER);
		
		JButton b1 = new JButton("ȷ��");
		varpanel.add(b1, BorderLayout.EAST);

		b1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
//            	@SuppressWarnings("unused")
//				boolean b = jlist.isSelectionEmpty();
                Object[] a= DataStructPanel.this.jlist.getSelectedValues();
            	selected = a;
//                DataStructPanel.this.selected ;
                
                DataStructPanel.this.sendDSData();
                varwindow.setVisible(false);
//                DataStructPanel.this.varwindow.dispose();
            }
        });
		
		return varpanel;
	}
	
	public JTabbedPane setting(){
        JTabbedPane tabPane = new JTabbedPane();
        tabPane.setTabPlacement(SwingConstants.TOP);

        //zhang:(08-13)ѡ���������ɫ
        JPanel setColor = new JPanel();
        setColor.setLayout(new GridLayout(4,0));

        //zhang:(08-12)�й���ɫ������
        Color blue = new Color(51,51,255);
        Color red = new Color(255,51,51);
        Color orange = new Color(255,153,51);
        Color yellow = new Color(255,204,0);
        //zhang:(08-12)����ɫ�����ֺ���ɫ��ӵ�ӳ����
        scMap.put("Blue", blue);
        scMap.put("Red", red);
        scMap.put("Orange", orange);
        scMap.put("Yellow", yellow);
        Set<String>keys = scMap.keySet();
        JPanel setFontColor = new JPanel();
        JComboBox fontColor = new JComboBox();
        //zhang:(08-12)�˴����������ɫѡ��
        for(String key : keys)
            fontColor.addItem(key);
        setFontColor.add(new JLabel("������ɫ��    "));
        setFontColor.add(fontColor);

        JPanel setNodeColor = new JPanel();
        JComboBox nodeColor = new JComboBox();
        //zhang:(08-12)�˴���ӽ����ɫѡ��
        for(String key : keys)
            nodeColor.addItem(key);
        setNodeColor.add(new JLabel("�����ɫ��    "));
        setNodeColor.add(nodeColor);

        JPanel setCorrowColor = new JPanel();
        JComboBox corrowColor = new JComboBox();
        //zhang:(08-12)�˴���Ӽ�ͷ��ɫѡ��;
        for(String key : keys)
            corrowColor.addItem(key);
        setCorrowColor.add(new JLabel("��ͷ��ɫ��    "));
        setCorrowColor.add(corrowColor);

        JPanel jbutts = new JPanel();
        JButton ok = new JButton("ȷ��");
        JButton cancel = new JButton("ȡ��");
        JButton res = new JButton("�ָ�Ĭ������");
        jbutts.add(res);
        jbutts.add(ok);
        jbutts.add(cancel);

        setColor.add(setFontColor);
        setColor.add(setNodeColor);
        setColor.add(setCorrowColor);
        setColor.add(jbutts);

        //zhang:(08-13)ѡ������ô�С
        JPanel setSize = new JPanel();
        setSize.setLayout(new GridLayout(3,0));

        JPanel sli = new JPanel();

        if(befsize == 0)
            s = new JSlider(SwingConstants.HORIZONTAL,MinSize,MaxSize,MinSize);
        else
            s = new JSlider(SwingConstants.HORIZONTAL,MinSize,MaxSize,befsize);
        s.setPaintLabels(true);
        s.setPaintTicks(true);
        s.setMajorTickSpacing(3);
        s.setLabelTable(s.createStandardLabels(3,MinSize));
        sli.add(new JLabel("�� �� �� С��"));
        sli.add(s);

        JPanel labs = new JPanel();
        final JLabel v = new JLabel();
        if(befsize == 0)
            v.setText(Integer.toString(MinSize));
        else
            v.setText(Integer.toString(befsize));
        labs.add(new JLabel("��ǰ�Ĵ�С��"));
        labs.add(v);

        JPanel buts = new JPanel();
        JButton re  = new JButton("�ָ�Ĭ������");
        JButton OK = new JButton("ȷ��");
        JButton Cancel = new JButton("ȡ��");
        buts.add(re);
        buts.add(OK);
        buts.add(Cancel);

        setSize.add(sli);
        setSize.add(labs);
        setSize.add(buts);

        tabPane.addTab("��ɫ", setColor);
        tabPane.addTab("��С", setSize);

        fontColor.addItemListener(new ItemListener(){
            @Override
			public void itemStateChanged(ItemEvent evt){
                if(evt.getStateChange() == ItemEvent.SELECTED){
                    try{
                        String s = evt.getItem().toString();
                        System.out.println("������ɫ��" + s);
                        fc = scMap.get(s);
                        fcF=fc;/////////////////
                    }catch(Exception e){

                    }
                }
            }
        });

        nodeColor.addItemListener(new ItemListener(){
            @Override
			public void itemStateChanged(ItemEvent evt){
                if(evt.getStateChange() == ItemEvent.SELECTED){
                    try{
                        String s = evt.getItem().toString();
                        System.out.println("�����ɫ��" + s);
                        nc = scMap.get(s);
                        ncF = nc;
                    }catch(Exception e){

                    }
                }
            }
        });

        corrowColor.addItemListener(new ItemListener(){
            @Override
			public void itemStateChanged(ItemEvent evt){
                if(evt.getStateChange() == ItemEvent.SELECTED){
                    try{
                        String s = evt.getItem().toString();
                        System.out.println("��ͷ��ɫ��" + s);
                        cc = scMap.get(s);
                        ccF = cc;
                    }catch(Exception e){

                    }
                }
            }
        });

        s.addChangeListener(new ChangeListener(){

            @Override
            public void stateChanged(ChangeEvent e) {
               s = (JSlider) e.getSource();
               if(!s.getValueIsAdjusting()){
                    cursize = s.getValue();
                    v.setText(Integer.toString(cursize));
               }
            }
        });

        ok.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                sizeF = cursize;
                ncF = nc;
                ccF = cc;
                fcF = fc;
                sendDSData();
                window.dispose();
            }
        });

        cancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {

                window.dispose();
            }
        });

        res.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                sizeF = sizeDef;
                ncF = ncDef;
                ccF = ccDef;
                fcF = fcDef;
                sendDSData();////ˢ�����wcb:2012915
                window.dispose();
            }
        });

        re.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                sizeF = sizeDef;
                ncF = ncDef;
                ccF = ccDef;
                fcF = fcDef;
                sendDSData();
                window.dispose();
            }
        });

        OK.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                sizeF = cursize;
                ncF = nc;
                ccF = cc;
                fcF = fc;
                sendDSData();
                //setTabbedPane();
                window.dispose();
            }
        });

        Cancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
            }
        });

        return tabPane;
    }

	@Override
	public void setSession(Session e) {
		owningSession = e;
		owningSession.addListener(this);
	}

	@Override
	public void activated(SessionEvent sevt) {
		// Add ourselves as a context change listener.
		ContextManager ctxtMgr = (ContextManager) sevt.getSession().getManager(
				ContextManager.class);
		ctxtMgr.addContextListener(this);
		refreshLater();
	}

	@Override
	public void deactivated(SessionEvent sevt) {
		// Remove ourselves as a context change listener.
		ContextManager ctxtMgr = (ContextManager) sevt.getSession().getManager(
				ContextManager.class);
		ctxtMgr.removeContextListener(this);
		// setMessage(null);
		dsObjectReference.clear();
		tabbedPane.removeAll();
		selected = null;
	}

	@Override
	public void contextChanged(ContextChangeEvent cce) {
		if (!cce.isBrief()) {
			// Not a brief event, refresh the display.
			refreshLater();
		}
	}

	@Override
	public void refresh(Session session) {
		// Get the list of visible local variables.

		findObjectReference();
		// �������
		// setTabbedPane();
		sendDSData();
//		this.menu();

		
	}

	public void findObjectReference() {
		// SessionManager sm = SessionProvider.getSessionManager();
		// Session session = sm.getCurrent();

		// if (session.isConnected()) {
		// DebuggingContext dc = ContextProvider.getContext(session);

		Session session = ConsoleCenter.getCurrentSession();
		ContextManager cm = (ContextManager) session
				.getManager(ContextManager.class);

		final ThreadReference thread = cm.getCurrentThread();
		if (thread != null) {
			try {
				StackFrame frame = cm.getCurrentStackFrame();
				if (frame.location().codeIndex() == -1) {
					throw new NativeMethodException("work around JPDA bug");
				}
				ReferenceType clazz = frame.location().declaringType();
				ObjectReference thisObj = frame.thisObject();

				// Build the set of visible variables, starting with fields.
				if (thisObj != null) {
					// This takes in all of the fields, static and instance.
					// ����this�����е����ݽṹ����
					findDSObjectReference(thisObj);
					// lmk:��this�����������ݽṹ�е�ȫ�ֱ���
					findGlobalVar(thisObj);
					// ���Ҫ���ľֲ�����
					if (thisObj != null
							&& DSObjectReferenceChecker
									.getObjectReferenceType(thisObj) == 0) {
						System.out.println("---clear local variables.");
						// AlwaysMai:���ʱ������ͬʱ���drawer�ľֲ�����nbMap
						Set<String> keys = ldMap.keySet();
						for (String key : keys) {
							ObjectReference or = ldMap.get(key);// ��ȡ�����������ݽṹ
							BaseDrawer bd = dsMap.get(or);
							bd.clearNbMap();// ���nbMap
							bd.clearLocalVar(); // lmk
							bd.clearBeforeNbMap();
						}
						// AlwaysMai:�˴�Ϊ�˳��оֲ��������������������View�����
						ldMap.clear();
						lvMap.clear();
					}
				} else {
					// Must be in a static method, so show the static fields.
					List<Field> fields = clazz.visibleFields();
					for (Field field : fields) {
						if (field.isStatic()) {
							Value value = clazz.getValue(field);
						}
					}
				}

				// Now collect the visible local variables.
				// ���Ҿֲ������е����ݽṹ����

				List<LocalVariable> locals = frame.visibleVariables();
				// AlwaysMai:�ݹ����ʱҲ��գ�
				System.out.println("---clear variable while recursing.");
				// AlwaysMai:���ʱ������ͬʱ���drawer�ľֲ�����nbMap
				Set<String> keys = ldMap.keySet();
				for (String key : keys) {
					ObjectReference or = ldMap.get(key);// ��ȡ�����������ݽṹ
					BaseDrawer bd = dsMap.get(or);
					bd.clearNbMap();// ���nbMap
					// bd.clearBeforeNbMap();
				} // AlwaysMai:��ΪDrawerÿ��PaintChildren���Զ����
					// AlwaysMai:�����Զ����ʱ�����ֻ�δ�ı����ݽṹ���ػ��Ŀ��ܣ����±�����ʧ
				ldMap.clear();
				lvMap.clear();

				for (LocalVariable local : locals) {
					Value value = frame.getValue(local);
					if (value == null) {// AlwaysMai:���Ա���ֵΪ�յ����
						System.out.println("---The value of " + local.name()
								+ " is null");
						ObjectReference or = beforeLdMap.get(local.name());// ��ȡ�����������ݽṹ
						if (or != null) {

							ObjectReference beforeValue = beforeLvMap.get(local
									.name());
							if (beforeValue != null) {
								ldMap.put(local.name(), or);
								lvMap.put(local.name(), null);
							}
						}
						// AlwaysMai:��λ�ȡ��������������
						// AlwaysMai:ֵΪ��ʱ������ʹ��local.type����ȡ��������
					}

					if (value instanceof ObjectReference) {
						ObjectReference or = (ObjectReference) value;
						// ���this�����������ݽṹ���ã���Ĭ���������Ҫ�����õľֲ�����
						// if (thisObj != null &&
						// DSObjectReferenceChecker.getObjectReferenceType(thisObj)
						// > 0) {
						// ����������ͬ�������Ҳ��δ����
						if (LVObjectReferenceChecker.getObjectReferenceType(or) > 0) {
							lcMap.put(local.name(), thisObj); // lmk:�Ժ������ж�null�����
							lvMap.put(local.name(), or);
							// ��������Ϊ�յ������δ����
							// }
						} else {
							findDSObjectReference(or);
						}
					}// end ObjectReference
				}// end for localvariable
					// AlwaysMai:����������ᣬ�Ѿ����Ϊ�յľֲ����������
				System.out.println("---the number of DS references : "
						+ dsObjectReference.size());
				System.out
						.println("---the number of local variable references : "
								+ lvMap.size());
				int size = lvMap.size();
				if (size > 0) {
					Set<String> key = lvMap.keySet();
					String[] name = new String[size];
					key.toArray(name);
					for (int i = 0; i < size; i++) {
						if (lvMap.get(name[i]) != null) {
							beforeLvMap.put(name[i], lvMap.get(name[i]));
						} else {
							System.out.println("---the key of lvMap is null: "
									+ name[i]);
						}
					}
				}
				beforeLdMap.putAll(ldMap);

				// Catch this here so we at least capture the available
				// fields of this class, despite missing local variables.
				// System.out.println(NbBundle.getMessage(
				// VariablesView.class, "EXC_AbsentInformation") +
				// NbBundle.getMessage(VariablesView.class,
				// "TIP_AbsentInformation"));
				// // Get the argument values, even without their names.
				// List<Value> arguments = frame.getArgumentValues();
				// int count = 1;
				// String prefix = NbBundle.getMessage(VariablesView.class,
				// "LBL_VariablesView_Argument");
				// for (Value arg : arguments) {
				// count++;
				// }

			} catch (IncompatibleThreadStateException itse) {
				// System.out.println(NbBundle.getMessage(VariablesView.class,
				// "EXC_ThreadNotSuspended"));
			} catch (InconsistentDebugInfoException idie) {
				// System.out.println(NbBundle.getMessage(VariablesView.class,
				// "EXC_InconsistentInfo"));
			} catch (IndexOutOfBoundsException ioobe) {
				// System.out.println(NbBundle.getMessage(VariablesView.class,
				// "EXC_ThreadNotStarted"));
			} catch (InvalidStackFrameException isfe) {
				// System.out.println(NbBundle.getMessage(VariablesView.class,
				// "EXC_InvalidStackFrame"));
			} catch (NativeMethodException nme) {
				// System.out.println(NbBundle.getMessage(VariablesView.class,
				// "EXC_NativeMethod"));
			} catch (VMDisconnectedException vmde) {
				// This happens often, nothing we can do.
			} catch (AbsentInformationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// Must expand the nodes on the AWT event thread.
	}

	public void sendDSData() {
		// ����Ҫ�������ݽṹ
		System.out.println("where");

		for (ObjectReference or : dsObjectReference) {
			BaseDrawer bd = dsMap.get(or);
			System.out.println("()()()()())()( " + bd);
			curbd = bd;
			this.menu();
			System.out.println("Current DS object : " + or.toString());
			bd.RecevieData(or);
			bd.GetChoice(fcF, ncF, ccF, sizeF);
			
			
			if(selected != null){
				for(Object key: selected){
					bd.RecevieData((String)key, lvMap.get(key));
				}
			}
//			for(String key: lvMap.keySet()){
//				bd.RecevieData(key, lvMap.get(key));
//			}
			
			tabbedPane.repaint();
		}
		// wcb(20121129)��ջ�ֲ�����������Ӧ
		// d.sentData();

		this.setLdMap();

		Set<String> keys = ldMap.keySet(); /* ���ݾֲ��������������� */
		System.out.println("���еľֲ�������" + keys);
		for (String key : keys) {
			ObjectReference or = ldMap.get(key);// ��ȡ�����������ݽṹ
			BaseDrawer bd = dsMap.get(or);
			// wcb:(20121130)��ջ�ֲ�������Ӧ
//			f.setDrawer(bd);
//			f.sentData();
//			bd.RecevieData(key, lvMap.get(key));
			// wcb:(��ȡ���)20121130
			// d.setDrawer(bd);
			// �ֲ�����
			// bd.RecevieData(key, lvMap.get(key));//����Ҫ���ı������ƺͱ���ֵ
			// d.sentData();
			tabbedPane.repaint();// AlwaysMai:0706
		}

		this.setGdMap();

		Set<String> keys_g = gdMap.keySet(); /* ����ȫ�ֱ������������� */
		for (String key : keys_g) {
			ObjectReference or = gdMap.get(key);// ��ȡ�����������ݽṹ
			BaseDrawer bd = dsMap.get(or);
			bd.RecevieData(key, gvMap.get(key));// ����Ҫ���ı������ƺͱ���ֵ
			tabbedPane.repaint();
		}
	}
	
	/*lmk:����lcMap����ldMap*/
    private void setLdMap(){
         int i;
         Set<String> keys = lvMap.keySet();
         for(String key:keys){
             ObjectReference or = lvMap.get(key);
             for(i=0; i<dsObjectReference.size(); i++){
                 int type_ds = DSObjectReferenceChecker.getObjectReferenceType(dsObjectReference.get(i));
                 int type_lv = mmMap.get(type_ds);
                 if(LVObjectReferenceChecker.getObjectReferenceType(or) == type_lv){
                    ldMap.put(key, dsObjectReference.get(i));
                 }
             }
         }
    }
    
//    private void setLdMap1(){
//    	int i;
//        Set<String> keys = lvMap.keySet();
//        for(String key:keys){
//            ObjectReference or = lvMap.get(key);
//            for(i=0; i<dsObjectReference.size(); i++){
//                int type_ds = DSObjectReferenceChecker.getObjectReferenceType(dsObjectReference.get(i));
//                int type_lv = mmMap.get(type_ds);
//                if(LVObjectReferenceChecker.getObjectReferenceType(or) == type_lv){
//                   ldMap.put(key, dsObjectReference.get(i));
//                }
//            }
//        }
//    }

    /*lmk:����lcMap����ldMap*/
    private void setGdMap(){
         int i;
         Set<String> keys = gvMap.keySet();
         for(String key:keys){
             ObjectReference or = gvMap.get(key);
             for(i=0; i<dsObjectReference.size(); i++){
                 int type_ds = DSObjectReferenceChecker.getObjectReferenceType(dsObjectReference.get(i));
                 int type_lv = mmMap.get(type_ds);
                 if(LVObjectReferenceChecker.getObjectReferenceType(or) == type_lv){
                    gdMap.put(key, dsObjectReference.get(i));
                 }
             }
         }
    }

	public void findDSObjectReference(ObjectReference or) {
		// �����ö���������
		if (or.referenceType() instanceof ArrayType) {
			return;
		}
		// �����ö�����ĳ�����ݽṹ���ݽṹ
		if (DSObjectReferenceChecker.getObjectReferenceType(or) > 0) {
			// �����ݽṹ���ö��󲻴��ڣ��ż���
			System.out.println("one one");
			if (!this.dsObjectReference.contains(or)) {
				// ���µ����ݽṹ���ö�������ȥ
				System.out.println("this where");
				dsObjectReference.add(or);
				// �������ݽṹ��Ӧ�����
				addTabbedPane(or);
			}
			return;
		}

		ReferenceType clazz = or.referenceType();
		List<Field> fields = clazz.allFields();

		for (Field field : fields) {
			Value value = or.getValue(field);
			// System.out.println("�ֶ���:"+field.name()+"  �ֶ�������:"+field.typeName());
			if (value instanceof ObjectReference) {
				ObjectReference or1 = (ObjectReference) value;
				if (or1.referenceType() instanceof ArrayType) {
					return;
				} else {
					findDSObjectReference(or1); /* �ݹ�������ݽṹ */
				}
			}
		}// end for

	}

	public void addTabbedPane(ObjectReference or) {
		int type = DSObjectReferenceChecker.getObjectReferenceType(or);
		// System.out.println("%%%%%%%%%%%%%%%%66666666666666666666666666666666666666666666666"+type);
		// BaseDrawer bd = new BinaryTreeDrawer();
		BaseDrawer bd = new LinkListDrawer();
		switch (type) {
		case 0:
			// bd = new GListDrawer();
			break;
		case 1:
			 bd = new BinaryTreeDrawer();
			break;
		case 2:
			// bd = new GraphDrawer();
			break;
		case 3:
			// bd = new HashTableDrawer();
			break;
		// case 4: bd = new HeapDrawer(); break;
		case 5:
		case 6:
		case 7:
		case 8:
			bd = new LinkListDrawer();
			break;
		case 9:
		case 10:
		case 11:
		case 12:
			// bd = new StaQueDrawer();
			break;
		case 13:
			// bd = new GListDrawer();
			break;
		case 14:
			// bd = new BiThreadTreeDrawer();
			if (bd == null)
				System.out.println("��  bd == null������������������������������");
			break;
		case 15:
			// bd = new CLTreeDrawer();
			break;
		case 16:
			// bd = new LinearHashTableDrawer();
			break;
		case 17:
			// bd = new MGraphDrawer();
			break;
		case 18:
			// bd = new VListDrawer();
			break;
		case 19:
			// bd = new CSTreeDrawer();
			break;
		case 20:
			// bd = new CTreeDrawer();
			break;
		case 21:
			// bd = new GListDrawer();
			break;
		}
		JScrollPane scrollPane = new JScrollPane((JPanel) bd,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		tabbedPane.add(
				DSTag[DSObjectReferenceChecker.getObjectReferenceType(or)],
				scrollPane);
		dsMap.put(or, bd);
	}

	public void findGlobalVar(ObjectReference or) {
		/* lmk: �ж�or1�Ƿ�Ϊȫ�ֱ���,���ж����������ݽṹ��ȫ�ֱ��� */
		ReferenceType clazz = or.referenceType();
		List<Field> fields = clazz.allFields();

		for (Field field : fields) {
			Value value = or.getValue(field);
			// System.out.println("�ֶ���:"+field.name()+"  �ֶ�������:"+field.typeName());
			if (value instanceof ObjectReference) {
				ObjectReference or1 = (ObjectReference) value;
				if (or1.referenceType() instanceof ArrayType) {
					return;
				} else
				// ����������ͬ�������Ҳ��δ����
				if (LVObjectReferenceChecker.getObjectReferenceType(or1) > 0) {
					gcMap.put(field.name(), or);
					gvMap.put(field.name(), or1);
				} else {
					findGlobalVar(or1);
				}

			}
		}
	}

	@Override
	public JComponent getUI() {
		return tabbedPane;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
