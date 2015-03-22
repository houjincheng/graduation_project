package anyviewj.net.client;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import anyviewj.net.common.Request;

public class LoginRequest extends Request{

	private String studentID = null;
	private String password = null;
	
	
	public LoginRequest() {
	}
	
	public LoginRequest( String studentID, String password )
	{
		this.studentID = studentID;
		this.password = password;
	}
	@Override
	public int resolve( )
	{
		Document doc = buildData();
		Document result = null;
		ClientTerminal ct = ClientTerminal.getInstance();
		
		result = ct.server( doc );
		

		
		return getResultValue( result );
	}

	private int getResultValue( Document result )
	{
		Element root = result.getDocumentElement();
		int value = Integer.valueOf( root.getAttribute( REQUESTRESOLVER_RESULTNODE_ATTRIBUTE ) );
		
		if ( value == RESOLVE_SUCCESSED )
		{
			Element loginIDElement = ( Element )root.getElementsByTagName( LOGINTOKEN_NODE ).item( 0 );
			String loginToken = loginIDElement.getAttribute( LOGINTOKEN_NODE );
//			把登录指纹等信息保存到客户端，方便登录成功后执行其他请求
			ClientTerminal ct = ClientTerminal.getInstance();
			ct.setLoginToken( loginToken );
			ct.setStudentID( this.studentID );
			ct.setPassword( this.password );
		}
		
		return value;
	}
	private Document buildData()
	{
		Document doc = super.createFormattedRequsetDocment();
		Element studentIDNode = doc.createElement( LOGINREQUEST_STUDENTID );
		Element passwordNode = doc.createElement( LOGINREQUEST_PASSWORD );
		
		
		studentIDNode.setAttribute( LOGINREQUEST_STUDENTID, this.studentID );
		passwordNode.setAttribute( LOGINREQUEST_PASSWORD, this.password );

		super.appendChildToDataNode( doc, studentIDNode );
		super.appendChildToDataNode( doc, passwordNode );
		
		signResolver( "anyviewj.net.server.LoginRequestResolver", doc );
		return doc;
	}

	




}
