package anyviewj.interfaces.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import anyviewj.client.database.UploadRequest;
import anyviewj.console.ConsoleCenter;
import anyviewj.interfaces.ui.JavaProject;
import anyviewj.interfaces.ui.Project;
import anyviewj.interfaces.ui.panel.ProjectManager;


public class UploadAction extends AbstractAction {

	/**
	 * author: yang
	 */
	private static final long serialVersionUID = 1L;
	
	private ConsoleCenter conCenter;

	public UploadAction(ConsoleCenter aCenter) {
		// TODO Auto-generated constructor stub
		super();
		this.conCenter=aCenter;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("上传");
		this.setEnabled(false);
		
		ProjectManager pM = conCenter.projectManager;
		
		JavaProject cur=(JavaProject) pM.getCurProject();
		if(cur==null){
			
			JOptionPane.showMessageDialog(conCenter.mainFrame,"没有要上传的项目");
		}else{
			if(cur.fileList.isEmpty()){
				JOptionPane.showMessageDialog(conCenter.mainFrame,"没有要上传的文件");
			}else{
				UploadRequest upr = new UploadRequest(cur);
				int state=upr.resolve();
				if (state==1){
					JOptionPane.showMessageDialog(conCenter.mainFrame,"上传成功");
				}else{
					JOptionPane.showMessageDialog(conCenter.mainFrame,"上传失败");
				}
			}
			
		}
		
		this.setEnabled(true);
	}

}
