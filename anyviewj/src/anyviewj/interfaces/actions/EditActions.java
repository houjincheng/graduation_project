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
    public final Action menuEditAction; //编辑菜单
    public final Action undoAction; //撤销
    public final Action redoAction; //重做
    public final Action cutAction; //剪切
    public final Action copyAction; //复制
    public final Action pasteAction; //粘贴
    public final Action deleteAction; //删除
    public final Action selectAllAction; //全选

    public EditActions(ConsoleCenter aCenter) {
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

        //编辑菜单
        menuEditAction.putValue(Action.NAME, resource.getActionName(ActionResource.MENUEDIT));
        menuEditAction.putValue(Action.ACTION_COMMAND_KEY,
                            resource.getActionKey(ActionResource.MENUEDIT));

        //撤销
        putActionValue(undoAction,ActionResource.UNDO,resource,akResource);

        //重做
        putActionValue(redoAction,ActionResource.REDO,resource,akResource);

        //剪切
        putActionValue(cutAction,ActionResource.CUT,resource,akResource);

        //复制
        putActionValue(copyAction,ActionResource.COPY,resource,akResource);

        //粘贴
        putActionValue(pasteAction,ActionResource.PASTE,resource,akResource);

        //删除
        putActionValue(deleteAction,ActionResource.DELETE,resource,akResource);

        //全选
        putActionValue(selectAllAction,ActionResource.SELECTALL,resource,akResource);

    }

    /**
     * 处理Action的响应
     * @param aCenter ConsoleCenter
     * @param noMeaning int
     */
    private EditActions(ConsoleCenter aCenter, int noMeaning) {
        super(aCenter);

        //编辑菜单
        menuEditAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };


        //撤销
        undoAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //重做
        redoAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //剪切
        cutAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //复制
        copyAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //粘贴
        pasteAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //删除
        deleteAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };

        //全选
        selectAllAction = new AbstractAction(){
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        };
    }

}
