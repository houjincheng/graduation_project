package anyviewj.interfaces.ui.drawer;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import anyviewj.console.ConsoleCenter;
import anyviewj.interfaces.ui.manager.JavaProjectManager;
import anyviewj.interfaces.ui.panel.CodePane;

public class FindMainMethod {
 
	public List fileList = new ArrayList();
    public CodePane codePane;
    
	public FindMainMethod(JavaProjectManager jpm){
		fileList.clear();
    	if(ConsoleCenter.getCurrentSession()==null)
    	{
    		return;
    	}
//    	if(((BasicSession)ConsoleCenter.getCurrentSession()).getCurrentSourcePane()==null)
//    		return;
//    	SourcePane sp = ((BasicSession)ConsoleCenter.getCurrentSession()).getCurrentSourcePane();
    	for(int i = 0;i<jpm.allFileList.size();i++)
    	{
    	 File file = new File((String) jpm.allFileList.get(i));
    	 FileInputStream is = null;
    	 if(!file.exists())
    	 {
    		 System.out.println("File not exist!!!");
    		 continue;
    	 }

		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}//file.source.sourceSrc.getInputStream();
       
        
    	 InputStreamReader isr = new InputStreamReader(is);
    	 BufferedReader br = new BufferedReader(isr);
         try {
        	while(br.read()!=-1){
        		String s = br.readLine();
			if(s!=null)
        	if(s.contains("main")&&s.contains("static")&&s.contains("void"))
        	{
        		String str = file.getPath().substring(file.getPath().indexOf("src\\")+4,file.getPath().length());
        		str = str.replace('\\', '/');
        		if(!fileList.contains(str))
        		fileList.add(str);
        		System.out.println(" in FindMainMethod   "+str+"  "+file.getName());
        		break;
        	}
        	
         }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
    	}

    }
	
	public List getFileList(){
		return this.fileList;
	}
	
	
}
