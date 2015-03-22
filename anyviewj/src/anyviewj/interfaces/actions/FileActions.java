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

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;

import anyviewj.console.ConsoleCenter;
import anyviewj.debug.session.Session;
import anyviewj.debug.session.SessionFrameMapper;
import anyviewj.interfaces.resource.AcceleratorKeyResource;
import anyviewj.interfaces.resource.ActionResource;
import anyviewj.interfaces.resource.ResourceManager;
import anyviewj.interfaces.ui.Project;
import anyviewj.interfaces.ui.manager.JavaProjectManager;
import anyviewj.interfaces.ui.panel.CodeTabPane;
import anyviewj.interfaces.ui.panel.FilePane;
import anyviewj.interfaces.ui.panel.SourcePane;
import anyviewj.interfaces.ui.panel.newHomeworkPanel;
import anyviewj.interfaces.ui.panel.newProjectPanel;
import anyviewj.net.client.actions.ChangePasswordAction;
import anyviewj.net.client.actions.ReloginAction;
import anyviewj.net.table.OpenTableAction;


public class FileActions extends CommandAction{
    public final Action menuFileAction; //文件菜单

    
    public final Action newAction; //新建项目,类,接口,文件等
    public final Action newProjectAction; //新建项目
    public final Action newClassAction; //新建类文件
    public final Action openProjectAction; //打开项目
    public final Action openFileAction; //打开文件
    public final Action closeProjectAction; //关闭项目
    public final Action closeFileAction; //关闭文件
    public final Action saveProjectAction; //保存项目
    public final Action saveFileAction; //保存文件
    public final Action saveAllAction; //保存全部文件
    public final Action reloginAction; //重新登录
    public final Action uploadAction; //上传
    
    public final Action openTableAction;
    public final Action newHomeworkAction;
    
    public final Action changePasswordAction; //修改密码
    public final Action exitAction; //退出 无图标

    /**
     * 构造器
     * @param aCenter ConsoleCenter
     */
    public FileActions(ConsoleCenter aCenter){
        this(aCenter,0);//使用这种构造方式,是为了让程序代码清晰
        refreshActions();//设置Actions的名称,提示,图标等.
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

        //文件菜单
        menuFileAction.putValue(Action.NAME, resource.getActionName(ActionResource.MENUFILE));
        menuFileAction.putValue(Action.ACTION_COMMAND_KEY,
                            resource.getActionKey(ActionResource.MENUFILE));


        
        //新建项目,类,接口,文件等
        putActionValue(newAction,ActionResource.NEW,resource,akResource);

        //新建项目
        putActionValue(newProjectAction,ActionResource.NEW_PROJECT,resource,akResource);

        //新建类文件
        putActionValue(newClassAction,ActionResource.NEW_CLASS,resource,akResource);

        //打开项目
        putActionValue(openProjectAction,ActionResource.OPEN_PROJECT,resource,akResource);

        //打开文件
        putActionValue(openFileAction,ActionResource.OPEN_FILE,resource,akResource);

        //关闭项目
        putActionValue(closeProjectAction,ActionResource.CLOSE_PROJECT,resource,akResource);

        //关闭文件
        putActionValue(closeFileAction,ActionResource.CLOSE_FILE,resource,akResource);

        //保存项目
        putActionValue(saveProjectAction,ActionResource.SAVE_PROJECT,resource,akResource);

        //保存文件
        putActionValue(saveFileAction,ActionResource.SAVE_FILE,resource,akResource);

        //保存全部文件
        putActionValue(saveAllAction,ActionResource.SAVE_ALL,resource,akResource);
        
        //重新登录
        putActionValue(reloginAction,ActionResource.RELOGIN,resource,akResource);

        //修改密码
        putActionValue(changePasswordAction,ActionResource.CHANGE_PASSWORD,resource,akResource);
        
        //打开题目表
        putActionValue(openTableAction,ActionResource.OPEN_TABLE,resource,akResource);
        
        //上传
        putActionValue(uploadAction,ActionResource.UPLOAD,resource,akResource);
        
        //新建作业工程
        putActionValue(newHomeworkAction,ActionResource.NEW_HOMEWORK,resource,akResource);
        
        //退出
        putActionValue(exitAction,ActionResource.EXIT,resource,akResource);

    }

    /**
     * 处理Action的响应
     * @param aCenter ConsoleCenter
     * @param noMeaning int
     */
    private FileActions(ConsoleCenter aCenter,int noMeaning) {
        super(aCenter);

        //文件菜单
        menuFileAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };
        
        reloginAction = new ReloginAction();
        changePasswordAction = new ChangePasswordAction();
        
        openTableAction = new OpenTableAction();
        
        uploadAction = new UploadAction(aCenter);
        
        newHomeworkAction = new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				newHomeworkPanel hw = new newHomeworkPanel(center);
			}
        	
        };
        
        //新建项目,类,接口,文件等
        newAction = new AbstractAction() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 7197655832252554184L;

			@Override
			public void actionPerformed(ActionEvent e) {

            }
        };
        
        

        //新建项目
        newProjectAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	
            	newProjectPanel newPrjPanel = new newProjectPanel(center);
            	newPrjPanel.setModal(true);            	
            	newPrjPanel.setVisible(true);    
            }
        };

        //新建类文件
        newClassAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //打开项目
        openProjectAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	Session session = SessionFrameMapper.getSessionForFrame(SessionFrameMapper.getOwningFrame(e));
            	ConsoleCenter.setCurrentSession(session);
            	FileDialog fd = new FileDialog(ConsoleCenter.mainFrame,"打开Java工程",FileDialog.LOAD);
                fd.setFile("*.ajp");
                fd.setVisible(true);
                String filename="";
                if(fd.getDirectory()!=null && fd.getFile()!=null){
                    filename = fd.getDirectory() + fd.getFile();
                    Project prj = ((JavaProjectManager)ConsoleCenter.projectManager).findProject(filename);
                    if (prj != null) {
                    	ConsoleCenter.mainFrame.leftPartPane.projectPane.curProjectPane.setCurProject(prj);
                    	((JavaProjectManager)ConsoleCenter.projectManager).setCurProject(prj);
                    }
                    else {
                    	prj = ConsoleCenter.projectManager.openProject(filename, session);
                    }
                    ((JavaProjectManager)ConsoleCenter.projectManager).setprjList(prj);
                }
                fd.dispose();
            }
        };

        //打开文件
        openFileAction = new OpenFileAction(center);

        //关闭项目
        closeProjectAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	Project prj = ConsoleCenter.projectManager.CloseProject();
            	if (prj == null)
            		return;
            	Project tPrj = ConsoleCenter.projectManager.getCurProject();
            	ConsoleCenter.mainFrame.leftPartPane.projectPane.curProjectPane.updateProject(tPrj);
            	ConsoleCenter.mainFrame.rightPartPane.codePane.closeProject(prj);
            }
        };

        //关闭文件
        closeFileAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	int index = ConsoleCenter.mainFrame.rightPartPane.codePane.fileTabPane.getSelectedIndex();
            	ConsoleCenter.mainFrame.rightPartPane.codePane.closeTab(index);
            }
        };

        //保存项目
        saveProjectAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	ConsoleCenter.projectManager.StoreProject(ConsoleCenter.projectManager.getCurProject());
            }
        };

        //保存文件
        saveFileAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {
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
        };

        //保存全部文件
        saveAllAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {
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
            }
        };

        //退出
        exitAction = new ExitAction();
    }

}
