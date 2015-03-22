package anyviewj.client.database;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.xml.parsers.ParserConfigurationException;

import anyviewj.console.ConsoleCenter;
import anyviewj.debug.session.Session;
import anyviewj.debug.session.SessionFrameMapper;
import anyviewj.interfaces.ui.Project;
import anyviewj.interfaces.ui.manager.JavaProjectManager;
import anyviewj.net.common.Request;

public class QuestionBank extends JFrame{
	
	private JTabbedPane tab = new JTabbedPane();
	
	private JButton refresh = new JButton("刷新");
	
	private JButton OpenOriginal = new JButton("打开原题");
	
	private JButton open = new JButton("打开");
	
	private JButton exit = new JButton("退出");
	
	public static Execl excel = new Execl("离散数学");
	private JScrollPane jsPane = new JScrollPane(excel);
	
	private static Session session = null;
	
	public String chapter = null;
	public String question = null;
	
	public QuestionBank(Session session){
		super();
		this.session = session;
//exit.addActionListener( new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				System.exit( 0 );
//				
//			}
//		} );
		
		Box bottom = Box.createHorizontalBox();
		bottom.add(refresh);
		bottom.add(Box.createHorizontalGlue());
		bottom.add(OpenOriginal);
		bottom.add(open);
//		bottom.add(exit);
		
		Color color = new Color(193,210,240);
		excel.setSelectionBackground(color);
        excel.setSelectionForeground(Color.BLACK);
		
		
			  
		
		JPanel panel = new JPanel(new GridBagLayout());
		//constraints is composing class;
		GridBagConstraints constraints = new GridBagConstraints();
		panel.setOpaque(false);
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		panel.add(tab, constraints);
		tab.addTab("离散数学",jsPane);
		
		constraints.weighty = 0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridy = 1;
		panel.add(bottom, constraints);
		setContentPane(panel);
		this.setLocation(350, 150);
		this.setSize(700,400);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//pack();
		try {
//          System.out.println(UIManager.getSystemLookAndFeelClassName());
          UIManager.setLookAndFeel(UIManager.
                                   getSystemLookAndFeelClassName());
          //com.sun.java.swing.plaf.windows.WindowsLookAndFeel
          SwingUtilities.updateComponentTreeUI( this );
      } catch (Exception exception) {
          exception.printStackTrace();
      }
		initRefreshButton();
	}
	
	public void initial(){
		
	}
	
	private void initRefreshButton(){
		refresh.addActionListener(new refreshButtonActionListener());
		//OpenOriginal.addActionListener(new openButtonActionListener());
		OpenOriginal.addActionListener(new OpenOriginalActionListener());
	}
	
//	public static void main(String[] args){
//		SwingUtilities.invokeLater(
//				new Runnable(){
//					public void run(){
//						createAndShow();
//					}
//				}
//				);
//	}
	
	public void ShowTable(){
		SwingUtilities.invokeLater(
				new Runnable(){
					public void run(){
						createAndShow();
					}
				}
				);
	}
	
	public static void createAndShow(){
		QuestionBank q1 = new QuestionBank(session);
		q1.setVisible(true);
	}
	
	public void setCQ(String s1, String s2){
		chapter = s1;
		question = s2;
	}
	
	
	private class openButtonActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(chapter!=null&&question!=null)
			{
			 Request a = new QuestionRequest("线性表","单向链表","raw");
			 a.resolve();
			}
		}
	}
	
	private class refreshButtonActionListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JButton s = refresh;
			s.setEnabled(false);
			JScrollPane j = (JScrollPane) tab.getSelectedComponent();
			Execl a = (Execl) j.getViewport().getView();
			String name = a.getTabName();
			ExeclRequest er = new ExeclRequest(name);
			er.resolve();
			
			if(a.refreshData()==0)
			{
				OpenOriginal.setVisible(false);
				open.setVisible(false);
			}
			else
			{
				OpenOriginal.setVisible(true);
				open.setVisible(true);
			}
				
			s.setEnabled(true);
		}
	}
	
	
	
	private class OpenOriginalActionListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int row = excel.getSelectedRow();
	    	if(row<0){
	    		return ;
	    	}
	    	else
	    	{
	    	  String s1 = (String) excel.getValueAt(row, 0);
	    	  String s2 = (String) excel.getValueAt(row,1);
	    	  
	    	  chineseTransformer trans = new chineseTransformer(s1,s2);
	    	  setCQ(trans.returnChapter(),trans.returnExercise());
	    	  if(chapter!=null&&question!=null)
				{
				 Request a = new QuestionRequest(chapter,question,"raw");
				 a.resolve();
				}
	    	  else
	    	  {
	    		  return;
	    	  }
	  		  String exerciseName = s1;
	  		if(session == null)
	  		{
	  			return;
	  		}
	        else
	        {
	          exerciseName = "E:\\anyviewjPPPP\\project\\"+chapter+"\\"+question+".ajp";
	          Project prj = ((JavaProjectManager)ConsoleCenter.projectManager).findProject(exerciseName);
	          if (prj != null) {	        		
	          	ConsoleCenter.mainFrame.leftPartPane.projectPane.curProjectPane.setCurProject(prj);
	          	((JavaProjectManager)ConsoleCenter.projectManager).setCurProject(prj);
	          }
	          else {
	        	  try{
	          	prj = ConsoleCenter.projectManager.openProject(exerciseName, session);
	        	  }
	        	  catch (NullPointerException x){
	        		    String message = "项目不存在!";
						String title = "警告";
						int optionType = JOptionPane.DEFAULT_OPTION;
						int messageType = JOptionPane.ERROR_MESSAGE;
						JOptionPane.showConfirmDialog(excel, message, title, optionType, messageType);
						return;
	        	  }
	          }
	          ((JavaProjectManager)ConsoleCenter.projectManager).setprjList(prj);
	        }
	    	}
		}
		
	}
	
}
