package anyviewj.net.server.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

/**
 * ��Ҫ������࣬��ֻ��һ���������������
 * @author hou
 *
 */
@Deprecated
public class DBAccessorOld {

	public static final String STUDENTINFORMATIONTABLE_STUDENTNUM = "studentNum";
	public static final String STUDENTINFORMATIONTABLE_PASSWORD = "password";

	private Connection conn = null;
	
	public DBAccessorOld( Connection conn )
	{
		this.conn = conn;
	}
	/**
	 * �����ݿ��еı��ĳ������ΪNULLʱ��ResultSet��null��ʾ�����浽Object[]��Ҳ��null
	 * ��ʱ�ַ�������ʱ��������ַ������ȱ�ʵ�ʷ���Ŀռ�Сʱ�����ÿո���
	 * @param table     ������������������ģʽ��+����
	 * @param column
	 * @return
	 * @throws SQLException
	 */
	@Deprecated
	public LinkedList<Object[]> read(String table, String[] column) throws SQLException
	{
		int len = column.length;
		int i = 0;
		String sql = "SELECT ";
		
		Statement statement = null;
		ResultSet resultSet = null;
		
		LinkedList<Object[]> result = new LinkedList<Object[]>( ); 
//		���ݲ�������Ҫִ�е�SQL���
		for ( i=0; i<len - 1 ; i++ )
		{
			sql += ( column[i] + "," );
		}
		sql += column[i] + " ";
		sql += "FROM " + table;
		System.out.println( "sql = " + sql );
		
		statement = this.conn.createStatement();
		resultSet = statement.executeQuery( sql );
		
		while ( resultSet.next() )
//			�����������ÿһ��
		{
			Object[] tmp = new Object[len];  
			for ( int index=0; index<len; index++ )
//			������ǰ�е�ÿ������
			{
				tmp[index] = resultSet.getObject( column[index] );
			}
			result.add( tmp );
		}
		return result;
	}
//	public List read(String table, String[] column, HashMap conditions) throws SQLException;
//	public void insert(String table, List rows) throws SQLException;
//	public void update(String table, Row selectedRow, Row updateRow) throws SQLException;
//	public void delete(String table, Row selectedRow) throws SQLException;

		
}
