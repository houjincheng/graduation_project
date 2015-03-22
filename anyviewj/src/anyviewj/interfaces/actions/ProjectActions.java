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
import javax.swing.AbstractAction;
import javax.swing.Action;
import anyviewj.console.ConsoleCenter;
import anyviewj.debug.session.Session;

import java.awt.event.ActionEvent;
import anyviewj.interfaces.resource.ActionResource;
import anyviewj.interfaces.resource.AcceleratorKeyResource;
import anyviewj.interfaces.resource.ResourceManager;
import anyviewj.interfaces.ui.panel.CodeTabPane;
import anyviewj.interfaces.ui.panel.FilePane;
import anyviewj.interfaces.ui.panel.SourcePane;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;



public class ProjectActions extends CommandAction{
    public final Action menuProjectAction; //项目菜单
    public final Action compileProjectAction; //编译项目
    public final Action compileFileAction; //编译文件
    public final Action buildAction; //建立项目
    public final Action projectPropertiesAction; //项目属性

    public ProjectActions(ConsoleCenter aCenter) {
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


        //项目菜单
        menuProjectAction.putValue(Action.NAME,resource.getActionName(ActionResource.MENUPROJECT));
        menuProjectAction.putValue(Action.ACTION_COMMAND_KEY,
                                      resource.
                                      getActionKey(ActionResource.MENUPROJECT));

        //编译项目
        putActionValue(compileProjectAction,ActionResource.COMPILE_PROJECT,resource,akResource);

        //编译文件
        putActionValue(compileFileAction,ActionResource.COMPILE_FILE,resource,akResource);

        //建立项目
        putActionValue(buildAction,ActionResource.BUILD,resource,akResource);

        //项目属性
        putActionValue(projectPropertiesAction,ActionResource.PROJECT_PROPERTIES,resource,akResource);

    }

    /**
     * 处理Action的响应
     * @param aCenter ConsoleCenter
     * @param noMeaning int
     */
    private ProjectActions(ConsoleCenter aCenter, int noMeaning) {
        super(aCenter);

        //项目菜单
        menuProjectAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //编译项目
        compileProjectAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	
            	
            	Session ss = ConsoleCenter.getCurrentSession();
            	if(ss!=null)
            	if(ss.isActive())
            	{
            		
            	  ss.deactivate(false,this);	
            		
            	}
            	
            	 String szSrcPath,szClassName;
                 CodeTabPane ftp = ConsoleCenter.mainFrame.rightPartPane.codePane.fileTabPane;
                 int count = ftp.getTabCount();
                 int i;
                 for(i=0; i<count; i++){
                     //int i = ftp.getSelectedIndex();
                     SourcePane sp = ((FilePane)(ftp.getTabComponentAt(i))).source;
                     szSrcPath = sp.getFilePath();
                     File file= new File(szSrcPath);
                     FileOutputStream stream = null;
                     try {
                         stream = new FileOutputStream(file);
                         szSrcPath = sp.editorPanel.editor.getText();
                         try {
                             stream.write(szSrcPath.getBytes());
                             stream.close();
                         } catch (IOException ex1) {
                         }
                     } catch (FileNotFoundException ex) {
                     }
                 }
            	
                try {
                    center.compilerManager.allDoCompile();
                } catch (IOException ex) {
                    Logger.getLogger(ProjectActions.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        //编译文件
        compileFileAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {
                
            	{
                    String szSrcPath,szClassName;
                    CodeTabPane ftp = ConsoleCenter.mainFrame.rightPartPane.codePane.fileTabPane;
                    int i = ftp.getSelectedIndex();
                    SourcePane sp = ((FilePane)(ftp.getTabComponentAt(i))).source;
                    szSrcPath = sp.getFilePath();
                    File file= new File(szSrcPath);
                    FileOutputStream stream = null;
                    try {
                        stream = new FileOutputStream(file);
                        szSrcPath = sp.editorPanel.editor.getText();
                        try {
                            stream.write(szSrcPath.getBytes());
                            stream.close();
                        } catch (IOException ex1) {
                        }
                    } catch (FileNotFoundException ex) {
                    }
                }
            	
            	try {
                    center.compilerManager.doCompile();
                } catch (IOException ex) {
                    Logger.getLogger(ProjectActions.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        //建立项目
        buildAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //项目属性
        projectPropertiesAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

    }

}
