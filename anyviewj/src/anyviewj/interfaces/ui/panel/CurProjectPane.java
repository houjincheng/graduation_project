package anyviewj.interfaces.ui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import anyviewj.console.ConsoleCenter;
import anyviewj.debug.session.Session;
import anyviewj.debug.session.SessionFrameMapper;
import anyviewj.interfaces.actions.CommandManager;
import anyviewj.interfaces.ui.CategoryNodeRenderer;
import anyviewj.interfaces.ui.JavaProject;
import anyviewj.interfaces.ui.Project;
import anyviewj.interfaces.ui.manager.JavaProjectManager;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007 gdut 1627</p>
 *
 * <p>Company: gdut 1627</p>
 *
 * @author cyf
 * @version 1.0
 */
public class CurProjectPane extends JSplitPane {
//	JTree必须添加到JSplitPane上
	public final ConsoleCenter center;
//	全局控制中心的命令管理器
	public final CommandManager cm;
    //当前项目项目视图
    public final JPanel projectPane = new JPanel(new BorderLayout());
    public final JToolBar projectToolbar = new JToolBar();
    public final JTree projectTree = new JTree(newPrjTreeNode());//(Object[])null);//
    //当前项目结构视图
    public final JPanel structPane = new JPanel(new BorderLayout());
    public final JToolBar structToolbar = new JToolBar();
    public final JTree structTree = new JTree(newStructTreeNode());//(Object[])null);//
    //
    private Project cur_project = null;
    private DefaultMutableTreeNode curSelectNode = null;
    private DefaultMutableTreeNode curNode = null;
//    private TreePath curpath = null;
    
    
    
    JScrollPane jspPtree = null;
    JScrollPane jspSPane = null;
    
    JPopupMenu popupMenu = new JPopupMenu();
    
    JMenu newBuilder = new JMenu("新建");
//    加入新功能。 hou 2013年5月26日9:44:43
    JMenu open = new JMenu("打开");
    JMenu close = new JMenu("关闭");
    
    JMenu importFile = new JMenu("导入...        ");
    
    JMenuItem newProject = new JMenuItem("  项目  ");
    JMenuItem newFile 	 = new JMenuItem("  文件  ");
    JMenuItem newPackage = new JMenuItem("  包     ");
    
    JMenuItem openProject = new JMenuItem("  打开项目  ");
    JMenuItem openFile 	 = new JMenuItem("  打开文件  ");
    
    JMenuItem closeProject = new JMenuItem("  关闭项目  ");
    JMenuItem closeFile    = new JMenuItem("  关闭文件  ");
    
    JMenuItem compileFile = new JMenuItem("编译文件");
    JMenuItem compileProject = new JMenuItem("编译项目");
    
    JMenuItem runFile = new JMenuItem("运行文件");
    JMenuItem runProject = new JMenuItem("运行项目");
    
    JMenuItem importJarPackage = new JMenuItem("导入jar包文件");
    JMenuItem classFile = new JMenuItem("导入class文件");
    JMenuItem delete = new JMenuItem("删除...        ");
    

