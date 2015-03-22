package anyviewj.net.table;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.xml.transform.TransformerFactoryConfigurationError;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import anyviewj.net.client.ClientTerminal;
import anyviewj.net.common.CommunicationProtocol;
import anyviewj.net.common.Request;

public class TableServerRequest extends Request{

	private String studentID = null;

	
	
	public static final int DOWNLOAD_TABLE_SUCCESSED = 1;
	public static final int DOWNLOAD_TABLE_FAIL = 0;
	
	public TableServerRequest(String id){
		studentID = id;
	}
	
	@Override
	public int resolve() throws NullPointerException {
		// TODO Auto-generated method stub
		
	    Document doc = createFormattedRequsetDocment();
	    
        signResolver( TABLESERVERREQUEST_RESOLVER, doc );
		
		Element tableNode =  doc.createElement( TABLESERVERREQUEST_TABLE );
	    Element studentIDNode = doc.createElement(TABLESERVERREQUEST_STUDENTIDNODE);
		
		tableNode.setAttribute( TABLESERVERREQUEST_TABLE, "dbo.ExerceiseTable" );
		studentIDNode.setAttribute( TABLESERVERREQUEST_STUDENTIDNODE, this.studentID );
		
		appendChildToDataNode( doc, studentIDNode );
		appendChildToDataNode(doc,tableNode);
		
		Document result = ClientTerminal.getInstance().server( doc );
				
		
		String value = ( ( Element )result.
				getElementsByTagName(REQUESTRESOLVER_RESULTNODE ).item( 0 ) )
				.getAttribute( REQUESTRESOLVER_RESULTNODE_ATTRIBUTE );
		
		if ( Integer.valueOf( value ) == RESOLVE_SUCCESSED )
		{
			return  DOWNLOAD_TABLE_SUCCESSED;
		}
		return  DOWNLOAD_TABLE_FAIL;
	}
	
	
	

}
