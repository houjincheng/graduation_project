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
    //文件按钮
    public final JButton jButtonFile_New = new JButton();//新建项目,类,接口,文件等子按钮
    public final JButton jButtonFile_OpenProject = new JButton();//打开项目子按钮
    public final JButton jButtonFile_CloseProject = new JButton();//关闭项目子按钮
    //public final JButton jButtonFile_OpenFile = new JButton();//打开文件子按钮
    //public final JButton jButtonFile_CloseFile = new JButton();//关闭文件子按钮
    //public final JButton jButtonFile_SaveFile = new JButton();//保存文件子按钮
    public final JButton jButtonFile_SaveAll = new JButton();//保存全部文件子按钮

    /*/编辑按钮
    public final JButton jButtonEdit_Undo = new JButton();//撤销子按钮
    public final JButton jButtonEdit_Redo = new JButton();//重做子按钮
    public final JButton jButtonEdit_Cut = new JButton();//剪切子按钮
    public final JButton jButtonEdit_Copy = new JButton();//复制子按钮
    public final JButton jButtonEdit_Paste = new JButton();//粘贴子按钮
    public final JButton jButtonEdit_Delete = new JButton();//删除子按钮//*/
    //查找按钮
    //public final JButton jButtonSearch_Find = new JButton();//查找子按钮
    public final JButton jButtonSearch_FindInPath = new JButton();//在指定路径查找子按钮
    //public final JButton jButtonSearch_FindDown = new JButton();//查找下一个子按钮
    //public final JButton jButtonSearch_FindUp = new JButton();//查找上一个子按钮
    //public final JButton jButtonSearch_Replace = new JButton();//替换子按钮
    public final JButton jButtonSearch_ReplaceInPath = new JButton();//在指定路径替换子按钮
    //public final JButton jButtonSearch_Goto = new JButton();//跳至子按钮

    //项目
    public final JButton jButtonProject_CompileProject = new JButton();//编译项目子按钮

    //运行
    public final JButton jButtonRun_RunProject = new JButton();//运行项目子按钮
    //public final JButton jButtonRun_RunFile = new JButton();//运行文件子按钮
    public final JButton jButtonRun_DebugProject = new JButton();//调试项目子按钮
    public final JButton jButtonRun_DebugFile = new JButton();//调试文件子按钮
    public final JButton jButtonRun_DebugProjectTime = new JButton();//定时执行调试项目子按钮
//    public final JButton jButtonRun_DebugFileTime = new JButton();//定时执行调试文件子按钮
    public final JButton jButtonRun_Pause = new JButton();//暂停子按钮
    public final JButton jButtonRun_Stop = new JButton();//停止子按钮
    public final JButton jButtonRun_StepOver = new JButton();//下一行子按钮
    public final JButton jButtonRun_StepInto = new JButton();//下一步子按钮
    public final JButton jButtonRun_StepOut = new JButton();//步出子按钮
//    public final JButton jButtonRun_SingleInstruction = new JButton();//单指令子按钮
    public final JButton jButtonRun_Answer = new JButton();
    
    public final JButton jButtonUpload = new JButton(); //上传
    /**
     * hou 2013年8月10日19:07:47
     * 作为一个时间的选择器，其值作为定时调试的时间间隔
     */
    public final DebugTimeSelector timeSelector = new DebugTimeSelector
    		( jButtonRun_StepOver.getBackground() );
    
    
   
    private JToolBar jToolBar;
    public ToolButtons(JToolBar owner) {
        assert(owner != null);
        this.jToolBar = owner;
    }
