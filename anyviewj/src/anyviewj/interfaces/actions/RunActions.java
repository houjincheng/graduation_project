package anyviewj.interfaces.actions;

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

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import anyviewj.console.ConsoleCenter;
import anyviewj.debug.actions.AnswerAction;
import anyviewj.debug.actions.DebugFileAction;
import anyviewj.debug.actions.DebugProjectTimeAction;
import anyviewj.debug.actions.StepIntoAction;
import anyviewj.debug.actions.StepOutAction;
import anyviewj.debug.actions.StepOverAction;
import anyviewj.debug.actions.StopDebugAction;
import anyviewj.debug.actions.PauseDebugAction;
import anyviewj.debug.manager.BreakpointManager;
import anyviewj.interfaces.resource.AcceleratorKeyResource;
import anyviewj.interfaces.resource.ActionResource;
import anyviewj.interfaces.resource.ResourceManager;
import anyviewj.interfaces.ui.panel.TextEditor;
//import anyviewj.test.classinfo.javaclass.test.*;
//import anyviewj.classinfo.javaclass.JavaClassesManager;

public class RunActions extends CommandAction{
    public final Action menuRunAction; //运行菜单
    public final Action runProjectAction; //运行项目
//    public final Action runFileAction; //运行文件
    public final Action debugProjectAction; //调试项目
    public final Action debugFileAction; //调试文件
    public final Action debugProjectTimeAction; //定时执行调试项目
    public final Action debugFileTimeAction; //定时执行调试文件
    public final Action pauseAction; //暂停
    public final Action stopDebugAction; //停止
    public final Action stepOverAction; //下一行
    public final Action stepIntoAction; //下一步
    public final Action stepOutAction; //步出
    public final Action singleInstructionAction; //单指令
    
    public final Action answerAction;
    
    
    public BreakpointManager bpm;
    public TextEditor td;
    
    public RunActions(ConsoleCenter aCenter) {
        this(aCenter, 0); //使用这种构造方式,是为了让程序代码清晰
        refreshActions(); //设置Actions的名称,提示,图标等.        
    }

    /**
     * 更新Actions的名称,提示,图标等.
     * 特别当资源发生改变(如语言改变)时,调用该函数来更新相关资料
     */
    @Override
	public void refreshActions() {
        ResourceManager rm = center.resourceManager;
        assert (rm != null);
        ActionResource resource = rm.getActionResource();
        AcceleratorKeyResource akResource = rm.getAcceleratorKeyResource();

        //运行菜单
        menuRunAction.putValue(Action.NAME,
                                  resource.getActionName(ActionResource.MENURUN));
        menuRunAction.putValue(Action.ACTION_COMMAND_KEY,
                                  resource.getActionKey(ActionResource.MENURUN));
        //运行项目
        putActionValue(runProjectAction,ActionResource.RUN_PROJECT,resource,akResource);

        //运行文件
        //putActionValue(runFileAction,resource.RUN_FILE,resource,akResource);

        //调试项目
        putActionValue(debugProjectAction,ActionResource.DEBUG_PROJECT,resource,akResource);

        //调试文件
        putActionValue(debugFileAction,ActionResource.DEBUG_FILE,resource,akResource);

        //定时执行调试项目
        putActionValue(debugProjectTimeAction,ActionResource.DEBUG_PROJECT_TIME,resource,akResource);

        //定时执行调试文件
        //putActionValue(debugFileTimeAction,resource.DEBUG_FILE_TIME,resource,akResource);

        //暂停
        putActionValue(pauseAction,ActionResource.PAUSE,resource,akResource);

        //停止
        putActionValue(stopDebugAction,ActionResource.STOP,resource,akResource);

        //下一行
        putActionValue(stepOverAction,ActionResource.STEPOVER,resource,akResource);

        //下一步
        putActionValue(stepIntoAction,ActionResource.STEPINTO,resource,akResource);
        
        //步出
        putActionValue(stepOutAction,ActionResource.STEPOUT,resource,akResource);

        //单指令
        putActionValue(singleInstructionAction,ActionResource.SINGLE_INSTRUCTION,resource,akResource);

        //单步运行
        putActionValue(answerAction,ActionResource.RUN_ANSWER,resource,akResource);
    }

     public void setBPM(BreakpointManager bpm){
    	 this.bpm = bpm;
     }
     
     public void setTD(TextEditor td){
    	 this.td = td;
     }
    
    /**
     * 处理Action的响应
     * @param aCenter ConsoleCenter
     * @param noMeaning int
     */
    private RunActions(ConsoleCenter aCenter, int noMeaning) {
        super(aCenter);

        //运行菜单
        menuRunAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	
            }
        };

        //运行项目
        runProjectAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {
                //center.interpreterManager.runProject();
            }
        };

        //运行文件
//        runFileAction = new AbstractAction() {
//            public void actionPerformed(ActionEvent e) {
//                //center.interpreterManager.runFile(e);
//            }
//        };

//        //调试项目
//        debugProjectAction = new AbstractAction() {
//            public void actionPerformed(ActionEvent e) {            	
//                //center.interpreterManager.debugProject();
//                //TestClassLoader.test((JavaClassesManager)center.classesManager);
//            }
//        };
        //调试项目
        debugProjectAction = new DebugFileAction();

        //调试文件
        debugFileAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e){            	
                //center.interpreterManager.debugFile(e);
            	DebugFileAction debugFile = new DebugFileAction();
            	debugFile.actionPerformed(e);
            }
        };

        //定时执行调试项目
        debugProjectTimeAction = DebugProjectTimeAction.getDebugProjectTimeAction( aCenter );

//      需要获取全局控制中心，一遍启动虚拟机
        debugFileTimeAction = DebugProjectTimeAction.getDebugProjectTimeAction( aCenter );

        //暂停
        pauseAction = new PauseDebugAction();

        //停止
        stopDebugAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	StopDebugAction stopDebug = new StopDebugAction();
            	stopDebug.actionPerformed(e);
            }
        };

        //下一行
        stepOverAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	System.out.println("Step Over");
            	StepOverAction stepOver = new StepOverAction();
            	stepOver.actionPerformed(e);
            }
        };

        //下一步
        stepIntoAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	System.out.println("Step Into");
            	StepIntoAction stepInto = new StepIntoAction();
            	stepInto.actionPerformed(e);
            }
        };
        
        //步出
        stepOutAction = new StepOutAction();
        
        //单指令
        singleInstructionAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {
                //center.interpreterManager.singleInstruction();
            }
        };
         
        answerAction = new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			  AnswerAction answer = new AnswerAction();
			  answer.actionPerformed(e);
			}
        	
        };
        
        
    }

}