    public CurProjectPane(final ConsoleCenter center) {
        super(VERTICAL_SPLIT,null,null);
        this.center = center;
        cm = ConsoleCenter.commandManager;
        //初始化项目视图
        projectToolbar.setBorder(BorderFactory.createEmptyBorder());
        JButton button = new JButton("项目");
        projectToolbar.add(button);
        projectPane.add(projectToolbar,BorderLayout.NORTH);
        
//        projectPane.setAutoscrolls(true);
        jspPtree = new JScrollPane(projectPane);
        jspPtree.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jspPtree.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jspPtree.getVerticalScrollBar().setUnitIncrement(20);

        jspSPane = new JScrollPane(structPane);
        jspSPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jspSPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jspSPane.getVerticalScrollBar().setUnitIncrement(20);
        
        projectTree.setBorder(BorderFactory.createLineBorder(new Color(0x7F,0x9D,0xB9),1));
        projectPane.add(projectTree,BorderLayout.CENTER);
        projectTree.setCellRenderer(new CategoryNodeRenderer());
        projectTree.setRootVisible(false);
        projectTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        projectTree.addTreeSelectionListener(new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				// TODO Auto-generated method stub
                             
                   
				MyTreeNode node = (MyTreeNode)projectTree.getLastSelectedPathComponent();
				if (node == null) {
					curSelectNode = getCurrentNode();
					return;
				}
				curSelectNode = node;
				System.out.println(node.filename);
			}
		});
        
       InitPopupMenu();
        
       projectTree.addMouseListener(new MouseAdapter() {
       	@Override
		public void mouseClicked(MouseEvent e) {
       		if (e.getClickCount() == 2) {
       			TreePath path = projectTree.getPathForLocation(e.getX(), e.getY());
       			if (path != null) {
       				MyTreeNode node = (MyTreeNode)path.getLastPathComponent();
       				if (node.isLeaf() && node.filename.endsWith(".java")) {
//       					System.out.println(node.filename);
       					MyTreeNode parent = (MyTreeNode)node.getParent();
       					while (parent != null && !parent.filename.endsWith(".ajp")) {
//       						System.out.println(parent.filename);
       						parent = (MyTreeNode)parent.getParent();
       					}
       					System.out.println(parent.filename);
       					Project prj = null;
       					if (parent != null) {
       						prj = ((JavaProjectManager)ConsoleCenter.projectManager).findProject(parent.filename);
       						if (getCurProject() != prj) {
       							updateCurrentNode(prj);
       							((JavaProjectManager)ConsoleCenter.projectManager).setCurProject(prj);
       						}
       					}
       					Object obj[] = node.getUserObjectPath();
       					String str = obj[1].toString();
       					str = str.substring(0, str.lastIndexOf('.'));
       					for (int i = 2; i < obj.length -1; ++i) {
       						str += "\\" + obj[i].toString();
       					}
       					System.out.println(str);
       					Session session = SessionFrameMapper.getSessionForFrame(SessionFrameMapper.getOwningFrame(e));
       					ConsoleCenter.mainFrame.rightPartPane.codePane.newFilePane(prj, node.filename, true, session);
       					ConsoleCenter.mainFrame.rightPartPane.codePane.fileTabPane.updateTabInfo();
       				}
       			}
       		}
       		else if (e.isMetaDown()) {
//                               curpath = projectTree.getSelectionModel().getSelectionPath() ;
       			System.out.println("!!!!!!!!!  :  "+this.toString());
       			popupMenu.show(e.getComponent(), e.getX(), e.getY());
       		}
       	}
		});
        
        //初始化结构视图
        structToolbar.setBorder(BorderFactory.createEmptyBorder());
        structToolbar.add(new JButton("结构"));//测试
        structPane.add(structToolbar,BorderLayout.NORTH);

        structTree.setBorder(BorderFactory.createLineBorder(new Color(0x7F,0x9D,0xB9), 1));
        structPane.add(structTree,BorderLayout.CENTER);
        //初始化当前项目面板
        setBorder(null);
        setToolTipText("");
//        add(projectPane,TOP);
        add(jspPtree,TOP);
        add(jspSPane,BOTTOM);
        setDividerSize(5);
        setDividerLocation(220);
        setResizeWeight(1.0);
        

    }

    private void InitPopupMenu() {
    	
    	newBuilder.add(newProject);
    	newBuilder.add(newFile);
    	newBuilder.add(newPackage);
    	
    	popupMenu.add(newBuilder);    
    	popupMenu.add(new JSeparator());
//    	打开项目、文件
    	open.add( openProject );
    	open.add( openFile );
    	
//    	关闭项目、文件
    	close.add( closeProject );
    	close.add( closeFile );
    	
    	popupMenu.add(open);    
    	popupMenu.add(new JSeparator());
//    	添加关闭功能
    	popupMenu.add(close); 
    	popupMenu.add(new JSeparator());
    	
    	popupMenu.add(compileFile);
    	popupMenu.add(compileProject);
    	popupMenu.add(new JSeparator());
    	
//    	popupMenu.add(runFile);
//    	popupMenu.add(runProject);
//    	popupMenu.add(new JSeparator());
    	
    	importFile.add(importJarPackage);
    	importFile.add(classFile);
    	popupMenu.add(importFile);
    	popupMenu.add(delete);
    	
    	
    	newProject.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				newProjectPanel newPrjPanel = new newProjectPanel(center);
            	newPrjPanel.setModal(true);            	
            	newPrjPanel.setVisible(true);  
			}
		});
    	
    	newFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String path = getCurrentSelectNodeFolderPath();
				if (path == null) {
					return ;
				}
				newFilePanel filePanel = new newFilePanel(center, path);
				filePanel.setModal(true);            	
				filePanel.setVisible(true);  
                                
                                projectTree.updateUI();
                                //MyTreeNode node = (MyTreeNode)getCurrentSelectNode();
