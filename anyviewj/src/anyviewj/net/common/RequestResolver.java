package anyviewj.net.common;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class RequestResolver implements CommunicationProtocol{

	public abstract Document execute( Document doc );
	/**
	 * �涨RequestResolver���������XML��Ϣ��ʽ
	 * 
	 * ��Ϣͷָ���������ݰ���RequestResolver,
	 * ������������Ҫ����������
	 * @return
	 * @throws ParserConfigurationException
	 */
	protected Document createFormattedResultDocment()
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		Document doc = db.newDocument();
		
		Element result = doc.createElement( REQUESTRESOLVER_RESULTNODE );
		Element data = doc.createElement( REQUESTRESOLVER_RESULTNODE_DATACHILD );

		result.setAttribute( REQUESTRESOLVER_RESULTNODE_ATTRIBUTE, 
				String.valueOf( RESOLVE_FAILE ) );
		doc.appendChild( result );
		result.appendChild( data );
		
		return doc;
	}
	protected void signResult( Document doc, String value )
	{
		Element resultNode = doc.getDocumentElement();
		
		resultNode.setAttribute( REQUESTRESOLVER_RESULTNODE_ATTRIBUTE, value );
	}
	protected void appendChildToDataNode( Document doc, Element e )
	{
		Element root = doc.getDocumentElement();
		
		Element data = ( Element )root.getElementsByTagName( REQUESTRESOLVER_RESULTNODE_DATACHILD ).item( 0 );
		
		data.appendChild( e );
	}
}
