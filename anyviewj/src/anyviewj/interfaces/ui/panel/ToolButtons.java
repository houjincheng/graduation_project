package anyviewj.interfaces.ui.panel;

import javax.swing.JButton;
import javax.swing.JToolBar;

import anyviewj.interfaces.actions.CommandManager;
/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007 gdut 1627</p>
 *
 * <p>Company: gdut 1627</p>
 *
 * @author ltt
 * @version 1.0
 */
public class ToolButtons {
    //�ļ���ť
    public final JButton jButtonFile_New = new JButton();//�½���Ŀ,��,�ӿ�,�ļ����Ӱ�ť
    public final JButton jButtonFile_OpenProject = new JButton();//����Ŀ�Ӱ�ť
    public final JButton jButtonFile_CloseProject = new JButton();//�ر���Ŀ�Ӱ�ť
    //public final JButton jButtonFile_OpenFile = new JButton();//���ļ��Ӱ�ť
    //public final JButton jButtonFile_CloseFile = new JButton();//�ر��ļ��Ӱ�ť
    //public final JButton jButtonFile_SaveFile = new JButton();//�����ļ��Ӱ�ť
    public final JButton jButtonFile_SaveAll = new JButton();//����ȫ���ļ��Ӱ�ť

    /*/�༭��ť
    public final JButton jButtonEdit_Undo = new JButton();//�����Ӱ�ť
    public final JButton jButtonEdit_Redo = new JButton();//�����Ӱ�ť
    public final JButton jButtonEdit_Cut = new JButton();//�����Ӱ�ť
    public final JButton jButtonEdit_Copy = new JButton();//�����Ӱ�ť
    public final JButton jButtonEdit_Paste = new JButton();//ճ���Ӱ�ť
    public final JButton jButtonEdit_Delete = new JButton();//ɾ���Ӱ�ť//*/
    //���Ұ�ť
    //public final JButton jButtonSearch_Find = new JButton();//�����Ӱ�ť
    public final JButton jButtonSearch_FindInPath = new JButton();//��ָ��·�������Ӱ�ť
    //public final JButton jButtonSearch_FindDown = new JButton();//������һ���Ӱ�ť
    //public final JButton jButtonSearch_FindUp = new JButton();//������һ���Ӱ�ť
    //public final JButton jButtonSearch_Replace = new JButton();//�滻�Ӱ�ť
    public final JButton jButtonSearch_ReplaceInPath = new JButton();//��ָ��·���滻�Ӱ�ť
    //public final JButton jButtonSearch_Goto = new JButton();//�����Ӱ�ť

    //��Ŀ
    public final JButton jButtonProject_CompileProject = new JButton();//������Ŀ�Ӱ�ť

    //����
    public final JButton jButtonRun_RunProject = new JButton();//������Ŀ�Ӱ�ť
    //public final JButton jButtonRun_RunFile = new JButton();//�����ļ��Ӱ�ť
    public final JButton jButtonRun_DebugProject = new JButton();//������Ŀ�Ӱ�ť
    public final JButton jButtonRun_DebugFile = new JButton();//�����ļ��Ӱ�ť
    public final JButton jButtonRun_DebugProjectTime = new JButton();//��ʱִ�е�����Ŀ�Ӱ�ť
//    public final JButton jButtonRun_DebugFileTime = new JButton();//��ʱִ�е����ļ��Ӱ�ť
    public final JButton jButtonRun_Pause = new JButton();//��ͣ�Ӱ�ť
    public final JButton jButtonRun_Stop = new JButton();//ֹͣ�Ӱ�ť
    public final JButton jButtonRun_StepOver = new JButton();//��һ���Ӱ�ť
    public final JButton jButtonRun_StepInto = new JButton();//��һ���Ӱ�ť
    public final JButton jButtonRun_StepOut = new JButton();//�����Ӱ�ť
//    public final JButton jButtonRun_SingleInstruction = new JButton();//��ָ���Ӱ�ť
    public final JButton jButtonRun_Answer = new JButton();
    
    public final JButton jButtonUpload = new JButton(); //�ϴ�
    /**
     * hou 2013��8��10��19:07:47
     * ��Ϊһ��ʱ���ѡ��������ֵ��Ϊ��ʱ���Ե�ʱ����
     */
    public final DebugTimeSelector timeSelector = new DebugTimeSelector
    		( jButtonRun_StepOver.getBackground() );
    
    
   
