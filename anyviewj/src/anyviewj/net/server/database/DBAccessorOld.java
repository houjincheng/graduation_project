package anyviewj.net.server.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

/**
 * 不要用这个类，这只是一个拿来做试验的类
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
	 * 当数据库中的表的某个分量为NULL时，ResultSet用null表示，保存到Object[]中也是null
	 * 当时字符串类型时，保存的字符串长度比实际分配的空间小时，会用空格补齐
	 * @param table     必须是完整表名，即模式名+表名
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
//		根据参数构造要执行的SQL语句
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
//			遍历结果集的每一行
		{
			Object[] tmp = new Object[len];  
			for ( int index=0; index<len; index++ )
//			遍历当前行的每个分量
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
