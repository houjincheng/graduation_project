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
    //全局控制中心引用
    protected final ConsoleCenter center;

    public CommandAction(ConsoleCenter aCenter) {
        this.center = aCenter;
    }

    /**
     * 更新Actions的名称,提示,图标等.
     * 特别当资源发生改变(如语言改变)时,调用该函数来更新相关资料
     */
    public abstract void refreshActions();


    protected void putActionValue(Action action,int index,ActionResource resource,
                          AcceleratorKeyResource akResource){
        //名称
        action.putValue(Action.NAME,resource.getActionName(index));
        //提示
        String s = akResource.getAcceleratorKeyName(index);
        if(s != null && !s.equals("")) s = " "+s;
        else s = "";
        action.putValue(Action.SHORT_DESCRIPTION ,resource.getActionDesc(index)+s);
        //命令键
        action.putValue(Action.ACTION_COMMAND_KEY,resource.getActionKey(index));
        //图标
        action.putValue(Action.SMALL_ICON,resource.getIcon(index));
        //加速键
        action.putValue(Action.ACCELERATOR_KEY,akResource.getAcceleratorKey(index));
    }
    
    
    protected void putActionValue(Action action, String name,String hint, String icon,String acc){
    	//名称
    	action.putValue(Action.NAME, name);
    	//提示    	
    	action.putValue(Action.SHORT_DESCRIPTION ,hint);
    	//图标
    	action.putValue(Action.SMALL_ICON,new ImageIcon(icon));
    	//加速键
    	action.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke(acc));
}

}
