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
    public final Action menuSearchAction; //查找菜单
    public final Action findAction; //查找
    public final Action findInPathction; //在指定路径查找
    public final Action findDownAction; //查找下一个
    public final Action findUpAction; //查找上一个
    public final Action replaceAction; //替换
    public final Action replaceInPathAction; //在指定路径替换
    public final Action gotoAction; //跳至

    public SearchActions(ConsoleCenter aCenter) {
        this(aCenter, 0); //使用这种构造方式,是为了让程序代码清晰
        refreshActions(); //设置Actions的名称,提示,图标等.
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


        //查找菜单
        menuSearchAction.putValue(Action.NAME, resource.getActionName(ActionResource.MENUSEARCH));
        menuSearchAction.putValue(Action.ACTION_COMMAND_KEY,resource.getActionKey(ActionResource.MENUSEARCH));

        //查找
        putActionValue(findAction,ActionResource.FIND,resource,akResource);

        //在指定路径查找
        putActionValue(findInPathction,ActionResource.FIND_INPATH,resource,akResource);

        //查找下一个
        putActionValue(findDownAction,ActionResource.FINDDOWN,resource,akResource);

        //查找上一个
        putActionValue(findUpAction,ActionResource.FINDUP,resource,akResource);

        //替换
        putActionValue(replaceAction,ActionResource.REPLACE,resource,akResource);

        //在指定路径替换
        putActionValue(replaceInPathAction,ActionResource.REPLACE_INPATH,resource,akResource);

        //跳至
        putActionValue(gotoAction,ActionResource.GOTO,resource,akResource);

    }

    /**
     * 处理Action的响应
     * @param aCenter ConsoleCenter
     * @param noMeaning int
     */
    private SearchActions(ConsoleCenter aCenter, int noMeaning) {
        super(aCenter);

        //查找菜单
        menuSearchAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //查找
        findAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //在指定路径查找
        findInPathction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //查找下一个
        findDownAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //查找上一个
        findUpAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //替换
        replaceAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //在指定路径替换
        replaceInPathAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //跳至
        gotoAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

    }

}
