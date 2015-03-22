package anyviewj.interfaces.ui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import anyviewj.console.ConsoleCenter;
import anyviewj.debug.manager.BreakpointManager;
import anyviewj.debug.manager.ContextManager;
import anyviewj.debug.session.Session;
import anyviewj.debug.source.SourceSource;
import anyviewj.interfaces.ui.drawer.BreakPointDrawer;
import anyviewj.interfaces.ui.drawer.DebuggingHighlighter;
import anyviewj.interfaces.ui.drawer.GutterArea;


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
public class SourcePane extends JPanel {
	private static final long serialVersionUID = 8897629348107342663L;
	
	//工具栏
    public final JToolBar toolbar = new JToolBar();
    //文本编辑器的外框
    public final TextEditor editorPanel;
    //状态栏
    public final JLabel statusbar = new JLabel(" ");
    //所有者
    public final CodeTabPane owner;
    //工具栏按钮集
    public final SourcePaneToolButtons toolButtons;
    
    public final SourceSource sourceSrc;
    
    public  DebuggingHighlighter dbgER;
    
    private String filePath = "";
    
    public SourcePane(final CodeTabPane owner, Session session, SourceSource sourceSrc) {
        super(new BorderLayout());
        this.owner = owner;
        this.sourceSrc = sourceSrc;
        //初始化工具栏
        toolButtons = new SourcePaneToolButtons(ConsoleCenter.commandManager,toolbar);
        toolbar.setBorder(BorderFactory.createEtchedBorder(Color.white,new Color(165, 163, 151)));
        toolbar.setMinimumSize(new Dimension(180,toolbar.getHeight()));
        
        editorPanel = new TextEditor(this);
        BreakPointDrawer breakpointDrawer = new BreakPointDrawer(editorPanel.rowArea, sourceSrc);
        BreakpointManager bpman = (BreakpointManager)
                session.getManager(BreakpointManager.class);
            bpman.addBreakListener(breakpointDrawer);
            //owner.center.commandManager.runActions.setTD(editorPanel);
        ((GutterArea)editorPanel.rowArea).setBpDrawer(breakpointDrawer);
        dbgER = new DebuggingHighlighter(editorPanel.editor, Color.CYAN);
        
        
        ((SourceEditor)editorPanel.editor).setLineHighlighter(dbgER);
        ContextManager ctman = (ContextManager) session.getManager(ContextManager.class);
        ctman.addContextListener((SourceEditor)editorPanel.editor);
       
        
        
        //初始化状态栏
        statusbar.setBorder(BorderFactory.createEtchedBorder());
        statusbar.setDisplayedMnemonic('0');
        
        //添加到面板中
        add(toolbar,BorderLayout.NORTH);
        add(editorPanel,BorderLayout.CENTER);
        add(statusbar,BorderLayout.SOUTH);
        

    }

    public String getFilePath(){
        return this.filePath;
    }

    public void setFilePath(String filepath){
        this.filePath = filepath;
        File file = new File(filepath);
        if(file.exists()){
            try {
            	try {
					System.out.println(file.getCanonicalPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
                FileInputStream fin = new FileInputStream(file);
                int len = (int) file.length();
                byte[] buf = new byte[len];
                try {
                    fin.read(buf, 0, len);
                    fin.close();
                } catch (IOException ex1) {
                    System.out.println("IO Error!");
                }
                String str;
                try {
                    str = new String(buf, "GB2312");
                } catch (UnsupportedEncodingException ex2) {
                    str = new String(buf);
                    ex2.printStackTrace();
                }
                this.editorPanel.setTextAndAddUndoable(str);
            } catch (FileNotFoundException ex) {
            	ex.printStackTrace();
            }
        }
    }
    
    public boolean undo() {
    	return editorPanel.undo();
    }
    
    public boolean redo() {
    	return editorPanel.redo();
    }
    
    public void copy() {
    	editorPanel.copy();
    }
    
    public void cut() {
    	editorPanel.cut();
    }
    
    public void paste() {
    	editorPanel.paste();
    }
    
    public void delete() {
    	editorPanel.delete();
    }

    public void selectAll() {
    	editorPanel.selectAll();
    }

	public SourceSource getSourceSrc() {
		return sourceSrc;
	}
}
