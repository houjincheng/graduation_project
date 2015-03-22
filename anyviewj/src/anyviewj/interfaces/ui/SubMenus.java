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
    //�ļ��˵�
    public final JMenu jMenuFile = new JMenu();
        public final JMenuItem jMenuFile_New = new JMenuItem();//�½���Ŀ,��,�ӿ�,�ļ����Ӳ˵�
        public final JMenuItem jMenuFile_NewProject = new JMenuItem();//�½���Ŀ�Ӳ˵�
        public final JMenuItem jMenuFile_NewClass = new JMenuItem();//�½����ļ��Ӳ˵�
        public final JMenuItem jMenuFile_OpenProject = new JMenuItem();//����Ŀ�Ӳ˵�
        public final JMenuItem jMenuFile_OpenFile = new JMenuItem();//���ļ��Ӳ˵�
        public final JMenuItem jMenuFile_CloseProject = new JMenuItem();//�ر���Ŀ�Ӳ˵�
        public final JMenuItem jMenuFile_CloseFile = new JMenuItem();//�ر��ļ��Ӳ˵�
        public final JMenuItem jMenuFile_SaveProject = new JMenuItem();//������Ŀ�Ӳ˵�
        public final JMenuItem jMenuFile_SaveFile = new JMenuItem();//�����ļ��Ӳ˵�
        public final JMenuItem jMenuFile_SaveAll = new JMenuItem();//����ȫ���ļ��Ӳ˵�
        public final JMenuItem jMenuFile_Exit = new JMenuItem();//�˳��Ӳ˵�
        public final JMenuItem jMenuFile_Relogin = new JMenuItem();//���µ�¼�Ӳ˵�
        public final JMenuItem jMenuFile_ChangePassword = new JMenuItem();//�޸��û�����
 
        public final JMenuItem jMenuFile_OpenTable = new JMenuItem();
        public final JMenuItem jMenuFile_Upload = new JMenuItem();
        public final JMenuItem jMenuFile_NewHomework = new JMenuItem();
        
    //�༭�˵�
    public final JMenu jMenuEdit = new JMenu();
        public final JMenuItem jMenuEdit_Undo = new JMenuItem();//�����Ӳ˵�
        public final JMenuItem jMenuEdit_Redo = new JMenuItem();//�����Ӳ˵�
        public final JMenuItem jMenuEdit_Cut = new JMenuItem();//�����Ӳ˵�
        public final JMenuItem jMenuEdit_Copy = new JMenuItem();//�����Ӳ˵�
        public final JMenuItem jMenuEdit_Paste = new JMenuItem();//ճ���Ӳ˵�
        public final JMenuItem jMenuEdit_Delete = new JMenuItem();//ɾ���Ӳ˵�
        public final JMenuItem jMenuEdit_SelectAll = new JMenuItem();//ȫѡ�Ӳ˵�
    //���Ҳ˵�
    public final JMenu jMenuSearch = new JMenu();
        public final JMenuItem jMenuEdit_Find = new JMenuItem();//�����Ӳ˵�
        public final JMenuItem jMenuEdit_FindInPath = new JMenuItem();//��ָ��·�������Ӳ˵�
        public final JMenuItem jMenuEdit_FindDown = new JMenuItem();//������һ���Ӳ˵�
        public final JMenuItem jMenuEdit_FindUp = new JMenuItem();//������һ���Ӳ˵�
        public final JMenuItem jMenuEdit_Replace = new JMenuItem();//�滻�Ӳ˵�
        public final JMenuItem jMenuEdit_ReplaceInPath = new JMenuItem();//��ָ��·���滻�Ӳ˵�
        public final JMenuItem jMenuEdit_Goto = new JMenuItem();//�����Ӳ˵�
    //��Ŀ�˵�
    public final JMenu jMenuProject = new JMenu();
        public final JMenuItem jMenuProject_CompileProject = new JMenuItem();//������Ŀ�Ӳ˵�
        public final JMenuItem jMenuProject_CompileFile = new JMenuItem();//�����ļ��Ӳ˵�
        public final JMenuItem jMenuProject_Build = new JMenuItem();//������Ŀ�Ӳ˵�
        public final JMenuItem jMenuProject_ProjectProperties = new JMenuItem();//��Ŀ�����Ӳ˵�
    //���в˵�
    public final JMenu jMenuRun = new JMenu();
        public final JMenuItem jMenuRun_RunProject = new JMenuItem();//������Ŀ�Ӳ˵�
        public final JMenuItem jMenuRun_RunFile = new JMenuItem();//�����ļ��Ӳ˵�
        public final JMenuItem jMenuRun_DebugProject = new JMenuItem();//������Ŀ�Ӳ˵�
        public final JMenuItem jMenuRun_DebugFile = new JMenuItem();//�����ļ��Ӳ˵�
        public final JMenuItem jMenuRun_DebugProjectTime = new JMenuItem();//��ʱִ�е�����Ŀ�Ӳ˵�
        public final JMenuItem jMenuRun_DebugFileTime = new JMenuItem();//��ʱִ�е����ļ��Ӳ˵�
        public final JMenuItem jMenuRun_Pause = new JMenuItem();//��ͣ�Ӳ˵�
        public final JMenuItem jMenuRun_Stop = new JMenuItem();//ֹͣ�Ӳ˵�
        public final JMenuItem jMenuRun_StepOver = new JMenuItem();//��һ���Ӳ˵�
        public final JMenuItem jMenuRun_StepInto = new JMenuItem();//��һ���Ӳ˵�
        public final JMenuItem jMenuRun_StepOut = new JMenuItem();//�����Ӳ˵�
