package anyviewj.interfaces.ui.panel;

import javax.swing.JButton;
import javax.swing.JToolBar;

import anyviewj.interfaces.actions.CommandManager;

public class SourcePaneToolButtons {
	//������
    //�ļ���ť
    public final JButton toolButtonFile_New = new JButton(); //�½���Ŀ,��,�ӿ�,�ļ����Ӱ�ť
    public final JButton toolButtonFile_OpenFile = new JButton(); //���ļ��Ӱ�ť
    //public final JButton toolButtonFile_CloseFile = new JButton();//�ر��ļ��Ӱ�ť
    public final JButton toolButtonFile_SaveFile = new JButton(); //�����ļ��Ӱ�ť
    //public final JButton toolButtonFile_SaveAll = new JButton();//����ȫ���ļ��Ӱ�ť

    //�༭��ť
    public final JButton toolButtonEdit_Undo = new JButton(); //�����Ӱ�ť
    public final JButton toolButtonEdit_Redo = new JButton(); //�����Ӱ�ť
    public final JButton toolButtonEdit_Cut = new JButton(); //�����Ӱ�ť
    public final JButton toolButtonEdit_Copy = new JButton(); //�����Ӱ�ť
    public final JButton toolButtonEdit_Paste = new JButton(); //ճ���Ӱ�ť
    //public final JButton toolButtonEdit_Delete = new JButton();//ɾ���Ӱ�ť
    //���Ұ�ť
    public final JButton toolButtonSearch_Find = new JButton(); //�����Ӱ�ť
    //public final JButton toolButtonSearch_FindInPath = new JButton();//��ָ��·�������Ӱ�ť
    public final JButton toolButtonSearch_FindDown = new JButton(); //������һ���Ӱ�ť
    public final JButton toolButtonSearch_FindUp = new JButton(); //������һ���Ӱ�ť
    public final JButton toolButtonSearch_Replace = new JButton(); //�滻�Ӱ�ť
    //public final JButton toolButtonSearch_ReplaceInPath = new JButton();//��ָ��·���滻�Ӱ�ť
    public final JButton toolButtonSearch_Goto = new JButton(); //�����Ӱ�ť

    //��Ŀ
    public final JButton toolButtonProject_CompileFile = new JButton(); //�����ļ��Ӱ�ť

    //����
    //public final JButton toolButtonRun_RunProject = new JButton();//������Ŀ�Ӱ�ť
    public final JButton toolButtonRun_RunFile = new JButton(); //�����ļ��Ӱ�ť
    //public final JButton toolButtonRun_DebugProject = new JButton();//������Ŀ�Ӱ�ť
    public final JButton toolButtonRun_DebugFile = new JButton(); //�����ļ��Ӱ�ť
    public final JButton toolButtonRun_DebugProjectTime = new JButton();//��ʱִ�е�����Ŀ�Ӱ�ť
    public final JButton toolButtonRun_DebugFileTime = new JButton(); //��ʱִ�е����ļ��Ӱ�ť
    public final JButton toolButtonRun_Pause = new JButton(); //��ͣ�Ӱ�ť
    public final JButton toolButtonRun_Stop = new JButton(); //ֹͣ�Ӱ�ť
    public final JButton toolButtonRun_StepOver = new JButton(); //��һ���Ӱ�ť
    public final JButton toolButtonRun_StepInto = new JButton(); //��һ���Ӱ�ť
    public final JButton toolButtonRun_StepOut = new JButton(); //�����Ӱ�ť
//    public final JButton toolButtonRun_SingleInstruction = new JButton(); //��ָ���Ӱ�ť

