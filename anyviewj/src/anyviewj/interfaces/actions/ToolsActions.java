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
    public final Action menuToolsAction; //工具菜单
    public final Action configAction; //配置

    public ToolsActions(ConsoleCenter aCenter){
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


        //工具菜单
        menuToolsAction.putValue(Action.NAME,resource.getActionName(ActionResource.MENUTOOLS));
        menuToolsAction.putValue(Action.ACTION_COMMAND_KEY,resource.getActionKey(ActionResource.MENUTOOLS));

        //配置
        putActionValue(configAction,ActionResource.CONFIG,resource,akResource);

    }

    /**
    * 处理Action的响应
    * @param aCenter ConsoleCenter
    * @param noMeaning int
    */
    private ToolsActions(ConsoleCenter aCenter,int noMeaning) {
        super(aCenter);

        //工具菜单
        menuToolsAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //配置
        configAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };
    }
}
