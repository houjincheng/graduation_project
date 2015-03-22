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
    public final Action menuProjectAction; //��Ŀ�˵�
    public final Action compileProjectAction; //������Ŀ
    public final Action compileFileAction; //�����ļ�
    public final Action buildAction; //������Ŀ
    public final Action projectPropertiesAction; //��Ŀ����

    public ProjectActions(ConsoleCenter aCenter) {
        this(aCenter, 0); //ʹ�����ֹ��췽ʽ,��Ϊ���ó����������
        refreshActions(); //����Actions������,��ʾ,ͼ���.
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


        //��Ŀ�˵�
        menuProjectAction.putValue(Action.NAME,resource.getActionName(ActionResource.MENUPROJECT));
        menuProjectAction.putValue(Action.ACTION_COMMAND_KEY,
                                      resource.
                                      getActionKey(ActionResource.MENUPROJECT));

        //������Ŀ
        putActionValue(compileProjectAction,ActionResource.COMPILE_PROJECT,resource,akResource);

        //�����ļ�
        putActionValue(compileFileAction,ActionResource.COMPILE_FILE,resource,akResource);

        //������Ŀ
        putActionValue(buildAction,ActionResource.BUILD,resource,akResource);

        //��Ŀ����
        putActionValue(projectPropertiesAction,ActionResource.PROJECT_PROPERTIES,resource,akResource);

    }

    /**
     * ����Action����Ӧ
     * @param aCenter ConsoleCenter
     * @param noMeaning int
     */
    private ProjectActions(ConsoleCenter aCenter, int noMeaning) {
        super(aCenter);

        //��Ŀ�˵�
        menuProjectAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //������Ŀ
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

        //�����ļ�
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

        //������Ŀ
        buildAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //��Ŀ����
        projectPropertiesAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

    }

}
