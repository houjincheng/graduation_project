package anyviewj.interfaces.ui.panel;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import anyviewj.console.ConsoleCenter;

public class newPackagePanel extends JDialog {
	private ConsoleCenter center;
	private customProjectPanel packagePanel = null;
	private JScrollPane jsPane = null;
	public JTextField sourceFolder = null;
	private JTextField name = null;
	private JButton finish = null;
	private JButton cancel = null;
	
	public newPackagePanel(ConsoleCenter center, String path) {
		super(ConsoleCenter.mainFrame, "New Package");
        this.center = center;
        initPanel(path);
	}
	
	
	private void initPanel(String path) {
		Point pt = ConsoleCenter.mainFrame.getLocation();
    	setBounds(pt.x+170, pt.y+30, 500, 500);
    	setLayout(null);
    	
    	packagePanel = new customProjectPanel(center, this, false);
    	
    	jsPane = new JScrollPane(packagePanel);
    	jsPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    	jsPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    	jsPane.getVerticalScrollBar().setUnitIncrement(20);
    	jsPane.setBounds(50, 30, 400, 230 );
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
//			path = path.replace('\\', '.');
			sourceFolder.setText(path);
		}
		
		name.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if (sourceFolder.getText().isEmpty() 
						|| name.getText().isEmpty() ) {
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
				String refPath = name.getText();
				refPath = refPath.replace('.', '\\');
				String path = sourceFolder.getText() +File.separator + refPath;
				File file = new File(path);
				if (!file.exists() && !file.isDirectory()) {
					file.mkdirs();	
					String prjName = packagePanel.getSelectProjectName();
					String selectName = sourceFolder.getText();
					DefaultMutableTreeNode prjNode = ConsoleCenter.mainFrame.leftPartPane.projectPane.curProjectPane.findTreeNode(prjName);
					if (prjNode != null) {
						DefaultMutableTreeNode node = ConsoleCenter.mainFrame.leftPartPane.projectPane.curProjectPane.findNode(prjNode, selectName);
						String fileName = refPath;
						int index = fileName.indexOf('\\');
						if (index <= 0) {
							ConsoleCenter.mainFrame.leftPartPane.projectPane.curProjectPane.updateFile(path, node);
						}
						else {
//							fileName = sourceFolder.getText() + File.separator + fileName.substring(0, index);
							fileName = sourceFolder.getText() + File.separator + refPath.substring(0, refPath.indexOf('\\'));
							ConsoleCenter.mainFrame.leftPartPane.projectPane.curProjectPane.updateFile(fileName, node);
						}
//						JTree prjTree = center.mainFrame.leftPartPane.projectPane.curProjectPane.projectTree;
//						DefaultTreeModel model = (DefaultTreeModel)(prjTree.getModel());
//				    	DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
//				    	model.setRoot(root);
					}
				}
				else {
					System.out.println("文件已经存在或不是目录");
				}
				newPackagePanel.this.dispose();
			}
		});
		
		
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				newPackagePanel.this.dispose();
			}
		});
	}
}