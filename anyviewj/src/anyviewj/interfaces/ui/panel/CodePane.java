package anyviewj.interfaces.ui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import org.apache.commons.lang.StringUtils;

import anyviewj.console.ConsoleCenter;
import anyviewj.debug.manager.PathManager;
import anyviewj.debug.session.Session;
import anyviewj.debug.source.SourceFactory;
import anyviewj.debug.source.SourceSource;
import anyviewj.interfaces.actions.CommandManager;
import anyviewj.interfaces.resource.IconResource;
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
 *
 */
public class CodePane extends JPanel {
    //

    public final CodeTabPane fileTabPane;
    ;
    //工具栏
    public final JToolBar toolbar = new JToolBar();
    //工具栏按钮集
    public final ToolButtons toolButtons;
    private ArrayList filePaneList = new ArrayList();
    //控制中心
    public final ConsoleCenter center;

    public  FilePane fp;
    public List<FilePane> fpaneList = new ArrayList<FilePane>();
//    public List allFileList = new ArrayList();
//    public List prjList = new ArrayList();
    
    public CodePane(ConsoleCenter center) {
        super(new BorderLayout());
        this.center = center;
        IconResource ir = center.resourceManager.getIconResource();
        //初始化工具栏
        toolbar.setBorder(BorderFactory.createEtchedBorder(Color.white, new Color(165, 163, 151)));
        toolButtons = new ToolButtons(ConsoleCenter.commandManager, toolbar);
        //代码面板添加选项卡和文件面板
        toolbar.setMinimumSize(new Dimension(180, toolbar.getHeight()));

        fileTabPane = new CodeTabPane(center, null, SwingConstants.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        addTabCloseListener();

        add(fileTabPane, BorderLayout.CENTER);
        
    }
    
//    public void searchAllFile(String sPath, JavaProject jp){
//    	File f = new File(sPath);
//    	File[] fl = f.listFiles();
////    	System.out.println("Digui  @@@@@@2  !!!  : "+sPath);
//    	for(int i = 0;i<fl.length;i++)
//    	{
//    		File f2 = fl[i];
//    		if(f2.getName().endsWith(".java"))
//    		{
//
//    			  String str = jp.getSourcePath() + File.separator +f2.getName();
//                System.out.println("AAAAAAAAAAAAa  "+str);
//                allFileList.add(str);
// //   			System.out.println("Digui  !!!  : "+f2.getName());
//    		}
//    		if(f2.isDirectory())
//    			searchAllFile(f2.getAbsolutePath(),jp);
//    	}
//    }
//    
//    
//    public void setprjList(Project prj){
//    	allFileList.clear();
//    	prjList.add(prj);
//    	for(int i = 0;i<prjList.size();i++){
//    		JavaProject jp = (JavaProject) ((Project)prjList.get(i));
//    		searchAllFile(jp.getSourcePath(),jp);
//    		System.out.println("!!!!!!!!!  : "+((Project)prjList.get(i)).shortName+"  ");
////            for (int x = 0; x < jp.allList.size(); x++) {
////                String str = jp.getSourcePath() + File.separator +jp.allList.get(x);
////                System.out.println("AAAAAAAAAAAAa  "+str);
////                allFileList.add(str);
////                
////            }
//    	}
//        
//    }
    
    
    
    public FilePane newFilePane(Project prj, String fileName, boolean isNewFile, Session session) {
        // 需要将当前文件关联到prj项目中，且此文件不在打开列表中，则isNewFile设置为true(一般情况下都应设为true)

    	
        if (fileName == null || StringUtils.isEmpty(fileName)) {
            return null;
        }
       // FilePane fp;
      //  System.out.println("newFilePane 1");
        for (int i = 0, count = this.filePaneList.size(); i < count; i++) {
         //   System.out.println("newFilePane 1.1 " + i);
            fp = (FilePane) (this.filePaneList.get(i));
         //   System.out.println("newFilePane 1.2 " + i);
            if (fp.source.getFilePath().compareToIgnoreCase(fileName) == 0) {
              //  System.out.println("newFilePane 1.3 " + i);
                if (fileTabPane.getSelectedIndex() != fileTabPane.getTabComponentIndex(fp)) {
               //     System.out.println("newFilePane 1.3.1 " + i);
                    fileTabPane.setSelectedComponent(fp);
              //      System.out.println("newFilePane 1.3.2 " + i);
                }
             //   System.out.println("newFilePane 1.4 " + i);
                return fp;
            }
          //  System.out.println("newFilePane 1.5 " + i);
        }

       // System.out.println("newFilePane 2");
        File file = new File(fileName);
        PathManager pathman = (PathManager) session.getManager(PathManager.class);
        SourceSource src = SourceFactory.getInstance().create(file, pathman);
        fp = new FilePane(center, fileTabPane, session, src);
        String name = file.getName();
        if (prj != null) {
            fp.source.setName(prj.projectPath + prj.shortName);
            fp.setPrj(prj);
            
            if (isNewFile == true) {
                String str = file.getAbsolutePath().substring(((JavaProject) prj).getSourcePath().length() + 1);
                ((JavaProject) prj).fileList.add(str);
                ConsoleCenter.projectManager.StoreProject(prj); //没必要每次打开都保存，只需要关闭的时候保存即可，以后修改
            }
        }
        //System.out.println("newFilePane 3");

        if (name != null && name.endsWith(".java")) {
            name = name.substring(0, name.length() - 5);
        }
      
        //System.out.println("newFilePane 4");

        this.filePaneList.add(fp);
        fp.source.setFilePath(file.getAbsolutePath());
        IconResource ir = center.resourceManager.getIconResource();
        this.fileTabPane.addTab(name, ir.getIcon(IconResource.TABPANE_CLOSE_WITHOUT_SAVE), fp);
//        System.out.println("^^ in CodePane : 2");
//        System.out.println("fileName : "+fileName);
        this.fileTabPane.setSelectedComponent(fp);
//        System.out.println("newFilePane 5");
        
        return fp;
    }

    public void closeTab(int index) {
        FilePane fp = (FilePane) (fileTabPane.getTabComponentAt(index));
//      System.out.println(fp.source.getFilePath()+"\n");
        System.out.println(fp.source.getName());
        JavaProject prj = (JavaProject) ((JavaProjectManager) ConsoleCenter.projectManager).findProject(fp.source.getName());
        if (prj != null) {
            String fileName = fp.source.getFilePath();
            String prePrjName = prj.getSourcePath();
//      	prePrjName = prePrjName.substring(0, prePrjName.length()-5);
            for (int i = 0; i < prj.fileList.size(); ++i) {
//      		System.out.println(prj.fileList.get(i));
                if (fileName.compareToIgnoreCase(prePrjName + "\\" + prj.fileList.get(i)) == 0) {
                    prj.fileList.remove(i);
                    if (prj.fileList.size() == 0) {
                        prj.setPrjFileIndex(-1);
                    }
                    ConsoleCenter.projectManager.StoreProject(prj);//没必要每次关闭都保存，只需要关闭项目的时候保存即可，以后修改
                    break;
                }
            }
        }
        filePaneList.remove(fp);
        fileTabPane.removeTabAt(index);
        fileTabPane.updateTabInfo();
    }

    private void addTabCloseListener() {
        this.fileTabPane.addClickIconListener(new CodeTabPane.ClickIconListener() {
            @Override
			public void clickIcon(CodeTabPane.ClickIconEvent e) {
                closeTab(e.index);
            }
        });
    }

    public boolean openProject(Project prj, Session session) {
//        this.fileTabPane.removeAllTabs();
//        this.filePaneList.clear();
        int count = fileTabPane.getTabCount();
        JavaProject jp = (JavaProject) prj;
        for (int i = 0; i < jp.fileList.size(); i++) {
            String str = (String) jp.fileList.get(i);
            FilePane xfp;
            xfp = this.newFilePane(jp, jp.getSourcePath() + File.separator + str, false, session);
            fpaneList.add(xfp);
        }
        int index = ((JavaProject) prj).getPrjFileIndex();        	
        if (index >= 0) {
//            int idx = fileTabPane.getTabComponentIndex(fileTabPane.getTabComponentAt(index));
            fileTabPane.setSelectedIndex(index + count);
            fileTabPane.updateTabInfo();
        }
        return true;
    }

    public boolean closeProject(Project prj) {
        JavaProject jp = (JavaProject) prj;
        FilePane fp = null;
        String fileName = null;
        for (int i = 0, prjOpenFileCount = jp.fileList.size(); i < prjOpenFileCount; ++i) {
            fileName = jp.getSourcePath() + File.separator + jp.fileList.get(i);
            for (int j = 0, count = this.filePaneList.size(); j < count; j++) {
                fp = (FilePane) (this.filePaneList.get(j));
                if (fp.source.getFilePath().compareToIgnoreCase(fileName) == 0) {
                    filePaneList.remove(fp);
                    fileTabPane.removeTabAt(j);
                    break;
                }
            }
        }
        fileTabPane.updateTabInfo();
        return true;
    }

    public String getTabName(int index) {
        FilePane fp = (FilePane) (fileTabPane.getTabComponentAt(index));
        return fp.source.getFilePath();
    }

    public int getTabIndex(String path) {
        if (path == null) {
            return -1;
        }
        for (int i = filePaneList.size() - 1; i >= 0; --i) {
            FilePane fp = (FilePane) filePaneList.get(i);
            if (path.compareTo(fp.source.getFilePath()) == 0) {
                return i;
            }
        }
        return -1;
    }

    
    public class ToolButtons {
        //工具栏
        //文件按钮

        public final JButton toolButtonFile_New = new JButton(); //新建项目,类,接口,文件等子按钮
        public final JButton toolButtonFile_OpenFile = new JButton(); //打开文件子按钮
        //public final JButton toolButtonFile_CloseFile = new JButton();//关闭文件子按钮
        public final JButton toolButtonFile_SaveFile = new JButton(); //保存文件子按钮
        //public final JButton toolButtonFile_SaveAll = new JButton();//保存全部文件子按钮
        //编辑按钮
        public final JButton toolButtonEdit_Undo = new JButton(); //撤销子按钮
        public final JButton toolButtonEdit_Redo = new JButton(); //重做子按钮
        public final JButton toolButtonEdit_Cut = new JButton(); //剪切子按钮
        public final JButton toolButtonEdit_Copy = new JButton(); //复制子按钮
        public final JButton toolButtonEdit_Paste = new JButton(); //粘贴子按钮
        //public final JButton toolButtonEdit_Delete = new JButton();//删除子按钮
        //查找按钮
        public final JButton toolButtonSearch_Find = new JButton(); //查找子按钮
        //public final JButton toolButtonSearch_FindInPath = new JButton();//在指定路径查找子按钮
        public final JButton toolButtonSearch_FindDown = new JButton(); //查找下一个子按钮
        public final JButton toolButtonSearch_FindUp = new JButton(); //查找上一个子按钮
        public final JButton toolButtonSearch_Replace = new JButton(); //替换子按钮
        //public final JButton toolButtonSearch_ReplaceInPath = new JButton();//在指定路径替换子按钮
        public final JButton toolButtonSearch_Goto = new JButton(); //跳至子按钮
        //项目
        public final JButton toolButtonProject_CompileFile = new JButton(); //编译文件子按钮
        //运行
        //public final JButton toolButtonRun_RunProject = new JButton();//运行项目子按钮
        public final JButton toolButtonRun_RunFile = new JButton(); //运行文件子按钮
        //public final JButton toolButtonRun_DebugProject = new JButton();//调试项目子按钮
        public final JButton toolButtonRun_DebugFile = new JButton(); //调试文件子按钮
        //public final JButton toolButtonRun_DebugProjectTime = new JButton();//定时执行调试项目子按钮
        public final JButton toolButtonRun_DebugFileTime = new JButton(); //定时执行调试文件子按钮
        public final JButton toolButtonRun_Pause = new JButton(); //暂停子按钮
        public final JButton toolButtonRun_Stop = new JButton(); //停止子按钮
        public final JButton toolButtonRun_StepOver = new JButton(); //下一行子按钮
        public final JButton toolButtonRun_StepInto = new JButton(); //下一步子按钮
        public final JButton toolButtonRun_SingleInstruction = new JButton(); //单指令子按钮

        private ToolButtons(CommandManager cm, JToolBar toolbar) {
            //文件按钮设置动作
            toolButtonFile_New.putClientProperty("hideActionText", Boolean.TRUE); //新建项目,类,接口,文件等子按钮
            toolButtonFile_OpenFile.putClientProperty("hideActionText",
                    Boolean.TRUE); //打开文件子按钮
            //toolButtonFile_CloseFile.putClientProperty("hideActionText",Boolean.TRUE);//关闭文件子按钮
            toolButtonFile_SaveFile.putClientProperty("hideActionText",
                    Boolean.TRUE); //保存文件子按钮
            //toolButtonFile_SaveAll.putClientProperty("hideActionText",Boolean.TRUE);//保存全部文件子按钮

            toolButtonFile_New.setAction(cm.fileActions.newAction); //新建项目,类,接口,文件等子按钮
            toolButtonFile_OpenFile.setAction(cm.fileActions.openFileAction); //打开文件子按钮
            //toolButtonFile_CloseFile.setAction(cm.fileActions.closeFileAction);//关闭文件子按钮
            toolButtonFile_SaveFile.setAction(cm.fileActions.saveFileAction); //保存文件子按钮
            //toolButtonFile_SaveAll.setAction(cm.fileActions.saveAllAction);//保存全部文件子按钮

            //编辑按钮
            toolButtonEdit_Undo.putClientProperty("hideActionText", Boolean.TRUE); //撤销子按钮
            toolButtonEdit_Redo.putClientProperty("hideActionText", Boolean.TRUE); //重做子按钮
            toolButtonEdit_Cut.putClientProperty("hideActionText", Boolean.TRUE); //剪切子按钮
            toolButtonEdit_Copy.putClientProperty("hideActionText", Boolean.TRUE); //复制子按钮
            toolButtonEdit_Paste.putClientProperty("hideActionText", Boolean.TRUE); //粘贴子按钮
            //toolButtonEdit_Delete.putClientProperty("hideActionText",Boolean.TRUE);//删除子按钮

            toolButtonEdit_Undo.setAction(cm.editActions.undoAction); //撤销子按钮
            toolButtonEdit_Redo.setAction(cm.editActions.redoAction); //重做子按钮
            toolButtonEdit_Cut.setAction(cm.editActions.cutAction); //剪切子按钮
            toolButtonEdit_Copy.setAction(cm.editActions.copyAction); //复制子按钮
            toolButtonEdit_Paste.setAction(cm.editActions.pasteAction); //粘贴子按钮
            //toolButtonEdit_Delete.setAction(cm.editActions.deleteAction);//删除子按钮

            //查找按钮
            toolButtonSearch_Find.putClientProperty("hideActionText", Boolean.TRUE); //查找子按钮
            //toolButtonSearch_FindInPath.putClientProperty("hideActionText",Boolean.TRUE);//在指定路径查找子按钮
            toolButtonSearch_FindDown.putClientProperty("hideActionText",
                    Boolean.TRUE); //查找下一个子按钮
            toolButtonSearch_FindUp.putClientProperty("hideActionText",
                    Boolean.TRUE); //查找上一个子按钮
            toolButtonSearch_Replace.putClientProperty("hideActionText",
                    Boolean.TRUE); //替换子按钮
            //toolButtonSearch_ReplaceInPath.putClientProperty("hideActionText",Boolean.TRUE);//在指定路径替换子按钮
            toolButtonSearch_Goto.putClientProperty("hideActionText", Boolean.TRUE); //跳至子按钮

            toolButtonSearch_Find.setAction(cm.searchActions.findAction); //查找子按钮
            //toolButtonSearch_FindInPath.setAction(cm.searchActions.findInPathction);//在指定路径查找子按钮
            toolButtonSearch_FindDown.setAction(cm.searchActions.findDownAction); //查找下一个子按钮
            toolButtonSearch_FindUp.setAction(cm.searchActions.findUpAction); //查找上一个子按钮
            toolButtonSearch_Replace.setAction(cm.searchActions.replaceAction); //替换子按钮
            //toolButtonSearch_ReplaceInPath.setAction(cm.searchActions.replaceInPathAction);//在指定路径替换子按钮
            toolButtonSearch_Goto.setAction(cm.searchActions.gotoAction); //跳至子按钮
            //项目
            toolButtonProject_CompileFile.putClientProperty("hideActionText",
                    Boolean.TRUE); //编译文件子按钮
            toolButtonProject_CompileFile.setAction(cm.projectActions.compileFileAction); //编译文件子按钮
            //运行设置动作
            //toolButtonRun_RunProject.putClientProperty("hideActionText",Boolean.TRUE);//运行项目子按钮
            toolButtonRun_RunFile.putClientProperty("hideActionText", Boolean.TRUE); //运行文件子按钮
            //toolButtonRun_DebugProject.putClientProperty("hideActionText",Boolean.TRUE);//调试项目子按钮
            toolButtonRun_DebugFile.putClientProperty("hideActionText",
                    Boolean.TRUE); //调试文件子按钮
            //toolButtonRun_DebugProjectTime.putClientProperty("hideActionText",Boolean.TRUE);//定时执行调试项目子按钮
            toolButtonRun_DebugFileTime.putClientProperty("hideActionText",
                    Boolean.TRUE); //定时执行调试文件子按钮
            toolButtonRun_Pause.putClientProperty("hideActionText", Boolean.TRUE); //暂停子按钮
            toolButtonRun_Stop.putClientProperty("hideActionText", Boolean.TRUE); //停止子按钮
            toolButtonRun_StepOver.putClientProperty("hideActionText", Boolean.TRUE); //下一行子按钮
            toolButtonRun_StepInto.putClientProperty("hideActionText", Boolean.TRUE); //下一步子按钮
            toolButtonRun_SingleInstruction.putClientProperty("hideActionText",
                    Boolean.TRUE); //单指令子按钮

            //toolButtonRun_RunProject.setAction(cm.runActions.runProjectAction);//运行项目子按钮
//            toolButtonRun_RunFile.setAction(cm.runActions.runFileAction); //运行文件子按钮
            //toolButtonRun_DebugProject.setAction(cm.runActions.debugProjectAction);//调试项目子按钮
//            toolButtonRun_DebugFile.setAction(cm.runActions.debugFileAction); //调试文件子按钮
            //toolButtonRun_DebugProjectTime.setAction(cm.runActions.debugProjectTimeAction);//定时执行调试项目子按钮
//            toolButtonRun_DebugFileTime.setAction(cm.runActions.debugFileTimeAction); //定时执行调试文件子按钮
            toolButtonRun_Pause.setAction(cm.runActions.pauseAction); //暂停子按钮

//			注释掉了Stop按钮上的动作 hou 2013年5月22日9:40:50
//            toolButtonRun_Stop.setAction(cm.runActions.stopAction); //停止子按钮
            toolButtonRun_StepOver.setAction(cm.runActions.stepOverAction); //下一行子按钮
            toolButtonRun_StepInto.setAction(cm.runActions.stepIntoAction); //下一步子按钮
            toolButtonRun_SingleInstruction.setAction(cm.runActions.singleInstructionAction); //单指令子按钮

            toolbar.add(toolButtonFile_New);
            toolbar.add(toolButtonFile_OpenFile);
            toolbar.add(toolButtonFile_SaveFile);
            toolbar.addSeparator();
            toolbar.add(toolButtonEdit_Undo);
            toolbar.add(toolButtonEdit_Redo);
            toolbar.add(toolButtonEdit_Copy);
            toolbar.add(toolButtonEdit_Paste);
            toolbar.add(toolButtonEdit_Cut);
            toolbar.addSeparator();
            toolbar.add(toolButtonSearch_Find);
            toolbar.add(toolButtonSearch_FindDown);
            toolbar.add(toolButtonSearch_FindUp);
            toolbar.add(this.toolButtonSearch_Replace);
            toolbar.add(this.toolButtonSearch_Goto);
            toolbar.addSeparator();
            toolbar.add(toolButtonProject_CompileFile);
//            toolbar.add(toolButtonRun_RunFile);
//            toolbar.add(toolButtonRun_DebugFile);
//            toolbar.add(toolButtonRun_DebugFileTime);
            toolbar.add(toolButtonRun_Stop);
            toolbar.add(toolButtonRun_Pause);
            toolbar.add(toolButtonRun_StepOver);
            toolbar.add(toolButtonRun_StepInto);
            toolbar.add(toolButtonRun_SingleInstruction);
            /*/JButton b = new JButton("asdf");
             b.setAction(new AbstractAction(){
             public void actionPerformed(ActionEvent e) {
             Dimension dm = toolbar.getMinimumSize();
             System.out.println("new dm = "+dm);
             }
             });
             toolbar.add(b,0);//*/
        }
    }
}
//package anyviewj.interfaces.ui;
//
//import java.awt.Component;
//import java.awt.GridLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseEvent;
//import java.io.File;
//import java.util.Hashtable;
//import java.util.prefs.Preferences;
//
//import javax.swing.JMenu;
//import javax.swing.JMenuItem;
//import javax.swing.JPanel;
//import javax.swing.JTabbedPane;
//
//import anyviewj.console.ConsoleCenter;
//import anyviewj.debug.manager.PathManager;
//import anyviewj.debug.session.Session;
//import anyviewj.debug.source.SourceFactory;
//import anyviewj.debug.source.SourceSource;
//import anyviewj.interfaces.resource.IconResource;
//import anyviewj.interfaces.view.JavaSourceView;
//import anyviewj.interfaces.view.View;
//import anyviewj.interfaces.view.ViewException;
//import anyviewj.util.Defaults;
////import com.bluemarsh.jswat.ui.SmartPopupMenu;
//
///**
// * Class TabbedViewDesktop implements a ViewDesktop using a tabbed pane
// * component.
// *
// * @author  ltt
// */
//public class CodePane extends JPanel{
//    /** The preferences node. */
//    private static Preferences preferences;
//    /** The tabbed pane. */
//    private JTabbedPane tabbedPane;
//    /** Our view manager instance. */
//    //private ViewManager viewManager;
//    /** Map of View objects to Component objects. */
//    private Hashtable viewToComponentMap;
//    /** Map of Component objects to View objects. */
//    private Hashtable componentToViewMap;
//    //控制中心
//    public final ConsoleCenter center;
//    /**
//     * hou 2013年5月19日18:17:24
//     * 用以保存每个View对应的文件的文件名
//     */
//    private Hashtable<String, JavaSourceView> fileNameToView = null;
//    static {
//        preferences = Preferences.userRoot().node(
//            "com/bluemarsh/jswat/ui/graphical");
//    }
//
//    /**
//     * Constructs a TabbedViewDesktop.
//     *
//     * @param  vm  view manager.
//     */
//    public CodePane(ConsoleCenter center){
//        //viewManager = vm;
//    	super();
//        this.center = center;
//        setLayout(new GridLayout(1, 1));        
//        
//        tabbedPane = new JTabbedPane();
//        setPreferences();       
//        // Don't add the panel just yet, or it completely covers our
//        // beautiful background color.
//        viewToComponentMap = new Hashtable();
//        componentToViewMap = new Hashtable();
//        fileNameToView     = new Hashtable();
//
//        // Add the popup menu for the tabbed pane.
//        TabbedPopup popupMenu = new TabbedPopup();
//        tabbedPane.addMouseListener(popupMenu);
//    } // TabbedViewDesktop
//    
//    /**
//     * Called when the look and feel is changing.
//     */
//    public void updateUI() {
//        super.updateUI();        
//        if (tabbedPane!=null && getComponentCount() == 0) {
//            // Update the disconnected tabbed pane.
//            tabbedPane.updateUI();
//        }
//    }
//
//    /**
//     * Adds the appropriate display component for the given view.
//     *对于给定的视图中添加适当的显示组件
//     * @param  view  view to display.
//     * @throws  ViewException
//     *          if there was a problem displaying the view.
//     */
//    public void addView(View view) throws ViewException {
//        if (viewToComponentMap.size() == 0) {
//            // Add the tabbed pane to our panel so we can start showing
//            // views.
//            add(tabbedPane);
//        }
//        Component comp = view.getUI();
//        viewToComponentMap.put(view, comp);
//        componentToViewMap.put(comp, view);    
//        
//        tabbedPane.addTab(view.getTitle(), null, comp, view.getLongTitle());
//        tabbedPane.setSelectedComponent(comp);
//    } // addView
//    
//    
//    public JavaSourceView newFilePane(Project prj, String fileName, 
//    		boolean isNewFile, Session session) throws ViewException 
//    {
//    	// 需要将当前文件关联到prj项目中，且此文件不在打开列表中，则isNewFile设置为true(一般情况下都应设为true)
////        FilePane fp;
//    	System.out.println( "fileName = " + fileName 
//    			            + "prj = " + prj 
//    			            + "session = " + session );
//    	JavaSourceView newView = null;
//    	
//        if(this.fileNameToView.containsKey( fileName ))
//        {
//            selectView(this.fileNameToView.get( fileName ) );
//            return newView;
//        }
//        
//        File file = new File(fileName);
//        try
//        {
//        	PathManager pathman = (PathManager) session.getManager(PathManager.class);
//        	SourceSource src = SourceFactory.getInstance().create(file, pathman);
//        	newView = new JavaSourceView( src ); 
//        }
//        catch ( NullPointerException e )
//        {
//        	e.printStackTrace();
//        }
//        
////        fp = new FilePane(center, fileTabPane, session, src);
//        
//        String name = file.getName();
//        
//        if (prj != null) {
////        	newView.source.setName(prj.projectPath+prj.shortName);
////        	newView.getUI().setToolTipText( prj.projectPath+prj.shortName );
//        	if (isNewFile == true) {
//        		String str = file.getAbsolutePath().substring(((JavaProject)prj).getSourcePath().length()+1);
//        		((JavaProject)prj).fileList.add(str);
//        		center.projectManager.StoreProject(prj); //没必要每次打开都保存，只需要关闭的时候保存即可，以后修改
//        	}
//        }
//        
//        if(name!=null && name.endsWith(".java")){
//            name=name.substring(0,name.length()-5);
//        }
//        System.out.println( newView.getUI() );
//        addView( newView );
//        selectView( newView );
////        fp.source.setFilePath(file.getAbsolutePath());
////        IconResource ir = center.resourceManager.getIconResource();
////        this.fileTabPane.addTab(name,ir.getIcon(ir.TABPANE_CLOSE_WITHOUT_SAVE),fp);
////        this.fileTabPane.setSelectedComponent(fp);       
//        return newView;
//    }   
//   
//    /**
//     * 侯锦城 2013年5月19日10:34:57
//     * 在移植JavaProjectManager.java文件時移植了這個文件
//     * @param prj
//     * @param session
//     * @return
//     */
//	public boolean openProject(Project prj, Session session) {
//		// this.fileTabPane.removeAllTabs();
//		// this.filePaneList.clear();
//		int count = tabbedPane.getTabCount();
//		JavaProject jp = (JavaProject) prj;
//		for (int i = 0; i < jp.fileList.size(); i++) {
//			String str = (String) jp.fileList.get(i);
//			try {
//				this.newFilePane(jp, jp.getSourcePath() + File.separator + str,
//						false, session);
//			} catch (ViewException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		int index = ((JavaProject) prj).getPrjFileIndex();
//		if (index >= 0) {
////			int idx = fileTabPane.getTabComponentIndex(fileTabPane
////					.getTabComponentAt(index));
////			fileTabPane.setSelectedIndex(index + count);
////			fileTabPane.updateTabInfo();
//		}
//		return true;
//	}
//    /**
//     * Prepare this view desktop for non-use. Free any allocated
//     * resources, empty collections, and set any references to null.
//     */
//    public void dispose() {
//        tabbedPane.removeAll();
//        tabbedPane = null;
//        //backgroundPanel = null;
//        //viewManager = null;
//        viewToComponentMap.clear();
//        viewToComponentMap = null;
//        componentToViewMap.clear();
//        componentToViewMap = null;
//    } // dispose
//
//    /**
//     * Returns the menu for this view desktop, if any. The menu is
//     * positioned in the menu structure in place of the @window special
//     * menu.
//     *
//     * @return  window menu, or null if none.
//     */
//    public JMenu getMenu() {
//        return null;
//    } // getMenu
//
//
//    /**
//     * Retreives the currently selected view.
//     *
//     * @return  selected view, or null if none.
//     */
//    public View getSelectedView() {
//        Component comp = tabbedPane.getSelectedComponent();
//        if (comp != null) {
//            return (View) componentToViewMap.get(comp);
//        } else {
//            return null;
//        }
//    } // getSelectedView
//
//
//
//    /**
//     * Removes the given view from the desktop, as well as from the view
//     * manager.
//     *
//     * @param  view  view to remove.
//     */
//    protected void removeView(View view) {
//        Component comp = (Component) viewToComponentMap.remove(view);
//        componentToViewMap.remove(comp);
//        tabbedPane.remove(comp);
//        //viewManager.removeView(view);
//        if (viewToComponentMap.size() == 0) {
//            // Remove the tabbed pane from our panel so we can see the
//            // pretty background. Need to force a repaint, too.
//            remove(tabbedPane);
//            repaint();
//        }
//    } // removeView
//
//    /**
//     * Set the given view as the selected one. This should make the view
//     * component visible to the user.
//     *
//     * @param  view  view to be made active.
//     * @throws  ViewException
//     *          if a problem occurred.
//     */
//    public void selectView(View view) throws ViewException {
//        tabbedPane.setSelectedComponent(view.getUI());
//    } // selectView
//
//    /**
//     * Called when the preferences have changed. The view desktop may
//     * want to update its cached settings based on the changes.
//     */
//    public void setPreferences() {
//        if (preferences.getBoolean("oneRowTabs",
//                                   Defaults.VIEW_SINGLE_ROW_TABS)) {
//            tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
//        } else {
//            tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
//        }
//    } // setPreferences
//
//
//
//    /**
//     * Class TabbedPopup defines a popup menu that works specifically
//     * for the tabbed pane view desktop.
//     *
//     * @author  Nathan Fiedler
//     */
//    protected class TabbedPopup extends SmartPopupMenu
//        implements ActionListener {
//        /** silence the compiler warnings */
//        private static final long serialVersionUID = 1L;
//        /** Suffix added to command string to retrieve menu labels. */
//        private static final String LABEL_SUFFIX = "Label";
//        /** Command name for close action. */
//        private static final String CMD_CLOSE = "close";
//        /** Command name for close all action. */
//        private static final String CMD_CLOSE_ALL = "closeAll";
//        /** Command name for close all others action. */
//        private static final String CMD_CLOSE_OTHERS = "closeOthers";
//        /** Index of the tab the mouse was clicked on. */
//        private int selectedTabIndex;
//        /** Menu item to close one view. */
//        private JMenuItem closeMenuItem;
//        /** Menu item to close other views. */
//        private JMenuItem closeOthersMenuItem;
//
//        /**
//         * Create a TabbedPopup.
//         */
//        public TabbedPopup() {
//            super(Bundle.getString("TabbedPopup.title"));
//            closeMenuItem = createMenuItem(CMD_CLOSE);
//            add(closeMenuItem);
//            add(createMenuItem(CMD_CLOSE_ALL));
//            closeOthersMenuItem = createMenuItem(CMD_CLOSE_OTHERS);
//            add(closeOthersMenuItem);
//        } // TabbedPopup
//
//        /**
//         * One of the menu items we're listening to was activated.
//         *
//         * @param  ae  action event.
//         */
//        public void actionPerformed(ActionEvent ae) {
//            // Get the source of the event (it is a JMenuItem).
//            JMenuItem menuItem = (JMenuItem) ae.getSource();
//            // Get the action command.
//            String cmd = menuItem.getActionCommand();
//
//            if (cmd.equals(CMD_CLOSE)) {
//                // Close just one view.
//                Component comp = tabbedPane.getComponentAt(selectedTabIndex);
//                removeView((View) componentToViewMap.get(comp));
//            } else if (cmd.equals(CMD_CLOSE_ALL)) {
//                // Close all of the views.
//                for (int ii = tabbedPane.getTabCount() - 1; ii >= 0; ii--) {
//                    Component comp = tabbedPane.getComponentAt(ii);
//                    removeView((View) componentToViewMap.get(comp));
//                }
//            } else if (cmd.equals(CMD_CLOSE_OTHERS)) {
//                // Close all of the views but the selected.
//                Component selected = tabbedPane.getComponentAt(
//                    selectedTabIndex);
//                for (int ii = tabbedPane.getTabCount() - 1; ii >= 0; ii--) {
//                    Component comp = tabbedPane.getComponentAt(ii);
//                    if (!comp.equals(selected)) {
//                        removeView((View) componentToViewMap.get(comp));
//                    }
//                }
//            }
//        } // actionPerformed
//
//        /**
//         * This is the hook through which all menu items are created. Using
//         * the <code>cmd</code> string it finds the menu item label in the
//         * resource bundle.
//         *
//         * @param  cmd  name for this menu item, used to get the label
//         * @return  new menu item
//         */
//        protected JMenuItem createMenuItem(String cmd) {
//            // Create menu item and set the text label.
//            JMenuItem mi = new JMenuItem(Bundle.getString(cmd + LABEL_SUFFIX));
//            // Set menu action command.
//            mi.setActionCommand(cmd);
//            // Set up the action to listen for events.
//            mi.addActionListener(this);
//            return mi;
//        } // createMenuItem
//
//        /**
//         * Set the popup menu items enabled or disabled depending on
//         * whether the mouse is over a tab or not.
//         *
//         * @param  e  mouse event.
//         */
//        protected void setMenuItemsForEvent(MouseEvent e) {
//            selectedTabIndex = tabbedPane.indexAtLocation(e.getX(), e.getY());
//            boolean enable = selectedTabIndex > -1;
//            closeMenuItem.setEnabled(enable);
//            closeOthersMenuItem.setEnabled(enable);
//        } // setMenuItemsForEvent
//
//        /**
//         * Determine which line the user clicked on and find any breakpoints
//         * at that line. If none found, show the "Add breakpoint" menu. If
//         * there's a breakpoint, show a popup that provides breakpoint
//         * management features.
//         *
//         * @param  evt  mouse event.
//         */
//        protected void showPopup(MouseEvent evt) {
//            setMenuItemsForEvent(evt);
//            show(evt.getComponent(), evt.getX(), evt.getY());
//        } // showPopup
//    } // TabbedPopup
//
//
//} // TabbedViewDesktop
