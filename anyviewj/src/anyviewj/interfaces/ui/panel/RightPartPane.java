package anyviewj.interfaces.ui.panel;

import javax.swing.JSplitPane;
import anyviewj.console.ConsoleCenter;

//import anyviewj.olds.mainframes.frames.rightpart.*;
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
public class RightPartPane extends JSplitPane{
    //控制中心
    public final ConsoleCenter center;

    //代码主面板
    public final CodePane codePane;
    //多用途面板
    public final GroupPane groupPane;

    public RightPartPane(ConsoleCenter center) {
        super(VERTICAL_SPLIT);
        this.center = center;
        //初始化两大面板
        codePane = new CodePane(center);
        groupPane = new GroupPane(center);
        //初始化
        setBorder(null);
        setToolTipText("");
        setLeftComponent(null);
        setRightComponent(null);
        add(codePane,TOP);
        add(groupPane,BOTTOM);
        setDividerSize(5);
        setDividerLocation(400);
        setResizeWeight(1.0);
    }
    
    public CodePane getCodePane(){
    	return codePane;
    }
}
