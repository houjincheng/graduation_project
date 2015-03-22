package anyviewj.interfaces.ui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import anyviewj.console.ConsoleCenter;
import anyviewj.interfaces.ui.panel.CurProjectPane.MyTreeNode;

public class customProjectPanel extends JPanel {
	private ConsoleCenter center;
	private CurProjectPane curPrjPane = null;
	private JTree prjTree = null;
	private boolean showAllFolder = false;
	private JDialog dlgPanel = null;
	private DefaultMutableTreeNode selectNode = null;
	
	public customProjectPanel(ConsoleCenter center, JDialog dlg, boolean showAllFolder) {
		super(new BorderLayout());
        this.center = center;
        curPrjPane = ConsoleCenter.mainFrame.leftPartPane.projectPane.curProjectPane;
        this.dlgPanel = dlg;
        this.showAllFolder = showAllFolder;
        initPanel(showAllFolder);
	}
	
	/**
	 * hou
	 * @param tree     currentProjectPane上的项目树
	 * @param prjTree 新建包面板上的项目树
	 * @return
	 */
	private void setSelectNode( JTree tree, JTree prjTree )
	{
//		找到要新建包的工程
		MyTreeNode currentPrjNode = (MyTreeNode)tree.getLastSelectedPathComponent();
		String prjName = currentPrjNode.getFileName();
		MyTreeNode node = (MyTreeNode)currentPrjNode.getParent();
		while (!prjName.endsWith(".ajp")) {
			if (node == null) {
				break;
			}
			prjName = node.getFileName();
			node = (MyTreeNode)node.getParent();
		}
    	prjName.substring( 0, prjName.length() - 4 );
    	
    	
//    	遍历新建包面板上的项目树，比对出同一个项目，并记录到selectNode
    	DefaultTreeModel prjModel = (DefaultTreeModel)(prjTree.getModel());
    	DefaultMutableTreeNode prjRoot = (DefaultMutableTreeNode)prjModel.getRoot(); 
    	MyTreeNode nodeTmp = (MyTreeNode)prjRoot.getFirstChild();

		while (nodeTmp != null) {
			
			String tmp = nodeTmp.getFileName();
			System.out.println( "tmp == " + tmp );
			if ( ( nodeTmp.getFileName() ).equals( prjName ) )
			{
				this.selectNode = nodeTmp;
			}
			nodeTmp = (MyTreeNode)prjRoot.getChildAfter(nodeTmp);
		}
	}	
	
	private void initPanel(boolean showAllFolder) {
    	setBounds(50, 30, 400, 230);
    	setBackground(Color.WHITE);
    	setBorder(BorderFactory.createLineBorder(new Color(0x7F,0x9D,0xB9), 1));
    	
    	prjTree = new JTree(curPrjPane.new MyTreeNode(""));
//    	String path = center.mainFrame.leftPartPane.projectPane.curProjectPane.getCurrentSelectNodeFolderPath();
    	
    	JTree tree = ConsoleCenter.mainFrame.leftPartPane.projectPane.curProjectPane.projectTree;
    	DefaultTreeModel model = (DefaultTreeModel)(tree.getModel());
    	DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
    	if (root == null || root.getChildCount() <= 0) {
    		return ;
    	}
    	
    	DefaultTreeModel prjModel = (DefaultTreeModel)(this.prjTree.getModel());
    	DefaultMutableTreeNode prjRoot = (DefaultMutableTreeNode)prjModel.getRoot();    	
    	
    	if (showAllFolder) {
    		MyTreeNode node = (MyTreeNode)root.getFirstChild();
    		while (node != null) {
    			String prjName = node.getUserObject().toString();
    			prjName = prjName.substring(0, prjName.lastIndexOf('.'));
    			MyTreeNode curNode = curPrjPane.new MyTreeNode(prjName);
    			curNode.setFolder(node.isFolder());
    			curNode.setFileName(node.getFileName());
    			prjRoot.add(curNode); 	
    			traceFolderNode(node, curNode);
    			node = (MyTreeNode)root.getChildAfter(node);
    		}
    	}
    	else {
    		MyTreeNode node = (MyTreeNode)root.getFirstChild();
    		while (node != null) {
    			String prjName = node.getUserObject().toString();
    			prjName = prjName.substring(0, prjName.lastIndexOf('.'));
    			MyTreeNode curNode = curPrjPane.new MyTreeNode(prjName);
    			curNode.setFolder(true);
    			curNode.setFileName(node.getFileName());
    			MyTreeNode childNode = curPrjPane.new MyTreeNode("src");
    			String path = node.getFileName().substring(0, node.getFileName().lastIndexOf('\\'));
    			childNode.setFileName(path + File.separator + "src");
    			childNode.setFolder(true);
    			curNode.add(childNode);
    			prjRoot.add(curNode);
    			node = (MyTreeNode)root.getChildAfter(node);
    		}
    	}
    	prjTree.setBorder(BorderFactory.createLineBorder(new Color(0x7F,0x9D,0xB9),1));
        this.add(prjTree,BorderLayout.CENTER);
        prjTree.setRootVisible(false);
        prjModel.setRoot(prjRoot);
        
//        hou 不应该默认为第一个工程
//        selectNode = (MyTreeNode)prjRoot.getFirstChild();
        setSelectNode( tree,  prjTree );        
        
        prjTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        
        prjTree.addTreeSelectionListener(new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				System.out.println( "tree selected!");
				MyTreeNode node = (MyTreeNode)prjTree.getLastSelectedPathComponent();
				selectNode = node;
				System.out.println( "valueChange selectPtj = " + ((MyTreeNode)selectNode).getFileName());
				String path = node.getFileName();
				if (path.endsWith(".ajp")) {
					path = path.substring(0, path.lastIndexOf('\\'));
				}
				if (customProjectPanel.this.showAllFolder) {
					// 处理新建文件面板
					((newFilePanel)dlgPanel).sourceFolder.setText(path);
				}
				else {
					((newPackagePanel)dlgPanel).sourceFolder.setText(path);
				}
			}
		});
	}
	

	private void traceFolderNode(DefaultMutableTreeNode srcNode, DefaultMutableTreeNode dstNode) {
		if (srcNode == null || dstNode == null || srcNode.getChildCount() <= 0) {
			return ;
		}
		MyTreeNode childNode = (MyTreeNode)srcNode.getFirstChild();
		while (childNode != null) {  
			if (childNode.isFolder()) {
				String name = childNode.getUserObject().toString();
				MyTreeNode subNode = curPrjPane.new MyTreeNode(name);
				subNode.setFolder(childNode.isFolder());
				subNode.setFileName(childNode.getFileName());
				dstNode.add(subNode);
				traceFolderNode(childNode, subNode);
			}
			childNode = (MyTreeNode)srcNode.getChildAfter(childNode);
		}
	}
	
	public DefaultMutableTreeNode getSelectNode() {
		return this.selectNode;
	}
	
	public String getSelectProjectName() {
		String prjName = ((MyTreeNode)selectNode).getFileName();
		MyTreeNode node = (MyTreeNode)selectNode.getParent();
		while (!prjName.endsWith(".ajp")) {
			if (node == null) {
				break;
			}
			prjName = node.getFileName();
			node = (MyTreeNode)node.getParent();
		}
		return prjName;
	}

}