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
    public final Action menuHelpAction; //帮助菜单
    public final Action helpAction; //帮助
    public final Action aboutAction; //关于

    public HelpActions(ConsoleCenter aCenter){
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


        //帮助菜单
        menuHelpAction.putValue(Action.NAME,resource.getActionName(ActionResource.MENUHELP));
        menuHelpAction.putValue(Action.ACTION_COMMAND_KEY,resource.getActionKey(ActionResource.MENUHELP));

        //帮助
        putActionValue(helpAction,ActionResource.HELP,resource,akResource);


        //关于
        putActionValue(aboutAction,ActionResource.ABOUT,resource,akResource);

    }

    /**
    * 处理Action的响应
    * @param aCenter ConsoleCenter
    * @param noMeaning int
    */
    private HelpActions(ConsoleCenter aCenter,int noMeaning) {
        super(aCenter);

        //帮助菜单
        menuHelpAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //帮助
        helpAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //关于
        aboutAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };
    }
}