    public SourcePaneToolButtons(CommandManager cm,JToolBar toolbar) {
        //�ļ���ť���ö���
        toolButtonFile_New.putClientProperty("hideActionText", Boolean.TRUE); //�½���Ŀ,��,�ӿ�,�ļ����Ӱ�ť
        toolButtonFile_OpenFile.putClientProperty("hideActionText",
                                                  Boolean.TRUE); //���ļ��Ӱ�ť
        //toolButtonFile_CloseFile.putClientProperty("hideActionText",Boolean.TRUE);//�ر��ļ��Ӱ�ť
        toolButtonFile_SaveFile.putClientProperty("hideActionText",
                                                  Boolean.TRUE); //�����ļ��Ӱ�ť
        //toolButtonFile_SaveAll.putClientProperty("hideActionText",Boolean.TRUE);//����ȫ���ļ��Ӱ�ť

        toolButtonFile_New.setAction(cm.fileActions.newAction); //�½���Ŀ,��,�ӿ�,�ļ����Ӱ�ť
        toolButtonFile_OpenFile.setAction(cm.fileActions.openFileAction); //���ļ��Ӱ�ť
        //toolButtonFile_CloseFile.setAction(cm.fileActions.closeFileAction);//�ر��ļ��Ӱ�ť
        toolButtonFile_SaveFile.setAction(cm.fileActions.saveFileAction); //�����ļ��Ӱ�ť
        //toolButtonFile_SaveAll.setAction(cm.fileActions.saveAllAction);//����ȫ���ļ��Ӱ�ť

        //�༭��ť
        toolButtonEdit_Undo.putClientProperty("hideActionText", Boolean.TRUE); //�����Ӱ�ť
        toolButtonEdit_Redo.putClientProperty("hideActionText", Boolean.TRUE); //�����Ӱ�ť
        toolButtonEdit_Cut.putClientProperty("hideActionText", Boolean.TRUE); //�����Ӱ�ť
        toolButtonEdit_Copy.putClientProperty("hideActionText", Boolean.TRUE); //�����Ӱ�ť
        toolButtonEdit_Paste.putClientProperty("hideActionText", Boolean.TRUE); //ճ���Ӱ�ť
        //toolButtonEdit_Delete.putClientProperty("hideActionText",Boolean.TRUE);//ɾ���Ӱ�ť

        toolButtonEdit_Undo.setAction(cm.editActions.undoAction); //�����Ӱ�ť
        toolButtonEdit_Redo.setAction(cm.editActions.redoAction); //�����Ӱ�ť
        toolButtonEdit_Cut.setAction(cm.editActions.cutAction); //�����Ӱ�ť
        toolButtonEdit_Copy.setAction(cm.editActions.copyAction); //�����Ӱ�ť
        toolButtonEdit_Paste.setAction(cm.editActions.pasteAction); //ճ���Ӱ�ť
        //toolButtonEdit_Delete.setAction(cm.editActions.deleteAction);//ɾ���Ӱ�ť

        //���Ұ�ť
        toolButtonSearch_Find.putClientProperty("hideActionText", Boolean.TRUE); //�����Ӱ�ť
        //toolButtonSearch_FindInPath.putClientProperty("hideActionText",Boolean.TRUE);//��ָ��·�������Ӱ�ť
        toolButtonSearch_FindDown.putClientProperty("hideActionText",
                Boolean.TRUE); //������һ���Ӱ�ť
        toolButtonSearch_FindUp.putClientProperty("hideActionText",
                                                  Boolean.TRUE); //������һ���Ӱ�ť
        toolButtonSearch_Replace.putClientProperty("hideActionText",
                Boolean.TRUE); //�滻�Ӱ�ť
        //toolButtonSearch_ReplaceInPath.putClientProperty("hideActionText",Boolean.TRUE);//��ָ��·���滻�Ӱ�ť
        toolButtonSearch_Goto.putClientProperty("hideActionText", Boolean.TRUE); //�����Ӱ�ť

        toolButtonSearch_Find.setAction(cm.searchActions.findAction); //�����Ӱ�ť
        //toolButtonSearch_FindInPath.setAction(cm.searchActions.findInPathction);//��ָ��·�������Ӱ�ť
        toolButtonSearch_FindDown.setAction(cm.searchActions.findDownAction); //������һ���Ӱ�ť
        toolButtonSearch_FindUp.setAction(cm.searchActions.findUpAction); //������һ���Ӱ�ť
        toolButtonSearch_Replace.setAction(cm.searchActions.replaceAction); //�滻�Ӱ�ť
        //toolButtonSearch_ReplaceInPath.setAction(cm.searchActions.replaceInPathAction);//��ָ��·���滻�Ӱ�ť
        toolButtonSearch_Goto.setAction(cm.searchActions.gotoAction); //�����Ӱ�ť
        //��Ŀ
        toolButtonProject_CompileFile.putClientProperty("hideActionText",
                Boolean.TRUE); //�����ļ��Ӱ�ť
        toolButtonProject_CompileFile.setAction(cm.projectActions.
                                                compileFileAction); //�����ļ��Ӱ�ť
        //�������ö���
        //toolButtonRun_RunProject.putClientProperty("hideActionText",Boolean.TRUE);//������Ŀ�Ӱ�ť
        toolButtonRun_RunFile.putClientProperty("hideActionText", Boolean.TRUE); //�����ļ��Ӱ�ť
        //toolButtonRun_DebugProject.putClientProperty("hideActionText",Boolean.TRUE);//������Ŀ�Ӱ�ť
        toolButtonRun_DebugFile.putClientProperty("hideActionText",
                                                  Boolean.TRUE); //�����ļ��Ӱ�ť
        //toolButtonRun_DebugProjectTime.putClientProperty("hideActionText",Boolean.TRUE);//��ʱִ�е�����Ŀ�Ӱ�ť
        toolButtonRun_DebugFileTime.putClientProperty("hideActionText",
                Boolean.TRUE); //��ʱִ�е����ļ��Ӱ�ť
        toolButtonRun_Pause.putClientProperty("hideActionText", Boolean.TRUE); //��ͣ�Ӱ�ť
        toolButtonRun_Stop.putClientProperty("hideActionText", Boolean.TRUE); //ֹͣ�Ӱ�ť
        toolButtonRun_StepOver.putClientProperty("hideActionText", Boolean.TRUE); //��һ���Ӱ�ť
        toolButtonRun_StepInto.putClientProperty("hideActionText", Boolean.TRUE); //��һ���Ӱ�ť
        toolButtonRun_StepOut.putClientProperty("hideActionText", Boolean.TRUE); //�����Ӱ�ť
//        toolButtonRun_SingleInstruction.putClientProperty("hideActionText",
//                Boolean.TRUE); //��ָ���Ӱ�ť

        //toolButtonRun_RunProject.setAction(cm.runActions.runProjectAction);//������Ŀ�Ӱ�ť
//        toolButtonRun_RunFile.setAction(cm.runActions.runFileAction); //�����ļ��Ӱ�ť
        //toolButtonRun_DebugProject.setAction(cm.runActions.debugProjectAction);//������Ŀ�Ӱ�ť
        toolButtonRun_DebugFile.setAction(cm.runActions.debugFileAction); //�����ļ��Ӱ�ť
        //toolButtonRun_DebugProjectTime.setAction(cm.runActions.debugProjectTimeAction);//��ʱִ�е�����Ŀ�Ӱ�ť
//        toolButtonRun_DebugFileTime.setAction(cm.runActions.debugFileTimeAction); //��ʱִ�е����ļ��Ӱ�ť
        toolButtonRun_Pause.setAction(cm.runActions.pauseAction); //��ͣ�Ӱ�ť
        toolButtonRun_Stop.setAction(cm.runActions.stopDebugAction); //ֹͣ�Ӱ�ť
        toolButtonRun_StepOver.setAction(cm.runActions.stepOverAction); //��һ���Ӱ�ť
        toolButtonRun_StepInto.setAction(cm.runActions.stepIntoAction); //��һ���Ӱ�ť
        toolButtonRun_StepOut.setAction(cm.runActions.stepOutAction); //�����Ӱ�ť
//        toolButtonRun_SingleInstruction.setAction(cm.runActions.
//                                                  singleInstructionAction); //��ָ���Ӱ�ť

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
        toolbar.add(toolButtonRun_RunFile);
        toolbar.add(toolButtonRun_DebugFileTime);
        toolbar.add(toolButtonRun_DebugFile);
        toolbar.addSeparator();
        toolbar.add(toolButtonRun_Stop);
        toolbar.add(toolButtonRun_Pause);
        toolbar.add(toolButtonRun_StepOver);
        toolbar.add(toolButtonRun_StepInto);
        toolbar.add(toolButtonRun_StepOut);
//        toolbar.add(toolButtonRun_SingleInstruction);
    }
}
