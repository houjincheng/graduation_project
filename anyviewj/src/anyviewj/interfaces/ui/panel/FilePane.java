package anyviewj.interfaces.ui.panel;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import anyviewj.console.ConsoleCenter;
import anyviewj.debug.session.Session;
import anyviewj.debug.source.SourceSource;
import anyviewj.interfaces.resource.FormResource;
import anyviewj.interfaces.ui.Project;

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
public class FilePane extends CodeTabPane {
	private static final long serialVersionUID = -1679800288077664613L;
	//源代码面板
    public final SourcePane source;

    private Project prj = null;
	public Project getPrj() {
		return prj;
	}
	public void setPrj(Project prj) {
		this.prj = prj;
	}
	public FilePane(ConsoleCenter center, CodeTabPane codeparent,
			Session session, SourceSource sourceSrc) {
		super(center,codeparent, SwingConstants.BOTTOM,JTabbedPane.SCROLL_TAB_LAYOUT);

        //IconResource ir = center.resourceManager.getIconResource();
        FormResource fr = owner.center.resourceManager.getFormResource();

        //初始化源代码
        source = new SourcePane(this, session, sourceSrc);
        addTab(fr.getNames(FormResource.CODEPANE_SOURCEFILE),source);
        
        //初始化类数据
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLUE);
        addTab(fr.getNames(FormResource.CODEPANE_CLASSFILE),panel);
        
	}
    
}
