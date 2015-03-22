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
import java.awt.event.ActionEvent;
import anyviewj.interfaces.resource.ActionResource;
import anyviewj.interfaces.resource.AcceleratorKeyResource;
import anyviewj.interfaces.resource.ResourceManager;

public class ViewActions extends CommandAction{
    public final Action menuViewAction; //��ͼ�˵�
    public final Action outputAction; //�������

    public ViewActions(ConsoleCenter aCenter){
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


        //��ͼ�˵�
        menuViewAction.putValue(Action.NAME,resource.getActionName(ActionResource.MENUVIEW));
        menuViewAction.putValue(Action.ACTION_COMMAND_KEY,resource.getActionKey(ActionResource.MENUVIEW));

        //�������
        putActionValue(outputAction,ActionResource.OUTPUT,resource,akResource);

    }

    /**
    * ����Action����Ӧ
    * @param aCenter ConsoleCenter
    * @param noMeaning int
    */
    private ViewActions(ConsoleCenter aCenter,int noMeaning) {
        super(aCenter);

        //��ͼ�˵�
        menuViewAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //�������
        outputAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };
    }
}
