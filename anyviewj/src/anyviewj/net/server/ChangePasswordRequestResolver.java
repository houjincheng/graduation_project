package anyviewj.net.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import anyviewj.net.common.RequestResolver;
/**
 * 这里需要特别注意一点的是：
 * 用户的登录指纹是用户名与密码的拼接
 * 需不需要更新登录表中的数据，这需要重新考虑
 * @author hou
 *
 */
public class ChangePasswordRequestResolver extends RequestResolver{

	@Override
	public Document execute(Document doc) {
		
		Document result = createFormattedResultDocment();;
		
		String studentID = 
				( (Element) doc.getElementsByTagName( CHANGEPASSWORDREQUEST_STUDENTIDNODE ).item( 0 ) )
				.getAttribute( CHANGEPASSWORDREQUEST_STUDENTIDNODE );
		String oldPsw = 
				( (Element) doc.getElementsByTagName( CHANGEPASSWORDREQUEST_OLDPASSWORDNODE ).item( 0 ) )
				.getAttribute( CHANGEPASSWORDREQUEST_OLDPASSWORDNODE );
		String newPsw = 
				( (Element) doc.getElementsByTagName( CHANGEPASSWORDREQUEST_NEWPASSWORDNODE ).item( 0 ) )
				.getAttribute( CHANGEPASSWORDREQUEST_NEWPASSWORDNODE );
		
		if ( ServerTerminal.getInstance().getLoginedClientMap().contain( studentID ) )
//		用户的登录指纹是用户名与密码的简单拼接
		{
			int value = process(studentID, newPsw );
			
			if ( value == 1 )
//			将解析结果更新为成功，因为默认是失败
			{
				( ( Element )result.getElementsByTagName( REQUESTRESOLVER_RESULTNODE  ).item( 0 ) )
				.setAttribute( REQUESTRESOLVER_RESULTNODE_ATTRIBUTE,
						String.valueOf( RESOLVE_SUCCESSED ) );
			}
		}
		
		return result;
	}
	/**
	 * 更新指定学生的密码
	 * @param studentID
	 * @param newPsw
	 * @return 成功返回1，更新失败返回0
	 */
	private int process( String studentID, String newPsw )
	{
		Connection conn  = ServerTerminal.getInstance().getConnectionPool().getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		
		String tableName = "dbo." + studentID.substring( 0, studentID.length() - 4 );
		String rowIndex = studentID.substring( studentID.length() - 4, studentID.length() );
		
		String sql = "UPDATE " + tableName + " SET password = " + newPsw 
				+ " WHERE studentNum = " + rowIndex + ";";
		
		try {
			statement = conn.createStatement();
			statement.executeUpdate( sql );
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}


}
