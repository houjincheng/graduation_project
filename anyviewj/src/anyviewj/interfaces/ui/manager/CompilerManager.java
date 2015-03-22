package anyviewj.interfaces.ui.manager;

import anyviewj.console.ConsoleCenter;
import anyviewj.interfaces.ui.JavaProject;
import anyviewj.interfaces.ui.panel.CodeTabPane;
import anyviewj.interfaces.ui.panel.FilePane;
import anyviewj.interfaces.ui.panel.SourcePane;

import javax.tools.*;
import javax.tools.JavaFileObject.Kind;


import java.util.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

//import anyviewj.mainframes.frames.rightpart.codepart.source.SourcePane;
//import anyviewj.mainframes.frames.rightpart.codepart.CodeTabPane;
//import anyviewj.mainframes.frames.rightpart.codepart.FilePane;
//import anyviewj.projects.java.*;

/**
 * <p>Title: 编译管理器</p>
 *
 * <p>Description: 管理编译</p>
 *
 * <p>Copyright: Copyright (c) 2007 gdut 1627</p>
 *
 * <p>Company: gdut 1627</p>
 *
 * @author cyf
 * @version 1.0
 */
public class CompilerManager {
    //全局控制中心引用
    private ConsoleCenter center;

    public CompilerManager(ConsoleCenter aCenter) {
        this.center = aCenter;
    }

    public void compiler(String encoding, List<File> sourceFileList, final List<File> classFileList, String jars) throws IOException {
    	JavaProject prj = (JavaProject) (ConsoleCenter.projectManager.getCurProject());
    	if (prj == null) {
    		return;
    	}
    	//需要将tools.jar从安装路径..\java\jdk\lib\中copy到..\java\jre\lib\中，否则其返回null
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        
        DiagnosticCollector diagnostics = new DiagnosticCollector();
        
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(sourceFileList);
        
        
//        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(sourceFileList);
        List<JavaFileObject> classUnits = new ArrayList<JavaFileObject>();
        
        for (int i = 0, count = sourceFileList.size(); i < count; ++i) {
        	 String str = sourceFileList.get(i).getAbsolutePath();
           
        	 final String path = str.substring(0, str.lastIndexOf('\\'));
        	 final String name = sourceFileList.get(i).getName();
        	JavaFileObject compilation = fileManager.getJavaFileForInput(new JavaFileManager.Location(){
        										@Override
        										public String getName() {
        											return path;
        										}
        										@Override
        										public boolean isOutputLocation() {
        											return false;
        										}
        							}, name, JavaFileObject.Kind.SOURCE);
    						
        	 myJavaFileManager myManager = new myJavaFileManager(path);
        	 JavaFileObject compilation1 = fileManager.getJavaFileForInput(StandardLocation.locationFor(path), str, JavaFileObject.Kind.SOURCE);
        	 classUnits.add(compilation1);
        }
        
//        fileManager.getJavaFileForInput(new TestLocation(classFileList.get(0).getPath()), classFileList.get(0).getName(), JavaFileObject.Kind.CLASS);
      
        for (int i = 0, count = classFileList.size(); i < count; ++i) {
        	String str = classFileList.get(i).getAbsolutePath();
 //       	System.out.println("+++++++++++ 2 :  "+str);
        	final String path = str.substring(0, str.lastIndexOf('\\'));
        	final String name = classFileList.get(i).getName();
        	JavaFileObject compilation = fileManager.getJavaFileForInput(new JavaFileManager.Location(){
        										@Override
        										public String getName() {
        											return path;
        										}
        										@Override
        										public boolean isOutputLocation() {
        											return false;
        										}
        							}, name, JavaFileObject.Kind.CLASS);
            
        	JavaFileManager.Location stdLocation = StandardLocation.locationFor(str);
        	
//        	System.out.println("+++++++++++ !!! :  "+stdLocation.getName()+"   "+name+"   "+JavaFileObject.Kind.CLASS+"   "+fileManager);
        	
        	//JavaFileObject compilation1 = fileManager.getJavaFileForInput(stdLocation, str, JavaFileObject.Kind.CLASS);
//        	JavaFileObject compilation1 = fileManager.getJavaFileForOutput(stdLocation, name, JavaFileObject.Kind.CLASS,null);
//       	classUnits.add(compilation1);
            
        	
        	
        	
        	
        }
        
        List< Iterable<? extends JavaFileObject> > compilerUnits = new ArrayList< Iterable<? extends JavaFileObject> >();
        compilerUnits.add(compilationUnits);
        compilerUnits.add(classUnits);
        
        String clapa = "E:\\Test\\Library\\classes";//jars
        Iterable options = Arrays.asList("-g", "-encoding", encoding, "-classpath",jars, "-d", prj.getClassPath(), "-sourcepath", prj.getSourcePath());
           
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, options, null, compilationUnits);
        
        boolean b = false;
        try {
        	b = task.call();
        }catch(Exception ep) {
        	ep.printStackTrace();
        }
        
