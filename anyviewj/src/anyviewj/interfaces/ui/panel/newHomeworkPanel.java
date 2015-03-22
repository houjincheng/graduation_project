package anyviewj.interfaces.ui.panel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import anyviewj.console.ConsoleCenter;
import anyviewj.debug.session.Session;
import anyviewj.debug.session.SessionFrameMapper;
import anyviewj.interfaces.ui.JavaProject;
import anyviewj.interfaces.ui.Project;
import anyviewj.interfaces.ui.manager.JavaProjectManager;
import anyviewj.interfaces.ui.panel.newProjectPanel.addListen;
import anyviewj.interfaces.ui.panel.newProjectPanel.newPrjPanelComponetListener;

public class newHomeworkPanel extends JDialog{
	
	JButton broswerbt = null;
	JTextField prjLocationText = null;
	JTextField prjFolderText = null;
	JTextField prjNameText = null;
	JTextField mainClassText = null;
	String location = "E:\\";
	
	JPanel panel = new JPanel();
	JPanel secondPanel = new JPanel();
	JPanel buttonPanel = new JPanel();
	
	JButton finish = new JButton("完成");
	
	public newHomeworkPanel(ConsoleCenter center){
		super(ConsoleCenter.mainFrame, "New Homework");

		this.Init();
		this.setModal(true);            	
        this.setVisible(true);
		
	}
	
	public static void main(String args[]){
		File file = new File("Main.java");
		File file2 = new File("E:\\Main.java");
		
		fileCopy(file,file2);

	}

	public void Init(){
		
		Point pt = ConsoleCenter.mainFrame.getLocation();
		setBounds(pt.x+100, pt.y+50, 600, 500);
		
		CardLayout cLayout = new CardLayout();
		panel.setLayout(cLayout);
	   	buttonPanel.setBounds(0, getHeight()-100, getWidth(), 100);
		
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
    	
    	finish.addActionListener(new addListen());
        buttonPanel.add(finish);
    	panel.add(secondPanel);
    	
    	//setLayout(new BorderLayout());
    	
    	//add(panel,BorderLayout.CENTER);
    	//add(buttonPanel,BorderLayout.SOUTH);
    	setLayout(null);
    	add(panel);
    	add(buttonPanel);
    	addComponentListener(new newHomeworkPanelComponetListener());
	}
	
