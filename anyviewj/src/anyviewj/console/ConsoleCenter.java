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
    //保存所有的虚拟机事件监听器 VMEventListner
    private static List viewListenersList =new LinkedList();
    //保存所有的调试会话，目前暂时不用
    private static List SessionsList =new LinkedList();
    //保存当前会话 
    private static Session currentSession;
    //保存所有的断点管理器，目前暂时不用
    private static List BreakpointManagersList =new LinkedList();
    //保存当前断点管理器 
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

    //查找类文件:发布器和监听器--这里没有进行线程同步,使用时需要注意
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
     * 作为一个静态方法。
     * 其可以被界面上的任何组件所引用，以输出字符串信息
     * 全局控制中心将控制这些输出信息输出的方式、位置
     * @param str 要输出的信息
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