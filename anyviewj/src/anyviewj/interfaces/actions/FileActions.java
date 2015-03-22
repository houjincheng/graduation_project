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
    public final Action menuFileAction; //�ļ��˵�

    
    public final Action newAction; //�½���Ŀ,��,�ӿ�,�ļ���
    public final Action newProjectAction; //�½���Ŀ
    public final Action newClassAction; //�½����ļ�
    public final Action openProjectAction; //����Ŀ
    public final Action openFileAction; //���ļ�
    public final Action closeProjectAction; //�ر���Ŀ
    public final Action closeFileAction; //�ر��ļ�
    public final Action saveProjectAction; //������Ŀ
    public final Action saveFileAction; //�����ļ�
    public final Action saveAllAction; //����ȫ���ļ�
    public final Action reloginAction; //���µ�¼
    public final Action uploadAction; //�ϴ�
    
    public final Action openTableAction;
    public final Action newHomeworkAction;
    
    public final Action changePasswordAction; //�޸�����
    public final Action exitAction; //�˳� ��ͼ��

    /**
     * ������
     * @param aCenter ConsoleCenter
     */
    public FileActions(ConsoleCenter aCenter){
        this(aCenter,0);//ʹ�����ֹ��췽ʽ,��Ϊ���ó����������
        refreshActions();//����Actions������,��ʾ,ͼ���.
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

        //�ļ��˵�
        menuFileAction.putValue(Action.NAME, resource.getActionName(ActionResource.MENUFILE));
        menuFileAction.putValue(Action.ACTION_COMMAND_KEY,
                            resource.getActionKey(ActionResource.MENUFILE));


        
        //�½���Ŀ,��,�ӿ�,�ļ���
        putActionValue(newAction,ActionResource.NEW,resource,akResource);

        //�½���Ŀ
        putActionValue(newProjectAction,ActionResource.NEW_PROJECT,resource,akResource);

        //�½����ļ�
        putActionValue(newClassAction,ActionResource.NEW_CLASS,resource,akResource);

        //����Ŀ
        putActionValue(openProjectAction,ActionResource.OPEN_PROJECT,resource,akResource);

        //���ļ�
        putActionValue(openFileAction,ActionResource.OPEN_FILE,resource,akResource);

        //�ر���Ŀ
        putActionValue(closeProjectAction,ActionResource.CLOSE_PROJECT,resource,akResource);

        //�ر��ļ�
        putActionValue(closeFileAction,ActionResource.CLOSE_FILE,resource,akResource);

        //������Ŀ
        putActionValue(saveProjectAction,ActionResource.SAVE_PROJECT,resource,akResource);

        //�����ļ�
        putActionValue(saveFileAction,ActionResource.SAVE_FILE,resource,akResource);

        //����ȫ���ļ�
        putActionValue(saveAllAction,ActionResource.SAVE_ALL,resource,akResource);
        
        //���µ�¼
        putActionValue(reloginAction,ActionResource.RELOGIN,resource,akResource);

        //�޸�����
        putActionValue(changePasswordAction,ActionResource.CHANGE_PASSWORD,resource,akResource);
        
        //����Ŀ��
        putActionValue(openTableAction,ActionResource.OPEN_TABLE,resource,akResource);
        
        //�ϴ�
        putActionValue(uploadAction,ActionResource.UPLOAD,resource,akResource);
        
        //�½���ҵ����
        putActionValue(newHomeworkAction,ActionResource.NEW_HOMEWORK,resource,akResource);
        
        //�˳�
        putActionValue(exitAction,ActionResource.EXIT,resource,akResource);

    }

    /**
     * ����Action����Ӧ
     * @param aCenter ConsoleCenter
     * @param noMeaning int
     */
    private FileActions(ConsoleCenter aCenter,int noMeaning) {
        super(aCenter);

        //�ļ��˵�
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
        
        //�½���Ŀ,��,�ӿ�,�ļ���
        newAction = new AbstractAction() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 7197655832252554184L;

			@Override
			public void actionPerformed(ActionEvent e) {

            }
        };
        
        

        //�½���Ŀ
        newProjectAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	
            	newProjectPanel newPrjPanel = new newProjectPanel(center);
            	newPrjPanel.setModal(true);            	
            	newPrjPanel.setVisible(true);    
            }
        };

        //�½����ļ�
        newClassAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //����Ŀ
        openProjectAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	Session session = SessionFrameMapper.getSessionForFrame(SessionFrameMapper.getOwningFrame(e));
            	ConsoleCenter.setCurrentSession(session);
            	FileDialog fd = new FileDialog(ConsoleCenter.mainFrame,"��Java����",FileDialog.LOAD);
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

        //���ļ�
        openFileAction = new OpenFileAction(center);

        //�ر���Ŀ
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

        //�ر��ļ�
        closeFileAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	int index = ConsoleCenter.mainFrame.rightPartPane.codePane.fileTabPane.getSelectedIndex();
            	ConsoleCenter.mainFrame.rightPartPane.codePane.closeTab(index);
            }
        };

        //������Ŀ
        saveProjectAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	ConsoleCenter.projectManager.StoreProject(ConsoleCenter.projectManager.getCurProject());
            }
        };

        //�����ļ�
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

        //����ȫ���ļ�
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

        //�˳�
        exitAction = new ExitAction();
    }

}
