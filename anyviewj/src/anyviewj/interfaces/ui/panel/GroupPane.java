package anyviewj.interfaces.ui.panel;

import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.*;
import anyviewj.interfaces.resource.IconResource;
import anyviewj.interfaces.resource.FormResource;
import anyviewj.console.ConsoleCenter;
import javax.swing.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: ����;���</p>
 *
 * <p>Copyright: Copyright (c) 2007 gdut 1627</p>
 *
 * <p>Company: gdut 1627</p>
 *
 * @author cyf
 * @version 1.0
 */
public class GroupPane extends JTabbedPane {
    //����������
    //public final JPanel outputPane = new JPanel(new BorderLayout());
    //public final JTextArea output = new JTextArea();
    //����Ϣ���
    //public final ClassPanel classPane = new ClassPanel();
    //������
    public final OutputPanel ouputPane = new OutputPanel();
    //���öϵ����
    public final BreakPanel breakPane = new BreakPanel();
    //������ʾ���
    public final LocalsPanel localPane = new LocalsPanel();
    
    //��������
    public final ConsoleCenter center;
    public GroupPane(ConsoleCenter center) {
        super(BOTTOM,SCROLL_TAB_LAYOUT);
        this.center = center;
        FormResource fr = center.resourceManager.getFormResource();
        IconResource ir = center.resourceManager.getIconResource();

        //output.setFont(Font.getFont("DialogInput"));
        //Font font = new Font("DialogInput",Font.PLAIN,12);
        //output.setFont(font);
        //JScrollPane scrollpane= new JScrollPane(output);
        //outputPane.add(scrollpane,BorderLayout.CENTER);
        //outputPane.setBackground(Color.WHITE);
        //outputPane.setBorder(BorderFactory.createLineBorder(Color.lightGray,1));
        //addTab(fr.getNames(fr.OUTPUT_FORM),ir.getIcon(ir.TABPANE_CLOSE_WITHOUT_SAVE),
        //                  outputPane,fr.getTips(fr.OUTPUT_FORM));
        
        //���Ա�����Ϣ����
        JPanel p1 = new JPanel();
        p1.setBackground(Color.WHITE);
        p1.setBorder(BorderFactory.createLineBorder(Color.lightGray,1));
        //addTab("������Ϣ",ir.getIcon(ir.TABPANE_CLOSE_WITHOUT_SAVE),p1);
        //���Բ��ҽ������
        p1 = new JPanel();
        p1.setBackground(Color.WHITE);
        p1.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,1));
        //addTab("���ҽ��",ir.getIcon(ir.TABPANE_CLOSE_WITHOUT_SAVE),p1);
        
        //����Ϣ����      
        //addTab(fr.getNames(fr.CLASS_FORM),ir.getIcon(ir.TABPANE_CLOSE_WITHOUT_SAVE),
        //                   classPane.getUI(),fr.getTips(fr.CLASS_FORM));        
        
        //ConsoleCenter.addViewListeners(classPane);
        //�����������
        addTab(fr.getNames(FormResource.OUTPUT_FORM),ir.getIcon(IconResource.TABPANE_CLOSE_WITHOUT_SAVE),
        		ouputPane.getUI(),fr.getTips(FormResource.OUTPUT_FORM));        

        ConsoleCenter.addViewListeners(ouputPane);
        //�ϵ㴰��
        addTab(fr.getNames(FormResource.BREAKPOINT_FORM),ir.getIcon(IconResource.TABPANE_CLOSE_WITHOUT_SAVE),
        		breakPane.getUI(),fr.getTips(FormResource.BREAKPOINT_FORM));        

        ConsoleCenter.addViewListeners(breakPane);
        
        addTab(fr.getNames(FormResource.Locals_FORM),ir.getIcon(IconResource.TABPANE_CLOSE_WITHOUT_SAVE),
        		localPane.getUI(),fr.getTips(FormResource.Locals_FORM)); 
        
        ConsoleCenter.addViewListeners(localPane);
        
    }
}
