package anyviewj.net.server;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import anyviewj.net.common.CommunicationProtocol;
import anyviewj.net.common.RequestResolver;
import anyviewj.net.server.database.DBAccessorOld;
import anyviewj.net.server.database.DBConnectionPool;

public class LoginRequestResolver extends RequestResolver{

	public LoginRequestResolver()
	{
		
	}

	@Override
	public Document execute( Document doc ) {
		
		Document result = null;
		String studentID = "";
		String password = "";
		
		result = createFormattedResultDocment();
		
		Element root = doc.getDocumentElement();
		Element dataNode = ( Element )root.getElementsByTagName( REQUEST_DOCUMENT_DATANODE ).item( 0 );
		
		Element studentIDNode = ( Element )dataNode.getElementsByTagName( LOGINREQUEST_STUDENTID ).item( 0 );
		studentID = studentIDNode.getAttribute( LOGINREQUEST_STUDENTID );
		
		Element passwordNode = ( Element )dataNode.getElementsByTagName( LOGINREQUEST_PASSWORD ).item( 0 );
		password = passwordNode.getAttribute( LOGINREQUEST_PASSWORD );
		
		System.out.println( "studentID = " + studentID 
				+ "\npassword = " + password );
		
		int value = process( studentID, password );
		
		recordResult( result, value, studentID );
		return result;
	}
	/**
	 * 
	 * @param result
	 * @param value
	 */
	private void recordResult( Document result, int value, String studentID )
	{
//		把处理请求的最终结果保存到结果集中
		signResult( result, String.valueOf( value ) );

		if ( value == RESOLVE_SUCCESSED )
//		若是登陆成功了，还要在结果集和服务器端中都保存相同的一个唯一的登陆指纹，方便下一次请求验证
		{
			Element loginIDElement = result.createElement( LOGINTOKEN_NODE );
			
			ServerTerminal.getInstance().getLoginedClientMap().add( studentID );
			loginIDElement.setAttribute( LOGINTOKEN_NODE,  
			String.valueOf( ServerTerminal.getInstance().getLoginedClientMap().getToken( studentID ) ));
			appendChildToDataNode( result,  loginIDElement );
			
		}
		
	}
	private int process( String studentID, String password )
	{
		int state = CommunicationProtocol.STUDENTID_NOTFIND;
		
		String tableName = null;
		String studentNum = null;
		int len = 2;
		String[] column = new String[len];
		column[0] = DBAccessorOld.STUDENTINFORMATIONTABLE_STUDENTNUM;
		column[1] = DBAccessorOld.STUDENTINFORMATIONTABLE_PASSWORD;
		
		DBConnectionPool connPool = ServerTerminal.getInstance().getConnectionPool();
		Connection conn = connPool.getConnection();
		LinkedList<Object[]> queryResult = null;
		try
		{
			tableName = "dbo." + studentID.substring( 0, studentID.length() - 4 );
			studentNum = studentID.substring( studentID.length() - 4, studentID.length() );
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return state;
		}
		
		DBAccessorOld dBAccessor = new DBAccessorOld( conn );
		
		try {
			queryResult = dBAccessor.read( tableName, column );
		} catch (SQLException e) {
			
			e.printStackTrace();
			return state;
		}
		finally
		{
			System.out.println( "free conn" );
			connPool.freeConntion(conn);
		}
		
		for ( int i=0; i<queryResult.size(); i++ )
		{
			Object[] tmp = queryResult.get( i );
			
			if ( ( ( String )tmp[0] ).replaceAll( "\\s*", "").compareTo( studentNum ) == 0 )
//			找到对应序号
			{
				if ( ( password == "" ) && ( tmp[1] == null ) )
//					空密码
				{
					state = CommunicationProtocol.RESOLVE_SUCCESSED;
					break;
				}
				
				String pswTmp = ( ( String )tmp[1] ).replaceAll( "\\s*", "");
				if ( ( ( pswTmp ).compareTo( password ) == 0 ) )
				{
					state = CommunicationProtocol.RESOLVE_SUCCESSED;
				}
				else
				{
					state = CommunicationProtocol.PASSWORD_ERROR;
				}
				break;
			}
		}
		return state;
	}




}
