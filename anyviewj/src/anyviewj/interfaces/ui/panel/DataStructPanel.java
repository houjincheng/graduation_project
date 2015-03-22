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

	// 数据结构的面板标记
	private static final String[] DSTag = { "", "BinaryTree", "Graph",
			"HashTable", "Heap", "LList", "CList", "DuLList", "DuCList",
			"LStack", "VStack", "LQueue", "VQueue", "GList", "BiThreadTree",
			"CLTree", "LinearHashTable", "MGraph", "VList", "CSTree", "CTree",
			"GList2" };
	private static final Integer[] mode = { 1, 1, 5, 2, 6, 2, 7, 3, 8, 3, 2, 4,
			9, 5, 10, 5, 11, 5, 12, 5, 13, 6, 15, 7, 16, 8, 19, 9, 20, 11, 21,
			10 };
	/*
	 * lmk : mmMap:数据结构 模式（MODE） 到所对应的 局部变量模式（MODE）
	 * 的映射，功能：通过数据结构的模式查找其所局部变量对应的的模式
	 */
	private Map<Integer, Integer> mmMap;
	// 加入画图的面板
	private JTabbedPane tabbedPane;

	private List<ObjectReference> dsObjectReference;
	// 局部变量到它对应值的映射
	private Map<String, ObjectReference> lvMap;
	// 局部变量到它所属数据结构的映射
	private static Map<String, ObjectReference> ldMap;
	// lmk:全局变量到它对应值的映射
	private Map<String, ObjectReference> gvMap;
	// lmk:全局变量到它所属数据结构的映射
	private Map<String, ObjectReference> gdMap;
	// 数据结构到画图面板的映射
	private static Map<ObjectReference, BaseDrawer> dsMap;

	// lmk:局部变量到它所属类对象的映射,构造ldMap的中介
	private Map<String, ObjectReference> lcMap;

	// lmk:局部变量到它所属类对象的映射,构造gdMap的中介
	private Map<String, ObjectReference> gcMap;

	// AlwaysMai：记录前一步的局部变量到它对应值的映射
	private static Map<String, ObjectReference> beforeLvMap = new HashMap<String, ObjectReference>();
	// AlwaysMai：记录前一步的局部变量到它所属数据结构的映射
	private static Map<String, ObjectReference> beforeLdMap = new HashMap<String, ObjectReference>();
	//
	private static Map<String, ObjectReference> traceMap = new HashMap<String, ObjectReference>();
	
	//zhang:(08-05) 弹出式菜单
    private JPopupMenu jPopupMenu1 = new JPopupMenu();
    //zhang:(08-05) 弹出式菜单项
    private JMenuItem setting = new JMenuItem("设置");
    //zhang:(08-05) 当前画板
    private BaseDrawer curbd ;

    //zhang: (08-06)设置前的大小
    private int befsize = 0;
    //zhang:(08-06)计算次数，避免menu()被调用多次
    private int count = 0;
    //zhang:(08-07)判断是否开启设置大小的对话框
    private boolean isOpen = false;
    //zhang:(08-12)color name to color
    private static Map<String , Color> scMap = new HashMap<String, Color>();
    //zhang:(08-13)滑竿的最大值
    private int MaxSize = 50;
    //zhang:(08-13)滑竿的最小值
    private int MinSize = 20;
    //zhang(08-13)默认大小
    private int sizeDef = MinSize;
    //zhang:(08-13)默认字体颜色
    private Color fcDef = new Color(0,0,0);//黑色
    //zhang：(08-13)默认结点的颜色
    private Color ncDef = new Color(0,204,255);//蓝色
    //zhang:(08-13)默认箭头的颜色
    private Color ccDef = new Color(204,0,0);//红色
    //zhang:(08-14)临时存放大小
    private int size = sizeDef;
     //zhang：(08-06)用户设置的大小
    public int cursize = MinSize;////
    //zhang:(08-14)临时存放字体的颜色
    private Color fc = new Color(0,0,0);
    //zhang:(08-14)临时存放结点的颜色
    private Color nc = new Color(0,204,255);
    //zhang:(08-14)临时存放箭头的颜色
    private Color cc = new Color(204,0,0);
    //zhang:(08-14)存放最终的大小
    private int sizeF = size;
    //zhang:(08-14)存放最终的字体颜色
    private Color fcF = fc;
    //zhang:(08-14)存放最终的结点颜色
    private Color ncF = nc;
    //zhang:(08-14)存放最终的箭头颜色
    private Color ccF = cc;
    //zhang:(08-13)设置窗口
    private JDialog window = new JDialog();
    //zhang:(08-13)滑竿
    private JSlider s;
      //wcb:(20121129)
   // private SentData d;