    private JToolBar jToolBar;
    public ToolButtons(JToolBar owner) {
        assert(owner != null);
        this.jToolBar = owner;
    }
//	����ֲ��������ӵġ� hou 2013-5-30 11:40:56
    public ToolButtons(CommandManager commandManager, JToolBar owner) {
    	
    	assert(owner != null);
        this.jToolBar = owner;
        
    	initToolButtons(commandManager);
    }

	//��������ť
    /**
     * 
     * @param cm
     */
    public void initToolButtons(CommandManager cm){
        //�ļ���ť���ö���
        jButtonFile_New.putClientProperty("hideActionText",Boolean.TRUE);//�½���Ŀ,��,�ӿ�,�ļ����Ӱ�ť
        jButtonFile_OpenProject.putClientProperty("hideActionText",Boolean.TRUE);//����Ŀ�Ӱ�ť
        jButtonFile_CloseProject.putClientProperty("hideActionText",Boolean.TRUE);//�ر���Ŀ�Ӱ�ť
        //jButtonFile_OpenFile.putClientProperty("hideActionText",Boolean.TRUE);//���ļ��Ӱ�ť
        //jButtonFile_CloseFile.putClientProperty("hideActionText",Boolean.TRUE);//�ر��ļ��Ӱ�ť
        //jButtonFile_SaveFile.putClientProperty("hideActionText",Boolean.TRUE);//�����ļ��Ӱ�ť
        jButtonFile_SaveAll.putClientProperty("hideActionText",Boolean.TRUE);//����ȫ���ļ��Ӱ�ť
       // jButtonUpload.putClientProperty("hideActionText",Boolean.TRUE);

        jButtonFile_New.setAction(cm.fileActions.newAction);//�½���Ŀ,��,�ӿ�,�ļ����Ӱ�ť
        jButtonFile_OpenProject.setAction(cm.fileActions.openProjectAction);//����Ŀ�Ӱ�ť
        jButtonFile_CloseProject.setAction(cm.fileActions.closeProjectAction);//�ر���Ŀ�Ӱ�ť
        //jButtonFile_OpenFile.setAction(cm.fileActions.openFileAction);//���ļ��Ӱ�ť
        //jButtonFile_CloseFile.setAction(cm.fileActions.closeFileAction);//�ر��ļ��Ӱ�ť
        //jButtonFile_SaveFile.setAction(cm.fileActions.saveFileAction);//�����ļ��Ӱ�ť
        jButtonFile_SaveAll.setAction(cm.fileActions.saveAllAction);//����ȫ���ļ��Ӱ�ť
        
        jButtonUpload.setAction(cm.fileActions.uploadAction);//�ϴ�

        /*/�༭��ť
        jButtonEdit_Undo.putClientProperty("hideActionText",Boolean.TRUE);//�����Ӱ�ť
        jButtonEdit_Redo.putClientProperty("hideActionText",Boolean.TRUE);//�����Ӱ�ť
        jButtonEdit_Cut.putClientProperty("hideActionText",Boolean.TRUE);//�����Ӱ�ť
        jButtonEdit_Copy.putClientProperty("hideActionText",Boolean.TRUE);//�����Ӱ�ť
        jButtonEdit_Paste.putClientProperty("hideActionText",Boolean.TRUE);//ճ���Ӱ�ť
        jButtonEdit_Delete.putClientProperty("hideActionText",Boolean.TRUE);//ɾ���Ӱ�ť

        jButtonEdit_Undo.setAction(cm.editActions.undoAction);//�����Ӱ�ť
        jButtonEdit_Redo.setAction(cm.editActions.redoAction);//�����Ӱ�ť
        jButtonEdit_Cut.setAction(cm.editActions.cutAction);//�����Ӱ�ť
        jButtonEdit_Copy.setAction(cm.editActions.copyAction);//�����Ӱ�ť
        jButtonEdit_Paste.setAction(cm.editActions.pasteAction);//ճ���Ӱ�ť
        jButtonEdit_Delete.setAction(cm.editActions.deleteAction);//ɾ���Ӱ�ť */

        //���Ұ�ť
        //jButtonSearch_Find.putClientProperty("hideActionText",Boolean.TRUE);//�����Ӱ�ť
        jButtonSearch_FindInPath.putClientProperty("hideActionText",Boolean.TRUE);//��ָ��·�������Ӱ�ť
        //jButtonSearch_FindDown.putClientProperty("hideActionText",Boolean.TRUE);//������һ���Ӱ�ť
        //jButtonSearch_FindUp.putClientProperty("hideActionText",Boolean.TRUE);//������һ���Ӱ�ť
        //jButtonSearch_Replace.putClientProperty("hideActionText",Boolean.TRUE);//�滻�Ӱ�ť
        jButtonSearch_ReplaceInPath.putClientProperty("hideActionText",Boolean.TRUE);//��ָ��·���滻�Ӱ�ť
        //jButtonSearch_Goto.putClientProperty("hideActionText",Boolean.TRUE);//�����Ӱ�ť

        //jButtonSearch_Find.setAction(cm.searchActions.findAction);//�����Ӱ�ť
        jButtonSearch_FindInPath.setAction(cm.searchActions.findInPathction);//��ָ��·�������Ӱ�ť
        //jButtonSearch_FindDown.setAction(cm.searchActions.findDownAction);//������һ���Ӱ�ť
        //jButtonSearch_FindUp.setAction(cm.searchActions.findUpAction);//������һ���Ӱ�ť
        //jButtonSearch_Replace.setAction(cm.searchActions.replaceAction);//�滻�Ӱ�ť
        jButtonSearch_ReplaceInPath.setAction(cm.searchActions.replaceInPathAction);//��ָ��·���滻�Ӱ�ť
        //jButtonSearch_Goto.setAction(cm.searchActions.gotoAction);//�����Ӱ�ť

        //��Ŀ
        jButtonProject_CompileProject.putClientProperty("hideActionText",Boolean.TRUE);//������Ŀ�Ӱ�ť
        jButtonProject_CompileProject.setAction(cm.projectActions.compileProjectAction);//������Ŀ�Ӱ�ť
        //�������ö���
        jButtonRun_RunProject.putClientProperty("hideActionText",Boolean.TRUE);//������Ŀ�Ӱ�ť
        //jButtonRun_RunFile.putClientProperty("hideActionText",Boolean.TRUE);//�����ļ��Ӱ�ť
        jButtonRun_DebugProject.putClientProperty("hideActionText",Boolean.TRUE);//������Ŀ�Ӱ�ť
        jButtonRun_DebugFile.putClientProperty("hideActionText",Boolean.TRUE);//�����ļ��Ӱ�ť
        jButtonRun_DebugProjectTime.putClientProperty("hideActionText",Boolean.TRUE);//��ʱִ�е�����Ŀ�Ӱ�ť
//        jButtonRun_DebugFileTime.putClientProperty("hideActionText",Boolean.TRUE);//��ʱִ�е����ļ��Ӱ�ť
        jButtonRun_Pause.putClientProperty("hideActionText",Boolean.TRUE);//��ͣ�Ӱ�ť
        jButtonRun_Stop.putClientProperty("hideActionText",Boolean.TRUE);//ֹͣ�Ӱ�ť
        jButtonRun_StepOver.putClientProperty("hideActionText",Boolean.TRUE);//��һ���Ӱ�ť
        jButtonRun_StepInto.putClientProperty("hideActionText",Boolean.TRUE);//��һ���Ӱ�ť
        jButtonRun_StepOut.putClientProperty("hideActionText",Boolean.TRUE);//�����Ӱ�ť
//        jButtonRun_SingleInstruction.putClientProperty("hideActionText",Boolean.TRUE);//��ָ���Ӱ�ť

        jButtonRun_RunProject.setAction(cm.runActions.runProjectAction);//������Ŀ�Ӱ�ť
        //jButtonRun_RunFile.setAction(cm.runActions.runFileAction);//�����ļ��Ӱ�ť

        jButtonRun_Answer.setAction(cm.runActions.answerAction);
        
        jButtonRun_DebugProject.setAction(cm.runActions.debugProjectAction);//������Ŀ�Ӱ�ť
        
        jButtonRun_DebugFile.setAction(cm.runActions.debugFileAction);//�����ļ��Ӱ�ť
        jButtonRun_DebugProjectTime.setAction(cm.runActions.debugProjectTimeAction);//��ʱִ�е�����Ŀ�Ӱ�ť
//        jButtonRun_DebugFileTime.setAction(cm.runActions.debugFileTimeAction);//��ʱִ�е����ļ��Ӱ�ť
        jButtonRun_Pause.setAction(cm.runActions.pauseAction);//��ͣ�Ӱ�ť
        jButtonRun_Stop.setAction(cm.runActions.stopDebugAction);//ֹͣ�Ӱ�ť
        jButtonRun_StepOver.setAction(cm.runActions.stepOverAction);//��һ���Ӱ�ť
        jButtonRun_StepInto.setAction(cm.runActions.stepIntoAction);//��һ���Ӱ�ť
        jButtonRun_StepOut.setAction(cm.runActions.stepOutAction);//�����Ӱ�ť
//        jButtonRun_SingleInstruction.setAction(cm.runActions.singleInstructionAction);//��ָ���Ӱ�ť

        
        
        //�ļ���ť
        jToolBar.add(jButtonFile_New);//�½���Ŀ,��,�ӿ�,�ļ����Ӱ�ť
        jToolBar.add(this.jButtonFile_OpenProject);//����Ŀ�Ӱ�ť
        jToolBar.add(this.jButtonFile_CloseProject);//�ر���Ŀ�Ӱ�ť
        //jToolBar.add(jButtonFile_OpenFile);//���ļ��Ӱ�ť
        //jToolBar.add(jButtonFile_CloseFile);//�ر��ļ��Ӱ�ť
        //jToolBar.add(jButtonFile_SaveFile);//�����ļ��Ӱ�ť
        jToolBar.add(jButtonFile_SaveAll);//����ȫ���ļ��Ӱ�ť
        jToolBar.add(jButtonUpload);//�ϴ���ť

        jToolBar.addSeparator();

        /*/�༭��ť
        jToolBar.add(jButtonEdit_Undo);//�����Ӱ�ť
        jToolBar.add(jButtonEdit_Redo);//�����Ӱ�ť
        jToolBar.add(jButtonEdit_Cut);//�����Ӱ�ť
        jToolBar.add(jButtonEdit_Copy);//�����Ӱ�ť
        jToolBar.add(jButtonEdit_Paste);//ճ���Ӱ�ť
        jToolBar.add(jButtonEdit_Delete);//ɾ���Ӱ�ť

        jToolBar.addSeparator(); //*/

        //���Ұ�ť
        //jToolBar.add(jButtonSearch_Find);//�����Ӱ�ť
        jToolBar.add(jButtonSearch_FindInPath);//��ָ��·�������Ӱ�ť
        //jToolBar.add(jButtonSearch_FindDown);//������һ���Ӱ�ť
        //jToolBar.add(jButtonSearch_FindUp);//������һ���Ӱ�ť
        //jToolBar.add(jButtonSearch_Replace);//�滻�Ӱ�ť
        jToolBar.add(jButtonSearch_ReplaceInPath);//��ָ��·���滻�Ӱ�ť
        //jToolBar.add(jButtonSearch_Goto);//�����Ӱ�ť

        jToolBar.addSeparator();
        //��Ŀ
        jToolBar.add(jButtonProject_CompileProject);//������Ŀ�Ӱ�ť

        //����
        jToolBar.add(jButtonRun_RunProject);//������Ŀ�Ӱ�ť
        jToolBar.add(jButtonRun_DebugProject);//������Ŀ�Ӱ�ť
        jToolBar.add(jButtonRun_DebugProjectTime);//��ʱִ�е�����Ŀ�Ӱ�ť

        //jToolBar.addSeparator();

        //jToolBar.add(jButtonRun_RunFile);//�����ļ��Ӱ�ť
        jToolBar.add(jButtonRun_DebugFile);//�����ļ��Ӱ�ť
 //       jToolBar.add(jButtonRun_DebugFileTime);//��ʱִ�е����ļ��Ӱ�ť
        
        jToolBar.add(jButtonRun_Answer);
        
        jToolBar.addSeparator();

        jToolBar.add(jButtonRun_Stop);//ֹͣ�Ӱ�ť
        jToolBar.add(jButtonRun_Pause);//��ͣ�Ӱ�ť
        jToolBar.add(jButtonRun_StepOver);//��һ���Ӱ�ť
        jToolBar.add(jButtonRun_StepInto);//��һ���Ӱ�ť
        jToolBar.add(jButtonRun_StepOut);//�����Ӱ�ť
//        jToolBar.add(jButtonRun_SingleInstruction);//��ָ���Ӱ�ť
        jToolBar.add( timeSelector );//��ʱ����ʱ��ѡ����
        
    }

}
