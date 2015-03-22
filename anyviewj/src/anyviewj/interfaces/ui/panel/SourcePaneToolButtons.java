package anyviewj.interfaces.ui.panel;

import javax.swing.JButton;
import javax.swing.JToolBar;

import anyviewj.interfaces.actions.CommandManager;

public class SourcePaneToolButtons {
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
    public final JButton toolButtonRun_DebugProjectTime = new JButton();//定时执行调试项目子按钮
    public final JButton toolButtonRun_DebugFileTime = new JButton(); //定时执行调试文件子按钮
    public final JButton toolButtonRun_Pause = new JButton(); //暂停子按钮
    public final JButton toolButtonRun_Stop = new JButton(); //停止子按钮
    public final JButton toolButtonRun_StepOver = new JButton(); //下一行子按钮
    public final JButton toolButtonRun_StepInto = new JButton(); //下一步子按钮
    public final JButton toolButtonRun_StepOut = new JButton(); //步出子按钮
//    public final JButton toolButtonRun_SingleInstruction = new JButton(); //单指令子按钮

    public SourcePaneToolButtons(CommandManager cm,JToolBar toolbar) {
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
        toolButtonProject_CompileFile.setAction(cm.projectActions.
                                                compileFileAction); //编译文件子按钮
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
        toolButtonRun_StepOut.putClientProperty("hideActionText", Boolean.TRUE); //步出子按钮
//        toolButtonRun_SingleInstruction.putClientProperty("hideActionText",
//                Boolean.TRUE); //单指令子按钮

        //toolButtonRun_RunProject.setAction(cm.runActions.runProjectAction);//运行项目子按钮
//        toolButtonRun_RunFile.setAction(cm.runActions.runFileAction); //运行文件子按钮
        //toolButtonRun_DebugProject.setAction(cm.runActions.debugProjectAction);//调试项目子按钮
        toolButtonRun_DebugFile.setAction(cm.runActions.debugFileAction); //调试文件子按钮
        //toolButtonRun_DebugProjectTime.setAction(cm.runActions.debugProjectTimeAction);//定时执行调试项目子按钮
//        toolButtonRun_DebugFileTime.setAction(cm.runActions.debugFileTimeAction); //定时执行调试文件子按钮
        toolButtonRun_Pause.setAction(cm.runActions.pauseAction); //暂停子按钮
        toolButtonRun_Stop.setAction(cm.runActions.stopDebugAction); //停止子按钮
        toolButtonRun_StepOver.setAction(cm.runActions.stepOverAction); //下一行子按钮
        toolButtonRun_StepInto.setAction(cm.runActions.stepIntoAction); //下一步子按钮
        toolButtonRun_StepOut.setAction(cm.runActions.stepOutAction); //步出子按钮
//        toolButtonRun_SingleInstruction.setAction(cm.runActions.
//                                                  singleInstructionAction); //单指令子按钮

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
