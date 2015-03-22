package anyviewj.client.database;

import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class Execl extends JTable{
	
	String tabName = null;
	public Execl(String name){
		super();
		tabName = name;
		Font font = new Font(Font.SERIF,Font.BOLD,12);
        this.setFont(font);
		refreshData(name);
	}
	
	public int refreshData(){
		return refreshData(tabName);		
	}
	
	public boolean isCellEditable(int row,int col)
	 {
	  return false;
	 }

	
	int refreshData(String name){
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		Connection ct = Sqlite.getConnection();
		
		try {
			Statement s = ct.createStatement();
			ResultSet rs = s.executeQuery("select * from " + name);
			
			if(rs != null){
				
				
				ResultSetMetaData rsmd = rs.getMetaData();
				
				rs.last();
				int rows = rs.getRow();
				rs.beforeFirst();
				
				int cols = rsmd.getColumnCount();
				
				
				
				((DefaultTableModel) dataModel).setColumnCount(cols);
				((DefaultTableModel) dataModel).setRowCount(rows);
				TableColumnModel tcm = getColumnModel();
				
				for(int i=0 ;i<cols;i++){
					TableColumn tc = tcm.getColumn(i);
					tc.setMinWidth(100);
//					tc.setPreferredWidth(100);
//					tc.sizeWidthToFit();
//					String a = rsmd.getColumnName(i+1);
					tc.setHeaderValue(rsmd.getColumnName(i+1));
				}
				getTableHeader().setResizingAllowed(true);
				for(int i=0;rs.next();i++){
					for(int j=0;j<cols;j++){
						setValueAt(rs.getObject(j+1),i,j);
					}
				}
				if(rows==0)
				{
				 return 0;
				}
				else
					return 1;
				
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		   return -1;
	}
	
	public String getTabName(){
		return tabName;
	}
}