        try {
            fileManager.close();
        } catch (IOException ex) {
        }
        if (b) {
//            copyClassesToPackage(prj.getClassPath(), sourceFileList);
            ConsoleCenter.output("\nCompile success!\n");
        } else {
            List list = diagnostics.getDiagnostics();
            for (int j=0;j<list.size();j++) {
                Diagnostic diagnostic = (Diagnostic)list.get(j);
                
                String str = null;
                
                
                str  = "\nCode: " + diagnostic.getCode() + "\n";
                str +=  "Kind: " + diagnostic.getKind() + "\n";
                str +=  "Position: " + diagnostic.getPosition() + "\n";
                str +=  "Start Position: " + diagnostic.getStartPosition() + "\n";
                str +=  "End Position: " + diagnostic.getEndPosition() + "\n";
                str +=  "Source: " + diagnostic.getSource()+ "\n";
                str +=  "Message: " + diagnostic.getMessage(null) + "\n";
                ConsoleCenter.output(str);
                
                System.out.printf(

                        "Code: %s%n" +

                        "Kind: %s%n" +

                        "Position: %s%n" +

                        "Start Position: %s%n" +

                        "End Position: %s%n" +

                        "Source: %s%n" +

                        "Message: %s%n",

                        diagnostic.getCode(), diagnostic.getKind(),

                        diagnostic.getPosition(), diagnostic.getStartPosition(),

                        diagnostic.getEndPosition(), diagnostic.getSource(),

                        diagnostic.getMessage(null));
            }

        }
    }
    
    
    private String getJarFiles(String jarPath) {
    	File file = new File(jarPath);
    	String jars = "";
    	List<File> jarFilesList = new ArrayList<File>();
    	getFiles(file, ".jar", jarFilesList);
    	
    	
    	for (int i = 0, count = jarFilesList.size(); i < count; ++i) {
    		if (jarFilesList.get(i).isFile()) {
    			jars += jarFilesList.get(i).getPath() + ";";
    		}
    	}
    	
    	return jars;
    }
    
    private void getFiles(File file, final String prefix, List<File> FileList) {
    	if (file.exists() && FileList != null) {
    		if (file.isDirectory()) {
    			File[] childrenFiles = file.listFiles(new FileFilter() {
					
					@Override
					public boolean accept(File arg) {
						// TODO Auto-generated method stub
						if (arg.isDirectory()) {
							return true;
						}
						else {
							String name = arg.getName();
							return name.endsWith(prefix)? true : false;
						}
					}
				});
    			
    			for (File childFile : childrenFiles) {
    				getFiles(childFile, prefix, FileList);
    			}
    		}
    		else {
    			FileList.add(file);
    		}
    	}
    }
    
    private void getSourceFiles(File sourceFile, List<File> sourceFileList){
    	getFiles(sourceFile, ".java", sourceFileList);
//    	File classFile = new File(sourceFile + "\\Classes");
//    	getClassFiles(classFile, sourceFileList);
    }
    
    private void getClassFiles(File classFile, List<File> classFileList) {
    	getFiles(classFile, ".class", classFileList);
    }
    
    public void allDoCompile() throws IOException {
    	JavaProject prj = (JavaProject) (ConsoleCenter.projectManager.getCurProject());
    	if (prj == null) {
    		return ;
    	}
    	File sourceFile = new File(prj.getSourcePath());
    	List<File> sourceFileList = new ArrayList<File>();
    	getSourceFiles(sourceFile, sourceFileList);
    	if (sourceFileList.size() == 0) {
    		System.out.println(prj.getSourcePath() + "目录下查找不到java文件" + "\n");
    		return;
    	}
    	String jarPath = prj.projectPath + File.separator + "Library";
    	
    	List<File> classFileList = new ArrayList<File>();    	
    	
    	File classFile = new File(jarPath + "\\classes");
    	getClassFiles(classFile, classFileList);
    	
    	compiler("UTF-8", sourceFileList, classFileList, getJarFiles(jarPath + "\\jars"));
    }
    public void doCompile() throws IOException {
    	CodeTabPane ftp = ConsoleCenter.mainFrame.rightPartPane.codePane.fileTabPane;
        int i = ftp.getSelectedIndex();
        SourcePane sp = ((FilePane) (ftp.getTabComponentAt(i))).source;
        String szSrcPath = sp.getFilePath();
        String prjName = sp.getName();
        JavaProject prj = (JavaProject)((JavaProjectManager)ConsoleCenter.projectManager).findProject(prjName);
        if (prj == null) {
        	return;
        }
        String jarPath = prj.projectPath + File.separator + "Library";
//        File[] files = new File[1];
        File file = new File(szSrcPath);
        List<File> files = new ArrayList<File>();
        files.add(file);
        
        List<File> classFileList = new ArrayList<File>();    	
    	
    	File classFile = new File(jarPath + "\\classes");
    	getClassFiles(classFile, classFileList);
        
        compiler("UTF-8", files, classFileList, getJarFiles(jarPath + "\\jars"));        
    }
    
    private class myJavaFileManager implements JavaFileManager.Location {
    	private String name;
    	public myJavaFileManager(String path) {
    		this.name = path;
		}
    	
    	@Override
    	public String getName() {
    		return this.name;
    	}
    	@Override
    	public boolean isOutputLocation() {
    		return false;
    	}
    }

}

