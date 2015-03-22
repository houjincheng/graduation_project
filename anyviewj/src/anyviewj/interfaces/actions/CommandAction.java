package anyviewj.interfaces.actions;

import javax.swing.Action;
import javax.swing.ImageIcon;
import anyviewj.console.ConsoleCenter;
import anyviewj.interfaces.resource.ActionResource;
import anyviewj.interfaces.resource.AcceleratorKeyResource;
import javax.swing.KeyStroke;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007 gdut 1627</p>
 *
 * <p>Company: gdut 1627</p>
 *
 * @author ltt
 * @version 1.0
 */
public abstract class CommandAction {
    //ȫ�ֿ�����������
    protected final ConsoleCenter center;

    public CommandAction(ConsoleCenter aCenter) {
        this.center = aCenter;
    }

    /**
     * ����Actions������,��ʾ,ͼ���.
     * �ر���Դ�����ı�(�����Ըı�)ʱ,���øú����������������
     */
    public abstract void refreshActions();


    protected void putActionValue(Action action,int index,ActionResource resource,
                          AcceleratorKeyResource akResource){
        //����
        action.putValue(Action.NAME,resource.getActionName(index));
        //��ʾ
        String s = akResource.getAcceleratorKeyName(index);
        if(s != null && !s.equals("")) s = " "+s;
        else s = "";
        action.putValue(Action.SHORT_DESCRIPTION ,resource.getActionDesc(index)+s);
        //�����
        action.putValue(Action.ACTION_COMMAND_KEY,resource.getActionKey(index));
        //ͼ��
        action.putValue(Action.SMALL_ICON,resource.getIcon(index));
        //���ټ�
        action.putValue(Action.ACCELERATOR_KEY,akResource.getAcceleratorKey(index));
    }
    
    
    protected void putActionValue(Action action, String name,String hint, String icon,String acc){
    	//����
    	action.putValue(Action.NAME, name);
    	//��ʾ    	
    	action.putValue(Action.SHORT_DESCRIPTION ,hint);
    	//ͼ��
    	action.putValue(Action.SMALL_ICON,new ImageIcon(icon));
    	//���ټ�
    	action.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke(acc));
}

}