//    private VariableTraceAction f;
    //
    private JMenuItem varShow = new JMenuItem("变量");
    private JDialog varwindow = new JDialog(); 
    
    
	public DataStructPanel() {

		// uicomp.setLayout(new BorderLayout());

		// 初始化
		tabbedPane = new JTabbedPane();
//		uicomp = new JScrollPane(tabbedPane);
		dsObjectReference = new LinkedList<ObjectReference>();
		mmMap = new HashMap<Integer, Integer>(); /* lmk: mmMap初始化 */
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
                System.out.println("设置");
//                Image image = new ImageIcon(com.bluemarsh.jswat.ui.views.DataStructView.class.getResource("debug_option.gif")).getImage();
//                window.setIconImage(image);
                window.setTitle("设置");
                
                window.setSize(400, 300);
                window.setResizable(false);
                window.setAlwaysOnTop(true);
                window.setVisible(true);
                window.setLocationRelativeTo(null);//zhang:(08-13)设置居中
            }
        });
        
        jPopupMenu1.add(varShow);
        varwindow.add(settingvar());
        varShow.addActionListener(new ActionListener(){
        	@Override
			public void actionPerformed(ActionEvent e){
        		varwindow.setTitle("跟踪变量");
        		
        		varwindow.setSize(400, 300);
                varwindow.setResizable(false);
                varwindow.setAlwaysOnTop(true);
                varwindow.setVisible(true);
                varwindow.setLocationRelativeTo(null);//zhang:(08-13)设置居中
        		
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
		
		JButton b1 = new JButton("确定");
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

        //zhang:(08-13)选项卡：设置颜色
        JPanel setColor = new JPanel();
        setColor.setLayout(new GridLayout(4,0));

        //zhang:(08-12)有关颜色的设置
        Color blue = new Color(51,51,255);
        Color red = new Color(255,51,51);
        Color orange = new Color(255,153,51);
        Color yellow = new Color(255,204,0);
        //zhang:(08-12)将颜色的名字和颜色添加到映射中
        scMap.put("Blue", blue);
        scMap.put("Red", red);
        scMap.put("Orange", orange);
        scMap.put("Yellow", yellow);
        Set<String>keys = scMap.keySet();
        JPanel setFontColor = new JPanel();
        JComboBox fontColor = new JComboBox();
        //zhang:(08-12)此处添加字体颜色选项
        for(String key : keys)
            fontColor.addItem(key);
        setFontColor.add(new JLabel("字体颜色：    "));
        setFontColor.add(fontColor);

        JPanel setNodeColor = new JPanel();
        JComboBox nodeColor = new JComboBox();
        //zhang:(08-12)此处添加结点颜色选项
        for(String key : keys)
            nodeColor.addItem(key);
        setNodeColor.add(new JLabel("结点颜色：    "));
        setNodeColor.add(nodeColor);

        JPanel setCorrowColor = new JPanel();
        JComboBox corrowColor = new JComboBox();
        //zhang:(08-12)此处添加箭头颜色选项;
        for(String key : keys)
            corrowColor.addItem(key);
        setCorrowColor.add(new JLabel("箭头颜色：    "));
        setCorrowColor.add(corrowColor);

        JPanel jbutts = new JPanel();
        JButton ok = new JButton("确定");
        JButton cancel = new JButton("取消");
        JButton res = new JButton("恢复默认设置");
        jbutts.add(res);
        jbutts.add(ok);
        jbutts.add(cancel);

        setColor.add(setFontColor);
        setColor.add(setNodeColor);
        setColor.add(setCorrowColor);
        setColor.add(jbutts);

        //zhang:(08-13)选项卡：设置大小
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
        sli.add(new JLabel("设 置 大 小："));
        sli.add(s);

        JPanel labs = new JPanel();
        final JLabel v = new JLabel();
        if(befsize == 0)
            v.setText(Integer.toString(MinSize));
        else
            v.setText(Integer.toString(befsize));
        labs.add(new JLabel("当前的大小："));
        labs.add(v);

        JPanel buts = new JPanel();
        JButton re  = new JButton("恢复默认设置");
        JButton OK = new JButton("确定");
        JButton Cancel = new JButton("取消");
        buts.add(re);
        buts.add(OK);
        buts.add(Cancel);

        setSize.add(sli);
        setSize.add(labs);
        setSize.add(buts);

        tabPane.addTab("颜色", setColor);
        tabPane.addTab("大小", setSize);

        fontColor.addItemListener(new ItemListener(){
            @Override
			public void itemStateChanged(ItemEvent evt){
                if(evt.getStateChange() == ItemEvent.SELECTED){
                    try{
                        String s = evt.getItem().toString();
                        System.out.println("字体颜色：" + s);
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
                        System.out.println("结点颜色：" + s);
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
                        System.out.println("箭头颜色：" + s);
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
                sendDSData();////刷新面板wcb:2012915
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
		// 设置面板
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
					// 查找this变量中的数据结构引用
					findDSObjectReference(thisObj);
					// lmk:从this引用中找数据结构中的全局变量
					findGlobalVar(thisObj);
					// 清空要画的局部变量
					if (thisObj != null
							&& DSObjectReferenceChecker
									.getObjectReferenceType(thisObj) == 0) {
						System.out.println("---clear local variables.");
						// AlwaysMai:清空时，必须同时清空drawer的局部变量nbMap
						Set<String> keys = ldMap.keySet();
						for (String key : keys) {
							ObjectReference or = ldMap.get(key);// 获取它所属的数据结构
							BaseDrawer bd = dsMap.get(or);
							bd.clearNbMap();// 清空nbMap
							bd.clearLocalVar(); // lmk
							bd.clearBeforeNbMap();
						}
						// AlwaysMai:此处为退出有局部变量的情况，故依旧在View中清除
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
				// 查找局部变量中的数据结构引用

				List<LocalVariable> locals = frame.visibleVariables();
				// AlwaysMai:递归调用时也清空？
				System.out.println("---clear variable while recursing.");
				// AlwaysMai:清空时，必须同时清空drawer的局部变量nbMap
				Set<String> keys = ldMap.keySet();
				for (String key : keys) {
					ObjectReference or = ldMap.get(key);// 获取它所属的数据结构
					BaseDrawer bd = dsMap.get(or);
					bd.clearNbMap();// 清空nbMap
					// bd.clearBeforeNbMap();
				} // AlwaysMai:改为Drawer每次PaintChildren后自动清空
					// AlwaysMai:出错，自动清空时，出现还未改变数据结构就重画的可能，导致变量丢失
				ldMap.clear();
				lvMap.clear();

				for (LocalVariable local : locals) {
					Value value = frame.getValue(local);
					if (value == null) {// AlwaysMai:测试变量值为空的情况
						System.out.println("---The value of " + local.name()
								+ " is null");
						ObjectReference or = beforeLdMap.get(local.name());// 获取它所属的数据结构
						if (or != null) {

							ObjectReference beforeValue = beforeLvMap.get(local
									.name());
							if (beforeValue != null) {
								ldMap.put(local.name(), or);
								lvMap.put(local.name(), null);
							}
						}
						// AlwaysMai:如何获取变量的声明类型
						// AlwaysMai:值为空时，不能使用local.type来获取声明类型
					}

					if (value instanceof ObjectReference) {
						ObjectReference or = (ObjectReference) value;
						// 如果this变量就是数据结构引用，则默认情况是需要画引用的局部变量
						// if (thisObj != null &&
						// DSObjectReferenceChecker.getObjectReferenceType(thisObj)
						// > 0) {
						// ？？变量名同名的情况也并未考虑
						if (LVObjectReferenceChecker.getObjectReferenceType(or) > 0) {
							lcMap.put(local.name(), thisObj); // lmk:以后用于判断null的情况
							lvMap.put(local.name(), or);
							// ？？引用为空的情况还未考虑
							// }
						} else {
							findDSObjectReference(or);
						}
					}// end ObjectReference
				}// end for localvariable
					// AlwaysMai:输出代码外提，已经清除为空的局部变量的清空
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
		// 传递要画的数据结构
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
		// wcb(20121129)堆栈局部变量跟踪响应
		// d.sentData();

		this.setLdMap();

		Set<String> keys = ldMap.keySet(); /* 传递局部变量名及其引用 */
		System.out.println("所有的局部变量：" + keys);
		for (String key : keys) {
			ObjectReference or = ldMap.get(key);// 获取它所属的数据结构
			BaseDrawer bd = dsMap.get(or);
			// wcb:(20121130)堆栈局部变量响应
//			f.setDrawer(bd);
//			f.sentData();
//			bd.RecevieData(key, lvMap.get(key));
			// wcb:(获取面板)20121130
			// d.setDrawer(bd);
			// 局部变量
			// bd.RecevieData(key, lvMap.get(key));//传递要画的变量名称和变量值
			// d.sentData();
			tabbedPane.repaint();// AlwaysMai:0706
		}

		this.setGdMap();

		Set<String> keys_g = gdMap.keySet(); /* 传递全局变量名及其引用 */
		for (String key : keys_g) {
			ObjectReference or = gdMap.get(key);// 获取它所属的数据结构
			BaseDrawer bd = dsMap.get(or);
			bd.RecevieData(key, gvMap.get(key));// 传递要画的变量名称和变量值
			tabbedPane.repaint();
		}
	}
	
	/*lmk:根据lcMap构造ldMap*/
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

    /*lmk:根据lcMap构造ldMap*/
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
		// 该引用对象是数组
		if (or.referenceType() instanceof ArrayType) {
			return;
		}
		// 该引用对象是某个数据结构数据结构
		if (DSObjectReferenceChecker.getObjectReferenceType(or) > 0) {
			// 该数据结构引用对象不存在，才加入
			System.out.println("one one");
			if (!this.dsObjectReference.contains(or)) {
				// 把新的数据结构引用对象加入进去
				System.out.println("this where");
				dsObjectReference.add(or);
				// 创建数据结构对应的面板
				addTabbedPane(or);
			}
			return;
		}

		ReferenceType clazz = or.referenceType();
		List<Field> fields = clazz.allFields();

		for (Field field : fields) {
			Value value = or.getValue(field);
			// System.out.println("字段名:"+field.name()+"  字段类型名:"+field.typeName());
			if (value instanceof ObjectReference) {
				ObjectReference or1 = (ObjectReference) value;
				if (or1.referenceType() instanceof ArrayType) {
					return;
				} else {
					findDSObjectReference(or1); /* 递归查找数据结构 */
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
				System.out.println("在  bd == null——————————————》");
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
		/* lmk: 判断or1是否为全局变量,并判断是那种数据结构的全局变量 */
		ReferenceType clazz = or.referenceType();
		List<Field> fields = clazz.allFields();

		for (Field field : fields) {
			Value value = or.getValue(field);
			// System.out.println("字段名:"+field.name()+"  字段类型名:"+field.typeName());
			if (value instanceof ObjectReference) {
				ObjectReference or1 = (ObjectReference) value;
				if (or1.referenceType() instanceof ArrayType) {
					return;
				} else
				// ？？变量名同名的情况也并未考虑
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
