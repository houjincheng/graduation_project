package anyviewj.console;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: gdut 1627</p>
 *
 * @author cyf
 * @version 1.0
 */

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import anyviewj.console.listeners.IAnyviewPublisher;
import anyviewj.console.listeners.IFindClassFileListener;
import anyviewj.debug.manager.BreakpointManager;
import anyviewj.debug.session.Session;
import anyviewj.debug.session.event.SessionListener;
import anyviewj.interfaces.actions.CommandManager;
import anyviewj.interfaces.resource.ResourceManager;
//import anyviewj.compiler.CompilerManager;
//import anyviewj.interpret.java.*;
//import anyviewj.classinfo.ClassesManager;
//import anyviewj.projects.ProjectManager;
//import anyviewj.resources.ResourceManager;
//import anyviewj.classinfo.javaclass.JavaClassesManager;
//import anyviewj.console.publisher.*;
//import anyviewj.projects.java.*;
import anyviewj.interfaces.ui.manager.CompilerManager;
import anyviewj.interfaces.ui.manager.JavaProjectManager;
import anyviewj.interfaces.ui.panel.MainFrame;
import anyviewj.interfaces.ui.panel.ProjectManager;

public class ConsoleCenter {
    public final ResourceManager resourceManager;
    public static CommandManager commandManager;
    
    //public final ClassesManager classesManager;
    public static  ProjectManager projectManager;
    public final CompilerManager compilerManager;
    //public final InterpreterManager interpreterManager;
    public static MainFrame mainFrame;
    //�������е�������¼������� VMEventListner
    private static List viewListenersList =new LinkedList();
    //�������еĵ��ԻỰ��Ŀǰ��ʱ����
    private static List SessionsList =new LinkedList();
    //���浱ǰ�Ự 
    private static Session currentSession;
    //�������еĶϵ��������Ŀǰ��ʱ����
    private static List BreakpointManagersList =new LinkedList();
    //���浱ǰ�ϵ������ 
    private static BreakpointManager currentBKManager = new BreakpointManager();
    
    private static Map ProSessionMap = new HashMap();
    
    public ConsoleCenter(MainFrame aMainFrame) {
        //
        this.addFindClassFileListener(new InnerFindClassFileListener());
        //
        ConsoleCenter.mainFrame = aMainFrame;
        resourceManager = new ResourceManager(this);
        commandManager = new CommandManager(this);
        
        projectManager = new JavaProjectManager(this);
        //classesManager = new JavaClassesManager(this);
        //projectManager = new JavaProjectManager(this);
        compilerManager = new CompilerManager(this);
        //interpreterManager = new JavaInterpreterManager(this);
        //interpreterManager = new InterpreterManager(this);
        addViewListeners(currentBKManager);
        currentBKManager.opened();       
    }

    //�������ļ�:�������ͼ�����--����û�н����߳�ͬ��,ʹ��ʱ��Ҫע��
    private ArrayList findClassFileListeners = new ArrayList(5);

    public void addFindClassFilePublisher(IAnyviewPublisher publisher) {
        publisher.setListenersList(this.findClassFileListeners);
    }

    public void addFindClassFileListener(IFindClassFileListener listener) {
        findClassFileListeners.remove(listener);
        findClassFileListeners.add(listener);
    }

    private class InnerFindClassFileListener implements IFindClassFileListener {
        @Override
		public byte[] findClassFile(String fileName) {
            byte[] buf = null;
            File file = new File("..\\AnyviewJTest\\classes\\"+fileName.replace('.','\\')+".class");
            try {
                FileInputStream fin = new FileInputStream(file);
                int len = (int) file.length();
                buf = new byte[len];
                try {
                    fin.read(buf, 0, len);
                    fin.close();
                } catch (IOException ex1) {
                    System.out.println("IO Error!");
                }
            } catch (FileNotFoundException ex) {
            }
            return buf;
        }

        @Override
		public String findLibraryPath(String libname) {
            return "E:\\Anyview Java\\AnyviewJPrj\\";
        }
    }
    
    public static void addViewListeners(SessionListener sl){    	
    	viewListenersList.add(sl);
    }
    
    public static void setViewSession(Session session){ 
    	for(int i =0 ; i < viewListenersList.size();i++){
    		SessionListener sl = (SessionListener)viewListenersList.get(i);
    		sl.setSession(session);
    	}
    }
    
    public static Session getCurrentSession(){
    	return currentSession;
    }
    
    public static void setCurrentSession(Session session){
    	currentSession = session;
    }
    
    public static BreakpointManager getcurrentBKManager(){
    	return currentBKManager;
    }    
    
    public static MainFrame getMainFrame(){
    	return mainFrame;
    }
    
    public static void putProSessionMap(String pro, Session session){
    	ProSessionMap.put(pro,session);
    }
    
    public static Session getProSessionMap(String pro){
    	return (Session)ProSessionMap.get(pro);
    }   
    /**
     * ��Ϊһ����̬������
     * ����Ա������ϵ��κ���������ã�������ַ�����Ϣ
     * ȫ�ֿ������Ľ�������Щ�����Ϣ����ķ�ʽ��λ��
     * @param str Ҫ�������Ϣ
     */
    public static void output( String str )
    {
    	OutPut.output( mainFrame, str);
    }

}
class OutPut {	
	
	
	public static void output(MainFrame mf, String str)
	{
		mf.rightPartPane.groupPane.ouputPane.output(str);
		
	}

}