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
    public final Action menuRunAction; //���в˵�
    public final Action runProjectAction; //������Ŀ
//    public final Action runFileAction; //�����ļ�
    public final Action debugProjectAction; //������Ŀ
    public final Action debugFileAction; //�����ļ�
    public final Action debugProjectTimeAction; //��ʱִ�е�����Ŀ
    public final Action debugFileTimeAction; //��ʱִ�е����ļ�
    public final Action pauseAction; //��ͣ
    public final Action stopDebugAction; //ֹͣ
    public final Action stepOverAction; //��һ��
    public final Action stepIntoAction; //��һ��
    public final Action stepOutAction; //����
    public final Action singleInstructionAction; //��ָ��
    
    public final Action answerAction;
    
    
    public BreakpointManager bpm;
    public TextEditor td;
    
    public RunActions(ConsoleCenter aCenter) {
        this(aCenter, 0); //ʹ�����ֹ��췽ʽ,��Ϊ���ó����������
        refreshActions(); //����Actions������,��ʾ,ͼ���.        
    }

    /**
     * ����Actions������,��ʾ,ͼ���.
     * �ر���Դ�����ı�(�����Ըı�)ʱ,���øú����������������
     */
    @Override
	public void refreshActions() {
        ResourceManager rm = center.resourceManager;
        assert (rm != null);
        ActionResource resource = rm.getActionResource();
        AcceleratorKeyResource akResource = rm.getAcceleratorKeyResource();

        //���в˵�
        menuRunAction.putValue(Action.NAME,
                                  resource.getActionName(ActionResource.MENURUN));
        menuRunAction.putValue(Action.ACTION_COMMAND_KEY,
                                  resource.getActionKey(ActionResource.MENURUN));
        //������Ŀ
        putActionValue(runProjectAction,ActionResource.RUN_PROJECT,resource,akResource);

        //�����ļ�
        //putActionValue(runFileAction,resource.RUN_FILE,resource,akResource);

        //������Ŀ
        putActionValue(debugProjectAction,ActionResource.DEBUG_PROJECT,resource,akResource);

        //�����ļ�
        putActionValue(debugFileAction,ActionResource.DEBUG_FILE,resource,akResource);

        //��ʱִ�е�����Ŀ
        putActionValue(debugProjectTimeAction,ActionResource.DEBUG_PROJECT_TIME,resource,akResource);

        //��ʱִ�е����ļ�
        //putActionValue(debugFileTimeAction,resource.DEBUG_FILE_TIME,resource,akResource);

        //��ͣ
        putActionValue(pauseAction,ActionResource.PAUSE,resource,akResource);

        //ֹͣ
        putActionValue(stopDebugAction,ActionResource.STOP,resource,akResource);

        //��һ��
        putActionValue(stepOverAction,ActionResource.STEPOVER,resource,akResource);

        //��һ��
        putActionValue(stepIntoAction,ActionResource.STEPINTO,resource,akResource);
        
        //����
        putActionValue(stepOutAction,ActionResource.STEPOUT,resource,akResource);

        //��ָ��
        putActionValue(singleInstructionAction,ActionResource.SINGLE_INSTRUCTION,resource,akResource);

        //��������
        putActionValue(answerAction,ActionResource.RUN_ANSWER,resource,akResource);
    }

     public void setBPM(BreakpointManager bpm){
    	 this.bpm = bpm;
     }
     
     public void setTD(TextEditor td){
    	 this.td = td;
     }
    
    /**
     * ����Action����Ӧ
     * @param aCenter ConsoleCenter
     * @param noMeaning int
     */
    private RunActions(ConsoleCenter aCenter, int noMeaning) {
        super(aCenter);

        //���в˵�
        menuRunAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	
            }
        };

        //������Ŀ
        runProjectAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {
                //center.interpreterManager.runProject();
            }
        };

        //�����ļ�
//        runFileAction = new AbstractAction() {
//            public void actionPerformed(ActionEvent e) {
//                //center.interpreterManager.runFile(e);
//            }
//        };

//        //������Ŀ
//        debugProjectAction = new AbstractAction() {
//            public void actionPerformed(ActionEvent e) {            	
//                //center.interpreterManager.debugProject();
//                //TestClassLoader.test((JavaClassesManager)center.classesManager);
//            }
//        };
        //������Ŀ
        debugProjectAction = new DebugFileAction();

        //�����ļ�
        debugFileAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e){            	
                //center.interpreterManager.debugFile(e);
            	DebugFileAction debugFile = new DebugFileAction();
            	debugFile.actionPerformed(e);
            }
        };

        //��ʱִ�е�����Ŀ
        debugProjectTimeAction = DebugProjectTimeAction.getDebugProjectTimeAction( aCenter );

//      ��Ҫ��ȡȫ�ֿ������ģ�һ�����������
        debugFileTimeAction = DebugProjectTimeAction.getDebugProjectTimeAction( aCenter );

        //��ͣ
        pauseAction = new PauseDebugAction();

        //ֹͣ
        stopDebugAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	StopDebugAction stopDebug = new StopDebugAction();
            	stopDebug.actionPerformed(e);
            }
        };

        //��һ��
        stepOverAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	System.out.println("Step Over");
            	StepOverAction stepOver = new StepOverAction();
            	stepOver.actionPerformed(e);
            }
        };

        //��һ��
        stepIntoAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	System.out.println("Step Into");
            	StepIntoAction stepInto = new StepIntoAction();
            	stepInto.actionPerformed(e);
            }
        };
        
        //����
        stepOutAction = new StepOutAction();
        
        //��ָ��
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
