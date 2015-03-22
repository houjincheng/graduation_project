package anyviewj.interfaces.ui.panel;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import anyviewj.console.ConsoleCenter;
import anyviewj.debug.session.Session;
import anyviewj.debug.session.SessionFrameMapper;
import anyviewj.interfaces.ui.Project;
import anyviewj.interfaces.ui.manager.JavaProjectManager;

public class newFilePanel extends JDialog {
	private ConsoleCenter center;
	private customProjectPanel prjPanel = null;
	private JScrollPane jsPane = null;
	public JTextField sourceFolder = null;
	private JTextField name = null;
	private JButton finish = null;
	private JButton cancel = null;
	
	public newFilePanel(ConsoleCenter center, String path) {
		super(ConsoleCenter.mainFrame, "New File");
        this.center = center;
        initPanel(path);
	}
	
	
	private void initPanel(String path) {
		Point pt = ConsoleCenter.mainFrame.getLocation();
		setBounds(pt.x+170, pt.y+30, 500, 500);
		setLayout(null);
		
		prjPanel = new customProjectPanel(center, this, true);
		
		jsPane = new JScrollPane(prjPanel);
		jsPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jsPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jsPane.getVerticalScrollBar().setUnitIncrement(20);
        jsPane.setBounds(50, 30, 400, 230);
		this.add(jsPane);
		
		sourceFolder = new JTextField();
		name = new JTextField();
		finish = new JButton("完成");
		cancel = new JButton("取消");
		JLabel sFolderLabel = new JLabel("Source Folder:");
		JLabel nameLabel = new JLabel("Name:");
		
		sFolderLabel.setBounds(20, 300, 100, 30);
		nameLabel.setBounds(20, 350, 100, 30);
		sourceFolder.setBounds(120, 300, 300, 30);
		name.setBounds(120, 350, 300, 30);
		finish.setBounds(120, 400, 100, 30);
		cancel.setBounds(260, 400, 100, 30);
		
		finish.setEnabled(false);
		sourceFolder.setEditable(false);
		
		this.add(sFolderLabel);
		this.add(sourceFolder);
		this.add(nameLabel);
		this.add(name);
		this.add(finish);
		this.add(cancel);
		
		if (path != null) {
			sourceFolder.setText(path);
		}
		
		name.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if (sourceFolder.getText().isEmpty() || name.getText().isEmpty()) {
					finish.setEnabled(false);
				}
				else {
					finish.setEnabled(true);
				}
			}
		});
		
		finish.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String path = sourceFolder.getText() +File.separator + name.getText();
				String prjName = prjPanel.getSelectProjectName();
				if (!path.endsWith(".java")) {
					path += ".java";
				}
				File file = new File(path);
				if (!file.exists() && !file.isFile()) {
					try {
						file.createNewFile();
						FileOutputStream fos = null;
						try {
							fos = new FileOutputStream(file.getAbsoluteFile(), true);
							PrintWriter pw = new PrintWriter(fos);
							int index = prjName.lastIndexOf('\\') + "src".length();
//							String prjPath = prjName.substring(0, index);
							String packageName = sourceFolder.getText();
							packageName = packageName.substring(index + 1, packageName.length());
							if (packageName.isEmpty()) {
								pw.write("\r\n"
										 + "public class " + name.getText() + "{\r\n"
										 + "\t\r\n}");
							}
							else {
								packageName = packageName.substring(1, packageName.length());
								packageName = packageName.replace('\\', '.');
								pw.write( "package " + packageName + ";\r\n\r\n"
										+ "public class " + name.getText() + "{\r\n"
										+ "\t\r\n}");
							}
							pw.flush();
						} catch (FileNotFoundException fe) {
							fe.printStackTrace();
						}
						fos.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}			
				Session session = SessionFrameMapper.getSessionForFrame(SessionFrameMapper.getOwningFrame(e));
				Project prj = ((JavaProjectManager)ConsoleCenter.projectManager).findProject(prjName);
				ConsoleCenter.mainFrame.rightPartPane.codePane.newFilePane(prj, path, true, session);
				String selectName = sourceFolder.getText();
				DefaultMutableTreeNode prjNode = ConsoleCenter.mainFrame.leftPartPane.projectPane.curProjectPane.findTreeNode(prjName);
				if (prjNode != null) {
					DefaultMutableTreeNode node = ConsoleCenter.mainFrame.leftPartPane.projectPane.curProjectPane.findNode(prjNode, selectName);
					String fileName = name.getText();
					int index = fileName.indexOf('\\');
					if (index <= 0) {
						ConsoleCenter.mainFrame.leftPartPane.projectPane.curProjectPane.updateFile(path, node);
					}
					else {
						fileName = sourceFolder.getText() + File.separator + fileName.substring(0, index + 1);
						ConsoleCenter.mainFrame.leftPartPane.projectPane.curProjectPane.updatePrjFiles(fileName, node);
					}
//					JTree prjTree = center.mainFrame.leftPartPane.projectPane.curProjectPane.projectTree;
//					DefaultTreeModel model = (DefaultTreeModel)(prjTree.getModel());
//			    	DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
//			    	model.setRoot(root);
				}
				newFilePanel.this.dispose();
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				newFilePanel.this.dispose();
			}
		});
	}
}