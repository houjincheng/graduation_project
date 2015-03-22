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

public class ToolsActions extends CommandAction{
    public final Action menuToolsAction; //���߲˵�
    public final Action configAction; //����

    public ToolsActions(ConsoleCenter aCenter){
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


        //���߲˵�
        menuToolsAction.putValue(Action.NAME,resource.getActionName(ActionResource.MENUTOOLS));
        menuToolsAction.putValue(Action.ACTION_COMMAND_KEY,resource.getActionKey(ActionResource.MENUTOOLS));

        //����
        putActionValue(configAction,ActionResource.CONFIG,resource,akResource);

    }

    /**
    * ����Action����Ӧ
    * @param aCenter ConsoleCenter
    * @param noMeaning int
    */
    private ToolsActions(ConsoleCenter aCenter,int noMeaning) {
        super(aCenter);

        //���߲˵�
        menuToolsAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //����
        configAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };
    }
}