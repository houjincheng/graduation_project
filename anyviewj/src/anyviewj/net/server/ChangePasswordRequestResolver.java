package anyviewj.net.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import anyviewj.net.common.RequestResolver;
/**
 * ������Ҫ�ر�ע��һ����ǣ�
 * �û��ĵ�¼ָ�����û����������ƴ��
 * �費��Ҫ���µ�¼���е����ݣ�����Ҫ���¿���
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
//		�û��ĵ�¼ָ�����û���������ļ�ƴ��
		{
			int value = process(studentID, newPsw );
			
			if ( value == 1 )
//			�������������Ϊ�ɹ�����ΪĬ����ʧ��
			{
				( ( Element )result.getElementsByTagName( REQUESTRESOLVER_RESULTNODE  ).item( 0 ) )
				.setAttribute( REQUESTRESOLVER_RESULTNODE_ATTRIBUTE,
						String.valueOf( RESOLVE_SUCCESSED ) );
			}
		}
		
		return result;
	}
	/**
	 * ����ָ��ѧ��������
	 * @param studentID
	 * @param newPsw
	 * @return �ɹ�����1������ʧ�ܷ���0
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