//        public final JMenuItem jMenuRun_SingleInstruction = new JMenuItem();//��ָ���Ӳ˵�
    //���߲˵�
    public final JMenu jMenuTools = new JMenu();
        public final JMenuItem jMenuTools_Config = new JMenuItem();//�����Ӳ˵�
    //��ͼ�˵�
    public final JMenu jMenuViews = new JMenu();
        public final JMenuItem jMenuView_Output = new JMenuItem();//��������Ӳ˵�
    //�����˵�
    public final JMenu jMenuHelp = new JMenu();
        public final JMenuItem jMenuHelp_Help = new JMenuItem();//�����Ӳ˵�
        public final JMenuItem jMenuHelp_About = new JMenuItem();//�����Ӳ˵�

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
        //�ļ��˵�
        jMenuFile.setAction(cm.fileActions.menuFileAction);
        	
            jMenuFile_New.setAction(cm.fileActions.newAction);//�½���Ŀ,��,�ӿ�,�ļ����Ӳ˵�
            jMenuFile_NewProject.setAction(cm.fileActions.newProjectAction);//�½���Ŀ�Ӳ˵�
            jMenuFile_NewClass.setAction(cm.fileActions.newClassAction);//�½����ļ��Ӳ˵�
            jMenuFile_OpenProject.setAction(cm.fileActions.openProjectAction);//����Ŀ�Ӳ˵�
            jMenuFile_OpenFile.setAction(cm.fileActions.openFileAction);//���ļ��Ӳ˵�
            jMenuFile_CloseProject.setAction(cm.fileActions.closeProjectAction);//�ر���Ŀ�Ӳ˵�
            jMenuFile_CloseFile.setAction(cm.fileActions.closeFileAction);//�ر��ļ��Ӳ˵�
            jMenuFile_SaveProject.setAction(cm.fileActions.saveProjectAction);//������Ŀ�Ӳ˵�
            jMenuFile_SaveFile.setAction(cm.fileActions.saveFileAction);//�����ļ��Ӳ˵�
            jMenuFile_SaveAll.setAction(cm.fileActions.saveAllAction);//����ȫ���ļ��Ӳ˵�
            jMenuFile_Relogin.setAction(cm.fileActions.reloginAction);//���µ�¼
            jMenuFile_ChangePassword.setAction( cm.fileActions.changePasswordAction );
            
            jMenuFile_OpenTable.setAction(cm.fileActions.openTableAction);//����Ŀ��
            jMenuFile_Upload.setAction(cm.fileActions.uploadAction);
            jMenuFile_NewHomework.setAction(cm.fileActions.newHomeworkAction);//�½���ҵ����
            
            
            jMenuFile_Exit.setAction(cm.fileActions.exitAction);//�˳��Ӳ˵�

            jMenuFile.add(jMenuFile_Relogin);
            jMenuFile.add(jMenuFile_ChangePassword);
            jMenuFile.addSeparator();
            jMenuFile.add(jMenuFile_New);//�½���Ŀ,��,�ӿ�,�ļ����Ӳ˵�
            jMenuFile.add(jMenuFile_NewProject);//�½���Ŀ�Ӳ˵�
            jMenuFile.add(jMenuFile_NewClass);//�½����ļ��Ӳ˵�
            jMenuFile.addSeparator();
            jMenuFile.add(jMenuFile_OpenProject);//����Ŀ�Ӳ˵�
            jMenuFile.add(jMenuFile_OpenFile);//���ļ��Ӳ˵�
            
            jMenuFile.add(jMenuFile_OpenTable);//����Ŀ��
            jMenuFile.add(jMenuFile_NewHomework);//�½���ҵ����
            
            
            jMenuFile.addSeparator();
            jMenuFile.add(jMenuFile_CloseProject);//�ر���Ŀ�Ӳ˵�
            jMenuFile.add(jMenuFile_CloseFile);//�ر��ļ��Ӳ˵�
            jMenuFile.addSeparator();
            jMenuFile.add(jMenuFile_SaveProject);//������Ŀ�Ӳ˵�
            jMenuFile.add(jMenuFile_SaveFile);//�����ļ��Ӳ˵�
            jMenuFile.add(jMenuFile_SaveAll);//����ȫ���ļ��Ӳ˵�
            jMenuFile.add(jMenuFile_Upload);
            jMenuFile.addSeparator();
            jMenuFile.add(jMenuFile_Exit);//�˳��Ӳ˵�

        //�༭�˵�
        jMenuEdit.setAction(cm.editActions.menuEditAction);
            jMenuEdit_Undo.setAction(cm.editActions.undoAction);//�����Ӳ˵�
            jMenuEdit_Redo.setAction(cm.editActions.redoAction);//�����Ӳ˵�
            jMenuEdit_Cut.setAction(cm.editActions.cutAction);//�����Ӳ˵�
            jMenuEdit_Copy.setAction(cm.editActions.copyAction);//�����Ӳ˵�
            jMenuEdit_Paste.setAction(cm.editActions.pasteAction);//ճ���Ӳ˵�
            jMenuEdit_Delete.setAction(cm.editActions.deleteAction);//ɾ���Ӳ˵�
            jMenuEdit_SelectAll.setAction(cm.editActions.selectAllAction);//ȫѡ�Ӳ˵�

            jMenuEdit.add(jMenuEdit_Undo);//�����Ӳ˵�
            jMenuEdit.add(jMenuEdit_Redo);//�����Ӳ˵�
            jMenuEdit.addSeparator();
            jMenuEdit.add(jMenuEdit_Cut);//�����Ӳ˵�
            jMenuEdit.add(jMenuEdit_Copy);//�����Ӳ˵�
            jMenuEdit.add(jMenuEdit_Paste);//ճ���Ӳ˵�
            jMenuEdit.add(jMenuEdit_Delete);//ɾ���Ӳ˵�
            jMenuEdit.addSeparator();
            jMenuEdit.add(jMenuEdit_SelectAll);//ȫѡ�Ӳ˵�)

        //���Ҳ˵�
        jMenuSearch.setAction(cm.searchActions.menuSearchAction);
            jMenuEdit_Find.setAction(cm.searchActions.findAction);//�����Ӳ˵�
            jMenuEdit_FindInPath.setAction(cm.searchActions.findInPathction);//��ָ��·�������Ӳ˵�
            jMenuEdit_FindDown.setAction(cm.searchActions.findDownAction);//������һ���Ӳ˵�
            jMenuEdit_FindUp.setAction(cm.searchActions.findUpAction);//������һ���Ӳ˵�
            jMenuEdit_Replace.setAction(cm.searchActions.replaceAction);//�滻�Ӳ˵�
            jMenuEdit_ReplaceInPath.setAction(cm.searchActions.replaceInPathAction);//��ָ��·���滻�Ӳ˵�
            jMenuEdit_Goto.setAction(cm.searchActions.gotoAction);//�����Ӳ˵�

            jMenuSearch.add(jMenuEdit_Find);//�����Ӳ˵�
            jMenuSearch.add(jMenuEdit_FindInPath);//��ָ��·�������Ӳ˵�
            jMenuSearch.add(jMenuEdit_FindDown);//������һ���Ӳ˵�
            jMenuSearch.add(jMenuEdit_FindUp);//������һ���Ӳ˵�
            jMenuSearch.addSeparator();
            jMenuSearch.add(jMenuEdit_Replace);//�滻�Ӳ˵�
            jMenuSearch.add(jMenuEdit_ReplaceInPath);//��ָ��·���滻�Ӳ˵�
            jMenuSearch.addSeparator();
            jMenuSearch.add(jMenuEdit_Goto);//�����Ӳ˵�


        //��Ŀ�˵�
        jMenuProject.setAction(cm.projectActions.menuProjectAction);
            jMenuProject_CompileProject.setAction(cm.projectActions.compileProjectAction);//������Ŀ�Ӳ˵�
            jMenuProject_CompileFile.setAction(cm.projectActions.compileFileAction);//�����ļ��Ӳ˵�
            jMenuProject_Build.setAction(cm.projectActions.buildAction);//������Ŀ�Ӳ˵�
            jMenuProject_ProjectProperties.setAction(cm.projectActions.projectPropertiesAction);//��Ŀ�����Ӳ˵�

            jMenuProject.add(jMenuProject_CompileProject);//������Ŀ�Ӳ˵�
            jMenuProject.add(jMenuProject_CompileFile);//�����ļ��Ӳ˵�
            jMenuProject.add(jMenuProject_Build);//������Ŀ�Ӳ˵�
            jMenuProject.addSeparator();
            jMenuProject.add(jMenuProject_ProjectProperties);//��Ŀ�����Ӳ˵�


        //���в˵�
        jMenuRun.setAction(cm.runActions.menuRunAction);
            jMenuRun_RunProject.setAction(cm.runActions.runProjectAction);//������Ŀ�Ӳ˵�
//            jMenuRun_RunFile.setAction(cm.runActions.runFileAction);//�����ļ��Ӳ˵�
            jMenuRun_DebugProject.setAction(cm.runActions.debugProjectAction);//������Ŀ�Ӳ˵�
            jMenuRun_DebugFile.setAction(cm.runActions.debugFileAction);//�����ļ��Ӳ˵�
            jMenuRun_DebugProjectTime.setAction(cm.runActions.debugProjectTimeAction);//��ʱִ�е�����Ŀ�Ӳ˵�
            jMenuRun_DebugFileTime.setAction(cm.runActions.debugFileTimeAction);//��ʱִ�е����ļ��Ӳ˵�
            jMenuRun_Pause.setAction(cm.runActions.pauseAction);//��ͣ�Ӳ˵�
            jMenuRun_Stop.setAction(cm.runActions.stopDebugAction);//ֹͣ�Ӳ˵�
            
            jMenuRun_StepOver.setAction(cm.runActions.stepOverAction);//��һ���Ӳ˵�
            jMenuRun_StepInto.setAction(cm.runActions.stepIntoAction);//��һ���Ӳ˵�
            jMenuRun_StepOut.setAction(cm.runActions.stepOutAction);//�����Ӳ˵�
//            jMenuRun_SingleInstruction.setAction(cm.runActions.singleInstructionAction);//��ָ���Ӳ˵�

            jMenuRun.add(jMenuRun_RunProject);//������Ŀ�Ӳ˵�
            jMenuRun.add(jMenuRun_DebugProject);//������Ŀ�Ӳ˵�
            jMenuRun.add(jMenuRun_DebugProjectTime);//��ʱִ�е�����Ŀ�Ӳ˵�
//            jMenuRun.addSeparator();
//            jMenuRun.add(jMenuRun_RunFile);//�����ļ��Ӳ˵�
            jMenuRun.add(jMenuRun_DebugFile);//�����ļ��Ӳ˵�
            jMenuRun.add(jMenuRun_DebugFileTime);//��ʱִ�е����ļ��Ӳ˵�
            jMenuRun.addSeparator();
            jMenuRun.add(jMenuRun_Pause);//��ͣ�Ӳ˵�
            jMenuRun.add(jMenuRun_Stop);//ֹͣ�Ӳ˵�
            jMenuRun.add(jMenuRun_StepOver);//��һ���Ӳ˵�
            jMenuRun.add(jMenuRun_StepInto);//��һ���Ӳ˵�
            jMenuRun.add(jMenuRun_StepOut);//�����Ӳ˵�
//            jMenuRun.add(jMenuRun_SingleInstruction);//��ָ���Ӳ˵�

        //���߲˵�
        jMenuTools.setAction(cm.toolsActions.menuToolsAction);
            jMenuTools_Config.setAction(cm.toolsActions.configAction);//�����Ӳ˵�

            jMenuTools.add(jMenuTools_Config);//�����Ӳ˵�

        //��ͼ�˵�
        jMenuViews.setAction(cm.viewActions.menuViewAction);
            jMenuView_Output.setAction(cm.viewActions.outputAction);//��������Ӳ˵�

            jMenuViews.add(jMenuView_Output);//��������Ӳ˵�

        //�����˵�
        jMenuHelp.setAction(cm.helpActions.menuHelpAction);
            jMenuHelp_Help.setAction(cm.helpActions.helpAction);//�����Ӳ˵�
            jMenuHelp_About.setAction(cm.helpActions.aboutAction);//�����Ӳ˵�

            jMenuHelp.add(jMenuHelp_Help);//�����Ӳ˵�
            jMenuHelp.add(jMenuHelp_About);//�����Ӳ˵�

    }
}
