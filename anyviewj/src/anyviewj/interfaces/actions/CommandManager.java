package anyviewj.interfaces.actions;

import anyviewj.console.ConsoleCenter;

/**
 * <p>Title: 控制命令管理器</p>
 *
 * <p>Description: 管理菜单,工具栏等用户命令</p>
 *
 * <p>Copyright: Copyright (c) 2007 gdut 1627</p>
 *
 * <p>Company: gdut 1627</p>
 *
 * @author cyf
 * @version 1.0
 */


public class CommandManager {
    private ConsoleCenter center;
    public final FileActions fileActions;
    public final EditActions editActions;
    public final SearchActions searchActions;
    public final ProjectActions projectActions;
    public final RunActions runActions;
    public final ToolsActions toolsActions;
    public final ViewActions viewActions;
    public final HelpActions helpActions;

    public CommandManager(ConsoleCenter aCenter) {
        assert(aCenter != null);
        this.center = aCenter;
        fileActions = new FileActions(center);
        editActions = new EditActions(center);
        searchActions = new SearchActions(center);
        projectActions = new ProjectActions(center);
        runActions = new RunActions(center);
        toolsActions = new ToolsActions(center);
        viewActions = new ViewActions(center);
        helpActions = new HelpActions(center);
    }
}
