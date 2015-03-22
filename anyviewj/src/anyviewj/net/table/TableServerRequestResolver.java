package anyviewj.net.table;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import anyviewj.net.common.RequestResolver;
import anyviewj.net.server.ServerTerminal;

public class TableServerRequestResolver extends RequestResolver{

	@Override
	public Document execute(Document doc) {
		// TODO Auto-generated method stub
		
Document result = createFormattedResultDocment();;
		
		String studentID = 
				( (Element) doc.getElementsByTagName( TABLESERVERREQUEST_STUDENTIDNODE ).item( 0 ) )
				.getAttribute( TABLESERVERREQUEST_STUDENTIDNODE );
		
		
		if ( ServerTerminal.getInstance().getLoginedClientMap().contain( studentID ) )
//		用户的登录指纹是用户名与密码的简单拼接
		{
			int value = process(studentID);
			
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

	private int process(String studentID) {
		// TODO Auto-generated method stub
		
		Connection conn  = ServerTerminal.getInstance().getConnectionPool().getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		
		String serverTable = "dbo.ExerciseTable";// + studentID.substring( 0, studentID.length() - 4 );
		String localTable = "dbo.ExerciseTable2";
		
		
		//String rowIndex = studentID.substring( studentID.length() - 4, studentID.length() );
		
//		String sql = "UPDATE " + tableName + " SET password = " + newPsw 
//				+ " WHERE studentNum = " + rowIndex + ";";
		
		try {
			statement = conn.createStatement();
			resultSet = statement.executeQuery("select * from " + serverTable);
			
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int cols = rsmd.getColumnCount();
			for(int r=0;resultSet.next();r++){
				for(int j=0;j<cols;j++){
					System.out.println(resultSet.getObject(j+1)+"?????????????????????????");
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}

}
