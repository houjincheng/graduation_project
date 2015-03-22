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
import java.awt.event.ActionEvent;
import anyviewj.console.ConsoleCenter;
import anyviewj.interfaces.resource.ActionResource;
import anyviewj.interfaces.resource.AcceleratorKeyResource;
import anyviewj.interfaces.resource.ResourceManager;


public class HelpActions extends CommandAction{
    public final Action menuHelpAction; //�����˵�
    public final Action helpAction; //����
    public final Action aboutAction; //����

    public HelpActions(ConsoleCenter aCenter){
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


        //�����˵�
        menuHelpAction.putValue(Action.NAME,resource.getActionName(ActionResource.MENUHELP));
        menuHelpAction.putValue(Action.ACTION_COMMAND_KEY,resource.getActionKey(ActionResource.MENUHELP));

        //����
        putActionValue(helpAction,ActionResource.HELP,resource,akResource);


        //����
        putActionValue(aboutAction,ActionResource.ABOUT,resource,akResource);

    }

    /**
    * ����Action����Ӧ
    * @param aCenter ConsoleCenter
    * @param noMeaning int
    */
    private HelpActions(ConsoleCenter aCenter,int noMeaning) {
        super(aCenter);

        //�����˵�
        menuHelpAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //����
        helpAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //����
        aboutAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };
    }
}
