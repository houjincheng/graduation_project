package anyviewj.interfaces.ui.panel;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTree;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import java.awt.Color;

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
public class ProjectGroupPane extends JPanel {

    public final JToolBar projectGroupToolbar = new JToolBar();
    public final JTree projectGroupTree = new JTree();

    public ProjectGroupPane() {
        super(new BorderLayout());

        projectGroupToolbar.setBorder(BorderFactory.createEmptyBorder());
        projectGroupToolbar.add(new JButton("ÏîÄ¿×é"));//²âÊÔ
        add(projectGroupToolbar,BorderLayout.NORTH);

        projectGroupTree.setBorder(BorderFactory.createLineBorder(new Color(0x7F,0x9D,0xB9), 1));
        add(projectGroupTree,BorderLayout.CENTER);
    }
}