//	在移植过程中添加的。 hou 2013-5-30 11:40:56
    public ToolButtons(CommandManager commandManager, JToolBar owner) {
    	
    	assert(owner != null);
        this.jToolBar = owner;
        
    	initToolButtons(commandManager);
    }

	//工具栏按钮
    /**
     * 
     * @param cm
     */
    public void initToolButtons(CommandManager cm){
        //文件按钮设置动作
        jButtonFile_New.putClientProperty("hideActionText",Boolean.TRUE);//新建项目,类,接口,文件等子按钮
        jButtonFile_OpenProject.putClientProperty("hideActionText",Boolean.TRUE);//打开项目子按钮
        jButtonFile_CloseProject.putClientProperty("hideActionText",Boolean.TRUE);//关闭项目子按钮
        //jButtonFile_OpenFile.putClientProperty("hideActionText",Boolean.TRUE);//打开文件子按钮
        //jButtonFile_CloseFile.putClientProperty("hideActionText",Boolean.TRUE);//关闭文件子按钮
        //jButtonFile_SaveFile.putClientProperty("hideActionText",Boolean.TRUE);//保存文件子按钮
        jButtonFile_SaveAll.putClientProperty("hideActionText",Boolean.TRUE);//保存全部文件子按钮
       // jButtonUpload.putClientProperty("hideActionText",Boolean.TRUE);

        jButtonFile_New.setAction(cm.fileActions.newAction);//新建项目,类,接口,文件等子按钮
        jButtonFile_OpenProject.setAction(cm.fileActions.openProjectAction);//打开项目子按钮
        jButtonFile_CloseProject.setAction(cm.fileActions.closeProjectAction);//关闭项目子按钮
        //jButtonFile_OpenFile.setAction(cm.fileActions.openFileAction);//打开文件子按钮
        //jButtonFile_CloseFile.setAction(cm.fileActions.closeFileAction);//关闭文件子按钮
        //jButtonFile_SaveFile.setAction(cm.fileActions.saveFileAction);//保存文件子按钮
        jButtonFile_SaveAll.setAction(cm.fileActions.saveAllAction);//保存全部文件子按钮
        
        jButtonUpload.setAction(cm.fileActions.uploadAction);//上传

        /*/编辑按钮
        jButtonEdit_Undo.putClientProperty("hideActionText",Boolean.TRUE);//撤销子按钮
        jButtonEdit_Redo.putClientProperty("hideActionText",Boolean.TRUE);//重做子按钮
        jButtonEdit_Cut.putClientProperty("hideActionText",Boolean.TRUE);//剪切子按钮
        jButtonEdit_Copy.putClientProperty("hideActionText",Boolean.TRUE);//复制子按钮
        jButtonEdit_Paste.putClientProperty("hideActionText",Boolean.TRUE);//粘贴子按钮
        jButtonEdit_Delete.putClientProperty("hideActionText",Boolean.TRUE);//删除子按钮

        jButtonEdit_Undo.setAction(cm.editActions.undoAction);//撤销子按钮
        jButtonEdit_Redo.setAction(cm.editActions.redoAction);//重做子按钮
        jButtonEdit_Cut.setAction(cm.editActions.cutAction);//剪切子按钮
        jButtonEdit_Copy.setAction(cm.editActions.copyAction);//复制子按钮
        jButtonEdit_Paste.setAction(cm.editActions.pasteAction);//粘贴子按钮
        jButtonEdit_Delete.setAction(cm.editActions.deleteAction);//删除子按钮 */

        //查找按钮
        //jButtonSearch_Find.putClientProperty("hideActionText",Boolean.TRUE);//查找子按钮
        jButtonSearch_FindInPath.putClientProperty("hideActionText",Boolean.TRUE);//在指定路径查找子按钮
        //jButtonSearch_FindDown.putClientProperty("hideActionText",Boolean.TRUE);//查找下一个子按钮
        //jButtonSearch_FindUp.putClientProperty("hideActionText",Boolean.TRUE);//查找上一个子按钮
        //jButtonSearch_Replace.putClientProperty("hideActionText",Boolean.TRUE);//替换子按钮
        jButtonSearch_ReplaceInPath.putClientProperty("hideActionText",Boolean.TRUE);//在指定路径替换子按钮
        //jButtonSearch_Goto.putClientProperty("hideActionText",Boolean.TRUE);//跳至子按钮

        //jButtonSearch_Find.setAction(cm.searchActions.findAction);//查找子按钮
        jButtonSearch_FindInPath.setAction(cm.searchActions.findInPathction);//在指定路径查找子按钮
        //jButtonSearch_FindDown.setAction(cm.searchActions.findDownAction);//查找下一个子按钮
        //jButtonSearch_FindUp.setAction(cm.searchActions.findUpAction);//查找上一个子按钮
        //jButtonSearch_Replace.setAction(cm.searchActions.replaceAction);//替换子按钮
        jButtonSearch_ReplaceInPath.setAction(cm.searchActions.replaceInPathAction);//在指定路径替换子按钮
        //jButtonSearch_Goto.setAction(cm.searchActions.gotoAction);//跳至子按钮

        //项目
        jButtonProject_CompileProject.putClientProperty("hideActionText",Boolean.TRUE);//编译项目子按钮
        jButtonProject_CompileProject.setAction(cm.projectActions.compileProjectAction);//编译项目子按钮
        //运行设置动作
        jButtonRun_RunProject.putClientProperty("hideActionText",Boolean.TRUE);//运行项目子按钮
        //jButtonRun_RunFile.putClientProperty("hideActionText",Boolean.TRUE);//运行文件子按钮
        jButtonRun_DebugProject.putClientProperty("hideActionText",Boolean.TRUE);//调试项目子按钮
        jButtonRun_DebugFile.putClientProperty("hideActionText",Boolean.TRUE);//调试文件子按钮
        jButtonRun_DebugProjectTime.putClientProperty("hideActionText",Boolean.TRUE);//定时执行调试项目子按钮
//        jButtonRun_DebugFileTime.putClientProperty("hideActionText",Boolean.TRUE);//定时执行调试文件子按钮
        jButtonRun_Pause.putClientProperty("hideActionText",Boolean.TRUE);//暂停子按钮
        jButtonRun_Stop.putClientProperty("hideActionText",Boolean.TRUE);//停止子按钮
        jButtonRun_StepOver.putClientProperty("hideActionText",Boolean.TRUE);//下一行子按钮
        jButtonRun_StepInto.putClientProperty("hideActionText",Boolean.TRUE);//下一步子按钮
        jButtonRun_StepOut.putClientProperty("hideActionText",Boolean.TRUE);//步出子按钮
//        jButtonRun_SingleInstruction.putClientProperty("hideActionText",Boolean.TRUE);//单指令子按钮

        jButtonRun_RunProject.setAction(cm.runActions.runProjectAction);//运行项目子按钮
        //jButtonRun_RunFile.setAction(cm.runActions.runFileAction);//运行文件子按钮

        jButtonRun_Answer.setAction(cm.runActions.answerAction);
        
        jButtonRun_DebugProject.setAction(cm.runActions.debugProjectAction);//调试项目子按钮
        
        jButtonRun_DebugFile.setAction(cm.runActions.debugFileAction);//调试文件子按钮
        jButtonRun_DebugProjectTime.setAction(cm.runActions.debugProjectTimeAction);//定时执行调试项目子按钮
//        jButtonRun_DebugFileTime.setAction(cm.runActions.debugFileTimeAction);//定时执行调试文件子按钮
        jButtonRun_Pause.setAction(cm.runActions.pauseAction);//暂停子按钮
        jButtonRun_Stop.setAction(cm.runActions.stopDebugAction);//停止子按钮
        jButtonRun_StepOver.setAction(cm.runActions.stepOverAction);//下一行子按钮
        jButtonRun_StepInto.setAction(cm.runActions.stepIntoAction);//下一步子按钮
        jButtonRun_StepOut.setAction(cm.runActions.stepOutAction);//步出子按钮
//        jButtonRun_SingleInstruction.setAction(cm.runActions.singleInstructionAction);//单指令子按钮

        
        
        //文件按钮
        jToolBar.add(jButtonFile_New);//新建项目,类,接口,文件等子按钮
        jToolBar.add(this.jButtonFile_OpenProject);//打开项目子按钮
        jToolBar.add(this.jButtonFile_CloseProject);//关闭项目子按钮
        //jToolBar.add(jButtonFile_OpenFile);//打开文件子按钮
        //jToolBar.add(jButtonFile_CloseFile);//关闭文件子按钮
        //jToolBar.add(jButtonFile_SaveFile);//保存文件子按钮
        jToolBar.add(jButtonFile_SaveAll);//保存全部文件子按钮
        jToolBar.add(jButtonUpload);//上传按钮

        jToolBar.addSeparator();

        /*/编辑按钮
        jToolBar.add(jButtonEdit_Undo);//撤销子按钮
        jToolBar.add(jButtonEdit_Redo);//重做子按钮
        jToolBar.add(jButtonEdit_Cut);//剪切子按钮
        jToolBar.add(jButtonEdit_Copy);//复制子按钮
        jToolBar.add(jButtonEdit_Paste);//粘贴子按钮
        jToolBar.add(jButtonEdit_Delete);//删除子按钮

        jToolBar.addSeparator(); //*/

        //查找按钮
        //jToolBar.add(jButtonSearch_Find);//查找子按钮
        jToolBar.add(jButtonSearch_FindInPath);//在指定路径查找子按钮
        //jToolBar.add(jButtonSearch_FindDown);//查找下一个子按钮
        //jToolBar.add(jButtonSearch_FindUp);//查找上一个子按钮
        //jToolBar.add(jButtonSearch_Replace);//替换子按钮
        jToolBar.add(jButtonSearch_ReplaceInPath);//在指定路径替换子按钮
        //jToolBar.add(jButtonSearch_Goto);//跳至子按钮

        jToolBar.addSeparator();
        //项目
        jToolBar.add(jButtonProject_CompileProject);//编译项目子按钮

        //运行
        jToolBar.add(jButtonRun_RunProject);//运行项目子按钮
        jToolBar.add(jButtonRun_DebugProject);//调试项目子按钮
        jToolBar.add(jButtonRun_DebugProjectTime);//定时执行调试项目子按钮

        //jToolBar.addSeparator();

        //jToolBar.add(jButtonRun_RunFile);//运行文件子按钮
        jToolBar.add(jButtonRun_DebugFile);//调试文件子按钮
 //       jToolBar.add(jButtonRun_DebugFileTime);//定时执行调试文件子按钮
        
        jToolBar.add(jButtonRun_Answer);
        
        jToolBar.addSeparator();

        jToolBar.add(jButtonRun_Stop);//停止子按钮
        jToolBar.add(jButtonRun_Pause);//暂停子按钮
        jToolBar.add(jButtonRun_StepOver);//下一行子按钮
        jToolBar.add(jButtonRun_StepInto);//下一步子按钮
        jToolBar.add(jButtonRun_StepOut);//步出子按钮
//        jToolBar.add(jButtonRun_SingleInstruction);//单指令子按钮
        jToolBar.add( timeSelector );//定时调试时间选择器
        
    }

}
