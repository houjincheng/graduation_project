package anyviewj.client.database;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import anyviewj.console.ConsoleCenter;
import anyviewj.debug.session.Session;
import anyviewj.debug.session.SessionFrameMapper;
import anyviewj.interfaces.ui.Project;
import anyviewj.interfaces.ui.manager.JavaProjectManager;
import anyviewj.net.common.Request;

public class ExcelAction implements MouseListener{
	
	private String exerciseName = null;
    public final Execl excel;
    public Session session = null;
	
	public ExcelAction(final Execl e){
		excel = e;
	}
	
    public void setSession(Session ss){
    	this.session = ss;
    }

//    public void OpenOriginal(ActionEvent e){
//		// TODO Auto-generated method stub
//		int row = excel.getSelectedRow();
//    	if(row<0){
//    		return ;
//    	}
//    	else
//    	{
//    	  String s1 = (String) excel.getValueAt(row, 0);
//    	  String s2 = (String) excel.getValueAt(row,1);
//    	  
//    	  chineseTransformer trans = new chineseTransformer(s1,s2);
//    	  String chapter = trans.returnChapter();
//    	  String question = trans.returnExercise();
//    	  if(chapter!=null&&question!=null)
//			{
//			 Request a = new QuestionRequest(chapter,question,"raw");
//			 a.resolve();
//			}
//    	  else
//    	  {
//    		  return;
//    	  }
//  		  String exerciseName = s1;
//  		if(session == null)
//  		{
//  			return;
//  		}
//        else
//        {
//          exerciseName = "E:\\anyviewjPPPP\\project\\"+chapter+"\\"+question+".ajp";
//          Project prj = ((JavaProjectManager)ConsoleCenter.projectManager).findProject(exerciseName);
//          if (prj != null) {
//          	ConsoleCenter.mainFrame.leftPartPane.projectPane.curProjectPane.setCurProject(prj);
//          	((JavaProjectManager)ConsoleCenter.projectManager).setCurProject(prj);
//          }
//          else {
//          	prj = ConsoleCenter.projectManager.openProject(exerciseName, session);
//          }
//          ((JavaProjectManager)ConsoleCenter.projectManager).setprjList(prj);
//        }
//    	}
//	}
//    

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getClickCount()!=2)
			return;
		int row = excel.getSelectedRow();
    	if(row<0){
    		return ;
    	}
    	else
    	{
    	  String s1 = (String) excel.getValueAt(row, 0);
    	  String s2 = (String) excel.getValueAt(row,1);
    	  
    	  chineseTransformer trans = new chineseTransformer(s1,s2);
    	  String chapter = trans.returnChapter();
    	  String question = trans.returnExercise();
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


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


		
	}

    
