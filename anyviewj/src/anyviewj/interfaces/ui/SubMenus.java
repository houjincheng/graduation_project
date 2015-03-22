package anyviewj.interfaces.ui;

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

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import anyviewj.interfaces.actions.*;

public class SubMenus {
    //文件菜单
    public final JMenu jMenuFile = new JMenu();
        public final JMenuItem jMenuFile_New = new JMenuItem();//新建项目,类,接口,文件等子菜单
        public final JMenuItem jMenuFile_NewProject = new JMenuItem();//新建项目子菜单
        public final JMenuItem jMenuFile_NewClass = new JMenuItem();//新建类文件子菜单
        public final JMenuItem jMenuFile_OpenProject = new JMenuItem();//打开项目子菜单
        public final JMenuItem jMenuFile_OpenFile = new JMenuItem();//打开文件子菜单
        public final JMenuItem jMenuFile_CloseProject = new JMenuItem();//关闭项目子菜单
        public final JMenuItem jMenuFile_CloseFile = new JMenuItem();//关闭文件子菜单
        public final JMenuItem jMenuFile_SaveProject = new JMenuItem();//保存项目子菜单
        public final JMenuItem jMenuFile_SaveFile = new JMenuItem();//保存文件子菜单
        public final JMenuItem jMenuFile_SaveAll = new JMenuItem();//保存全部文件子菜单
        public final JMenuItem jMenuFile_Exit = new JMenuItem();//退出子菜单
        public final JMenuItem jMenuFile_Relogin = new JMenuItem();//重新登录子菜单
        public final JMenuItem jMenuFile_ChangePassword = new JMenuItem();//修改用户密码
 
        public final JMenuItem jMenuFile_OpenTable = new JMenuItem();
        public final JMenuItem jMenuFile_Upload = new JMenuItem();
        public final JMenuItem jMenuFile_NewHomework = new JMenuItem();
        
    //编辑菜单
    public final JMenu jMenuEdit = new JMenu();
        public final JMenuItem jMenuEdit_Undo = new JMenuItem();//撤销子菜单
        public final JMenuItem jMenuEdit_Redo = new JMenuItem();//重做子菜单
        public final JMenuItem jMenuEdit_Cut = new JMenuItem();//剪切子菜单
        public final JMenuItem jMenuEdit_Copy = new JMenuItem();//复制子菜单
        public final JMenuItem jMenuEdit_Paste = new JMenuItem();//粘贴子菜单
        public final JMenuItem jMenuEdit_Delete = new JMenuItem();//删除子菜单
        public final JMenuItem jMenuEdit_SelectAll = new JMenuItem();//全选子菜单
    //查找菜单
    public final JMenu jMenuSearch = new JMenu();
        public final JMenuItem jMenuEdit_Find = new JMenuItem();//查找子菜单
        public final JMenuItem jMenuEdit_FindInPath = new JMenuItem();//在指定路径查找子菜单
        public final JMenuItem jMenuEdit_FindDown = new JMenuItem();//查找下一个子菜单
        public final JMenuItem jMenuEdit_FindUp = new JMenuItem();//查找上一个子菜单
        public final JMenuItem jMenuEdit_Replace = new JMenuItem();//替换子菜单
        public final JMenuItem jMenuEdit_ReplaceInPath = new JMenuItem();//在指定路径替换子菜单
        public final JMenuItem jMenuEdit_Goto = new JMenuItem();//跳至子菜单
    //项目菜单
    public final JMenu jMenuProject = new JMenu();
        public final JMenuItem jMenuProject_CompileProject = new JMenuItem();//编译项目子菜单
        public final JMenuItem jMenuProject_CompileFile = new JMenuItem();//编译文件子菜单
        public final JMenuItem jMenuProject_Build = new JMenuItem();//建立项目子菜单
        public final JMenuItem jMenuProject_ProjectProperties = new JMenuItem();//项目属性子菜单
    //运行菜单
    public final JMenu jMenuRun = new JMenu();
        public final JMenuItem jMenuRun_RunProject = new JMenuItem();//运行项目子菜单
        public final JMenuItem jMenuRun_RunFile = new JMenuItem();//运行文件子菜单
        public final JMenuItem jMenuRun_DebugProject = new JMenuItem();//调试项目子菜单
        public final JMenuItem jMenuRun_DebugFile = new JMenuItem();//调试文件子菜单
        public final JMenuItem jMenuRun_DebugProjectTime = new JMenuItem();//定时执行调试项目子菜单
        public final JMenuItem jMenuRun_DebugFileTime = new JMenuItem();//定时执行调试文件子菜单
        public final JMenuItem jMenuRun_Pause = new JMenuItem();//暂停子菜单
        public final JMenuItem jMenuRun_Stop = new JMenuItem();//停止子菜单
        public final JMenuItem jMenuRun_StepOver = new JMenuItem();//下一行子菜单
        public final JMenuItem jMenuRun_StepInto = new JMenuItem();//下一步子菜单
        public final JMenuItem jMenuRun_StepOut = new JMenuItem();//步出子菜单
//        public final JMenuItem jMenuRun_SingleInstruction = new JMenuItem();//单指令子菜单
    //工具菜单
    public final JMenu jMenuTools = new JMenu();
        public final JMenuItem jMenuTools_Config = new JMenuItem();//配置子菜单
    //视图菜单
    public final JMenu jMenuViews = new JMenu();
        public final JMenuItem jMenuView_Output = new JMenuItem();//输出窗口子菜单
    //帮助菜单
    public final JMenu jMenuHelp = new JMenu();
        public final JMenuItem jMenuHelp_Help = new JMenuItem();//帮助子菜单
        public final JMenuItem jMenuHelp_About = new JMenuItem();//关于子菜单

