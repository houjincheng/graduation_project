package anyviewj.interfaces.ui.manager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import anyviewj.console.ConsoleCenter;
import anyviewj.debug.actions.DebugProjectTimeAction;
import anyviewj.debug.session.Session;
import anyviewj.interfaces.ui.JavaProject;
import anyviewj.interfaces.ui.Project;
import anyviewj.interfaces.ui.panel.ProjectManager;

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
public class JavaProjectManager extends ProjectManager {
    private ArrayList<JavaProject> prjlist = new ArrayList<JavaProject>();
    private JavaProject cur_prj=null;
    
    public List allFileList = new ArrayList();
    public List prjList = new ArrayList();
    public List ClosedProject = new ArrayList();
    
//    父类时抽象类
    public JavaProjectManager(ConsoleCenter aCenter) {
        super(aCenter);
    }
    
  //读取自定义公共类库
    @Override
	public byte[] loadCommonLibrary(String name){
    	try {
    		name = name.replace('.', '\\');
    		String fileName = cur_prj.getClassPath() + File.separator + name + ".class";
    		FileInputStream input = new FileInputStream(fileName);
    		
    		byte[] buffer = new byte[1024];
			int len = -1;
			ByteArrayOutputStream binaryBuf = new ByteArrayOutputStream();
			while ((len = input.read(buffer)) != -1) {
				binaryBuf.write(buffer, 0, len);
			}
			
			input.close();
			binaryBuf.close();
			
			return binaryBuf.toByteArray();
			
    	} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
    }

    /**
     * OpenProject
     *如果工程存在则打开工程，否则新建工程并添加
     * @param prjName String
     * @return boolean
     * @todo Implement this anyviewj.projects.ProjectManager method
     */
    @Override
	public Project OpenProject(String prjName) {
        JavaProject prj = null;
        for(int i = prjlist.size()-1;i>=0;i--){
            prj = prjlist.get(i);
            String name = prj.projectPath+prj.shortName;
            if(name.equalsIgnoreCase(prjName)){
                cur_prj = prj;
                return prj;
            }
        }
        File f = new File(prjName);
        String path = f.getAbsolutePath();
        path = path.substring(0,path.length()-f.getName().length());
        prj = new JavaProject(path,f.getName());
        prjlist.add(prj);
        cur_prj = prj;
        return prj;
    }
    
    
    @Override
	public void StoreProject(Project project) {
    	if (project == null) {
    		return;
    	}
    	Properties properties = new Properties();
    	JavaProject prj = (JavaProject)project;
    	try {
			FileInputStream fis = new FileInputStream(prj.projectPath + prj.shortName);
			properties.loadFromXML(fis);
			fis.close();
			
			properties.clear();
			
//			properties.setProperty(JavaProject.MAIN_FILE_NAME, prj.getMainFile());
			properties.setProperty(JavaProject.SOURCE_PATH, "src");			
			properties.setProperty(JavaProject.CLASSES_PATH, "classes");
			properties.setProperty(JavaProject.PRJ_FILE_INDEX, "" + prj.getPrjFileIndex());
			properties.setProperty(JavaProject.OPEN_FILE_NUM, "" +   prj.fileList.size());
			for (int i = 0; i < prj.fileList.size(); ++i) {
				properties.setProperty(JavaProject.OPEN_FILE_NAME + i, ""+prj.fileList.get(i));
			}
//			int num = center.mainFrame.rightPartPane.codePane
			FileOutputStream fos = new FileOutputStream(prj.projectPath + prj.shortName);
			properties.storeToXML(fos, null, "UTF-8");
			fos.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException ex) {
			ex.printStackTrace();
		}
    }
    /**
     * 如果工程存在则否会工程
     * @param prjName
     * @return
     */
    public Project findProject(String prjName) {
    	if (prjName == null) {
    		return null;
    	}
    	for (int i = 0; i < prjlist.size(); ++i) {
    		if (prjName.compareToIgnoreCase(prjlist.get(i).projectPath + prjlist.get(i).shortName) == 0) {
    			return prjlist.get(i);
    		}
    	}
    	return null;
    }
    
