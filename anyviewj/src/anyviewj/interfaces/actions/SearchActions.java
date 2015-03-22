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

public class SearchActions extends CommandAction{
    public final Action menuSearchAction; //���Ҳ˵�
    public final Action findAction; //����
    public final Action findInPathction; //��ָ��·������
    public final Action findDownAction; //������һ��
    public final Action findUpAction; //������һ��
    public final Action replaceAction; //�滻
    public final Action replaceInPathAction; //��ָ��·���滻
    public final Action gotoAction; //����

    public SearchActions(ConsoleCenter aCenter) {
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


        //���Ҳ˵�
        menuSearchAction.putValue(Action.NAME, resource.getActionName(ActionResource.MENUSEARCH));
        menuSearchAction.putValue(Action.ACTION_COMMAND_KEY,resource.getActionKey(ActionResource.MENUSEARCH));

        //����
        putActionValue(findAction,ActionResource.FIND,resource,akResource);

        //��ָ��·������
        putActionValue(findInPathction,ActionResource.FIND_INPATH,resource,akResource);

        //������һ��
        putActionValue(findDownAction,ActionResource.FINDDOWN,resource,akResource);

        //������һ��
        putActionValue(findUpAction,ActionResource.FINDUP,resource,akResource);

        //�滻
        putActionValue(replaceAction,ActionResource.REPLACE,resource,akResource);

        //��ָ��·���滻
        putActionValue(replaceInPathAction,ActionResource.REPLACE_INPATH,resource,akResource);

        //����
        putActionValue(gotoAction,ActionResource.GOTO,resource,akResource);

    }

    /**
     * ����Action����Ӧ
     * @param aCenter ConsoleCenter
     * @param noMeaning int
     */
    private SearchActions(ConsoleCenter aCenter, int noMeaning) {
        super(aCenter);

        //���Ҳ˵�
        menuSearchAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //����
        findAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //��ָ��·������
        findInPathction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //������һ��
        findDownAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //������һ��
        findUpAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //�滻
        replaceAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //��ָ��·���滻
        replaceInPathAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //����
        gotoAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

    }

}
