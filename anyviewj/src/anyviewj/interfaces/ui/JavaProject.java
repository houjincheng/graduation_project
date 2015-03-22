package anyviewj.interfaces.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
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
public class JavaProject extends Project{
    private String mainFile;//项目主文件. 使用class名称.如 anyviewj.Application
    private int curFileIndex = -1;
    private int prjFileIndex = -1;
    public final ArrayList fileList = new ArrayList();//打开的文件
    public final Properties properties = new Properties();
    private String sourcePath;
    private String classesPath;

    //
    public static final String SOURCE_PATH = "srcpath";
    public static final String CLASSES_PATH = "clspath";
//    public static final String MAIN_FILE_NAME = "mainfile";
    public static final String OPEN_FILE_NUM = "openfilenum";
    public static final String OPEN_FILE_NAME = "fn";
    public static final String PRJ_FILE_INDEX = "prjfileindex";
    
    public List allList = new ArrayList();
    public int temp = 0;
    public int countAll = 0;

    public JavaProject(String prjPath,String shortName) {
        super(prjPath,shortName);
        try {
            properties.loadFromXML(new FileInputStream(prjPath+shortName));
            loadProperties();
        } catch (IOException ex) {
        }
    }

    public void searchAllFile(String sPath){
    	File f = new File(sPath);
    	File[] fl = f.listFiles();
    	System.out.println("Digui  @@@@@@2  !!!  : "+sPath);
    	for(int i = 0;i<fl.length;i++)
    	{
    		File f2 = fl[i];
    		if(f2.getName().endsWith(".java"))
    		{
    			System.out.println("Digui  !!!  : "+f2.getName()+" "+(countAll++));
    		    countAll++;
    		}
    		if(f2.isDirectory())
    			searchAllFile(f2.getAbsolutePath());
    	}
    }
    
    
    private void loadProperties(){
    	
    	System.out.println( "\n" + this.properties );
        sourcePath = properties.getProperty(SOURCE_PATH,"src");
        classesPath = properties.getProperty(CLASSES_PATH,"classes");

//        mainFile = properties.getProperty(MAIN_FILE_NAME);
        String curNum = properties.getProperty(PRJ_FILE_INDEX, "-1");
        this.prjFileIndex = Integer.valueOf(curNum, 10);
                
//       File f = new File(this.sourcePath);
//       File[] fl = f.listFiles();
//       for(int i = 0;i<fl.length;i++)
//       {
//    	   File f2 = fl[i];
//    	   if(f2.isDirectory())
//    	   {
//    		   f2.
//    	   }
//       }
        
        String str = properties.getProperty(OPEN_FILE_NUM,"-1");
        int num = Integer.valueOf(str,10);
        for(int i=0;i<num;i++){
            str = properties.getProperty(OPEN_FILE_NAME+i);
            fileList.add(str);
        }
             
        if(this.prjFileIndex>num)
        	temp = this.prjFileIndex;
        else
        	temp = num-1;
        for(int i = 0;i<=temp;i++)
        {
        	allList.add(properties.getProperty(OPEN_FILE_NAME+i));
        	System.out.println(i+"  "+properties.getProperty(OPEN_FILE_NAME+i));
        }
        
       // this.searchAllFile(getSourcePath());
        
       
    }

    
    
    
    public String getSourcePath(){
        return this.projectPath+this.sourcePath;
    }

    public String getClassPath(){
        return this.projectPath+this.classesPath;
    }

    public int getCurFileIndex(){
        return this.curFileIndex;
    }

    public void setCurFileIndex(int newIndex){
        if(newIndex >=0 && newIndex < fileList.size()){
            this.curFileIndex = newIndex;
        }
    }

    public int getPrjFileIndex(){
        return this.prjFileIndex;
    }

    public String getMainFile() {
        return mainFile;
    }

    public void setPrjFileIndex(int newIndex){
//        if(newIndex >=0 && newIndex < fileList.size())
            this.prjFileIndex = newIndex;
    }

    public void setMainFile(String mainFile) {
        this.mainFile = mainFile;
    }

    /**
     * 读取用户类数据
     * @param name String
     * @return byte[]
     */
    @Override
	public byte[] loadUserClass(String name){
        return null;
    }

//    public static void main(String[] args){
//        Properties properties = new Properties();
//
//        properties.setProperty(SOURCE_PATH,"src");
//        properties.setProperty(CLASSES_PATH,"classes");
//        properties.setProperty(MAIN_FILE_NAME,"test");
//        properties.setProperty(OPEN_FILE_NUM,"1");
//        for(int i=0;i<1;i++){
//            properties.setProperty(OPEN_FILE_NAME+i,"test.java");
//        }
//        try {
//            properties.storeToXML(new FileOutputStream("E:\\Test\\test.ajp"), null);
//        } catch (IOException ex) {
//        }
//    }
}