    //add my code
    @Override
	public Project CloseProject() {
    	// 返回的是关闭的项目
    	if (cur_prj == null)
    		return null;
//    	hou 2013年9月6日19:47:54
    	
//    	Session session = center.getCurrentSession();
//    	if(session!=null)
//    	{
//    	  if(session.isActive())
//    	  {
//    		  
//    	  }
//    	}

    	
    	notifyDebugFileTimeAction();
    	
    	JavaProject prj = prjlist.size() > 1 ? prjlist.get(0) : null;
    	JavaProject curPrj = cur_prj;
    	for (int i = prjlist.size() - 1; i >= 0; --i) {   		
    		if (cur_prj == prjlist.get(i)) {
    			cur_prj = prj;
    			StoreProject(curPrj);
    			prjlist.remove(i);
    			break;
    		}
    		prj = prjlist.get(i);
    	}
    	
    	this.setClosedProject(curPrj); 
    	
    	if(prjList.contains(curPrj))
			prjList.remove(curPrj);
    	return curPrj;
    }

    /**
     * DebugFileTimeAction定时调试采用单例模式设计，
     * 所以取到的实例与按钮上的是同一个实例。
     * 执行此操作的结果是：如果关闭的是正在定时调试的工程，则终止任务，
     * 若处于非活动状态，则不执行任何有效操作。
     */
    private void notifyDebugFileTimeAction()
    {
    	if ( cur_prj == null )
    	{
    		return;
    	}
    	else
    	{
    		DebugProjectTimeAction actionTmp =
    				DebugProjectTimeAction.getDebugProjectTimeAction();
    		if ( actionTmp.getCurrentProject() == cur_prj )
    		{
    			actionTmp.deactive();
    		}
    	}
    	
    }
    @Override
	public Project getCurProject(){
        return cur_prj;
    }

    public void setCurProject(Project prj) {
    	this.cur_prj = (JavaProject)prj;
    }

    public int getOpenPrjCount() {
    	return prjlist.size();
    }
    
    public void setClosedProject(Project prj){
    	ClosedProject.add(prj);
    }
    
    public Project getClosedProject(){
    	if(!ClosedProject.isEmpty())
    	return (Project) ClosedProject.get(ClosedProject.size()-1);
    	else
    	return null;
    }
    
    

	@Override
	/**
	 * 这个方法的移植可能存在缺陷。
	 */
	public Project openProject(String prjName, Session session) {
//		在Console中添加ProjectManager变量
		Project prj = ConsoleCenter.projectManager.OpenProject(prjName);
		ConsoleCenter.mainFrame.leftPartPane.projectPane.curProjectPane.openProject(prj);
//		在CodePane.java中添加了方法
		ConsoleCenter.mainFrame.rightPartPane.codePane.openProject(prj, session);
//		在Session中添加抽象方法initPath(prj, session)
//		初步测试这是个与MainFrame关联的BaseSession
		session.initPath(prj, session);
		return prj;
	}
	
    public void searchAllFile(String sPath, JavaProject jp){
    	File f = new File(sPath);
    	File[] fl = f.listFiles();
//    	System.out.println("Digui  @@@@@@2  !!!  : "+sPath);
    	if(fl==null)
    		return;
    	for(int i = 0;i<fl.length;i++)
    	{
    		File f2 = fl[i];
    		if(f2.getName().endsWith(".java"))
    		{

    			  String str = f2.getAbsolutePath();
                allFileList.add(str);
 //   			System.out.println("Digui  !!!  : "+f2.getName());
    		}
    		if(f2.isDirectory())
    			searchAllFile(f2.getAbsolutePath(),jp);
    	}
    }
    
    
    public void setprjList(Project prj){
    	allFileList.clear();
    	prjList.add(prj);
    	for(int i = 0;i<prjList.size();i++){
    		JavaProject jp = (JavaProject) ((Project)prjList.get(i));
    		searchAllFile(jp.getSourcePath(),jp);
//            for (int x = 0; x < jp.allList.size(); x++) {
//                String str = jp.getSourcePath() + File.separator +jp.allList.get(x);
//                System.out.println("AAAAAAAAAAAAa  "+str);
//                allFileList.add(str);
//                
//            }
    	}
        
    }
	
	
}
