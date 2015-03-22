package anyviewj.interfaces.ui.panel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import anyviewj.console.ConsoleCenter;
import anyviewj.debug.session.Session;
import anyviewj.debug.session.SessionFrameMapper;
import anyviewj.interfaces.ui.JavaProject;
import anyviewj.interfaces.ui.Project;
import anyviewj.interfaces.ui.manager.JavaProjectManager;

public class newProjectPanel extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ConsoleCenter center;
//	private JDialog thisPrjDlg = null;
	
	JavaProject newPrj = null;
	
	JPanel panel = null;
	JPanel northPanel = null;
	JPanel secondPanel = null;
	JPanel southPanel = null;
	JTextArea stepNewProject = null;
	JPanel managePrjPane = null;
	JPanel prjPane = null;
	JTextArea discriblePane = null;
	
	JFileChooser fc = null;
	
	JTree prjTree = null;
	DefaultMutableTreeNode selectNode = null;
	
	JTextField prjNameText = null;
	JTextField prjLocationText = null;
	JTextField prjFolderText = null;
	JTextField mainClassText = null;
	String location = "C:\\Users\\hou\\Desktop\\Test";
	
	
	CardLayout cLayout = null;
	
	JButton prevbt = null;
	JButton nextbt = null;
	JButton finishbt = null;
	JButton cancelbt = null;
	JButton broswerbt = null;
	
	public newProjectPanel(ConsoleCenter center) {
		super(ConsoleCenter.mainFrame, "New Project");
        this.center = center;
        initPanel();
	}
	
