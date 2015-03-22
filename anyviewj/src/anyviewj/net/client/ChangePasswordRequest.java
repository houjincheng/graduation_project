package anyviewj.net.client;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import anyviewj.net.common.Request;

public class ChangePasswordRequest extends Request{

	private String studentID = null;
	private String oldPsw = null;
	private String newPsw = null;
	public static final int CHANGE_PASSWORD_SUCCESSED = 1;
	public static final int CHANGE_PASSWORD_FAIL = 0;
	
	public ChangePasswordRequest( String studentID, String oldPsw, String newPsw )
	{
		this.studentID = studentID;
		this.oldPsw = oldPsw;
		this.newPsw = newPsw;
	}
	@Override
	public int resolve() {

		Document doc = createFormattedRequsetDocment();
		
		signResolver( CHANGEPASSWORDREQUEST_RESOLVER, doc );
		
		Element studentIDNode =  doc.createElement( CHANGEPASSWORDREQUEST_STUDENTIDNODE );
		Element oldPswNode =  doc.createElement( CHANGEPASSWORDREQUEST_OLDPASSWORDNODE );
		Element newPswNode =  doc.createElement( CHANGEPASSWORDREQUEST_NEWPASSWORDNODE );
		
		studentIDNode.setAttribute( CHANGEPASSWORDREQUEST_STUDENTIDNODE, this.studentID );
		oldPswNode.setAttribute( CHANGEPASSWORDREQUEST_OLDPASSWORDNODE, this.oldPsw );
		newPswNode.setAttribute( CHANGEPASSWORDREQUEST_NEWPASSWORDNODE, this.newPsw );
		
		appendChildToDataNode( doc, studentIDNode );
		appendChildToDataNode( doc, oldPswNode );
		appendChildToDataNode( doc, newPswNode );
		
		Document result = ClientTerminal.getInstance().server( doc );
		String value = ( ( Element )result.
				getElementsByTagName(REQUESTRESOLVER_RESULTNODE ).item( 0 ) )
				.getAttribute( REQUESTRESOLVER_RESULTNODE_ATTRIBUTE );
		
		if ( Integer.valueOf( value ) == RESOLVE_SUCCESSED )
		{
			return CHANGE_PASSWORD_SUCCESSED;
		}
		return CHANGE_PASSWORD_FAIL;
	}


}
