package anyviewj.net.table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;

import anyviewj.client.database.ExcelAction;
import anyviewj.client.database.QuestionBank;
import anyviewj.debug.session.Session;
import anyviewj.debug.session.SessionFrameMapper;

public class OpenTableAction extends AbstractAction{
    
	private TableTerminal tt = new TableTerminal();
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Session session = SessionFrameMapper.getSessionForFrame(SessionFrameMapper.getOwningFrame(arg0));
		QuestionBank qb = new QuestionBank(session);
		final ExcelAction ea = new ExcelAction(qb.excel);
		ea.setSession(session);
		qb.excel.addMouseListener(ea);
		qb.ShowTable();
	}

}
