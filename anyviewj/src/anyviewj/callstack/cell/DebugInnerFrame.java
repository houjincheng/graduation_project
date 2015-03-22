package anyviewj.callstack.cell;

import javax.swing.JInternalFrame;
import anyviewj.console.ConsoleCenter;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Component;

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
public abstract class DebugInnerFrame extends JInternalFrame {

    //控制中心
    public final ConsoleCenter center;
    public final JScrollPane scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                                                         ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    public DebugInnerFrame(String title,ConsoleCenter center) {
        super(title,true,true,true,true);
        this.center = center;
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setVisible(true);
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(scrollPane,BorderLayout.CENTER);
    }

    public void addScrollComponent(Component c){
        this.scrollPane.setViewportView(c);
    }
    public void removeScrollComponent(Component c){
    	
    	this.scrollPane.setViewportView( null );
    }
    

    
    /**
     * 每次运行前都必须将以前的数据清空
     */
    public abstract void clear();
}