    private JMenuBar jMenuBar;
    public SubMenus(JMenuBar owner) {
        assert(owner != null);
        this.jMenuBar = owner;
    }

    public void initSubMenus(CommandManager cm){
        jMenuBar.add(jMenuFile);
        jMenuBar.add(jMenuEdit);
        jMenuBar.add(jMenuSearch);
        jMenuBar.add(jMenuProject);
        jMenuBar.add(jMenuRun);
        jMenuBar.add(jMenuTools);
        jMenuBar.add(jMenuViews);
        jMenuBar.add(jMenuHelp);
        //文件菜单
        jMenuFile.setAction(cm.fileActions.menuFileAction);
        	
            jMenuFile_New.setAction(cm.fileActions.newAction);//新建项目,类,接口,文件等子菜单
            jMenuFile_NewProject.setAction(cm.fileActions.newProjectAction);//新建项目子菜单
            jMenuFile_NewClass.setAction(cm.fileActions.newClassAction);//新建类文件子菜单
            jMenuFile_OpenProject.setAction(cm.fileActions.openProjectAction);//打开项目子菜单
            jMenuFile_OpenFile.setAction(cm.fileActions.openFileAction);//打开文件子菜单
            jMenuFile_CloseProject.setAction(cm.fileActions.closeProjectAction);//关闭项目子菜单
            jMenuFile_CloseFile.setAction(cm.fileActions.closeFileAction);//关闭文件子菜单
            jMenuFile_SaveProject.setAction(cm.fileActions.saveProjectAction);//保存项目子菜单
            jMenuFile_SaveFile.setAction(cm.fileActions.saveFileAction);//保存文件子菜单
            jMenuFile_SaveAll.setAction(cm.fileActions.saveAllAction);//保存全部文件子菜单
            jMenuFile_Relogin.setAction(cm.fileActions.reloginAction);//重新登录
            jMenuFile_ChangePassword.setAction( cm.fileActions.changePasswordAction );
            
            jMenuFile_OpenTable.setAction(cm.fileActions.openTableAction);//打开题目表
            jMenuFile_Upload.setAction(cm.fileActions.uploadAction);
            jMenuFile_NewHomework.setAction(cm.fileActions.newHomeworkAction);//新建作业工程
            
            
            jMenuFile_Exit.setAction(cm.fileActions.exitAction);//退出子菜单

            jMenuFile.add(jMenuFile_Relogin);
            jMenuFile.add(jMenuFile_ChangePassword);
            jMenuFile.addSeparator();
            jMenuFile.add(jMenuFile_New);//新建项目,类,接口,文件等子菜单
            jMenuFile.add(jMenuFile_NewProject);//新建项目子菜单
            jMenuFile.add(jMenuFile_NewClass);//新建类文件子菜单
            jMenuFile.addSeparator();
            jMenuFile.add(jMenuFile_OpenProject);//打开项目子菜单
            jMenuFile.add(jMenuFile_OpenFile);//打开文件子菜单
            
            jMenuFile.add(jMenuFile_OpenTable);//打开题目表
            jMenuFile.add(jMenuFile_NewHomework);//新建作业工程
            
            
            jMenuFile.addSeparator();
            jMenuFile.add(jMenuFile_CloseProject);//关闭项目子菜单
            jMenuFile.add(jMenuFile_CloseFile);//关闭文件子菜单
            jMenuFile.addSeparator();
            jMenuFile.add(jMenuFile_SaveProject);//保存项目子菜单
            jMenuFile.add(jMenuFile_SaveFile);//保存文件子菜单
            jMenuFile.add(jMenuFile_SaveAll);//保存全部文件子菜单
            jMenuFile.add(jMenuFile_Upload);
            jMenuFile.addSeparator();
            jMenuFile.add(jMenuFile_Exit);//退出子菜单

        //编辑菜单
        jMenuEdit.setAction(cm.editActions.menuEditAction);
            jMenuEdit_Undo.setAction(cm.editActions.undoAction);//撤销子菜单
            jMenuEdit_Redo.setAction(cm.editActions.redoAction);//重做子菜单
            jMenuEdit_Cut.setAction(cm.editActions.cutAction);//剪切子菜单
            jMenuEdit_Copy.setAction(cm.editActions.copyAction);//复制子菜单
            jMenuEdit_Paste.setAction(cm.editActions.pasteAction);//粘贴子菜单
            jMenuEdit_Delete.setAction(cm.editActions.deleteAction);//删除子菜单
            jMenuEdit_SelectAll.setAction(cm.editActions.selectAllAction);//全选子菜单

            jMenuEdit.add(jMenuEdit_Undo);//撤销子菜单
            jMenuEdit.add(jMenuEdit_Redo);//重做子菜单
            jMenuEdit.addSeparator();
            jMenuEdit.add(jMenuEdit_Cut);//剪切子菜单
            jMenuEdit.add(jMenuEdit_Copy);//复制子菜单
            jMenuEdit.add(jMenuEdit_Paste);//粘贴子菜单
            jMenuEdit.add(jMenuEdit_Delete);//删除子菜单
            jMenuEdit.addSeparator();
            jMenuEdit.add(jMenuEdit_SelectAll);//全选子菜单)

        //查找菜单
        jMenuSearch.setAction(cm.searchActions.menuSearchAction);
            jMenuEdit_Find.setAction(cm.searchActions.findAction);//查找子菜单
            jMenuEdit_FindInPath.setAction(cm.searchActions.findInPathction);//在指定路径查找子菜单
            jMenuEdit_FindDown.setAction(cm.searchActions.findDownAction);//查找下一个子菜单
            jMenuEdit_FindUp.setAction(cm.searchActions.findUpAction);//查找上一个子菜单
            jMenuEdit_Replace.setAction(cm.searchActions.replaceAction);//替换子菜单
            jMenuEdit_ReplaceInPath.setAction(cm.searchActions.replaceInPathAction);//在指定路径替换子菜单
            jMenuEdit_Goto.setAction(cm.searchActions.gotoAction);//跳至子菜单

            jMenuSearch.add(jMenuEdit_Find);//查找子菜单
            jMenuSearch.add(jMenuEdit_FindInPath);//在指定路径查找子菜单
            jMenuSearch.add(jMenuEdit_FindDown);//查找下一个子菜单
            jMenuSearch.add(jMenuEdit_FindUp);//查找上一个子菜单
            jMenuSearch.addSeparator();
            jMenuSearch.add(jMenuEdit_Replace);//替换子菜单
            jMenuSearch.add(jMenuEdit_ReplaceInPath);//在指定路径替换子菜单
            jMenuSearch.addSeparator();
            jMenuSearch.add(jMenuEdit_Goto);//跳至子菜单


        //项目菜单
        jMenuProject.setAction(cm.projectActions.menuProjectAction);
            jMenuProject_CompileProject.setAction(cm.projectActions.compileProjectAction);//编译项目子菜单
            jMenuProject_CompileFile.setAction(cm.projectActions.compileFileAction);//编译文件子菜单
            jMenuProject_Build.setAction(cm.projectActions.buildAction);//建立项目子菜单
            jMenuProject_ProjectProperties.setAction(cm.projectActions.projectPropertiesAction);//项目属性子菜单

            jMenuProject.add(jMenuProject_CompileProject);//编译项目子菜单
            jMenuProject.add(jMenuProject_CompileFile);//编译文件子菜单
            jMenuProject.add(jMenuProject_Build);//建立项目子菜单
            jMenuProject.addSeparator();
            jMenuProject.add(jMenuProject_ProjectProperties);//项目属性子菜单


        //运行菜单
        jMenuRun.setAction(cm.runActions.menuRunAction);
            jMenuRun_RunProject.setAction(cm.runActions.runProjectAction);//运行项目子菜单
//            jMenuRun_RunFile.setAction(cm.runActions.runFileAction);//运行文件子菜单
            jMenuRun_DebugProject.setAction(cm.runActions.debugProjectAction);//调试项目子菜单
            jMenuRun_DebugFile.setAction(cm.runActions.debugFileAction);//调试文件子菜单
            jMenuRun_DebugProjectTime.setAction(cm.runActions.debugProjectTimeAction);//定时执行调试项目子菜单
            jMenuRun_DebugFileTime.setAction(cm.runActions.debugFileTimeAction);//定时执行调试文件子菜单
            jMenuRun_Pause.setAction(cm.runActions.pauseAction);//暂停子菜单
            jMenuRun_Stop.setAction(cm.runActions.stopDebugAction);//停止子菜单
            
            jMenuRun_StepOver.setAction(cm.runActions.stepOverAction);//下一行子菜单
            jMenuRun_StepInto.setAction(cm.runActions.stepIntoAction);//下一步子菜单
            jMenuRun_StepOut.setAction(cm.runActions.stepOutAction);//步出子菜单
//            jMenuRun_SingleInstruction.setAction(cm.runActions.singleInstructionAction);//单指令子菜单

            jMenuRun.add(jMenuRun_RunProject);//运行项目子菜单
            jMenuRun.add(jMenuRun_DebugProject);//调试项目子菜单
            jMenuRun.add(jMenuRun_DebugProjectTime);//定时执行调试项目子菜单
//            jMenuRun.addSeparator();
//            jMenuRun.add(jMenuRun_RunFile);//运行文件子菜单
            jMenuRun.add(jMenuRun_DebugFile);//调试文件子菜单
            jMenuRun.add(jMenuRun_DebugFileTime);//定时执行调试文件子菜单
            jMenuRun.addSeparator();
            jMenuRun.add(jMenuRun_Pause);//暂停子菜单
            jMenuRun.add(jMenuRun_Stop);//停止子菜单
            jMenuRun.add(jMenuRun_StepOver);//下一行子菜单
            jMenuRun.add(jMenuRun_StepInto);//下一步子菜单
            jMenuRun.add(jMenuRun_StepOut);//步出子菜单
//            jMenuRun.add(jMenuRun_SingleInstruction);//单指令子菜单

        //工具菜单
        jMenuTools.setAction(cm.toolsActions.menuToolsAction);
            jMenuTools_Config.setAction(cm.toolsActions.configAction);//配置子菜单

            jMenuTools.add(jMenuTools_Config);//配置子菜单

        //视图菜单
        jMenuViews.setAction(cm.viewActions.menuViewAction);
            jMenuView_Output.setAction(cm.viewActions.outputAction);//输出窗口子菜单

            jMenuViews.add(jMenuView_Output);//输出窗口子菜单

        //帮助菜单
        jMenuHelp.setAction(cm.helpActions.menuHelpAction);
            jMenuHelp_Help.setAction(cm.helpActions.helpAction);//帮助子菜单
            jMenuHelp_About.setAction(cm.helpActions.aboutAction);//关于子菜单

            jMenuHelp.add(jMenuHelp_Help);//帮助子菜单
            jMenuHelp.add(jMenuHelp_About);//关于子菜单

    }
}
