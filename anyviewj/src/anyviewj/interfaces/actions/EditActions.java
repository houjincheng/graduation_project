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

public class EditActions extends CommandAction{
    public final Action menuEditAction; //�༭�˵�
    public final Action undoAction; //����
    public final Action redoAction; //����
    public final Action cutAction; //����
    public final Action copyAction; //����
    public final Action pasteAction; //ճ��
    public final Action deleteAction; //ɾ��
    public final Action selectAllAction; //ȫѡ

    public EditActions(ConsoleCenter aCenter) {
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

        //�༭�˵�
        menuEditAction.putValue(Action.NAME, resource.getActionName(ActionResource.MENUEDIT));
        menuEditAction.putValue(Action.ACTION_COMMAND_KEY,
                            resource.getActionKey(ActionResource.MENUEDIT));

        //����
        putActionValue(undoAction,ActionResource.UNDO,resource,akResource);

        //����
        putActionValue(redoAction,ActionResource.REDO,resource,akResource);

        //����
        putActionValue(cutAction,ActionResource.CUT,resource,akResource);

        //����
        putActionValue(copyAction,ActionResource.COPY,resource,akResource);

        //ճ��
        putActionValue(pasteAction,ActionResource.PASTE,resource,akResource);

        //ɾ��
        putActionValue(deleteAction,ActionResource.DELETE,resource,akResource);

        //ȫѡ
        putActionValue(selectAllAction,ActionResource.SELECTALL,resource,akResource);

    }

    /**
     * ����Action����Ӧ
     * @param aCenter ConsoleCenter
     * @param noMeaning int
     */
    private EditActions(ConsoleCenter aCenter, int noMeaning) {
        super(aCenter);

        //�༭�˵�
        menuEditAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };


        //����
        undoAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //����
        redoAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //����
        cutAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //����
        copyAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //ճ��
        pasteAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //ɾ��
        deleteAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //ȫѡ
        selectAllAction = new AbstractAction(){
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };
    }

}
