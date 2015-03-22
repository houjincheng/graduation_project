package anyviewj.interfaces.actions;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileFilter;

import anyviewj.console.ConsoleCenter;
import anyviewj.debug.session.Session;
import anyviewj.interfaces.ui.JavaProject;
import anyviewj.interfaces.ui.manager.JavaProjectManager;

public class OpenFileAction extends JSwatAction {
	private static final long serialVersionUID = 6703480522256100283L;

	private final ConsoleCenter center;
	
	public OpenFileAction(final ConsoleCenter center) {
        super("openFile");
        this.center = center;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
        FileDialog fd = new FileDialog(ConsoleCenter.mainFrame,"打开Java文件",FileDialog.LOAD);
        fd.setFile("*.java");
        fd.setVisible(true);
        String filename;
        if(fd.getDirectory()!=null && fd.getFile()!=null){
            filename = fd.getDirectory() + fd.getFile();
            String dir = filename.substring(0, filename.indexOf("\\src"));
            int index = dir.lastIndexOf('\\');
            String prjName = null;
            if (index >= 0) {
            	prjName = dir.substring(index + 1, dir.length()) + ".ajp";
            }
            File file = new File(dir);
            
            if (file.isDirectory()) {
            	File[] files = file.listFiles(new FileFilter() {
					
					@Override
					public boolean accept(File arg) {
						// TODO Auto-generated method stub
						if (arg.isDirectory()) {
							return false;
						}
						else {
							return arg.getName().endsWith(".ajp")? true : false;
						}
					}
				});
            	for (int i = 0; i < files.length; ++i) {
            		if (files[0].getName().compareTo(prjName) == 0) {
            			prjName = files[0].getAbsolutePath();
            			break;
            		}
            	}
            }
            Session session = getSession(e);
            ConsoleCenter.setCurrentSession(session);
            JavaProject jPrj = (JavaProject)((JavaProjectManager)ConsoleCenter.projectManager).findProject(prjName);
            ConsoleCenter.mainFrame.rightPartPane.codePane.newFilePane(jPrj,filename,true, session);
        }
        fd.dispose();
    }

}
