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
    //��������
    public final ConsoleCenter center;

    //���������
    public final CodePane codePane;
    //����;���
    public final GroupPane groupPane;

    public RightPartPane(ConsoleCenter center) {
        super(VERTICAL_SPLIT);
        this.center = center;
        //��ʼ���������
        codePane = new CodePane(center);
        groupPane = new GroupPane(center);
        //��ʼ��
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