//                                TreePath a = projectTree.getSelectionModel().getSelectionPath() ;
//				projectTree.expandPath( curpath );
                              
			}
			
		});
    	
    	newPackage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String path = getCurrentSelectNodeFolderPath();
				if (path == null) {
					return ;
				}
				newPackagePanel packagePanel = new newPackagePanel(center, path);
				packagePanel.setModal(true);            	
				packagePanel.setVisible(true);  
                                
                                projectTree.updateUI();
//                                projectTree.expandPath( curpath );
			}
		});
    	
    	//打开项目子菜单
    	openProject.setAction(this.cm.fileActions.openProjectAction);
    	//打开文件子菜单
    	openFile.setAction(cm.fileActions.openFileAction);
    	
        closeProject.setAction(cm.fileActions.closeProjectAction);//关闭项目子菜单
        closeFile.setAction(cm.fileActions.closeFileAction);//关闭文件子菜单
    	
    	compileFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					center.compilerManager.doCompile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
    	
    	compileProject.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					center.compilerManager.allDoCompile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
//    	
//    	runFile.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//					center.interpreterManager.runFile();
//			}
//		});
//    	
//    	runProject.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if (center.projectManager.getCurProject() != null) {
//            		center.interpreterManager.runProject();
//            	}
//			}
//		});
    	
    	importJarPackage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (curNode == null) {
					return ;
				}
				FileDialog fd = new FileDialog(ConsoleCenter.mainFrame,"导入jar包",FileDialog.LOAD);
                fd.setFile("*.jar");
                fd.setVisible(true);
                String filename="";
                if(fd.getDirectory()!=null && fd.getFile()!=null){
                    filename = fd.getDirectory() + fd.getFile();
                    String path = ((MyTreeNode)curNode).getFileName();
                    path = path.substring(0, path.lastIndexOf('\\')) + File.separator + "Library";
    				File file = new File(path + "\\jars");
    				if (!file.exists() && !file.isDirectory()) {
    					file.mkdirs();					   				    					
    				}
    				File jarFile = new File(path + "\\jars" + File.separator + fd.getFile());
    				if (!jarFile.exists() && !jarFile.isFile()) {
    					try {
							jarFile.createNewFile();
							File srcJarFile = new File(filename);
							if (srcJarFile.exists() && srcJarFile.isFile()) {
								copyJarFile(srcJarFile, jarFile);
							}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
    				}
    				updateUniqueFile(path, jarFile.getAbsolutePath(), curNode);
					DefaultTreeModel model = (DefaultTreeModel)(projectTree.getModel());
			    	DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
			    	model.setRoot(root);
                    
                }
                fd.dispose();
			}
		});
    	
    	classFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (curNode == null) {
					return ;
				}
				FileDialog fd = new FileDialog(ConsoleCenter.mainFrame,"导入class文件",FileDialog.LOAD);
                fd.setFile("*.class");
                fd.setVisible(true);
                String filename="";
                if(fd.getDirectory()!=null && fd.getFile()!=null){
                    filename = fd.getDirectory() + fd.getFile();
                    String path = ((MyTreeNode)curNode).getFileName();
                    path = path.substring(0, path.lastIndexOf('\\')) + File.separator + "Library";
    				File file = new File(path + "\\classes");
    				if (!file.exists() && !file.isDirectory()) {
    					file.mkdirs();					   				    					
    				}
    				File classFile = new File(path + "\\classes" + File.separator + fd.getFile());
    				if (!classFile.exists() && !classFile.isFile()) {
    					try {
    						classFile.createNewFile();
							File srcClassFile = new File(filename);
							if (srcClassFile.exists() && srcClassFile.isFile()) {
								copyFile(srcClassFile, classFile);
							}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
    				}   
    				updateUniqueFile(path, classFile.getAbsolutePath(), curNode);
					DefaultTreeModel model = (DefaultTreeModel)(projectTree.getModel());
			    	DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
			    	model.setRoot(root);
                }
                fd.dispose();
			}
		});
    	
    	delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (curSelectNode == null || curSelectNode.isRoot()) {
					return ;
				}
				DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)curSelectNode.getParent();
				DefaultMutableTreeNode nextNode = parentNode;
				if (parentNode.getChildCount() > 1) {
					nextNode = (DefaultMutableTreeNode)parentNode.getChildAfter(curSelectNode);
					if (nextNode == null) {
						nextNode = (DefaultMutableTreeNode)parentNode.getChildBefore(curSelectNode);
					}
				}
				String path = ((MyTreeNode)curSelectNode).getFileName();
				parentNode.remove(curSelectNode);
				if (path.endsWith(".ajp")) {
					ConsoleCenter.projectManager.CloseProject();
					setCurProject(ConsoleCenter.projectManager.getCurProject());
					path = path.substring(0, path.lastIndexOf('\\'));
				}
				
				removeAllFile(path);

				setCurrentSelectNode(nextNode);
				DefaultTreeModel model = (DefaultTreeModel)(projectTree.getModel());
		    	DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
		    	model.setRoot(root);
			}
		});
    }
    
    private void removeAllFile(String path) {
    	File file = new File(path);
    	if (file.exists() && file.isDirectory()) {
    		File[] files = file.listFiles();
    		for (int i = 0; i < files.length; ++i) {
    			if (files[i].isFile()) {
    				int index = ConsoleCenter.mainFrame.rightPartPane.codePane.getTabIndex(files[i].getAbsolutePath());
    				if (index >= 0) {
    					ConsoleCenter.mainFrame.rightPartPane.codePane.closeTab(index);
    				}
    				files[i].delete();
    			}
    			else {
    				removeAllFile(files[i].getAbsolutePath());
    			}
    		}
    	}
    	else if (file.exists() && file.isFile()) {
    		int index = ConsoleCenter.mainFrame.rightPartPane.codePane.getTabIndex(file.getAbsolutePath());
    		if (index >= 0) {
    			ConsoleCenter.mainFrame.rightPartPane.codePane.closeTab(index);
    		}
    	}
    	file.delete();
    }
    
    private void copyFile(File sourceFile, File destFile) {
    	try {
			FileInputStream input = new FileInputStream(sourceFile);
			BufferedInputStream bufInput = new BufferedInputStream(input);
			
			FileOutputStream output = new FileOutputStream(destFile);
			BufferedOutputStream bufOutput = new BufferedOutputStream(output);
			
			byte[] buffer = new byte[1024];
			int len = bufInput.read(buffer, 0, buffer.length);
			while (len != -1) {
				bufOutput.write(buffer, 0, len);
				len = bufInput.read(buffer, 0, buffer.length);
			}
			
			bufOutput.flush();
			bufInput.close();
			input.close();
			bufOutput.close();
			output.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void copyJarFile(File sourceJarFile, File destJarFile) {
    	
		try {
			FileInputStream input= new FileInputStream(sourceJarFile);
			BufferedInputStream bufInput = new BufferedInputStream(input);
			JarInputStream srcBuffer = new JarInputStream(bufInput);
			Manifest manifest = srcBuffer.getManifest();
			
			FileOutputStream output = new FileOutputStream(destJarFile);
			BufferedOutputStream bufOutput = new BufferedOutputStream(output);
			JarOutputStream destBuffer = null;
			if (manifest == null) {
				destBuffer = new JarOutputStream(bufOutput);
			}
			else {
				destBuffer = new JarOutputStream(bufOutput, manifest);
			}
					
			byte[] buffer = new byte[1024];
			while (true) {
				ZipEntry entry = srcBuffer.getNextEntry();
				if (entry == null) {
					break;
				}
				destBuffer.putNextEntry(entry);
				int len = srcBuffer.read(buffer, 0, buffer.length);
				while (len != -1) {
					destBuffer.write(buffer, 0, len);
					len = srcBuffer.read(buffer, 0, buffer.length);
				}
			}
	
			srcBuffer.close();
			bufInput.close();
			input.close();
			destBuffer.finish();
			destBuffer.close();
			bufOutput.close();
			output.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    private DefaultMutableTreeNode newPrjTreeNode(){
    	DefaultMutableTreeNode root = new MyTreeNode("");         
        return root;
    }


    private DefaultMutableTreeNode newStructTreeNode(){
        DefaultMutableTreeNode top =new DefaultMutableTreeNode("Structs");
        DefaultMutableTreeNode category = null;
        DefaultMutableTreeNode book = null;

        category = new DefaultMutableTreeNode("Imports");
        top.add(category);

        //original Tutorial
        category = new DefaultMutableTreeNode("TestAll");//Classes");
        top.add(category);
        book = new DefaultMutableTreeNode("main");
        category.add(book);

        return top;
    }

    protected boolean clearProject(){
        this.projectTree.removeAll();
        this.structTree.removeAll();
        return true;
    }
    
    public void updateUniqueFile(String beginFileName, String endFileName, DefaultMutableTreeNode parent) {
    	if (beginFileName == null || endFileName == null || parent == null) {
    		return ;
    	}
    	if (((MyTreeNode)parent).isFolder == false) {
    		return ;
    	}
    	String fileName = beginFileName;
    	File aFile = new File(fileName);
    	if (parent.getChildCount() > 0) {
    		MyTreeNode node = (MyTreeNode)parent.getFirstChild();
    		while (node != null) {
    			if (node.filename != null && fileName.compareTo(node.filename) == 0) {
    				int index = endFileName.indexOf('\\', fileName.length() + 1);
    				String increasePath = "";
    				if (index <= 0) {
    					increasePath = endFileName;
    				}
    				else {
    					increasePath = endFileName.substring(0, index);
    				}
    				updateUniqueFile(increasePath, endFileName, node);
    				return ;
    			}
    			node = (MyTreeNode)parent.getChildAfter(node);
    		}    		
    	}
    	
    	MyTreeNode filenode = new MyTreeNode(aFile.getName());
        parent.add(filenode);
        filenode.filename = aFile.getAbsolutePath();
        if(aFile.isDirectory()){
            filenode.isFolder = true;
            updatePrjFiles(filenode.filename+File.separator,filenode);
        }
        else if (aFile.getName().endsWith(".jar")) {
        	filenode.isFolder = true;
        	getClassesFromJarFile(filenode.filename, filenode);
        }
    }
    
    public void updateFile(String fileName, DefaultMutableTreeNode parent) {
    	if (fileName == null || parent == null) {
    		return ;
    	}
    	File aFile = new File(fileName);
    	MyTreeNode filenode = new MyTreeNode(aFile.getName());
    	if((filenode.toString()).equals("Admin.java"))
    		return;
        parent.add(filenode);
        filenode.filename = aFile.getAbsolutePath();
        if(aFile.isDirectory()){
            filenode.isFolder = true;
            updatePrjFiles(filenode.filename+File.separator,filenode);
        }
        else if (aFile.getName().endsWith(".jar")) {
        	filenode.isFolder = true;
        	getClassesFromJarFile(filenode.filename, filenode);
        }
    }

    public void updatePrjFiles(String path,DefaultMutableTreeNode parent){
        MyTreeNode filenode = null;
        File files = new File(path);
        String[] filenames = files.list();
        
        File[] f = files.listFiles();
        
        for(int i=0;i<filenames.length;i++){
              String str = filenames[i];
            int ch = f[i].getPath().indexOf("src\\");
            int ar = f[i].getPath().lastIndexOf("\\");
            if(ch>=0)
            {
                if(ar>=0&&ar+1!=ch+4)
                {
                    if(f[i].getPath().substring(ch+4,ar).equals("standard"))
                	continue;
                }
                else if(ar==ch+3)
                {
                	if(f[i].getPath().substring(ch+4).equals("standard"))
                	continue;
                }
            }
             if(str.equals(".")||str.equals("..")) 
            	{

            	 continue;
            	}
//            File aFile = new File(path+str);
//            filenode = new MyTreeNode(aFile.getName());
//            parent.add(filenode);
//            filenode.filename = aFile.getAbsolutePath();
//            if(aFile.isDirectory()){
//                filenode.isFolder = true;
//                updatePrjFiles(filenode.filename+File.separator,filenode);
//            }
//            else if (aFile.getName().endsWith(".jar")) {
//            	filenode.isFolder = true;
//            	getClassesFromJarFile(filenode.filename, filenode);
//            }
            updateFile(path + str, parent);
        }

    }

    private void getClassesFromJarFile(String jar, DefaultMutableTreeNode parent){
    	try {
			JarFile jFile = new JarFile(jar);
			Enumeration files = jFile.entries();
			String prex = null;
			MyTreeNode curNode = null;
			while(files.hasMoreElements()) {
				JarEntry entry = (JarEntry)files.nextElement();
				String str = entry.getName();
				if (str.endsWith("/")) {
					continue;
				}
				int index = str.lastIndexOf('/');
				if (index < 0) {
//					System.out.println(str);
					MyTreeNode node = new MyTreeNode(str);
					node.filename = str;
					parent.add(node);
					continue;
				}
				String subStr = str.substring(0, index);
				if (subStr.equals(prex)) {
					MyTreeNode node = new MyTreeNode(str.substring(index+1, str.length()));
					node.filename = str.replace('/', '.');
					curNode.add(node);
				}
				else {
					prex = subStr;
					subStr = subStr.replace('/', '.');
					curNode = new MyTreeNode(subStr);
					curNode.filename = subStr;
					parent.add(curNode);
					MyTreeNode node = new MyTreeNode(str.substring(index+1, str.length()));
					node.filename = str.replace('/', '.');
					curNode.add(node);
				}
//				System.out.println(str);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    
    private void createPrjTreeNode(Project prj){
    	
        MyTreeNode top =new MyTreeNode(prj.shortName);
        top.isFolder = true;
        top.filename = prj.projectPath + prj.shortName;
        
        JavaProject jp = (JavaProject)prj;
        MyTreeNode srcnode = new MyTreeNode("src");
        srcnode.isFolder = true;
        srcnode.filename = jp.getSourcePath();       
        updatePrjFiles(jp.getSourcePath()+File.separator,srcnode); 
        top.add(srcnode);
        
        // 一般来说，应该需要将jrenode的isFolder设为true的，但是这里不影响，而且新建包之类的不需要展开jre文件，所以暂时为默认的false
        MyTreeNode jrenode = new MyTreeNode("JRE System Library [JavaSE-"+System.getProperty("java.specification.version")+"]");
//        jrenode.isFolder = true;
//        jrenode.filename = System.getProperty("java.home");
        top.add(jrenode);
        updatePrjFiles(System.getProperty("java.home") + File.separator, jrenode);
        
        File file = new File(jp.projectPath + "\\Library");
        if (file.exists() && file.isDirectory()) {
        	MyTreeNode libraryNode = new MyTreeNode("Library");
        	libraryNode.isFolder = true;
        	libraryNode.filename = file.getAbsolutePath();
        	updatePrjFiles(jp.projectPath + "\\Library"+ File.separator, libraryNode);
        	top.add(libraryNode);
        }
        
        
        
        DefaultTreeModel model = (DefaultTreeModel)(this.projectTree.getModel());
    	DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
    	root.add(top);
    	curNode = top;
    	setCurrentSelectNode(curNode);
    	model.setRoot(root);
//        this.projectTree.setRootVisible(true);
    }
    
    public DefaultMutableTreeNode findTreeNode(String name) {
    	DefaultTreeModel model = (DefaultTreeModel)(this.projectTree.getModel());
     	DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
     	MyTreeNode node = (MyTreeNode)root.getFirstChild();
     	while (node != null) {
     		if (node.filename.compareTo(name) == 0) {
     			return node;
     		}
     		else if (!name.endsWith(".ajp")) {
     			String path = node.filename.substring(0, node.filename.lastIndexOf('\\'));
     			if (name.regionMatches(0, path, 0, path.length())) {
     				MyTreeNode curNode = (MyTreeNode)findNode(node, name);
     				if (curNode != null) {
     					return curNode;
     				}
     			}
     		}
     		node = (MyTreeNode)root.getChildAfter(node);
     	}
     	return node;
    }
    
    public DefaultMutableTreeNode findNode(DefaultMutableTreeNode node, String name) {
    	if (node.getChildCount() <= 0) {
    		return null;
    	}
    	MyTreeNode curNode = (MyTreeNode)node.getFirstChild();    	
    	while (curNode != null) {
    		if (curNode.filename.compareTo(name) == 0) {
    			return curNode;
    		}
    		else if(name.regionMatches(0, curNode.filename, 0, curNode.filename.length())){
    			return findNode(curNode, name);
    		}
    		curNode = (MyTreeNode)node.getChildAfter(curNode);
    	}
    	return curNode;
    }
    
    private void updateCurrentNode(Project prj) {
    	this.curNode = findTreeNode(prj.projectPath + prj.shortName);
    }
    
    private DefaultMutableTreeNode getCurrentNode() {
    	return curNode;
    }
    
    public void setCurrentSelectNode(DefaultMutableTreeNode selectNode) {
    	this.curSelectNode = selectNode;
    }
    
    
    public DefaultMutableTreeNode getCurrentSelectNode() {
    	return curSelectNode;
    }
    
    public String getCurrentSelectNodeFolderPath() {
    	MyTreeNode node = (MyTreeNode)getCurrentSelectNode();
		if (node == null) {
			return null;
		}
		String path = node.filename;
		if (node.isFolder == false || node.filename.endsWith(".ajp")) {
			path = path.substring(0, path.lastIndexOf('\\'));
		}
		return path;
    }
    
    public void updateProject(Project prj) {
    	DefaultTreeModel model = (DefaultTreeModel)(this.projectTree.getModel());
    	DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
    	DefaultMutableTreeNode curNode = getCurrentNode();
    	TreeNode Node = root.getChildAfter(curNode);
    	root.remove(curNode);
    	if (Node == null && !root.isLeaf())
    		Node = root.getFirstChild();
    	this.curNode = (DefaultMutableTreeNode)Node;
    	model.setRoot(root);
    	setCurProject(prj);
    }


    public boolean openProject(Project prj){
    	if (prj == null) {
    		return false;
    	}    	
    	if(cur_project!=prj){
    		setCurProject(prj);
            createPrjTreeNode(prj);
        }
        return true;
    }
    
    public Project getCurProject() {
    	return cur_project;
    }
    
    public void setCurProject(Project prj) {
    	this.cur_project = prj;
    	if (prj != null) {
    		String name = prj.shortName;
    		name = name.substring(0, name.lastIndexOf('.'));
    		ConsoleCenter.mainFrame.setTitle("Anyview for Java - " + name);
    	}
    }
    

    public class MyTreeNode extends DefaultMutableTreeNode{
        private boolean isFolder = false;
        private String filename;
        public MyTreeNode(Object userObject){
            super(userObject);
        }
        
        public boolean isFolder() {
        	return this.isFolder;
        }
        public String getFileName() {
        	return this.filename;
        }
        
        public void setFolder(boolean isFolder) {
        	this.isFolder = isFolder;
        }
        public void setFileName(String fileName) {
        	this.filename = fileName;
        }
    }
  /*  
    private class PrjTree extends JTree {
    	MyTreeNode root = new MyTreeNode("");
    	PrjTree(DefaultMutableTreeNode pRoot) {
    		super(pRoot);
    		root.add(pRoot);
    	}
    }*/
}