//	public Project createNewProject()
	
	private void initPanel() {
		panel = new JPanel();
		northPanel = new JPanel();
		southPanel = new JPanel();
		secondPanel = new JPanel();
		stepNewProject = new JTextArea();
		managePrjPane = new JPanel(new BorderLayout());
		prjPane = new JPanel(new BorderLayout());
		discriblePane = new JTextArea();
		
		stepNewProject.setEditable(false);
		stepNewProject.setLineWrap(true);
		stepNewProject.append("\r\n" + " 步骤:" + "\r\n");
		stepNewProject.append("   1. 选择项目" + "\r\n");
		stepNewProject.append("   2. ...." + "\r\n");
		
		Point pt = ConsoleCenter.mainFrame.getLocation();
    	setBounds(pt.x+100, pt.y+50, 600, 500);
    	cLayout = new CardLayout();
		panel.setLayout(cLayout);
		setLayout(null);
		stepNewProject.setBounds(0, 0, 150, getHeight()-100);
		panel.setBorder(BorderFactory.createLineBorder(new Color(0x7F,0x9D,0xB9), 1));
    	panel.setBounds(150, 0, getWidth()-150, getHeight()-100);
    	northPanel.setBounds(0, 0, getWidth()-150, getHeight()-100);
    	secondPanel.setBounds(0, 0, getWidth()-150, getHeight()-100);
    	southPanel.setBounds(0, getHeight()-100, getWidth(), 100);
//    	southPanel.setBorder(BorderFactory.createLineBorder(new Color(0x7F,0x9D,0xB9), 1));
    	northPanel.setBorder(BorderFactory.createLineBorder(new Color(0x7F,0x9D,0xB9), 1));
    	secondPanel.setBorder(BorderFactory.createLineBorder(new Color(0x7F,0x9D,0xB9), 1));
    	
    	northPanel.setLayout(null);
    	managePrjPane.setBounds(20, 50, northPanel.getWidth()-300, northPanel.getHeight()-200);
    	prjPane.setBounds(northPanel.getWidth()-260, 50, northPanel.getWidth()-210, northPanel.getHeight()-200);
    	discriblePane.setBounds(20, northPanel.getHeight()-120,northPanel.getWidth()-40,100);

    	managePrjPane.setBorder(BorderFactory.createLineBorder(new Color(0x7F,0x9D,0xB9), 1));
    	prjPane.setBorder(BorderFactory.createLineBorder(new Color(0x7F,0x9D,0xB9), 1));
    	discriblePane.setBorder(BorderFactory.createLineBorder(new Color(0x7F,0x9D,0xB9), 1));
    	northPanel.add(managePrjPane);
    	northPanel.add(prjPane);
    	northPanel.add(discriblePane);
    	
    	DefaultMutableTreeNode root = new DefaultMutableTreeNode("");
    	DefaultMutableTreeNode top = new DefaultMutableTreeNode("Java");
    	DefaultMutableTreeNode JavaFx = new DefaultMutableTreeNode("JavaFX");
    	DefaultMutableTreeNode Maven = new DefaultMutableTreeNode("Maven");
    	selectNode = top;
    	root.add(top);
    	root.add(JavaFx);
    	root.add(Maven);
    	prjTree = new JTree(root);
    	prjTree.setRootVisible(false);
//    	managePrjPane.add(prjTree, BorderLayout.CENTER);
    	prjTree.setBounds(0, 0, managePrjPane.getWidth(), managePrjPane.getHeight());
    	managePrjPane.add(prjTree);
    	
    	prjTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    	prjTree.addTreeSelectionListener(new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				// TODO Auto-generated method stub
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)prjTree.getLastSelectedPathComponent();
				if (node == null)
					return;
				selectNode = node;
//				String str = (String)node.getUserObject();
//				System.out.println(str);
			}
		});
    	
    	
    	prjNameText = new JTextField("JavaApplication");
    	prjLocationText = new JTextField(location);
    	prjFolderText = new JTextField(location + "\\" + prjNameText.getText());
    	mainClassText = new JTextField("JavaApplication");
    	
    	prjNameText.addKeyListener(new KeyAdapter() {
    		@Override
			public void keyReleased(KeyEvent e) {
    			mainClassText.setText(prjNameText.getText());
    			prjFolderText.setText(prjLocationText.getText() + "\\"+ mainClassText.getText());
    		}
		});
    	
    	prjLocationText.addKeyListener(new KeyAdapter() {
    		@Override
			public void keyReleased(KeyEvent e) {
    			prjFolderText.setText(prjLocationText.getText() + "\\" + prjNameText.getText());
    		}
		});
    	
    	JLabel prjNameLabel = new JLabel("项目名称:");
    	JLabel prjLocationLabel = new JLabel("项目位置:");
    	JLabel prjFolderLabel = new JLabel("项目文件夹:");
    	JLabel mainClassTextLabel = new JLabel("创建主类:");    	
    	broswerbt = new JButton("浏览...");
    	broswerbt.addActionListener(new addListen());
    	
    	secondPanel.setLayout(null);
    	prjNameLabel.setBounds(20, 50, 70, 25);
    	prjLocationLabel.setBounds(20, 100, 70, 25);
    	prjFolderLabel.setBounds(20, 150, 70, 25);
    	mainClassTextLabel.setBounds(20, 250, 70, 25);
    	
    	prjNameText.setBounds(100, 50, 250, 25);
    	prjLocationText.setBounds(100, 100, 250, 25);
    	prjFolderText.setBounds(100, 150, 250, 25);
    	mainClassText.setBounds(100, 250, 250, 25);
    	broswerbt.setBounds(360, 100, 75, 25);
    	
    	secondPanel.add(prjNameLabel);
    	secondPanel.add(prjLocationLabel);
    	secondPanel.add(prjFolderLabel);
    	secondPanel.add(mainClassTextLabel);
    	secondPanel.add(prjNameText);
    	secondPanel.add(prjLocationText);
    	secondPanel.add(prjFolderText);
    	secondPanel.add(mainClassText);
    	secondPanel.add(broswerbt);
    	
    	
    	add(stepNewProject);
    	panel.add(northPanel, "first");
    	panel.add(secondPanel, "second");
    	add(panel);
    	add(southPanel);
    	
    	prevbt = new JButton(" 上一步 ");
    	prevbt.addActionListener(new addListen());
    	prevbt.setEnabled(false);
    	nextbt = new JButton(" 下一步 ");
    	nextbt.addActionListener(new addListen());
    	finishbt = new JButton("  完成  ");
    	finishbt.addActionListener(new addListen());
    	finishbt.setEnabled(false);
    	cancelbt = new JButton("  取消  ");
    	cancelbt.addActionListener(new addListen());
    	
    	southPanel.add(prevbt);
    	southPanel.add(nextbt);
    	southPanel.add(finishbt);
    	southPanel.add(cancelbt);
    	addComponentListener(new newPrjPanelComponetListener());
	}
	
	class addListen extends AbstractAction  {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JButton jbt = (JButton)e.getSource();
			if (jbt.equals(prevbt)) {
				prevbt.setEnabled(false);
				finishbt.setEnabled(false);
				nextbt.setEnabled(true);
				int start, end;
				try {
					start = stepNewProject.getLineStartOffset(3);
					end = stepNewProject.getLineEndOffset(3);
					stepNewProject.replaceRange("   2. ....", start, end);
				}catch(BadLocationException exp) {
					exp.printStackTrace();
				}
				cLayout.show(panel, "first");
			}
			else if (jbt.equals(nextbt)) {
				prevbt.setEnabled(true);
				finishbt.setEnabled(true);
				nextbt.setEnabled(false);
				int start, end;
				try {
					start = stepNewProject.getLineStartOffset(3);
					end = stepNewProject.getLineEndOffset(3);
					stepNewProject.replaceRange("   2. 名称和位置", start, end);
				}catch(BadLocationException exp) {
					exp.printStackTrace();
				}
				
				cLayout.show(panel, "second");
			}
			else if (jbt.equals(finishbt)) {
//				获取用户输入的要新建项目工程的路径，如果没有就新建
				File dirfile = new File(prjFolderText.getText());
				if (!dirfile.exists() && !dirfile.isDirectory()) {
					dirfile.mkdirs();
//					System.out.println("dirfile Created");
				}

				File classesDir = new File(dirfile.getAbsolutePath() + "\\classes");
				if (!classesDir.exists() && !classesDir.isDirectory()) {
					classesDir.mkdir();
				}
				File srcDir = new File(dirfile.getAbsolutePath() + "\\src");
				if (!srcDir.exists() && !srcDir.isDirectory()) {
					srcDir.mkdir();
				}
//				File mainSrcFile = new File(srcDir.getAbsolutePath(), mainClassText.getText() + ".java");
//				if (!mainSrcFile.exists() && !mainSrcFile.isFile()) {
//					try {
//						mainSrcFile.createNewFile();
//						FileOutputStream fos = null;
//						try {
//							fos = new FileOutputStream(mainSrcFile.getAbsoluteFile(), true);
//							PrintWriter pw = new PrintWriter(fos);
//							pw.write("import java.util.*;\r\n\r\n"
//									+ "public class " + mainClassText.getText() + "{\r\n\r\n"
//									+ "\tpublic static void main(String arg[]) {\r\n\t\t\r\n"
//									+ "\t}\r\n}");
//							pw.flush();
//						} catch (FileNotFoundException fe) {
//							fe.printStackTrace();
//						}
//						fos.close();
//					} catch (IOException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//				}
//				在工程文件中写入工程信息
				File prjFile = new File(prjFolderText.getText(), prjNameText.getText() + ".ajp");
				if (!prjFile.exists() && !prjFile.isFile()) {
					try {
						prjFile.createNewFile();
//						System.out.println(prjFile.getAbsolutePath());
						Properties properties = new Properties();												
//						properties.setProperty("mainfile", mainSrcFile.getName());
						properties.setProperty(JavaProject.SOURCE_PATH, "src");
//						properties.setProperty(JavaProject.OPEN_FILE_NUM, "" + 1);
						properties.setProperty(JavaProject.CLASSES_PATH, "classes");
//						properties.setProperty(JavaProject.OPEN_FILE_NAME+0, mainSrcFile.getName());
						FileOutputStream fos = new FileOutputStream(prjFile.getAbsolutePath());
						properties.storeToXML(fos, null, "UTF-8");
						fos.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
//					System.out.println("prjFile create succeed");
				}
//				从全局中心获取要新建的工程是否已经存在，若存在则选中该工程为当前工程，否则新建
				Project prj = ((JavaProjectManager)ConsoleCenter.projectManager).findProject(prjFile.getAbsolutePath());
				if (prj != null) {
            	ConsoleCenter.mainFrame.leftPartPane.projectPane.curProjectPane.setCurProject(prj);
            	((JavaProjectManager)ConsoleCenter.projectManager).setCurProject(prj);
				}
				else {
					Session session = SessionFrameMapper.getSessionForFrame(SessionFrameMapper.getOwningFrame(e));
					String prjName = prjFolderText.getText()+"\\"+prjNameText.getText()+".ajp";
					System.out.println( "newProjectPane : session = " + session );
					prj = ConsoleCenter.projectManager.openProject(prjName, session);
					
				}                
				((JavaProjectManager)ConsoleCenter.projectManager).setprjList(prj);
				newProjectPanel.this.dispose();
			}
			else if (jbt.equals(cancelbt)) {
//				newProjectPanel.this.setModal(false);
				newProjectPanel.this.dispose();
			}
			else if (jbt.equals(broswerbt)) {
				fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.setDialogTitle("选择项目保存位置");
				int val = fc.showOpenDialog(newProjectPanel.this);
				if (val == JFileChooser.APPROVE_OPTION) {
					String sPath = fc.getSelectedFile().getAbsolutePath();
//					System.out.println(sPath);
					prjLocationText.setText(sPath);
					prjFolderText.setText(sPath + "\\" + prjNameText.getText());
				}
			}
		}
		
	}
	
	class newPrjPanelComponetListener extends ComponentAdapter {

		@Override
		public void componentResized(ComponentEvent arg0) {
			// TODO Auto-generated method stub
			newProjectPanel prjPanel = (newProjectPanel)arg0.getComponent();
			int increase = prjPanel.getWidth() - northPanel.getWidth() - 150;
			stepNewProject.setBounds(0, 0, 150, prjPanel.getHeight()-100);
        	panel.setBounds(150, 0, prjPanel.getWidth()-150, prjPanel.getHeight()-100);
        	northPanel.setBounds(0, 0, prjPanel.getWidth()-150, prjPanel.getHeight()-100);
        	secondPanel.setBounds(0, 0, prjPanel.getWidth()-150, prjPanel.getHeight()-100);
        	southPanel.setBounds(0, prjPanel.getHeight()-100, prjPanel.getWidth()-150, 100);
        	
        	int mpSize = managePrjPane.getWidth();
        	int mpIncrease = (increase%2==0)? increase/2 : (increase - 1)/2;
        	managePrjPane.setBounds(20, 50, mpSize + mpIncrease, northPanel.getHeight()-200);
        	prjTree.setBounds(0, 0, managePrjPane.getWidth(), managePrjPane.getHeight());
        	int size = northPanel.getWidth()-mpSize-mpIncrease-40-20;
        	prjPane.setBounds(mpSize + mpIncrease + 40, 50, size, northPanel.getHeight()-200);
        	discriblePane.setBounds(20, northPanel.getHeight()-120,northPanel.getWidth()-40,100);

        	
        	prjNameText.setBounds(100, 50, secondPanel.getWidth()-100-100, 25);
        	prjLocationText.setBounds(100, 100, secondPanel.getWidth()-100-100, 25);
        	prjFolderText.setBounds(100, 150, secondPanel.getWidth()-100-100, 25);
        	mainClassText.setBounds(100, 250, secondPanel.getWidth()-100-100, 25);
        	broswerbt.setBounds(secondPanel.getWidth()-90, 100, 75, 25);
		}
    	
    };
};