	class addListen extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			JButton jbt = (JButton)arg0.getSource();
			JFileChooser fc;
			if (jbt.equals(broswerbt)) {
				fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.setDialogTitle("选择项目保存位置");
				int val = fc.showOpenDialog(newHomeworkPanel.this);
				if (val == JFileChooser.APPROVE_OPTION) {
					String sPath = fc.getSelectedFile().getAbsolutePath();
//					System.out.println(sPath);
					prjLocationText.setText(sPath);
					prjFolderText.setText(sPath + "\\" + prjNameText.getText());
				}
			}
			else if (jbt.equals(finish)) {
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
				File testDir = new File(dirfile.getAbsolutePath() + "\\src"+"\\test");
				if (!testDir.exists() && !testDir.isDirectory()) {
					testDir.mkdir();
					File User1 = new File("User.java");
					File User2 = new File(dirfile.getAbsolutePath() + "\\src"+"\\test"+"\\User.java");
					fileCopy(User1,User2);
				}
				File checkDir = new File(dirfile.getAbsolutePath() + "\\src"+"\\check");
				if (!checkDir.exists() && !checkDir.isDirectory()) {
					checkDir.mkdir();
					File Answer1 = new File("Answer.java");
					File Answer2 = new File(dirfile.getAbsolutePath() + "\\src"+"\\check"+"\\Answer.java");
					fileCopy(Answer1,Answer2);
				}
				File testmainDir = new File(dirfile.getAbsolutePath() + "\\src"+"\\testmain");
				if (!testmainDir.exists() && !testmainDir.isDirectory()) {
					testmainDir.mkdir();
					File Main1 = new File("Main.java");
					File Main2 = new File(dirfile.getAbsolutePath() + "\\src"+"\\testmain"+"\\Main.java");
					fileCopy(Main1,Main2);
					
					File Standard1 = new File("Standard.java");
					File Standard2 = new File(dirfile.getAbsolutePath() + "\\src"+"\\testmain"+"\\Standard.java");
					fileCopy(Standard1,Standard2);
				}
				File DataDir = new File(dirfile.getAbsolutePath() + "\\src"+"\\Data");
				if (!DataDir.exists() && !DataDir.isDirectory()) {
					DataDir.mkdir();
					File Visitor1 = new File("Visitor.java");
					File Visitor2 = new File(dirfile.getAbsolutePath() + "\\src"+"\\Data"+"\\Visitor.java");
					fileCopy(Visitor1,Visitor2);
				}
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
						properties.setProperty("testPath", "test");
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
					Session session = SessionFrameMapper.getSessionForFrame(SessionFrameMapper.getOwningFrame(arg0));
					String prjName = prjFolderText.getText()+"\\"+prjNameText.getText()+".ajp";
					System.out.println( "newHomework : session = " + session );
					prj = ConsoleCenter.projectManager.openProject(prjName, session);
					
				}                
				((JavaProjectManager)ConsoleCenter.projectManager).setprjList(prj);
				newHomeworkPanel.this.dispose();
			}
			
		}
		
	}
	
	class newHomeworkPanelComponetListener extends ComponentAdapter {

		@Override
		public void componentResized(ComponentEvent arg0) {
			// TODO Auto-generated method stub
			newHomeworkPanel prjPanel = (newHomeworkPanel)arg0.getComponent();
//			int increase = prjPanel.getWidth() - northPanel.getWidth() - 150;
//			stepNewProject.setBounds(0, 0, 150, prjPanel.getHeight()-100);
        	panel.setBounds(150, 0, prjPanel.getWidth()-150, prjPanel.getHeight()-100);
//        	northPanel.setBounds(0, 0, prjPanel.getWidth()-150, prjPanel.getHeight()-100);
        	secondPanel.setBounds(0, 0, prjPanel.getWidth()-150, prjPanel.getHeight()-100);
        	buttonPanel.setBounds(0, prjPanel.getHeight()-100, prjPanel.getWidth()-150, 100);
        	
//        	int mpSize = managePrjPane.getWidth();
//        	int mpIncrease = (increase%2==0)? increase/2 : (increase - 1)/2;
//        	managePrjPane.setBounds(20, 50, mpSize + mpIncrease, northPanel.getHeight()-200);
//        	prjTree.setBounds(0, 0, managePrjPane.getWidth(), managePrjPane.getHeight());
//        	int size = northPanel.getWidth()-mpSize-mpIncrease-40-20;
//        	prjPane.setBounds(mpSize + mpIncrease + 40, 50, size, northPanel.getHeight()-200);
//        	discriblePane.setBounds(20, northPanel.getHeight()-120,northPanel.getWidth()-40,100);

        	
        	prjNameText.setBounds(100, 50, secondPanel.getWidth()-100-100, 25);
        	prjLocationText.setBounds(100, 100, secondPanel.getWidth()-100-100, 25);
        	prjFolderText.setBounds(100, 150, secondPanel.getWidth()-100-100, 25);
        	mainClassText.setBounds(100, 250, secondPanel.getWidth()-100-100, 25);
        	broswerbt.setBounds(secondPanel.getWidth()-90, 100, 75, 25);
		}
	}
	
	public static void fileCopy(File from, File to){
		try {
			FileInputStream fis = new FileInputStream(from);
			FileOutputStream fos = new FileOutputStream(to);
			
			byte[] b = new byte[100]; 
			
			
			
			while (fis.read(b) != (-1)) 
			{	
				fos.write(b);  
		    }
			
			fis.close();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
	}
	
}
